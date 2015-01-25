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

public class AddSubjectCommand extends Command {

	private static final long serialVersionUID = -1505430469675582018L;
	private static final Logger LOG = Logger.getLogger(AddSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command execution starts");

		// result.setFirst(ActionType.REDIRECT);
		String result = null;

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not allowed to access this page
		if ("client".equals(role)) {
			result = null;
		} else if ("admin".equals(role)) {
			LOG.debug("Finished executing Command");

			String name = request.getParameter("name");
			LOG.trace("Fetch request parapeter: 'name' = " + name);

			SubjectRepository subjectRepository = new SubjectRepository();

			Subject subject = new Subject();
			subject.setName(name);

			LOG.trace("Create subject transfer object: " + subject);

			subjectRepository.create(subject);
			LOG.trace("Create subject record in database: " + subject);

			result = Path.SUBJECT_VIEW_ADMIN;
		}
		return result;
	}

}
