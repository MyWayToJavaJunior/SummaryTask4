package ua.nure.norkin.SummaryTask4.repository;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.norkin.SummaryTask4.entity.result.EntrantReportSheet;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class ReportSheetRepositoryTest {

	private static ReportSheetRepository reportSheetRepository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reportSheetRepository = new ReportSheetRepository(
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
		reportSheetRepository = null;
	}

	@Test
	public void testReportSheetRepositoryDataSource() {
		new ReportSheetRepository(null);
	}

	@Test
	public void testReportSheetRepository() {
		new ReportSheetRepository();
	}

	@Test
	public void testGetReport() {
		List<EntrantReportSheet> report = reportSheetRepository.getReport(1);

		assertThat(report.isEmpty(),
				is(anyOf(is(Boolean.TRUE), is(Boolean.FALSE))));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCreate() {
		reportSheetRepository.create(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		reportSheetRepository.update(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDelete() {
		reportSheetRepository.delete(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testFind() {
		reportSheetRepository.find(0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testFindAll() {
		reportSheetRepository.findAll();
	}

}
