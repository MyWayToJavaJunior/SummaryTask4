package ua.nure.norkin.SummaryTask4.utils.validation;

/**
 * Class with utility methods for checking input values send from the user.
 *
 * @author Mark Norkin
 *
 */
public class FieldValidation {

	private static final String positiveDecimalNumberRegEx = "\\d+";
	private static final String filledRegex = "^\\p{L}[\\p{L}\\s]*\\p{L}$";
	private static final String isLatinWord = "[a-zA-Z ]+";
	private static final String isCyrillicWord = "[а-яА-Я ]+";

	private static <T> boolean checkNull(
			@SuppressWarnings("unchecked") T... values) {
		if (values == null) {
			return true;
		} else {
			for (T value : values) {
				if (value == null) {
					return true;
				}
			}
			return false;
		}
	}

	public static boolean isFilled(String... values) {

		if (checkNull(values)) {
			return false;
		}

		for (String value : values) {

			if (!value.matches(filledRegex)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isCyrillicWord(String... values) {

		if (checkNull(values)) {
			return false;
		}

		for (String value : values) {
			if (!value.matches(isCyrillicWord)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLatinWord(String... values) {

		if (checkNull(values)) {
			return false;
		}

		for (String value : values) {
			if (!value.matches(isLatinWord)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isPositiveDecimalNumber(String... values) {

		if (checkNull(values)) {
			return false;
		}

		for (String value : values) {
			if (!value.matches(positiveDecimalNumberRegEx)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isPositiveByte(Number... values) {

		if (checkNull(values)) {
			return false;
		}

		for (Number value : values) {
			Long longValue = value.longValue();

			if (longValue.compareTo((long) 0) < 0
					|| longValue.compareTo((long) Byte.MAX_VALUE) > 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkBudgetLowerTotal(byte budget, byte total) {
		return budget < total;
	}
}
