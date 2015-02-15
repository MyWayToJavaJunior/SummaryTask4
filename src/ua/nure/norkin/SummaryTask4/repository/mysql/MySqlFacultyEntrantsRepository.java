package ua.nure.norkin.SummaryTask4.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;
import ua.nure.norkin.SummaryTask4.repository.DatabaseAbstractRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyEntrantsRepository;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

/**
 * Faculty Entrants DAO. Performs basic read/write operations on Faculty
 * Entrants table.
 *
 * @author Mark Norkin
 *
 */
public class MySqlFacultyEntrantsRepository extends
		DatabaseAbstractRepository<FacultyEntrants> implements
		FacultyEntrantsRepository {

	private static final String FIND_ALL_FACULTY_ENTRANTS = "SELECT * FROM university_admission.faculty_entrants;";
	private static final String FIND_FACULTY_ENTRANT_BY_ID = "SELECT * FROM university_admission.faculty_entrants WHERE faculty_entrants.id = ? LIMIT 1;";
	private static final String FIND_FACULTY_ENTRANT_BY_FOREIGN_KEYS = "SELECT * FROM university_admission.faculty_entrants WHERE faculty_entrants.Faculty_idFaculty = ? AND faculty_entrants.Entrant_idEntrant = ? LIMIT 1;";
	private static final String INSERT_FACULTY_ENTRANT = "INSERT INTO university_admission.faculty_entrants(faculty_entrants.Faculty_idFaculty,faculty_entrants.Entrant_idEntrant) VALUES (?,?);";
	private static final String DELETE_FACULTY_ENTRANT = "DELETE FROM university_admission.faculty_entrants WHERE faculty_entrants.id=? LIMIT 1;";

	private final static Logger LOG = Logger
			.getLogger(MySqlFacultyEntrantsRepository.class);

	public MySqlFacultyEntrantsRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	public MySqlFacultyEntrantsRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(FacultyEntrants entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_FACULTY_ENTRANT,
					Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			pstmt.setInt(counter++, entity.getFacultyId());
			pstmt.setInt(counter, entity.getEntrantId());

			pstmt.execute();
			connection.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				entity.setId(rs.getInt(Fields.GENERATED_KEY));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create a faculty entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
	}

	@Override
	public void delete(FacultyEntrants entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_FACULTY_ENTRANT);
			pstmt.setInt(1, entity.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete a faculty entrant", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public FacultyEntrants find(int entityPK) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FacultyEntrants facultyEntrant = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_FACULTY_ENTRANT_BY_ID);
			pstmt.setInt(1, entityPK);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				facultyEntrant = null;
			} else {
				facultyEntrant = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a faculty entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultyEntrant;
	}

	@Override
	public FacultyEntrants find(FacultyEntrants facultyEntrants) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FacultyEntrants facultyEntrant = null;
		try {
			connection = getConnection();
			pstmt = connection
					.prepareStatement(FIND_FACULTY_ENTRANT_BY_FOREIGN_KEYS);
			pstmt.setInt(1, facultyEntrants.getFacultyId());
			pstmt.setInt(2, facultyEntrants.getEntrantId());

			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				facultyEntrant = null;
			} else {
				facultyEntrant = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a faculty entrant", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultyEntrant;
	}

	@Override
	public List<FacultyEntrants> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FacultyEntrants> facultyEntrants = new ArrayList<FacultyEntrants>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_FACULTY_ENTRANTS);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				facultyEntrants.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all faculty entrants", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultyEntrants;
	}

	/**
	 * Unmarshals Faculty Entrant record to java instance.
	 *
	 * @param rs
	 *            - ResultSet record in Faculty Entrants table
	 * @return Faculty Entrant instance of given record
	 */
	private static FacultyEntrants unmarshal(ResultSet rs) {
		FacultyEntrants facultyEntrant = new FacultyEntrants();
		try {
			facultyEntrant.setId(rs.getInt(Fields.ENTITY_ID));
			facultyEntrant.setFacultyId(rs
					.getInt(Fields.FACULTY_FOREIGN_KEY_ID));
			facultyEntrant.setEntrantId(rs
					.getInt(Fields.ENTRANT_FOREIGN_KEY_ID));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to faculty entrant", e);
		}
		return facultyEntrant;
	}
}
