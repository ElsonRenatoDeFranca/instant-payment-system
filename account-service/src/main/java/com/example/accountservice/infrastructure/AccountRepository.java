package com.example.accountservice.infrastructure;

import com.example.accountservice.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    public Account findById(String id) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(1000);
        return account;
    }
}

