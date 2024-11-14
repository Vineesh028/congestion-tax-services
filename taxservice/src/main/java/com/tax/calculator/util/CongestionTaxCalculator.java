package com.tax.calculator.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tax.calculator.exception.VehicleTypeNotFoundException;
import com.tax.calculator.model.TaxRate;
import com.tax.calculator.model.Vehicle;
import com.tax.calculator.model.VehicleTypes;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CongestionTaxCalculator {
		

	/**
	 * Get congestion tax from the vehicle type and dates
	 * @param vehicle
	 * @param dates
	 * @return
	 */
	public int getTax(Vehicle vehicle, List<LocalDateTime> dates) {

		//Check if the vehicle type in the request exists or else throws exception
		if (!Arrays.asList(VehicleTypes.values()).toString().contains(vehicle.getVehicleType().toUpperCase()))
			throw new VehicleTypeNotFoundException(vehicle.getVehicleType());

		//Check vehicle is toll free type, if so the tax is zero
		if (isTollFreeVehicle(vehicle.getVehicleType())) {
			return 0;
		}

		int totalTax = 0;

		List<LocalDateTime> tollDates = removeTollFreeDates(dates);

		if (!tollDates.isEmpty()) {
			totalTax = getTotalTollFee(tollDates);
		}

		return totalTax;
	}

	/**
	 * Calculate toll fee for all days
	 * @param sortedDates
	 * @return
	 */
	private int getTotalTollFee(List<LocalDateTime> sortedDates) {

		int totalTax = 0;

		List<LocalDateTime> sameDays = new ArrayList<>();
		sameDays.add(sortedDates.get(0));

		if (sortedDates.size() > 1) {
			for (int i = 0; i < sortedDates.size() - 1; i++) {

				if (sortedDates.get(i).getDayOfMonth() == sortedDates.get(i + 1).getDayOfMonth()) {
					sameDays.add(sortedDates.get(i + 1));
				} else {
					totalTax += getTotalTollFeeForDay(sameDays);
					sameDays.clear();
					sameDays.add(sortedDates.get(i + 1));
				}

			}

		}

		totalTax += getTotalTollFeeForDay(sameDays);

		return totalTax;

	}

	/**
	 * Calculate toll fee for a single day
	 * @param sortedDates
	 * @return
	 */
	private int getTotalTollFeeForDay(List<LocalDateTime> sortedDates) {
		int totalFee = 0;
		
		int maxChargePerDay = JsonReader.getCityTaxRates().getMaxCharge();
		
		LocalDateTime start = sortedDates.get(0);

		for (int j = 0; j < sortedDates.size(); j++) {
			int tempFee = getTollFee(start);

			LocalDateTime end = sortedDates.get(j);

			Duration duration = Duration.between(start, end);

			long minutes = duration.toMinutes();

			int nextFee = getTollFee(end);

			if (minutes < 60 && minutes > 0) {
				totalFee -= tempFee;
				tempFee = nextFee > tempFee ? nextFee : tempFee;
				totalFee += tempFee;

			} else if (minutes == 0) {
				totalFee += tempFee;
			} else {
				totalFee += nextFee;
				start = end;
			}

		}

		return totalFee > maxChargePerDay ? maxChargePerDay : totalFee;

	}

	/**
	 * Get the toll fee amount for the time using city tax rates
	 * @param dateTime
	 * @return
	 */
	private int getTollFee(LocalDateTime dateTime) {

		List<TaxRate> rates = JsonReader.getCityTaxRates().getIntervalRates();

		for (TaxRate rate : rates) {

			if (dateTime.toLocalTime().isAfter(rate.getStart()) && dateTime.toLocalTime().isBefore(rate.getEnd())) {
				return rate.getAmount();
			}

		}

		return 0;
	}

	/**
	 * Checking whether vehicle type is toll free
	 * @param vehicleType
	 * @return
	 */
	private boolean isTollFreeVehicle(String vehicleType) {
		
		List<String> tollFreeVehicles = getTollFreeVehicleList();
		
		return tollFreeVehicles.contains(vehicleType.toUpperCase());

	}

	/**
	 * removing toll vehicles from vehicles list
	 * @return
	 */
	private List<String> getTollFreeVehicleList() {

		List<String> vehicles = Stream.of(VehicleTypes.values())
                .map(Enum::name)
                .collect(Collectors.toList());
		vehicles.removeAll(List.of(VehicleTypes.CAR.name(), VehicleTypes.VAN.name(), VehicleTypes.TRUCK.name()));
		return vehicles;

	}

	/**
	 * Removing weekends, holidays, days before holidays and July month from dates
	 * @param dates
	 * @return
	 */
	private List<LocalDateTime> removeTollFreeDates(List<LocalDateTime> dates) {

		List<MonthDay> holidays = JsonReader.getCityTaxRates().getHolidays();

		List<LocalDateTime> tollDates = new ArrayList<>();

		dates.forEach(e -> {

			if (!isTollFreeDate(e, holidays)) {
				tollDates.add(e);
			}

		});

		return tollDates.stream().sorted().toList();
	}

	/**
	 * Checking the date falls on weekends, holidays, days before holidays or on July month 
	 * @param dateTime
	 * @param holidays
	 * @return
	 */
	private boolean isTollFreeDate(LocalDateTime dateTime, List<MonthDay> holidays) {

		if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
			return true;
		if (dateTime.getMonthValue() == 7)
			return true;
		for (MonthDay holiday : holidays) {

			if ((dateTime.getMonthValue() == holiday.getMonthValue())
					&& (dateTime.getDayOfMonth() == holiday.getDayOfMonth()
							|| dateTime.getDayOfMonth() == holiday.getDayOfMonth() - 1)) {
				return true;
			}

		}

		return false;
	}

}
