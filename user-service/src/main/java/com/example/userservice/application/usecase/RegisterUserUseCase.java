package com.example.userservice.application.usecase;

import com.example.common.dto.UsuarioDTO;

public interface RegisterUserUseCase {
    UsuarioDTO execute(UsuarioDTO usuarioDTO);
}

