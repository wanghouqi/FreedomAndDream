package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 文章修改日志表
 * SpittleLog entity.
 */

@ClassMeta(tablename = "TN_SPITTLE_LOG")
public class SpittleLog extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -2327374351133786070L;

	// Fields    

	@FieldMeta(columnName = "CR_USER_ID")
	private User user; // 修改用户
	@FieldMeta(columnName = "CR_SPITTLE_ID")
	private Spittle spittle;// 修改帖子
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 修改时间
	@FieldMeta(columnName = "CN_CONTENT")
	private String content;// 修改后的内容

	/**
	 * 
	 */
	public SpittleLog() {
	}

	public SpittleLog(String id) {
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

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}