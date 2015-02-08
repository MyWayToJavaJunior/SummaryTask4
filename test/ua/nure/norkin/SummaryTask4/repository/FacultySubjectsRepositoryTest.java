package ua.nure.norkin.SummaryTask4.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultySubjects;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class FacultySubjectsRepositoryTest {

	private static FacultyRepository facultyRepository;
	private static Faculty faculty;
	private static SubjectRepository subjectRepository;
	private static Subject subject;

	private static FacultySubjectsRepository facultySubjectsRepository;
	private FacultySubjects facultySubjects;
	private static int facultySubjectsId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		facultyRepository = new FacultyRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection connection = ds.getConnection();
				connection.setAutoCommit(false);
				return connection;
			}
		};

		faculty = new Faculty();

		faculty.setNameRu("new ru");
		faculty.setNameEng("name eng");
		faculty.setBudgetSeats((byte) 30);
		faculty.setTotalSeats((byte) 40);

		facultyRepository.create(faculty);

		subjectRepository = new SubjectRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection connection = ds.getConnection();
				connection.setAutoCommit(false);
				return connection;
			}
		};

		subject = new Subject();
		subject.setNameRu("subject ru");
		subject.setNameEng("name eng");

		subjectRepository.create(subject);

		facultySubjectsRepository = new FacultySubjectsRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection connection = ds.getConnection();
				connection.setAutoCommit(false);
				return connection;
			}
		};

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// clean up db
		FacultySubjects facultySubjectsToDelete = new FacultySubjects();
		facultySubjectsToDelete.setId(facultySubjectsId);
		facultySubjectsRepository.delete(facultySubjectsToDelete);

		facultyRepository.delete(faculty);
		subjectRepository.delete(subject);
	}

	@Before
	public void setUp() throws Exception {
		facultySubjects = new FacultySubjects(subject, faculty);
		facultySubjectsRepository.create(facultySubjects);
		facultySubjectsId = facultySubjects.getId();
	}

	@After
	public void tearDown() throws Exception {
		facultySubjectsRepository.delete(facultySubjects);
		facultySubjects = null;
	}

	@Test
	public void testFacultySubjectsRepositoryDataSource() {
		new FacultySubjectsRepository(null);
	}

	@Test
	public void testFacultySubjectsRepository() {
		new FacultySubjectsRepository();
	}

	@Test
	public void testCreate() {
		facultySubjectsRepository.delete(facultySubjects);
		facultySubjects.setId(-1);// error code
		facultySubjectsRepository.create(facultySubjects);
		assertThat(facultySubjects.getId(), not(equals(-1)));
		facultySubjectsRepository.delete(facultySubjects);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		facultySubjectsRepository.update(facultySubjects);
	}

	@Test
	public void testDelete() {
		facultySubjectsRepository.delete(facultySubjects);
		assertThat(facultySubjectsRepository.find(facultySubjectsId),
				is(equalTo(null)));
	}

	@Test
	public void testDeleteAllSubjectsByFaculty() {
		facultySubjectsRepository.deleteAllSubjects(faculty);
		assertThat(facultySubjectsRepository.find(facultySubjectsId),
				is(nullValue()));
	}

	@Test
	public void testFind() {
		assertNotNull(facultySubjectsRepository.find(facultySubjectsId));
	}

	@Test
	public void testFindAll() {
		List<FacultySubjects> facultySubjectsList = facultySubjectsRepository
				.findAll();
		assertTrue(facultySubjectsList.size() > 0);
	}

}
