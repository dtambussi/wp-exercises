package com.exercise.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AmortizationScheduleParameters {
	
	private Amount loanAmount;
	private Period term;
	private List<PeriodicInterestRate> periodicInterestRates;
}
