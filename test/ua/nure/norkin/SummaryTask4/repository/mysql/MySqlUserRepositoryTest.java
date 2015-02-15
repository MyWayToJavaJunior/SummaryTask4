package ua.nure.norkin.SummaryTask4.repository.mysql;

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

import ua.nure.norkin.SummaryTask4.entity.User;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;
import ua.nure.norkin.SummaryTask4.repository.mysql.MySqlUserRepository;

public class MySqlUserRepositoryTest {

	private User user;
	private static MySqlUserRepository userRepository;
	private static int userId;

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

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//clean up db
		User userToDelete = new User();
		userToDelete.setId(userId);
		userRepository.delete(userToDelete);
	}

	@Before
	public void setUp() throws Exception {
		user = new User();

		user.setFirstName("Al");
		user.setLastName("Pacino");
		user.setEmail("alpacino@gmail.com");
		user.setPassword("1234");
		user.setRole("client");
		user.setLang("ru");

		userRepository.create(user);

		userId = user.getId();
	}

	@After
	public void tearDown() throws Exception {
		userRepository.delete(user);
		user = null;
	}

	@Test
	public void testUserRepositoryConstructorWithDataSource() {
		new MySqlUserRepository();
	}

	@Test
	public void testUserRepositoryDefaultConstructor() {
		new MySqlUserRepository(null);
	}

	@Test
	public void testCreate() {
		userRepository.delete(user);

		user.setId(-1);// error code
		userRepository.create(user);

		assertThat(user.getId(), not(equalTo(-1)));

		userRepository.delete(user);
	}

	@Test
	public void testUpdate() {
		user.setEmail("fantasticpacino@gmail.com");
		userRepository.update(user);

		assertThat(user.getEmail(), equalTo(userRepository.find(userId)
				.getEmail()));
	}

	@Test
	public void testDelete() {
		userRepository.delete(user);
		assertThat(userRepository.find(userId), is(equalTo(null)));
	}

	@Test
	public void testFindInt() {
		assertNotNull(userRepository.find(userId));
	}

	@Test
	public void testFindStringString() {
		assertNotNull(userRepository.find(user.getEmail(), user.getPassword()));
	}

	@Test
	public void testFindString() {
		assertNotNull(userRepository.find(user.getEmail()));
	}

	@Test
	public void testFindAll() {
		List<User> users = userRepository.findAll();
		assertThat(users.isEmpty(), is(false));
	}

}
