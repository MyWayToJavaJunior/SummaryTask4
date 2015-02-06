package ua.nure.norkin.SummaryTask4.command.faculty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

/**
 * Invoked when admin wants to edit information about some faculty
 *
 * @author Mark Norkin
 *
 */
public class EditFacultyCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(EditFacultyCommand.class);

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
		LOG.debug("Start executing Command");

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			return null;
		}

		String result = null;

		if ("admin".equals(role)) {
			if (ActionType.FORWARD == actionType) {
				result = doGet(request, response);
			} else if (ActionType.REDIRECT == actionType) {
				result = doPost(request, response);
			}
		}

		LOG.debug("Finished executing Command");
		return result;

	}

	/**
	 * Forwards to the edit faculty page
	 *
	 * @return path to edit page.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String facultyName = request.getParameter("name");

		FacultyRepository facultyRepository = new FacultyRepository();
		Faculty faculty = facultyRepository.find(facultyName);

		request.setAttribute(Fields.FACULTY_NAME_RU, faculty.getNameRu());
		LOG.trace("Set attribute 'name_ru': " + faculty.getNameRu());
		request.setAttribute(Fields.FACULTY_NAME_ENG, faculty.getNameEng());
		LOG.trace("Set attribute 'name_eng': " + faculty.getNameEng());
		request.setAttribute(Fields.FACULTY_TOTAL_SEATS,
				faculty.getTotalSeats());
		LOG.trace("Set attribute 'total_seats': " + faculty.getTotalSeats());
		request.setAttribute(Fields.FACULTY_BUDGET_SEATS,
				faculty.getBudgetSeats());
		LOG.trace("Set attribute 'budget_seats': " + faculty.getBudgetSeats());

		SubjectRepository subjectRepository = new SubjectRepository();

		List<Subject> otherSubjects = subjectRepository
				.findAllNotFacultySubjects(faculty);
		request.setAttribute("otherSubjects", otherSubjects);
		LOG.trace("Set attribute 'otherSubjects': " + otherSubjects);

		List<Subject> facultySubjects = subjectRepository
				.findAllFacultySubjects(faculty);
		request.setAttribute("facultySubjects", facultySubjects);
		LOG.trace("Set attribute 'facultySubjects': " + facultySubjects);

		return Path.FORWARD_FACULTY_EDIT_ADMIN;
	}

	// TODO
	/**
	 * Edits faculty according to entered data by admin.
	 *
	 * @return path to the view of edited faculty if succeeded, otherwise
	 *         redisplays page with <code>doGet</code>
	 * @throws UnsupportedEncodingException
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String result = null;
		// get parameters from page
		String facultyNameRu = request.getParameter(Fields.FACULTY_NAME_RU);
		LOG.trace("Get parameter 'name_ru' = " + facultyNameRu);
		String facultyNameEng = request.getParameter(Fields.FACULTY_NAME_ENG);
		LOG.trace("Get parameter 'name_eng' = " + facultyNameEng);
		String facultyTotalSeats = request
				.getParameter(Fields.FACULTY_TOTAL_SEATS);
		LOG.trace("Get parameter 'total_seats' = " + facultyTotalSeats);
		String facultyBudgetSeats = request
				.getParameter(Fields.FACULTY_BUDGET_SEATS);
		LOG.trace("Get parameter 'budget_seats' = " + facultyBudgetSeats);

		boolean valid = true;

		/*
		 * if (!FieldValidation.isFilled(facultyName, facultyBudgetSeats,
		 * facultyTotalSeats)) { request.setAttribute("errorMessage",
		 * "Please fill all fields properly!");
		 * LOG.error("errorMessage: Not all fields are properly filled"); valid
		 * = false; } if (!FieldValidation.isByte(facultyTotalSeats,
		 * facultyBudgetSeats)) { request.setAttribute("errorMessage",
		 * "Please enter a valid number!");
		 * LOG.error("errorMessage: not a numbers"); valid = false;
		 *
		 * }
		 */

		Byte totalSeats = Byte.valueOf(facultyTotalSeats);
		Byte budgetSeats = Byte.valueOf(facultyBudgetSeats);

		if (!FieldValidation.checkBudgetLowerTotal(budgetSeats, totalSeats)) {
			request.setAttribute("errorMessage",
					"Budget seats should be lower then Total seats!");
			LOG.error("errorMessage: not valid number's for faculty seats");
			valid = false;
		}

		if (valid == false) {
			result = Path.REDIRECT_FACULTY_EDIT_ADMIN + facultyNameEng;
		}
		if (valid) {
			// if it's true then let's start to update the db

			LOG.trace("All fields are properly filled. Start updating database.");

			Faculty faculty = new Faculty(facultyNameRu, facultyNameEng,
					budgetSeats, totalSeats);

			FacultyRepository facultyRepository = new FacultyRepository();

			// if user changes faculty name we need to know the old one
			// to update record in db
			String oldFacultyName = request.getParameter("oldName");

			LOG.trace("Get old faculty name from page: " + oldFacultyName);

			Faculty oldFacultyRecord = facultyRepository.find(oldFacultyName);

			faculty.setId(oldFacultyRecord.getId());

			facultyRepository.update(faculty);

			LOG.trace("Faculty record updated from: " + oldFacultyRecord
					+ ", to: " + faculty);

			String[] oldCheckedSubjectIds = request
					.getParameterValues("oldCheckedSubjects");

			LOG.trace("Get checked subjects before: "
					+ Arrays.toString(oldCheckedSubjectIds));

			String[] newCheckedSubjectsIds = request
					.getParameterValues("subjects");

			LOG.trace("Get checked subjects after: "
					+ Arrays.toString(newCheckedSubjectsIds));

			FacultySubjectsRepository facultySubjectsRepository = new FacultySubjectsRepository();

			if (oldCheckedSubjectIds == null) {
				if (newCheckedSubjectsIds == null) {
					// if before all subjects were unchecked and they are
					// still
					// are
					// then nothing changed - do nothing
					LOG.trace("No faculty subjects records will be changed");
				} else if (newCheckedSubjectsIds != null) {
					// if user checked something,but before no subjects were
					// checked
					for (String newCheckedSubject : newCheckedSubjectsIds) {
						Integer subjectId = Integer.valueOf(newCheckedSubject);
						FacultySubjects facultySubject = new FacultySubjects(
								subjectId, faculty.getId());
						facultySubjectsRepository.create(facultySubject);
						LOG.trace("Faculty subjects record was created: "
								+ facultySubject);
					}
				}
			}

			if (oldCheckedSubjectIds != null) {
				if (newCheckedSubjectsIds == null) {
					// if user made unchecked all checkbox's and before
					// there
					// were some checked subjects
					LOG.trace("No subjects were checked for this faculty - all records that will be found will be deleted ");
					facultySubjectsRepository.deleteAllSubjects(faculty);
				} else if (newCheckedSubjectsIds != null) {
					// if there were checked subjects and still are

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
							facultySubjectsRepository.create(facultySubject);
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
				}
			}
			result = Path.REDIRECT_TO_FACULTY
					+ facultyNameEng;
		}
		return result;
	}
}
