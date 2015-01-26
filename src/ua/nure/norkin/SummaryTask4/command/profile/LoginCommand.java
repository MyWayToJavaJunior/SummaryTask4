package ua.nure.norkin.SummaryTask4.command.profile;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;

/**
 * Login command.
 *
 * @author Mark Norkin.
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Start executing Command");

		String result = null;

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserRepository userRepository = new UserRepository();
		User user = userRepository.find(email, password);
		LOG.trace("User found: " + user);
		if (user == null) {
			request.setAttribute("errorMessage",
					"Cannot find user with such login/password");
			LOG.error("errorMessage: Cannot find user with such login/password");
			// result.setFirst(ActionType.FORWARD);
			result = null;
		} else {
			HttpSession session = request.getSession(true);

			session.setAttribute("user", user.getEmail());
			LOG.trace("Set the session attribute 'user' = " + user.getEmail());

			String role = user.getRole();

			session.setAttribute("userRole", role);
			LOG.trace("Set the session attribute: 'userRole' = " + role);

			LOG.info("User: " + user + " logged as " + role);

			// result.setFirst(ActionType.FORWARD);

			FacultyRepository facultyRepository = new FacultyRepository();
			List<Faculty> faculties = facultyRepository.findAll();

			request.setAttribute("faculties", faculties);
			LOG.trace("Set request attribute: 'faculties' = " + faculties);

			if ("client".equals(role)) {
				result = "controller?command=viewAllFaculties";
//				result = Path.FACULTY_VIEW_ALL_CLIENT;
			} else if ("admin".equals(role)) {
				result = "controller?command=viewAllFaculties";
//				result = Path.FACULTY_VIEW_ALL_ADMIN;
			}
			LOG.debug("End executing command");
		}
		return result;
	}

}