package com.example.pixservice.application.usecase;

import com.example.pixservice.domain.entities.ChavePix;
import java.util.List;
import java.util.UUID;

public interface ListPixKeysByUserUseCase {
    List<ChavePix> execute(UUID usuarioId);
}

