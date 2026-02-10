package com.example.instantpaymentsystem.interfaceadapter.gateway;

import com.example.instantpaymentsystem.domain.entities.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    List<Usuario> findAll();
    void deleteById(UUID id);
}

