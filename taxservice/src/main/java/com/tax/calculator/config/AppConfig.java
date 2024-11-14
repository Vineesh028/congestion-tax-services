package com.tax.calculator.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.tax.calculator.util.Constants;
import com.tax.calculator.util.JsonReader;

@Configuration
public class AppConfig {

	
	/**
	 * Path to city json file
	 */
	@Value("${json.path}")
	String jsonPath;

	/**
	 * Reading tax rates, currency, max tax perday, holidays for a city from JSON file
	 */
	@Bean
	public CommandLineRunner startup() {

		return args -> JsonReader.readJSON(jsonPath, objectMapper());

	}

	/**
	 * Object mapper configuring with java.time module
	 */
	@Bean
	public ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(sdf);
		return mapper;
	}

}
