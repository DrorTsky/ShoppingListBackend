package dts.exceptions;

public class UserNotManagerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotManagerException() {
	}

	public UserNotManagerException(String message) {
		super(message);
	}

	public UserNotManagerException(Throwable cause) {
		super(cause);
	}

	public UserNotManagerException(String message, Throwable cause) {
		super(message, cause);
	}	

}
