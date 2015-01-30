package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user wants to see some specific faculty.
 *
 * @author Mark Norkin
 *
 */
public class ViewFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(ViewFacultyCommand.class);

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

		String result = null;
		if (actionType == ActionType.FORWARD) {
			result = doGet(request, response);
		} else {
			return null;
		}

		LOG.debug("Finished executing Command");

		return result;
	}

	/**
	 * Shows page with faculty attributes. Type of action on the page depends on
	 * user role.
	 *
	 * @return path to the view of some faculty.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String facultyName = request.getParameter(Fields.FACULTY_NAME);

		LOG.trace("Faculty name to look for is equal to: '" + facultyName + "'");

		String result = null;

		FacultyRepository facultyRepository = new FacultyRepository();

		Faculty facultyRecord = facultyRepository.find(facultyName);

		LOG.trace("Faculty record found: " + facultyRecord);

		request.setAttribute(Fields.FACULTY_NAME, facultyRecord.getName());
		LOG.trace("Set the request attribute: 'name' = "
				+ facultyRecord.getName());
		request.setAttribute(Fields.FACULTY_TOTAL_SEATS,
				facultyRecord.getTotalSeats());
		LOG.trace("Set the request attribute: 'total_seats' = "
				+ facultyRecord.getTotalSeats());
		request.setAttribute(Fields.FACULTY_BUDGET_SEATS,
				facultyRecord.getBudgetSeats());
		LOG.trace("Set the request attribute: 'budget_seats' = "
				+ facultyRecord.getBudgetSeats());

		SubjectRepository subjectRepository = new SubjectRepository();
		List<Subject> facultySubjects = subjectRepository
				.findAllFacultySubjects(facultyRecord);

		request.setAttribute("facultySubjects", facultySubjects);
		LOG.trace("Set the request attribute: 'facultySubjects' = "
				+ facultySubjects);

		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));

		if ("client".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_CLIENT;
		} else if ("admin".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ADMIN;
		}

		return result;
	}
}
