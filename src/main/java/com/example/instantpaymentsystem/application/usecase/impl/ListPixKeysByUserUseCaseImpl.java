package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.ListPixKeysByUserUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListPixKeysByUserUseCaseImpl implements ListPixKeysByUserUseCase {

    @Autowired
    private PixKeyGateway pixKeyGateway;

    @Override
    public List<ChavePix> execute(UUID usuarioId) {
        return pixKeyGateway.findByUsuarioId(usuarioId);
    }
}

