package ua.nure.norkin.SummaryTask4.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user logins in the system.
 *
 * @author Mark Norkin.
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

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

		String result = null;

		if (actionType == ActionType.REDIRECT) {
			result = doPost(request, response);
		} else {
			result = null;
		}

		LOG.debug("End executing command");
		return result;
	}

	/**
	 * Logins user in system. As first page displays view of all faculties.
	 *
	 * @return path to the view of all faculties.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
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
			result = null;
		} else {
			HttpSession session = request.getSession(true);

			session.setAttribute("user", user.getEmail());
			LOG.trace("Set the session attribute 'user' = " + user.getEmail());

			session.setAttribute("userRole", user.getRole());
			LOG.trace("Set the session attribute: 'userRole' = "
					+ user.getRole());

			session.setAttribute("lang", user.getLang());
			LOG.trace("Set the session attribute 'lang' = " + user.getLang());

			LOG.info("User: " + user + " logged as " + user.getRole());

			result = Path.REDIRECT_TO_VIEW_ALL_FACULTIES;
		}
		return result;
	}

}