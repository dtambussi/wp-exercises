package com.exercise.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AmortizationSchedulePaymentDetail {
	
	private Date paymentDueDate;
	private InterestRate interestRate;
	private Amount payment, principal, interest, remainingPrincipal;
}
