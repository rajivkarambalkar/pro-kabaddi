package com.kabaddi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		this("Resource not found!");
	}

	public ValidationException(String message) {
		this(message, null);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
