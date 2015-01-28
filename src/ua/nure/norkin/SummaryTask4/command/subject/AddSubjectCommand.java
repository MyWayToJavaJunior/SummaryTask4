package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

public class AddSubjectCommand extends Command {

	private static final long serialVersionUID = -1505430469675582018L;
	private static final Logger LOG = Logger.getLogger(AddSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Command execution starts");

		String result = null;

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not allowed to access this page
		if ("client".equals(role)) {
			return null;
		}

		if (actionType == ActionType.FORWARD) {
			result = doGet(request, response);
		} else if (actionType == ActionType.REDIRECT) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		return Path.FORWARD_SUBJECT_ADD_ADMIN;
	}

	// TODO validation
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name");
		LOG.trace("Fetch request parapeter: 'name' = " + name);

		SubjectRepository subjectRepository = new SubjectRepository();

		Subject subject = new Subject();
		subject.setName(name);

		LOG.trace("Create subject transfer object: " + subject);

		subjectRepository.create(subject);
		LOG.trace("Create subject record in database: " + subject);

		return Path.REDIRECT_TO_SUBJECT + name;
	}

}
