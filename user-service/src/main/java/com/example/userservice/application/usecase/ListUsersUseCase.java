package com.example.userservice.application.usecase;

import com.example.common.dto.UsuarioDTO;
import java.util.List;

public interface ListUsersUseCase {
    List<UsuarioDTO> execute();
}

