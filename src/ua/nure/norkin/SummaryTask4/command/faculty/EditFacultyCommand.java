package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class EditFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(EditFacultyCommand.class);

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
			Boolean justShow = Boolean.valueOf(request.getParameter("show"));
			// check if user just wants to open edit page to make some changes
			if (justShow) {
				String facultyName = request.getParameter("name");
				FacultyRepository facultyRepository = new FacultyRepository();
				Faculty faculty = facultyRepository.find(facultyName);
				request.setAttribute(Fields.FACULTY_NAME, faculty.getName());
				LOG.trace("Set attribute 'name': " + faculty.getName());
				request.setAttribute(Fields.FACULTY_TOTAL_SEATS,
						faculty.getTotalSeats());
				LOG.trace("Set attribute 'total_seats': "
						+ faculty.getTotalSeats());
				request.setAttribute(Fields.FACULTY_BUDGET_SEATS,
						faculty.getBudgetSeats());
				LOG.trace("Set attribute 'budget_seats': "
						+ faculty.getBudgetSeats());

				SubjectRepository subjectRepository = new SubjectRepository();

				List<Subject> otherSubjects = subjectRepository
						.findAllNotFacultySubjects(faculty);
				request.setAttribute("otherSubjects", otherSubjects);
				LOG.trace("Set attribute 'otherSubjects': " + otherSubjects);

				List<Subject> facultySubjects = subjectRepository
						.findAllFacultySubjects(faculty);
				request.setAttribute("facultySubjects", facultySubjects);
				LOG.trace("Set attribute 'facultySubjects': " + facultySubjects);

				result = Path.FACULTY_EDIT_ADMIN;
				// if not then user updated info and we should update db
			} else {
				// get parameters from page
				String facultyName = request.getParameter(Fields.FACULTY_NAME);
				String facultyTotalSeats = request
						.getParameter(Fields.FACULTY_TOTAL_SEATS);
				String facultyBudgetSeats = request
						.getParameter(Fields.FACULTY_BUDGET_SEATS);

				// check if they are valid
				if (!FieldValidation.isFilled(facultyName)
						|| !FieldValidation.isNumber(facultyTotalSeats,
								facultyBudgetSeats)) {
					request.setAttribute("errorMessage",
							"<br> Please fill all fields properly!");

					LOG.error("errorMessage: Not all fields are properly filled");

					result = Path.FACULTY_EDIT_ADMIN;
				} else {
					// if it's true then let's start to update the db

					Byte totalSeats = Byte.valueOf(facultyTotalSeats);
					Byte budgetSeats = Byte.valueOf(facultyBudgetSeats);

					Faculty faculty = new Faculty(facultyName, budgetSeats,
							totalSeats);

					FacultyRepository facultyRepository = new FacultyRepository();

					// if user changes faculty name we need to know the old one
					// to update record in db
					String oldFacultyName = request.getParameter("oldName");

					Faculty oldFacultyRecord = facultyRepository
							.find(oldFacultyName);

					faculty.setId(oldFacultyRecord.getId());

					facultyRepository.update(faculty);

					LOG.trace("Faculty record updated from: "
							+ oldFacultyRecord + ", to: " + faculty);

					String[] oldCheckedSubjectIds = request
							.getParameterValues("oldCheckedSubjects");

					String[] newCheckedSubjectsIds = request
							.getParameterValues("subjects");

					Arrays.toString(oldCheckedSubjectIds);
					Arrays.toString(newCheckedSubjectsIds);

					FacultySubjectsRepository facultySubjectsRepository = new FacultySubjectsRepository();

					// if user made unchecked all checkbox's and before there
					// were some checked subjects
					if (newCheckedSubjectsIds == null
							&& oldCheckedSubjectIds != null) {
						LOG.trace("No subjects were checked for this faculty - all records that will be found will be deleted ");
						facultySubjectsRepository.deleteAllSubjects(faculty);
					}
					// if before all subjects were unchecked and they are still
					// are
					// then nothing changed - do nothing
					if (newCheckedSubjectsIds == null
							&& oldCheckedSubjectIds == null) {
						// NOPE
						LOG.trace("No faculty subjects records will be changed");
					}

					// if there were checked subjects and still are
					if (newCheckedSubjectsIds != null
							&& oldCheckedSubjectIds != null) {
						// then for INSERT we should check if the record already
						// exists in db
						Set<String> existingRecords = new HashSet<>(
								Arrays.asList(oldCheckedSubjectIds));

						for (String newCheckedSubject : newCheckedSubjectsIds) {
							if (existingRecords.contains(newCheckedSubject)) {
								// if exists - then do nothing
								LOG.trace("This faculty subjects records already exists in db: "
										+ "facultyId = "
										+ faculty.getId()
										+ ", subjectId = " + newCheckedSubject);
							} else {
								// otherwise INSERT
								Integer subjectId = Integer
										.valueOf(newCheckedSubject);
								FacultySubjects facultySubject = new FacultySubjects(
										subjectId, faculty.getId());
								facultySubjectsRepository
										.create(facultySubject);
								LOG.trace("Faculty subjects record was created: "
										+ facultySubject);
							}
						}
						// and check for DELETE records that were previously
						// checked and now are not

						Set<String> newRecords = new HashSet<>(
								Arrays.asList(newCheckedSubjectsIds));

						for (String oldCkeckedSubject : oldCheckedSubjectIds) {
							if (newRecords.contains(oldCheckedSubjectIds)) {
								// then do nothing
							} else {
								// otherwise DELETE record in database
								Integer subjectId = Integer
										.valueOf(oldCkeckedSubject);
								FacultySubjects facultySubjectRecordToDelete = new FacultySubjects(
										subjectId, faculty.getId());
								facultySubjectsRepository
										.delete(facultySubjectRecordToDelete);
								LOG.trace("Faculty subjects record was deleted: "
										+ facultySubjectRecordToDelete);
							}
						}

						result = Path.FACULTY_VIEW_ALL_ADMIN;
					}
				}
			}
		}
		LOG.debug("Finished executing Command");
		return result;

	}
}
