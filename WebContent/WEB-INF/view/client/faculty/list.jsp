<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	You are in client faculties list!
	<h1>Faculties list</h1>

	<ul>
		<c:forEach var="faculty" items="${faculties}">
			<li><a
				href="<c:url value="controller?command=viewFaculty"> <c:param name="name" value="${faculty.name}"/></c:url>">${faculty.name}</a>
			</li>
		</c:forEach>
	</ul>
</body>
</html>