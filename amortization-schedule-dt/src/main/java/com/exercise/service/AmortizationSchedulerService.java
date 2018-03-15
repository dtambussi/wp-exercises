package com.exercise.service;

import org.springframework.stereotype.Component;

import com.exercise.dto.AmortizationSchedulePaymentDTO;
import com.exercise.dto.AmortizationScheduleRequestDTO;
import com.exercise.dto.AmortizationScheduleRequestDTO.InterestPercentagePerPeriodDTO;
import com.exercise.dto.AmortizationScheduleResponseDTO;
import com.exercise.model.AmortizationSchedule;
import com.exercise.model.AmortizationScheduleParameters;
import com.exercise.model.AmortizationSchedulePaymentDetail;
import com.exercise.model.Amount;
import com.exercise.model.InterestRate;
import com.exercise.model.Month;
import com.exercise.model.Months;
import com.exercise.model.PeriodicInterestRate;
import com.exercise.scheduler.AmortizationScheduleGenerator;
import com.exercise.validator.AmortizationScheduleRequestValidator;

import static com.exercise.model.Period.months;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AmortizationSchedulerService {
	
	private static final Month Default_Scheduling_Start_Month = Months.January.year(2018);
	
	private AmortizationScheduleGenerator amortizationScheduleGenerator;

	private AmortizationScheduleRequestValidator amortizationScheduleRequestValidator;
	
	public AmortizationSchedulerService(final AmortizationScheduleRequestValidator amortizationScheduleRequestValidator,
			final AmortizationScheduleGenerator amortizationScheduleGenerator) {
		this.amortizationScheduleRequestValidator = amortizationScheduleRequestValidator;
		this.amortizationScheduleGenerator = amortizationScheduleGenerator;
	}
	
	public AmortizationScheduleResponseDTO generateAmortizationSchedule(final AmortizationScheduleRequestDTO request) {
		amortizationScheduleRequestValidator.validate(request);
		final AmortizationScheduleParameters params = AmortizationScheduleParameters.builder()
				.loanAmount(new Amount(request.getLoanAmount()))
				.term(months(request.getTermInMonths()))
				.periodicInterestRates(toPeriodicInterestRates(request.getPeriodicInterest()))
				.build();
		return toResponseDTO(amortizationScheduleGenerator.generateAmortizationSchedule(params));
	}
	
	private List<PeriodicInterestRate> toPeriodicInterestRates(
			final List<InterestPercentagePerPeriodDTO> interestPercentagesPerPeriod) {
		Month periodStartingMonth = Default_Scheduling_Start_Month;
		final List<PeriodicInterestRate> periodicInterestRates = new LinkedList<>();
		for (final InterestPercentagePerPeriodDTO interestPercentagePerPeriod : interestPercentagesPerPeriod) {
			final PeriodicInterestRate periodicInterestRate = toPeriodicInterestRate(interestPercentagePerPeriod,
					periodStartingMonth);
			periodicInterestRates.add(periodicInterestRate);
			periodStartingMonth = Months.nextMonthGiven(periodicInterestRate.getPeriod().getLastMonth());
		}
		return periodicInterestRates;
	}
	
	private PeriodicInterestRate toPeriodicInterestRate(
			final InterestPercentagePerPeriodDTO interestPercentagePerPeriod, final Month periodStartingMonth) {	
		return PeriodicInterestRate.builder()
				.period(months(interestPercentagePerPeriod.getMonthCount(), periodStartingMonth))
				.interestRate(new InterestRate(interestPercentagePerPeriod.getInterestPercentage() / 100))
				.build();
	}
	
	private AmortizationScheduleResponseDTO toResponseDTO(final AmortizationSchedule amortizationSchedule) {
		return AmortizationScheduleResponseDTO.builder()
				.schedulePayments(toSchedulePayments(amortizationSchedule.getPayments()))
				.build();
	}

	private List<AmortizationSchedulePaymentDTO> toSchedulePayments(final List<AmortizationSchedulePaymentDetail> payments) {
		return payments.stream().map(this::toSchedulePayment).collect(Collectors.toList());
	}
	
	private AmortizationSchedulePaymentDTO toSchedulePayment(final AmortizationSchedulePaymentDetail paymentDetail) {
		return AmortizationSchedulePaymentDTO.builder()
				.paymentDueDate(paymentDetail.getPaymentDueDate())
				.payment(paymentDetail.getPayment().doubleValue())
				.principal(paymentDetail.getPrincipal().doubleValue())
				.interest(paymentDetail.getInterest().doubleValue())
				.remainingPrincipal(paymentDetail.getRemainingPrincipal().doubleValue())
				.build();
	}
}
