package com.exercise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.dto.AmortizationScheduleRequestDTO;
import com.exercise.dto.AmortizationScheduleResponseDTO;
import com.exercise.service.AmortizationSchedulerService;

@RestController
public class ApplicationController {
	
	private AmortizationSchedulerService amortizationSchedulerService;

	public ApplicationController(final AmortizationSchedulerService amortizationSchedulerService) {
		this.amortizationSchedulerService = amortizationSchedulerService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/amortizationScheduleRequest")
	public AmortizationScheduleResponseDTO generateSchedule(final @RequestBody AmortizationScheduleRequestDTO request) {
		return this.amortizationSchedulerService.generateAmortizationSchedule(request);
	}
}
