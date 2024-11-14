package com.tax.calculator.service;

import com.tax.calculator.model.TaxRequest;
import com.tax.calculator.model.TaxResponse;

/**
 * Tax interface can be implemented to calculate different taxes
 */
public interface TaxService {
	
	TaxResponse calculateTax (TaxRequest taxRequest);

}
