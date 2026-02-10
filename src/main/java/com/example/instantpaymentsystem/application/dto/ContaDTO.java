package com.example.instantpaymentsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    private String id;
    private String numero;
    private String agencia;
    private double saldo;
    private String usuarioId;
}
