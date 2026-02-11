# 🏗️ Microservices Architecture - Instant Payment System

## 📋 Overview

This document describes the transformation from a monolithic application to a microservices architecture using **Hexagonal Architecture** (Ports & Adapters).

## 🎯 Microservices Decomposition

### Bounded Contexts & Services

```
┌─────────────────────────────────────────────────────────────┐
│                      API GATEWAY                            │
│                    (Port 8080)                              │
└─────────────────────────────────────────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
┌───────────────┐   ┌───────────────┐   ┌───────────────┐
│ User Service  │   │Account Service│   │PIX Key Service│
│  (Port 8081)  │   │  (Port 8082)  │   │  (Port 8083)  │
└───────────────┘   └───────────────┘   └───────────────┘
        │                   │                   │
        └───────────────────┼───────────────────┘
                            ▼
                  ┌───────────────────┐
                  │Transaction Service│
                  │    (Port 8084)    │
                  └───────────────────┘
```

## 📦 Service Details

### 1. **user-service** (Port 8081)
**Responsibility**: User management
- Create users
- Query users
- List users
- User validation

**Database**: MongoDB (`users` collection)

**Domain Entities**:
- Usuario
- CPF (Value Object)

**API Endpoints**:
```
POST   /api/v1/users
GET    /api/v1/users/{id}
GET    /api/v1/users
```

---

### 2. **account-service** (Port 8082)
**Responsibility**: Account management
- Create accounts
- Query accounts
- List accounts
- Balance management

**Database**: MongoDB (`accounts` collection)

**Domain Entities**:
- Conta

**API Endpoints**:
```
POST   /api/v1/accounts
GET    /api/v1/accounts/{id}
GET    /api/v1/accounts/user/{userId}
GET    /api/v1/accounts
```

**External Communication**:
- Calls **user-service** to validate user existence

---

### 3. **pix-key-service** (Port 8083)
**Responsibility**: PIX key management
- Register PIX keys
- Query PIX keys
- List PIX keys
- Key validation

**Database**: MongoDB (`pix_keys` collection)

**Domain Entities**:
- ChavePix

**API Endpoints**:
```
POST   /api/v1/pix-keys
GET    /api/v1/pix-keys/{id}
GET    /api/v1/pix-keys/account/{accountId}
GET    /api/v1/pix-keys/value/{value}
GET    /api/v1/pix-keys
```

**External Communication**:
- Calls **account-service** to validate account existence

---

### 4. **transaction-service** (Port 8084)
**Responsibility**: Transaction processing
- Process PIX transfers
- Query transactions
- Transaction history
- Settlement management

**Database**: MongoDB (`transactions` collection)

**Domain Entities**:
- Transacao

**API Endpoints**:
```
POST   /api/v1/transactions
GET    /api/v1/transactions/{id}
GET    /api/v1/transactions/account/{accountId}
```

**External Communication**:
- Calls **account-service** to validate accounts and update balances
- Calls **pix-key-service** to resolve PIX keys

---

### 5. **api-gateway** (Port 8080)
**Responsibility**: API Gateway & Routing
- Request routing
- Authentication/Authorization (future)
- Rate limiting (future)
- Load balancing

**Technology**: Spring Cloud Gateway

**Routes**:
```
/api/v1/users/**        -> user-service:8081
/api/v1/accounts/**     -> account-service:8082
/api/v1/pix-keys/**     -> pix-key-service:8083
/api/v1/transactions/** -> transaction-service:8084
```

---

### 6. **common-lib**
**Responsibility**: Shared code
- Common DTOs
- Exception classes
- Utility classes
- Validation helpers

**Packaging**: JAR library

---

## 🏛️ Hexagonal Architecture per Service

Each microservice follows this structure:

```
service-name/
├── src/main/java/com/example/service/
│   ├── domain/                      # Domain Layer (Core)
│   │   ├── entities/               # Business entities
│   │   └── valueobjects/           # Value objects
│   │
│   ├── application/                 # Application Layer
│   │   ├── usecase/                # Use case interfaces
│   │   ├── usecase/impl/           # Use case implementations
│   │   ├── dto/                    # Application DTOs
│   │   └── mapper/                 # Entity <-> DTO mappers
│   │
│   ├── infrastructure/              # Infrastructure Layer
│   │   ├── gateway/                # Gateway implementations
│   │   ├── repository/             # MongoDB repositories
│   │   ├── client/                 # Feign clients
│   │   └── config/                 # Configurations
│   │
│   └── interfaceadapter/            # Interface Adapter Layer
│       ├── controller/             # REST controllers
│       ├── gateway/                # Gateway interfaces (ports)
│       └── dto/                    # API DTOs
│
└── src/main/resources/
    ├── application.yml
    └── bootstrap.yml
```

---

## 🔄 Communication Patterns

### Synchronous Communication (REST)
- **OpenFeign** for inter-service communication
- Circuit breaker with **Resilience4j**
- Timeout configurations
- Retry mechanisms

### Example: Transaction Service calling Account Service
```java
@FeignClient(name = "account-service", url = "${services.account.url}")
public interface AccountClient {
    @GetMapping("/api/v1/accounts/{id}")
    AccountDTO getAccount(@PathVariable UUID id);
    
    @PutMapping("/api/v1/accounts/{id}/balance")
    void updateBalance(@PathVariable UUID id, @RequestBody BalanceUpdateDTO dto);
}
```

---

## 🗄️ Database Strategy

### Database per Service Pattern
Each microservice has its own MongoDB database:

```yaml
user-service:        instant_payment_users
account-service:     instant_payment_accounts
pix-key-service:     instant_payment_pix_keys
transaction-service: instant_payment_transactions
```

**Benefits**:
- Service independence
- Technology flexibility
- Scalability
- Fault isolation

---

## 🐳 Docker Compose Setup

```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:7.0
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - user-service
      - account-service
      - pix-key-service
      - transaction-service

  user-service:
    build: ./user-service
    ports:
      - "8081:8081"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_users?authSource=admin
    depends_on:
      - mongodb

  account-service:
    build: ./account-service
    ports:
      - "8082:8082"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_accounts?authSource=admin
      USER_SERVICE_URL: http://user-service:8081
    depends_on:
      - mongodb
      - user-service

  pix-key-service:
    build: ./pix-key-service
    ports:
      - "8083:8083"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_pix_keys?authSource=admin
      ACCOUNT_SERVICE_URL: http://account-service:8082
    depends_on:
      - mongodb
      - account-service

  transaction-service:
    build: ./transaction-service
    ports:
      - "8084:8084"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_transactions?authSource=admin
      ACCOUNT_SERVICE_URL: http://account-service:8082
      PIX_KEY_SERVICE_URL: http://pix-key-service:8083
    depends_on:
      - mongodb
      - account-service
      - pix-key-service

volumes:
  mongodb_data:
```

---

## 📊 Project Structure

```
instant-payment-system/
├── api-gateway/              # API Gateway (Spring Cloud Gateway)
├── user-service/             # User management microservice
├── account-service/          # Account management microservice
├── pix-key-service/          # PIX key management microservice
├── transaction-service/      # Transaction processing microservice
├── common-lib/               # Shared library
├── docker-compose.yml        # Docker orchestration
├── pom.xml                   # Parent POM
└── README.md
```

---

## 🔐 Security Considerations

### Future Enhancements
1. **JWT Authentication** - Token-based authentication
2. **API Rate Limiting** - Prevent abuse
3. **HTTPS/TLS** - Encrypted communication
4. **Service Mesh** - Istio/Linkerd for advanced networking
5. **Secret Management** - Vault for credentials

---

## 📈 Scalability & Resilience

### Patterns Implemented
1. **Circuit Breaker** - Prevent cascading failures
2. **Retry Pattern** - Automatic retries with exponential backoff
3. **Timeout Pattern** - Prevent indefinite waits
4. **Bulkhead Pattern** - Isolate resources

### Configuration Example
```yaml
resilience4j:
  circuitbreaker:
    instances:
      accountService:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10000
        permittedNumberOfCallsInHalfOpenState: 3
```

---

## 🚀 Deployment Strategy

### Local Development
```bash
# Build all services
mvn clean install

# Start with Docker Compose
docker-compose up -d
```

### Production (Kubernetes)
```bash
# Build Docker images
docker build -t user-service:1.0 ./user-service
docker build -t account-service:1.0 ./account-service
# ... etc

# Deploy to Kubernetes
kubectl apply -f k8s/
```

---

## 📝 Migration Steps

### Phase 1: Setup Infrastructure
1. Create parent POM with modules
2. Create common-lib module
3. Setup Docker Compose
4. Configure MongoDB databases

### Phase 2: Extract Services (in order)
1. **user-service** (no dependencies)
2. **account-service** (depends on user-service)
3. **pix-key-service** (depends on account-service)
4. **transaction-service** (depends on account-service & pix-key-service)

### Phase 3: API Gateway
1. Create Spring Cloud Gateway
2. Configure routes
3. Add resilience patterns

### Phase 4: Testing & Migration
1. Integration tests
2. Load testing
3. Gradual migration
4. Monitoring setup

---

## 🎯 Benefits

### Scalability
- Scale services independently
- Optimize resource usage per service

### Maintainability
- Smaller, focused codebases
- Independent deployment
- Technology flexibility

### Resilience
- Fault isolation
- Better error handling
- Service redundancy

### Team Organization
- Team ownership per service
- Parallel development
- Clear boundaries

---

**Version**: 2.0.0
**Date**: February 2026
**Status**: ✅ READY FOR IMPLEMENTATION

