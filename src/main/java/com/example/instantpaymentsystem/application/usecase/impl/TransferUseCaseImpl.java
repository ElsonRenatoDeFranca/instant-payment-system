package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.TransferUseCase;
import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.domain.entities.Transacao;
import com.example.instantpaymentsystem.interfaceadapter.gateway.AccountGateway;
import com.example.instantpaymentsystem.interfaceadapter.gateway.TransactionGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferUseCaseImpl implements TransferUseCase {

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private TransactionGateway transactionGateway;

    @Override
    public void execute(String origemContaId, String destinoContaId, double valor) {
        UUID origemId = UUID.fromString(origemContaId);
        UUID destinoId = UUID.fromString(destinoContaId);

        // Buscar contas
        Optional<Conta> contaOrigem = accountGateway.findById(origemId);
        Optional<Conta> contaDestino = accountGateway.findById(destinoId);

        if (contaOrigem.isEmpty() || contaDestino.isEmpty()) {
            throw new IllegalArgumentException("Uma ou ambas as contas não foram encontradas");
        }

        Conta origem = contaOrigem.get();
        Conta destino = contaDestino.get();

        // Validar saldo
        if (origem.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência");
        }

        // Realizar transferência
        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        // Salvar contas atualizadas
        accountGateway.save(origem);
        accountGateway.save(destino);

        // Registrar transação
        Transacao transacao = new Transacao();
        transacao.setId(UUID.randomUUID());
        transacao.setOrigemContaId(origemId);
        transacao.setDestinoContaId(destinoId);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());

        transactionGateway.save(transacao);
    }
}

