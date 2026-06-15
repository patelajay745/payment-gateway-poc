package com.example.paymentgateway.merchant.repository;

import com.example.paymentgateway.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

}
