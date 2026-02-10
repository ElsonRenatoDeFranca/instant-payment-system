package com.example.instantpaymentsystem.application.mapper;

import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.application.dto.ContaDTO;

public class ContaMapper {
    public static ContaDTO toDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId().toString());
        dto.setNumero(conta.getNumero());
        dto.setAgencia(conta.getAgencia());
        dto.setSaldo(conta.getSaldo());
        dto.setUsuarioId(conta.getUsuarioId().toString());
        return dto;
    }
}

