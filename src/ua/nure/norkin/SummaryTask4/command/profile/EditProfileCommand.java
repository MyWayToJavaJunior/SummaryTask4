package ua.nure.norkin.SummaryTask4.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Edit profile command.
 *
 * @author Mark Norkin
 *
 */
public class EditProfileCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(EditProfileCommand.class);

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

		if (role == null) {
			return null;
		}

		String result = null;

		if (ActionType.FORWARD == actionType) {
			result = doGet(request, response);
		} else if (ActionType.REDIRECT == actionType) {
			result = doPost(request, response);
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	/**
	 * Invoked when user wants to edit his page.
	 *
	 * @return path to the edit profile page.
	 */
	private String doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String result = null;
		HttpSession session = request.getSession(false);

		String userEmail = String.valueOf(session.getAttribute("user"));
		String role = String.valueOf(session.getAttribute("userRole"));

		UserRepository userRepository = new UserRepository();
		User user = userRepository.find(userEmail);

		request.setAttribute(Fields.USER_FIRST_NAME, user.getFirstName());
		LOG.trace("Set attribute 'first_name': " + user.getFirstName());
		request.setAttribute(Fields.USER_LAST_NAME, user.getLastName());
		LOG.trace("Set attribute 'last_name': " + user.getLastName());
		request.setAttribute(Fields.USER_EMAIL, user.getEmail());
		LOG.trace("Set attribute 'email': " + user.getEmail());
		request.setAttribute(Fields.USER_PASSWORD, user.getPassword());
		LOG.trace("Set attribute 'password': " + user.getPassword());

		// result.setFirst(ActionType.FORWARD);

		if ("client".equals(role)) {

			EntrantRepository entrantRepository = new EntrantRepository();
			Entrant entrant = entrantRepository.find(user);

			request.setAttribute(Fields.ENTRANT_CITY, entrant.getCity());
			LOG.trace("Set attribute 'city': " + entrant.getCity());
			request.setAttribute(Fields.ENTRANT_DISTRICT, entrant.getDistrict());
			LOG.trace("Set attribute 'district': " + entrant.getDistrict());
			request.setAttribute(Fields.ENTRANT_SCHOOL, entrant.getSchool());
			LOG.trace("Set attribute 'school': " + entrant.getSchool());
			request.setAttribute(Fields.ENTRANT_IS_BLOCKED,
					entrant.getBlockedStatus());
			LOG.trace("Set attribute 'isBlocked': "
					+ entrant.getBlockedStatus());

			result = Path.FORWARD_CLIENT_PROFILE_EDIT;
		} else if ("admin".equals(role)) {
			result = Path.FORWARD_ADMIN_PROFILE_EDIT;
		}
		return result;
	}

	// TODO validation
	/**
	 * Invoked when user already edit his profile and wants to update it.
	 *
	 * @return path to the user profile if command succeeds, otherwise
	 *         redisplays editing page.
	 */
	private String doPost(HttpServletRequest request,
			HttpServletResponse response) {
		// user updated info and we should update db
		String oldUserEmail = request.getParameter("oldEmail");
		LOG.trace("Fetch request parapeter: 'oldEmail' = " + oldUserEmail);
		UserRepository userRepository = new UserRepository();
		// should not be null !
		User user = userRepository.find(oldUserEmail);

		LOG.trace("User found with such email:" + user);

		String userFirstName = request.getParameter(Fields.USER_FIRST_NAME);
		LOG.trace("Fetch request parapeter: 'first_name' = " + userFirstName);
		String userLastName = request.getParameter(Fields.USER_LAST_NAME);
		LOG.trace("Fetch request parapeter: 'last_name' = " + userLastName);
		String email = request.getParameter("email");
		LOG.trace("Fetch request parapeter: 'email' = " + email);
		String password = request.getParameter("password");
		LOG.trace("Fetch request parapeter: 'password' = " + password);

		// user.setEmail(userEmail);
		user.setFirstName(userFirstName);
		user.setLastName(userLastName);
		user.setEmail(email);
		user.setPassword(password);

		LOG.trace("After calling setters with request parapeters on user entity: "
				+ user);

		userRepository.update(user);

		LOG.trace("User info updated");

		// if user role is client then we should also update entrant record
		// for him
		if ("client".equals(user.getRole())) {

			EntrantRepository entrantRepository = new EntrantRepository();

			// should not be null !!
			Entrant entrant = entrantRepository.find(user);

			String school = request.getParameter(Fields.ENTRANT_SCHOOL);
			LOG.trace("Fetch request parameter: 'school' = " + school);
			String district = request.getParameter(Fields.ENTRANT_DISTRICT);
			LOG.trace("Fetch request parameter: 'district' = " + district);
			String city = request.getParameter(Fields.ENTRANT_CITY);
			LOG.trace("Fetch request parameter: 'city' = " + city);
			boolean blockedStatus = Boolean.valueOf(request
					.getParameter(Fields.ENTRANT_IS_BLOCKED));
			LOG.trace("Fetch request parameter: 'isBlocked' = " + blockedStatus);

			entrant.setCity(city);
			entrant.setDistrict(district);
			entrant.setSchool(school);
			entrant.setBlockedStatus(blockedStatus);

			LOG.trace("After calling setters with request parapeters on entrant entity: "
					+ entrant);

			entrantRepository.update(entrant);
			LOG.trace("Entrant info updated");

		}

		// update session attribute if user changed it
		request.getSession().setAttribute("user", email);

		return Path.REDIRECT_TO_PROFILE;
	}

}