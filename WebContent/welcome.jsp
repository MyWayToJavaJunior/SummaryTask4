<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<h1>
		<fmt:message key="welcome_jsp.label.greeting" />
	</h1>
	<form id="login_form" action="controller" method="POST">
		<input type="hidden" name="command" value="login" />

		<table id="main-container">
			<tr>
				<td class="content center"><input type="hidden" name="command"
					value="login" />

					<fieldset>
						<legend>
							<fmt:message key="welcome_jsp.label.login" />
						</legend>
						<input type="text" name="email" required /><br />
					</fieldset> <br />
					<fieldset>
						<legend>
							<fmt:message key="welcome_jsp.label.password" />
						</legend>
						<input type="password" name="password" required />
					</fieldset> <br /> <input type="submit"
					value="<fmt:message key="welcome_jsp.button.login"/>"></td>
			</tr>
			<tr>
				<td colspan="2"><fmt:message
						key="welcome_jsp.label.not_registered_msg" /> <a
					href="controller?command=client_registration"><fmt:message
							key="welcome_jsp.label.register_here_msg" /></a></td>
			</tr>
		</table>
	</form>

	<c:if test="${not empty errorMessage}">
		<c:out value="${errorMessage}"></c:out>
	</c:if>

	<c:if test="${not empty successfulMessage}">
		<c:out value="${successfulMessage}"></c:out>
	</c:if>

</body>
</html>