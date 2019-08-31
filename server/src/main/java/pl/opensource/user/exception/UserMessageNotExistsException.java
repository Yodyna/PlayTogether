package pl.opensource.user.exception;

public class UserMessageNotExistsException extends RuntimeException {
	public UserMessageNotExistsException(final String message) {
		super(message);
	}
}
