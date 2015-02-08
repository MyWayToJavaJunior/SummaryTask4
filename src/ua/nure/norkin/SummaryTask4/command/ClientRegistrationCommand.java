package ua.nure.norkin.SummaryTask4.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Role;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.MySQLRepositoryFactory;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

/**
 * Invoked when client registers in system.
 *
 * @author Mark Norkin
 *
 */
public class ClientRegistrationCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(ClientRegistrationCommand.class);

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

		if (ActionType.GET == actionType) {
			result = doGet(request, response);
		} else if (ActionType.POST == actionType) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");

		return result;
	}

	/**
	 * Forwards user to client registration page.
	 *
	 * @return path where page lies
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		return Path.FORWARD_CLIENT_REGISTRATION_PAGE;
	}

	/**
	 * Registers user in system, if all fields is properly filled
	 *
	 * @return path to welcome page if registration successful, redisplays
	 *         client registration page otherwise.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug("Start executing Command");
		String email = request.getParameter(Fields.USER_EMAIL);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);
		String lang = request.getParameter(Fields.USER_LANG);

		String town = request.getParameter(Fields.ENTRANT_CITY);
		String district = request.getParameter(Fields.ENTRANT_DISTRICT);
		String school = request.getParameter(Fields.ENTRANT_SCHOOL);

		String result = null;

		// TODO check email for uniqueness, password for char's amount
		if (!FieldValidation.isFilled(email, password, firstName, lastName,
				town, district, school)) {
			request.setAttribute("errorMessage", "Please fill all fields!");

			UserRepository userRepository = MySQLRepositoryFactory
					.getUserRepository();
			User user = userRepository.find(email);
			if (user != null) {

			}

			LOG.error("errorMessage: Not all fields are filled");
			result = Path.REDIRECT_CLIENT_REGISTRATION_PAGE;
		} else {
			User user = new User(email, password, firstName, lastName,
					Role.CLIENT, lang);
			UserRepository userRepository = new UserRepository();
			userRepository.create(user);
			LOG.trace("User record created: " + user);
			Entrant entrant = new Entrant(town, district, school, user);
			EntrantRepository entrantRepository = MySQLRepositoryFactory
					.getEntrantRepository();
			entrantRepository.create(entrant);

			LOG.trace("Entrant record created: " + entrant);
			request.setAttribute("successfulMessage",
					"Your successfully registered!");
			result = Path.WELCOME_PAGE;
		}
		return result;
	}

}
