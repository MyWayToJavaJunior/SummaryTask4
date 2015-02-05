<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<h2 align="center">
		<fmt:message key="faculty.list_jsp.label.faculties_list" />
	</h2>
	<p>
		<a href="controller?command=addFaculty"><fmt:message
				key="faculty.list_jsp.button.add" /></a>
	</p>
	<table id="facultiesTable" class="display">
		<thead>
			<tr>
				<td><fmt:message key="faculty.list_jsp.label.name" /></td>
				<td><fmt:message key="faculty.list_jsp.label.total_seats" /></td>
				<td><fmt:message key="faculty.list_jsp.label.budget_seats" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="faculty" items="${faculties}">
				<tr>
					<td><a
						href="<c:url value="controller?command=viewFaculty"> <c:param name="name" value="${faculty.name}"/></c:url>"><c:out
								value="${faculty.name}"></c:out></a></td>
					<td><c:out value="${faculty.totalSeats}"></c:out></td>
					<td><c:out value="${faculty.budgetSeats}"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>