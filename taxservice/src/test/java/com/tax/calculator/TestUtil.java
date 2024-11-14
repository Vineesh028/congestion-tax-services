package com.tax.calculator;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.tax.calculator.model.TaxRequest;

public class TestUtil {

	public static final String FEW_DATETIME = """
			{
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013-02-08 06:20:27",
			      "2013-02-08 15:35:00"
			   ]
			}

												""";

	public static final String MAX_TAX_PERDAY = """

			{
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013-02-08 06:20:27",
			      "2013-02-08 14:35:00",
			      "2013-02-08 15:29:00",
			      "2013-02-08 15:47:00",
			      "2013-02-08 16:01:00",
			      "2013-02-08 16:48:00",
			      "2013-02-08 17:49:00",
			      "2013-02-08 18:29:00",
			      "2013-02-08 18:35:00"
			   ]
			}
						""";

	public static final String MULTIPLE_DAYS = """
						{
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013-01-15 07:01:00",
			      "2013-02-08 06:20:27",
			      "2013-02-08 18:15:00",
			      "2013-03-26 15:25:00"
			   ]
			}
						""";

	public static final String TOLL_FREE_DAYS = """
						 	{
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013-01-05 13:00:00",
			      "2013-03-28 07:20:27",
			      "2013-03-27 18:25:00",
			      "2013-07-16 14:25:00"
			   ]
			}

						""";

	public static final String TAX_EXEMPTED_VEHICLE = """
					{
			   "vehicle":{
			      "vehicleType":"Emergency"
			   },
			   "dates":[
			      "2013-02-08 06:20:27",
			      "2013-02-08 14:35:00",
			      "2013-02-08 15:29:00"
			   ]
			}
						""";

	public static final String SINGLE_CHARGE_RULE = """
						 {
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013-02-08 06:20:27",
			      "2013-02-08 07:40:00",
			      "2013-02-08 07:55:00"
			   ]
			}

						""";
	public static final String SINGLE_CHARGE_RULE_MAX_TAX = """
						   {
   "vehicle":{
      "vehicleType":"Car"
   },
   "dates":[
      "2013-02-08 06:20:27",
      "2013-02-08 06:50:00",
      "2013-02-08 14:35:00",
      "2013-02-08 15:29:00",
      "2013-02-08 15:47:00",
      "2013-02-08 16:01:00",
      "2013-02-08 16:48:00",
      "2013-02-08 17:49:00",
      "2013-02-08 18:29:00",
      "2013-02-08 18:35:00"
   ]
}
						""";

	public static final String NO_TAX_TIME = """
						 {
			  "vehicle":{
			     "vehicleType":"Car"
			  },
			  "dates":[
			     "2013-02-08 01:20:27"

			  ]
			}

						""";

	public static final String INVALID_VEHICLE_TYPE = """
						 		 {
			   "vehicle":{
			      "vehicleType":"Helicopter"
			   },
			   "dates":[
			      "2013-02-08 06:20:27",
			      "2013-02-08 06:40:00"
			   ]
			}
						""";

	public static final String INVALID_DATETIME = """
						    	 		 {
			   "vehicle":{
			      "vehicleType":"Car"
			   },
			   "dates":[
			      "2013020806:20:27",
			      "2013020806:40:00"
			   ]
			}

						""";

	public static TaxRequest getFewDateTimeRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(FEW_DATETIME, TaxRequest.class);
	}

	public static TaxRequest getMaxTaxRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(MAX_TAX_PERDAY, TaxRequest.class);
	}

	public static TaxRequest getMultipleDaysRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(MULTIPLE_DAYS, TaxRequest.class);
	}

	public static TaxRequest getTollFreeDaysRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(TOLL_FREE_DAYS, TaxRequest.class);
	}

	public static TaxRequest getTaxExemptedVehicleRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(TAX_EXEMPTED_VEHICLE, TaxRequest.class);
	}

	public static TaxRequest getSingleChargeRuleRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(SINGLE_CHARGE_RULE, TaxRequest.class);
	}

	public static TaxRequest getInvalidVehicleTypeRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(INVALID_VEHICLE_TYPE, TaxRequest.class);
	}

	public static TaxRequest getInvalidDateTimeRequest() throws JsonProcessingException {
		return buildObjectMapper().readValue(INVALID_DATETIME, TaxRequest.class);
	}

	public static ObjectMapper buildObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(sdf);
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;
	}

	public static String asJsonString(final Object obj) {
		try {
			return buildObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
