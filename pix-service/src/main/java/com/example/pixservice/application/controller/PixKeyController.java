package com.example.pixservice.application.controller;

import com.example.pixservice.application.usecase.ListPixKeysByUserUseCase;
import com.example.pixservice.domain.entities.ChavePix;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {
    private final ListPixKeysByUserUseCase listPixKeysByUserUseCase;

    public PixKeyController(ListPixKeysByUserUseCase listPixKeysByUserUseCase) {
        this.listPixKeysByUserUseCase = listPixKeysByUserUseCase;
    }

    @GetMapping("/user/{userId}")
    public List<ChavePix> getPixKeysByUser(@PathVariable UUID userId) {
        return listPixKeysByUserUseCase.execute(userId);
    }
}
