package com.example.accountservice.application.usecase.impl;

import com.example.accountservice.application.usecase.QueryAccountUseCase;
import com.example.common.dto.ContaDTO;
import com.example.accountservice.infrastructure.gateway.AccountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class QueryAccountUseCaseImpl implements QueryAccountUseCase {
    @Autowired
    private AccountGateway accountGateway;
    @Override
    public Optional<ContaDTO> execute(String contaId) {
        return accountGateway.findById(contaId);
    }
}
