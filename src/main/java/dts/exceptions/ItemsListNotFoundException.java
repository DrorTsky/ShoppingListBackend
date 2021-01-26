package dts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ItemsListNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemsListNotFoundException() {
	}

	public ItemsListNotFoundException(String message) {
		super(message);
	}

	public ItemsListNotFoundException(Throwable cause) {
		super(cause);
	}

	public ItemsListNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}	
}
