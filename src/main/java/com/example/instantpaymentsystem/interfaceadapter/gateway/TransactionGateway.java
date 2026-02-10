package com.example.instantpaymentsystem.interfaceadapter.gateway;

import com.example.instantpaymentsystem.domain.entities.Transacao;
import java.util.Optional;
import java.util.UUID;

public interface TransactionGateway {
    Transacao save(Transacao transacao);
    Optional<Transacao> findById(UUID id);
}

