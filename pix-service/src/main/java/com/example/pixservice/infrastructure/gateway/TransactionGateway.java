package com.example.pixservice.infrastructure.gateway;

import com.example.common.dto.TransacaoDTO;
import java.util.List;
import java.util.Optional;

public interface TransactionGateway {
    void save(TransacaoDTO transacao);
    Optional<TransacaoDTO> findById(String id);
    List<TransacaoDTO> findAll();
}

