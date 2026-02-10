package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.QueryPixKeyUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueryPixKeyUseCaseImpl implements QueryPixKeyUseCase {

    @Autowired
    private PixKeyGateway pixKeyGateway;

    @Override
    public void execute(String chavePixId) {
        UUID id = UUID.fromString(chavePixId);
        Optional<ChavePix> chavePix = pixKeyGateway.findById(id);

        if (chavePix.isEmpty()) {
            throw new IllegalArgumentException("Chave Pix não encontrada com ID: " + chavePixId);
        }

        // Log ou retorna a chave Pix encontrada
        // Pode ser melhorado para retornar a chave
    }
}

