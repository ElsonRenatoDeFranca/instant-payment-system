package com.example.accountservice.application.mapper;

import com.example.common.dto.ContaDTO;

public class ContaMapper {
    private ContaMapper() {}

    public static ContaDTO toDTO(ContaDTO conta) {
        // Example: return a new instance if you want to avoid reference sharing
        if (conta == null) return null;
        return new ContaDTO(
            conta.getId(),
            conta.getNumero(),
            conta.getAgencia(),
            conta.getSaldo(),
            conta.getUsuarioId()
        );
    }
}
