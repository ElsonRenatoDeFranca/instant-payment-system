package com.example.accountservice.infrastructure.gateway;

import com.example.common.dto.ContaDTO;
import java.util.List;
import java.util.Optional;

public interface AccountGateway {
    void save(ContaDTO conta);
    Optional<ContaDTO> findById(String id);
    List<ContaDTO> findAll();
}

