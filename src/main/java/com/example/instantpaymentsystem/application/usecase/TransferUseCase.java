package com.example.instantpaymentsystem.application.usecase;

public interface TransferUseCase {
    void execute(String origemContaId, String destinoContaId, double valor);
}

