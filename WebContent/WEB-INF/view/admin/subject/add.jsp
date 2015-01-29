<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
	<form id="add_subject" action="controller" method="POST">
		<input type="hidden" name="command" value="addSubject" />
		 <input type="text" name="name" value="" />
		<p>
			<input type="submit" value="OK">
		</p>
	</form>
</html>