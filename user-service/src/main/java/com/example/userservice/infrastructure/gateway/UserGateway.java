package com.example.userservice.infrastructure.gateway;

import com.example.common.dto.UsuarioDTO;
import java.util.List;
import java.util.Optional;

public interface UserGateway {
    void save(UsuarioDTO usuario);
    Optional<UsuarioDTO> findById(String id);
    List<UsuarioDTO> findAll();
}

