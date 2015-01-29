<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<form id="registration_form" method="POST" action="conroller">
		<input type="hidden" name="command" value="admin_registration" />
		<!-- width="30%" cellpadding="5" -->
		<table border="1">
			<thead>
				<tr>
					<th colspan="2"><fmt:message
							key="registration.label.enter_info_msg" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><fmt:message key="registration.label.first_name" /></td>
					<td><input type="text" name="first_name" value="" /></td>
				</tr>
				<tr>
					<td><fmt:message key="registration.label.last_name" /></td>
					<td><input type="text" name="last_name" value="" /></td>
				</tr>
				<tr>
					<td><fmt:message key="registration.label.email" /></td>
					<td><input type="text" name="email" value="" /></td>
				</tr>
				<tr>
					<td><fmt:message key="registration.label.password" /></td>
					<td><input type="password" name="password" value="" /></td>
				</tr>
				<tr>
					<td><input type="reset"
						value="<fmt:message
					key="registration.button.reset" />" /></td>
					<td><input type="submit"
						value="<fmt:message
					key="registration.button.submit" />" /></td>
				</tr>
				<tr>
					<td colspan="2"><fmt:message
							key="registration.label.alredy_registered_msg" /><a
						href="welcome.jsp"><fmt:message
								key="registration.label.login_here_msg" /></a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>