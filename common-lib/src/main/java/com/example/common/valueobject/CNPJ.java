package com.example.common.valueobject;

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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CNPJ cnpj = (CNPJ) o;
        return value.equals(cnpj.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

