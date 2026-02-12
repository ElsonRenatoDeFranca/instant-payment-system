package com.example.accountservice.application.usecase;

import com.example.common.dto.ContaDTO;
import java.util.Optional;

public interface QueryAccountUseCase {
    Optional<ContaDTO> execute(String contaId);
}
