package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 跟帖
 * SpittleFollow entity. 
 */

@ClassMeta(tablename = "TN_SPITTLE_FOLLOW")
public class SpittleFollow extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -635084921121305670L;

	// Fields
	@FieldMeta(columnName = "CR_USER_ID")
	private User user; // 用户
	@FieldMeta(columnName = "CR_SPITTLE_ID")
	private Spittle spittle;// 所属文章
	@FieldMeta(columnName = "CN_CONTENT")
	private String content;// 内容
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 跟帖时间
	@FieldMeta(columnName = "CN_ADMIRE_COUNT")
	private Long admireCount;// 点赞统计
	@FieldMeta(columnName = "CR_PARENT_ID")
	private SpittleFollow parentSpittleFollow; // 所属父跟帖

	/**
	 * 
	 */
	public SpittleFollow() {
	}

	public SpittleFollow(String id) {
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
	 * @return the admireCount
	 */
	public Long getAdmireCount() {
		return admireCount;
	}

	/**
	 * @param admireCount the admireCount to set
	 */
	public void setAdmireCount(Long admireCount) {
		this.admireCount = admireCount;
	}

	/**
	 * @return the parentSpittleFollow
	 */
	public SpittleFollow getParentSpittleFollow() {
		return parentSpittleFollow;
	}

	/**
	 * @param parentSpittleFollow the parentSpittleFollow to set
	 */
	public void setParentSpittleFollow(SpittleFollow parentSpittleFollow) {
		this.parentSpittleFollow = parentSpittleFollow;
	}

}