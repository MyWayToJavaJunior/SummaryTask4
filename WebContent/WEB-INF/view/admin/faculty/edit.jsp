<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
	<h1>${faculty.name}</h1>

	<form id="edit_faculty" action="controller" method="POST">
		<input type="hidden" name="command" value="editFaculty" /> <input
			type="hidden" name="oldName" value="${requestScope.name}" /> <input
			type="hidden" name="show" value="false" />

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
					<input type="hidden" name="oldCheckedSubjects" value="${oldCheckedSubject.id}" />
					<input type="checkbox" name="subjects" value="${oldCheckedSubject.id}" checked/> ${oldCheckedSubject.name} <br>
				</c:forEach>
			</c:if>

			<c:if test="${not empty otherSubjects}">
				<c:forEach var="oldUncheckedSubject" items="${otherSubjects}">
					<%-- <input type="hidden" name="oldUncheckedSubjects" value="${oldUncheckedSubject.id}" /> --%>
					<input type="checkbox" name="subjects" value="${oldUncheckedSubject.id}" /> ${oldUncheckedSubject.name} <br>
				</c:forEach>
			</c:if>

<%--

			<c:forEach var="subject" items="${allSubjects}">

				<c:if test="${empty facultySubjects}">
					<input type="checkbox" name="subjects" value="${subject.id}" /> ${subject.name} <br>
				</c:if>

				<c:if test="${not empty facultySubjects}">
					<c:forEach var="oldCheckedSubject" items="${facultySubjects}">
						<input type="hidden" name="oldCheckedSubjects"
							value="${oldCheckedSubject.id}" />
					</c:forEach>

					<c:forEach var="facultySubject" items="${facultySubjects}">
						<c:choose>
							<c:when test="${subject.id == facultySubject.id}">
								<input type="checkbox" name="subjects" value="${subject.id}"
									checked /> ${subject.name} <br>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="subjects" value="${subject.id}" /> ${subject.name} <br>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:forEach var="facultySubject" items="${facultySubjects}">
						<c:if test="${subject.id == facultySubject.id}">
							<input type="checkbox" name="subjects" value="${subject.id}"
								checked /> ${subject.name} HELLO 1<br>
						</c:if>
					</c:forEach>

					<input type="checkbox" name="subjects" value="${subject.id}" /> ${subject.name} HELLO 2<br>

				</c:if>
			</c:forEach>
 --%>
			<input type="submit" value="OK" />
		</fieldset>
	</form>
</body>
</html>