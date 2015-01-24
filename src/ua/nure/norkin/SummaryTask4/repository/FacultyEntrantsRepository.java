package ua.nure.norkin.SummaryTask4.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;

public class FacultyEntrantsRepository extends
		AbstractRepository<FacultyEntrants> {

	private static final String FIND_ALL_FACULTY_ENTRANTS = "SELECT * FROM university_admission.faculty_entrants;";
	private static final String FIND_FACULTY_ENTRANT = "SELECT * FROM university_admission.faculty_entrants WHERE faculty_entrants.id = ? LIMIT 1;";
	private static final String INSERT_FACULTY_ENTRANT = "INSERT INTO university_admission.faculty_entrants(faculty_entrants.Faculty_idFaculty,faculty_entrants.Entrant_idEntrant) VALUES (?,?);";
	private static final String UPDATE_FACULTY_ENTRANT = "UPDATE faculty_entrants SET faculty_entrants.Faculty_idFaculty=?, faculty_entrants.Entrant_idEntrant=? WHERE faculty_entrants.id=? LIMIT 1;";
	private static final String DELETE_FACULTY_ENTRANT = "DELETE FROM university_admission.faculty_entrants WHERE faculty_entrants.id=? LIMIT 1;";

	private final static Logger LOG = Logger
			.getLogger(FacultyEntrantsRepository.class);

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
	public void update(FacultyEntrants entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_FACULTY_ENTRANT);
			int counter = 1;
			pstmt.setInt(counter++, entity.getFacultyId());
			pstmt.setInt(counter++, entity.getEntrantId());
			pstmt.setInt(counter, entity.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update a faculty entant", e);
		} finally {
			close(connection);
			close(pstmt);
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
			pstmt = connection.prepareStatement(FIND_FACULTY_ENTRANT);
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
