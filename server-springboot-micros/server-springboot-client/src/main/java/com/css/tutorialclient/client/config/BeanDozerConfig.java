package com.css.tutorialclient.client.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDozerConfig {

	@Bean
	DozerBeanMapper getDozerBeanMapper() {

		return new DozerBeanMapper();
	}

}
