package com.revso.movies.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.revso.movies.builder.MovieBuilder;
import com.revso.movies.model.Movie;
import com.revso.movies.repository.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRole")
	private TestRestTemplate testRestTemplateRole;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@TestConfiguration
	@Lazy
	static class Config {
		@Bean(name = "testRestTemplateRole")
		public TestRestTemplate testRestTemplateRoleCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("diddyzin", "12345");
            return new TestRestTemplate(restTemplateBuilder);
        }
	}
	
	@Test
    @DisplayName("Find all movies when successful")
    void findAllMoviesWhenSuccessful() {
        Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());

        String expectedName = savedMovie.getName();

        List<Movie> movies = testRestTemplateRole.exchange("/movie/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Movie>>() {
                }).getBody();
        
        assertThat(movies)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(movies.get(0).getName())
        	.isEqualTo(expectedName);
    }
	
	@Test
    @DisplayName("Find all movies by name when successful")
    void findByNameWhenSuccesful() {
        Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());

        String expectedName = savedMovie.getName();

        List<Movie> movies = testRestTemplateRole.exchange("/movie/name/"+expectedName, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Movie>>() {
                }).getBody();
        
        assertThat(movies)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(movies.get(0).getName())
        	.isEqualTo(expectedName);
    }
	
	@Test
    @DisplayName("Find all movies by category when successful")
    void findByCategoryWhenSuccesful() {
        Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());

        String expectedName = savedMovie.getName();

        List<Movie> movies = testRestTemplateRole.exchange("/movie/category/"+savedMovie.getCategory(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Movie>>() {
                }).getBody();
        
        assertThat(movies)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(movies.get(0).getName())
        	.isEqualTo(expectedName);
    }
	
	@Test
	@DisplayName("Find by id when successful")
    void findByIdWhenSuccesful() {
		Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());

        Long expectedId = savedMovie.getId();

        Movie movie = testRestTemplateRole.getForObject("/movie/{id}", Movie.class, expectedId);

        assertThat(movie).isNotNull();

        assertThat(movie.getId())
        	.isNotNull()
        	.isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("Find By name returns an empty list of movies when movie is not found")
    void findByCategoryWhenSuccesfulMovieIsNotFound() {
        List<Movie> movies = testRestTemplateRole.exchange("/movie/name/dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Movie>>() {
                }).getBody();

        assertThat(movies)
            .isNotNull()
            .isEmpty();
    }
	
	@Test
	@DisplayName("Save movie when successful")
    void saveMovieWhenSuccessful() {
		Movie savedMovie = MovieBuilder.buildMovieWithId();

        ResponseEntity<Movie> entity = testRestTemplateRole.postForEntity("/movie/save", savedMovie, Movie.class);

        assertThat(entity).isNotNull();
        
        assertThat(entity.getStatusCode())
        	.isEqualTo(HttpStatus.CREATED);
        
        assertThat(entity.getBody())
        	.isNotNull();
        
        assertThat(entity.getBody().getId())
        	.isNotNull();
    }
	
	@Test
	@DisplayName("Alter movie when successful")
    void alterMovieWhenSuccesful() {
		Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());
		Movie changedMovie = MovieBuilder.buildAlterMovie();

        ResponseEntity<Movie> entity = testRestTemplateRole
        		.exchange("/movie/alter/{id}", HttpMethod.PUT, new HttpEntity<>(changedMovie), Movie.class, savedMovie.getId());

        assertThat(entity).isNotNull();
        
        assertThat(entity.getStatusCode())
        	.isEqualTo(HttpStatus.OK);
        
        assertThat(entity.getBody())
        	.isNotNull();
        
        assertThat(entity.getBody().getId())
        	.isNotNull();
        
        assertThat(entity.getBody().getName())
    		.isNotEqualTo(savedMovie.getName());
    }
	
	@Test
	@DisplayName("Delete movie when successful")
    void deleteMovieWhenSuccessful() {
		Movie savedMovie = movieRepository.save(MovieBuilder.buildMovieWithId());

        ResponseEntity<Void> entity = testRestTemplateRole.exchange("/movie/delete/{id}",
                HttpMethod.DELETE, null, Void.class, savedMovie.getId());

        assertThat(entity).isNotNull();

        assertThat(entity.getStatusCode())
        	.isEqualTo(HttpStatus.NO_CONTENT);
    }
}
