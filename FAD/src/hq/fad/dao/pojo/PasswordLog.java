package hq.fad.dao.pojo;

import hq.fad.dao.annotation.ClassMeta;
import hq.fad.dao.annotation.FieldMeta;

/**
 * 密码修改日志 PasswordLog entity.
 */

@ClassMeta(tablename = "TN_PASSWORD_LOG")
public class PasswordLog extends BasePOJO implements java.io.Serializable {
	private static final long serialVersionUID = 6977537681555115590L;

	// Fields
	@FieldMeta(columnName = "CR_USER_ID")
	private User user; // 用户
	@FieldMeta(columnName = "CN_PASSWORD")
	private String password; // 密码
	@FieldMeta(columnName = "CN_TIME")
	private Long time;// 修改时间
	@FieldMeta(columnName = "CN_IP")
	private String ip;// 用户ip

	/**
	 * 
	 */
	public PasswordLog() {
	}

	public PasswordLog(String id) {
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

}