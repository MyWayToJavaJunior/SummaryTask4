package ua.nure.norkin.SummaryTask4.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class SubjectRepositoryTest {

	private Subject subject;
	private static SubjectRepository subjectRepository;
	private static int subjectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// clean up db
		Subject subjectToDelete = new Subject();
		subjectToDelete.setId(subjectId);
		subjectRepository.delete(subjectToDelete);
	}

	@Before
	public void setUp() throws Exception {
		subject = new Subject();
		subject.setNameRu("subject ru");
		subject.setNameEng("name eng");
		subjectRepository.create(subject);

		subjectId = subject.getId();
	}

	@After
	public void tearDown() throws Exception {
		subjectRepository.delete(subject);
		subject = null;
	}

	@Test
	public void testSubjectRepositoryDataSource() {
		new SubjectRepository(null);
	}

	@Test
	public void testSubjectRepository() {
		new SubjectRepository();
	}

	@Test
	public void testCreate() {
		subjectRepository.delete(subject);
		subject.setId(-1);// error code
		subjectRepository.create(subject);
		assertThat(subject.getId(), not(equalTo(-1)));
		subjectRepository.delete(subject);
	}

	@Test
	public void testUpdate() {
		subject.setNameEng("new subject name");
		subjectRepository.update(subject);
		assertThat(subject.getNameEng(),
				equalTo(subjectRepository.find(subjectId).getNameEng()));
	}

	@Test
	public void testDelete() {
		subjectRepository.delete(subject);
		assertThat(subjectRepository.find(subjectId), is(equalTo(null)));
	}

	@Test
	public void testFindInt() {
		assertNotNull(subjectRepository.find(subjectId));
	}

	@Test
	public void testFindString() {
		assertNotNull(subjectRepository.find(subject.getNameEng()));
		assertNotNull(subjectRepository.find(subject.getNameRu()));
	}

	@Test
	public void testFindAll() {
		List<Subject> subjects = subjectRepository.findAll();
		assertFalse(subjects.isEmpty());
	}

	@Test
	public void testFindAllFacultySubjects() {
		Faculty faculty = new Faculty();
		faculty.setId(100_000_000);// so faculty with such id
		List<Subject> facultySubjects = subjectRepository
				.findAllFacultySubjects(faculty);
		assertEquals(facultySubjects.size(), 0);
	}

	@Test
	public void testFindAllNotFacultySubjects() {
		Faculty faculty = new Faculty();
		faculty.setId(100_000_000);// so faculty with such id
		List<Subject> notFacultySubjects = subjectRepository
				.findAllNotFacultySubjects(faculty);

		List<Subject> allSubjects = subjectRepository.findAll();

		assertThat(notFacultySubjects.size(), is(equalTo(allSubjects.size())));
	}

}
