package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 用户浏览记录 UserViewSpittleLog entity.
 */

@ClassMeta(tablename = "TN_USER_VIEW_SPITTLE_LOG")
public class UserViewSpittleLog extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -766632736444584205L;

	// Fields
	@FieldMeta(columnName = "CR_USER_ID")
	private User user;// 用户
	@FieldMeta(columnName = "CR_SPITTLE_ID")
	private Spittle spittle;// 浏览的帖子
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 浏览时间

	/**
	 * 
	 */
	public UserViewSpittleLog() {
	}

	public UserViewSpittleLog(String id) {
		super(id);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
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
	 * @param spittle
	 *            the spittle to set
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
	 * @param time
	 *            the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

}