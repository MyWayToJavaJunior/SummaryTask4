package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultySubjects;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.MySQLRepositoryFactory;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

/**
 * Invoked when user wants to add faculty. Command allowed only for admins.
 *
 * @author Mark Norkin
 *
 */
public class AddFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AddFacultyCommand.class);

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

		// clients are not permitted to access this page
		if (role == null || "client".equals(role)) {
			return null;
		}

		String result = null;

		if ("admin".equals(role)) {
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
	 * Forwards to add page.
	 *
	 * @return path to the add faculty page.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Request for only showing (not already adding) faculty/add.jsp");

		SubjectRepository subjectRepository = MySQLRepositoryFactory
				.getSubjectRepository();
		List<Subject> allSubjects = subjectRepository.findAll();
		LOG.trace("All subjects found: " + allSubjects);
		request.setAttribute("allSubjects", allSubjects);
		LOG.trace("Set request attribute 'allSubjects' = " + allSubjects);

		return Path.FORWARD_FACULTY_ADD_ADMIN;
	}

	/**
	 * Redirects user after submitting add faculty form.
	 *
	 * @return path to the view of added faculty if fields properly filled,
	 *         otherwise redisplays add Faculty page.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String result = null;

		String facultyNameRu = request.getParameter(Fields.FACULTY_NAME_RU);
		String facultyNameEng = request.getParameter(Fields.FACULTY_NAME_ENG);
		String facultyTotalSeats = request
				.getParameter(Fields.FACULTY_TOTAL_SEATS);
		String facultyBudgetSeats = request
				.getParameter(Fields.FACULTY_BUDGET_SEATS);

		boolean valid = true;

		if (!FieldValidation.isFilled(facultyNameRu, facultyNameEng,
				facultyBudgetSeats, facultyTotalSeats)) {
			request.setAttribute("errorMessage",
					"Please fill all fields properly!");
			LOG.error("errorMessage: Not all fields are properly filled");
			valid = false;
		}
		if (!FieldValidation.isByte(facultyTotalSeats, facultyBudgetSeats)) {
			request.setAttribute("errorMessage", "Please enter a valid number!");
			LOG.error("errorMessage: not a numbers");
			valid = false;

		}

		Byte totalSeats = Byte.valueOf(facultyTotalSeats);
		Byte budgetSeats = Byte.valueOf(facultyBudgetSeats);

		if (!FieldValidation.checkBudgetLowerTotal(budgetSeats, totalSeats)) {
			request.setAttribute("errorMessage",
					"Budget seats should be lower then Total seats!");
			LOG.error("errorMessage: not valid number's for faculty seats");
			valid = false;
		}

		if (valid == false) {
			result = Path.REDIRECT_FACULTY_ADD_ADMIN;
		}
		if (valid) {

			// TODO check name for uniqueness

			Faculty faculty = new Faculty(facultyNameRu, facultyNameEng,
					budgetSeats, totalSeats);

			LOG.trace("Create faculty transfer object: " + faculty);

			FacultyRepository facultyRepository = MySQLRepositoryFactory
					.getFacultyRepository();

			facultyRepository.create(faculty);

			LOG.trace("Create faculty record in database: " + faculty);

			// only after creating a faculty record we can proceed with
			// adding faculty subjects
			String[] choosedSubjectsIds = request
					.getParameterValues("subjects");

			if (choosedSubjectsIds != null) {
				FacultySubjectsRepository facultySubjectsRepository = MySQLRepositoryFactory
						.getFacultySubjectsRepository();

				for (String subjectId : choosedSubjectsIds) {
					FacultySubjects facultySubject = new FacultySubjects(
							Integer.valueOf(subjectId), faculty.getId());
					facultySubjectsRepository.create(facultySubject);
					LOG.trace("FacultySubjects record created in databaset: "
							+ facultySubject);
				}
			}
			result = Path.REDIRECT_TO_FACULTY + facultyNameEng;
		}
		return result;
	}

}
