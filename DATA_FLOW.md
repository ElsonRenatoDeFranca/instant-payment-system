# 🔄 Fluxo de Dados - Sistema Pix

## Diagrama de Fluxo de Requisição

```
┌─────────────┐
│   Cliente   │
│  (HTTP)     │
└──────┬──────┘
       │ POST /users
       │ {nome, cpf}
       │
       ▼
┌──────────────────┐
│   Controller     │
│  UserController  │
└──────┬───────────┘
       │
       │ injecta
       │
       ▼
┌────────────────────────┐
│   Use Case             │
│ RegisterUserUseCase    │
└──────┬─────────────────┘
       │
       │ delega
       │
       ▼
┌──────────────────────┐
│   Gateway (Interface)│
│   UserGateway        │
└──────┬───────────────┘
       │
       │ implementa
       │
       ▼
┌──────────────────────┐
│  Gateway Impl        │
│ UserGatewayImpl       │
└──────┬───────────────┘
       │
       │ chamada
       │
       ▼
┌──────────────────────┐
│   Repository         │
│ UserRepository       │
│ (MongoDB)            │
└──────┬───────────────┘
       │
       │ persiste
       │
       ▼
┌──────────────────────┐
│   MongoDB            │
│  Banco de Dados      │
└──────────────────────┘
```

---

## Exemplo 1: Criar Usuário

### 1. Request HTTP
```json
POST /users
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "12345678901"
}
```

### 2. Flow no Controller
```
UserController.createUser(UsuarioDTO request)
  ↓
@Autowired RegisterUserUseCase
  ↓
registerUserUseCase.execute(request.getNome(), request.getCpf())
  ↓
retorna HTTP 201 Created
```

### 3. Flow no Use Case
```
RegisterUserUseCaseImpl.execute(nome, cpf)
  ↓
Usuario usuario = new Usuario()
usuario.setId(UUID.randomUUID())
usuario.setNome(nome)
usuario.setCpf(new CPF(cpf))  // Valida CPF
  ↓
userGateway.save(usuario)
```

### 4. Flow na Gateway
```
UserGatewayImpl.save(usuario)
  ↓
userRepository.save(usuario)  // Persistir no MongoDB
  ↓
retorna Usuario salvo
```

---

## Exemplo 2: Listar Chaves Pix de um Usuário

### 1. Request HTTP
```
GET /users/{usuarioId}/pix-keys
GET /users/550e8400-e29b-41d4-a716-446655440000/pix-keys
```

### 2. Flow no Controller
```
UserController.listPixKeysByUser(UUID usuarioId)
  ↓
@Autowired ListPixKeysByUserUseCase
  ↓
List<ChavePix> chaves = listPixKeysByUserUseCase.execute(usuarioId)
  ↓
map ChavePix to ChavePixResponseDTO
  ↓
retorna HTTP 200 com lista
```

### 3. Flow no Use Case
```
ListPixKeysByUserUseCaseImpl.execute(usuarioId)
  ↓
pixKeyGateway.findByUsuarioId(usuarioId)
  ↓
retorna List<ChavePix>
```

### 4. Flow na Gateway
```
PixKeyGatewayImpl.findByUsuarioId(usuarioId)
  ↓
pixKeyRepository.findByContaId_UsuarioId(usuarioId)
  ↓
Query MongoDB: { conta.usuarioId: usuarioId }
  ↓
retorna List<ChavePix>
```

### 5. Response HTTP
```json
HTTP 200 OK
Content-Type: application/json

[
  {
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "tipo": "CPF",
    "valor": "12345678901",
    "contaId": "550e8400-e29b-41d4-a716-446655440001"
  },
  {
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d480",
    "tipo": "EMAIL",
    "valor": "joao@email.com",
    "contaId": "550e8400-e29b-41d4-a716-446655440001"
  }
]
```

---

## Exemplo 3: Realizar Transferência

### 1. Request HTTP
```json
POST /transactions
Content-Type: application/json

{
  "origemContaId": "550e8400-e29b-41d4-a716-446655440001",
  "destinoContaId": "550e8400-e29b-41d4-a716-446655440002",
  "valor": 100.50
}
```

### 2. Flow no Controller
```
TransactionController.createTransaction(TransactionRequestDTO request)
  ↓
@Autowired TransferUseCase
  ↓
transferUseCase.execute(
  request.getOrigemContaId(),
  request.getDestinoContaId(),
  request.getValor()
)
  ↓
retorna HTTP 201 Created
```

### 3. Flow no Use Case
```
TransferUseCaseImpl.execute(origemId, destinoId, valor)
  ↓
Buscar contaOrigem = accountGateway.findById(origemId)
Buscar contaDestino = accountGateway.findById(destinoId)
  ↓
Validar: contaOrigem.getSaldo() >= valor
  ↓
contaOrigem.setSaldo(contaOrigem.getSaldo() - valor)
contaDestino.setSaldo(contaDestino.getSaldo() + valor)
  ↓
Salvar: accountGateway.save(contaOrigem)
Salvar: accountGateway.save(contaDestino)
  ↓
Criar transacao = new Transacao()
transacao.setId(UUID.randomUUID())
transacao.setOrigemContaId(origemId)
transacao.setDestinoContaId(destinoId)
transacao.setValor(valor)
transacao.setDataHora(LocalDateTime.now())
  ↓
Registrar: transactionGateway.save(transacao)
```

### 4. Resultado
- Conta de origem: saldo reduzido
- Conta de destino: saldo aumentado
- Transação registrada no banco de dados

---

## Mapeamento de Entidades para DTOs

### Usuario → UsuarioDTO
```
Usuario (Domain)          UsuarioDTO (Response)
├── id: UUID             ├── id: String
├── nome: String         ├── nome: String
└── cpf: CPF             └── cpf: String
```

### ChavePix → ChavePixResponseDTO
```
ChavePix (Domain)         ChavePixResponseDTO (Response)
├── id: UUID             ├── id: String
├── tipo: String         ├── tipo: String
├── valor: String        ├── valor: String
└── contaId: UUID        └── contaId: String
```

### Conta → ContaDTO
```
Conta (Domain)            ContaDTO (Response)
├── id: UUID             ├── id: String
├── numero: String       ├── numero: String
├── agencia: String      ├── agencia: String
├── saldo: double        ├── saldo: double
└── usuarioId: UUID      └── usuarioId: String
```

### Transacao → TransacaoDTO
```
Transacao (Domain)        TransacaoDTO (Response)
├── id: UUID             ├── id: String
├── origemContaId: UUID  ├── origemContaId: String
├── destinoContaId: UUID ├── destinoContaId: String
├── valor: double        ├── valor: double
└── dataHora: LocalDateTime └── dataHora: String
```

---

## Arquitetura Hexagonal Ilustrada

```
                    ENTRADA (Portas)
                         │
            ┌────────────┴────────────┐
            │                         │
        REST API                  gRPC/GraphQL
        (Controllers)              (Future)
            │                         │
            └────────────┬────────────┘
                         │
                    ┌────▼────┐
                    │ Use Case │
                    │ (Business)
                    │  Logic   │
                    └────┬─────┘
                         │
                    SAÍDA (Portas)
                         │
            ┌────────────┴────────────┐
            │                         │
         Gateway                   Email/
        (Interface)              SMS/Push
            │                     (Future)
        MongoDB          
      Repository
        (Adapter)
            │
            └────► Database
```

---

## Validações Implementadas

### 1. CPF - Value Object
```java
new CPF("12345678901")
  ↓
isValid() - verifica se tem 11 dígitos
  ↓
Se inválido: lança IllegalArgumentException
```

### 2. CNPJ - Value Object
```java
new CNPJ("12345678901234")
  ↓
isValid() - verifica se tem 14 dígitos
  ↓
Se inválido: lança IllegalArgumentException
```

### 3. Transfer - Validação de Saldo
```java
TransferUseCaseImpl.execute()
  ↓
if (origem.getSaldo() < valor)
  ↓
  throw IllegalArgumentException("Saldo insuficiente")
```

---

## 📊 Resumo dos Componentes

| Componente | Quantidade | Status |
|-----------|-----------|--------|
| Domain Entities | 4 | ✅ |
| Value Objects | 2 | ✅ |
| Gateway Interfaces | 4 | ✅ |
| Gateway Implementations | 4 | ✅ |
| Use Cases (Interfaces) | 10 | ✅ |
| Use Cases (Implementations) | 10 | ✅ |
| REST Controllers | 4 | ✅ |
| Endpoints | 15+ | ✅ |
| DTOs | 7 | ✅ |
| Mappers | 5 | ✅ |


