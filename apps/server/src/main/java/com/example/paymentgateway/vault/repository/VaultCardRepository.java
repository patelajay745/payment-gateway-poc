package com.example.paymentgateway.vault.repository;

import com.example.paymentgateway.vault.entity.VaultCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VaultCardRepository extends JpaRepository<VaultCard, UUID> {

}
