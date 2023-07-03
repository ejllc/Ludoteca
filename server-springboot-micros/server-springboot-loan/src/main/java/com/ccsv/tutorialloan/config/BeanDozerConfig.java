package com.ccsv.tutorialloan.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author emlloren
 *
 */
@Configuration
public class BeanDozerConfig {

	@Bean
	DozerBeanMapper getDozerBeanMapper() {

		return new DozerBeanMapper();
	}

}