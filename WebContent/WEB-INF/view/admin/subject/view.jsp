<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
	<label>Subject name: <c:out value="${name}"></c:out></label><br>
	<a href="controller?command=editSubject&show=true&name=${name}">Edit</a>
</html>