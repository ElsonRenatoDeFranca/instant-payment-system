package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Usuario;
import com.example.instantpaymentsystem.interfaceadapter.gateway.UserGateway;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserGatewayImpl implements UserGateway {

    // TODO: Inject MongoRepository when ready
    // private UserRepository repository;

    @Override
    public Usuario save(Usuario usuario) {
        // TODO: Implement save logic with repository
        // return repository.save(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        // TODO: Implement findById logic with repository
        // return repository.findById(id);
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        // TODO: Implement findAll logic with repository
        // return repository.findAll();
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {
        // TODO: Implement deleteById logic with repository
        // repository.deleteById(id);
    }
}

