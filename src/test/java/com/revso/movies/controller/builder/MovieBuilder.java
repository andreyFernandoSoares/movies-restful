package com.revso.movies.controller.builder;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.enums.StreamingEnum;
import com.revso.movies.model.Movie;

public class MovieBuilder {
	
	public static Movie buildMovieWithId() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setName("The Witch");
		movie.setCategory(CategoryEnum.HORROR);
		movie.setStreaming(StreamingEnum.NETFLIX);
		return movie;
	}
	
	public static Movie buildMovieWithOutId() {
		Movie movie = new Movie();
		movie.setName("The Witch");
		movie.setCategory(CategoryEnum.HORROR);
		movie.setStreaming(StreamingEnum.NETFLIX);
		return movie;
	}
}
