package ua.nure.norkin.SummaryTask4.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.Fields;
import ua.nure.norkin.SummaryTask4.entity.result.EntrantReportSheet;

public class ReportSheetRepository extends
		AbstractRepository<EntrantReportSheet> {

	private final static Logger LOG = Logger
			.getLogger(ReportSheetRepository.class);

	private static final String GET_REPORT_SHEET = "SELECT `facultyId`, university_admission.user.first_name, university_admission.user.last_name, university_admission.user.email, university_admission.entrant.isBlocked, `preliminary_sum`, `diploma_sum`, `preliminary_sum` + `diploma_sum` AS `total_sum` FROM (SELECT university_admission.faculty_entrants.Faculty_idFaculty AS `facultyId`, university_admission.mark.Entrant_idEntrant AS `entrantId`, SUM(CASE `exam_type` WHEN 'preliminary' THEN university_admission.mark.value ELSE 0 END) AS `preliminary_sum`, SUM(CASE `exam_type` WHEN 'diploma' THEN university_admission.mark.value ELSE 0 END) AS `diploma_sum` FROM university_admission.faculty_entrants INNER JOIN university_admission.mark ON university_admission.faculty_entrants.Entrant_idEntrant = university_admission.mark.Entrant_idEntrant GROUP BY entrantId) AS `entrant_marks_sum` INNER JOIN university_admission.faculty ON `entrant_marks_sum`.`entrantId` = university_admission.faculty.id INNER JOIN university_admission.entrant ON `entrantId` = university_admission.entrant.id INNER JOIN university_admission.user ON university_admission.entrant.User_idUser = university_admission.user.id WHERE facultyId = ? ORDER BY isBlocked ASC , `total_sum` DESC;";

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
