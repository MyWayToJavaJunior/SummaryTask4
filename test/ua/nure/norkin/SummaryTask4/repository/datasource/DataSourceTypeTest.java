package ua.nure.norkin.SummaryTask4.repository.datasource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataSourceTypeTest {

	@Test
	public void test() {
	DataSourceType.values();
	DataSourceType.valueOf(DataSourceType.MY_SQL_DATASOURCE.name());
	}

}
