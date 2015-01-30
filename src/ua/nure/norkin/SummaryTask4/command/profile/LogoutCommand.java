package ua.nure.norkin.SummaryTask4.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when user wants to logout from the system.
 *
 * @author Mark Norkin
 *
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

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
		if (session != null) {
			session.invalidate();
		}
		LOG.debug("Finished executing Command");

		return Path.WELCOME_PAGE;
	}

}