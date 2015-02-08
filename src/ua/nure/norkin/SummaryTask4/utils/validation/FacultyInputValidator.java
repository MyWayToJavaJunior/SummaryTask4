package ua.nure.norkin.SummaryTask4.utils.validation;

public class FacultyInputValidator {

	/**
	 * Validates user input for faculty.
	 *
	 * @return <code>true</code> if parameters valid, <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateParameters(String facultyNameRu,
			String facultyNameEng, String facultyBudgetSeats,
			String facultyTotalSeats) {
		if (!FieldValidation.isCyrillicWord(facultyNameRu)
				|| !FieldValidation.isLatinWord(facultyNameEng)) {
			return false;
		}

		if (!FieldValidation.isPositiveDecimalNumber(facultyBudgetSeats,
				facultyTotalSeats)) {
			return false;
		}

		if (!FieldValidation.isPositiveByte(Long.valueOf(facultyBudgetSeats),
				Long.valueOf(facultyTotalSeats))) {
			return false;
		}

		Byte budget = Byte.valueOf(facultyBudgetSeats);
		Byte total = Byte.valueOf(facultyTotalSeats);

		if (!FieldValidation.checkBudgetLowerTotal(budget, total)) {
			return false;
		}

		return true;
	}
}
