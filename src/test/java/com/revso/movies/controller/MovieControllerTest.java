package com.revso.movies.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revso.movies.builder.MovieBuilder;
import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.exception.MovieBadRequestException;
import com.revso.movies.exception.MovieNotFoundException;
import com.revso.movies.model.Movie;
import com.revso.movies.service.MovieService;
import com.revso.movies.utils.TestUtils;

@ExtendWith(SpringExtension.class)
public class MovieControllerTest {
	
	@InjectMocks
	private MovieController movieController;
	
	@Mock 
	private MovieService movieServiceMock;
	
	@BeforeEach
	void setUp() throws MovieBadRequestException, MovieNotFoundException {
		
		BDDMockito.when(this.movieServiceMock.findAll())
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(this.movieServiceMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(this.movieServiceMock.findByCategory(ArgumentMatchers.any(CategoryEnum.class)))
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(this.movieServiceMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(MovieBuilder.buildMovieWithId());
		
		BDDMockito.when(this.movieServiceMock.save(ArgumentMatchers.any(Movie.class)))
			.thenReturn(MovieBuilder.buildMovieWithId());
		
		BDDMockito.when(this.movieServiceMock.alter(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Movie.class)))
			.thenReturn(MovieBuilder.buildMovieWithId());
		
		BDDMockito.doNothing().when(movieServiceMock)
			.delete(ArgumentMatchers.anyLong());
	}
	
	@Test
	@DisplayName("Find all movies when successful")
	void findAllMoviesWhenSuccessful() throws MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		String expectedName = MovieBuilder.buildMovieWithId().getName();
		
		ResponseEntity<List<Movie>> entity = movieController.findAll(http);
		 
		List<Movie> movies = entity.getBody();
		
		assertThat(entity.getStatusCode())
	 		.isEqualTo(HttpStatus.OK);
		 
		assertThat(movies)
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(movies.get(0).getName())
			.isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("Find by name when successful")
	void findByNameWhenSuccesful() throws MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		String name = MovieBuilder.buildMovieWithId().getName();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		ResponseEntity<List<Movie>> entity = movieController.findByName(name, http);
		
		List<Movie> movies = entity.getBody();
		
		assertThat(entity.getStatusCode())
 			.isEqualTo(HttpStatus.OK);
		
		assertThat(movies)
		.isNotEmpty()
		.hasSize(1);
	
		assertThat(movies.get(0).getId())
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Find by category when successful")
	void findByCategoryWhenSuccesful() throws MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		CategoryEnum category = MovieBuilder.buildMovieWithId().getCategory();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		ResponseEntity<List<Movie>> entity = movieController.findByCategory(category, http);
		
		List<Movie> movies = entity.getBody();
		
		assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.OK);
		
		assertThat(movies)
		.isNotEmpty()
		.hasSize(1);
	
		assertThat(movies.get(0).getId())
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Find by id when successful")
	void findByIdWhenSuccesful() throws MovieNotFoundException, MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		ResponseEntity<Movie> entity = movieController.findById(1L, http);
		
		Movie movie = entity.getBody();
		
		assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.OK);
		
		assertThat(movie).isNotNull();
		
        assertThat(movie.getId())
        	.isNotNull()
        	.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Save movie when successful")
	void saveMovieWhenSuccessful() throws MovieNotFoundException, MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		ResponseEntity<Movie> entity = movieController
				.save(MovieBuilder.buildMovieWithId(), http);
		
		Movie movie = entity.getBody();
		
		assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.CREATED);
		
		assertThat(movie)
			.isNotNull()
			.isEqualTo(MovieBuilder.buildMovieWithId());
	}
	
	@Test
	@DisplayName("Alter movie when successful")
	void alterMovieWhenSuccesful() throws MovieNotFoundException, MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		ResponseEntity<Movie> entity = movieController
				.alter(expectedId, MovieBuilder.buildAlterMovie(), http);
		
		Movie changedMovie = entity.getBody();
		
		assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.OK);
		
		assertThat(changedMovie.getId())
			.isNotNull()
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Delete movie when successful")
	void deleteMovieWhenSuccessful() throws MovieNotFoundException, MovieBadRequestException {
		MockHttpServletRequest http = TestUtils.buildMockHttpServletRequest();
		
		assertThatCode(() -> movieController.delete(1L, http))
			.doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = movieController.delete(1L, http);
		
		assertThat(entity).isNotNull();
		
		assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.NO_CONTENT);
	}
}
