package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 布尔表 Boolean entity.
 */

@ClassMeta(tablename = "TL_BOOLEAN")
public class FBoolean extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -5309266798543557689L;
	public static final String YES = "BOOLEAN_YES";// 是
	public static final String NO = "BOOLEAN_NO";// 否

	// Fields
	@FieldMeta(columnName = "CN_NAME")
	private String name;

	public FBoolean() {
	}

	public FBoolean(String id) {
		super(id);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}