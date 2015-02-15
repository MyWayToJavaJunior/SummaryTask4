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
import ua.nure.norkin.SummaryTask4.entity.Mark;
import ua.nure.norkin.SummaryTask4.repository.DatabaseAbstractRepository;
import ua.nure.norkin.SummaryTask4.repository.MarkRepository;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

/**
 * Mark DAO. Performs basic read/write operations on Mark database table.
 *
 * @author Mark Norkin
 *
 */
public class MySqlMarkRepository extends DatabaseAbstractRepository<Mark>
		implements MarkRepository {

	private static final String FIND_ALL_MARKS = "SELECT * FROM university_admission.mark;";
	private static final String FIND_MARK = "SELECT * FROM university_admission.mark WHERE mark.id = ? LIMIT 1;";
	private static final String INSERT_MARK = "INSERT INTO university_admission.mark(mark.Entrant_idEntrant,mark.Subject_idSubject,mark.value,mark.exam_type) VALUES (?,?,?,?);";
	private static final String UPDATE_MARK = "UPDATE mark SET mark.Entrant_idEntrant=?, mark.Subject_idSubject=?,mark.value=?,mark.exam_type=? WHERE mark.id=? LIMIT 1;";
	private static final String DELETE_MARK = "DELETE FROM university_admission.mark WHERE mark.id=? LIMIT 1;";

	private final static Logger LOG = Logger
			.getLogger(MySqlMarkRepository.class);

	public MySqlMarkRepository(DataSource dataSource) {
		super(dataSource);
	}

	public MySqlMarkRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	@Override
	public void create(Mark entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_MARK,
					Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			// pstmt.setInt(1, user.getId());
			pstmt.setInt(counter++, entity.getEntrantId());
			pstmt.setInt(counter++, entity.getSubjectId());
			pstmt.setByte(counter++, entity.getMark());
			pstmt.setString(counter++, entity.getExamType());

			pstmt.execute();
			connection.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				entity.setId(rs.getInt(Fields.GENERATED_KEY));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create a mark", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
	}

	@Override
	public void update(Mark entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_MARK);
			int counter = 1;
			pstmt.setInt(counter++, entity.getEntrantId());
			pstmt.setInt(counter++, entity.getSubjectId());
			pstmt.setByte(counter++, entity.getMark());
			pstmt.setString(counter++, entity.getExamType());
			pstmt.setInt(counter, entity.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update a mark", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public void delete(Mark entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_MARK);
			pstmt.setInt(1, entity.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete a mark", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public Mark find(int entityPK) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Mark mark = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_MARK);
			pstmt.setInt(1, entityPK);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				mark = null;
			} else {
				mark = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a mark", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return mark;
	}

	@Override
	public List<Mark> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Mark> users = new ArrayList<Mark>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_MARKS);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				users.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all marks", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return users;
	}

	/**
	 * Unmarshals database Mark record to Mark java instance.
	 *
	 * @param rs
	 *            - ResultSet record in Mark table
	 * @return Mark instance of this record
	 */
	private static Mark unmarshal(ResultSet rs) {
		Mark mark = new Mark();
		try {
			mark.setId(rs.getInt(Fields.ENTITY_ID));
			mark.setEntrantId(rs.getInt(Fields.ENTRANT_FOREIGN_KEY_ID));
			mark.setSubjectId(rs.getInt(Fields.SUBJECT_FOREIGN_KEY_ID));
			mark.setMark(rs.getByte(Fields.MARK_VALUE));
			mark.setExamType(rs.getString(Fields.MARK_EXAM_TYPE));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to mark", e);
		}
		return mark;
	}
}
