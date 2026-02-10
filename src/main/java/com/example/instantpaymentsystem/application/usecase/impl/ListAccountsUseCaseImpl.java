package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.ListAccountsUseCase;
import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.interfaceadapter.gateway.AccountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListAccountsUseCaseImpl implements ListAccountsUseCase {

    @Autowired
    private AccountGateway accountGateway;

    @Override
    public List<Conta> execute() {
        return accountGateway.findAll();
    }
}

