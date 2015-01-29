<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	You are in admin faculty view!
	<h1>${requestScope.name}</h1>
	<ul>
		<li><label><fmt:message key="faculty.view_jsp.label.name" /></label>
			<c:out value="${name}"></c:out></li>
		<li><label><fmt:message
					key="faculty.view_jsp.label.total_seats" /></label> <c:out
				value="${total_seats}"></c:out></li>
		<li><label><fmt:message
					key="faculty.view_jsp.label.budget_seats" /></label> <c:out
				value="${budget_seats}"></c:out></li>
		<li><label><fmt:message
					key="faculty.view_jsp.label.preliminary_subjects" /></label></li>

		<c:if test="${empty facultySubjects}">
			<fmt:message key="faculty.view_jsp.label.no_subjects_msg" />
		</c:if>

		<c:if test="${not empty facultySubjects}">
			<c:forEach var="subject" items="${facultySubjects}" varStatus="item">
				<a
					href="<c:url value="controller?command=viewSubject"> <c:param name="name" value="${subject.name}"/></c:url>"><c:out
						value="${item.index + 1} ${subject.name}"></c:out></a>
				<br>
			</c:forEach>
		</c:if>
	</ul>
	<a
		href="controller?command=editFaculty&show=true&name=${requestScope.name}"><fmt:message
			key="faculty.view_jsp.button.edit" /></a>

</body>
</html>