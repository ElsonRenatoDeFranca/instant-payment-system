package com.example.instantpaymentsystem.interfaceadapter.gateway;

import com.example.instantpaymentsystem.domain.entities.Conta;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountGateway {
    Conta save(Conta conta);
    Optional<Conta> findById(UUID id);
    List<Conta> findAll();
    List<Conta> findByUsuarioId(UUID usuarioId);
    void deleteById(UUID id);
}

