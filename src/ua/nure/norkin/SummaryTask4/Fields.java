package ua.nure.norkin.SummaryTask4;

/**
 * Holder for fields names in database tables.
 *
 * @author Mark Norkin.
 *
 */
public final class Fields {

	public static final String ENTITY_ID = "id";
	public static final String GENERATED_KEY = "GENERATED_KEY";

	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE = "role";
	public static final String USER_LANG = "lang";
	public static final String USER_ACTIVE_STATUS = "isActive";
	public static final String USER_FOREIGN_KEY_ID = "User_idUser";

	public static final String ENTRANT_CITY = "city";
	public static final String ENTRANT_DISTRICT = "district";
	public static final String ENTRANT_SCHOOL = "school";
	public static final String ENTRANT_IS_BLOCKED = "isBlocked";
	public static final String ENTRANT_FOREIGN_KEY_ID = "Entrant_idEntrant";

	public static final String FACULTY_NAME_RU = "name_ru";
	public static final String FACULTY_NAME_ENG = "name_eng";
	public static final String FACULTY_BUDGET_SEATS = "budget_seats";
	public static final String FACULTY_TOTAL_SEATS = "total_seats";
	public static final String FACULTY_FOREIGN_KEY_ID = "Faculty_idFaculty";

	public static final String SUBJECT_NAME_RU = "name_ru";
	public static final String SUBJECT_NAME_ENG = "name_eng";
	public static final String SUBJECT_FOREIGN_KEY_ID = "Subject_idSubject";

	public static final String MARK_VALUE = "value";
	public static final String MARK_EXAM_TYPE = "exam_type";
	public static final String MARK_FOREIGN_KEY_ID = "Mark_idMark";

	public static final String REPORT_SHEET_FACULTY_ID = "facultyId";
	public static final String REPORT_SHEET_USER_FIRST_NAME = "first_name";
	public static final String REPORT_SHEET_USER_LAST_NAME = "last_name";
	public static final String REPORT_SHEET_USER_EMAIL = "email";
	public static final String REPORT_SHEET_ENTRANT_IS_BLOCKED = "isBlocked";
	public static final String REPORT_SHEET_ENTRANT_PRELIMINARY_SUM = "preliminary_sum";
	public static final String REPORT_SHEET_ENTRANT_DIPLOMA_SUM = "diploma_sum";
	public static final String REPORT_SHEET_ENTRANT_TOTAL_SUM = "total_sum";
}