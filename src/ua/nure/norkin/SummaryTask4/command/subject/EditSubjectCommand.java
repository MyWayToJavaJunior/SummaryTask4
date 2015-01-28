package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

public class EditSubjectCommand extends Command {

	private static final long serialVersionUID = 2946525838609196070L;
	private static final Logger LOG = Logger
			.getLogger(EditSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Command execution starts");

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			return null;
		}

		String result = null;

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
		String subjectName = request.getParameter("name");
		Subject subject = new SubjectRepository().find(subjectName);

		request.setAttribute(Fields.SUBJECT_NAME, subject.getName());
		LOG.trace("Set attribute 'name': " + subject.getName());

		return Path.FORWARD_SUBJECT_EDIT_ADMIN;
	}

	// TODO validation
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// get parameters from page

		String oldSubjectName = request.getParameter("oldName");
		LOG.trace("Fetch request parapeter: 'oldName' = " + oldSubjectName);

		SubjectRepository subjectRepository = new SubjectRepository();
		// should not be null !
		Subject subject = subjectRepository.find(oldSubjectName);
		LOG.trace("Subject record found with this data:" + subject);

		String newSubjectName = request.getParameter("name");
		LOG.trace("Fetch request parapeter: 'name' = " + newSubjectName);

		subject.setName(newSubjectName);

		LOG.trace("After calling setters with request parameters on subject entity: "
				+ subject);

		subjectRepository.update(subject);

		LOG.trace("Subject record updated");

		return Path.REDIRECT_TO_SUBJECT
				+ URLEncoder.encode(newSubjectName, "UTF-8");
	}

}
