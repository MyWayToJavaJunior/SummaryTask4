package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;

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

/**
 * Edit subject Command.
 *
 * @author Mark Norkin
 * @see Subject
 *
 */
public class EditSubjectCommand extends Command {

	private static final long serialVersionUID = 2946525838609196070L;
	private static final Logger LOG = Logger
			.getLogger(EditSubjectCommand.class);

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
		LOG.debug("Command execution starts");

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not permitted to access this page
		if (role == null || "client".equals(role)) {
			return null;
		}

		String result = null;

		if (actionType == ActionType.GET) {
			result = doGet(request, response);
		} else if (actionType == ActionType.POST) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	/**
	 * Forwards admin to edit page, so then he can update the subject data.
	 *
	 * @return path to the edit subject page.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String subjectName = request.getParameter(Fields.FACULTY_NAME_ENG);
		Subject subject = new SubjectRepository().find(subjectName);

		request.setAttribute(Fields.SUBJECT_NAME_RU, subject.getNameRu());
		LOG.trace("Set attribute 'name_ru': " + subject.getNameRu());

		request.setAttribute(Fields.SUBJECT_NAME_ENG, subject.getNameEng());
		LOG.trace("Set attribute 'name_eng': " + subject.getNameEng());

		return Path.FORWARD_SUBJECT_EDIT_ADMIN;
	}

	// TODO validation
	/**
	 * Updates subject info.
	 *
	 * @return path to the view of edited subject if all fields were properly
	 *         filled, otherwise redisplays edit page.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		// get parameters from page

		String oldSubjectName = request.getParameter("oldName");
		LOG.trace("Fetch request parapeter: 'oldName' = " + oldSubjectName);

		SubjectRepository subjectRepository = new SubjectRepository();
		// should not be null !
		Subject subject = subjectRepository.find(oldSubjectName);
		LOG.trace("Subject record found with this data:" + subject);

		String newSubjectNameRu = request.getParameter(Fields.SUBJECT_NAME_RU);
		LOG.trace("Fetch request parapeter: 'name_ru' = " + newSubjectNameRu);
		String newSubjectNameEng = request
				.getParameter(Fields.SUBJECT_NAME_ENG);
		LOG.trace("Fetch request parapeter: 'name_eng' = " + newSubjectNameEng);

		subject.setNameRu(newSubjectNameRu);
		subject.setNameEng(newSubjectNameEng);

		LOG.trace("After calling setters with request parameters on subject entity: "
				+ subject);

		subjectRepository.update(subject);

		LOG.trace("Subject record updated");

		return Path.REDIRECT_TO_SUBJECT + newSubjectNameEng;
	}

}
