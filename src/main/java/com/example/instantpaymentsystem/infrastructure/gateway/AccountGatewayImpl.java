package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.interfaceadapter.gateway.AccountGateway;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountGatewayImpl implements AccountGateway {

    // TODO: Inject MongoRepository when ready
    // private AccountRepository repository;

    @Override
    public Conta save(Conta conta) {
        // TODO: Implement save logic with repository
        // return repository.save(conta);
        return conta;
    }

    @Override
    public Optional<Conta> findById(UUID id) {
        // TODO: Implement findById logic with repository
        // return repository.findById(id);
        return Optional.empty();
    }

    @Override
    public List<Conta> findAll() {
        // TODO: Implement findAll logic with repository
        // return repository.findAll();
        return List.of();
    }

    @Override
    public List<Conta> findByUsuarioId(UUID usuarioId) {
        // TODO: Implement findByUsuarioId logic with repository
        // return repository.findByUsuarioId(usuarioId);
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {
        // TODO: Implement deleteById logic with repository
        // repository.deleteById(id);
    }
}

