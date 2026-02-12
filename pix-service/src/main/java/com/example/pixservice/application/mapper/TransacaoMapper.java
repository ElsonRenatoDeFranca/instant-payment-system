package com.example.pixservice.application.mapper;

import com.example.common.dto.TransacaoDTO;

public class TransacaoMapper {
    private TransacaoMapper() {}

    public static TransacaoDTO toDTO(TransacaoDTO transacao) {
        if (transacao == null) return null;
        return new TransacaoDTO(
            transacao.getId(),
            transacao.getOrigemContaId(),
            transacao.getDestinoContaId(),
            transacao.getValor(),
            transacao.getDataHora()
        );
    }
}
