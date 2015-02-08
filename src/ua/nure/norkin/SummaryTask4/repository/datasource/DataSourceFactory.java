package ua.nure.norkin.SummaryTask4.repository.datasource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class DataSourceFactory {

	private final static Logger LOG = Logger.getLogger(DataSourceFactory.class);

	public static DataSource getDataSource(DataSourceType type) {

		switch (type) {

		case MY_SQL_DATASOURCE:
			Context initContext;
			try {
				initContext = new InitialContext();

				return (DataSource) initContext
						.lookup("java:/comp/env/jdbc/SummaryTask4");
			} catch (NamingException e) {
				LOG.error("Cannot get JNDI DataSource", e);
			}
		default:
			throw new UnsupportedOperationException("No such DataSource: "
					+ type);
		}
	}
}
