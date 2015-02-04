<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>Edit Profile</h1>

	<form id="profile" method="POST" action="controller">
		<input type="hidden" name="command" value="editProfile"> <input
			type="hidden" name="isBlocked" value="${requestScope.isBlocked}">
		<input type="hidden" name="oldEmail" value="${requestScope.email}">
		<div>
			<p>
				<fmt:message key="profile.edit_jsp.label.language" />
			</p>
			<select name="locale">
				<option value="ru">Russian</option>
				<option value="en">English</option>
			</select>
		</div>
		<br>

		<fieldset>
			<label for="first_name"><fmt:message
					key="profile.edit_jsp.label.first_name" /></label> <input
				name="first_name" type="text" value="${requestScope.first_name}" /><br>
			<label for="last_name"><fmt:message
					key="profile.edit_jsp.label.last_name" /></label> <input name="last_name"
				type="text" value="${requestScope.last_name}" /><br> <label
				for="email"><fmt:message key="profile.edit_jsp.label.email" /></label>
			<input name="email" type="text" value="${requestScope.email}" /> <br>
			<label for="password"><fmt:message
					key="profile.edit_jsp.label.password" /></label> <input name="password"
				type="password" value="${requestScope.password}" /> <input
				type="submit"
				value="<fmt:message key="profile.edit_jsp.button.update"/>" />
		</fieldset>
	</form>
</body>
</html>