package com.exercise.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AmortizationSchedulePaymentDTO {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date paymentDueDate;
	private Double payment, principal, interest, remainingPrincipal;
}
