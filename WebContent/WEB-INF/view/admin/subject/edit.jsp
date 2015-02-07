<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="form">
		<form id="edit_subject" method="POST" action="controller">
			<input type="hidden" name="command" value="editSubject"> <input
				type="hidden" name="oldName" value="${name_eng}">

			<div class="field">
				<p>
					<label for="name_ru"><fmt:message
							key="subject.edit_jsp.label.name" /> (ru)</label> <input type="text"
						name="name_ru" id="name_ru" value="${name_ru}" required>
				</p>
			</div>
			<div class="field">
				<p>
					<label for="name_eng"><fmt:message
							key="subject.edit_jsp.label.name" /> (eng)</label> <input type="text"
						name="name_eng" id="name_eng" value="${name_eng}" required>
				</p>
			</div>
			<p>
				<input type="submit"
					value="<fmt:message key="subject.edit_jsp.button.submit" />">
			</p>
		</form>

		<p>
			<a href="controller?command=viewSubject&name_eng=${name_eng}"><fmt:message
					key="subject.edit_jsp.button.back" /></a>
		</p>
	</div>
	<script src="/script/subject-validation.js"></script>
</body>
</html>