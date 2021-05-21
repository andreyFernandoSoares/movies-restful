package com.revso.movies.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revso.movies.builder.MovieBuilder;
import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.exception.MovieBadRequestException;
import com.revso.movies.exception.MovieNotFoundException;
import com.revso.movies.model.Movie;
import com.revso.movies.repository.MovieRepository;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {
	
	@InjectMocks
	private MovieServiceImpl movieServiceImpl;
	
	@Mock
	private MovieRepository movieRepositoryMock;
	
	@BeforeEach
	void setUp() {
		
		BDDMockito.when(movieRepositoryMock.findAll())
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(movieRepositoryMock.findByCategory(ArgumentMatchers.any(CategoryEnum.class)))
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(movieRepositoryMock.findByNameContainingIgnoreCase(ArgumentMatchers.anyString()))
			.thenReturn(List.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(movieRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(MovieBuilder.buildMovieWithId()));
		
		BDDMockito.when(movieRepositoryMock.save(ArgumentMatchers.any(Movie.class)))
			.thenReturn(MovieBuilder.buildMovieWithId());
		
		BDDMockito.doNothing().when(movieRepositoryMock)
			.delete(ArgumentMatchers.any(Movie.class));
	}	
	
	@Test
	@DisplayName("Find all movies when successful")
	void findAllWhenSuccessful() throws MovieBadRequestException {
		String expectedName = MovieBuilder.buildMovieWithId().getName();
		
		List<Movie> movies = movieServiceImpl.findAll();
		
		assertThat(movies)
			.isNotEmpty()
			.hasSize(1);
	
		assertThat(movies.get(0).getName())
			.isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("Find by name when successful")
	void findByNameWhenSuccesful() throws MovieBadRequestException {
		String name = MovieBuilder.buildMovieWithId().getName();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		List<Movie> movies = movieServiceImpl.findByName(name);
		
		assertThat(movies)
		.isNotEmpty()
		.hasSize(1);
	
		assertThat(movies.get(0).getId())
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Find by category when successful")
	void findByCategoryWhenSuccesful() throws MovieBadRequestException {
		CategoryEnum category = MovieBuilder.buildMovieWithId().getCategory();
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		List<Movie> movies = movieServiceImpl.findByCategory(category);
		
		assertThat(movies)
		.isNotEmpty()
		.hasSize(1);
	
		assertThat(movies.get(0).getId())
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Find by id when successful")
	void findByIdWhenSuccesful() throws MovieNotFoundException, MovieBadRequestException {
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		Movie movie = movieServiceImpl.findById(1L);
		
		assertThat(movie).isNotNull();
		
        assertThat(movie.getId())
        	.isNotNull()
        	.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Save movie when successful")
	void saveMovieWhenSuccessful() throws MovieNotFoundException, MovieBadRequestException {
		Movie movie = movieServiceImpl
				.save(MovieBuilder.buildMovieWithId());
		
		assertThat(movie)
			.isNotNull()
			.isEqualTo(MovieBuilder.buildMovieWithId());
	}
	
	@Test
	@DisplayName("Alter movie when successful")
	void alterMovieWhenSuccesful() throws MovieNotFoundException, MovieBadRequestException {
		Long expectedId = MovieBuilder.buildMovieWithId().getId();
		
		Movie changedMovie = movieServiceImpl
				.alter(expectedId, MovieBuilder.buildAlterMovie());

		assertThat(changedMovie.getId())
			.isNotNull()
			.isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("Delete movie when successful")
	void deleteMovieWhenSuccessful() throws MovieNotFoundException, MovieBadRequestException {
		assertThatCode(() -> movieServiceImpl.delete(1L))
			.doesNotThrowAnyException();
	}
}
