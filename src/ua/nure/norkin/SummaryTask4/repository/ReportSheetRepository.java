package ua.nure.norkin.SummaryTask4.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.result.EntrantReportSheet;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceFactory;
import ua.nure.norkin.SummaryTask4.repository.datasource.DataSourceType;

public class ReportSheetRepository extends
		AbstractRepository<EntrantReportSheet> {

	private final static Logger LOG = Logger
			.getLogger(ReportSheetRepository.class);

	private static final String GET_REPORT_SHEET = "SELECT * FROM faculties_report_sheet WHERE facultyId=?;";

	public ReportSheetRepository(DataSource dataSource) {
		super(dataSource);
	}

	public ReportSheetRepository() {
		this(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE));
	}

	public List<EntrantReportSheet> getReport(int facultyId) {
		List<EntrantReportSheet> entrantsResults = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(GET_REPORT_SHEET);
			pstmt.setInt(1, facultyId);
			rs = pstmt.executeQuery();
			connection.commit();
			while (rs.next()) {
				entrantsResults.add(unmarshal(rs));
			}
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Can not find a faculty", e);
		} finally {
			close(connection);
			close(pstmt);
			close(rs);
		}
		return entrantsResults;
	}

	@Override
	public void create(EntrantReportSheet entity) {
		throw new UnsupportedOperationException(
				"Working with SQL View. Only read is allowed.");
	}

	@Override
	public void update(EntrantReportSheet entity) {
		throw new UnsupportedOperationException(
				"Working with SQL View. Only read is allowed.");
	}

	@Override
	public void delete(EntrantReportSheet entity) {
		throw new UnsupportedOperationException(
				"Working with SQL View. Only read is allowed.");
	}

	@Override
	public EntrantReportSheet find(int entityPK) {
		throw new UnsupportedOperationException(
				"Working with SQL View. Only read is allowed.");
	}

	@Override
	public List<EntrantReportSheet> findAll() {
		throw new UnsupportedOperationException(
				"Working with SQL View. Only read is allowed.");
	}

	private static EntrantReportSheet unmarshal(ResultSet rs) {
		EntrantReportSheet reportSheet = new EntrantReportSheet();
		try {
			reportSheet.setFacultyId(rs.getInt(Fields.REPORT_SHEET_FACULTY_ID));
			reportSheet.setFirstName(rs
					.getString(Fields.REPORT_SHEET_USER_FIRST_NAME));
			reportSheet.setLastName(rs
					.getString(Fields.REPORT_SHEET_USER_LAST_NAME));
			reportSheet.setEmail(rs.getString(Fields.REPORT_SHEET_USER_EMAIL));
			reportSheet.setBlockedStatus(rs
					.getBoolean(Fields.REPORT_SHEET_ENTRANT_IS_BLOCKED));
			reportSheet.setPreliminarySum(rs
					.getShort(Fields.REPORT_SHEET_ENTRANT_PRELIMINARY_SUM));
			reportSheet.setDiplomaSum(rs
					.getShort(Fields.REPORT_SHEET_ENTRANT_DIPLOMA_SUM));
			reportSheet.setTotalSum(rs
					.getShort(Fields.REPORT_SHEET_ENTRANT_TOTAL_SUM));
		} catch (SQLException e) {
			LOG.error("Can not unmarshal ResultSet to report sheet", e);
		}
		return reportSheet;
	}
}
