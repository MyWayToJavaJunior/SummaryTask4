package ua.nure.norkin.SummaryTask4.repository.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class DataSourceFactoryTest {

	@Test
	public void testGetDataSource() {
		DataSource dataSource = DataSourceFactory
				.getDataSource(DataSourceType.MY_SQL_DATASOURCE);
		DataSource dataSource2 = DataSourceFactory
				.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI);

		assertTrue(dataSource.getClass().equals(
				MysqlConnectionPoolDataSource.class));
		assertEquals(dataSource2.getClass(),
				MysqlConnectionPoolDataSource.class);
	}

}
