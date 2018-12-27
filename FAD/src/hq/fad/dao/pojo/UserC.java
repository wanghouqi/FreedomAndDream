package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 系统管理员
 * UserC entity.
 */

@ClassMeta(tablename = "TN_USER_C")
public class UserC extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = -6619234373338505937L;

	// Fields    
	@FieldMeta(columnName = "CR_USER_ID")
	private User user;// 管理的普通用户
	@FieldMeta(columnName = "CN_NO")
	private String no;// 工号
	@FieldMeta(columnName = "CN_NAME")
	private String name;// 姓名
	@FieldMeta(columnName = "CN_LOGIN_NAME")
	private String loginName;// 登录帐号
	@FieldMeta(columnName = "CN_PASSWORD")
	private String password;// 密码
	@FieldMeta(columnName = "CR_SUPPER_FLAG")
	private FBoolean supperFlag;// 是否为超级管理员
	@FieldMeta(columnName = "CR_ACTIVE_FLAG")
	private FBoolean activeFlag;// 是否为活动用户

	/**
	 * 
	 */
	public UserC() {
	}

	public UserC(String id) {
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
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
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

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the supperFlag
	 */
	public FBoolean getSupperFlag() {
		return supperFlag;
	}

	/**
	 * @param supperFlag the supperFlag to set
	 */
	public void setSupperFlag(FBoolean supperFlag) {
		this.supperFlag = supperFlag;
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

}