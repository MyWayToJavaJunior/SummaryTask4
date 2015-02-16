package ua.nure.norkin.SummaryTask4.repository.datasource;

import org.junit.Test;

public class DataSourceTypeTest {

	@Test
	public void test() {
		DataSourceType.values();
		DataSourceType.valueOf(DataSourceType.MY_SQL_DATASOURCE.name());
	}

}
