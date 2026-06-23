package com.example.paymentgateway.operations.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.enums.WebhookEventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "webhook_event")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebhookEvent extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private UUID merchantId;
	
	@Column(nullable = false, length = 100)
	private String eventType;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> payload;
	
	@Column(nullable = false)
	private String targetUrl;
	
	@Column(nullable = false)
	private String signature;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private WebhookEventStatus status = WebhookEventStatus.PENDING;
	
	@Column(nullable = false)
	private Integer attempts = 0;
	
	private Instant nextRetryAt;
	
	private Instant lastAttemptAt;
	
	private Integer lastResponseCode;
	
	@Column(length = 1000)
	private String lastResponseBody;
	
	private Instant deliveredAt;
}
