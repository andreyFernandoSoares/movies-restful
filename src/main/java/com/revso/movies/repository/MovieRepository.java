package com.revso.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<Movie> findByCategory(CategoryEnum category);
	
	List<Movie> findByNameContainingIgnoreCase(String name);
}
