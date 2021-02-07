package com.football.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> handleException(Exception exception) {
		logger.error("Unexpected exception", exception);

		Map<String, Object> map = new HashMap<>();
		map.put("error", "Internal Server Error");
		map.put("cause", exception.getCause().getMessage());
		map.put("message", exception.getMessage());
		return map;
	}

}
