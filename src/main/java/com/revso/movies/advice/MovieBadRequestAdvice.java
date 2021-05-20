package com.revso.movies.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revso.movies.exception.MovieBadRequestException;

@ControllerAdvice
public class MovieBadRequestAdvice {
	
	@ExceptionHandler(MovieBadRequestException.class)
	public ResponseEntity<?> handlerNotFoundException(MovieBadRequestException mbrEx, HttpServletRequest http) {
		String location = http.getRequestURL().toString();
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.header(HttpHeaders.LOCATION, location)
				.body(mbrEx.getMessage());
	}
}
