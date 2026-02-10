package com.example.instantpaymentsystem.interfaceadapter.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    private String origemContaId;
    private String destinoContaId;
    private double valor;
}

