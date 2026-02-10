package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.RegisterUserUseCase;
import com.example.instantpaymentsystem.domain.entities.Usuario;
import com.example.instantpaymentsystem.domain.valueobjects.CPF;
import com.example.instantpaymentsystem.interfaceadapter.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    @Autowired
    private UserGateway userGateway;

    @Override
    public void execute(String nome, String cpf) {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome(nome);
        usuario.setCpf(new CPF(cpf));

        userGateway.save(usuario);
    }
}

