package zdemo.combinator.uservalidationsystemdemo;

public class ZMain {

	public static void main(String[] args) {
		User user1 = new User("email@gmail.com", "1234");
		User user2 = new User("emailgmail.com", "1234");
		User user3 = new User("email@gmail.com", "123");
		
		UserValidator userValidator = UserValidator.isEmailValid().and(UserValidator.isPasswordStrong());
		
		System.out.println(userValidator.apply(user1));
		System.out.println(userValidator.apply(user2));
		System.out.println(userValidator.apply(user3));
	}
}
