package ua.nure.norkin.SummaryTask4.repository;

import java.util.List;

import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.User;

public interface EntrantRepository extends Repository<Entrant> {

	/**
	 * Finds Entrant record by User instance. This can be done in such way
	 * because User and Entrant have relationship one to one.
	 *
	 * @param user
	 *            by which search will be done
	 * @return Entrant record of this User
	 */
	public Entrant find(User user);

	/**
	 * Finds all entrants that applied for this faculty.
	 *
	 * @param faculty
	 *            - entrants of which should be found
	 * @return entrants that applied for this faculty
	 */
	public List<Entrant> findAllFacultyEntrants(Faculty faculty);
}
