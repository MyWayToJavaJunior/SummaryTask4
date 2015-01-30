package ua.nure.norkin.SummaryTask4.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Mark Norkin
 *
 */
public abstract class Command implements Serializable {
	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Execution method for command. Returns path to go to based on the client
	 * request.
	 *
	 * @param request
	 *            - client request
	 * @param response
	 *            - server response
	 * @param actionType
	 *            - client HTTP method
	 * @return Address to go once the command is executed.
	 * @throws IOException
	 * @throws ServletException
	 * @see ActionType
	 */
	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}