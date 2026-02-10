package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.QueryTransactionUseCase;
import com.example.instantpaymentsystem.domain.entities.Transacao;
import com.example.instantpaymentsystem.interfaceadapter.gateway.TransactionGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueryTransactionUseCaseImpl implements QueryTransactionUseCase {

    @Autowired
    private TransactionGateway transactionGateway;

    @Override
    public void execute(String transacaoId) {
        UUID id = UUID.fromString(transacaoId);
        Optional<Transacao> transacao = transactionGateway.findById(id);

        if (transacao.isEmpty()) {
            throw new IllegalArgumentException("Transação não encontrada com ID: " + transacaoId);
        }

        // Log ou retorna a transação encontrada
        // Pode ser melhorado para retornar a transação
    }
}

