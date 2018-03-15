package com.exercise.dto;

import java.util.List;

import lombok.Data;

@Data
public class AmortizationScheduleRequestDTO {
	
	private Double loanAmount;
	private Integer termInMonths;
	private List<InterestPercentagePerPeriodDTO> periodicInterest;
	
	@Data
	public static class InterestPercentagePerPeriodDTO {
		private Integer monthCount;
		private Double interestPercentage;
	}
}