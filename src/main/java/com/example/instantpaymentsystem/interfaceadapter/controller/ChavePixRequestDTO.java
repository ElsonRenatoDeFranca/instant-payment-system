package com.example.instantpaymentsystem.interfaceadapter.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChavePixRequestDTO {
    private String tipo;
    private String valor;
    private String contaId;
}

