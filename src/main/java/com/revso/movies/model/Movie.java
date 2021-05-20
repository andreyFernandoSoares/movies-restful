package com.revso.movies.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.revso.movies.enums.CategoryEnum;
import com.revso.movies.enums.StreamingEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Movie extends RepresentationModel<Movie> implements Serializable {
	
	private static final long serialVersionUID = 4371531599732067568L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "The movie name cannot be empty")
	@NotNull(message = "The movie name cannot be null")
	private String name;
	
	@NotNull(message = "The movie category cannot be null")
	@Enumerated(EnumType.STRING)
	private CategoryEnum category;
	
	@NotNull(message = "The movie streaming cannot be null")
	private StreamingEnum streaming;
	
}
