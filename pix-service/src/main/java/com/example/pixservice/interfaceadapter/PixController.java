package com.example.pixservice.interfaceadapter;

import com.example.pixservice.application.PixService;
import com.example.pixservice.domain.PixTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {
    @Autowired
    private PixService pixService;

    @PostMapping("/transfer")
    public PixTransaction transfer(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        return pixService.createTransaction(from, to, amount);
    }
}

