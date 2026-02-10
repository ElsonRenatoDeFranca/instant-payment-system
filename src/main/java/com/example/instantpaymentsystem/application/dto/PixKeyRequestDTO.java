package com.example.instantpaymentsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PixKeyRequestDTO {
    private String tipo;
    private String valor;
    private String contaId;
}
