package com.example.paymentgateway.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "order_record")
public class OrderRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	private UUID merchantId;
	
	@Embedded
	private Money amount;
}
