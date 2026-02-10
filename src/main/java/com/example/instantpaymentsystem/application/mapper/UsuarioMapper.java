package com.example.instantpaymentsystem.application.mapper;

import com.example.instantpaymentsystem.domain.entities.Usuario;
import com.example.instantpaymentsystem.application.dto.UsuarioDTO;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId().toString());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf().getValue());
        return dto;
    }
}

