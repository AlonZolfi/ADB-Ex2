package hib;
// Generated Dec 14, 2019 11:35:03 AM by Hibernate Tools 4.3.5.Final

/**
 * Loginlog generated by hbm2java
 */
public class Loginlog implements java.io.Serializable {

	private LoginlogId id;
	private Users users;

	public Loginlog() {
	}

	public Loginlog(LoginlogId id, Users users) {
		this.id = id;
		this.users = users;
	}

	public LoginlogId getId() {
		return this.id;
	}

	public void setId(LoginlogId id) {
		this.id = id;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
