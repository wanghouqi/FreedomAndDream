package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 登录日志 LoginLog entity.
 */
@ClassMeta(tablename = "TN_LOGIN_LOG")
public class LoginLog extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 4924492243209174205L;

	// Fields
	@FieldMeta(columnName = "CR_USER_ID")
	private User user;// 用户
	@FieldMeta(columnName = "CN_TIME")
	private Long time; // 时间
	@FieldMeta(columnName = "CN_IP")
	private String ip; // ip
	@FieldMeta(columnName = "CN_ADDRESS")
	private String address; // 地址

	/**
	 * 
	 */
	public LoginLog() {
	}

	public LoginLog(String id) {
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

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}