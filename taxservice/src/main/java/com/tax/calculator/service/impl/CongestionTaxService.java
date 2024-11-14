package com.tax.calculator.service.impl;

import org.springframework.stereotype.Service;

import com.tax.calculator.model.TaxRequest;
import com.tax.calculator.model.TaxResponse;
import com.tax.calculator.service.TaxService;
import com.tax.calculator.util.CongestionTaxCalculator;
import com.tax.calculator.util.Constants;
import com.tax.calculator.util.JsonReader;

import lombok.AllArgsConstructor;

/**
 * Implementation of TaxService to calculate congestion tax
 */
@Service
@AllArgsConstructor
public class CongestionTaxService implements TaxService {

	/**
	 *Using CongestionTaxCalculator to calculate tax and returning TaxResponse
	 * @param taxRequest
	 * @return TaxResponse
	 */
	@Override
	public TaxResponse calculateTax(TaxRequest taxRequest) {

		int congestionTax = CongestionTaxCalculator.getTax(taxRequest.getVehicle(), taxRequest.getDates());

		return new TaxResponse(Constants.MESSAGE_200,
				String.valueOf("Congestion Tax : "+congestionTax+" "+JsonReader.getCityTaxRates().getCurrency()) );
	}

}
