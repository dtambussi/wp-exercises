package com.exercise.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Months {

	public static final Month January = month(1);
	public static final Month February = month(2);
	public static final Month March = month(3);
	public static final Month April = month(4);
	public static final Month May = month(5);
	public static final Month June = month(6);
	public static final Month July = month(7);
	public static final Month August = month(8);
	public static final Month September = month(9);
	public static final Month October = month(10);
	public static final Month November = month(11);
	public static final Month December = month(12);
	
	final static List<Month> months = Arrays.asList(January, February, March, April, May, June, July, August, September, October, November, December);
	
	public static List<Month> getNMonthsStartingFrom(final Month initialMonth, final Integer n) {
		return getNMonthsStartingFrom(initialMonth.getMonthNumber(), initialMonth.getYear(), n);
	}
	
	public static List<Month> getNMonthsStartingFrom(final Integer initialMonthNumber, final Integer initialYear,
			final Integer n) {
		final Month initialMonth = getMonthByNumber(initialMonthNumber).year(initialYear);
		final List<Month> monthList = new LinkedList<Month>();
		monthList.add(initialMonth);
		Month lastAddedMonth = initialMonth;
		for (int counter = 1; counter < n; counter++) { 
			lastAddedMonth = nextMonthGiven(lastAddedMonth);
			monthList.add(lastAddedMonth); 
		}
		return monthList;
	}
	
	public static Month nextMonthGiven(final Month month) {
		return month.equalsIgnoreYear(December) ? 
				January.year(month.getYear() + 1) : getMonthByNumber(month.getMonthNumber() + 1).year(month.getYear());
	}
	
	private static Month getMonthByNumber(Integer monthNumber) {
		return months.get(monthNumber - 1);
	}
	
	private static Month month(Integer monthNumber) {
		return new Month(monthNumber);
	}

}
