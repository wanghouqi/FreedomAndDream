package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 用户点赞记录
 * UserAdmireSpittleLog entity.
 */

@ClassMeta(tablename = "TN_USER_ADMIRE_SPITTLE_LOG")
public class UserAdmireSpittleLog extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 3428788870117400265L;

	// Fields    
	@FieldMeta(columnName = "CR_USER_ID")
	private User user;// 用户
	@FieldMeta(columnName = "CR_SPITTLE_ID")
	private Spittle spittle;// 点赞的文章
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 点赞时间

	/**
	 * 
	 */
	public UserAdmireSpittleLog() {
	}

	public UserAdmireSpittleLog(String id) {
		super(id);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the spittle
	 */
	public Spittle getSpittle() {
		return spittle;
	}

	/**
	 * @param spittle the spittle to set
	 */
	public void setSpittle(Spittle spittle) {
		this.spittle = spittle;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

}