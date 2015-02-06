<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<div class="form">
		<form id="registration_form" method="POST" action="controller">
			<input type="hidden" name="command" value="admin_registration" />

			<h2>
				<fmt:message key="registration.label.enter_info_msg" />
			</h2>

			<div class="field">
				<label for=""lang""> <fmt:message
						key="registration.label.language" />
				</label> <select name="lang">
					<option value="ru">Russian</option>
					<option value="en">English</option>
				</select>
			</div>
			<div class="field">
				<fmt:message key="registration.label.first_name" />
				<input type="text" name="first_name" value="" required />
			</div>
			<div class="field">
				<fmt:message key="registration.label.last_name" />
				<input type="text" name="last_name" value="" required />
			</div>
			<div class="field">
				<fmt:message key="registration.label.email" />
				<input type="text" name="email" value="" required />
			</div>
			<div class="field">
				<fmt:message key="registration.label.password" />
				<input type="password" name="password" value="" required />
			</div>

			<div class="field">
				<input type="reset"
					value="<fmt:message
					key="registration.button.reset" />" />
			</div>
			<div class="field">
				<input type="submit"
					value="<fmt:message
					key="registration.button.submit" />" />
			</div>
			<div class="field">
				<fmt:message key="registration.label.alredy_registered_msg" />
				<a href="welcome.jsp"><fmt:message
						key="registration.label.login_here_msg" /></a>
			</div>
		</form>
	</div>
</body>
</html>