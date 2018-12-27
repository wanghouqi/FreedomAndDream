package hq.fad.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;
import hq.fad.dao.mapper.LocalRowMapper;
import hq.fad.dao.pojo.BasePOJO;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.exception.DAOException;
import hq.fad.utils.orderby.OrderBy;
import hq.fad.utils.orderby.Sort;

/**
 * 数据库访问对象,所有的数据库访问都通过当前类来实现
 * @author Administrator
 *
 */
@Component
public class BaseDAO {
	private boolean showSQL = true;
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(this.getClass().getName());

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void test() {
		try {
			System.out.println("in BaseDAO.test()");
			//this.jdbcTemplate.execute("create table testTable (CN_ID int,CN_NAME varchar(255))");
			//			for (int i = 0; i < 10; i++) {
			//				this.jdbcTemplate.execute("INSERT INTO testTable VALUES (" + i + ", '" + i + "')");
			//			}
			//			int a = jdbcTemplate.queryForInt("select count(1) from testTable");
			//			System.out.println(a);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException("test()", e);
		}
	}

	public <T> ArrayList<T> query(Class<T> c) {
		return query(c, 1, new CondSetBean(), new OrderBy());
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel) {
		return query(c, deepLevel, new CondSetBean(), new OrderBy());
	}

	public <T> ArrayList<T> query(Class<T> c, CondSetBean csb) {
		return query(c, 1, csb, new OrderBy());
	}

	public <T> ArrayList<T> query(Class<T> c, Sort sort) {
		return query(c, 1, new CondSetBean(), new OrderBy(sort));
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel, Sort sort) {
		return query(c, deepLevel, new CondSetBean(), new OrderBy(sort));
	}

	public <T> ArrayList<T> query(Class<T> c, CondSetBean csb, Sort sort) {
		return query(c, 1, csb, new OrderBy(sort));
	}

	public <T> ArrayList<T> query(Class<T> c, OrderBy orderby) {
		return query(c, 1, new CondSetBean(), orderby);
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel, CondSetBean csb) {
		return query(c, deepLevel, csb, new OrderBy());
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel, OrderBy orderby) {
		return query(c, deepLevel, new CondSetBean(), orderby);
	}

	public <T> ArrayList<T> query(Class<T> c, CondSetBean csb, OrderBy orderby) {
		return query(c, 1, csb, orderby);
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel, CondSetBean csb, OrderBy orderby) {
		return this.query(c, deepLevel, csb, orderby, null);
	}

	public <T> ArrayList<T> query(Class<T> c, int deepLevel, CondSetBean csb, Sort sort, JSONObject splitPageSet) {
		return this.query(c, deepLevel, csb, new OrderBy(sort), splitPageSet);
	}

	/**
	 * 数据库查询方法
	 * @param c : 所要查询的表的POJO.class
	 * @param deepLevel : 查几层数据,1代表只查询当前层,大于1则向下查询
	 * @param conditions : 查询条件,是JSONObject的数组
	 * 				|--> key : 查询的栏位: CN_NO
	 * 				|--> operator : 运算符,大于,等于,between等..
	 * 				|--> value : 值,可以是字符,数字,JSONArray等..
	 * 			 
	 * @return
	 */
	public <T> ArrayList<T> query(Class<T> c, int deepLevel, CondSetBean csb, OrderBy orderby, JSONObject splitPageSet) {
		ArrayList<T> alPOJO = null;
		try {
			String tableName = c.getAnnotation(ClassMeta.class).tablename();
			// 读取当前Class对应的数据
			BasePOJO pojoInst = (BasePOJO) c.newInstance();
			String sql = "SELECT " + pojoInst.getAttributeSQL() + " FROM " + tableName;
			String whereSQL = csb.toSQLString();
			String orderBySQL = orderby.toSQLString();
			if (StringUtils.isNotBlank(whereSQL)) {
				sql += " WHERE " + whereSQL;
			}
			if (StringUtils.isNotBlank(orderBySQL)) {
				sql += " ORDER BY " + orderBySQL;
			}
			if (splitPageSet != null && splitPageSet.getIntValue("onePageNumber") > 0) {
				int startIndex = splitPageSet.getIntValue("pageIndex") * splitPageSet.getIntValue("onePageNumber");
				sql += " LIMIT " + startIndex + "," + splitPageSet.getIntValue("onePageNumber");
			}
			if (showSQL) {
				System.out.println(sql);
			}
			alPOJO = (ArrayList<T>) jdbcTemplate.query(sql, new LocalRowMapper<T>(c));

			// 格局deepLevel向下读取
			if (--deepLevel > 0) {
				processDeepQuery(c, deepLevel, alPOJO);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alPOJO;
	}

	/**
	 * 这里代码存在问题,目前没有时间详细处理
	 * 	1. 需要处理ArrayList<AbstractBasePOJO>和HashSet<AbstractBasePOJO>这类的集合行数据.
	 * 	2. 现在的代码需要拆分为两部分,第一处理AbstractBasePOJO类型,第二处理集合行数据.
	 * @param <T>
	 * @param c : 查询数据的类型(父类)
	 * @param deepLevel : 要向下查几层
	 * @param alPOJO : 需要补充属性的POJO的集合
	 * @throws IllegalAccessException
	 */
	private <T> void processDeepQuery(Class<T> c, int deepLevel, ArrayList<T> alPOJO) throws IllegalAccessException {
		// 构建下层要查询的Field和CN_ID数组对应的HashMap
		HashMap<Field, HashSet<String>> hmAttributeFieldToPrimaryId_single = new HashMap<Field, HashSet<String>>();// 处理一对一
		/*
		 * 遍历c的所有属性,找出属性类型为BasePOJO的属性.构建Field与primaryIdArray之间的关联,用于查询和补全数据
		 */
		for (Field field : c.getDeclaredFields()) {
			field.setAccessible(true); // 设定后可以访问私有变量
			if (BasePOJO.class.isAssignableFrom(field.getType())) {
				// 属性为BasePOJO
				for (T pojo : alPOJO) {
					BasePOJO attributePOJO = (BasePOJO) field.get(pojo);
					if (hmAttributeFieldToPrimaryId_single.containsKey(field)) {
						hmAttributeFieldToPrimaryId_single.get(field).add(attributePOJO.getId());
					} else {
						HashSet<String> al = new HashSet<String>();
						al.add(attributePOJO.getId());
						hmAttributeFieldToPrimaryId_single.put(field, al);
					}
				}
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				// 属性为BasePOJO数组
				ParameterizedType pt = (ParameterizedType) field.getGenericType();// 获取泛型的类型
				// 判断泛型类的类型  
				if (BasePOJO.class.isAssignableFrom((Class<?>) pt.getActualTypeArguments()[0])) {
					HashSet<String> hsId = new HashSet<String>();// 记录当前需要查找子表记录的POJO数组中的ID
					for (T t : alPOJO) {
						BasePOJO pojo = (BasePOJO) t;
						hsId.add(pojo.getId());
					}
					FieldMeta fieldMeta = field.getAnnotation(FieldMeta.class);// 得到当前数组类型的属性的疏解,用于得到外键的相关设定.
					if (fieldMeta != null) {
						String foreignKey = fieldMeta.foreignKey();
						if (StringUtils.isNotBlank(foreignKey)) {
							foreignKey = foreignKey.trim();
							JSONObject jo = JSONObject.parseObject(foreignKey);
							String className = jo.getString("className");// 子表的Class Name
							String fkColumnName = jo.getString("columnName");// 当前id对应子表中的栏位名称
							OrderBy orderBy = new OrderBy();
							if (jo.get("orderBy") != null) {
								JSON json = (JSON) jo.get("orderBy"); // 排序规则
								orderBy = OrderBy.parseOrderBy(json);
							}
							CondSetBean csb = new CondSetBean();
							csb.addCondBean_in(fkColumnName, hsId);
							try {
								Class<?> subClz = Class.forName(className);
								Field fkField = null;
								for (Field subField : subClz.getDeclaredFields()) {
									FieldMeta subFieldMeta = subField.getAnnotation(FieldMeta.class);
									if (subFieldMeta != null && fkColumnName.contentEquals(subFieldMeta.columnName())) {
										fkField = subField;
										break;
									}
								}
								if (fkField == null) {
									throw new DAOException("子表管理错误,无法在子表中找到对应类FKColumnName的属性[注解].\r\n   ClassName: " + className + "\r\n   fkColumnName: " + fkColumnName);
								}
								fkField.setAccessible(true);
								ArrayList<BasePOJO> alSubPOJO = (ArrayList<BasePOJO>) this.query(subClz, csb, orderBy);
								HashMap<String, ArrayList<BasePOJO>> hmParentIdToSubPOJOArray = new HashMap<String, ArrayList<BasePOJO>>();// 根据Parent的Id度数据集分组
								for (BasePOJO subPOJO : alSubPOJO) {
									BasePOJO parentPOJO = (BasePOJO) fkField.get(subPOJO);// 得到ParentId
									String parentId = parentPOJO.getId();
									if (hmParentIdToSubPOJOArray.containsKey(parentId)) {
										hmParentIdToSubPOJOArray.get(parentId).add(subPOJO);
									} else {
										ArrayList<BasePOJO> al = new ArrayList<BasePOJO>();
										al.add(subPOJO);
										hmParentIdToSubPOJOArray.put(parentId, al);
									}
								}
								fkField.setAccessible(false);
								for (T t : alPOJO) {
									BasePOJO pojo = (BasePOJO) t;
									field.set(pojo, hmParentIdToSubPOJOArray.get(pojo.getId()));
								}

							} catch (ClassNotFoundException e) {
								throw new DAOException("子表的ClassName错误,无法找到对应类.\r\n   ClassName: " + className, e);
							}
						}
					}
				}
			} else if (field.getType().isArray()) {
				throw new DAOException("当前属性为[数组]类型,无法查询!查询的属性如果是集合请使用Collection.");
			}
			field.setAccessible(false); // 设定后可以访问私有变量
		}

		/*
		 * 处理一对一关联
		 */
		if (hmAttributeFieldToPrimaryId_single.size() > 0) {
			// 查询二级数据并将结果放入对应的POJO
			for (Entry<Field, HashSet<String>> entry : hmAttributeFieldToPrimaryId_single.entrySet()) {
				Field field = entry.getKey();// AbstractBasePOJO类型的属性
				field.setAccessible(true); // 设定后可以访问私有变量
				// 读取数据
				CondSetBean csbSecond = new CondSetBean();
				csbSecond.addCondBean_in("CN_ID", entry.getValue());
				@SuppressWarnings("unchecked")
				ArrayList<BasePOJO> alAttributePOJO = (ArrayList<BasePOJO>) this.query(field.getType(), deepLevel, csbSecond);
				HashMap<String, BasePOJO> hmIdToAttributePOJO = new HashMap<String, BasePOJO>();
				for (BasePOJO attributePOJO : alAttributePOJO) {
					hmIdToAttributePOJO.put(attributePOJO.getId(), attributePOJO);
				}
				// 将读取的数据放入POJO
				for (T pojo : alPOJO) {
					BasePOJO attributePOJO_old = (BasePOJO) field.get(pojo);
					field.set(pojo, hmIdToAttributePOJO.get(attributePOJO_old.getId()));
				}
				field.setAccessible(false); // 设定后可以访问私有变量
			}
		}
	}

	public <T> T queryObject(Class<T> c, String primaryId) {
		return this.queryObject(c, 1, primaryId);
	}

	public <T> T queryObject(Class<T> c, CondSetBean csb) {
		return this.queryObject(c, 1, csb);
	}

	public <T> T queryObject(Class<T> c, int deepLevel, String primaryId) {
		CondSetBean csb = new CondSetBean();
		csb.addCondBean_equal("CN_ID", primaryId);
		return this.queryObject(c, deepLevel, csb);
	}

	/**
	 * 数据库查询方法
	 * @param c : 所要查询的表的POJO.class
	 * @param deepLevel : 查几层数据,1代表只查询当前层,大于1则向下查询
	 * @param conditions : 查询条件,是JSONObject的数组
	 * 				|--> key : 查询的栏位: CN_NO
	 * 				|--> operator : 运算符,大于,等于,between等..
	 * 				|--> value : 值,可以是字符,数字,JSONArray等..
	 * 			 
	 * @return
	 */
	public <T> T queryObject(Class<T> c, int deepLevel, CondSetBean csb) {
		T retPOJO = null;
		try {
			String tableName = c.getAnnotation(ClassMeta.class).tablename();
			// 读取当前Class对应的数据
			BasePOJO pojoInst = (BasePOJO) c.newInstance();
			String sql = "SELECT " + pojoInst.getAttributeSQL() + " FROM " + tableName;
			String whereSQL = csb.toSQLString();
			if (StringUtils.isNotBlank(whereSQL)) {
				sql += " WHERE " + whereSQL;
			}
			if (showSQL) {
				System.out.println(sql);
			}
			retPOJO = c.cast(jdbcTemplate.queryForObject(sql, new LocalRowMapper<Object>(c)));

			// 格局deepLevel向下读取
			if (--deepLevel > 0) {
				ArrayList<T> al = new ArrayList<T>();
				al.add(retPOJO);
				processDeepQuery(c, deepLevel, al);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			retPOJO = null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			retPOJO = null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			retPOJO = null;
		}
		return retPOJO;
	}

	public void save(BasePOJO pojo) {
		if (pojo == null) {
			return;
		}
		ArrayList<BasePOJO> alPOJO = new ArrayList<BasePOJO>();
		alPOJO.add(pojo);
		this.save(alPOJO);
	}

	public void save(ArrayList<BasePOJO> alPOJO) {
		if (alPOJO == null || alPOJO.size() == 0) {
			return;
		}
		for (BasePOJO abstractBasePOJO : alPOJO) {
			abstractBasePOJO.save(this);
		}
	}

	public void delete(BasePOJO pojo) {
		if (pojo == null) {
			return;
		}
		ArrayList<BasePOJO> alPOJO = new ArrayList<BasePOJO>();
		alPOJO.add(pojo);
		this.delete(alPOJO);
	}

	/**
	 * 将传入的POJO全部删除
	 * @param alPOJO
	 */
	public void delete(ArrayList<BasePOJO> alPOJO) {
		if (alPOJO == null || alPOJO.size() == 0) {
			return;
		}
		for (BasePOJO basePOJO : alPOJO) {
			basePOJO.delete(this);
		}
	}

	/**
	 * 执行Insert　SQL　返回新的ID值
	 *   INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
	 * @param insertSQL
	 * @return HashMap<String, String> 
	 */
	public void insert(final String insertSQL) {
		if (showSQL) {
			System.out.println(insertSQL);
		}
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				return ps;
			}
		});
	}

	/**
	 * 执行updateSQL
	 *   UPDATE 表名称 SET 列名称1 = 新值1,列名称2 = 新值2 where .......
	 * @param updateSQL
	 * @return HashMap
	 */
	public void update(String updateSQL) {
		if (showSQL) {
			System.out.println(updateSQL);
		}
		this.jdbcTemplate.update(updateSQL);
	}

	/**
	 * 执行deleteSQL
	 *   DELETE FROM 表名称  WHERE CN_ID IN 'primaryId'
	 * @param deleteSQL
	 * @return String
	 */
	public boolean delete(String deleteSQL) {
		boolean isOK = true;
		try {
			if (showSQL) {
				System.out.println(deleteSQL);
			}
			this.jdbcTemplate.update(deleteSQL);
		} catch (Exception e) {
			isOK = false;
		}
		return isOK;
	}

	/**
	 * 根据TableName,依据ID删除数据
	 * @param tableName
	 * @param hsPrimaryId
	 */
	public boolean deleteById(Class<?> clz, String primaryId) {
		HashSet<String> hsPrimaryId = new HashSet<String>();
		hsPrimaryId.add(primaryId);
		return this.deleteInId(clz, hsPrimaryId);
	}

	/**
	 * 根据TableName,依据ID删除数据
	 * @param tableName
	 * @param hsPrimaryId
	 */
	public boolean deleteByIds(Class<?> clz, String[] primaryIds) {
		HashSet<String> hsPrimaryId = new HashSet<>(Arrays.asList(primaryIds));
		return this.deleteInId(clz, hsPrimaryId);
	}

	/**
	 * 根据TableName,依据ID删除数据
	 * @param tableName
	 * @param hsPrimaryId
	 */
	public boolean deleteInId(Class<?> clz, HashSet<String> hsPrimaryId) {
		boolean isOk = false;
		CondSetBean csb = new CondSetBean();
		csb.addCondBean_in("CN_ID", hsPrimaryId);
		@SuppressWarnings("unchecked")
		ArrayList<BasePOJO> al = (ArrayList<BasePOJO>) this.query(clz, csb);
		for (BasePOJO basePOJO : al) {
			basePOJO.delete(this);
		}
		return isOk;
	}
}
