package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.enums.BusinessType;
import com.example.paymentgateway.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchants",
		indexes = {
				@Index(name = "idx_merchant_status", columnList = "status")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 200)
	private String name;
	
	@Column(nullable = false, unique = true, length = 200)
	private String email;
	
	@Column(length = 20)
	private String contactNumber;
	
	@Enumerated(EnumType.STRING)
	private BusinessType businessType;
	
	@Column(length = 200)
	private String businessName;
	
	private String gstId;
	
	@Column(length = 200)
	private String websiteUrl;
	
	private String panId;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 200)
	private MerchantStatus status = MerchantStatus.PENDING_KYC;
	
	@Column(length = 200)
	private String settlementBankAccountNumber;
	
	@Column(length = 200)
	private String settlementAccountHolderName;
	
	@Column(length = 200)
	private String settlementIfsc;
	
	private String createdBy;
	
	@Column(updatable = false)
	@CreatedDate
	private Instant createdAt;
	
	@Column(updatable = false)
	@UpdateTimestamp
	private Instant updatedAt;
}
