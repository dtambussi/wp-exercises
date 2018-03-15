package com.exercise.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AmortizationScheduleResponseDTO {
	
	private List<AmortizationSchedulePaymentDTO> schedulePayments;
}
