package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.MySQLRepositoryFactory;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user wants to delete faculty. Command allowed only for admins.
 *
 * @author Mark Norkin
 *
 */
public class DeleteFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(DeleteFacultyCommand.class);

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
	 * Redirects user to view of all faculties after submiting a delete button.
	 *
	 * @return path to view of all faculties.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		int facultyId = Integer.valueOf(request.getParameter(Fields.ENTITY_ID));

		Faculty facultyToDelete = new Faculty();
		facultyToDelete.setId(facultyId);

		FacultySubjectsRepository facultySubjectsRepository = MySQLRepositoryFactory
				.getFacultySubjectsRepository();
		facultySubjectsRepository.deleteAllSubjects(facultyToDelete);
		LOG.trace("Delete preliminary subjects records in database of a faculty: "
				+ facultyToDelete);

		FacultyRepository facultyRepository = MySQLRepositoryFactory
				.getFacultyRepository();
		facultyRepository.delete(facultyToDelete);

		LOG.trace("Delete faculty record in database: " + facultyToDelete);
		return Path.REDIRECT_TO_VIEW_ALL_FACULTIES;
	}

}
