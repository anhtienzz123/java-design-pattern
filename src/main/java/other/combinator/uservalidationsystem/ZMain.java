package other.combinator.uservalidationsystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create users
		User validUser = new User("john.doe@example.com", "secure1234", 25);
		User invalidUser = new User("invalid.email", "short", 16);

		// Combine validators
		UserValidator validator = UserValidator.isEmailValid().and(UserValidator.isPasswordStrong())
				.and(UserValidator.isAdult());

		// Validate users
		System.out.println("Validating valid user: " + validUser);
		ValidationResult result1 = validator.apply(validUser);
		System.out.println("Result: " + result1);

		System.out.println("\nValidating invalid user: " + invalidUser);
		ValidationResult result2 = validator.apply(invalidUser);
		System.out.println("Result: " + result2);

		// Test partial validator
		UserValidator emailOnly = UserValidator.isEmailValid();
		System.out.println("\nValidating email only for invalid user: " + invalidUser);
		ValidationResult result3 = emailOnly.apply(invalidUser);
		System.out.println("Result: " + result3);
		
//		== Output:
//		Validating valid user: User{email='john.doe@example.com', password='secure1234', age=25}
//		Result: Valid
//
//		Validating invalid user: User{email='invalid.email', password='short', age=16}
//		Result: Invalid: Invalid email format
//
//		Validating email only for invalid user: User{email='invalid.email', password='short', age=16}
//		Result: Invalid: Invalid email format
	}
}
