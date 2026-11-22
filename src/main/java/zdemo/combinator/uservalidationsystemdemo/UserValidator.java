package zdemo.combinator.uservalidationsystemdemo;

import java.util.function.Function;

@FunctionalInterface
public interface UserValidator extends Function<User, ValidationResult> {

	default UserValidator and(UserValidator other) {
		return user -> {
			ValidationResult result = this.apply(user);
			if(result.isInvalid()) {
				return result;
			}
			
			return other.apply(user);
		};
	}
	
	default UserValidator or(UserValidator other) {
		return user -> {
			ValidationResult result = this.apply(user);
			if(result.isValid()) {
				return result;
			}
			
			return other.apply(user);
		};
	}

	static UserValidator isEmailValid() {
		return user -> user.getEmail().contains("@") ? ValidationResult.valid()
				: ValidationResult.invalid("Email invalid");
	}

	static UserValidator isPasswordStrong() {
		return user -> user.getPassword().length() >= 4 ? ValidationResult.valid()
				: ValidationResult.invalid("Password invalid");
	}
}
