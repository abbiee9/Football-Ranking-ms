package com.football.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler({ Exception.class, HttpClientErrorException.class })
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Exception caught: ", ex);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
	}

}
