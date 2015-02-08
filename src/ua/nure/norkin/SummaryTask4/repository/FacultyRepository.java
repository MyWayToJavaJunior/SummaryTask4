package ua.nure.norkin.SummaryTask4.repository;

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
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

/**
 * Faculty DAO. Performs basic read/write operations on Faculty entity.
 *
 * @author Mark Norkin
 *
 */
public class FacultyRepository extends AbstractRepository<Faculty> {

	private static final String FIND_ALL_FACULTIES = "SELECT * FROM university_admission.faculty;";
	private static final String FIND_FACULTY_BY_ID = "SELECT * FROM university_admission.faculty WHERE faculty.id = ? LIMIT 1;";
	private static final String FIND_FACULTY_BY_NAME = "SELECT * FROM university_admission.faculty WHERE faculty.name_ru = ? OR faculty.name_eng = ? LIMIT 1;";
	private static final String INSERT_FACULTY = "INSERT INTO university_admission.faculty(faculty.name_ru, faculty.name_eng, faculty.total_seats,faculty.budget_seats) VALUES (?,?,?,?);";
	private static final String UPDATE_FACULTY = "UPDATE faculty SET faculty.name_ru=?, faculty.name_eng=?, faculty.total_seats=?,faculty.budget_seats=? WHERE faculty.id=? LIMIT 1;";
	private static final String DELETE_FACULTY = "DELETE FROM university_admission.faculty WHERE faculty.id=? LIMIT 1;";

	private final static Logger LOG = Logger.getLogger(FacultyRepository.class);

	public FacultyRepository(DataSource dataSource) {
		super(dataSource);
	}

	public FacultyRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#create(java.lang.Object
	 * )
	 */
	@Override
	public void create(Faculty entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_FACULTY,
					Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			pstmt.setString(counter++, entity.getNameRu());
			pstmt.setString(counter++, entity.getNameEng());
			pstmt.setByte(counter++, entity.getTotalSeats());
			pstmt.setByte(counter, entity.getBudgetSeats());

			pstmt.execute();
			connection.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				entity.setId(rs.getInt(Fields.GENERATED_KEY));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create a faculty", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ua.nure.norkin.SummaryTask4.repository.Repository#update(java.lang.Object
	 * )
	 */
	@Override
	public void update(Faculty entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_FACULTY);
			int counter = 1;
			pstmt.setString(counter++, entity.getNameRu());
			pstmt.setString(counter++, entity.getNameEng());
			pstmt.setByte(counter++, entity.getTotalSeats());
			pstmt.setByte(counter++, entity.getBudgetSeats());

			pstmt.setInt(counter, entity.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update a faculty", e);
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
	@Override
	public void delete(Faculty entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_FACULTY);
			pstmt.setInt(1, entity.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete a faculty", e);
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
	@Override
	public Faculty find(int entityPK) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Faculty faculty = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_FACULTY_BY_ID);
			pstmt.setInt(1, entityPK);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				faculty = null;
			} else {
				faculty = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a faculty", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return faculty;
	}

	/**
	 * Finds Faculty record in database through faculty name. This can be
	 * achieved, because all faculties have unique names.
	 *
	 * @param facultyName
	 *            - faculty name to be searched through
	 * @return Faculty instance with specified name
	 */
	public Faculty find(String facultyName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Faculty faculty = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_FACULTY_BY_NAME);
			pstmt.setString(1, facultyName);
			pstmt.setString(2, facultyName);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				faculty = null;
			} else {
				faculty = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a faculty", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return faculty;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ua.nure.norkin.SummaryTask4.repository.Repository#findAll()
	 */
	@Override
	public List<Faculty> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Faculty> faculties = new ArrayList<Faculty>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_FACULTIES);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				faculties.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all faculties", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return faculties;
	}

	/**
	 * Unmarshals database Faculty record to java Faculty instance.
	 *
	 * @param rs
	 *            - ResultSet record
	 * @return Faculty instance of this record
	 */
	private static Faculty unmarshal(ResultSet rs) {
		Faculty faculty = new Faculty();
		try {
			faculty.setId(rs.getInt(Fields.ENTITY_ID));
			faculty.setNameRu(rs.getString(Fields.FACULTY_NAME_RU));
			faculty.setNameEng(rs.getString(Fields.FACULTY_NAME_ENG));
			faculty.setTotalSeats(rs.getByte(Fields.FACULTY_TOTAL_SEATS));
			faculty.setBudgetSeats(rs.getByte(Fields.FACULTY_BUDGET_SEATS));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to faculty", e);
		}
		return faculty;
	}
}
