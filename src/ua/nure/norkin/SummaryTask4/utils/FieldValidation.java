package ua.nure.norkin.SummaryTask4.utils;

public class FieldValidation {

	public static boolean isFilled(String... values) {
		for (String value : values) {
			if (value == null || value.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String... values) {

		if (!isFilled(values)) {
			return false;
		}

		for (String value : values) {
			for (char digit : value.toCharArray()) {
				if (!Character.isDigit(digit)) {
					return false;
				}
			}
		}
		return true;
	}
}
