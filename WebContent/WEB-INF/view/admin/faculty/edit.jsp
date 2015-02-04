<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>${faculty.name}</h1>

	<form id="edit_faculty" action="controller" method="POST">
		<input type="hidden" name="command" value="editFaculty" /> <input
			type="hidden" name="oldName" value="${requestScope.name}" />

		<fieldset>
			<label for="name">Faculty name:</label> <input name="name"
				type="text" value="${requestScope.name}" /> <br> <label
				for="budget_seats">Budget seats:</label> <input name="budget_seats"
				type="text" value="${requestScope.budget_seats}" /> <br> <label
				for="total_seats">Total seats:</label> <input name="total_seats"
				type="text" value="${requestScope.total_seats}" /> <br>
			<p>
				<label>Preliminary subjects:</label>
			</p>

			<c:if test="${not empty facultySubjects}">
				<c:forEach var="oldCheckedSubject" items="${facultySubjects}">
					<input type="hidden" name="oldCheckedSubjects"
						value="${oldCheckedSubject.id}" />
					<input type="checkbox" name="subjects"
						value="${oldCheckedSubject.id}" checked /> ${oldCheckedSubject.name} <br>
				</c:forEach>
			</c:if>

			<c:if test="${not empty otherSubjects}">
				<c:forEach var="oldUncheckedSubject" items="${otherSubjects}">
					<input type="checkbox" name="subjects"
						value="${oldUncheckedSubject.id}" /> ${oldUncheckedSubject.name} <br>
				</c:forEach>
			</c:if>
			<input type="submit" value="OK" />
		</fieldset>
	</form>
</body>
</html>