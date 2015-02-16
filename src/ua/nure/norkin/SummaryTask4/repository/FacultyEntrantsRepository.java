package ua.nure.norkin.SummaryTask4.repository;

import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;

public interface FacultyEntrantsRepository extends Repository<FacultyEntrants> {

	/**
	 * Finds FacultyEntrants entity by searching a record with faculty id and
	 * entrant id incapsulated in specified entity. This operation is needed to
	 * check if some entrant is already applied for some faculty.
	 *
	 * @param facultyEntrants
	 *            entity to be searched for
	 * @return <code>null</code> if there is no such entity or returns found
	 *         entity otherwise.
	 */
	public FacultyEntrants find(FacultyEntrants facultyEntrants);

	/*
	 * Default behavior of update operation is to be not used in implementation.
	 * This is the case because Faculty Entrants is a compound entity, which
	 * have foreign keys of Faculty and Entrant entities.
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#update(java.lang.Object
	 * )
	 */
	default public void update(FacultyEntrants entity) {
		throw new UnsupportedOperationException(
				"This operation is not supported on compound entity 'faculty_entrants'");
	}
}
