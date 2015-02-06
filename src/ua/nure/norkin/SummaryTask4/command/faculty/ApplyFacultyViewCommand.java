package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;
import ua.nure.norkin.SummaryTask4.entity.Mark;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyEntrantsRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.MarkRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when client wants to apply for some faculty.
 *
 * @author Mark Norkin
 *
 */
public class ApplyFacultyViewCommand extends Command {

	private static final long serialVersionUID = 8295388021320200832L;
	private static final Logger LOG = Logger
			.getLogger(ApplyFacultyViewCommand.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.command.Command#execute(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * ua.nure.norkin.SummaryTask4.utils.ActionType)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {

		LOG.debug("Start executing Command");

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// admins are not permitted to access this page
		if (role == null || "admin".equals(role)) {
			return null;
		}

		String result = null;

		if ("client".equals(role)) {
			if (ActionType.GET == actionType) {
				result = doGet(request, response);
			} else if (ActionType.POST == actionType) {
				result = doPost(request, response);
			}
		}

		LOG.debug("Finished executing Command");
		return result;

	}

	/**
	 * Forwards user to apply page of interested faculty.
	 *
	 * @return path to apply for faculty page
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String result = null;
		String facultyNameEng = request.getParameter(Fields.FACULTY_NAME_ENG);
		FacultyRepository facultyRepository = new FacultyRepository();
		Faculty faculty = facultyRepository.find(facultyNameEng);

		request.setAttribute(Fields.ENTITY_ID, faculty.getId());
		LOG.trace("Set the request faculty attribute: 'id' = "
				+ faculty.getId());

		request.setAttribute(Fields.FACULTY_NAME_RU, faculty.getNameRu());
		LOG.trace("Set the request attribute: 'name' = " + faculty.getNameRu());
		request.setAttribute(Fields.FACULTY_NAME_ENG, faculty.getNameEng());
		LOG.trace("Set the request attribute: 'name_eng' = "
				+ faculty.getNameEng());
		request.setAttribute(Fields.FACULTY_TOTAL_SEATS,
				faculty.getTotalSeats());
		LOG.trace("Set the request attribute: 'total_seats' = "
				+ faculty.getTotalSeats());
		request.setAttribute(Fields.FACULTY_BUDGET_SEATS,
				faculty.getBudgetSeats());
		LOG.trace("Set the request attribute: 'budget_seats' = "
				+ faculty.getBudgetSeats());
		SubjectRepository subjectRepository = new SubjectRepository();

		List<Subject> facultySubjects = subjectRepository
				.findAllFacultySubjects(faculty);
		request.setAttribute("facultySubjects", facultySubjects);
		LOG.trace("Set attribute 'facultySubjects': " + facultySubjects);

		List<Subject> allSubjects = subjectRepository.findAll();
		request.setAttribute("allSubjects", allSubjects);
		LOG.trace("Set attribute 'allSubjects': " + allSubjects);

		result = Path.FORWARD_FACULTY_APPLY_CLIENT;
		return result;
	}

	/**
	 * @return redirects user to view of applied faculty if applying is
	 *         successful, otherwise redisplays this page.
	 * @throws UnsupportedEncodingException
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		LOG.trace("Start processing applying for faculty form");

		HttpSession session = request.getSession(false);
		String email = String.valueOf(session.getAttribute("user"));

		UserRepository userRepository = new UserRepository();
		User user = userRepository.find(email);
		LOG.trace("Found user in database that wants to apply: " + user);

		EntrantRepository entrantRepository = new EntrantRepository();
		Entrant entrant = entrantRepository.find(user);

		LOG.trace("Found entrant record in database for this user: " + entrant);

		FacultyEntrantsRepository facultyEntrantsRepository = new FacultyEntrantsRepository();

		Integer facultyId = Integer.valueOf(request
				.getParameter(Fields.ENTITY_ID));

		FacultyEntrants newFacultyEntrant = new FacultyEntrants(facultyId,
				entrant.getId());
		FacultyEntrants existingRecord = facultyEntrantsRepository
				.find(newFacultyEntrant);

		if (existingRecord != null) {
			// user is already applied
			LOG.trace("User: " + user + " with Entrant record: " + entrant
					+ " already applied for faculti with id: " + facultyId);
			return Path.REDIRECT_TO_VIEW_ALL_FACULTIES;
		} else {

			LOG.trace("Start extracting data from request");

			Map<String, String[]> parameterMap = request.getParameterMap();

			for (String parameterName : parameterMap.keySet()) {

				if (parameterName.endsWith("preliminary")
						|| parameterName.endsWith("diploma")) {
					String[] value = parameterMap.get(parameterName);
					Byte markValue = Byte.valueOf(value[0]);
					String[] subjectIdAndExamType = parameterName.split("_");

					Integer subjectId = Integer
							.valueOf(subjectIdAndExamType[0]);
					String examType = subjectIdAndExamType[1];

					Mark mark = new Mark(subjectId, entrant.getId(), markValue,
							examType);
					LOG.trace("Create Mark transfer object: " + mark);

					MarkRepository markRepository = new MarkRepository();

					markRepository.create(mark);

					LOG.trace("Mark record was created in database: " + mark);
				}
			}

			LOG.trace("End extracting data from request");

			LOG.trace("Create FacultyEntrants transfer object: "
					+ newFacultyEntrant);

			facultyEntrantsRepository.create(newFacultyEntrant);

			LOG.trace("FacultyEntrants record was created in database: "
					+ newFacultyEntrant);

			LOG.trace("Finished processing applying for faculty form");

			FacultyRepository facultyRepository = new FacultyRepository();
			Faculty faculty = facultyRepository.find(facultyId);
			return Path.REDIRECT_TO_FACULTY + faculty.getNameEng();
		}
	}
}
