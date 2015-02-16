package ua.nure.norkin.SummaryTask4.repository.mysql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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

import ua.nure.norkin.SummaryTask4.entity.Entrant;
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.FacultyEntrants;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class MySqlFacultyEntrantsRepositoryTest {
	private static MySqlUserRepository userRepository;
	private static MySqlEntrantRepository entrantRepository;
	private static MySqlFacultyRepository facultyRepository;

	private static MySqlFacultyEntrantsRepository facultyEntrantsRepository;

	private static Faculty faculty;
	private static Entrant entrant;
	private static User user;

	private FacultyEntrants facultyEntrants;
	private static int facultyEntrantsId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		userRepository = new MySqlUserRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
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

		entrantRepository = new MySqlEntrantRepository(
				DataSourceFactory
						.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
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

		facultyRepository = new MySqlFacultyRepository(
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

		facultyEntrantsRepository = new MySqlFacultyEntrantsRepository(
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
		FacultyEntrants facultyEntrantsToDelete = new FacultyEntrants();

		facultyEntrantsToDelete.setId(facultyEntrantsId);
		facultyEntrantsRepository.delete(facultyEntrantsToDelete);

		entrantRepository.delete(entrant);
		userRepository.delete(user);
		facultyRepository.delete(faculty);
	}

	@Before
	public void setUp() throws Exception {
		facultyEntrants = new FacultyEntrants(faculty, entrant);
		facultyEntrantsRepository.create(facultyEntrants);
		facultyEntrantsId = facultyEntrants.getId();
	}

	@After
	public void tearDown() throws Exception {
		facultyEntrantsRepository.delete(facultyEntrants);
		facultyEntrants = null;
	}

	@Test
	public void testFacultyEntrantsRepositoryDataSource() {
		new MySqlFacultyEntrantsRepository(null);
	}

	@Test
	public void testCreate() {
		facultyEntrantsRepository.delete(facultyEntrants);
		facultyEntrants.setId(-1);// error code
		facultyEntrantsRepository.create(facultyEntrants);
		assertThat(facultyEntrants.getId(), not(equalTo(-1)));
		facultyEntrantsRepository.delete(facultyEntrants);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		facultyEntrantsRepository.update(facultyEntrants);
	}

	@Test
	public void testDelete() {
		facultyEntrantsRepository.delete(facultyEntrants);
		assertThat(facultyEntrantsRepository.find(facultyEntrantsId),
				is(equalTo(null)));
	}

	@Test
	public void testFindInt() {
		assertNotNull(facultyEntrantsRepository.find(facultyEntrantsId));
	}

	@Test
	public void testFindFacultyEntrants() {
		assertNotNull(facultyEntrantsRepository.find(facultyEntrants));
	}

	@Test
	public void testFindAll() {
		List<FacultyEntrants> facultyEntrantsList = facultyEntrantsRepository
				.findAll();
		assertTrue(facultyEntrantsList.size() > 0);
	}

}
