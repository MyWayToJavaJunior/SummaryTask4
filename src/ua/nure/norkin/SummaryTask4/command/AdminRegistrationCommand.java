package ua.nure.norkin.SummaryTask4.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.entity.Role;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.MySQLRepositoryFactory;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

/**
 * Invoked when administrator wants to add another admin user.
 *
 * @author Mark Norkin
 *
 */
public class AdminRegistrationCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(AdminRegistrationCommand.class);

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

		HttpSession session = request.getSession(false);
		String role = String.valueOf(session.getAttribute("userRole"));

		// clients are not permitted to access this page
		if ("client".equals(role)) {
			return null;
		}

		String result = null;

		if (actionType == ActionType.GET) {
			result = doGet(request, response);
		} else if (actionType == ActionType.POST) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	/**
	 * Forwards user to registration admin page.
	 *
	 * @return path where lie this page
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		return Path.FORWARD_ADMIN_REGISTRATION_PAGE;
	}

	/**
	 * If validation is successful then admin record will be added in database.
	 *
	 * @return after registartion will be completed returns path to welcome
	 *         page, if not then doGet method will be called.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String email = request.getParameter(Fields.USER_EMAIL);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);
		String lang = request.getParameter(Fields.USER_LANG);

		String result = null;

		if (!FieldValidation.isFilled(email, password, firstName, lastName)) {
			request.setAttribute("errorMessage", "Please fill all fields!");

			LOG.error("errorMessage: Not all fields are filled");
			result = Path.REDIRECT_ADMIN_REGISTRATION_PAGE;
		} else {
			User user = new User(email, password, firstName, lastName,
					Role.ADMIN, lang);
			UserRepository userRepository = MySQLRepositoryFactory
					.getUserRepository();
			userRepository.create(user);
			LOG.trace("User record created: " + user);
			request.setAttribute("successfulMessage",
					"Your successfully registered!");
			result = Path.WELCOME_PAGE;
		}
		return result;

	}
}
