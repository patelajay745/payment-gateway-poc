package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customers",
		indexes = {
				
				@Index(name = "idx_customer_merchant_id", columnList = "merchant_id"),
				@Index(name = "idx_customer_email", columnList = "email")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 200)
	private String name;
	
	@Column(nullable = false, length = 200)
	private String email;
	
	@Column(nullable = false, length = 20)
	private String contactNumber;
	
	private String gstId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;
	
	private Instant deletedAt;
}
