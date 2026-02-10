package com.example.instantpaymentsystem.interfaceadapter.controller;

import com.example.instantpaymentsystem.application.dto.ContaDTO;
import com.example.instantpaymentsystem.application.mapper.ContaMapper;
import com.example.instantpaymentsystem.application.usecase.ListAccountsUseCase;
import com.example.instantpaymentsystem.domain.entities.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private ListAccountsUseCase listAccountsUseCase;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody ContaDTO request) {
        // TODO: Implement with a create account use case
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> listAccounts() {
        List<Conta> contas = listAccountsUseCase.execute();
        List<ContaDTO> response = contas.stream()
                .map(ContaMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getAccountById(@PathVariable UUID id) {
        // TODO: Implement with a specific query use case
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        // TODO: Implement with a delete use case
        return ResponseEntity.noContent().build();
    }
}

