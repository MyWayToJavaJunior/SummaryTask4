package ua.nure.norkin.SummaryTask4.entity;

/**
 * Faculty subjects entity. Main purpose of this class is to tell which subjects
 * are needed to entrant, so then he can apply for some faculty. This subjects
 * are also called preliminary.
 *
 * @author Mark Norkin
 *
 */
public class FacultySubjects extends Entity {

	private static final long serialVersionUID = 1165092452837127706L;
	private int subjectId;
	private int facultyId;

	public FacultySubjects(int subjectId, int facultyId) {
		super();
		this.subjectId = subjectId;
		this.facultyId = facultyId;
	}

	public FacultySubjects(Subject subject, Faculty faculty) {
		this(subject.getId(), faculty.getId());
	}

	public FacultySubjects() {
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	@Override
	public String toString() {
		return "FacultySubjects [subjectId=" + subjectId + ", facultyId="
				+ facultyId + "]";
	}

}
