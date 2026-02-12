package com.example.accountservice.application;

import com.example.accountservice.domain.Account;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    public Account getAccount(String id) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(1000);
        return account;
    }
}
