package com.exercise.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PeriodicInterestRate {
	
	private Period period;
	private InterestRate interestRate;
	
	public InterestRate getInterestRatePerMonth() {
		return interestRate.dividedBy(period.getMonthCount());
	}
	
	public Integer getMonthCount() {
		return period.getMonthCount();
	}
}
