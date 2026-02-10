package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Transacao;
import com.example.instantpaymentsystem.interfaceadapter.gateway.TransactionGateway;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionGatewayImpl implements TransactionGateway {

    // TODO: Inject MongoRepository when ready
    // private TransactionRepository repository;

    @Override
    public Transacao save(Transacao transacao) {
        // TODO: Implement save logic with repository
        // return repository.save(transacao);
        return transacao;
    }

    @Override
    public Optional<Transacao> findById(UUID id) {
        // TODO: Implement findById logic with repository
        // return repository.findById(id);
        return Optional.empty();
    }
}

