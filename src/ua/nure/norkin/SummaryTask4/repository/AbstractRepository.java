package ua.nure.norkin.SummaryTask4.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class AbstractRepository<T> implements Repository<T> {

	private final static Logger LOG = Logger
			.getLogger(AbstractRepository.class);
	protected final DataSource ds /*= init()*/;

	private DataSource init() {
		DataSource dataSource = null;
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext
					.lookup("java:/comp/env/jdbc/SummaryTask4");
		} catch (NamingException ex) {
			LOG.error("Cannot obtain a connection from the pool", ex);
		}
		return dataSource;
	}

	public AbstractRepository() {
		ds = init();
	}

	protected Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	protected void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error("Cannot commit transaction and close connection", ex);
			}
		}
	}

	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close a result set", ex);
			}
		}
	}

	protected void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close a statement", ex);
			}
		}
	}

	/**
	 * Rollbacks and close the given connection.
	 *
	 * @param con
	 *            Connection to be rollbacked and closed.
	 */
	protected void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}
}
