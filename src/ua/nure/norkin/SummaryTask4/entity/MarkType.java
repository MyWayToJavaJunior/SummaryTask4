package ua.nure.norkin.SummaryTask4.entity;

public enum MarkType {
	DIPLOMA, PRELIMINARY;

	public String getName() {
		return this.name().toLowerCase();
	}
}
