package com.example.pixservice.infrastructure.gateway;

import com.example.common.dto.ChavePixDTO;
import java.util.List;
import java.util.Optional;

public interface PixKeyGateway {
    void save(ChavePixDTO chavePix);
    Optional<ChavePixDTO> findById(String id);
    List<ChavePixDTO> findAll();
    List<ChavePixDTO> findByUsuarioId(String usuarioId);
}
