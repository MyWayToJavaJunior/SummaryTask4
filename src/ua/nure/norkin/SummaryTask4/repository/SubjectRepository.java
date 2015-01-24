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
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.Subject;

public class SubjectRepository extends AbstractRepository<Subject> {

	private static final String FIND_ALL_SUBJECTS = "SELECT * FROM university_admission.subject;";
	private static final String FIND_SUBJECT_BY_ID = "SELECT * FROM university_admission.subject WHERE subject.id=? LIMIT 1;";
	private final static String FIND_SUBJECT_BY_NAME = "SELECT * FROM university_admission.subject WHERE subject.name=? LIMIT 1;";
	private static final String INSERT_SUBJECT = "INSERT INTO subject(name) VALUES(?)";
	private static final String UPDATE_SUBJECT = "UPDATE subject SET subject.name=? WHERE subject.id=?;";
	private static final String DELETE_SUBJECT = "DELETE FROM university_admission.subject WHERE subject.id=? LIMIT 1;";
	private static final String FIND_ALL_FACULTY_SUBJECTS = "SELECT university_admission.subject.id, university_admission.subject.name FROM university_admission.subject,university_admission.faculty_subjects WHERE faculty_subjects.Faculty_idFaculty = ? AND university_admission.faculty_subjects.Subject_idSubject=university_admission.subject.id ;";
	private static final String FIND_ALL_NOT_FACULTY_SUBJECTS = "SELECT university_admission.subject.id, university_admission.subject.name FROM university_admission.subject LEFT JOIN university_admission.Faculty_Subjects ON university_admission.Faculty_Subjects.Subject_idSubject = university_admission.subject.id AND university_admission.Faculty_Subjects.Faculty_idFaculty = ? WHERE university_admission.Faculty_Subjects.id IS NULL;";

	private final static Logger LOG = Logger.getLogger(SubjectRepository.class);

	@Override
	public void create(Subject entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(INSERT_SUBJECT,
					Statement.RETURN_GENERATED_KEYS);
			// pstmt.setInt(1, entity.getId());
			pstmt.setString(1, entity.getName());

			pstmt.execute();
			connection.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				entity.setId(rs.getInt(Fields.GENERATED_KEY));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not create a subject", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}

	}

	@Override
	public void update(Subject entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(UPDATE_SUBJECT);
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getId());

			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not update a subject", e);
		} finally {
			close(connection);
			close(pstmt);
		}

	}

	@Override
	public void delete(Subject entity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(DELETE_SUBJECT);
			pstmt.setInt(1, entity.getId());

			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not delete a subject", e);
		} finally {
			close(connection);
			close(pstmt);
		}
	}

	@Override
	public Subject find(int entityPK) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Subject subject = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_SUBJECT_BY_ID);
			pstmt.setInt(1, entityPK);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				subject = null;
			} else {
				subject = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a subject", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return subject;
	}

	public Subject find(String subjectName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Subject subject = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_SUBJECT_BY_NAME);
			pstmt.setString(1, subjectName);
			rs = pstmt.executeQuery();
			connection.commit();
			if (!rs.next()) {
				subject = null;
			} else {
				subject = unmarshal(rs);
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a subject", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return subject;
	}

	@Override
	public List<Subject> findAll() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Subject> subjects = new ArrayList<Subject>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_SUBJECTS);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				subjects.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all subjects", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return subjects;
	}

	public List<Subject> findAllFacultySubjects(Faculty faculty) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Subject> facultySubjects = new ArrayList<Subject>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_FACULTY_SUBJECTS);
			pstmt.setInt(1, faculty.getId());
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				facultySubjects.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all faculty subjects", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultySubjects;
	}

	public List<Subject> findAllNotFacultySubjects(Faculty faculty) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Subject> facultySubjects = new ArrayList<Subject>();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_NOT_FACULTY_SUBJECTS);
			pstmt.setInt(1, faculty.getId());
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				facultySubjects.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find all not faculty subjects", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return facultySubjects;
	}


	private static Subject unmarshal(ResultSet rs) {
		Subject subject = new Subject();
		try {
			subject.setId(rs.getInt(Fields.ENTITY_ID));
			subject.setName(rs.getString(Fields.SUBJECT_NAME));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to subject", e);
		}
		return subject;
	}

}
