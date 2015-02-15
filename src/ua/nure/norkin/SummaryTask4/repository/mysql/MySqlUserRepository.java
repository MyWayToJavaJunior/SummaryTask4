package ua.nure.norkin.SummaryTask4.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.DatabaseAbstractRepository;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

/**
 * User DAO object. Performs basic read/write operations on User data.
 *
 * @author Mark Norkin
 *
 */
public class MySqlUserRepository extends DatabaseAbstractRepository<User> {

	private static final String FIND_ALL_USERS = "SELECT * FROM university_admission.user;";
	private static final String FIND_USER = "SELECT * FROM university_admission.user WHERE user.id = ? LIMIT 1;";
	private static final String FIND_USER_BY_EMAIL_AND_PASS = "SELECT * FROM university_admission.user WHERE user.email = ? AND user.password=? LIMIT 1;";
	private static final String FIND_USER_BY_EMAIL = "SELECT * FROM university_admission.user WHERE user.email = ? LIMIT 1;";
	private static final String INSERT_USER = "INSERT INTO university_admission.user(user.first_name,user.last_name,user.email,user.password,user.role, lang) VALUES (?,?,?,?,?,?);";
	private static final String UPDATE_USER = "UPDATE user SET first_name=?,last_name=?,email=?,password=?,role=?, lang=? WHERE user.id= ? LIMIT 1;";
	private static final String DELETE_USER = "DELETE FROM university_admission.user WHERE user.id=? LIMIT 1;";

	private final static Logger LOG = Logger.getLogger(MySqlUserRepository.class);

	public MySqlUserRepository(DataSource dataSource) {
		super(dataSource);
	}

	public MySqlUserRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#create(java.lang.Object
	 * )
	 */
	public void create(User user) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		try {

			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_USER,
					PreparedStatement.RETURN_GENERATED_KEYS);
			int counter = 1;
			pstmt.setString(counter++, user.getFirstName());
			pstmt.setString(counter++, user.getLastName());
			pstmt.setString(counter++, user.getEmail());
			pstmt.setString(counter++, user.getPassword());
			pstmt.setString(counter++, user.getRole());
			pstmt.setString(counter, user.getLang());

			pstmt.execute();
			connection.commit();
			generatedKeys = pstmt.getGeneratedKeys();

			if (generatedKeys.next()) {
				user.setId(generatedKeys.getInt(Fields.GENERATED_KEY));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create a user", e);
		} finally {
			close(connection);
			close(pstmt);
			close(generatedKeys);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#update(java.lang.Object
	 * )
	 */
	public void update(User user) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_USER);
			int counter = 1;
			pstmt.setString(counter++, user.getFirstName());
			pstmt.setString(counter++, user.getLastName());
			pstmt.setString(counter++, user.getEmail());
			pstmt.setString(counter++, user.getPassword());
			pstmt.setString(counter++, user.getRole());
			pstmt.setString(counter++, user.getLang());

			pstmt.setInt(counter, user.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update a user", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#delete(java.lang.Object
	 * )
	 */
	public void delete(User user) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_USER);
			pstmt.setInt(1, user.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete a user", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ua.nure.norkin.SummaryTask4.repository.Repository#find(int)
	 */
	public User find(int userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_USER);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				user = null;
			} else {
				user = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a user", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return user;
	}

	/**
	 * Finds User in database by specified login and password. Mainly useful for
	 * login User in system.
	 *
	 * @param email
	 *            - user email
	 * @param password
	 *            - user password
	 * @return User with such fields
	 */
	public User find(String email, String password) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASS);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				user = null;
			} else {
				user = unmarshal(rs);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a user", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return user;
	}

	/**
	 * Finds User in database by specified email, it can be done because email
	 * should be unique.
	 *
	 * @param email
	 *            - user email
	 * @return User instance with such email
	 */
	public User find(String email) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				user = null;
			} else {
				user = unmarshal(rs);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a user", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ua.nure.norkin.SummaryTask4.repository.Repository#findAll()
	 */
	public List<User> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_USERS);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				users.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all users", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return users;
	}

	/**
	 * Unmarshals User record in database to Java instance.
	 *
	 * @param rs
	 *            - record from result set
	 * @return User instance of database record.
	 */
	private static User unmarshal(ResultSet rs) {
		User user = new User();
		try {
			user.setId(rs.getInt(Fields.ENTITY_ID));
			user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
			user.setLastName(rs.getString(Fields.USER_LAST_NAME));
			user.setEmail(rs.getString(Fields.USER_EMAIL));
			user.setPassword(rs.getString(Fields.USER_PASSWORD));
			user.setRole(rs.getString(Fields.USER_ROLE));
			user.setLang(rs.getString(Fields.USER_LANG));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal result set to user", e);
		}
		return user;
	}
}
