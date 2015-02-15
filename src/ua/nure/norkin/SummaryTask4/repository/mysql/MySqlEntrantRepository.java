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
import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.DatabaseAbstractRepository;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

/**
 * Entrant DAO. Performs basic read/write operations on Entrant entity.
 *
 * @author Mark Norkin
 *
 */
public class MySqlEntrantRepository extends DatabaseAbstractRepository<Entrant> {

	private static final String FIND_ALL_ENTRANTS = "SELECT * FROM university_admission.entrant;";
	private static final String FIND_ENTRANT = "SELECT * FROM university_admission.entrant WHERE entrant.id = ? LIMIT 1;";
	private static final String FIND_ENTRANT_BY_USER_ID = "SELECT * FROM university_admission.entrant WHERE entrant.User_idUser = ? LIMIT 1;";
	private static final String INSERT_ENTRANT = "INSERT INTO university_admission.entrant(entrant.city,entrant.district,entrant.school,entrant.User_idUser,entrant.isBlocked) VALUES (?,?,?,?,?);";
	private static final String UPDATE_ENTRANT = "UPDATE entrant SET entrant.city=?, entrant.district=?,entrant.school=?,entrant.User_idUser=?, entrant.isBlocked=? WHERE entrant.id=? LIMIT 1;";
	private static final String DELETE_ENTRANT = "DELETE FROM university_admission.entrant WHERE entrant.id=? LIMIT 1;";

	private final static Logger LOG = Logger.getLogger(MySqlEntrantRepository.class);
	private static final String FIND_ALL_FACULTY_ENTRANTS = "SELECT university_admission.entrant.* FROM university_admission.entrant INNER JOIN university_admission.faculty_entrants ON university_admission.faculty_entrants.Entrant_idEntrant=university_admission.entrant.id  WHERE faculty_entrants.Faculty_idFaculty = ? ;";

	public MySqlEntrantRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	public MySqlEntrantRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(Entrant entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_ENTRANT,
					PreparedStatement.RETURN_GENERATED_KEYS);
			int counter = 1;
			pstmt.setString(counter++, entity.getCity());
			pstmt.setString(counter++, entity.getDistrict());
			pstmt.setString(counter++, entity.getSchool());
			pstmt.setInt(counter++, entity.getUserId());
			pstmt.setBoolean(counter, entity.getBlockedStatus());

			pstmt.execute();
			connection.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				entity.setId(rs.getInt(Fields.GENERATED_KEY));
			}

		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create an entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
	}

	@Override
	public void update(Entrant entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_ENTRANT);
			int counter = 1;
			pstmt.setString(counter++, entity.getCity());
			pstmt.setString(counter++, entity.getDistrict());
			pstmt.setString(counter++, entity.getSchool());
			pstmt.setInt(counter++, entity.getUserId());
			pstmt.setBoolean(counter++, entity.getBlockedStatus());

			pstmt.setInt(counter, entity.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update an entrant", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public void delete(Entrant entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_ENTRANT);
			pstmt.setInt(1, entity.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete an entrant", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public Entrant find(int entityPK) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Entrant entrant = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ENTRANT);
			pstmt.setInt(1, entityPK);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				entrant = null;
			} else {
				entrant = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find an entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return entrant;
	}

	public Entrant find(User user) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Entrant entrant = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ENTRANT_BY_USER_ID);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				entrant = null;
			} else {
				entrant = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find an entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return entrant;
	}

	@Override
	public List<Entrant> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Entrant> users = new ArrayList<Entrant>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_ENTRANTS);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				users.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all entrants", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return users;
	}

	public List<Entrant> findAllFacultyEntrants(Faculty faculty) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Entrant> facultyEntrants = new ArrayList<Entrant>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_FACULTY_ENTRANTS);
			pstmt.setInt(1, faculty.getId());
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				facultyEntrants.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find faculty entrants", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultyEntrants;
	}

	/**
	 * Unmarshals Entrant record in database to Java Entrant instance.
	 *
	 * @param rs
	 *            - ResultSet record
	 * @return Entrant instance of this record
	 */
	private static Entrant unmarshal(ResultSet rs) {
		Entrant entrant = new Entrant();
		try {
			entrant.setId(rs.getInt(Fields.ENTITY_ID));
			entrant.setCity(rs.getString(Fields.ENTRANT_CITY));
			entrant.setDistrict(rs.getString(Fields.ENTRANT_DISTRICT));
			entrant.setSchool(rs.getString(Fields.ENTRANT_SCHOOL));
			entrant.setUserId(rs.getInt(Fields.USER_FOREIGN_KEY_ID));
			entrant.setBlockedStatus(rs.getBoolean(Fields.ENTRANT_IS_BLOCKED));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to entrant", e);
		}
		return entrant;
	}
}
