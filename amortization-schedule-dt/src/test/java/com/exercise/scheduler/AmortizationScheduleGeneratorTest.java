package com.exercise.scheduler;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.exercise.model.AmortizationSchedule;
import com.exercise.model.AmortizationScheduleParameters;
import com.exercise.model.AmortizationSchedulePaymentDetail;
import com.exercise.model.Amount;
import com.exercise.model.InterestRate;
import com.exercise.model.Month;
import com.exercise.model.Months;
import com.exercise.model.Period;
import com.exercise.model.PeriodicInterestRate;

@RunWith(SpringRunner.class)
public class AmortizationScheduleGeneratorTest {
	
	static final Double Twelve_Percent = 12D;
	static final Double Ten_Percent = 10D;
	
	static final Integer Twelve_Months = 12;
	
	final AmortizationScheduleGenerator generator = new AmortizationScheduleGenerator();

	@Test
	public void whenValidParams_thenAmortizationScheduleShouldBeGeneratedOk() {
		final AmortizationSchedule schedule = generator.generateAmortizationSchedule(validSampleAmortizationParams());
		assertSchedule(schedule);
	}
	
	private static void assertSchedule(final AmortizationSchedule schedule) {
		assertNotNull(schedule);
		assertEquals("Schedule should include as many payments as months in term",
				schedule.getParameters().getTerm().getMonthCount(), schedule.getPayments().size(), 0);
		assertEquals("Unscheduled balance (remaining principal) should amount to zero",
				schedule.getRemainingPrincipal(), Amount.ZERO);
		assertPayments(schedule);
	}
	
	private static void assertPayments(final AmortizationSchedule schedule) {
		final List<AmortizationSchedulePaymentDetail> payments = schedule.getPayments();
		for(int index = 0; index < payments.size(); index++) {
			final Amount remainingPrincipalBeforePayment = index != 0 ? payments.get(index - 1).getRemainingPrincipal()
					: schedule.getParameters().getLoanAmount();
			assertPayment(payments.get(index), index, remainingPrincipalBeforePayment);
		}
	}
	
	private static void assertPayment(final AmortizationSchedulePaymentDetail payment, int index,
			final Amount remainingPrincipalBeforePayment) {
		assertNotNull("Payment due date must be set, payment index: " + index, payment.getPaymentDueDate());
		assertEquals("Payment principal must be equal to monthly payment minus interest, payment index: " + index,
				payment.getPrincipal(), payment.getPayment().minus(payment.getInterest()));
		assertEquals("Payment interest must apply month interest rate to remaining principal, payment index: " + index,
				payment.getInterest(), payment.getInterestRate().multipliedBy(remainingPrincipalBeforePayment));
	}
	
	private static AmortizationScheduleParameters validSampleAmortizationParams() {
		return AmortizationScheduleParameters.builder()
			.loanAmount(new Amount(100000))
			.term(Period.months(24))
			.periodicInterestRates(Arrays.asList(
						periodicInterestRate(Twelve_Percent, Twelve_Months, Months.January.year(2018)),
						periodicInterestRate(Ten_Percent, Twelve_Months, Months.January.year(2019))
					))
			.build();
	}
	
	private static PeriodicInterestRate periodicInterestRate(final Double rate, final Integer monthCount,
			final Month initialMonth) {
		return PeriodicInterestRate.builder()
				.interestRate(new InterestRate(rate))
				.period(Period.months(monthCount, initialMonth))
				.build();
	}
}
