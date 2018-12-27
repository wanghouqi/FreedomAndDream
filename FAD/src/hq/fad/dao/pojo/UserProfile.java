package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 用户信息 UserProfile entity.
 */

@ClassMeta(tablename = "TN_USER_PROFILE")
public class UserProfile extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 8187091279605363900L;

	// Fields
	private User user; // 用户
	@FieldMeta(columnName = "CN_NAME")
	private String name; // 名字
	@FieldMeta(columnName = "CN_NICK_NAME")
	private String nickName;// 昵称
	@FieldMeta(columnName = "CN_INVITE_CODE")
	private String inviteCode;// 邀请码
	@FieldMeta(columnName = "CN_REGISTER_TIME")
	private Long registerTime;// 注册时间
	@FieldMeta(columnName = "CN_HOME_PAGE")
	private String homePage;// 主页URL
	@FieldMeta(columnName = "CN_HEAD_IMG")
	private String headImg;// 头像
	@FieldMeta(columnName = "CN_REMARK")
	private String remark;// 个性签名

	/**
	 * 
	 */
	public UserProfile() {
	}

	public UserProfile(String id) {
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

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the inviteCode
	 */
	public String getInviteCode() {
		return inviteCode;
	}

	/**
	 * @param inviteCode
	 *            the inviteCode to set
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	/**
	 * @return the registerTime
	 */
	public Long getRegisterTime() {
		return registerTime;
	}

	/**
	 * @param registerTime
	 *            the registerTime to set
	 */
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		return homePage;
	}

	/**
	 * @param homePage
	 *            the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	/**
	 * @return the headImg
	 */
	public String getHeadImg() {
		return headImg;
	}

	/**
	 * @param headImg
	 *            the headImg to set
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}