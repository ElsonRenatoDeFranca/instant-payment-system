package com.example.pixservice.application;

import com.example.pixservice.domain.PixTransaction;
import org.springframework.stereotype.Service;

@Service
public class PixService {
    public PixTransaction createTransaction(String from, String to, double amount) {
        PixTransaction tx = new PixTransaction();
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setAmount(amount);
        tx.setTransactionId("TX-" + System.currentTimeMillis());
        return tx;
    }
}

