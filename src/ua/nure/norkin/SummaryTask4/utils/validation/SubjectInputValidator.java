package ua.nure.norkin.SummaryTask4.utils.validation;

public class SubjectInputValidator {

	/**
	 * Validates user input for subject.
	 *
	 * @return <code>true</code> if parameters valid, <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateParameters(String subjectNameRu,
			String subjectNameEng) {
		if (!FieldValidation.isCyrillicWord(subjectNameRu)) {
			return false;
		}
		if (!FieldValidation.isLatinWord(subjectNameEng)) {
			return false;
		}
		return true;
	}
}
