package ua.nure.norkin.SummaryTask4.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Path;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Invoked when no command was found for client request.
 *
 * @author Mark Norkin
 *
 */
public class NoCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Command execution starts");

		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: 'errorMessage' = " + errorMessage);

		LOG.debug("Command execution finished");
		return Path.ERROR_PAGE;
	}

}