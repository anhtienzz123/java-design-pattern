package zdemo.combinator.uservalidationsystemdemo;

import lombok.Data;

@Data
public class ValidationResult {

	private boolean isValid;
	private String errorMessage;
	
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
	
	public boolean isInvalid() {
		return !isValid;
	}
}
