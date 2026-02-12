package com.example.pixservice.application.mapper;

import com.example.common.dto.ChavePixDTO;
import com.example.pixservice.domain.entities.ChavePix;
import java.util.UUID;

public class ChavePixMapper {
    private ChavePixMapper() {}

    public static ChavePixDTO toDTO(ChavePix entity) {
        if (entity == null) return null;
        return new ChavePixDTO(
            entity.getId() != null ? entity.getId().toString() : null,
            entity.getTipo(),
            entity.getValor(),
            entity.getContaId() != null ? entity.getContaId().toString() : null
        );
    }
    public static ChavePix toEntity(ChavePixDTO dto) {
        if (dto == null) return null;
        return new ChavePix(
            dto.getId() != null ? UUID.fromString(dto.getId()) : null,
            dto.getTipo(),
            dto.getValor(),
            dto.getContaId() != null ? UUID.fromString(dto.getContaId()) : null
        );
    }
}
