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
import ua.nure.norkin.SummaryTask4.utils.ActionType;

public class ViewSubjectCommand extends Command {

	private static final long serialVersionUID = -1129276218825868557L;

	private static final Logger LOG = Logger
			.getLogger(ViewSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));

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
		String subjectName = request.getParameter(Fields.SUBJECT_NAME);

		LOG.trace("Subject name to look for is equal to: '" + subjectName+"'");

		SubjectRepository subjectRepository = new SubjectRepository();
		Subject subject = subjectRepository.find(subjectName);

		LOG.trace("Subject record found: " + subject);

		request.setAttribute(Fields.SUBJECT_NAME, subject.getName());
		LOG.trace("Set the request attribute: 'name' = " + subject.getName());
		return Path.FORWARD_SUBJECT_VIEW_ADMIN;
	}

}
