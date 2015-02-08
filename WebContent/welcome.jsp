<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="header">
		<fmt:message key="welcome_jsp.label.greeting" />
	</div>
	<br><br>

	<div class="welcomeform">
		<form id="login_form" action="controller" method="POST">
			<input type="hidden" name="command" value="login" />
			<div class="field">
				<label> <fmt:message key="welcome_jsp.label.login" />
				</label> <input type="text" name="email" required />
			</div>
			<br>
			<div class="field">
				<label> <fmt:message key="welcome_jsp.label.password" />
				</label> <input type="password" name="password" required />
			</div>
			<div class="field">
				<input type="submit"
					value="<fmt:message key="welcome_jsp.button.login"/>">
			</div>


			<div class="field">
				<fmt:message key="welcome_jsp.label.not_registered_msg" />
				<a href="controller?command=client_registration"><fmt:message
						key="welcome_jsp.label.register_here_msg" /></a>
			</div>
		</form>

	</div>
	<c:if test="${not empty errorMessage}">
		<c:out value="${errorMessage}"></c:out>
	</c:if>

	<c:if test="${not empty successfulMessage}">
		<c:out value="${successfulMessage}"></c:out>
	</c:if>

</body>
</html>