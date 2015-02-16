<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>

	<div class="form">
		<form id="add_faculty" action="controller" method="POST" onsubmit="return validate();">
			<input type="hidden" name="command" value="addFaculty" />
			<div class="field">
				<label for="name_ru"><fmt:message
						key="faculty.add_jsp.label.name" /> (ru)</label> <input type="text"
					name="name_ru" id="name_ru" value="" required />
			</div>
			<div class="field">
				<label for="name_eng"><fmt:message
						key="faculty.add_jsp.label.name" /> (eng)</label> <input type="text"
					name="name_eng" id="name_eng" value="" required />
			</div>
			<div class="field">
				<label for="total_seats"><fmt:message
						key="faculty.add_jsp.label.total_seats" /></label> <input type="number"
					name="total_seats" id="total_seats" value="" min="1" max="127" step="1" required />
			</div>
			<div class="field">
				<label for="budget_seats"><fmt:message
						key="faculty.add_jsp.label.budget_seats" /></label> <input type="number"
					name="budget_seats" id="budget_seats" value="" min="0" max="126" step="1" required />
			</div>
			<p>
				<label><fmt:message
						key="faculty.add_jsp.label.preliminary_subjects" /></label>
			</p>
			<br>
			<c:forEach var="subject" items="${allSubjects}">
				<p>
					<input type="checkbox" name="subjects" value="${subject.id}" />
					<c:out value="${lang eq 'ru' ? subject.nameRu : subject.nameEng}"></c:out>
				</p>
			</c:forEach>
			<p>
				<input type="submit"
					value="<fmt:message key="faculty.add_jsp.button.submit" />">
			</p>
		</form>


		<a href="controller?command=viewAllFaculties"><fmt:message
				key="faculty.add_jsp.button.back" /></a>
	</div>

	<script src="script/faculty-validation.js"></script>
</body>
</html>