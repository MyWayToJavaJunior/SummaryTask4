<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>Edit Profile</h1>

	<form id="profile" method="POST" action="controller">
		<input type="hidden" name="command" value="editProfile"> <input
			type="hidden" name="show" value="false"> <input type="hidden"
			name="oldEmail" value="${requestScope.email}">
		<div>
			<p>
				<fmt:message key="profile.edit_jsp.label.language" />
			</p>
			<select name="locale">
				<option value="ru">Russian</option>
				<option value="en">English</option>
			</select>
		</div>
		<fieldset>
			<label for="first_name"><fmt:message
					key="profile.edit_jsp.label.first_name" /></label> <input
				name="first_name" type="text" value="${requestScope.first_name}" />
			<label for="last_name"><fmt:message
					key="profile.edit_jsp.label.last_name" /></label> <input name="last_name"
				type="text" value="${requestScope.last_name}" /> <label for="email"><fmt:message
					key="profile.edit_jsp.label.email" /></label> <input name="email"
				type="text" value="${requestScope.email}" /> <label for="password"><fmt:message
					key="profile.edit_jsp.label.password" /></label> <input name="password"
				type="password" value="${requestScope.password}" /> <label
				for="city"><fmt:message key="profile.edit_jsp.label.city" /></label>
			<input name="city" type="text" value="${requestScope.city}" /> <label
				for="district"><fmt:message
					key="profile.edit_jsp.label.district" /></label> <input name="district"
				type="text" value="${requestScope.district}" /> <label for="school"><fmt:message
					key="profile.edit_jsp.label.school" /></label> <input name="school"
				type="text" value="${requestScope.school}" /> <input type="submit"
				value="<fmt:message key="profile.edit_jsp.button.update" />" />
		</fieldset>
	</form>
</body>
</html>