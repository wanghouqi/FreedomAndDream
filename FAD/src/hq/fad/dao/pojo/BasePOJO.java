package hq.fad.dao.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import hq.fad.dao.BaseDAO;
import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;
import hq.fad.utils.FadHelper;
import hq.fad.utils.condition.CondSetBean;
import hq.fad.utils.exception.DAOException;

/**
 * 所有POJO类的父类,定义了必要的抽象方法和普通方法
 * 
 * @author Administrator
 *
 */
public abstract class BasePOJO {
	private HashSet<String> hsChangeFieldName = new HashSet<String>();// 记录被修改过的栏位名

	public static final String PURPOSE_INSERT = "insert";// 用于新增记录
	public static final String PURPOSE_UPDATE = "update";// 用于修改记录
	public static final String PURPOSE_UNDEFINED = "Undefined";// 未定义(数据库读取,或自己创建)

	private String purpose = PURPOSE_UNDEFINED;// 表示当前POJO为新new出来的.false时为数据库读取.
	@FieldMeta(id = true, columnName = "CN_ID")
	private String id;// 数据库的主键UUID,new的时候会自动创建一个新的ID.

	public BasePOJO() {
		super();
	}

	/**
	 * @param id
	 */
	public BasePOJO(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	protected void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 得到一个用户新增数据库记录的POJO
	 * @param clz : 新增记录的POJO类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInsertInstance(Class<T> clz) {
		DaoPOJOProxy cglib = new DaoPOJOProxy();
		BasePOJO pojo = cglib.getInstance(clz);
		pojo.setId(FadHelper.createUUID());
		pojo.setPurpose(BasePOJO.PURPOSE_INSERT);
		return (T) pojo;
	}

	/**
	 * 得到一个用户修改数据库记录的POJO
	 * @param clz : 修改记录的POJO类型
	 * @param primaryId : 修改记录的主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getUpdateInstance(Class<T> clz, String primaryId) {
		DaoPOJOProxy cglib = new DaoPOJOProxy();
		BasePOJO pojo = cglib.getInstance(clz);
		pojo.setId(primaryId);
		pojo.setPurpose(BasePOJO.PURPOSE_UPDATE);
		return (T) pojo;
	}

	protected void addModifyFieldName(String fieldName) {
		this.hsChangeFieldName.add(fieldName);
	}

	/**
	 * 返回当前POJO的栏位对应的SQL语句
	 * 
	 * @param baseDAO
	 * @return
	 */
	public String getAttributeSQL() {
		Class<?> clz = null;// 得到当前真正的要操作的对象.
		if (BasePOJO.class == this.getClass().getSuperclass()) {
			clz = this.getClass();
		} else if (BasePOJO.class == this.getClass().getSuperclass().getSuperclass()) {
			clz = this.getClass().getSuperclass();
		}
		StringBuffer sbSQL_val = new StringBuffer();
		boolean isStart = true;
		ArrayList<Field> alField = new ArrayList<Field>();
		for (Field field : clz.getDeclaredFields()) {
			alField.add(field);
		}
		for (Field field : clz.getSuperclass().getDeclaredFields()) {
			alField.add(field);
		}
		for (Field f : alField) { // 获取字段中包含fieldMeta的注解 FieldMeta meta =
			if (Collection.class.isAssignableFrom(f.getType()) || f.getType().isArray()) {
				continue;// 过滤掉数组和集合的类型的参数.
			}
			FieldMeta meta = f.getAnnotation(FieldMeta.class);
			if (meta != null && meta.needQuery()) {
				if (!isStart) {
					sbSQL_val.append(",");
				} else {
					isStart = false;
				}
				sbSQL_val.append(meta.columnName() + " AS '" + f.getName() + "'");
			}
		}
		return sbSQL_val.toString();
	}

	/**
	 * 当前POJO的保存方法,依据isNew来进行insert Or Update操作.
	 * 
	 * @param baseDAO
	 * @return
	 */
	public void save(BaseDAO baseDAO) {
		Class<?> clz = null;// 得到当前真正的要操作的对象.
		if (BasePOJO.class == this.getClass().getSuperclass()) {
			clz = this.getClass();
		} else if (BasePOJO.class == this.getClass().getSuperclass().getSuperclass()) {
			clz = this.getClass().getSuperclass();
		}
		/*
		 *  处理属性为BasePOJO的数据的保存(外键数据)
		 */
		ArrayList<Field> alField = new ArrayList<Field>();
		for (Field field : clz.getDeclaredFields()) {
			alField.add(field);
		}
		for (Field field : clz.getSuperclass().getDeclaredFields()) {
			alField.add(field);
		}
		for (Field f : alField) {
			if (BasePOJO.class.isAssignableFrom(f.getType())) {
				f.setAccessible(true);//允许访问私有字段 
				try {
					Object value = f.get(this);
					if (value != null) {
						((BasePOJO) value).save(baseDAO);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				f.setAccessible(false);//允许访问私有字段 
			}
		}
		/*
		 * 处理自身保存
		 */
		String saveSQL = this.getSaveSQL();
		if (BasePOJO.PURPOSE_INSERT.contentEquals(this.getPurpose())) {
			baseDAO.insert(saveSQL);
		} else if (BasePOJO.PURPOSE_UPDATE.contentEquals(this.getPurpose())) {
			baseDAO.update(saveSQL);
		}
	}

	/**
	 * 当前POJO的删除方法,依据id对记录进行删除,如果没有id则抛出异常.
	 * 
	 * @param baseDAO
	 * @return
	 */
	public boolean delete(BaseDAO baseDAO) {
		boolean isOK = false;
		/*
		 * 删除自身相关子表
		 */
		isOK = this.deleteSub(baseDAO);
		/*
		 * 删除自身记录
		 */
		if (isOK) {
			Class<?> clz = null;// 得到当前真正的要操作的对象.
			if (BasePOJO.class == this.getClass().getSuperclass()) {
				clz = this.getClass();
			} else if (BasePOJO.class == this.getClass().getSuperclass().getSuperclass()) {
				clz = this.getClass().getSuperclass();
			}
			String tableName = clz.getAnnotation(ClassMeta.class).tablename();
			String deleteSQL = "DELETE FROM " + tableName + " WHERE CN_ID = '" + this.getId() + "'";
			isOK = baseDAO.delete(deleteSQL);
		}
		return isOK;
	}

	/**
	 * 当前POJO的删除方法,依据id对记录进行删除,如果没有id则抛出异常.
	 * 
	 * @param baseDAO
	 * @return
	 */
	public boolean deleteSub(BaseDAO baseDAO) {
		boolean isOk = false;
		Class<?> clz = null;// 得到当前真正的要操作的对象.
		if (BasePOJO.class == this.getClass().getSuperclass()) {
			clz = this.getClass();
		} else if (BasePOJO.class == this.getClass().getSuperclass().getSuperclass()) {
			clz = this.getClass().getSuperclass();
		}
		/* 得到需要删除子表的JSON字符串 */
		String subTable = clz.getAnnotation(ClassMeta.class).deleteSubTable();
		if (StringUtils.isNotBlank(subTable)) {
			subTable = subTable.trim();
			JSONArray jaSubTable = new JSONArray();
			if (subTable.startsWith("{")) {
				JSONObject jo = JSONObject.parseObject(subTable);
				jaSubTable.add(jo);
			} else if (subTable.startsWith("[")) {
				jaSubTable = JSONArray.parseArray(subTable);
			} else {
				throw new DAOException("注解的子表格式错误.");
			}
			for (Object jo : jaSubTable) {
				/*
				 * 遍历要删除的子表JSONObject设定,得到对应的class以及当前id所对应的子表的栏位名.
				 */
				JSONObject joSubTableInfo = (JSONObject) jo;
				String className = joSubTableInfo.getString("className");
				String columnName = joSubTableInfo.getString("columnName");
				try {
					/*
					 * 读取旧数据.并逐个删除.
					 */
					Class<?> clz_sub = Class.forName(className);
					CondSetBean csbSub = new CondSetBean();
					csbSub.addCondBean_equal(columnName, this.getId());
					@SuppressWarnings("unchecked")
					ArrayList<BasePOJO> alSubPOJO = (ArrayList<BasePOJO>) baseDAO.query(clz_sub, csbSub);
					for (BasePOJO subPOJO : alSubPOJO) {
						subPOJO.delete(baseDAO);
					}
				} catch (ClassNotFoundException e) {
					throw new DAOException("子表的ClassName错误,无法找到对应类.\r\n   ClassName: " + className, e);
				}
			}
		}
		isOk = true;
		return isOk;
	}

	/**
	 * 根据当前POJO生成相应的保存语句,Insert Or Update.
	 * @return
	 */
	private String getSaveSQL() {
		String sql = "";
		try {
			Class<?> clz = null;// 得到当前真正的要操作的对象.
			if (BasePOJO.class == this.getClass().getSuperclass()) {
				clz = this.getClass();
			} else if (BasePOJO.class == this.getClass().getSuperclass().getSuperclass()) {
				clz = this.getClass().getSuperclass();
			}
			String tableName = clz.getAnnotation(ClassMeta.class).tablename();
			if (BasePOJO.PURPOSE_INSERT.contentEquals(this.getPurpose())) {
				StringBuffer sbSQL_key = new StringBuffer();
				StringBuffer sbSQL_val = new StringBuffer();
				boolean isStart = true;
				for (String changeFieldName : this.hsChangeFieldName) {
					Field field;
					try {
						field = clz.getDeclaredField(changeFieldName);
					} catch (NoSuchFieldException e) {
						try {
							field = clz.getSuperclass().getDeclaredField(changeFieldName);
						} catch (NoSuchFieldException e1) {
							field = null;
						}
					}
					FieldMeta fieldMeta = field.getAnnotation(FieldMeta.class);
					if (fieldMeta != null && fieldMeta.needSave()) {
						field.setAccessible(true);//允许访问私有字段 
						String columnName = fieldMeta.columnName();
						String valueSQL = this.getSQLValue(field.get(this));
						field.setAccessible(false);
						if (!isStart) {
							sbSQL_key.append(",");
							sbSQL_val.append(",");
						} else {
							isStart = false;
						}
						sbSQL_key.append(columnName);
						sbSQL_val.append(valueSQL);
					}
				}
				sql = "INSERT INTO " + tableName + " (" + sbSQL_key.toString() + ") VALUES (" + sbSQL_val.toString() + ")";
			} else if (BasePOJO.PURPOSE_UPDATE.contentEquals(this.getPurpose())) {
				StringBuffer sbSQL_val = new StringBuffer();
				boolean isStart = true;
				for (String changeFieldName : this.hsChangeFieldName) {
					Field field;
					try {
						field = clz.getDeclaredField(changeFieldName);
					} catch (NoSuchFieldException e) {
						try {
							field = clz.getSuperclass().getDeclaredField(changeFieldName);
						} catch (NoSuchFieldException e1) {
							field = null;
						}
					}
					FieldMeta fieldMeta = field.getAnnotation(FieldMeta.class);
					if (fieldMeta != null && fieldMeta.needSave() && !fieldMeta.id()) {
						field.setAccessible(true);//允许访问私有字段 
						String columnName = fieldMeta.columnName();
						String valueSQL = this.getSQLValue(field.get(this));
						field.setAccessible(false);
						if (!isStart) {
							sbSQL_val.append(",");
						} else {
							isStart = false;
						}
						sbSQL_val.append(columnName + "=" + valueSQL);
					}
				}
				sql = "UPDATE " + tableName + " SET " + sbSQL_val.toString() + " WHERE CN_ID='" + this.getId() + "'";
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sql;
	}

	private String getSQLValue(Object val) {
		String v = "";
		if (val == null) {
			v = "NULL";
		} else if (val instanceof BasePOJO) {
			v = "'" + ((BasePOJO) val).getId() + "'";
		} else if (val instanceof String) {
			v = "'" + val + "'";
		} else if (val instanceof Long) {
			v = String.valueOf(val);
		}
		return v;
	}

}
