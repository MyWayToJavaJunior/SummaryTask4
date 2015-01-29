<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<c:set var="title" value="Error" scope="page" />

<body>

	<table id="main-container">

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<h2 class="error">The following error occurred</h2> <%-- this way we get the error information (error 404)--%>
				<c:set var="code"
					value="${requestScope['javax.servlet.error.status_code']}" /> <c:set
					var="message"
					value="${requestScope['javax.servlet.error.message']}" /> <c:if
					test="${not empty code}">
					<h3>
						Error code:
						<c:out value="${code}" />
					</h3>
				</c:if> <c:if test="${not empty message}">
					<h3>
						<c:out value="${message}" />
					</h3>
				</c:if> <%-- if get this page using forward --%> <c:if
					test="${not empty errorMessage}">
					<p>
						<c:out value="${errorMessage}" />
					</p>
				</c:if> <%-- this way we print exception stack trace --%> <%
 	if (exception != null)
 		exception.printStackTrace(new PrintWriter(out));
 %> <%-- CONTENT --%>
			</td>
		</tr>

	</table>
</body>
</html>