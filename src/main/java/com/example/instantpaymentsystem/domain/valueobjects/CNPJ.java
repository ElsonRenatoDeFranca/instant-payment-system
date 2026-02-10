package com.example.instantpaymentsystem.domain.valueobjects;

import lombok.Getter;

@Getter
public class CNPJ {
    private final String value;

    public CNPJ(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.value = value;
    }


    private boolean isValid(String cnpj) {
        return cnpj != null && cnpj.matches("\\d{14}");
    }
}

