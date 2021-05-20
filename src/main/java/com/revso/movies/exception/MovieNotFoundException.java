package com.revso.movies.exception;

public class MovieNotFoundException extends NoSuchFieldException {

	private static final long serialVersionUID = 3708425231096667041L;
	
	public MovieNotFoundException(String message) {
		super(message);
	}
}
