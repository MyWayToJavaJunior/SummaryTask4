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
import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class EntrantRepositoryTest {
	private static EntrantRepository entrantRepository;
	private static UserRepository userRepository;
	private static User user;
	private Entrant entrant;
	private static int entrantId;

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

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// clean up db
		Entrant testEntrantToDelete = new Entrant();
		testEntrantToDelete.setId(entrantId);
		entrantRepository.delete(testEntrantToDelete);
		userRepository.delete(user);
	}

	@Before
	public void setUp() throws Exception {
		entrant = new Entrant();
		entrant.setCity("California");
		entrant.setDistrict("CA");
		entrant.setSchool("School # 12");
		entrant.setBlockedStatus(false);
		entrant.setUserId(user.getId());

		entrantRepository.create(entrant);
		entrantId = entrant.getId();
	}

	@After
	public void tearDown() throws Exception {
		entrantRepository.delete(entrant);
		entrant = null;
	}

	@Test
	public void testEntrantRepository() {
		new EntrantRepository();
	}

	@Test
	public void testEntrantRepositoryDataSource() {
		new EntrantRepository(null);
	}

	@Test
	public void testCreate() {
		entrantRepository.delete(entrant);

		entrant.setId(-1);// error code
		entrantRepository.create(entrant);

		assertThat(user.getId(), not(equalTo(-1)));

		entrantRepository.delete(entrant);
	}

	@Test
	public void testUpdate() {
		entrant.setBlockedStatus(true);
		entrantRepository.update(entrant);

		assertThat(entrant.getBlockedStatus(),
				equalTo(entrantRepository.find(entrantId).getBlockedStatus()));
	}

	@Test
	public void testDelete() {
		entrantRepository.delete(entrant);
		assertThat(entrantRepository.find(entrantId), is(equalTo(null)));
	}

	@Test
	public void testFindInt() {
		assertNotNull(entrantRepository.find(entrantId));
	}

	@Test
	public void testFindUser() {
		assertNotNull(entrantRepository.find(user));
	}

	@Test
	public void testFindAll() {
		List<Entrant> users = entrantRepository.findAll();
		assertThat(users.isEmpty(), is(false));
	}

	@Test
	public void testFindAllFacultyEntrants() {
		Faculty faculty = new Faculty();
		faculty.setId(100_000_000); // no faculty with such id
		List<Entrant> facultyEntrants = entrantRepository
				.findAllFacultyEntrants(faculty);
		assertThat(facultyEntrants.size(), equalTo(0));
	}

}
