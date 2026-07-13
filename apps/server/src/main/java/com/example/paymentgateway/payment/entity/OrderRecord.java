package com.example.paymentgateway.payment.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.OrderStatus;
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
@Table(name = "order_records",
		indexes = {
				@Index(name = "idx_order_id_merchant_id", columnList = "id,merchant_id"),
				@Index(name = "idx_order_merchant_id", columnList = "merchant_id")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRecord extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "merchant_id", nullable = false)
	private UUID merchantId;
	
	@Embedded
	private Money amount;
	
	@Column(length = 100)
	private String receipt;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	@Builder.Default
	private OrderStatus orderStatus = OrderStatus.CREATED;
	
	@Column(nullable = false)
	@Builder.Default
	private Integer attempts = 0;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> notes;
	
	@Column(nullable = false)
	private Instant expires;
	
	public void updateOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
}
