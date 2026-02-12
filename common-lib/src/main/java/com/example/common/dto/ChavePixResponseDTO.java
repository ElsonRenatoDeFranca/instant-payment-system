package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChavePixResponseDTO {
    private String id;
    private String tipo;
    private String valor;
    private String contaId;
    private String dataCriacao;
}

