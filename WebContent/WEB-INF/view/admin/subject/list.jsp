<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<c:forEach var="subject" items="${allSubjects}" varStatus="item">
		<li><a
			href="<c:url value="controller?command=viewSubject"> <c:param name="name" value="${subject.name}"/></c:url>">${item.index} ${subject.name}</a></li>
	</c:forEach>
	<a href="controller?command=addSubject"></a>
</html>