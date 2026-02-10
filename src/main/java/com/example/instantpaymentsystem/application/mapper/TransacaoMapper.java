package com.example.instantpaymentsystem.application.mapper;

import com.example.instantpaymentsystem.domain.entities.Transacao;
import com.example.instantpaymentsystem.application.dto.TransacaoDTO;

public class TransacaoMapper {
    public static TransacaoDTO toDTO(Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(transacao.getId() != null ? transacao.getId().toString() : null);
        dto.setOrigemContaId(transacao.getOrigemContaId() != null ? transacao.getOrigemContaId().toString() : null);
        dto.setDestinoContaId(transacao.getDestinoContaId() != null ? transacao.getDestinoContaId().toString() : null);
        dto.setValor(transacao.getValor());
        dto.setDataHora(transacao.getDataHora() != null ? transacao.getDataHora().toString() : null);
        return dto;
    }
}

