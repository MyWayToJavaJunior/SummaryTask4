package ua.nure.norkin.SummaryTask4.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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

import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Mark;
import ua.nure.norkin.SummaryTask4.entity.Subject;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class MarkRepositoryTest {
	private static UserRepository userRepository;
	private static EntrantRepository entrantRepository;
	private static SubjectRepository subjectRepository;

	private static Entrant entrant;
	private static User user;
	private static Subject subject;

	private static MarkRepository markRepository;
	private Mark mark;
	private static int markId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userRepository = new UserRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_WO_JNDI_DATASOURCE)) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection connection = ds.getConnection();
				connection.setAutoCommit(false);
				return connection;
			}
		};

		user = new User();
		user.setFirstName("Al");
		user.setLastName("Pacino");
		user.setEmail("alpacino@gmail.com");
		user.setPassword("1234");
		user.setRole("client");
		user.setLang("ru");

		userRepository.create(user);

		entrantRepository = new EntrantRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_WO_JNDI_DATASOURCE)) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection connection = ds.getConnection();
				connection.setAutoCommit(false);
				return connection;
			}
		};

		entrant = new Entrant();
		entrant.setCity("California");
		entrant.setDistrict("CA");
		entrant.setSchool("School # 12");
		entrant.setBlockedStatus(false);
		entrant.setUserId(user.getId());

		entrantRepository.create(entrant);

		subjectRepository = new SubjectRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_WO_JNDI_DATASOURCE)) {
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

		markRepository = new MarkRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_WO_JNDI_DATASOURCE)) {
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
		Mark markToDelete = new Mark();
		markToDelete.setId(markId);
		markRepository.delete(markToDelete);

		entrantRepository.delete(entrant);
		userRepository.delete(user);
		subjectRepository.delete(subject);
	}

	@Before
	public void setUp() throws Exception {
		mark = new Mark();
		mark.setEntrantId(entrant.getId());
		mark.setSubjectId(subject.getId());
		mark.setMark((byte) 12);
		mark.setExamType("diploma");

		markRepository.create(mark);
		markId = mark.getId();
	}

	@After
	public void tearDown() throws Exception {
		markRepository.delete(mark);
		mark = null;
	}

	@Test
	public void testMarkRepositoryDataSource() {
		new MarkRepository(null);
	}

	@Test
	public void testMarkRepository() {
		new MarkRepository();
	}

	@Test
	public void testCreate() {
		markRepository.delete(mark);
		mark.setId(-1);
		markRepository.create(mark);
		assertThat(mark.getId(), not(equalTo(-1)));
		markRepository.delete(mark);
	}

	@Test
	public void testUpdate() {
		mark.setMark((byte) 10);
		markRepository.update(mark);
		assertThat(mark.getMark(), equalTo(markRepository.find(markId)
				.getMark()));
	}

	@Test
	public void testDelete() {
		markRepository.delete(mark);
		assertThat(markRepository.find(markId), is(equalTo(null)));
	}

	@Test
	public void testFind() {
		assertNotNull(markRepository.find(markId));
	}

	@Test
	public void testFindAll() {
		List<Mark> marks = markRepository.findAll();
		assertThat(marks.isEmpty(), is(equalTo(false)));
	}

}
