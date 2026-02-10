package com.example.instantpaymentsystem.interfaceadapter.controller;

import com.example.instantpaymentsystem.application.dto.ChavePixResponseDTO;
import com.example.instantpaymentsystem.application.mapper.ChavePixResponseMapper;
import com.example.instantpaymentsystem.application.usecase.ListPixKeysUseCase;
import com.example.instantpaymentsystem.application.usecase.ListPixKeysByUserUseCase;
import com.example.instantpaymentsystem.application.usecase.RegisterPixKeyUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {

    @Autowired
    private RegisterPixKeyUseCase registerPixKeyUseCase;

    @Autowired
    private ListPixKeysUseCase listPixKeysUseCase;

    @Autowired
    private ListPixKeysByUserUseCase listPixKeysByUserUseCase;

    @PostMapping
    public ResponseEntity<Void> createPixKey(@RequestBody ChavePixRequestDTO request) {
        registerPixKeyUseCase.execute(request.getTipo(), request.getValor(), request.getContaId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ChavePixResponseDTO>> listPixKeys() {
        List<ChavePix> chaves = listPixKeysUseCase.execute();
        List<ChavePixResponseDTO> response = chaves.stream()
                .map(ChavePixResponseMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChavePixResponseDTO> getPixKeyById(@PathVariable UUID id) {
        // TODO: Implement with a specific query use case
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<ChavePixResponseDTO> getPixKeyByValue(@PathVariable String value) {
        // TODO: Implement with a specific query use case
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<List<ChavePixResponseDTO>> listPixKeysByUser(@PathVariable UUID usuarioId) {
        List<ChavePix> chaves = listPixKeysByUserUseCase.execute(usuarioId);
        List<ChavePixResponseDTO> response = chaves.stream()
                .map(ChavePixResponseMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePixKey(@PathVariable UUID id) {
        // TODO: Implement with a delete use case
        return ResponseEntity.noContent().build();
    }
}

