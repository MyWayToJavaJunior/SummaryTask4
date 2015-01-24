package ua.nure.norkin.SummaryTask4.entity;

public class Entrant extends Entity {

	private static final long serialVersionUID = 2565574420335652970L;
	private String city;
	private String district;
	private String school;
	private int userId;
	private boolean isBlocked;

	public Entrant(String city, String district, String school, User user) {
		this(city, district, school, user.getId());
	}

	public Entrant(String city, String district, String school, int userId) {
		this.city = city;
		this.district = district;
		this.school = school;
		this.userId = userId;
		this.isBlocked = false;
	}

	public Entrant() {
	}

	public void block() {
		isBlocked = true;
	}

	public void unblock() {
		isBlocked = false;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	@Override
	public String toString() {
		return "KHPIEntrant [city=" + city + ", district=" + district
				+ ", school=" + school + ", userId=" + userId + ", isBlocked="
				+ isBlocked + "]";
	}

}
