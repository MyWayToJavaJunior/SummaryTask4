package ua.nure.norkin.SummaryTask4.repository;

import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlEntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlFacultyEntrantsRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlFacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlFacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlMarkRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlReportSheetRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlSubjectRepository;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlUserRepository;

public class MySQLRepositoryFactory {

	private static final MySqlUserRepository USER_REPOSITORY = new MySqlUserRepository();
	private static final MySqlEntrantRepository ENTRANT_REPOSITORY = new MySqlEntrantRepository();
	private static final MySqlFacultyRepository FACULTY_REPOSITORY = new MySqlFacultyRepository();
	private static final MySqlSubjectRepository SUBJECT_REPOSITORY = new MySqlSubjectRepository();
	private static final MySqlFacultySubjectsRepository FACULTY_SUBJECTS_REPOSITORY = new MySqlFacultySubjectsRepository();
	private static final MySqlFacultyEntrantsRepository FACULTY_ENTRANTS_REPOSITORY = new MySqlFacultyEntrantsRepository();
	private static final MySqlMarkRepository MARK_REPOSITORY = new MySqlMarkRepository();
	private static final MySqlReportSheetRepository REPORT_SHEET_REPOSITORY = new MySqlReportSheetRepository();

	public static MySqlEntrantRepository getEntrantRepository() {
		return ENTRANT_REPOSITORY;
	}

	public static MySqlUserRepository getUserRepository() {
		return USER_REPOSITORY;
	}

	public static MySqlFacultyRepository getFacultyRepository() {
		return FACULTY_REPOSITORY;
	}

	public static MySqlSubjectRepository getSubjectRepository() {
		return SUBJECT_REPOSITORY;
	}

	public static MySqlFacultySubjectsRepository getFacultySubjectsRepository() {
		return FACULTY_SUBJECTS_REPOSITORY;
	}

	public static MySqlFacultyEntrantsRepository getFacultyEntrantsRepository() {
		return FACULTY_ENTRANTS_REPOSITORY;
	}

	public static MySqlMarkRepository getMarkRepository() {
		return MARK_REPOSITORY;
	}

	public static MySqlReportSheetRepository getReportSheetRepository() {
		return REPORT_SHEET_REPOSITORY;
	}

}
