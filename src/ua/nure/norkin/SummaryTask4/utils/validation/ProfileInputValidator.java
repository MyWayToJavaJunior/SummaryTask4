package ua.nure.norkin.SummaryTask4.utils.validation;

public class ProfileInputValidator {
	/**
	 * Validates user input for faculty.
	 *
	 * @return <code>true</code> if parameters valid, <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateUserParameters(String firstName,
			String lastName, String email, String password, String lang) {
		return FieldValidation.isFilled(firstName, lastName, lang)
				&& (!email.isEmpty() && email.contains("@"))
				&& (password.length() >= 4);
	}

	public static boolean validateEntrantParameters(String city,
			String district, String school) {
		return FieldValidation.isFilled(city, district) && (!school.isEmpty());
	}

}
