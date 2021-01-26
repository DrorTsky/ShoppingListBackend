package dts.exceptions;

public class UserNotAdminException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotAdminException() {
	}

	public UserNotAdminException(String message) {
		super(message);
	}

	public UserNotAdminException(Throwable cause) {
		super(cause);
	}

	public UserNotAdminException(String message, Throwable cause) {
		super(message, cause);
	}	
}
