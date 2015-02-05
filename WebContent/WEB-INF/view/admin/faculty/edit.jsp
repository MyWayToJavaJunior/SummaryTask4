<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>${faculty.name}</h1>

	<div class="form">
		<form id="edit_faculty" action="controller" method="POST">
			<input type="hidden" name="command" value="editFaculty" /> <input
				type="hidden" name="oldName" value="${requestScope.name}" />
			<div class="field">
				<label for="name"><fmt:message
						key="faculty.edit_jsp.label.name" /></label> <input type="text"
					name="name" value="${requestScope.name}" size="${fn:length(name)}"
					required />
			</div>
			<div class="field">
				<label for="total_seats"><fmt:message
						key="faculty.edit_jsp.label.total_seats" /></label> <input type="number"
					name="total_seats" value="${requestScope.total_seats}" min="1"
					max="127" required />
			</div>
			<div class="field">
				<label for="budget_seats"><fmt:message
						key="faculty.edit_jsp.label.budget_seats" /></label> <input type="number"
					name="budget_seats" value="${requestScope.budget_seats} " min="0"
					max="126" required />
			</div>
			<p>
				<label><fmt:message
						key="faculty.edit_jsp.label.preliminary_subjects" /></label>
			</p>

			<br>
			<c:if test="${not empty facultySubjects}">
				<c:forEach var="oldCheckedSubject" items="${facultySubjects}">
					<input type="hidden" name="oldCheckedSubjects"
						value="${oldCheckedSubject.id}" />
					<p>
						<input type="checkbox" name="subjects"
							value="${oldCheckedSubject.id}" checked />
						<c:out value="${oldCheckedSubject.name}"></c:out>
					</p>
				</c:forEach>
			</c:if>

			<c:if test="${not empty otherSubjects}">
				<c:forEach var="oldUncheckedSubject" items="${otherSubjects}">
					<p>
						<input type="checkbox" name="subjects"
							value="${oldUncheckedSubject.id}" />
						<c:out value="${oldUncheckedSubject.name}"></c:out>
					</p>
				</c:forEach>
			</c:if>
			<input type="submit"
				value="<fmt:message
							key="faculty.edit_jsp.button.submit" />" />
		</form>
	</div>
</body>
</html>