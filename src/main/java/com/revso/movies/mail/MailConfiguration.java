package com.revso.movies.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

@Configuration
public class MailConfiguration {
	
	@Primary
	@Bean
	public FreeMarkerConfigurationFactory factoryBean() {
		FreeMarkerConfigurationFactory bean = new FreeMarkerConfigurationFactory();
		bean.setTemplateLoaderPath("classpath:/templates");
		Properties properties = freeMarkerProperties();
		bean.setFreemarkerSettings(properties);
		return bean;
	}

	private Properties freeMarkerProperties() {
		Properties properties = new Properties();
		properties.setProperty("classic_compatible", "true");
		properties.setProperty("defaultEncoding", "utf-8");
		properties.setProperty("template_exception_handler", "rethrow");
		return properties;
	}
}
