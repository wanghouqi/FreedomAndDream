package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 文章类型
 * SpittleType entity.
 */

@ClassMeta(tablename = "TN_SPITTLE_TYPE")
public class SpittleType extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -9127212481410767754L;

	// Fields    
	@FieldMeta(columnName = "CN_NAME")
	private String name; // 名称

	/**
	 * 
	 */
	public SpittleType() {
		super();
	}

	public SpittleType(String id) {
		super(id);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}