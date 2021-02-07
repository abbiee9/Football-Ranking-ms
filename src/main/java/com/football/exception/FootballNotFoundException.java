package com.football.exception;

public class FootballNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -541262783875534055L;

	public FootballNotFoundException(String ex) {
		super(ex);
	}

	public FootballNotFoundException() {
	}
}
