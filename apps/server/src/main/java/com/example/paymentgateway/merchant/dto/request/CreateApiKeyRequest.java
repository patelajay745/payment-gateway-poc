package com.example.paymentgateway.merchant.dto.request;

import com.example.paymentgateway.common.enums.Environment;

public record CreateApiKeyRequest(
		Environment environment
) {

}
