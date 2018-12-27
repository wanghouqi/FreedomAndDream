package hq.fad.dao.pojo;

import java.util.ArrayList;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 文章
 * Spittle entity.
 */

@ClassMeta(tablename = "TN_SPITTLE")
public class Spittle extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 4691940965512856734L;

	// Fields
	@FieldMeta(columnName = "CR_SPITTLE_TYPE_ID")
	private SpittleType spittleType; // 文章类型
	@FieldMeta(columnName = "CR_USER_ID")
	private User user;// 用户
	@FieldMeta(columnName = "CN_TITLE")
	private String title;// 标题
	@FieldMeta(columnName = "CN_CONTENT")
	private String content;// 内容
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 上传时间
	@FieldMeta(columnName = "CN_VIEW_COUNT")
	private Long viewCount;// 浏览次数统计
	@FieldMeta(columnName = "CN_ADMIRE_COUNT")
	private Long admireCount;// 点赞次数统计
	@FieldMeta(columnName = "CR_ACTIVE_FLAG")
	private FBoolean activeFlag;// 活动标识 
	@FieldMeta(columnName = "CR_AUTHORIZED_FLAG")
	private FBoolean authorizedFlag;// 审核通过标识
	@FieldMeta(foreignKey = "{className:'hq.fad.dao.pojo.SpittleFollow', columnName: 'CR_SPITTLE_ID'}")
	private ArrayList<SpittleFollow> alSpittleFollow = new ArrayList<SpittleFollow>();// 跟帖数组

	/**
	 * 
	 */
	public Spittle() {
	}

	public Spittle(String id) {
		super(id);
	}

	/**
	 * @return the spittleType
	 */
	public SpittleType getSpittleType() {
		return spittleType;
	}

	/**
	 * @param spittleType the spittleType to set
	 */
	public void setSpittleType(SpittleType spittleType) {
		this.spittleType = spittleType;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the viewCount
	 */
	public Long getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
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
	 * @return the activeFlag
	 */
	public FBoolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(FBoolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the authorizedFlag
	 */
	public FBoolean getAuthorizedFlag() {
		return authorizedFlag;
	}

	/**
	 * @param authorizedFlag the authorizedFlag to set
	 */
	public void setAuthorizedFlag(FBoolean authorizedFlag) {
		this.authorizedFlag = authorizedFlag;
	}

	/**
	 * @return the alSpittleFollow
	 */
	public ArrayList<SpittleFollow> getAlSpittleFollow() {
		return alSpittleFollow;
	}

	/**
	 * @param alSpittleFollow the alSpittleFollow to set
	 */
	public void setAlSpittleFollow(ArrayList<SpittleFollow> alSpittleFollow) {
		this.alSpittleFollow = alSpittleFollow;
	}

	public void addSpittleFollow(SpittleFollow spittleFollow) {
		this.alSpittleFollow.add(spittleFollow);
	}

}