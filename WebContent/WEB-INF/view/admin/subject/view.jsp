<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="view">
		<p>
			<label><fmt:message key="subject.view_jsp.label.name" /> </label>
			<c:out value="${language eq 'ru' ? name_ru : name_eng}"></c:out>
		</p>
		<a href="controller?command=editSubject&name_eng=${name_eng}"><fmt:message
				key="subject.view_jsp.button.edit" /></a> <br> <br>
		<form id="delete_subject" action="controller" method="POST">
			<input type="hidden" name="command" value="deleteSubject" /><input
				type="hidden" name="id" value="${id}" /><input type="submit"
				value="<fmt:message key="subject.view_jsp.button.delete" />" />
		</form>
	</div>

</body>
</html>