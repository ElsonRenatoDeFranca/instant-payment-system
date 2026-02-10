package com.example.instantpaymentsystem.application.mapper;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.application.dto.ChavePixDTO;

public class ChavePixMapper {
    public static ChavePixDTO toDTO(ChavePix chavePix) {
        ChavePixDTO dto = new ChavePixDTO();
        dto.setId(chavePix.getId() != null ? chavePix.getId().toString() : null);
        dto.setTipo(chavePix.getTipo());
        dto.setValor(chavePix.getValor());
        dto.setContaId(chavePix.getContaId() != null ? chavePix.getContaId().toString() : null);
        return dto;
    }
}
