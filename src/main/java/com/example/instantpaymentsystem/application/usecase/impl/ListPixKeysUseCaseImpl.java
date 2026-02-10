package com.example.instantpaymentsystem.application.usecase.impl;

import com.example.instantpaymentsystem.application.usecase.ListPixKeysUseCase;
import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListPixKeysUseCaseImpl implements ListPixKeysUseCase {

    @Autowired
    private PixKeyGateway pixKeyGateway;

    @Override
    public List<ChavePix> execute() {
        return pixKeyGateway.findAll();
    }
}

