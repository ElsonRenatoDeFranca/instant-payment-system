package com.example.userservice.application.usecase.impl;

import com.example.common.dto.UsuarioDTO;
import com.example.userservice.application.usecase.RegisterUserUseCase;
import com.example.userservice.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    @Autowired
    private UserGateway userGateway;
    @Override
    public UsuarioDTO execute(UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(java.util.UUID.randomUUID().toString());
        userGateway.save(usuarioDTO);
        return usuarioDTO;
    }
}
