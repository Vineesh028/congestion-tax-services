package com.tax.calculator.model;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tax.calculator.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *Object deserialised from city json file
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityTaxRates {
	
	private String currency;

	private int maxCharge;

	private List<TaxRate> intervalRates;

	private List<MonthDay> holidays;

	public List<MonthDay> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<String> holidays) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.MONTHDAY_FORMAT);

		this.holidays = holidays.stream().map(holiday -> MonthDay.parse(holiday, dateTimeFormatter)).toList();

	}

}