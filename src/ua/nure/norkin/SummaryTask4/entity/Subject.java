package ua.nure.norkin.SummaryTask4.entity;

/**
 * Subject entity. Every subject is characterized by its name.
 *
 * @author Mark Norkin
 *
 */
public class Subject extends Entity {

	private static final long serialVersionUID = -5388561545513613948L;
	private String name;

	public Subject(String name) {
		this.name = name;
	}

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + "]";
	}

}
