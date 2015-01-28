package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

public class ViewAllSubjectsCommand extends Command {

	private static final long serialVersionUID = 19699623476838931L;
	private static final Logger LOG = Logger
			.getLogger(ViewAllSubjectsCommand.class);

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

		if (actionType == ActionType.FORWARD) {
			result = doGet(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		SubjectRepository subjectRepository = new SubjectRepository();

		List<Subject> allSubjects = subjectRepository.findAll();

		LOG.trace("Subjects records found: " + allSubjects);

		request.setAttribute("allSubjects", allSubjects);
		LOG.trace("Set the request attribute: 'allSubjects' = " + allSubjects);

		return Path.FORWARD_SUBJECT_VIEW_ALL_ADMIN;
	}

}
