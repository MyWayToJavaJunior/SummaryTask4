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

import ua.nure.norkin.SummaryTask4.entity.Faculty;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class FacultyRepositoryTest {

	private Faculty faculty;
	private static FacultyRepository facultyRepository;
	private static int facultyId;

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

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// clean up db
		Faculty facultyToDelete = new Faculty();
		facultyToDelete.setId(facultyId);
		facultyRepository.delete(facultyToDelete);
	}

	@Before
	public void setUp() throws Exception {
		faculty = new Faculty();

		faculty.setNameRu("new ru");
		faculty.setNameEng("name eng");
		faculty.setBudgetSeats((byte) 30);
		faculty.setTotalSeats((byte) 40);

		facultyRepository.create(faculty);

		facultyId = faculty.getId();
	}

	@After
	public void tearDown() throws Exception {
		facultyRepository.delete(faculty);
		faculty = null;
	}

	@Test
	public void testFacultyRepositoryDataSource() {
		new FacultyRepository(null);
	}

	@Test
	public void testFacultyRepository() {
		new FacultyRepository();
	}

	@Test
	public void testCreate() {
		facultyRepository.delete(faculty);
		faculty.setId(-1);// error code
		facultyRepository.create(faculty);
		assertThat(faculty.getId(), not(equalTo(-1)));
	}

	@Test
	public void testUpdate() {
		faculty.setNameEng("updated name eng");
		facultyRepository.update(faculty);
		assertThat(faculty.getNameEng(),
				equalTo(facultyRepository.find(facultyId).getNameEng()));
	}

	@Test
	public void testDelete() {
		facultyRepository.delete(faculty);
		assertThat(facultyRepository.find(facultyId), is(equalTo(null)));
	}

	@Test
	public void testFindInt() {
		assertNotNull(facultyRepository.find(facultyId));
	}

	@Test
	public void testFindString() {
		assertNotNull(facultyRepository.find(faculty.getNameRu()));
		assertNotNull(facultyRepository.find(faculty.getNameEng()));
	}

	@Test
	public void testFindAll() {
		List<Faculty> faculties = facultyRepository.findAll();
		assertThat(faculties.size(), is(not(0)));
	}

}
