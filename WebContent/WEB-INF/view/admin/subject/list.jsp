<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<div class="view">
		<h2>
			<fmt:message key="subject.list_jsp.label.subjects_list" />
		</h2>
		<ol>
			<c:forEach var="subject" items="${allSubjects}">
				<li><a
					href="<c:url value="controller?command=viewSubject"> <c:param name="name" value="${subject.name}"/></c:url>">
						<c:out value="${subject.name}"></c:out>
				</a></li>
			</c:forEach>
		</ol>
		<a href="controller?command=addSubject"><fmt:message
				key="subject.list_jsp.button.add" /></a>
	</div>
</body>
</html>