package ua.nure.norkin.SummaryTask4.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context listener. Initializes log4j, i18n and Command Manager for future use.
 *
 * @author Mark Norkin
 *
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initI18N(servletContext);
		initCommandManager();

		log("Servlet context initialization finished");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// do nothing
		log("Servlet context destruction finished");
	}

	/**
	 * Initializes i18n subsystem.
	 *
	 * @param servletContext
	 *            - gets init parameters from descriptor.
	 */
	private void initI18N(ServletContext servletContext) {
		LOG.debug("I18N subsystem initialization started");

		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			LOG.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}

			LOG.debug("Application attribute set: 'locales' = " + locales);
			servletContext.setAttribute("locales", locales);
		}

		LOG.debug("I18N subsystem initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 *
	 * @param servletContext
	 *            with <code>log4j.properties</code> file path, from which
	 *            <code>log4j</code> will be configured
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext
					.getRealPath("WEB-INF/log4j.properties"));
		} catch (Exception ex) {
			LOG.error("Cannot configure Log4j", ex);
		}
		log("Log4J initialization finished");
		LOG.debug("Log4j has been initialized");
	}

	/**
	 * Initializes CommandManager.
	 */
	private void initCommandManager() {

		// initialize commands manager
		// just load class to JVM
		try {
			Class.forName("ua.nure.norkin.SummaryTask4.command.CommandManager");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Cannot initialize Command Manager", ex);
		}
	}

	/**
	 * Logs actions to console
	 *
	 * @param msg
	 *            to be logged
	 */
	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}