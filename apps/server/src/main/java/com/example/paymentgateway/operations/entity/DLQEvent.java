package com.example.paymentgateway.operations.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
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
@Table(name = "dlq_events")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DLQEvent extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "webhook_event_id")
	private WebhookEvent webhookEvent;
	
	@Column(nullable = false, length = 100)
	private String merchantId;
	
	@Column(nullable = false, length = 100)
	private String finalError;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> payload;
	
	private Instant movedAt;
	
	private Instant replayedAt;
}
