package other.combinator.uservalidationsystem;

// Context: Represents a user to be validated
public class User {
	private String email;
	private String password;
	private int age;

	public User(String email, String password, int age) {
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "User{email='" + email + "', password='" + password + "', age=" + age + "}";
	}
}
