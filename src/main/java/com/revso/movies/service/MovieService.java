package com.revso.movies.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.exception.MovieBadRequestException;
import com.revso.movies.exception.MovieNotFoundException;
import com.revso.movies.model.Movie;

@Component
public interface MovieService {
	
	public List<Movie> findAll() throws MovieBadRequestException;
	
	public Movie findById(Long id) throws MovieBadRequestException, MovieNotFoundException;
	
	public List<Movie> findByCategory(CategoryEnum category) throws MovieBadRequestException;
	
	public List<Movie> findByName(String name) throws MovieBadRequestException;
	
	public Movie save(Movie movie) throws MovieBadRequestException;
	
	public void delete(Long id) throws MovieBadRequestException, MovieNotFoundException;
	
	public Movie alter(Long id, Movie movie) throws MovieBadRequestException, MovieNotFoundException;
}
