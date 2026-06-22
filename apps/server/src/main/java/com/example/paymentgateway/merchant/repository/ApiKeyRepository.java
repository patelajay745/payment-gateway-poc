package com.example.paymentgateway.merchant.repository;

import com.example.paymentgateway.common.enums.Environment;
import com.example.paymentgateway.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

	List<ApiKey> findAllByMerchantId(UUID merchantId);

	List<ApiKey> findByMerchant_Id(UUID merchantId);

	List<ApiKey> findAllByMerchantIdAndEnabledTrue(UUID merchantId);

	List<ApiKey> findAllByMerchantIdAndEnvironment(UUID merchantId, Environment environment);

	Optional<ApiKey> findByIdAndMerchantId(UUID id, UUID merchantId);

	Optional<ApiKey> findByKeyId(String keyId);

	@Query("SELECT a FROM ApiKey a JOIN FETCH a.merchant WHERE a.keyId = :keyId")
	Optional<ApiKey> findByKeyIdWithMerchant(@Param("keyId") String keyId);
}
