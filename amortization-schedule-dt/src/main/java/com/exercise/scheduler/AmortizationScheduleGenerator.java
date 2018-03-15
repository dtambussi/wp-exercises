package com.exercise.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.exercise.model.AmortizationSchedule;
import com.exercise.model.AmortizationScheduleParameters;
import com.exercise.model.AmortizationSchedulePaymentDetail;
import com.exercise.model.Amount;
import com.exercise.model.InterestRate;
import com.exercise.model.Month;
import com.exercise.model.PeriodicInterestRate;

@Component
public class AmortizationScheduleGenerator {
	
	public AmortizationSchedule generateAmortizationSchedule(final AmortizationScheduleParameters params) {
		final AmortizationSchedule schedule = AmortizationSchedule.builder()
				.parameters(params)
				.payments(new ArrayList<>())
				.build();
		for (final PeriodicInterestRate periodicInterestRate : params.getPeriodicInterestRates()) {
			schedulePeriodPayments(periodicInterestRate, schedule);
		}
		return schedule.withRoundedFinalBalance();
	}
	
	/**
	 * Schedule payments for a period with some interest rate.
	 * 
	 * Formula reference: https://www.vertex42.com/ExcelArticles/amortization-calculation.html
	 */
	private AmortizationSchedule schedulePeriodPayments(final PeriodicInterestRate periodicInterestRate,
			final AmortizationSchedule schedule) {
		// Main parameters for calculation
		Amount remainingPrincipal = schedule.getRemainingPrincipal();
		final InterestRate irpm = periodicInterestRate.getInterestRatePerMonth();
		// determine number of months missing payment schedule, initially equal to whole loan term in months
		final Amount nm = new Amount(schedule.countUnscheduledMonths());
		// (irpm + 1)^nm = (interest rate per month + 1) to the power of (number of months remaining in loan)
		final Amount irpm_plus_1__exp_nm = (irpm.plusOne()).exp(nm);
		// Calculate monthly payment
		final Amount monthlyPayment = remainingPrincipal.multipliedBy(irpm.toAmount())
										.multipliedBy(irpm_plus_1__exp_nm)
										.dividedBy(irpm_plus_1__exp_nm.minusOne());
		
		// Schedule payment for each month in period
		final List<Month> monthsInPeriod = periodicInterestRate.getPeriod().getMonths();
		for (final Month month : monthsInPeriod) {
			final Amount interest = irpm.multipliedBy(remainingPrincipal);
			final Amount principal = monthlyPayment.minus(interest);
			remainingPrincipal = remainingPrincipal.minus(principal);
			// Build scheduled payment with details
			final AmortizationSchedulePaymentDetail scheduledPaymentDetail = AmortizationSchedulePaymentDetail.builder()
					.paymentDueDate(month.firstDay())
					.payment(monthlyPayment)
					.principal(principal)
					.interest(interest)
					.interestRate(irpm)
					.remainingPrincipal(remainingPrincipal)
					.build();
			schedule.addPayment(scheduledPaymentDetail);
		}		
		return schedule;
	}
}
