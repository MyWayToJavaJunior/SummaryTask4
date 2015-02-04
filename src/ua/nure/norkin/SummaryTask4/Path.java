package ua.nure.norkin.SummaryTask4;

/**
 * Path holder to jsp pages.
 *
 * @author Mark Norkin
 *
 */
public final class Path {
	// pages
	public static final String WELCOME_PAGE = "welcome.jsp";

	public static final String ERROR_PAGE = "/WEB-INF/view/errorPage.jsp";

	public static final String FORWARD_CLIENT_REGISTRATION_PAGE = "/WEB-INF/view/client/addClient.jsp";
	public static final String REDIRECT_CLIENT_REGISTRATION_PAGE = "controller?command=client_registration";

	public static final String FORWARD_ADMIN_REGISTRATION_PAGE = "/WEB-INF/view/admin/addAdmin.jsp";
	public static final String REDIRECT_ADMIN_REGISTRATION_PAGE = "controller?command=admin_registration";

	public static final String REDIRECT_TO_PROFILE = "controller?command=viewProfile";
	public static final String REDIRECT_TO_FACULTY = "controller?command=viewFaculty&name=";
	public static final String REDIRECT_TO_SUBJECT = "controller?command=viewSubject&name=";
	public static final String REDIRECT_TO_VIEW_ALL_FACULTIES = "controller?command=viewAllFaculties";
	public static final String REDIRECT_TO_VIEW_ALL_SUBJECTS = "controller?command=viewAllSubjects";

	public static final String FORWARD_ADMIN_PROFILE = "/WEB-INF/view/admin/profile/view.jsp";
	public static final String FORWARD_ADMIN_PROFILE_EDIT = "/WEB-INF/view/admin/profile/edit.jsp";

	public static final String FORWARD_CLIENT_PROFILE = "/WEB-INF/view/client/profile/view.jsp";
	public static final String FORWARD_CLIENT_PROFILE_EDIT = "/WEB-INF/view/client/profile/edit.jsp";

	public static final String FORWARD_FACULTY_VIEW_ALL_ADMIN = "/WEB-INF/view/admin/faculty/list.jsp";
	public static final String FORWARD_FACULTY_VIEW_ALL_CLIENT = "/WEB-INF/view/client/faculty/list.jsp";

	public static final String FORWARD_FACULTY_VIEW_ADMIN = "/WEB-INF/view/admin/faculty/view.jsp";
	public static final String FORWARD_FACULTY_EDIT_ADMIN = "/WEB-INF/view/admin/faculty/edit.jsp";
	public static final String REDIRECT_FACULTY_EDIT_ADMIN = "controller?command=editFaculty&name=";
	public static final String FORWARD_FACULTY_ADD_ADMIN = "/WEB-INF/view/admin/faculty/add.jsp";
	public static final String REDIRECT_FACULTY_ADD_ADMIN = "controller?command=addFaculty";

	public static final String FORWARD_FACULTY_VIEW_CLIENT = "/WEB-INF/view/client/faculty/view.jsp";
	public static final String FORWARD_FACULTY_APPLY_CLIENT = "/WEB-INF/view/client/faculty/apply.jsp";

	public static final String FORWARD_SUBJECT_VIEW_ALL_ADMIN = "/WEB-INF/view/admin/subject/list.jsp";
	public static final String FORWARD_SUBJECT_VIEW_ADMIN = "/WEB-INF/view/admin/subject/view.jsp";
	public static final String FORWARD_SUBJECT_ADD_ADMIN = "/WEB-INF/view/admin/subject/add.jsp";
	public static final String REDIRECT_SUBJECT_ADD_ADMIN = "/WEB-INF/view/admin/subject/add.jsp";
	public static final String FORWARD_SUBJECT_EDIT_ADMIN = "/WEB-INF/view/admin/subject/edit.jsp";

	public static final String FORWARD_ENTRANT_PROFILE = "/WEB-INF/view/admin/entrant/view.jsp";
	public static final String REDIRECT_ENTRANT_PROFILE = "controller?command=viewEntrant&userId=";

}