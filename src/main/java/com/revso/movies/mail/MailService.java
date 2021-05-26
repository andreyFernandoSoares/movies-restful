package com.revso.movies.mail;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.revso.movies.exception.MovieBadRequestException;

import freemarker.template.TemplateException;

@Component
public interface MailService {
	
	public void send(Mail mail) throws MovieBadRequestException;
	
	public String configureTemplate(Mail mail) throws IOException, TemplateException;
	
}
