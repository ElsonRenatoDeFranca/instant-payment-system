package com.example.instantpaymentsystem.interfaceadapter.controller;

import com.example.instantpaymentsystem.application.usecase.TransferUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransferUseCase transferUseCase;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequestDTO request) {
        transferUseCase.execute(request.getOrigemContaId(), request.getDestinoContaId(), request.getValor());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getTransactionById(@PathVariable UUID id) {
        // TODO: Implement with a specific query use case
        return ResponseEntity.notFound().build();
    }
}

