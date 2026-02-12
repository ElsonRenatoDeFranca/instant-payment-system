package com.example.pixservice.infrastructure.gateway;

import com.example.common.dto.TransacaoDTO;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TransactionGatewayImpl implements TransactionGateway {
    private final Map<String, TransacaoDTO> transactions = new HashMap<>();

    @Override
    public void save(TransacaoDTO transacao) {
        transactions.put(transacao.getId(), transacao);
    }

    @Override
    public Optional<TransacaoDTO> findById(String id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<TransacaoDTO> findAll() {
        return new ArrayList<>(transactions.values());
    }
}
