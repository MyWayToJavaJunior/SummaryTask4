var first_name = document.getElementById("first_name");

first_name.onkeypress = function(e) {
	// allow delete, backspace, arrow left and right, ctrl, alt and shift
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-яa-zA-Z ]/.test(String.fromCharCode(e.charCode)));
}

var last_name = document.getElementById("last_name");

last_name.onkeypress = function(e) {
	// allow delete, backspace, arrow left and right, ctrl, alt and shift
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-яa-zA-Z ]/.test(String.fromCharCode(e.charCode)));
}

var school = document.getElementById("school");

school.onkeypress = function(e) {
	// allow delete, backspace, arrow left and right, ctrl, alt and shift
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-яa-zA-Z-0-9 ]/.test(String.fromCharCode(e.charCode)));
}

var district = document.getElementById("district");

district.onkeypress = function(e) {
	// allow delete, backspace, arrow left and right, ctrl, alt and shift
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-яa-zA-Z- ]/.test(String.fromCharCode(e.charCode)));
}

var city = document.getElementById("city");

city.onkeypress = function(e) {
	// allow delete, backspace, arrow left and right, ctrl, alt and shift
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-яa-zA-Z- ]/.test(String.fromCharCode(e.charCode)));
}
