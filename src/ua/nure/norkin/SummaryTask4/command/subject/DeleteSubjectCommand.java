package ua.nure.norkin.SummaryTask4.command.subject;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.FacultySubjects;
import ua.nure.norkin.SummaryTask4.entity.Mark;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.FacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.repository.factory.FactoryType;
import ua.nure.norkin.SummaryTask4.repository.factory.RepositoryFactory;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user wants to delete a subject. Command allowed only for admins.
 *
 * @author Mark Norkin
 *
 */
public class DeleteSubjectCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(DeleteSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		if (ActionType.POST == actionType) {
			result = doPost(request, response);
		} else {
			result = null;
		}

		LOG.debug("Finished executing Command");

		return result;
	}

	/**
	 * Redirects user to view of all subjects after submiting a delete button.
	 *
	 * @return path to view of all subjects if deletion was successful,
	 *         otherwise to subject view.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		int subjectId = Integer.valueOf(request.getParameter(Fields.ENTITY_ID));

		RepositoryFactory repositoryFactory = RepositoryFactory
				.getFactoryByName(FactoryType.MYSQL_REPOSITORY_FACTORY);

		SubjectRepository subjectRepository = repositoryFactory
				.getSubjectRepository();

		Subject subjectToDelete = subjectRepository.find(subjectId);

		FacultySubjectsRepository facultySubjectsRepository = repositoryFactory
				.getFacultySubjectsRepository();

		Collection<FacultySubjects> facultySubjects = facultySubjectsRepository
				.findAll();

		facultySubjects
				.removeIf(record -> record.getSubjectId() != subjectToDelete
						.getId());

		String result = null;

		if (facultySubjects.isEmpty()) {
			Collection<Mark> marks = repositoryFactory.getMarkRepository()
					.findAll();

			marks.removeIf(record -> record.getSubjectId() != subjectToDelete
					.getId());

			if (marks.isEmpty()) {
				subjectRepository.delete(subjectToDelete);
				result = Path.REDIRECT_TO_VIEW_ALL_SUBJECTS;
			} else {
				result = Path.REDIRECT_TO_SUBJECT
						+ subjectToDelete.getNameEng();
			}

		} else {
			result = Path.REDIRECT_TO_SUBJECT + subjectToDelete.getNameEng();
		}
		return result;
	}
}
