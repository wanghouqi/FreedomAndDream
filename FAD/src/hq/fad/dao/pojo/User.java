package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 用户 User entity.
 */

@ClassMeta(tablename = "TN_USER", deleteSubTable = "[{tableName:'hq.fad.dao.pojo.UserProfile', columnName: 'CN_ID}]")
public class User extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 5376159925308517205L;
	// Fields
	@FieldMeta(columnName = "CN_LOGIN_NAME")
	private String loginName; // 登录帐号
	@FieldMeta(columnName = "CN_EMAIL")
	private String email;// 邮箱
	@FieldMeta(columnName = "CN_MOBILE_NO")
	private String mobileNo;// 手机号
	@FieldMeta(columnName = "CN_PASSWORD")
	private String password;// 密码
	@FieldMeta(columnName = "CR_USER_PROFILE_ID")
	private UserProfile userProfile; // 用户的详细信息
	@FieldMeta(columnName = "CR_ACTIVE_FLAG")
	private FBoolean activeFlag;// 是否为活动用户

	/**
	 * 
	 */
	public User() {
	}

	public User(String id) {
		super(id);
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * @param userProfile
	 *            the userProfile to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * @return the activeFlag
	 */
	public FBoolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(FBoolean activeFlag) {
		this.activeFlag = activeFlag;
	}

}