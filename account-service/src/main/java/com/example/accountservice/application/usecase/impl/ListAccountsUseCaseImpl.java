package com.example.accountservice.application.usecase.impl;

import com.example.accountservice.application.usecase.ListAccountsUseCase;
import com.example.common.dto.ContaDTO;
import com.example.accountservice.infrastructure.gateway.AccountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListAccountsUseCaseImpl implements ListAccountsUseCase {
    @Autowired
    private AccountGateway accountGateway;
    @Override
    public List<ContaDTO> execute() {
        return accountGateway.findAll();
    }
}
