package com.example.paymentgateway.payment.entity;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.common.enums.PaymentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Table(name = "payments")
@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private OrderRecord order;
	
	@Column(nullable = false)
	private UUID merchandId;
	
	@Embedded
	private Money amount;
	
	@Column(nullable = false, length = 100)
	private String idempotencyKey;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private PaymentStatus status = PaymentStatus.CREATED;
	
	@Column(nullable = false)
	private PaymentMethod method;
	
	@Column(length = 100)
	private String bankReference;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "method_details", columnDefinition = "jsonb")
	private Map<String, Object> methodDetails;
	
	@Column(length = 100)
	private String errorCode;
	
	@Column(length = 250)
	private String errorDescription;
	
	private Instant authorizedAt;
	
	private Instant capturedAt;
	
	private Instant failedAt;
	
	private Instant refundedAt;
	
	private Instant settledAt;
}
