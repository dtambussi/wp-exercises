package com.exercise.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InterestRate {
	
	private Double value;
	
	public Amount plusOne() {
		return new Amount(value + 1);
	}
	
	public InterestRate dividedBy(final Integer divisor) {
		return new InterestRate(value / divisor);
	}
	
	public Amount multipliedBy(final Amount amount) {
		return this.toAmount().multipliedBy(amount);
	}
	
	public Amount toAmount() {
		return new Amount(value);
	}
}
