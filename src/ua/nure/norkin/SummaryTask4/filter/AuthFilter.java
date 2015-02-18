package ua.nure.norkin.SummaryTask4.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Filter which performs authorization of the user to access resources of the
 * application.
 *
 * @author Mark Norkin
 *
 */
public class AuthFilter implements Filter {

	private final List<String> urls;
	/**
	 * accessible to all users
	 */
	private final Set<String> accessibleCommands;
	/**
	 * accessible only to logged in users
	 */
	private final Set<String> commonCommands;
	/**
	 * accessible only to client user
	 */
	private final Set<String> clientCommands;
	/**
	 * accessible only to administrator
	 */
	private final Set<String> adminCommands;
	private static final Logger LOG = Logger.getLogger(AuthFilter.class);

	/**
	 * Default constructor.
	 */

	public AuthFilter() {
		urls = new ArrayList<String>();
		accessibleCommands = new HashSet<>();
		commonCommands = new HashSet<>();
		clientCommands = new HashSet<>();
		adminCommands = new HashSet<>();

		accessibleCommands.add("login");
		accessibleCommands.add("viewFaculty");
		accessibleCommands.add("viewAllFaculties");
		accessibleCommands.add("client_registration");
		accessibleCommands.add("confirmRegistration");

		// common commands
		commonCommands.add("logout");
		commonCommands.add("viewProfile");
		commonCommands.add("editProfile");

		// client commands
		clientCommands.add("applyFaculty");
		// admin commands
		adminCommands.add("admin_registration");
		adminCommands.add("editFaculty");
		adminCommands.add("addFaculty");
		adminCommands.add("deleteFaculty");
		adminCommands.add("addSubject");
		adminCommands.add("editSubject");
		adminCommands.add("viewAllSubjects");
		adminCommands.add("viewSubject");
		adminCommands.add("viewEntrant");
		adminCommands.add("createReport");
		adminCommands.add("deleteSubject");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Start initializing filter: "
				+ AuthFilter.class.getSimpleName());
		String avoidURLs = fConfig.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(avoidURLs, ",");

		while (token.hasMoreTokens()) {
			urls.add(token.nextToken());
		}
		LOG.debug("Finished initializing filter: "
				+ AuthFilter.class.getSimpleName());
	}

	public void destroy() {
		LOG.debug("Start destroying filter: "
				+ AuthFilter.class.getSimpleName());
		// do nothing
		LOG.debug("Finished destroying filter: "
				+ AuthFilter.class.getSimpleName());
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String command = req.getParameter("command");

		if (accessibleCommands.contains(command)) {
			LOG.debug("This command can be accessed by all users: " + command);
			chain.doFilter(req, res); // request for accessible url
		} else {
			LOG.debug("This command can be accessed only by logged in users: "
					+ command);

			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				LOG.debug("Unauthorized access to resource. Client is not logged-in.");

				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				LOG.debug("User is logged-in. Check common commands to logged in users.");
				if (commonCommands.contains(command)) {
					chain.doFilter(req, res); // Logged-in user found, so
					// just
				} else {

					LOG.debug("Command is specific to user. Check user role.");
					if ("client".equals(session.getAttribute("userRole"))
							&& clientCommands.contains(command)) {
						LOG.debug("User is client. Command can be executed by client: "
								+ command);
						chain.doFilter(req, res); // Logged-in user found, so
													// just continue request.
					} else if ("admin".equals(session.getAttribute("userRole"))
							&& adminCommands.contains(command)) {

						LOG.debug("User is admin. Command can be executed by admin: "
								+ command);
						chain.doFilter(req, res); // Logged-in user found, so
													// just continue request.
					} else {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
			}
		}
	}

}
