package com.exercise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor
public class AmortizationSchedule {

	private AmortizationScheduleParameters parameters;
	private List<AmortizationSchedulePaymentDetail> payments;
	
	public Amount getRemainingPrincipal() { // last scheduled payment registers remaining principal
		return payments.isEmpty() ? getInitialLoanAmount() : mostRecentlyScheduledPayment().getRemainingPrincipal();
	}
	
	public Integer countUnscheduledMonths() {
		return loanTermInMonths() - scheduledPaymentsCount();
	}
	
	public AmortizationSchedule addPayment(final AmortizationSchedulePaymentDetail payment) {
		this.payments.add(payment);
		return this;
	}
	
	public AmortizationSchedule withRoundedFinalBalance() {
		if (!payments.isEmpty()) { 
			final AmortizationSchedulePaymentDetail mostRecentlyScheduledPayment = mostRecentlyScheduledPayment();
			final Amount remainingPrincipal = mostRecentlyScheduledPayment().getRemainingPrincipal();
			mostRecentlyScheduledPayment.setRemainingPrincipal(remainingPrincipal.roundDown(0));
		}
		return this;
	}
	
	private Amount getInitialLoanAmount() {
		return parameters != null ? parameters.getLoanAmount() : null;
	}
	
	private Integer scheduledPaymentsCount() {
		return payments.size();
	}
	
	private Integer loanTermInMonths() {
		return parameters != null ? parameters.getTerm().getMonthCount() : null;
	}
	
	private AmortizationSchedulePaymentDetail mostRecentlyScheduledPayment() {
		return payments.stream().reduce((a, b) -> b).orElse(null);
	}
}
