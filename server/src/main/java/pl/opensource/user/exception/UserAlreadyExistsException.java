package pl.opensource.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(final String username) {
		super(username);
	}
}
