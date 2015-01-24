package ua.nure.norkin.SummaryTask4.entity;

/**
 * User role type.
 *
 * @author Mark Norkin
 *
 */

public enum Role {
	ADMIN, CLIENT;

	public String getName() {
		return name().toLowerCase();
	}

}
