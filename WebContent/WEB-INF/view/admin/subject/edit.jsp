<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<form id="edit_subject" method="POST" action="controller">
		<input type="hidden" name="command" value="editSubject"> <input
			type="hidden" name="oldName" value="${name}"> <label
			for="name"><fmt:message key="subject.edit_jsp.label.name" /></label>
		<input type="text" name="name" value="${name}" required> <input
			type="submit"
			value="<fmt:message key="subject.edit_jsp.button.submit" />">
	</form>
	<a href="controller?command=viewSubject&name=${name}"><fmt:message
			key="subject.edit_jsp.button.back" /></a>
</body>
</html>