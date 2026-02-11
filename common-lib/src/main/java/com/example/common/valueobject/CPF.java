package com.example.common.valueobject;

import lombok.Getter;

@Getter
public class CPF {
    private final String value;

    public CPF(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.value = value;
    }

    private boolean isValid(String cpf) {
        // Simple validation, real validation should be more robust
        return cpf != null && cpf.matches("\\d{11}");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf = (CPF) o;
        return value.equals(cpf.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

