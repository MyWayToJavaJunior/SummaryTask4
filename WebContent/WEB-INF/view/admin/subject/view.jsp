<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="view">
		<label><fmt:message key="subject.view_jsp.label.name" /></label>
		<c:out value="${name}"></c:out>
		<br> <a href="controller?command=editSubject&name=${name}"><fmt:message
				key="subject.view_jsp.button.edit" /></a>
	</div>
</body>
</html>