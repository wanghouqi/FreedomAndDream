/**
 * 
 */
package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;

/**
 * 一个标准的POJO的样例,与项目无关.
 * @author Administrator
 *
 */
@ClassMeta(tablename = "TN_SAMPLE")
public class SamplePOJO extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -7108780255547010418L;

	/**
	 * 
	 */
	public SamplePOJO() {
	}

	public SamplePOJO(String id) {
		super(id);
	}

}
