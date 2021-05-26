package com.revso.movies.mail;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Mail {
	
	private String username;
	private String to;
	private String body;
	private String title;
	
	@JsonIgnore
	private String template;
	
	@JsonIgnore
	private Map<String, Object> mapVariables;
	
}

