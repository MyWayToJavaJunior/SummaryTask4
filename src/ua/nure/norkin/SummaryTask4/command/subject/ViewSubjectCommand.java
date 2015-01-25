package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;

public class ViewSubjectCommand extends Command {

	private static final long serialVersionUID = -1129276218825868557L;

	private static final Logger LOG = Logger
			.getLogger(ViewSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;
		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			result = null;
		} else if ("admin".equals(role)) {

			String subjectName = request.getParameter(Fields.SUBJECT_NAME);

			LOG.trace("Subject name to look for is equal to " + subjectName);

			SubjectRepository subjectRepository = new SubjectRepository();
			Subject subject = subjectRepository.find(subjectName);

			LOG.trace("Subject record found: " + subject);

			request.setAttribute(Fields.SUBJECT_NAME, subject.getName());
			LOG.trace("Set the request attribute: 'name' = "
					+ subject.getName());
			result = Path.SUBJECT_VIEW_ADMIN;
		}
		LOG.debug("Finished executing Command");
		return result;
	}

}
