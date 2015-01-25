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

public class EditSubjectCommand extends Command {

	private static final long serialVersionUID = 2946525838609196070L;
	private static final Logger LOG = Logger
			.getLogger(EditSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command execution starts");

		String role = String.valueOf(request.getSession(false).getAttribute(
				"userRole"));

		String result = null;
		// clients are not permitted to access this page
		if ("client".equals(role)) {
			result = null;
		} else if ("admin".equals(role)) {
			Boolean justShow = Boolean.valueOf(request.getParameter("show"));
			// check if user just wants to open edit page to make some changes
			if (justShow) {
				String subjectName = request.getParameter("name");
				Subject subject = new SubjectRepository().find(subjectName);

				request.setAttribute(Fields.SUBJECT_NAME, subject.getName());
				LOG.trace("Set attribute 'name': " + subject.getName());

				result = Path.SUBJECT_EDIT_ADMIN;
				// if not then user updated info and we should update db
			} else {
				// get parameters from page

				String oldSubjectName = request.getParameter("oldName");
				LOG.trace("Fetch request parapeter: 'oldName' = "
						+ oldSubjectName);

				SubjectRepository subjectRepository = new SubjectRepository();
				// should not be null !
				Subject subject = subjectRepository.find(oldSubjectName);
				LOG.trace("Subject record found with this data:" + subject);

				String newSubjectName = request.getParameter("name");
				LOG.trace("Fetch request parapeter: 'name' = " + newSubjectName);

				subject.setName(newSubjectName);

				LOG.trace("After calling setters with request parameters on subject entity: "
						+ subject);

				subjectRepository.update(subject);

				LOG.trace("Subject record updated");

				result = Path.SUBJECT_VIEW_ADMIN;
			}
		}
		LOG.debug("Finished executing Command");
		return result;
	}

}
