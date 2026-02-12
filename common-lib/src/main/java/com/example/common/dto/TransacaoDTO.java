package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
    private String id;
    private String origemContaId;
    private String destinoContaId;
    private double valor;
    private String dataHora;
}

