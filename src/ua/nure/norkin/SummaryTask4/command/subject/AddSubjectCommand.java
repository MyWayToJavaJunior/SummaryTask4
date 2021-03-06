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
import ua.nure.norkin.SummaryTask4.repository.factory.FactoryType;
import ua.nure.norkin.SummaryTask4.repository.factory.RepositoryFactory;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.validation.SubjectInputValidator;

/**
 * Add subject Command
 *
 * @author Mark Norkin
 * @see Subject
 *
 */
public class AddSubjectCommand extends Command {

	private static final long serialVersionUID = -1505430469675582018L;
	private static final Logger LOG = Logger.getLogger(AddSubjectCommand.class);

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
	 * Forwards admin to add page.
	 *
	 * @return path to add page
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		return Path.FORWARD_SUBJECT_ADD_ADMIN;
	}

	/**
	 * Adds subject if fields are properly filled, otherwise redisplays add
	 * page.
	 *
	 * @return view of added subject
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String nameRu = request.getParameter("name_ru");
		LOG.trace("Fetch request parapeter: 'name_ru' = " + nameRu);

		String nameEng = request.getParameter("name_eng");
		LOG.trace("Fetch request parapeter: 'name_eng' = " + nameEng);

		boolean valid = SubjectInputValidator.validateParameters(nameRu,
				nameEng);

		String result = null;
		if (valid == false) {
			request.setAttribute("errorMessage",
					"Please fill all fields properly!");
			LOG.error("errorMessage: Not all fields are properly filled");
			result = Path.REDIRECT_SUBJECT_ADD_ADMIN;
		} else if (valid) {
			RepositoryFactory repositoryFactory = RepositoryFactory
					.getFactoryByName(FactoryType.MYSQL_REPOSITORY_FACTORY);
			SubjectRepository subjectRepository = repositoryFactory
					.getSubjectRepository();

			Subject subject = new Subject();
			subject.setNameRu(nameRu);
			subject.setNameEng(nameEng);

			LOG.trace("Create subject transfer object: " + subject);

			subjectRepository.create(subject);
			LOG.trace("Create subject record in database: " + subject);
			result = Path.REDIRECT_TO_SUBJECT + nameEng;
		}
		return result;
	}

}
