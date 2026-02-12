package com.example.frauddetectionservice.infrastructure;

import com.example.frauddetectionservice.domain.FraudResult;
import org.springframework.stereotype.Repository;

@Repository
public class FraudDetectionRepository {
    public FraudResult checkFraud(String transactionId) {
        boolean isFraudulent = Math.random() > 0.5;
        String reason = isFraudulent ? "Fraud detected" : "No fraud";

        return FraudResult.builder()
                .positive(isFraudulent)
                .reason(reason)
                .build();
    }
}
