package com.example.userservice.application.mapper;

import com.example.common.dto.UsuarioDTO;

public class UsuarioMapper {
    private UsuarioMapper() {}

    public static UsuarioDTO toDTO(UsuarioDTO usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getCpf()
        );
    }
}
