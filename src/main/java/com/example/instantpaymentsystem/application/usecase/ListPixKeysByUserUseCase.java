package com.example.instantpaymentsystem.application.usecase;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import java.util.List;
import java.util.UUID;

public interface ListPixKeysByUserUseCase {
    List<ChavePix> execute(UUID usuarioId);
}

