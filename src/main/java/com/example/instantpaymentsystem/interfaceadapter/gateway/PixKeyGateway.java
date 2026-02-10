package com.example.instantpaymentsystem.interfaceadapter.gateway;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PixKeyGateway {
    ChavePix save(ChavePix chavePix);
    Optional<ChavePix> findById(UUID id);
    Optional<ChavePix> findByValor(String valor);
    List<ChavePix> findAll();
    List<ChavePix> findByContaId(UUID contaId);
    List<ChavePix> findByUsuarioId(UUID usuarioId);
    void deleteById(UUID id);
}

