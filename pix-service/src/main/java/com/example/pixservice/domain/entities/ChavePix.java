package com.example.pixservice.domain.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChavePix {
    private UUID id;
    private String tipo;
    private String valor;
    private UUID contaId;
}
// ...existing code...
