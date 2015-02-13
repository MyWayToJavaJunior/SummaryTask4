<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h2 align="center">
		<c:out value="${language eq 'ru' ? name_ru : name_eng}"></c:out>
	</h2>
	<div class="view">
		<p>
			<label><fmt:message key="faculty.view_jsp.label.name" /></label>
			<c:out value="${language eq 'ru' ? name_ru : name_eng}"></c:out>
		</p>
		<p>
			<label><fmt:message key="faculty.view_jsp.label.total_seats" /></label>
			<c:out value="${total_seats}"></c:out>
		</p>
		<p>
			<label><fmt:message key="faculty.view_jsp.label.budget_seats" /></label>
			<c:out value="${budget_seats}"></c:out>
		</p>
		<p>
			<label><fmt:message
					key="faculty.view_jsp.label.preliminary_subjects" /></label>
		</p>

		<c:if test="${empty facultySubjects}">
			<fmt:message key="faculty.view_jsp.label.no_subjects_msg" />
		</c:if>

		<br>
		<c:if test="${not empty facultySubjects}">
			<ol>
				<c:forEach var="subject" items="${facultySubjects}">
					<li><c:out
							value="${language eq 'ru' ? subject.nameRu : subject.nameEng}"></c:out></li>
				</c:forEach>
			</ol>
		</c:if>
		<c:if test="${userRole eq 'client' }">
		<p>
			<a href="controller?command=applyFaculty&name_eng=${name_eng}"><fmt:message
					key="faculty.view_jsp.button.apply" /></a>
		</p></c:if>
	</div>
</body>
</html>