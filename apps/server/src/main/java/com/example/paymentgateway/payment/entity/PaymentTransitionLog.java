package com.example.paymentgateway.payment.entity;

import com.example.paymentgateway.common.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transition_logs")
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
	private String toStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "event", length = 30, nullable = false)
	private PaymentStatus event;
	
	@Column(nullable = false, length = 100)
	private String actor;
	
	@Column(nullable = false)
	private String reason;
	
	private Instant occurredAt;
}
