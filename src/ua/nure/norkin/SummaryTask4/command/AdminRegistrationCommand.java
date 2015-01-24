package ua.nure.norkin.SummaryTask4.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.entity.Role;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.FieldValidation;

public class AdminRegistrationCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(AdminRegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Start executing Command");
		String email = request.getParameter(Fields.USER_EMAIL);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);

		String result = null;
		// result.setFirst(ActionType.REDIRECT);

		if (!FieldValidation.isFilled(email, password, firstName, lastName)) {
			request.setAttribute("errorMessage", "<br> Please fill all fields!");

			LOG.error("errorMessage: Not all fields are filled");
			result = Path.ADMIN_REGISTRATION_PAGE;
		} else {
			User user = new User(email, password, firstName, lastName,
					Role.ADMIN);
			UserRepository userRepository = new UserRepository();
			userRepository.create(user);
			LOG.trace("User record created: " + user);
			request.setAttribute("successfulMessage",
					"<br> Your successfully registered!");
			result = Path.WELCOME_PAGE;
		}
		LOG.debug("Finished executing Command");
		return result;
	}

}
