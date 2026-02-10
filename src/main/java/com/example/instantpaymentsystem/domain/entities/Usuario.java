package com.example.instantpaymentsystem.domain.entities;

import com.example.instantpaymentsystem.domain.valueobjects.CPF;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private UUID id;
    private String nome;
    private CPF cpf;
}
