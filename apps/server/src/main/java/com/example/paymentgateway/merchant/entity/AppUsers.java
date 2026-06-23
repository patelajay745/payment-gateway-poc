package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "app_users",
		indexes = {
				@Index(name = "idx_app_users_merchant_id", columnList = "merchant_id")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUsers extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String passwordHashed;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;
}
