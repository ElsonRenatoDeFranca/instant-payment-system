package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PixKeyGatewayImpl implements PixKeyGateway {

    // TODO: Inject MongoRepository when ready
    // private PixKeyRepository repository;

    @Override
    public ChavePix save(ChavePix chavePix) {
        // TODO: Implement save logic with repository
        // return repository.save(chavePix);
        return chavePix;
    }

    @Override
    public Optional<ChavePix> findById(UUID id) {
        // TODO: Implement findById logic with repository
        // return repository.findById(id);
        return Optional.empty();
    }

    @Override
    public Optional<ChavePix> findByValor(String valor) {
        // TODO: Implement findByValor logic with repository
        // return repository.findByValor(valor);
        return Optional.empty();
    }

    @Override
    public List<ChavePix> findAll() {
        // TODO: Implement findAll logic with repository
        // return repository.findAll();
        return List.of();
    }

    @Override
    public List<ChavePix> findByContaId(UUID contaId) {
        // TODO: Implement findByContaId logic with repository
        // return repository.findByContaId(contaId);
        return List.of();
    }

    @Override
    public List<ChavePix> findByUsuarioId(UUID usuarioId) {
        // TODO: Implement findByUsuarioId logic with repository
        // This requires joining with Conta and then with ChavePix
        // return repository.findByContaId_UsuarioId(usuarioId);
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {
        // TODO: Implement deleteById logic with repository
        // repository.deleteById(id);
    }
}

