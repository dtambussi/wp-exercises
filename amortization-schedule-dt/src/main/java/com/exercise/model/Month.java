package com.exercise.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.Data;

@Data
public class Month {
	
	private Integer monthNumber, year;
	
	public Month() { }
	
	public Month(final Integer monthNumber) {
		this.monthNumber = monthNumber;		
	}
	
	public Month(final Integer monthNumber, final Integer year) { 
		this.monthNumber = monthNumber;
		this.year = year;
	}
	
	public Month year(final Integer year) {
		return new Month(this.monthNumber, year);
	}
	
	public Date firstDay() { // TODO: Maybe switch to end of day
		return Date.from(LocalDate.of(this.year, this.monthNumber, 1)
				.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
	}
	
	public boolean equalsIgnoreYear(final Month anotherMonth) {
		return this.monthNumber == anotherMonth.getMonthNumber();
	}
}
