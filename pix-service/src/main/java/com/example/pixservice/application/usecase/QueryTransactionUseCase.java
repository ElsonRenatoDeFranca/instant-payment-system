package com.example.pixservice.application.usecase;

import com.example.common.dto.TransacaoDTO;
import java.util.Optional;

public interface QueryTransactionUseCase {
    Optional<TransacaoDTO> execute(String transacaoId);
}
