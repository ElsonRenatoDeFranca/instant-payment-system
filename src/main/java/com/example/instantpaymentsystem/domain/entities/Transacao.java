package com.example.instantpaymentsystem.domain.entities;

import java.util.UUID;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    private UUID id;
    private UUID origemContaId;
    private UUID destinoContaId;
    private double valor;
    private LocalDateTime dataHora;
}
