package com.tax.calculator.util;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tax.calculator.exception.JsonReadException;
import com.tax.calculator.model.CityTaxRates;

import lombok.experimental.UtilityClass;


/**
 * Utilicy class for reading city json files 
 */
@UtilityClass
public class JsonReader {

	private CityTaxRates cityTaxRates;

	public void readJSON(String cityJson, ObjectMapper objectMapper) {

		try {

			setCityTaxRates(objectMapper.readValue(new ClassPathResource(cityJson).getInputStream(), CityTaxRates.class));
		} catch (IOException e) {
			throw new JsonReadException(e.getMessage());
		}

	}

	public static CityTaxRates getCityTaxRates() {
		return cityTaxRates;
	}

	public static void setCityTaxRates(CityTaxRates cityTaxRates) {
		JsonReader.cityTaxRates = cityTaxRates;
	}


}
