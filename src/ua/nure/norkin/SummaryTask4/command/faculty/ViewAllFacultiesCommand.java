package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

public class ViewAllFacultiesCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(ViewAllFacultiesCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		if (actionType == ActionType.FORWARD) {
			result = doGet(request, response);
		} else {
			result = null;
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String result = null;

		FacultyRepository facultyRepository = new FacultyRepository();

		List<Faculty> faculties = facultyRepository.findAll();

		LOG.trace("Faculties records found: " + faculties);

		request.setAttribute("faculties", faculties);
		LOG.trace("Set the request attribute: 'faculties' = " + faculties);

		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));
		if ("client".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
		} else if ("admin".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ALL_ADMIN;
		}

		return result;
	}
}
