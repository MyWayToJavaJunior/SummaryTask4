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

public class ViewAllSubjectsCommand extends Command {

	private static final long serialVersionUID = 19699623476838931L;
	private static final Logger LOG = Logger
			.getLogger(ViewAllSubjectsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			result = null;
		} else if ("admin".equals(role)) {
			// result.setFirst(ActionType.FORWARD);

			SubjectRepository subjectRepository = new SubjectRepository();

			List<Subject> allSubjects = subjectRepository.findAll();

			LOG.trace("Subjects records found: " + allSubjects);

			request.setAttribute("allSubjects", allSubjects);
			LOG.trace("Set the request attribute: 'allSubjects' = "
					+ allSubjects);

			result = Path.SUBJECT_VIEW_ALL_ADMIN;
		}

		LOG.debug("Finished executing Command");
		return result;
	}

}
