package com.example.paymentgateway.payment.simulator;

import com.example.paymentgateway.common.enums.ChaosMode;
import com.example.paymentgateway.common.enums.PaymentStatus;
import com.example.paymentgateway.common.utils.RandomizerUtil;
import com.example.paymentgateway.payment.entity.Payment;
import com.example.paymentgateway.payment.repository.PaymentRepository;
import com.example.paymentgateway.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankCallbackSimulator {
	
	private final PaymentRepository paymentRepository;
	
	private final PaymentService paymentService;
	
	private final SimulatorConfig simulatorConfig;
	
	@Scheduled(fixedDelayString = "${payment.simulator.poll-interval-ms:5000}")
	public void processCallbacks() {
		Instant globalWindow = Instant.now().minusSeconds(1);

		List<Payment> candidates = paymentRepository.findByStatusAndCreatedAtBefore(PaymentStatus.AUTHORIZING, globalWindow);

		log.info("Simulator tick — found {} AUTHORIZING payment(s)", candidates.size());

		if (candidates.isEmpty()) return;

		for (Payment payment : candidates) {
			simulateCallback(payment);
		}
	}
	
	private void simulateCallback(Payment payment) {
		SimulatorConfig.MethodSimulatorConfig methodConfig = simulatorConfig.configFor(payment.getMethod());

		Instant dueAt = dueAt(payment, methodConfig);

		if (Instant.now().isBefore(dueAt)) {
			log.info("Simulator: payment [{}] not due yet, due at {}", payment.getId(), dueAt);
			return;
		}

		ChaosMode chaosMode = simulatorConfig.getChaosMode();
		log.info("Simulator: processing payment [{}] with chaosMode={}", payment.getId(), chaosMode);

		switch (chaosMode) {
			case SUCCESS -> resolve(payment, true);
			case FAILURE -> resolve(payment, false);
			case TIMEOUT -> log.info("Simulator: payment [{}] timed out (TIMEOUT mode)", payment.getId());
			case NORMAl, SLOW -> resolve(payment, shouldApprove(payment, methodConfig));
		}
	}
	
	private void resolve(Payment payment, boolean approve) {
		
		if (approve) {
			String bankRef = "SIM_BANK_REF" + RandomizerUtil.randomBase64(8);
			paymentService.resolveAuthorization(payment.getId(), true, bankRef, null, null);
		} else {
			paymentService.resolveAuthorization(payment.getId(), false, null, "SIM_BANK_ERROR_CODE", "Simulated Bank" +
					                                                                                         " " +
					                                                                                         "Declined");
		}
	}
	
	private boolean shouldApprove(Payment payment, SimulatorConfig.MethodSimulatorConfig methodConfig) {
		int bucket = Math.abs(payment.getId().hashCode()) % 100;
		
		return bucket < methodConfig.getSuccessRate();
	}
	
	private Instant dueAt(Payment payment, SimulatorConfig.MethodSimulatorConfig methodSimulatorConfig) {
		int range = methodSimulatorConfig.getMaxDelaySeconds() - methodSimulatorConfig.getMinDelaySeconds();

		int delaySeconds =
				methodSimulatorConfig.getMinDelaySeconds() + Math.abs(payment.getId().hashCode()) % (range + 1);

		if (simulatorConfig.getChaosMode() == ChaosMode.SLOW) {
			delaySeconds *= 2;
		}

		return payment.getCreatedAt().plusSeconds(delaySeconds);
	}
}
