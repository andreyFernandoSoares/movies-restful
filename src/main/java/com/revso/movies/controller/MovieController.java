package com.revso.movies.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.exception.MovieBadRequestException;
import com.revso.movies.exception.MovieNotFoundException;
import com.revso.movies.model.Movie;
import com.revso.movies.service.MovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Movies")
@RequestMapping("movie")
public class MovieController {
	
	@Autowired
	public MovieService movieService;
	
	@ApiOperation("Save movie")
	@PostMapping("/save")
	@CacheEvict(value = {"listAll", "listByCategory", "listByName"}, allEntries = true)
	@Transactional(rollbackOn = { MovieBadRequestException.class, MovieNotFoundException.class })
	public ResponseEntity<Movie> save(@RequestBody @Valid Movie movie, HttpServletRequest http) 
			throws MovieBadRequestException, MovieNotFoundException {
		
		String location = http.getRequestURL().toString();
		Movie savedMovie = this.movieService.save(movie);
		
		savedMovie.add(linkTo(methodOn(MovieController.class)
				.findById(savedMovie.getId(), http))
				.withRel("byId"));
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, location)
				.body(savedMovie);
	}
	
	@ApiOperation("Find all movies")
	@GetMapping("/all")
	@Cacheable(value = "listAll")
	public ResponseEntity<List<Movie>> findAll(HttpServletRequest http) 
			throws MovieBadRequestException {
		
		String location = http.getRequestURL().toString();
		List<Movie> movieList = this.movieService.findAll();
		
		movieList.forEach(m -> {
			try {
				m.add(linkTo(methodOn(MovieController.class).findById(m.getId(), http)).withSelfRel());
			} catch (MovieNotFoundException | MovieBadRequestException e) {
				// TODO Auto-generated catch block
			}
		});
			
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.LOCATION, location)
				.body(movieList);
	}
	
	@ApiOperation("Find movie by id")
	@GetMapping("/{id}")
	public ResponseEntity<Movie> findById(@PathVariable Long id, HttpServletRequest http) 
			throws MovieBadRequestException, MovieNotFoundException {
		
		String location = http.getRequestURL().toString();
		Movie movie = this.movieService.findById(id);
		
		movie.add(linkTo(methodOn(MovieController.class)
				.findByName(movie.getName(), http))
				.withRel("byName"));
		
		movie.add(linkTo(methodOn(MovieController.class)
				.findByCategory(movie.getCategory(), http))
				.withRel("byCategory"));
		
		movie.add(linkTo(methodOn(MovieController.class)
				.findAll(http))
				.withRel("all"));
		
		movie.add(linkTo(methodOn(MovieController.class)
				.alter(movie.getId(), movie, http))
				.withRel("alter"));
		
		movie.add(linkTo(methodOn(MovieController.class)
				.save(movie, http))
				.withRel("save"));
		
		movie.add(linkTo(methodOn(MovieController.class)
				.delete(movie.getId(), http))
				.withRel("save"));
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.LOCATION, location)
				.body(movie);
	}
	
	@ApiOperation("Find movies by name")
	@GetMapping("/name/{name}")
	@Cacheable(value = "listByName")
	public ResponseEntity<List<Movie>> findByName(@PathVariable String name, HttpServletRequest http) 
			throws MovieBadRequestException {
		
		String location = http.getRequestURL().toString();
		List<Movie> movieList = this.movieService.findByName(name);
		
		movieList.forEach(m -> {
			try {
				m.add(linkTo(methodOn(MovieController.class).findById(m.getId(), http)).withSelfRel());
			} catch (MovieNotFoundException | MovieBadRequestException e) {
				// TODO Auto-generated catch block
			}
		});
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.LOCATION, location)
				.body(movieList);
	}
	
	@ApiOperation("Find movies by category")
	@GetMapping("/category/{category}")
	@Cacheable(value = "listByCategory")
	public ResponseEntity<List<Movie>> findByCategory(@PathVariable CategoryEnum category, HttpServletRequest http) 
			throws MovieBadRequestException {
		
		String location = http.getRequestURL().toString();
		List<Movie> movieList = this.movieService.findByCategory(category);
		
		movieList.forEach(m -> {
			try {
				m.add(linkTo(methodOn(MovieController.class).findById(m.getId(), http)).withSelfRel());
			} catch (MovieNotFoundException | MovieBadRequestException e) {
				// TODO Auto-generated catch block
			}
		});
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.LOCATION, location)
				.body(movieList);
	}
	
	@ApiOperation("Alter movie")
	@PutMapping("/alter/{id}")
	@CacheEvict(value = {"listAll", "listByCategory", "listByName"}, allEntries = true)
	@Transactional(rollbackOn = { MovieBadRequestException.class, MovieNotFoundException.class })
	public ResponseEntity<Movie> alter(@PathVariable Long id, @RequestBody @Valid Movie movie, HttpServletRequest http) 
			throws MovieBadRequestException, MovieNotFoundException {
		
		String location = http.getRequestURL().toString();
		Movie changedMovie = this.movieService.alter(id, movie);
		
		changedMovie.add(linkTo(methodOn(MovieController.class)
				.findById(changedMovie.getId(), http))
				.withRel("byId"));
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.LOCATION, location)
				.body(changedMovie);
	}
	
	@ApiOperation("Delete movie")
	@DeleteMapping("/delete/{id}") 
	@CacheEvict(value = {"listAll", "listByCategory", "listByName"}, allEntries = true)
	@Transactional(rollbackOn = { MovieBadRequestException.class, MovieNotFoundException.class })
	public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest http) 
			throws MovieBadRequestException, MovieNotFoundException {
		
		String location = http.getRequestURL().toString();
		this.movieService.delete(id);
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, location)
				.build();
	}

}
