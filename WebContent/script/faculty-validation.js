var name_ru = document.getElementById("name_ru");

name_ru.onkeypress = function(e) {
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[А-Яа-я ]/.test(String.fromCharCode(e.charCode)));
}


var name_eng = document.getElementById("name_eng");

name_eng.onkeypress = function(e) {
	if (e.keyCode == 8 || e.keyCode == 46 || e.metaKey || e.keyCode == 37
			|| e.keyCode == 39) {
		return;
	}
	return (/[a-zA-Z ]/.test(String.fromCharCode(e.charCode)));
}