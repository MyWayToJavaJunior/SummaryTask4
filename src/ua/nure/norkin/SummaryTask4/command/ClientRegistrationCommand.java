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
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

public class ClientRegistrationCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(ClientRegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		if (ActionType.FORWARD == actionType) {
			result = doGet(request, response);
		} else if (ActionType.REDIRECT == actionType) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");

		return result;
	}

	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		return Path.FORWARD_CLIENT_REGISTRATION_PAGE;
	}

	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug("Start executing Command");
		String email = request.getParameter(Fields.USER_EMAIL);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);
		String town = request.getParameter(Fields.ENTRANT_CITY);
		String district = request.getParameter(Fields.ENTRANT_DISTRICT);
		String school = request.getParameter(Fields.ENTRANT_SCHOOL);

		String result = null;

		if (!FieldValidation.isFilled(email, password, firstName, lastName,
				town, district, school)) {
			request.setAttribute("errorMessage", "<br> Please fill all fields!");

			LOG.error("errorMessage: Not all fields are filled");
			result = Path.REDIRECT_CLIENT_REGISTRATION_PAGE;
		} else {
			User user = new User(email, password, firstName, lastName,
					Role.CLIENT);
			UserRepository userRepository = new UserRepository();
			userRepository.create(user);
			LOG.trace("User record created: " + user);
			Entrant entrant = new Entrant(town, district, school, user);
			EntrantRepository entrantRepository = new EntrantRepository();
			entrantRepository.create(entrant);

			LOG.trace("Entrant record created: " + entrant);
			request.setAttribute("successfulMessage",
					"<br> Your successfully registered!");
			result = Path.WELCOME_PAGE;
		}
		return result;
	}

}
