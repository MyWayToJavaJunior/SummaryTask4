<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
	You are in client apply for faculty view!

	<h1>${name}</h1>
	<ul>
		<li><label>Faculty name: </label> <c:out value="${name}"></c:out></li>
		<li><label>Total seats: </label> <c:out value="${total_seats}"></c:out></li>
		<li><label>Budget seats: </label> <c:out value="${budget_seats}"></c:out></li>
	</ul>

	<form action="controller" method="POST">
		<input type="hidden" name="command" value="applyFaculty" /> <input
			type="hidden" name="facultyId" value="${id}" /><input type="hidden"
			name="show" value="false" />

		<h1>Your preliminary marks</h1>

		<table id="preliminary">

			<c:if test="${empty facultySubjects}">
				<c:out value="Not needed"></c:out>
			</c:if>

			<c:forEach var="facultySubject" items="${facultySubjects}">
				<tr>
					<td><c:out value="${facultySubject.name}">${facultySubject.name}</c:out>
					</td>
					<td><input type="text" name="${facultySubject.id}_preliminary"
						value="" /></td>
				</tr>
			</c:forEach>

		</table>

		<h1>Your diploma marks</h1>

		<table id="diploma">

			<c:forEach var="subject" items="${allSubjects}">
				<tr>
					<td><c:out value="${subject.name}">${subject.name}</c:out></td>
					<td><input type="text" name="${subject.id}_diploma" value="" /></td>
				</tr>
			</c:forEach>

		</table>
		<p>
			<input type="submit" value="OK">
		</p>
	</form>
</body>
</html>