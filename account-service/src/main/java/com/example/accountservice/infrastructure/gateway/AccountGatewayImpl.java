package com.example.accountservice.infrastructure.gateway;

import com.example.common.dto.ContaDTO;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class AccountGatewayImpl implements AccountGateway {
    private final Map<String, ContaDTO> accounts = new HashMap<>();

    @Override
    public void save(ContaDTO conta) {
        accounts.put(conta.getId(), conta);
    }

    @Override
    public Optional<ContaDTO> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public List<ContaDTO> findAll() {
        return new ArrayList<>(accounts.values());
    }
}
