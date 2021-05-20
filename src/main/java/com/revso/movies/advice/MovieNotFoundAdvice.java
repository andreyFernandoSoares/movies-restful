package com.revso.movies.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revso.movies.exception.MovieNotFoundException;

@ControllerAdvice
public class MovieNotFoundAdvice {
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<?> handlerNotFoundException(MovieNotFoundException mnfEx, HttpServletRequest http) {
		String location = http.getRequestURL().toString();
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.header(HttpHeaders.LOCATION, location)
				.body(mnfEx.getMessage());
	}
}
