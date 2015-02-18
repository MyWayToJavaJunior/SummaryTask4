package ua.nure.norkin.SummaryTask4.entity;

/**
 * User entity. This transfer object characterized by id, first and last names,
 * email, password, role in system, site language and active status. Email
 * should be unique. Every field must be filled.
 *
 * @author Mark Norkin
 *
 */
public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
	private String lang;
	private boolean activeStatus;

	public User() {
	}

	public User(String email, String password, String firstName,
			String lastName, Role role, String lang, boolean activeStatus) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role.getName();
		this.lang = lang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String login) {
		this.email = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", lang=" + lang + ", activeStatus="
				+ activeStatus + "]";
	}

}
