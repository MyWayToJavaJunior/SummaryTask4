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

/**
 * View Subject Command
 *
 * @author Mark Norkin
 * @see Subject
 *
 */
public class ViewSubjectCommand extends Command {

	private static final long serialVersionUID = -1129276218825868557L;

	private static final Logger LOG = Logger
			.getLogger(ViewSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		if (actionType == ActionType.GET) {
			result = doGet(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	/**
	 * Forwards admin to the view of some specific subject.
	 *
	 * @return path to the subject view.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String subjectNameEng = request.getParameter(Fields.SUBJECT_NAME_ENG);

		LOG.trace("Subject name to look for is equal to: '" + subjectNameEng
				+ "'");

		RepositoryFactory repositoryFactory = RepositoryFactory
				.getFactoryByName(FactoryType.MYSQL_REPOSITORY_FACTORY);
		SubjectRepository subjectRepository = repositoryFactory
				.getSubjectRepository();
		Subject subject = subjectRepository.find(subjectNameEng);

		LOG.trace("Subject record found: " + subject);

		request.setAttribute(Fields.SUBJECT_NAME_RU, subject.getNameRu());
		LOG.trace("Set the request attribute: 'name_ru' = "
				+ subject.getNameRu());
		request.setAttribute(Fields.SUBJECT_NAME_ENG, subject.getNameEng());
		LOG.trace("Set the request attribute: 'name_eng' = "
				+ subject.getNameEng());
		return Path.FORWARD_SUBJECT_VIEW_ADMIN;
	}

}
