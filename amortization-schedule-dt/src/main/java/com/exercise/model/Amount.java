package com.exercise.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {
	
	public static final Amount ZERO = new Amount(0);
	
	private BigDecimal value;
	
	public Amount() { }
	
	public Amount(final Integer value) { 
		this.value = BigDecimal.valueOf(value);
	}
	
	public Amount(final Double value) { 
		this.value = BigDecimal.valueOf(value);
	}
	
	public Amount(final BigDecimal value) {
		this.value = value;
	}
	
	public Amount plus(final Amount another) {
		return new Amount(value.add(another.value));
	}
	
	public Amount plusOne() {
		return new Amount(value.add(BigDecimal.ONE));
	}
	
	public Amount minusOne() {
		return new Amount(value.subtract(BigDecimal.ONE));
	}
	
	public Amount minus(final Amount another) {
		return new Amount(value.subtract(another.value));
	}
	
	public Amount multipliedBy(final Amount another) {
		return new Amount(value.multiply(another.value));
	}
	
	public Amount dividedBy(final Amount another) {
		return new Amount(value.doubleValue() / (another.doubleValue()));
	}
	
	public Amount exp(final Amount exponent) {
		return new Amount(Math.pow(value.doubleValue(), exponent.value.doubleValue()));
	}
	
	public Amount roundDown(final Integer scale) {
		return new Amount(this.value.setScale(scale, RoundingMode.DOWN));
	}
	
	public Double doubleValue() {
		return this.value.doubleValue();
	}
	
	@Override
	public boolean equals(Object another) {
		return this.value.equals(((Amount)another).value);
	}
}
