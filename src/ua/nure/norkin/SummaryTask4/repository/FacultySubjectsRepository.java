package ua.nure.norkin.SummaryTask4.repository;

import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultySubjects;

public interface FacultySubjectsRepository extends Repository<FacultySubjects> {

	/**
	 * Deletes all preliminary subjects of specified faculty.
	 *
	 * @param entity
	 *            - faculty by which delete operation should be performed
	 */
	public void deleteAllSubjects(Faculty entity);

	/*
	 * Default behavior of update operation is to be not used in implementation.
	 * This is the case because Faculty Subjects is a compound entity, which
	 * have foreign keys of Faculty and Subject entities.
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#update(java.lang.Object
	 * )
	 */
	default public void update(FacultySubjects entity) {
		throw new UnsupportedOperationException(
				"This operation is not supported on compound entity 'faculty_subjects'");
	}
}
