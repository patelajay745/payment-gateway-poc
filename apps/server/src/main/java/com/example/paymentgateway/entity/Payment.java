package com.example.paymentgateway.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "payments")
@Entity
public class Payment {

    private UUID id;
    private OrderRecord order;

    private UUID merchandId;

    private Money amount;

    private String idempotencyKey;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updatedAt;


    
    

    
}
