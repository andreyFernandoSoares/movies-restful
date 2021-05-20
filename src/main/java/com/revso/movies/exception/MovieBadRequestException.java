package com.revso.movies.exception;

public class MovieBadRequestException extends Exception {

	private static final long serialVersionUID = -5480476676536693074L;
	
	public MovieBadRequestException(String message) {
		super(message);
	}
}
