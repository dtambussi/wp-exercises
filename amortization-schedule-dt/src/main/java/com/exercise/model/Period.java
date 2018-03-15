package com.exercise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Period {
	
	private Integer monthCount;
	private List<Month> months;
	
	public Period(final Integer monthCount) {
		this.monthCount = monthCount;
	}
	
	public Month getLastMonth() {
		return this.months.stream().reduce((m1, m2) -> m2).orElse(null);
	}
	
	public static Period months(final Integer monthCount) {
		return new Period(monthCount);
	}
	
	public static Period months(final Integer monthCount, final Month initialMonth) {
		final List<Month> months = Months.getNMonthsStartingFrom(initialMonth, monthCount);
		return new Period(monthCount, months);
	}
}
