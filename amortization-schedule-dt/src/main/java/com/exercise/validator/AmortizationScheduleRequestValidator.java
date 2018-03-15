package com.exercise.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.exercise.controller.error.AmortizationScheduleGenerationException;
import com.exercise.dto.AmortizationScheduleRequestDTO;
import com.exercise.dto.AmortizationScheduleRequestDTO.InterestPercentagePerPeriodDTO;

@Component
public class AmortizationScheduleRequestValidator {
	
	/**
	 * Only for demo purposes validation result is mainly exception based.
	 * 
	 * Another way to implement is to use annotations on request.
	 */
	public void validate(final @RequestBody AmortizationScheduleRequestDTO request) {
		assertNotNull(request.getLoanAmount(), "Loan amount must not be null");
		assertHigherThanZero(request.getLoanAmount().intValue(), "Loan amount must be higher than zero");
		assertNotNull(request.getTermInMonths(), "Loan term (month count) must not be null");
		assertHigherThanZero(request.getTermInMonths(), "Loan term (month count) must be higher than zero");
		assertTrue(request.getPeriodicInterest() != null && !request.getPeriodicInterest().isEmpty(),
				"Periodic interest configuration list must not be null or empty");
		Integer interestPeriodsTotalMonthCount = 0;
		for (final InterestPercentagePerPeriodDTO periodicInterestConfig : request.getPeriodicInterest()) {
			interestPeriodsTotalMonthCount += periodicInterestConfig.getMonthCount();
			assertNotNull(periodicInterestConfig.getMonthCount(), "Periodic interest month count must not be null");
			assertHigherThanZero(periodicInterestConfig.getMonthCount(),
					"Periodic interest month count must be higher than zero");
			assertNotNull(periodicInterestConfig.getInterestPercentage(),
					"Periodic interest percentage must not be null");
			assertTrue(periodicInterestConfig.getInterestPercentage() >= 0,
					"Periodic interest percentage must be greater or equal than zero");
		}
		assertTrue(request.getTermInMonths().equals(interestPeriodsTotalMonthCount),
				"Loan term must be fully covered by provided interest periods");
	}
	
	public void assertNotNull(final Object value, final String message) {
		assertTrue(value != null, message);
	}
		
	public void assertHigherThanZero(final Integer value, final String message) {
		assertTrue(value > 0, message);
	}
	
	private void assertTrue(final boolean condition, final String message) {
		if (!condition) throw new AmortizationScheduleGenerationException(message);
	}
}
