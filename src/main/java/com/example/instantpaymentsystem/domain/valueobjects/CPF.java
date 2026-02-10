package com.example.instantpaymentsystem.domain.valueobjects;

import lombok.Getter;

@Getter
public class CPF {
    private final String value;

    public CPF(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("CPF invalido");
        }
        this.value = value;
    }

    private boolean isValid(String cpf) {
        // Simple validation, real validation should be more robust
        return cpf != null && cpf.matches("\\d{11}");
    }
}

