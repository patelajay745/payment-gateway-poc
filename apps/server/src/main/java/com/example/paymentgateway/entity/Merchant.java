package com.example.paymentgateway.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.example.paymentgateway.common.enums.BusinessType;
import com.example.paymentgateway.common.enums.MerchantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length=200)
    private String name;

    @Column(nullable = false,unique = true,length=200)
    private String email;

    @Column(length=20)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @Column(length=200)
    private String businessName;


    private String gstId;

    @Column(length=200)
    private String websiteUrl;

    private String panId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length=200)
    private MerchantStatus status=MerchantStatus.PENDING_KYC;
    

    @Column(length=200) 
    private String settlementBankAccountNumber;

    @Column(length=200)
    private String settlementAccountHolderName;

    @Column(length=200)
    private String settlementIfsc;

    
    private String createdBy;

    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(updatable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}
