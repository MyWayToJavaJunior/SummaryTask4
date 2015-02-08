<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="view">
		<p>
			<label><fmt:message key="profile.view_jsp.label.first_name" />
			</label>
			<c:out value="${requestScope.first_name}"></c:out>
		</p>
		<p>
			<label><fmt:message key="profile.view_jsp.label.last_name" />
			</label>
			<c:out value="${requestScope.last_name}"></c:out>
		</p>
		<p>
			<label><fmt:message key="profile.view_jsp.label.email" /> </label>
			<c:out value="${requestScope.email}"></c:out>
		</p>
		<a href="controller?command=editProfile&user=${requestScope.email}"><fmt:message
				key="profile.view_jsp.button.edit" /></a> <br>
		<p>
			<a href="controller?command=admin_registration"><fmt:message
					key="profile.view_jsp.label.register_new_admin" /></a>
		</p>
		<br>
		<form action="controller" method="POST">
			<input type="hidden" name="command" value="logout"> <input
				type="submit"
				value="<fmt:message key="profile.view_jsp.button.logout" />">
		</form>

	</div>
</body>
</html>