# 🚀 Microservices Implementation Guide

## 📋 Step-by-Step Implementation

This guide will help you transform the monolithic application into microservices architecture.

### Prerequisites
- JDK 21
- Maven 3.8+
- Docker & Docker Compose
- Git

---

## Phase 1: Setup Infrastructure ✅

### Step 1.1: Backup Current Monolith
```powershell
# Create a backup branch
git checkout -b monolith-backup
git add .
git commit -m "Backup: Monolithic version before microservices migration"
git checkout -b microservices-implementation
```

### Step 1.2: Reorganize Project Structure
```powershell
# The new structure will be:
# instant-payment-system/
# ├── pom.xml (parent)
# ├── common-lib/
# ├── user-service/
# ├── account-service/
# ├── pix-key-service/
# ├── transaction-service/
# ├── api-gateway/
# └── docker-compose.yml
```

### Step 1.3: Replace Parent POM
```powershell
# Backup current pom.xml
Move-Item pom.xml pom-monolith.xml

# Use the new parent POM
Move-Item pom-microservices.xml pom.xml
```

---

## Phase 2: Create User Service 🔵

### Step 2.1: Generate User Service Structure
```powershell
# Create directories
mkdir user-service
mkdir user-service\src\main\java\com\example\userservice
mkdir user-service\src\main\java\com\example\userservice\domain
mkdir user-service\src\main\java\com\example\userservice\domain\entity
mkdir user-service\src\main\java\com\example\userservice\application
mkdir user-service\src\main\java\com\example\userservice\application\usecase
mkdir user-service\src\main\java\com\example\userservice\application\usecase\impl
mkdir user-service\src\main\java\com\example\userservice\application\dto
mkdir user-service\src\main\java\com\example\userservice\application\mapper
mkdir user-service\src\main\java\com\example\userservice\infrastructure
mkdir user-service\src\main\java\com\example\userservice\infrastructure\gateway
mkdir user-service\src\main\java\com\example\userservice\infrastructure\repository
mkdir user-service\src\main\java\com\example\userservice\infrastructure\config
mkdir user-service\src\main\java\com\example\userservice\interfaceadapter
mkdir user-service\src\main\java\com\example\userservice\interfaceadapter\controller
mkdir user-service\src\main\java\com\example\userservice\interfaceadapter\gateway
mkdir user-service\src\main\resources
```

### Step 2.2: Files to Create

**user-service/pom.xml** - Maven configuration
**Usuario.java** - Domain entity (from monolith)
**UserRepository.java** - MongoDB repository
**UserGateway.java** - Gateway interface
**UserGatewayImpl.java** - Gateway implementation
**RegisterUserUseCase.java** - Use case interface
**RegisterUserUseCaseImpl.java** - Use case implementation
**UserController.java** - REST controller
**UserDTO.java** - Data transfer object
**UserMapper.java** - Entity/DTO mapper
**UserServiceApplication.java** - Spring Boot main class
**application.yml** - Configuration file
**Dockerfile** - Docker image definition

---

## Phase 3: Create Account Service 🟢

### Step 3.1: Generate Account Service Structure
Similar to User Service, create the folder structure.

### Step 3.2: Files to Create
- Account entity
- Account repository
- Account gateway
- Use cases (Create, Query, List)
- REST controller
- DTOs and Mappers
- Feign client to call User Service
- Spring Boot application
- Configuration files

### Step 3.3: Add Inter-Service Communication
```java
// AccountClient.java
@FeignClient(name = "user-service", url = "${services.user.url}")
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{id}")
    UserDTO getUser(@PathVariable("id") UUID id);
}
```

---

## Phase 4: Create PIX Key Service 🟡

### Step 4.1: Generate PIX Key Service Structure
Create folders following hexagonal architecture.

### Step 4.2: Files to Create
- ChavePix entity
- PIX Key repository
- PIX Key gateway
- Use cases (Register, Query, List)
- REST controller
- DTOs and Mappers
- Feign client to call Account Service
- Spring Boot application
- Configuration files

---

## Phase 5: Create Transaction Service 🔴

### Step 5.1: Generate Transaction Service Structure
Create folders following hexagonal architecture.

### Step 5.2: Files to Create
- Transacao entity
- Transaction repository
- Transaction gateway
- Use cases (Transfer, Query)
- REST controller
- DTOs and Mappers
- Feign clients (Account Service, PIX Key Service)
- Spring Boot application
- Configuration files

### Step 5.3: Implement Distributed Transaction Logic
```java
// TransferUseCaseImpl.java
public TransacaoDTO transfer(TransferRequestDTO request) {
    // 1. Validate source account
    AccountDTO sourceAccount = accountClient.getAccount(request.getSourceAccountId());
    
    // 2. Resolve PIX key to destination account
    PixKeyDTO pixKey = pixKeyClient.getPixKeyByValue(request.getPixKey());
    AccountDTO destAccount = accountClient.getAccount(pixKey.getAccountId());
    
    // 3. Validate balance
    if (sourceAccount.getBalance() < request.getAmount()) {
        throw new InsufficientBalanceException(sourceAccount.getBalance(), request.getAmount());
    }
    
    // 4. Update balances
    accountClient.debit(sourceAccount.getId(), request.getAmount());
    accountClient.credit(destAccount.getId(), request.getAmount());
    
    // 5. Save transaction
    return transactionGateway.save(transaction);
}
```

---

## Phase 6: Create API Gateway 🌐

### Step 6.1: Generate API Gateway Project
```powershell
mkdir api-gateway
mkdir api-gateway\src\main\java\com\example\gateway
mkdir api-gateway\src\main\resources
```

### Step 6.2: Create Gateway Configuration
```yaml
# application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/v1/users/**
        
        - id: account-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/v1/accounts/**
        
        - id: pix-key-service
          uri: http://localhost:8083
          predicates:
            - Path=/api/v1/pix-keys/**
        
        - id: transaction-service
          uri: http://localhost:8084
          predicates:
            - Path=/api/v1/transactions/**
```

---

## Phase 7: Docker Composition 🐳

### Step 7.1: Create docker-compose.yml
```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:7.0
    container_name: instant-payment-mongodb
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123
    volumes:
      - mongodb_data:/data/db
    networks:
      - instant-payment-network

  user-service:
    build: ./user-service
    container_name: user-service
    ports:
      - "8081:8081"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_users?authSource=admin
    depends_on:
      - mongodb
    networks:
      - instant-payment-network

  account-service:
    build: ./account-service
    container_name: account-service
    ports:
      - "8082:8082"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_accounts?authSource=admin
      SERVICES_USER_URL: http://user-service:8081
    depends_on:
      - mongodb
      - user-service
    networks:
      - instant-payment-network

  pix-key-service:
    build: ./pix-key-service
    container_name: pix-key-service
    ports:
      - "8083:8083"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_pix_keys?authSource=admin
      SERVICES_ACCOUNT_URL: http://account-service:8082
    depends_on:
      - mongodb
      - account-service
    networks:
      - instant-payment-network

  transaction-service:
    build: ./transaction-service
    container_name: transaction-service
    ports:
      - "8084:8084"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_transactions?authSource=admin
      SERVICES_ACCOUNT_URL: http://account-service:8082
      SERVICES_PIXKEY_URL: http://pix-key-service:8083
    depends_on:
      - mongodb
      - account-service
      - pix-key-service
    networks:
      - instant-payment-network

  api-gateway:
    build: ./api-gateway
    container_name: api-gateway
    ports:
      - "8080:8080"
    environment:
      SERVICES_USER_URL: http://user-service:8081
      SERVICES_ACCOUNT_URL: http://account-service:8082
      SERVICES_PIXKEY_URL: http://pix-key-service:8083
      SERVICES_TRANSACTION_URL: http://transaction-service:8084
    depends_on:
      - user-service
      - account-service
      - pix-key-service
      - transaction-service
    networks:
      - instant-payment-network

volumes:
  mongodb_data:

networks:
  instant-payment-network:
    driver: bridge
```

---

## Phase 8: Build & Deploy 🏗️

### Step 8.1: Build All Services
```powershell
# Build common library first
cd common-lib
mvn clean install

# Build all services
cd ..
mvn clean install
```

### Step 8.2: Create Docker Images
```powershell
# Build Docker images for each service
docker-compose build
```

### Step 8.3: Start All Services
```powershell
# Start all services with Docker Compose
docker-compose up -d

# Check logs
docker-compose logs -f

# Check service health
curl http://localhost:8081/actuator/health  # User Service
curl http://localhost:8082/actuator/health  # Account Service
curl http://localhost:8083/actuator/health  # PIX Key Service
curl http://localhost:8084/actuator/health  # Transaction Service
curl http://localhost:8080/actuator/health  # API Gateway
```

---

## Phase 9: Testing 🧪

### Step 9.1: Test User Service
```powershell
# Create a user
curl -X POST http://localhost:8080/api/v1/users `
  -H "Content-Type: application/json" `
  -d '{\"nome\": \"João Silva\", \"cpf\": \"12345678901\"}'

# Get user
curl http://localhost:8080/api/v1/users/{userId}
```

### Step 9.2: Test Account Service
```powershell
# Create an account
curl -X POST http://localhost:8080/api/v1/accounts `
  -H "Content-Type: application/json" `
  -d '{\"numero\": \"12345\", \"agencia\": \"0001\", \"saldo\": 1000.0, \"usuarioId\": \"{userId}\"}'
```

### Step 9.3: Test PIX Key Service
```powershell
# Register PIX key
curl -X POST http://localhost:8080/api/v1/pix-keys `
  -H "Content-Type: application/json" `
  -d '{\"tipo\": \"CPF\", \"valor\": \"12345678901\", \"contaId\": \"{accountId}\"}'
```

### Step 9.4: Test Transaction Service
```powershell
# Execute transfer
curl -X POST http://localhost:8080/api/v1/transactions `
  -H "Content-Type: application/json" `
  -d '{\"origemContaId\": \"{sourceAccountId}\", \"destinoContaId\": \"{destAccountId}\", \"valor\": 100.0}'
```

---

## Phase 10: Monitoring & Observability 📊

### Step 10.1: Add Spring Boot Actuator
All services already have actuator dependency. Configure endpoints:

```yaml
# application.yml (each service)
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

### Step 10.2: Access Metrics
```
http://localhost:8081/actuator/metrics
http://localhost:8082/actuator/metrics
http://localhost:8083/actuator/metrics
http://localhost:8084/actuator/metrics
```

---

## 📝 Checklist

### Common Library
- [x] Exception classes created
- [x] Value objects (CPF, CNPJ) created
- [x] POM configuration
- [ ] Build and install to local Maven repository

### User Service
- [ ] Domain entities
- [ ] Use cases
- [ ] Gateway interface and implementation
- [ ] MongoDB repository
- [ ] REST controller
- [ ] DTOs and mappers
- [ ] Spring Boot application
- [ ] Configuration files
- [ ] Dockerfile
- [ ] Integration tests

### Account Service
- [ ] Domain entities
- [ ] Use cases
- [ ] Gateway interface and implementation
- [ ] MongoDB repository
- [ ] REST controller
- [ ] DTOs and mappers
- [ ] Feign client (User Service)
- [ ] Spring Boot application
- [ ] Configuration files
- [ ] Dockerfile
- [ ] Integration tests

### PIX Key Service
- [ ] Domain entities
- [ ] Use cases
- [ ] Gateway interface and implementation
- [ ] MongoDB repository
- [ ] REST controller
- [ ] DTOs and mappers
- [ ] Feign client (Account Service)
- [ ] Spring Boot application
- [ ] Configuration files
- [ ] Dockerfile
- [ ] Integration tests

### Transaction Service
- [ ] Domain entities
- [ ] Use cases
- [ ] Gateway interface and implementation
- [ ] MongoDB repository
- [ ] REST controller
- [ ] DTOs and mappers
- [ ] Feign clients (Account, PIX Key Services)
- [ ] Spring Boot application
- [ ] Configuration files
- [ ] Dockerfile
- [ ] Integration tests

### API Gateway
- [ ] Gateway routes configuration
- [ ] Spring Boot application
- [ ] Configuration files
- [ ] Dockerfile

### Infrastructure
- [ ] docker-compose.yml
- [ ] MongoDB initialization scripts
- [ ] Network configuration
- [ ] Volume configuration

---

## 🎯 Next Steps

1. **Complete Service Implementation**: Follow this guide to implement each service
2. **Add Resilience Patterns**: Circuit breaker, retry, timeout
3. **Add Security**: JWT authentication, API keys
4. **Add Logging**: Centralized logging with ELK stack
5. **Add Tracing**: Distributed tracing with Zipkin/Jaeger
6. **Performance Testing**: Load testing with JMeter/Gatling
7. **Kubernetes Deployment**: Production-ready K8s manifests

---

## 📚 References

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Microservices Patterns](https://microservices.io/patterns/index.html)
- [Docker Compose](https://docs.docker.com/compose/)
- [MongoDB with Spring Boot](https://spring.io/guides/gs/accessing-data-mongodb/)

---

**Version**: 2.0.0
**Date**: February 2026
**Status**: 📖 IMPLEMENTATION GUIDE READY

