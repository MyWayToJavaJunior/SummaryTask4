package ua.nure.norkin.SummaryTask4.repository;

public class MySQLRepositoryFactory {

	private static final UserRepository USER_REPOSITORY = new UserRepository();
	private static final EntrantRepository ENTRANT_REPOSITORY = new EntrantRepository();
	private static final FacultyRepository FACULTY_REPOSITORY = new FacultyRepository();
	private static final SubjectRepository SUBJECT_REPOSITORY = new SubjectRepository();
	private static final FacultySubjectsRepository FACULTY_SUBJECTS_REPOSITORY = new FacultySubjectsRepository();
	private static final FacultyEntrantsRepository FACULTY_ENTRANTS_REPOSITORY = new FacultyEntrantsRepository();
	private static final MarkRepository MARK_REPOSITORY = new MarkRepository();
	private static final ReportSheetRepository REPORT_SHEET_REPOSITORY = new ReportSheetRepository();

	public static EntrantRepository getEntrantRepository() {
		return ENTRANT_REPOSITORY;
	}

	public static UserRepository getUserRepository() {
		return USER_REPOSITORY;
	}

	public static FacultyRepository getFacultyRepository() {
		return FACULTY_REPOSITORY;
	}

	public static SubjectRepository getSubjectRepository() {
		return SUBJECT_REPOSITORY;
	}

	public static FacultySubjectsRepository getFacultySubjectsRepository() {
		return FACULTY_SUBJECTS_REPOSITORY;
	}

	public static FacultyEntrantsRepository getFacultyEntrantsRepository() {
		return FACULTY_ENTRANTS_REPOSITORY;
	}

	public static MarkRepository getMarkRepository() {
		return MARK_REPOSITORY;
	}

	public static ReportSheetRepository getReportSheetRepository() {
		return REPORT_SHEET_REPOSITORY;
	}

}
