package com.example.pixservice.infrastructure.gateway;

import com.example.common.dto.ChavePixDTO;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class PixKeyGatewayImpl implements PixKeyGateway {
    private final Map<String, ChavePixDTO> pixKeys = new HashMap<>();

    @Override
    public void save(ChavePixDTO chavePix) {
        pixKeys.put(chavePix.getId(), chavePix);
    }

    @Override
    public Optional<ChavePixDTO> findById(String id) {
        return Optional.ofNullable(pixKeys.get(id));
    }

    @Override
    public List<ChavePixDTO> findAll() {
        return new ArrayList<>(pixKeys.values());
    }

    public List<ChavePixDTO> findByUsuarioId(String usuarioId) {
        List<ChavePixDTO> result = new ArrayList<>();
        for (ChavePixDTO chavePix : pixKeys.values()) {
            if (usuarioId.equals(chavePix.getContaId())) {
                result.add(chavePix);
            }
        }
        return result;
    }
}
