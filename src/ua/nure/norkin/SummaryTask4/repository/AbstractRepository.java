package ua.nure.norkin.SummaryTask4.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Main parent for all DAO objects that working with relational databases.
 * Declares and implements methods for closing Connections, ResultSets and
 * Statements. Also performs rollbacking of transaction.
 *
 * @author Mark Norkin
 *
 * @param <T>
 *            type of entity
 */
public abstract class AbstractRepository<T> implements Repository<T> {

	private final static Logger LOG = Logger
			.getLogger(AbstractRepository.class);
	protected final DataSource ds;

	/**
	 * Initializes DataSource object.
	 */
	public AbstractRepository(DataSource dataSource) {
		ds = dataSource;
	}

	/**
	 * @return Connection object from the pool.
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * Closes given Connection object.
	 *
	 * @param con
	 *            - connection to be closed
	 */
	protected void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error("Cannot commit transaction and close connection", ex);
			}
		}
	}

	/**
	 * Closes given ResultSet object.
	 *
	 * @param rs
	 *            - ResultSet to be closed.
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close a result set", ex);
			}
		}
	}

	/**
	 * Closes given Statrmrnt object
	 *
	 * @param stmt
	 *            - Statement to be closed
	 */
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
