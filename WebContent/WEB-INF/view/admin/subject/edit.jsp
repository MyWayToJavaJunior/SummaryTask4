<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
	<form id="edit_subject" method="POST" action="controller">
		<input type="hidden" name="command" value="editSubject"> <input
			type="hidden" name="oldName" value="${name}">
			<input
			type="hidden" name="show" value="false">
			 <label>Subject
			name</label> <input type="text" name="name" value="${name}">
		<input type="submit" value="OK">
	</form>
	<a href="controller?command=viewSubject&name=${name}">Back
		to view</a>
</html>