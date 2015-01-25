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

import ua.nure.norkin.SummaryTask4.Path;

public class AuthFilter implements Filter {

	private  List<String> urls;

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
		urls = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String url = req.getContextPath();
		String url2 = req.getServletPath();
		//TODO
		System.out.println("ContextPath "+url);
		System.out.println("ServletPath"+url2);

		if (urls.contains(url)) {
			chain.doFilter(req, res); // request for accessible url
		}

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			res.sendRedirect(Path.WELCOME_PAGE); // No logged-in user found, so
													// redirect to login page.
		} else {
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
