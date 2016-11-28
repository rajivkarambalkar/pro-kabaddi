package com.kabaddi.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(Exception ex) {
		ex.printStackTrace();
		Locale locale = LocaleContextHolder.getLocale();
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(messageSource.getMessage("http.error.500", null, locale));
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.OK);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(ResourceNotFoundException ex) {
		ex.printStackTrace();
		Locale locale = LocaleContextHolder.getLocale();
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(messageSource.getMessage("http.error.404", null, locale));
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(ValidationException ex) {
		ex.printStackTrace();
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getLocalizedMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.OK);
	}

}
