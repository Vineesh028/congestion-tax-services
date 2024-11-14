package com.tax.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
class TaxServiceApplicationTests {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void calculateTaxSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.FEW_DATETIME)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 26 SEK"));

	}
	
	@Test
	void calculateMaxTaxSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.MAX_TAX_PERDAY)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 60 SEK"));

	}
	
	@Test
	void calculateTaxForMultipleDaysSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.MULTIPLE_DAYS)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 47 SEK"));

	}
	
	@Test
	void calculateTaxForTollFreeDaysSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.TOLL_FREE_DAYS)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 0 SEK"));

	}
	
	@Test
	void calculateTaxForExemptedVehicleSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.TAX_EXEMPTED_VEHICLE)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 0 SEK"));

	}
	
	@Test
	void calculateTaxWithSingleChargeRuleSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.SINGLE_CHARGE_RULE)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 26 SEK"));

	}
	
	@Test
	void calculateTaxWithSingleChargeRuleAndMaxTaxPerDaySuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.SINGLE_CHARGE_RULE_MAX_TAX)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 60 SEK"));

	}
	
	@Test
	void calculateTaxForNoTaxTimeSuccessfully() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.NO_TAX_TIME)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("Tax calculated successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value("Congestion Tax : 0 SEK"));

	}
	
	
	@Test
	void testInvalidVehicleType() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.INVALID_VEHICLE_TYPE)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("BAD_REQUEST"));

	}
	
	@Test
	void testInvalidDateTime() throws Exception {

		this.mvc.perform(post("/congestion-tax").content(TestUtil.INVALID_DATETIME)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Failed to read request"));

	}


}
