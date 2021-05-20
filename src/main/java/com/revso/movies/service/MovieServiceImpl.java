package com.revso.movies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.exception.MovieBadRequestException;
import com.revso.movies.exception.MovieNotFoundException;
import com.revso.movies.model.Movie;
import com.revso.movies.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> findAll() throws MovieBadRequestException {
		try {
			return this.movieRepository.findAll();
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to list all movies.");
		}
	}

	@Override
	public Movie findById(Long id) throws MovieBadRequestException, MovieNotFoundException {
		try {
			return this.movieRepository
				.findById(id)
				.orElseThrow(() -> new MovieNotFoundException("Not found movie"));
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to find movie.");
		}
	}

	@Override
	public List<Movie> findByCategory(CategoryEnum category) throws MovieBadRequestException {
		try {
			return this.movieRepository.findByCategory(category);
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to list all movies by category.");
		}
	}

	@Override
	public List<Movie> findByName(String name) throws MovieBadRequestException {
		try {
			return this.movieRepository.findByNameContainingIgnoreCase(name);
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to list all movies by name.");
		}
	}

	@Override
	public Movie save(Movie movie) throws MovieBadRequestException {
		try {
			return this.movieRepository.save(movie);
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to save movie.");
		}
	}

	@Override
	public void delete(Long id) throws MovieBadRequestException, MovieNotFoundException {
		try {
			Movie movie = this.findById(id);
			this.movieRepository.delete(movie);
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to save movie.");
		}
	}

	@Override
	public Movie alter(Long id, Movie movie) throws MovieBadRequestException, MovieNotFoundException {
		try {
			Movie movieToBeSaved = this.findById(id);
			movieToBeSaved.setCategory(movie.getCategory());
			movieToBeSaved.setName(movie.getName());
			movieToBeSaved.setStreaming(movie.getStreaming());
			return movieToBeSaved;
		} catch(DataAccessException ex) {
			throw new MovieBadRequestException("Failed to save movie.");
		}
	}
}
