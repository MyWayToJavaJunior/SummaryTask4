package ua.nure.norkin.SummaryTask4.entity;

/**
 * Faculty entity. Every faculty is characterized by the name, amount of budget
 * and total seats. The amount of budget seats must always be less then amount
 * of total seats for some faculty.
 *
 * @author Mark Norkin
 *
 */
public class Faculty extends Entity {

	private static final long serialVersionUID = 1590962657803610445L;
	private String nameRu;
	private String nameEng;
	private byte budgetSeats;
	private byte totalSeats;

	public Faculty(String nameRu, String nameEng, byte budgetSeats, byte totalSeats) {
		super();
		this.nameRu = nameRu;
		this.nameEng = nameEng;
		this.budgetSeats = budgetSeats;
		this.totalSeats = totalSeats;
	}

	public Faculty() {
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public byte getBudgetSeats() {
		return budgetSeats;
	}

	public void setBudgetSeats(byte budgetSeats) {
		this.budgetSeats = budgetSeats;
	}

	public byte getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(byte totalSeats) {
		this.totalSeats = totalSeats;
	}

	@Override
	public String toString() {
		return "Faculty [nameRu=" + nameRu + ", nameEng=" + nameEng
				+ ", budgetSeats=" + budgetSeats + ", totalSeats=" + totalSeats
				+ "]";
	}

}
