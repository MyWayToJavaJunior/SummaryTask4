package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultySubjects;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

public class AddFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AddFacultyCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			result = null;
		} else if ("admin".equals(role)) {
			boolean show = Boolean.valueOf(request.getParameter("show"));
			LOG.trace("Request for only showing (not already adding) faculty/add.jsp is equal to: "
					+ show);
			if (show) {

				SubjectRepository subjectRepository = new SubjectRepository();
				List<Subject> allSubjects = subjectRepository.findAll();
				LOG.trace("All subjects found: " + allSubjects);
				request.setAttribute("allSubjects", allSubjects);
				LOG.trace("Set request attribute 'allSubjects' = "
						+ allSubjects);

				result = Path.FACULTY_ADD_ADMIN;
			} else {
				String facultyName = request.getParameter(Fields.FACULTY_NAME);
				String facultyTotalSeats = request
						.getParameter(Fields.FACULTY_TOTAL_SEATS);
				String facultyBudgetSeats = request
						.getParameter(Fields.FACULTY_BUDGET_SEATS);

				if (!FieldValidation.isFilled(facultyName)
						|| !FieldValidation.isNumber(facultyTotalSeats,
								facultyBudgetSeats)) {
					request.setAttribute("errorMessage",
							"<br> Please fill all fields properly!");

					LOG.error("errorMessage: Not all fields are properly filled");
					result = Path.FACULTY_ADD_ADMIN;
				} else {

					Byte totalSeats = Byte.valueOf(facultyTotalSeats);
					Byte budgetSeats = Byte.valueOf(facultyBudgetSeats);

					Faculty faculty = new Faculty(facultyName, budgetSeats,
							totalSeats);

					LOG.trace("Create faculty transfer object: " + faculty);

					FacultyRepository facultyRepository = new FacultyRepository();

					facultyRepository.create(faculty);

					LOG.trace("Create faculty record in databaset: " + faculty);

					// only after creating a faculty record we can proceed with
					// adding faculty subjects
					String[] choosedSubjects = request
							.getParameterValues("subjects");

					SubjectRepository subjectRepository = new SubjectRepository();
					FacultySubjectsRepository facultySubjectsRepository = new FacultySubjectsRepository();

					for (String subject : choosedSubjects) {
						Subject subjectRecord = subjectRepository.find(subject);
						FacultySubjects facultySubject = new FacultySubjects(
								subjectRecord, faculty);
						facultySubjectsRepository.create(facultySubject);
						LOG.trace("FacultySubjects record created in databaset: "
								+ facultySubject);
					}

					result = Path.FACULTY_VIEW_ALL_ADMIN;
				}
			}
		}
		LOG.debug("Finished executing Command");
		return result;
	}
}
