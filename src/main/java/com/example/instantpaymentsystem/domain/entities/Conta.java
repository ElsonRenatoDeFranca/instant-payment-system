package com.example.instantpaymentsystem.domain.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    private UUID id;
    private String numero;
    private String agencia;
    private double saldo;
    private UUID usuarioId;
}

