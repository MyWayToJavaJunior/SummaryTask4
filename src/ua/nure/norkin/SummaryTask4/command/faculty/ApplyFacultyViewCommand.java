package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;
import ua.nure.norkin.SummaryTask4.entity.Mark;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyEntrantsRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.MarkRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;

public class ApplyFacultyViewCommand extends Command {

	private static final long serialVersionUID = 8295388021320200832L;
	private static final Logger LOG = Logger
			.getLogger(ApplyFacultyViewCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;
		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));

		// admins are not permitted to access this page
		if ("admin".equals(role)) {
			result = null;
		} else if ("client".equals(role)) {
			// check if user just wants to apply and not already done it
			Boolean justShow = Boolean.valueOf(request.getParameter("show"));
			if (justShow) {
				String facultyName = request.getParameter(Fields.FACULTY_NAME);
				FacultyRepository facultyRepository = new FacultyRepository();
				Faculty faculty = facultyRepository.find(facultyName);

				request.setAttribute(Fields.ENTITY_ID, faculty.getId());
				LOG.trace("Set the request faculty attribute: 'id' = "
						+ faculty.getId());

				request.setAttribute(Fields.FACULTY_NAME, faculty.getName());
				LOG.trace("Set the request attribute: 'name' = "
						+ faculty.getName());
				request.setAttribute(Fields.FACULTY_TOTAL_SEATS,
						faculty.getTotalSeats());
				LOG.trace("Set the request attribute: 'total_seats' = "
						+ faculty.getTotalSeats());
				request.setAttribute(Fields.FACULTY_BUDGET_SEATS,
						faculty.getBudgetSeats());
				LOG.trace("Set the request attribute: 'budget_seats' = "
						+ faculty.getBudgetSeats());
				SubjectRepository subjectRepository = new SubjectRepository();

				List<Subject> facultySubjects = subjectRepository
						.findAllFacultySubjects(faculty);
				request.setAttribute("facultySubjects", facultySubjects);
				LOG.trace("Set attribute 'facultySubjects': " + facultySubjects);

				List<Subject> allSubjects = subjectRepository.findAll();
				request.setAttribute("allSubjects", allSubjects);
				LOG.trace("Set attribute 'allSubjects': " + allSubjects);

				StringBuilder facultySubjectsIds = new StringBuilder();
				for (Subject subject : facultySubjects) {
					facultySubjectsIds.append("facultySubjectId=").append(
							subject.getId());
				}

				StringBuilder allSubjectsIds = new StringBuilder();
				for (Subject subject : allSubjects) {
					allSubjectsIds.append("allSubjectId=").append(
							subject.getId());
				}

				result = Path.FACULTY_APPLY_CLIENT;
			} else {
				// if user applied for faculty, then we should update db

				LOG.trace("Start processing applying for faculty form");

				String email = String.valueOf(session.getAttribute("user"));

				UserRepository userRepository = new UserRepository();
				User user = userRepository.find(email);
				LOG.trace("Found user in database that wants to apply: " + user);

				EntrantRepository entrantRepository = new EntrantRepository();
				Entrant entrant = entrantRepository.find(user);

				LOG.trace("Found entrant record in database for this user: "
						+ entrant);

				LOG.trace("Start extracting data from request");

				Map<String, String[]> parameterMap = request.getParameterMap();
				for (String parameterName : parameterMap.keySet()) {

					if (parameterName.endsWith("preliminary")
							|| parameterName.endsWith("diploma")) {
						String[] value = parameterMap.get(parameterName);
						Byte markValue = Byte.valueOf(value[0]);
						String[] subjectIdAndExamType = parameterName
								.split("_");

						Integer subjectId = Integer
								.valueOf(subjectIdAndExamType[0]);
						String examType = subjectIdAndExamType[1];

						Mark mark = new Mark(subjectId, entrant.getId(),
								markValue, examType);
						LOG.trace("Create Mark transfer object: " + mark);

						MarkRepository markRepository = new MarkRepository();

						markRepository.create(mark);

						LOG.trace("Mark record was created in database: "
								+ mark);
					}
				}

				Integer facultyId = Integer.valueOf(request
						.getParameter("facultyId"));

				LOG.trace("End extracting data from request");
				FacultyEntrants newFacultyEntrant = new FacultyEntrants(
						facultyId, entrant.getId());

				LOG.trace("Create FacultyEntrants transfer object: "
						+ newFacultyEntrant);

				FacultyEntrantsRepository facultyEntrantsRepository = new FacultyEntrantsRepository();

				facultyEntrantsRepository.create(newFacultyEntrant);

				LOG.trace("FacultyEntrants record was created in database: "
						+ newFacultyEntrant);

				LOG.trace("Finished processing applying for faculty form");

				result = Path.FACULTY_VIEW_ALL_CLIENT;

			}
		}
		LOG.debug("Finished executing Command");
		return result;
	}
}
