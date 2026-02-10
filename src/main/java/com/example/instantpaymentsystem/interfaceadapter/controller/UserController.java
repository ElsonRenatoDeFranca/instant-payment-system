package com.example.instantpaymentsystem.interfaceadapter.controller;

import com.example.instantpaymentsystem.application.dto.ChavePixResponseDTO;
import com.example.instantpaymentsystem.application.dto.UsuarioDTO;
import com.example.instantpaymentsystem.application.mapper.ChavePixResponseMapper;
import com.example.instantpaymentsystem.application.mapper.UsuarioMapper;
import com.example.instantpaymentsystem.application.usecase.ListPixKeysByUserUseCase;
import com.example.instantpaymentsystem.application.usecase.ListUsersUseCase;
import com.example.instantpaymentsystem.application.usecase.RegisterUserUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.domain.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @Autowired
    private ListPixKeysByUserUseCase listPixKeysByUserUseCase;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UsuarioDTO request) {
        registerUserUseCase.execute(request.getNome(), request.getCpf());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listUsers() {
        List<Usuario> usuarios = listUsersUseCase.execute();
        List<UsuarioDTO> response = usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable UUID id) {
        // TODO: Implement with a specific query use case
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{usuarioId}/pix-keys")
    public ResponseEntity<List<ChavePixResponseDTO>> listPixKeysByUser(@PathVariable UUID usuarioId) {
        List<ChavePix> chaves = listPixKeysByUserUseCase.execute(usuarioId);
        List<ChavePixResponseDTO> response = chaves.stream()
                .map(ChavePixResponseMapper::toDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}

