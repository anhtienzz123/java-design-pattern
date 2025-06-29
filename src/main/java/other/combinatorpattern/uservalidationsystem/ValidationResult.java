package other.combinatorpattern.uservalidationsystem;

// Represents the result of a validation
public class ValidationResult {
	private final boolean isValid;
	private final String errorMessage;

	private ValidationResult(boolean isValid, String errorMessage) {
		this.isValid = isValid;
		this.errorMessage = errorMessage;
	}

	public static ValidationResult valid() {
		return new ValidationResult(true, "");
	}

	public static ValidationResult invalid(String errorMessage) {
		return new ValidationResult(false, errorMessage);
	}

	public boolean isValid() {
		return isValid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return isValid ? "Valid" : "Invalid: " + errorMessage;
	}
}
