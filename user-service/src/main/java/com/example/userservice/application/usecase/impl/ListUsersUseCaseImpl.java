package com.example.userservice.application.usecase.impl;

import com.example.userservice.application.usecase.ListUsersUseCase;
import com.example.common.dto.UsuarioDTO;
import com.example.userservice.infrastructure.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    @Autowired
    private UserGateway userGateway;

    @Override
    public List<UsuarioDTO> execute() {
        return userGateway.findAll();
    }
}
