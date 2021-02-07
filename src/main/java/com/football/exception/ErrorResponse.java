package com.football.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private HttpStatus status;
	private String message;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RestApiError [status=").append(status).append(", message=").append(message).append("]");
		return builder.toString();
	}

}
