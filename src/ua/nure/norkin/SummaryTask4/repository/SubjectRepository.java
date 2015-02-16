package ua.nure.norkin.SummaryTask4.repository;

import java.util.List;

import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.Subject;

public interface SubjectRepository extends Repository<Subject> {

	/**
	 * Finds subject record by the subject name. This is achieved through having
	 * a unique subject name field.
	 *
	 * @param subjectName
	 *            - to be searched through
	 * @return subject instance which have specified name
	 */
	public Subject find(String subjectName);

	/**
	 * Finds all preliminary subjects of this faculty.
	 *
	 * @param faculty
	 *            by which search of subjects will be done
	 * @return<code>List</code> of faculty preliminary subjects
	 */
	public List<Subject> findAllFacultySubjects(Faculty faculty);

	/**
	 * Finds all subjects that are not required as preliminary for this faculty.
	 *
	 * @param faculty
	 * @return <codeList></code> of subjects that complied with this condition.
	 */
	public List<Subject> findAllNotFacultySubjects(Faculty faculty);
}
