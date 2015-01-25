package ua.nure.norkin.SummaryTask4.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;

/**
 * View profile command.
 *
 * @author Mark Norkin
 *
 */
public class ViewProfileCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(ViewProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command execution starts");

		HttpSession session = request.getSession(false);
		String userEmail = String.valueOf(session.getAttribute("user"));

		UserRepository userRepository = new UserRepository();
		// should not be null !
		User user = userRepository.find(userEmail);

		String result = null;

		// result.setFirst(ActionType.FORWARD);

		request.setAttribute("first_name", user.getFirstName());
		LOG.trace("Set the request attribute: 'first_name' = "
				+ user.getFirstName());
		request.setAttribute("last_name", user.getLastName());
		LOG.trace("Set the request attribute: 'last_name' = "
				+ user.getLastName());
		request.setAttribute("email", user.getEmail());
		LOG.trace("Set the request attribute: 'email' = " + user.getEmail());
		request.setAttribute("role", user.getRole());
		LOG.trace("Set the request attribute: 'role' = " + user.getRole());

		String role = user.getRole();

		if ("client".equals(role)) {

			EntrantRepository entrantRepository = new EntrantRepository();
			// should not be null !!
			Entrant entrant = entrantRepository.find(user);

			request.setAttribute("city", entrant.getCity());
			LOG.trace("Set the request attribute: 'city' = "
					+ entrant.getCity());
			request.setAttribute("district", entrant.getDistrict());
			LOG.trace("Set the request attribute: 'district' = "
					+ entrant.getDistrict());
			request.setAttribute("school", entrant.getSchool());
			LOG.trace("Set the request attribute: 'school' = "
					+ entrant.getSchool());

			result = Path.CLIENT_PROFILE;
		} else if ("admin".equals(role)) {

			result = Path.ADMIN_PROFILE;
		}
		LOG.debug("Command execution finished");
		return result;
	}

}