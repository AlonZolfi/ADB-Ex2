package hib;
// Generated Dec 14, 2019 11:35:03 AM by Hibernate Tools 4.3.5.Final

/**
 * History generated by hbm2java
 */
public class History implements java.io.Serializable {

	private HistoryId id;
	private Mediaitems mediaitems;
	private Users users;

	public History() {
	}

	public History(HistoryId id, Mediaitems mediaitems, Users users) {
		this.id = id;
		this.mediaitems = mediaitems;
		this.users = users;
	}

	public HistoryId getId() {
		return this.id;
	}

	public void setId(HistoryId id) {
		this.id = id;
	}

	public Mediaitems getMediaitems() {
		return this.mediaitems;
	}

	public void setMediaitems(Mediaitems mediaitems) {
		this.mediaitems = mediaitems;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}