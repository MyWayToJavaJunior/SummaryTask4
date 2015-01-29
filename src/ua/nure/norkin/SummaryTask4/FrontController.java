package ua.nure.norkin.SummaryTask4;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.command.Command;
import ua.nure.norkin.SummaryTask4.command.CommandManager;
import ua.nure.norkin.SummaryTask4.utils.ActionType;

/**
 * Servlet implementation class FrontController. This servlet handles all
 * requests by the client and then processes them according to specified command
 * name.
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(FrontController.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response, ActionType.FORWARD);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response, ActionType.REDIRECT);
	}

	/**
	 * Handles all requests coming from the client by executing the specified
	 * command name in a request. Implements PRG pattern by checking action type
	 * specified by the invoked method.
	 *
	 * @param request
	 * @param response
	 * @param actionType
	 * @throws IOException
	 * @throws ServletException
	 * @see ActionType
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {

		LOG.debug("Start processing in Controller");

		// extract command name from the request
		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: 'command' = " + commandName);

		// obtain command object by its name
		Command command = CommandManager.get(commandName);
		LOG.trace("Obtained 'command' = " + command);

		// execute command and get forward address
		String path = command.execute(request, response, actionType);

		if (path == null) {
			LOG.trace("Redirect to address = " + path);
			LOG.debug("Controller proccessing finished");
			response.sendRedirect(Path.WELCOME_PAGE);
		} else {
			if (actionType == ActionType.FORWARD) {
				LOG.trace("Forward to address = " + path);
				LOG.debug("Controller proccessing finished");
				RequestDispatcher disp = request.getRequestDispatcher(path);
				disp.forward(request, response);
			} else if (actionType == ActionType.REDIRECT) {
				LOG.trace("Redirect to address = " + path);
				LOG.debug("Controller proccessing finished");
				response.sendRedirect(path);
			}
		}
	}
}
