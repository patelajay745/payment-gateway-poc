package com.example.paymentgateway.payment.entity;

import com.example.paymentgateway.common.enums.PaymentActor;
import com.example.paymentgateway.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transition_logs",
		indexes = {
				@Index(
						name = "idx_payment_log_payment_id",
						columnList = "payment_id"
				)
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTransitionLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "from_status", length = 30, nullable = false)
	private PaymentStatus fromStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "to_status", length = 30, nullable = false)
	private PaymentStatus toStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "event", length = 30, nullable = false)
	private PaymentStatus event;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private PaymentActor actor;
	
	@Column(nullable = false)
	private String reason;
	
	private Instant occurredAt;
}
