package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user wants to delete faculty. Command allowed only for admins.
 *
 * @author Mark Norkin
 *
 */
public class DeleteFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(DeleteFacultyCommand.class);

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
		if ("client".equals(role)) {
			return null;
		}

		String result = null;

		if (ActionType.REDIRECT == actionType) {
			result = doPost(request, response);
		} else {
			result = null;
		}

		LOG.debug("Finished executing Command");

		return result;
	}

	/**
	 * Redirects user to view of all faculties after submiting a delete button.
	 *
	 * @return path to view of all faculties.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String facultyName = request.getParameter(Fields.FACULTY_NAME);

		FacultyRepository facultyRepository = new FacultyRepository();

		Faculty facultyToDelete = facultyRepository.find(facultyName);

		LOG.trace("Database faculty record that will be deleted was found: "
				+ facultyToDelete);

		facultyRepository.delete(facultyToDelete);

		LOG.trace("Delete faculty record in database: " + facultyToDelete);
		return Path.REDIRECT_TO_VIEW_ALL_FACULTIES;
	}

}
