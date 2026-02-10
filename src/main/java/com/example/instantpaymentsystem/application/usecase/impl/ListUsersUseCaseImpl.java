package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.ListUsersUseCase;
import com.example.instantpaymentsystem.domain.entities.Usuario;
import com.example.instantpaymentsystem.interfaceadapter.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    @Autowired
    private UserGateway userGateway;

    @Override
    public List<Usuario> execute() {
        return userGateway.findAll();
    }
}

