package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.QueryAccountUseCase;
import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.interfaceadapter.gateway.AccountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueryAccountUseCaseImpl implements QueryAccountUseCase {

    @Autowired
    private AccountGateway accountGateway;

    @Override
    public void execute(String contaId) {
        UUID id = UUID.fromString(contaId);
        Optional<Conta> conta = accountGateway.findById(id);

        if (conta.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada com ID: " + contaId);
        }

        // Log ou retorna a conta encontrada
        // Pode ser melhorado para retornar a conta
    }
}

