package dts.exceptions;

public class UserNotPlayerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotPlayerException() {
	}

	public UserNotPlayerException(String message) {
		super(message);
	}

	public UserNotPlayerException(Throwable cause) {
		super(cause);
	}

	public UserNotPlayerException(String message, Throwable cause) {
		super(message, cause);
	}	
}
