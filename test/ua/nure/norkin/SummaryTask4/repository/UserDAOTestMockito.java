package ua.nure.norkin.SummaryTask4.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTestMockito {

	@Mock
	DataSource mockDataSource;
	@Mock
	Connection mockConn;
	@Mock
	PreparedStatement mockPreparedStmnt;
	@Mock
	ResultSet mockResultSet;

	int userId = 100;

	public UserDAOTestMockito() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() throws SQLException {
		when(mockDataSource.getConnection()).thenReturn(mockConn);
		when(mockDataSource.getConnection(anyString(), anyString()))
				.thenReturn(mockConn);
		doNothing().when(mockConn).commit();
		when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(
				mockPreparedStmnt);
		doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
		when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
		when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(mockResultSet.getInt(Fields.GENERATED_KEY)).thenReturn(userId);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCreate() throws SQLException {

		UserRepository instance = new UserRepository(mockDataSource);
		instance.create(new User());

		// verify and assert
		verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
		verify(mockPreparedStmnt, times(6)).setString(anyInt(), anyString());
		verify(mockPreparedStmnt, times(1)).execute();
		verify(mockConn, times(1)).commit();
		verify(mockResultSet, times(1)).next();
		verify(mockResultSet, times(1)).getInt(Fields.GENERATED_KEY);

	}

	@Test
	public void testUpdate() throws SQLException {
		UserRepository instance = new UserRepository(mockDataSource);
		instance.update(new User());

		verify(mockPreparedStmnt, times(6)).setString(anyInt(), anyString());
		verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStmnt, times(1)).executeUpdate();
		verify(mockConn, times(1)).commit();
	}
}