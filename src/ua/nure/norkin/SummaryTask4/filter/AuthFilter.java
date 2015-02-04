package ua.nure.norkin.SummaryTask4.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import ua.nure.norkin.SummaryTask4.Path;

/**
 * Filter which performs authorization of the user to access some resources.
 *
 * @author Mark Norkin
 *
 */
public class AuthFilter implements Filter {

	private final List<String> urls;
	private static final Logger LOG = Logger.getLogger(AuthFilter.class);

	/**
	 * Default constructor.
	 */

	public AuthFilter() {
		urls = new ArrayList<String>();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOG.debug("Start destroying filter: "
				+ AuthFilter.class.getSimpleName());
		// do nothing
		LOG.debug("Finished destroying filter: "
				+ AuthFilter.class.getSimpleName());
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String url = req.getContextPath();

		LOG.debug("Context path from request: " + url);
		if (urls.contains(url)) {

			LOG.debug("This url is accessed to all clients: " + url);
			chain.doFilter(req, res); // request for accessible url
		}

		LOG.debug("Requested url can't be viewed by all clients.");

		LOG.trace("Check if user is logged in the sytem.");
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			LOG.debug("Unauthorized access to resource. Client is not logged-in.");

			res.sendRedirect(Path.WELCOME_PAGE); // No logged-in user found, so
													// redirect to login page.
		} else {
			LOG.debug("Client is logged-in. Access granted to the resource. Filter execution finished.");
			chain.doFilter(req, res); // Logged-in user found, so just continue
										// request.
		}
	}

	/*
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String avoidURLs = fConfig.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(avoidURLs, ",");

		while (token.hasMoreTokens()) {
			urls.add(token.nextToken());
		}
	}

}
