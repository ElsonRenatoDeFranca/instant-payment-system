package com.example.instantpaymentsystem.application.usecase;

import com.example.instantpaymentsystem.domain.entities.Conta;
import java.util.List;

public interface ListAccountsUseCase {
    List<Conta> execute();
}

