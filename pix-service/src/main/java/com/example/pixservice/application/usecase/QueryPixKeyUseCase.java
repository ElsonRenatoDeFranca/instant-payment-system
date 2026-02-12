package com.example.pixservice.application.usecase;

import com.example.pixservice.domain.entities.ChavePix;
import java.util.Optional;
import java.util.UUID;

public interface QueryPixKeyUseCase {
    Optional<ChavePix> execute(UUID chavePixId);
}
// ...existing code...
