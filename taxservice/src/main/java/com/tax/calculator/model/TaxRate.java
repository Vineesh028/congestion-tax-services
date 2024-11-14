package com.tax.calculator.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxRate {

	private LocalTime start;
	private LocalTime end;
	private int amount;

}