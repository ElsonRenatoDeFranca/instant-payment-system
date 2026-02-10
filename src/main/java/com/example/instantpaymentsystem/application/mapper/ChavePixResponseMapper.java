package com.example.instantpaymentsystem.application.mapper;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.application.dto.ChavePixResponseDTO;

public class ChavePixResponseMapper {
    public static ChavePixResponseDTO toDTO(ChavePix chavePix) {
        ChavePixResponseDTO dto = new ChavePixResponseDTO();
        dto.setId(chavePix.getId() != null ? chavePix.getId().toString() : null);
        dto.setTipo(chavePix.getTipo());
        dto.setValor(chavePix.getValor());
        dto.setContaId(chavePix.getContaId() != null ? chavePix.getContaId().toString() : null);
        return dto;
    }
}

