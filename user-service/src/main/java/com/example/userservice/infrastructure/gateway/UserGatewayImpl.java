package com.example.userservice.infrastructure.gateway;

import com.example.common.dto.UsuarioDTO;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserGatewayImpl implements UserGateway {
    private final Map<String, UsuarioDTO> users = new HashMap<>();

    @Override
    public void save(UsuarioDTO usuario) {
        users.put(usuario.getId(), usuario);
    }

    @Override
    public Optional<UsuarioDTO> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return new ArrayList<>(users.values());
    }
}
