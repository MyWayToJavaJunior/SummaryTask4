<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<h2 align="center">
		<c:out value="${faculty.name}" />
	</h2>

	<div class="form">
		<form id="edit_faculty" action="controller" method="POST" onsubmit="return validate();">
			<input type="hidden" name="command" value="editFaculty" /> <input
				type="hidden" name="oldName" value="${requestScope.name_eng}" />
			<div class="field">
				<label for="name_ru"><fmt:message
						key="faculty.edit_jsp.label.name" /></label> <input type="text"
					name="name_ru" id="name_ru" value="${requestScope.name_ru}"
					required />
			</div>
			<div class="field">
				<label for="name_eng"><fmt:message
						key="faculty.edit_jsp.label.name" /></label> <input type="text"
					name="name_eng" id="name_eng" value="${requestScope.name_eng}"
					required />
			</div>
			<div class="field">
				<label for="total_seats"><fmt:message
						key="faculty.edit_jsp.label.total_seats" /></label> <input type="number"
					name="total_seats" id="total_seats"
					value="${requestScope.total_seats}" min="1" max="127" step="1"
					required />
			</div>
			<div class="field">
				<label for="budget_seats"><fmt:message
						key="faculty.edit_jsp.label.budget_seats" /></label> <input type="number"
					name="budget_seats" id="budget_seats"
					value="${requestScope.budget_seats}" min="0" max="126" step="1"
					required />
			</div>
			<p>
				<a
					href="controller?command=viewFaculty&name_eng=${requestScope.name_eng}"><fmt:message
						key="profile.edit_jsp.button.back" /></a>
			</p>
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
						<c:out
							value="${lang eq 'ru' ? oldCheckedSubject.nameRu : oldCheckedSubject.nameEng}"></c:out>
					</p>
				</c:forEach>
			</c:if>

			<c:if test="${not empty otherSubjects}">
				<c:forEach var="oldUncheckedSubject" items="${otherSubjects}">
					<p>
						<input type="checkbox" name="subjects"
							value="${oldUncheckedSubject.id}" />
						<c:out
							value="${lang eq 'ru' ? oldUncheckedSubject.nameRu : oldUncheckedSubject.nameEng}"></c:out>
					</p>
				</c:forEach>
			</c:if>
			<input type="submit"
				value="<fmt:message
							key="faculty.edit_jsp.button.submit" />" />
		</form>
	</div>
	<script src="script/faculty-validation.js"></script>
</body>
</html>