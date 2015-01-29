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
	public static final String USER_FOREIGN_KEY_ID = "User_idUser";

	public static final String ENTRANT_CITY = "city";
	public static final String ENTRANT_DISTRICT = "district";
	public static final String ENTRANT_SCHOOL = "school";
	public static final String ENTRANT_IS_BLOCKED = "isBlocked";
	public static final String ENTRANT_FOREIGN_KEY_ID = "Entrant_idEntrant";

	public static final String FACULTY_NAME = "name";
	public static final String FACULTY_BUDGET_SEATS = "budget_seats";
	public static final String FACULTY_TOTAL_SEATS = "total_seats";
	public static final String FACULTY_FOREIGN_KEY_ID = "Faculty_idFaculty";

	public static final String SUBJECT_NAME = "name";
	public static final String SUBJECT_FOREIGN_KEY_ID = "Subject_idSubject";

	public static final String MARK_VALUE = "value";
	public static final String MARK_EXAM_TYPE = "exam_type";
	public static final String MARK_FOREIGN_KEY_ID = "Mark_idMark";

}