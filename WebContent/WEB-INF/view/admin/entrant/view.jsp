<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<ul>
		<li><label><fmt:message
					key="profile.view_jsp.label.first_name" /> <c:out
					value="${requestScope.first_name}"></c:out></label></li>
		<li><label><fmt:message
					key="profile.view_jsp.label.last_name" /> <c:out
					value="${requestScope.last_name}"></c:out></label></li>
		<li><label><fmt:message
					key="profile.view_jsp.label.email" /> <c:out
					value="${requestScope.email}"></c:out></label></li>
		<li><label><fmt:message key="profile.view_jsp.label.city" />
				<c:out value="${requestScope.city}"></c:out></label></li>
		<li><label><fmt:message
					key="profile.view_jsp.label.district" /> <c:out
					value="${requestScope.district}"></c:out></label></li>
		<li><label><fmt:message
					key="profile.view_jsp.label.school" /> <c:out
					value="${requestScope.school}"></c:out></label></li>

		<li><label>Blocked status <c:out
					value="${requestScope.isBlocked}"></c:out></label></li>
	</ul>

	<form action="controller" method="POST">
		<input type="hidden" name="command" value="viewEntrant"> <input
			type="hidden" name="id" value="${requestScope.id}"> <input
			type="submit" value="Change blocked status!">
	</form>

</body>
</html>