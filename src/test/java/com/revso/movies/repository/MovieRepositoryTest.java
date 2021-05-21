package com.revso.movies.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revso.movies.builder.MovieBuilder;
import com.revso.movies.model.Movie;

@DataJpaTest
@DisplayName("Test for Movie Repository")
public class MovieRepositoryTest {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	@DisplayName("Test for create movie when successful")
	void saveMovieWhenSuccessful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		assertThat(movieSaved).isNotNull();
		assertThat(movieSaved.getId()).isNotNull();
		assertThat(movieSaved.getName()).isEqualTo(movieToBeSaved.getName());
	}
	
	@Test
	@DisplayName("Test for alter movie when successful")
	void alterMovieWhenSuccessful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		movieSaved.setName("Invocação do Mal");
		Movie changedMovie = this.movieRepository.save(movieToBeSaved);
		assertThat(changedMovie).isNotNull();
		assertThat(changedMovie.getId()).isNotNull();
		assertThat(changedMovie.getId()).isEqualTo(movieSaved.getId());
	}
	
	@Test
	@DisplayName("Test for delete movie when successful")
	void deleteMovieWhenSuccessful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		this.movieRepository.delete(movieSaved);
		Optional<Movie> movieOpt = this.movieRepository.findById(movieSaved.getId());
		assertThat(movieOpt).isEmpty();
	}
	
	@Test
	@DisplayName("Test for get movie by Id")
	void getByIdWhenSuccesful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId(); 
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		Optional<Movie> movieOpt = this.movieRepository.findById(movieSaved.getId());
		assertThat(movieOpt).isNotEmpty();
		assertThat(movieOpt.get().getId()).isNotNull();
		assertThat(movieOpt.get().getId()).isEqualTo(movieSaved.getId());
	}
	
	@Test
	@DisplayName("Test for get all movies")
	void getAllWhenSuccesful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId(); 
		this.movieRepository.save(movieToBeSaved);
		List<Movie> movieList = this.movieRepository.findAll();
		assertThat(movieList).isNotEmpty();
		assertThat(movieList).isNotNull();
		assertThat(movieList.get(0).getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Test for get movies by name")
	void getByNameWhenSuccesful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId(); 
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		List<Movie> movieList = this.movieRepository.findByNameContainingIgnoreCase(movieSaved.getName());
		assertThat(movieList).isNotEmpty();
		assertThat(movieList).isNotNull();
		assertThat(movieList.get(0).getId()).isNotNull();
		assertThat(movieList.get(0).getName()).contains(movieSaved.getName());
	}
	
	@Test
	@DisplayName("Test for get movies by category")
	void getByCategoryWhenSuccesful() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId(); 
		Movie movieSaved = this.movieRepository.save(movieToBeSaved);
		List<Movie> movieList = this.movieRepository.findByCategory(movieSaved.getCategory());
		assertThat(movieList).isNotEmpty();
		assertThat(movieList).isNotNull();
		assertThat(movieList.get(0).getId()).isNotNull();
		assertThat(movieList.get(0).getCategory()).isEqualTo(movieSaved.getCategory());
	}
	
	@Test
	@DisplayName("Test save throw ConstraintViolationException when name is empty")
	void saveEnterpriseThrowsConstraintViolationExceptionWhenNameIsEmpty() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		movieToBeSaved.setName("");
		assertThatExceptionOfType(ConstraintViolationException.class)
			.isThrownBy(() -> this.movieRepository.save(movieToBeSaved))
			.withMessageContaining("The movie name cannot be empty");
	}
	
	@Test
	@DisplayName("Test save throw ConstraintViolationException when name is null")
	void saveEnterpriseThrowsConstraintViolationExceptionWhenNameIsNull() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		movieToBeSaved.setName(null);
		assertThatExceptionOfType(ConstraintViolationException.class)
			.isThrownBy(() -> this.movieRepository.save(movieToBeSaved))
			.withMessageContaining("The movie name cannot be null");
	}
	
	@Test
	@DisplayName("Test save throw ConstraintViolationException when category is null")
	void saveEnterpriseThrowsConstraintViolationExceptionWhenCategoryIsNull() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		movieToBeSaved.setCategory(null);
		assertThatExceptionOfType(ConstraintViolationException.class)
			.isThrownBy(() -> this.movieRepository.save(movieToBeSaved))
			.withMessageContaining("The movie category cannot be null");
	}
	
	@Test
	@DisplayName("Test save throw ConstraintViolationException when streaming is null")
	void saveEnterpriseThrowsConstraintViolationExceptionWhenStreamingIsNull() {
		Movie movieToBeSaved = MovieBuilder.buildMovieWithOutId();
		movieToBeSaved.setStreaming(null);
		assertThatExceptionOfType(ConstraintViolationException.class)
			.isThrownBy(() -> this.movieRepository.save(movieToBeSaved))
			.withMessageContaining("The movie streaming cannot be null");
	}
}
