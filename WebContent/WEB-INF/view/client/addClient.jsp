<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>
<%@ include file="/WEB-INF/view/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/view/jspf/header.jspf"%>
	<div class="header">
		<fmt:message key="registration.label.enter_info_msg" />
	</div>
	<br>
	<br>

	<div class="welcomeform">
		<form id="registration_form" method="POST" action="controller"
			onsubmit="return validate(this);">
			<input type="hidden" name="command" value="client_registration" />

			<div class="field">
				<label for="lang"> <fmt:message
						key="registration.label.language" />
				</label> <select name="lang" required>
					<option value="ru">Russian</option>
					<option value="en">English</option>
				</select>
			</div>

			<div class="field">
				<label for="first_name"> <fmt:message
						key="registration.label.first_name" /></label> <input type="text"
					name="first_name" value="" required />
			</div>
			<div class="field">
				<label for="last_name"> <fmt:message
						key="registration.label.last_name" /></label> <input type="text"
					name="last_name" value="" required />
			</div>
			<div class="field">
				<label for="email"> <fmt:message
						key="registration.label.email" /></label> <input type="text" name="email"
					value="" required />
			</div>
			<div class="field">
				<label for="password"> <fmt:message
						key="registration.label.password" /></label> <input type="password"
					name="password" value="" required />
			</div>
			<div class="field">
				<label for="town"> <fmt:message
						key="registration.label.city" /></label> <input type="text" name="city"
					value="" required />
			</div>
			<div class="field">
				<label for="district"> <fmt:message
						key="registration.label.district" /></label> <input type="text"
					name="district" value="" required />
			</div>
			<div class="field">
				<label for="school"> <fmt:message
						key="registration.label.school" /></label> <input type="text"
					name="school" value="" required />
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
	<script>
		function validate() {
			var first_name = document.getElementById("first_name");
			var last_name = document.getElementById("last_name");
			var email = document.getElementById("email");
			var password = document.getElementById("password");
			var city = document.getElementById("city");
			var district = document.getElementById("district");
			var school = document.getElementById("school");

			var valid = true;

			if (first_name.value.length <= 0 || last_name.value.length <= 0) {
				alert("Enter your name and surname!");
			}
			if (email.value.length <= 0 || password.value.length <= 0) {
				alert("Enter your email and password!");
				valid = false;
			}
			if (city.value.length <= 0 || district.value.length <= 0
					|| school.value.length <= 0) {
				alert("Enter your geo data!");
				valid = false;
			}
			return valid;
		};
	</script>
</body>
</html>