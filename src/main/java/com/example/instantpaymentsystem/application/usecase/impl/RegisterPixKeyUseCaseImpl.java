package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.RegisterPixKeyUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RegisterPixKeyUseCaseImpl implements RegisterPixKeyUseCase {

    @Autowired
    private PixKeyGateway pixKeyGateway;

    @Override
    public void execute(String tipo, String valor, String contaId) {
        ChavePix chavePix = new ChavePix();
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipo(tipo);
        chavePix.setValor(valor);
        chavePix.setContaId(UUID.fromString(contaId));

        pixKeyGateway.save(chavePix);
    }
}

