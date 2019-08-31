package pl.opensource.user.exception;

public class UserIsNotLoggedInException extends RuntimeException {
	public UserIsNotLoggedInException(final String username) {
		super(username);
	}
}
