package other.combinator.uservalidationsystem;

import java.util.function.Function;

// Combinator: Represents a validator function
@FunctionalInterface
public interface UserValidator extends Function<User, ValidationResult> {
	// Combine validators (logical AND)
	default UserValidator and(UserValidator other) {
		return user -> {
			ValidationResult result1 = this.apply(user);
			if (!result1.isValid()) {
				return result1;
			}
			return other.apply(user);
		};
	}

	// Static factory methods for common validators
	static UserValidator isEmailValid() {
		return user -> user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$") ? ValidationResult.valid()
				: ValidationResult.invalid("Invalid email format");
	}

	static UserValidator isPasswordStrong() {
		return user -> user.getPassword().length() >= 8 ? ValidationResult.valid()
				: ValidationResult.invalid("Password must be at least 8 characters");
	}

	static UserValidator isAdult() {
		return user -> user.getAge() >= 18 ? ValidationResult.valid()
				: ValidationResult.invalid("User must be at least 18 years old");
	}
}
