<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<div class="header">
		<fmt:message key="report.view_jsp.label.report" />
	</div>
	<br>
	<div class="header">
		<c:out value="${lang eq 'ru' ? name_ru : name_eng}"></c:out>
	</div>
	<br>
	<br>

	<table id="reportTable" class="display">
		<thead>
			<tr>
				<td><fmt:message key="report.view_jsp.label.first_name" /></td>
				<td><fmt:message key="report.view_jsp.label.last_name" /></td>
				<td><fmt:message key="report.view_jsp.label.email" /></td>
				<td><fmt:message key="report.view_jsp.label.isBlocked" /></td>
				<td><fmt:message key="report.view_jsp.label.preliminary_sum" /></td>
				<td><fmt:message key="report.view_jsp.label.diploma_sum" /></td>
				<td><fmt:message key="report.view_jsp.label.total_sum" /></td>
				<td><fmt:message key="report.view_jsp.label.entered" /></td>
				<td><fmt:message key="report.view_jsp.label.entered_on_budget" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="reportRecord" items="${facultyReport}">
				<tr>
					<td><c:out value="${reportRecord.firstName}"></c:out></td>
					<td><c:out value="${reportRecord.lastName}"></c:out></td>
					<td><c:out value="${reportRecord.email}"></c:out></td>
					<td><c:if test="${reportRecord.blockedStatus == true}">
							<fmt:message key="report.view_jsp.label.blocked" />
						</c:if> <c:if test="${reportRecord.blockedStatus == false}">
							<fmt:message key="report.view_jsp.label.unblocked" />
						</c:if></td>
					<td><c:out value="${reportRecord.preliminarySum}"></c:out></td>
					<td><c:out value="${reportRecord.diplomaSum}"></c:out></td>
					<td><c:out value="${reportRecord.totalSum}"></c:out></td>
					<td><c:if test="${reportRecord.entered == true}">
							<fmt:message key="report.view_jsp.label.entered_yes_msg" />
						</c:if> <c:if test="${reportRecord.entered == false}">
							<fmt:message key="report.view_jsp.label.entered_no_msg" />
						</c:if></td>
					<td><c:if test="${reportRecord.enteredOnBudget == true}">
							<fmt:message
								key="report.view_jsp.label.entered_on_budget_yes_msg" />
						</c:if> <c:if test="${reportRecord.enteredOnBudget == false}">
							<fmt:message key="report.view_jsp.label.entered_on_budget_no_msg" />
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>