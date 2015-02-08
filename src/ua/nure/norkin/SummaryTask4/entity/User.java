package ua.nure.norkin.SummaryTask4.entity;

/**
 * User entity. This transfer object characterized by first and last names,
 * email, password and role in system. Email should be unique. Every field must
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

	public User() {
	}

	public User(String email, String password, String firstName,
			String lastName, Role role, String lang) {
		this(email, password, firstName, lastName, role.getName(), lang);
	}

	public User(String email, String password, String firstName,
			String lastName, String role, String lang) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
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

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", lang=" + lang + "]";
	}

}
