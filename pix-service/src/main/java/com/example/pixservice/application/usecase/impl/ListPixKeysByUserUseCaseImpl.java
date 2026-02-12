package com.example.pixservice.application.usecase.impl;

import com.example.pixservice.application.usecase.ListPixKeysByUserUseCase;
import com.example.pixservice.domain.entities.ChavePix;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ListPixKeysByUserUseCaseImpl implements ListPixKeysByUserUseCase {
    @Override
    public List<ChavePix> execute(UUID usuarioId) {
        // Example: return empty list or mock data
        return new ArrayList<>();
    }
}

