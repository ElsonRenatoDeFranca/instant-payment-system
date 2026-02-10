# 📚 Próximos Passos: Integração com MongoDB

Este documento descreve como completar a implementação com MongoDB repositories.

---

## 1. Criar MongoDB Repositories

### 1.1 PixKeyRepository

Crie o arquivo: `src/main/java/com/example/instantpaymentsystem/infrastructure/persistence/PixKeyRepository.java`

```java
package com.example.instantpaymentsystem.infrastructure.persistence;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PixKeyRepository extends MongoRepository<ChavePix, UUID> {
    Optional<ChavePix> findByValor(String valor);
    List<ChavePix> findByContaId(UUID contaId);
    
    @Query("{ 'contaId': { $in: ?0 } }")
    List<ChavePix> findByContaIdIn(List<UUID> contaIds);
}
```

### 1.2 UserRepository

```java
package com.example.instantpaymentsystem.infrastructure.persistence;

import com.example.instantpaymentsystem.domain.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<Usuario, UUID> {
}
```

### 1.3 AccountRepository

```java
package com.example.instantpaymentsystem.infrastructure.persistence;

import com.example.instantpaymentsystem.domain.entities.Conta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Conta, UUID> {
    List<Conta> findByUsuarioId(UUID usuarioId);
}
```

### 1.4 TransactionRepository

```java
package com.example.instantpaymentsystem.infrastructure.persistence;

import com.example.instantpaymentsystem.domain.entities.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<Transacao, UUID> {
}
```

---

## 2. Atualizar as Implementações das Gateways

### 2.1 PixKeyGatewayImpl

```java
package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.ChavePix;
import com.example.instantpaymentsystem.infrastructure.persistence.PixKeyRepository;
import com.example.instantpaymentsystem.infrastructure.persistence.AccountRepository;
import com.example.instantpaymentsystem.interfaceadapter.gateway.PixKeyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PixKeyGatewayImpl implements PixKeyGateway {

    @Autowired
    private PixKeyRepository pixKeyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ChavePix save(ChavePix chavePix) {
        return pixKeyRepository.save(chavePix);
    }

    @Override
    public Optional<ChavePix> findById(UUID id) {
        return pixKeyRepository.findById(id);
    }

    @Override
    public Optional<ChavePix> findByValor(String valor) {
        return pixKeyRepository.findByValor(valor);
    }

    @Override
    public List<ChavePix> findAll() {
        return pixKeyRepository.findAll();
    }

    @Override
    public List<ChavePix> findByContaId(UUID contaId) {
        return pixKeyRepository.findByContaId(contaId);
    }

    @Override
    public List<ChavePix> findByUsuarioId(UUID usuarioId) {
        // Buscar todas as contas do usuário
        List<UUID> contaIds = accountRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(conta -> conta.getId())
                .collect(Collectors.toList());
        
        // Buscar todas as chaves Pix dessas contas
        return pixKeyRepository.findByContaIdIn(contaIds);
    }

    @Override
    public void deleteById(UUID id) {
        pixKeyRepository.deleteById(id);
    }
}
```

### 2.2 UserGatewayImpl

```java
package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Usuario;
import com.example.instantpaymentsystem.infrastructure.persistence.UserRepository;
import com.example.instantpaymentsystem.interfaceadapter.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Usuario save(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
```

### 2.3 AccountGatewayImpl

```java
package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Conta;
import com.example.instantpaymentsystem.infrastructure.persistence.AccountRepository;
import com.example.instantpaymentsystem.interfaceadapter.gateway.AccountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountGatewayImpl implements AccountGateway {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Conta save(Conta conta) {
        return accountRepository.save(conta);
    }

    @Override
    public Optional<Conta> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Conta> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Conta> findByUsuarioId(UUID usuarioId) {
        return accountRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }
}
```

### 2.4 TransactionGatewayImpl

```java
package com.example.instantpaymentsystem.infrastructure.gateway;

import com.example.instantpaymentsystem.domain.entities.Transacao;
import com.example.instantpaymentsystem.infrastructure.persistence.TransactionRepository;
import com.example.instantpaymentsystem.interfaceadapter.gateway.TransactionGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionGatewayImpl implements TransactionGateway {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transacao save(Transacao transacao) {
        return transactionRepository.save(transacao);
    }

    @Override
    public Optional<Transacao> findById(UUID id) {
        return transactionRepository.findById(id);
    }
}
```

---

## 3. Adicionar Anotações MongoDB às Entidades

### 3.1 Atualizar Usuario.java

```java
package com.example.instantpaymentsystem.domain.entities;

import com.example.instantpaymentsystem.domain.valueobjects.CPF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private UUID id;
    private String nome;
    private CPF cpf;
}
```

### 3.2 Atualizar Conta.java

```java
package com.example.instantpaymentsystem.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contas")
public class Conta {
    @Id
    private UUID id;
    private String numero;
    private String agencia;
    private double saldo;
    private UUID usuarioId;
}
```

### 3.3 Atualizar ChavePix.java

```java
package com.example.instantpaymentsystem.domain.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chaves_pix")
public class ChavePix {
    @Id
    private UUID id;
    private String tipo;
    private String valor;
    private UUID contaId;
}
```

### 3.4 Atualizar Transacao.java

```java
package com.example.instantpaymentsystem.domain.entities;

import java.util.UUID;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transacoes")
public class Transacao {
    @Id
    private UUID id;
    private UUID origemContaId;
    private UUID destinoContaId;
    private double valor;
    private LocalDateTime dataHora;
}
```

---

## 4. Atualizar Configuração MongoDB

### 4.1 Atualizar application.yml

```yaml
server:
  port: ${SERVER_PORT:8080}

spring:
  application:
    name: Instant Payment System
  data:
    mongodb:
      uri: ${MONGO_URI:mongodb://admin:admin!@localhost:27017/pixdb?authSource=admin}
      # ou manualmente:
      # host: ${MONGO_HOST:localhost}
      # port: ${MONGO_PORT:27017}
      # database: ${MONGO_DB:pixdb}
      # username: ${MONGO_USER:admin}
      # password: ${MONGO_PASS:admin!}
  jackson:
    default-property-inclusion: NON_EMPTY
  jmx:
    enabled: false
  main:
    banner-mode: "off"
    lazy-initialization: false

application:
  timeZone: GMT-3
```

---

## 5. Completar Métodos Faltantes nos Controllers

### 5.1 Implementar getPixKeyById

```java
@GetMapping("/{id}")
public ResponseEntity<ChavePixResponseDTO> getPixKeyById(@PathVariable UUID id) {
    Optional<ChavePix> chavePix = pixKeyGateway.findById(id);
    if (chavePix.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ChavePixResponseMapper.toDTO(chavePix.get()));
}
```

### 5.2 Implementar getUserById

```java
@GetMapping("/{id}")
public ResponseEntity<UsuarioDTO> getUserById(@PathVariable UUID id) {
    // Criar um novo use case: QueryUserUseCase
    Optional<Usuario> usuario = userGateway.findById(id);
    if (usuario.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(UsuarioMapper.toDTO(usuario.get()));
}
```

---

## 6. Testar a API

### 6.1 Iniciar o MongoDB
```bash
docker run -d \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin! \
  -e MONGO_INITDB_DATABASE=pixdb \
  mongo:7.0
```

### 6.2 Compilar o Projeto
```bash
mvn clean package
```

### 6.3 Executar a Aplicação
```bash
mvn spring-boot:run
```

### 6.4 Testar Endpoints
```bash
# Criar usuário
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"nome": "João", "cpf": "12345678901"}'

# Listar usuários
curl -X GET http://localhost:8080/users
```

---

## 7. Checklist de Implementação

- [ ] Criar todas as 4 MongoDB Repositories
- [ ] Atualizar as 4 Gateway Implementations com injeção
- [ ] Adicionar @Document em todas as entidades
- [ ] Adicionar @Id em todas as entidades
- [ ] Atualizar application.yml com MongoDB
- [ ] Completar métodos faltantes nos controllers
- [ ] Implementar exception handling global
- [ ] Adicionar validações nas entidades
- [ ] Criar testes unitários
- [ ] Criar testes de integração
- [ ] Documentar API com Swagger

---

## 📞 Suporte

Se encontrar problemas:
1. Verifique se MongoDB está rodando
2. Verifique configuração de conexão em application.yml
3. Verifique logs da aplicação
4. Certifique-se que todas as dependências estão no pom.xml


