<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>Add new faculty</h1>
	<form id="add_faculty" action="controller" method="POST">
		<input type="hidden" name="command" value="addFaculty" /> <label
			for="name">Faculty name:</label> <input type="text" name="name"
			value="" /> <label for="total_seats">Total seats:</label> <input
			type="text" name="total_seats" value="" /> <label for="budget_seats">Budget
			seats:</label> <input type="text" name="budget_seats" value="" /> <br>
		<label>Preliminary subjects:</label> <br>

		<c:forEach var="subject" items="${allSubjects}">
			<input type="checkbox" name="subjects" value="${subject.name}" /> ${subject.name}<br>
		</c:forEach>
		<p>
			<input type="submit" value="OK">
		</p>
	</form>

	<a href="controller?command=viewAllFaculties">View All Faculties</a>

</body>
</html>