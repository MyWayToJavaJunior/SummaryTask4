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
import ua.nure.norkin.SummaryTask4.repository.factory.FactoryType;
import ua.nure.norkin.SummaryTask4.repository.factory.RepositoryFactory;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.validation.SubjectInputValidator;

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

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Command execution starts");

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
		RepositoryFactory repositoryFactory = RepositoryFactory
				.getFactoryByName(FactoryType.MYSQL_REPOSITORY_FACTORY);

		Subject subject = repositoryFactory.getSubjectRepository().find(
				subjectName);

		request.setAttribute(Fields.SUBJECT_NAME_RU, subject.getNameRu());
		LOG.trace("Set attribute 'name_ru': " + subject.getNameRu());

		request.setAttribute(Fields.SUBJECT_NAME_ENG, subject.getNameEng());
		LOG.trace("Set attribute 'name_eng': " + subject.getNameEng());

		return Path.FORWARD_SUBJECT_EDIT_ADMIN;
	}

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

		RepositoryFactory repositoryFactory = RepositoryFactory
				.getFactoryByName(FactoryType.MYSQL_REPOSITORY_FACTORY);
		SubjectRepository subjectRepository = repositoryFactory
				.getSubjectRepository();
		// should not be null !
		Subject subject = subjectRepository.find(oldSubjectName);
		LOG.trace("Subject record found with this data:" + subject);

		String newSubjectNameRu = request.getParameter(Fields.SUBJECT_NAME_RU);
		LOG.trace("Fetch request parapeter: 'name_ru' = " + newSubjectNameRu);
		String newSubjectNameEng = request
				.getParameter(Fields.SUBJECT_NAME_ENG);
		LOG.trace("Fetch request parapeter: 'name_eng' = " + newSubjectNameEng);

		boolean valid = SubjectInputValidator.validateParameters(
				newSubjectNameRu, newSubjectNameEng);

		String result = null;
		if (valid == false) {
			request.setAttribute("errorMessage",
					"Please fill all fields properly!");
			LOG.error("errorMessage: Not all fields are properly filled");
			result = Path.REDIRECT_SUBJECT_EDIT_ADMIN + oldSubjectName;
		} else if (valid) {
			subject.setNameRu(newSubjectNameRu);
			subject.setNameEng(newSubjectNameEng);

			LOG.trace("After calling setters with request parameters on subject entity: "
					+ subject);

			subjectRepository.update(subject);

			LOG.trace("Subject record updated");

			result = Path.REDIRECT_TO_SUBJECT + newSubjectNameEng;
		}
		return result;
	}

}
