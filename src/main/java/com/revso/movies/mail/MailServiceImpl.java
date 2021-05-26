package com.revso.movies.mail;

import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.revso.movies.exception.MovieBadRequestException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private Configuration configuration;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Autowired
	private JavaMailSender jms;
	
	@Override
	public void send(Mail mail) throws MovieBadRequestException {
		try {
			MimeMessage message = jms.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
			mail.setBody(configureTemplate(mail));
			helper.setFrom(username);
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getTitle());
			helper.setText(mail.getBody(), true);
			jms.send(message);
		} catch (Exception e) {
			throw new MovieBadRequestException("Falha ao enviar email");
		}
	}

	@Override
	public String configureTemplate(Mail mail) throws IOException, TemplateException {
		Template template = configuration.getTemplate(mail.getTemplate());
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getMapVariables());
		return html;
	}
}
