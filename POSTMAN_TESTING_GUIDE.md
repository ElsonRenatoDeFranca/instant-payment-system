# 🧪 Postman Testing Guide - Microservices Architecture

## 📋 Overview

This guide provides complete Postman testing instructions for all microservices in the Instant Payment System.

---

## 🚀 Quick Setup

### Step 1: Start All Services

```powershell
# Start services with Docker Compose
docker-compose -f docker-compose-microservices.yml up -d

# Or run individually (for development)
# Terminal 1: User Service
cd user-service
mvn spring-boot:run

# Terminal 2: Account Service
cd account-service
mvn spring-boot:run

# Terminal 3: PIX Key Service
cd pix-key-service
mvn spring-boot:run

# Terminal 4: Transaction Service
cd transaction-service
mvn spring-boot:run

# Terminal 5: API Gateway
cd api-gateway
mvn spring-boot:run
```

### Step 2: Verify Services are Running

Check health endpoints:
- User Service: http://localhost:8081/actuator/health
- Account Service: http://localhost:8082/actuator/health
- PIX Key Service: http://localhost:8083/actuator/health
- Transaction Service: http://localhost:8084/actuator/health
- API Gateway: http://localhost:8080/actuator/health

---

## 📦 Postman Collection

### Import Pre-built Collection

I'll provide a complete Postman collection JSON that you can import.

**File**: `Instant-Payment-System.postman_collection.json`

---

## 🧪 Test Scenarios

### Scenario 1: Complete User Journey (End-to-End)

#### Test Flow:
1. Create User 1 (João)
2. Create User 2 (Maria)
3. Create Account for João
4. Create Account for Maria
5. Register PIX Key for João
6. Register PIX Key for Maria
7. Execute Transfer from João to Maria
8. Query Transaction History

---

## 📝 Individual Service Tests

### 1️⃣ USER SERVICE (Port 8081)

#### Base URL (Direct):
```
http://localhost:8081
```

#### Base URL (Via Gateway):
```
http://localhost:8080
```

---

#### 1.1 Create User - João

**Request:**
```http
POST http://localhost:8080/api/v1/users
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "12345678901"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "nome": "João Silva",
  "cpf": "12345678901",
  "createdAt": "2026-02-11T10:30:00Z"
}
```

**Save Variable:**
- Variable: `user1_id`
- Value: Copy the `id` from response

---

#### 1.2 Create User - Maria

**Request:**
```http
POST http://localhost:8080/api/v1/users
Content-Type: application/json

{
  "nome": "Maria Santos",
  "cpf": "98765432109"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439012",
  "nome": "Maria Santos",
  "cpf": "98765432109",
  "createdAt": "2026-02-11T10:31:00Z"
}
```

**Save Variable:**
- Variable: `user2_id`
- Value: Copy the `id` from response

---

#### 1.3 Get User by ID

**Request:**
```http
GET http://localhost:8080/api/v1/users/{{user1_id}}
```

**Expected Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "nome": "João Silva",
  "cpf": "12345678901",
  "createdAt": "2026-02-11T10:30:00Z"
}
```

---

#### 1.4 List All Users

**Request:**
```http
GET http://localhost:8080/api/v1/users
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "nome": "João Silva",
    "cpf": "12345678901"
  },
  {
    "id": "507f1f77bcf86cd799439012",
    "nome": "Maria Santos",
    "cpf": "98765432109"
  }
]
```

---

### 2️⃣ ACCOUNT SERVICE (Port 8082)

#### Base URL (Via Gateway):
```
http://localhost:8080
```

---

#### 2.1 Create Account for João

**Request:**
```http
POST http://localhost:8080/api/v1/accounts
Content-Type: application/json

{
  "numero": "12345-6",
  "agencia": "0001",
  "saldo": 5000.00,
  "usuarioId": "{{user1_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439021",
  "numero": "12345-6",
  "agencia": "0001",
  "saldo": 5000.00,
  "usuarioId": "507f1f77bcf86cd799439011",
  "createdAt": "2026-02-11T10:32:00Z"
}
```

**Save Variable:**
- Variable: `account1_id`
- Value: Copy the `id` from response

---

#### 2.2 Create Account for Maria

**Request:**
```http
POST http://localhost:8080/api/v1/accounts
Content-Type: application/json

{
  "numero": "65432-1",
  "agencia": "0001",
  "saldo": 2000.00,
  "usuarioId": "{{user2_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439022",
  "numero": "65432-1",
  "agencia": "0001",
  "saldo": 2000.00,
  "usuarioId": "507f1f77bcf86cd799439012",
  "createdAt": "2026-02-11T10:33:00Z"
}
```

**Save Variable:**
- Variable: `account2_id`
- Value: Copy the `id` from response

---

#### 2.3 Get Account by ID

**Request:**
```http
GET http://localhost:8080/api/v1/accounts/{{account1_id}}
```

**Expected Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439021",
  "numero": "12345-6",
  "agencia": "0001",
  "saldo": 5000.00,
  "usuarioId": "507f1f77bcf86cd799439011"
}
```

---

#### 2.4 Get Accounts by User ID

**Request:**
```http
GET http://localhost:8080/api/v1/accounts/user/{{user1_id}}
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439021",
    "numero": "12345-6",
    "agencia": "0001",
    "saldo": 5000.00,
    "usuarioId": "507f1f77bcf86cd799439011"
  }
]
```

---

#### 2.5 List All Accounts

**Request:**
```http
GET http://localhost:8080/api/v1/accounts
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439021",
    "numero": "12345-6",
    "agencia": "0001",
    "saldo": 5000.00
  },
  {
    "id": "507f1f77bcf86cd799439022",
    "numero": "65432-1",
    "agencia": "0001",
    "saldo": 2000.00
  }
]
```

---

### 3️⃣ PIX KEY SERVICE (Port 8083)

#### Base URL (Via Gateway):
```
http://localhost:8080
```

---

#### 3.1 Register PIX Key for João (CPF)

**Request:**
```http
POST http://localhost:8080/api/v1/pix-keys
Content-Type: application/json

{
  "tipo": "CPF",
  "valor": "12345678901",
  "contaId": "{{account1_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439031",
  "tipo": "CPF",
  "valor": "12345678901",
  "contaId": "507f1f77bcf86cd799439021",
  "createdAt": "2026-02-11T10:34:00Z"
}
```

**Save Variable:**
- Variable: `pixkey1_id`
- Value: Copy the `id` from response

---

#### 3.2 Register PIX Key for Maria (Email)

**Request:**
```http
POST http://localhost:8080/api/v1/pix-keys
Content-Type: application/json

{
  "tipo": "EMAIL",
  "valor": "maria.santos@email.com",
  "contaId": "{{account2_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439032",
  "tipo": "EMAIL",
  "valor": "maria.santos@email.com",
  "contaId": "507f1f77bcf86cd799439022",
  "createdAt": "2026-02-11T10:35:00Z"
}
```

**Save Variable:**
- Variable: `pixkey2_id`
- Value: Copy the `id` from response

---

#### 3.3 Register PIX Key - Phone

**Request:**
```http
POST http://localhost:8080/api/v1/pix-keys
Content-Type: application/json

{
  "tipo": "PHONE",
  "valor": "+5511987654321",
  "contaId": "{{account1_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439033",
  "tipo": "PHONE",
  "valor": "+5511987654321",
  "contaId": "507f1f77bcf86cd799439021",
  "createdAt": "2026-02-11T10:36:00Z"
}
```

---

#### 3.4 Register PIX Key - Random

**Request:**
```http
POST http://localhost:8080/api/v1/pix-keys
Content-Type: application/json

{
  "tipo": "RANDOM",
  "valor": "550e8400-e29b-41d4-a716-446655440000",
  "contaId": "{{account2_id}}"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439034",
  "tipo": "RANDOM",
  "valor": "550e8400-e29b-41d4-a716-446655440000",
  "contaId": "507f1f77bcf86cd799439022",
  "createdAt": "2026-02-11T10:37:00Z"
}
```

---

#### 3.5 Get PIX Key by ID

**Request:**
```http
GET http://localhost:8080/api/v1/pix-keys/{{pixkey1_id}}
```

**Expected Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439031",
  "tipo": "CPF",
  "valor": "12345678901",
  "contaId": "507f1f77bcf86cd799439021"
}
```

---

#### 3.6 Get PIX Key by Value

**Request:**
```http
GET http://localhost:8080/api/v1/pix-keys/value/12345678901
```

**Expected Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439031",
  "tipo": "CPF",
  "valor": "12345678901",
  "contaId": "507f1f77bcf86cd799439021"
}
```

---

#### 3.7 Get PIX Keys by Account ID

**Request:**
```http
GET http://localhost:8080/api/v1/pix-keys/account/{{account1_id}}
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439031",
    "tipo": "CPF",
    "valor": "12345678901",
    "contaId": "507f1f77bcf86cd799439021"
  },
  {
    "id": "507f1f77bcf86cd799439033",
    "tipo": "PHONE",
    "valor": "+5511987654321",
    "contaId": "507f1f77bcf86cd799439021"
  }
]
```

---

#### 3.8 List All PIX Keys

**Request:**
```http
GET http://localhost:8080/api/v1/pix-keys
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439031",
    "tipo": "CPF",
    "valor": "12345678901",
    "contaId": "507f1f77bcf86cd799439021"
  },
  {
    "id": "507f1f77bcf86cd799439032",
    "tipo": "EMAIL",
    "valor": "maria.santos@email.com",
    "contaId": "507f1f77bcf86cd799439022"
  }
]
```

---

### 4️⃣ TRANSACTION SERVICE (Port 8084)

#### Base URL (Via Gateway):
```
http://localhost:8080
```

---

#### 4.1 Execute Transfer - João to Maria (Using PIX Key)

**Request:**
```http
POST http://localhost:8080/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account1_id}}",
  "chavePix": "maria.santos@email.com",
  "valor": 500.00,
  "descricao": "Pagamento aluguel"
}
```

**Alternative (Using Destination Account ID):**
```http
POST http://localhost:8080/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account1_id}}",
  "destinoContaId": "{{account2_id}}",
  "valor": 500.00,
  "descricao": "Pagamento aluguel"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439041",
  "origemContaId": "507f1f77bcf86cd799439021",
  "destinoContaId": "507f1f77bcf86cd799439022",
  "valor": 500.00,
  "descricao": "Pagamento aluguel",
  "dataHora": "2026-02-11T10:40:00Z",
  "status": "COMPLETED"
}
```

**Save Variable:**
- Variable: `transaction1_id`
- Value: Copy the `id` from response

**Account Balances After:**
- João: 5000.00 - 500.00 = 4500.00
- Maria: 2000.00 + 500.00 = 2500.00

---

#### 4.2 Execute Transfer - Maria to João

**Request:**
```http
POST http://localhost:8080/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account2_id}}",
  "chavePix": "12345678901",
  "valor": 200.00,
  "descricao": "Devolução"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439042",
  "origemContaId": "507f1f77bcf86cd799439022",
  "destinoContaId": "507f1f77bcf86cd799439021",
  "valor": 200.00,
  "descricao": "Devolução",
  "dataHora": "2026-02-11T10:42:00Z",
  "status": "COMPLETED"
}
```

**Account Balances After:**
- João: 4500.00 + 200.00 = 4700.00
- Maria: 2500.00 - 200.00 = 2300.00

---

#### 4.3 Get Transaction by ID

**Request:**
```http
GET http://localhost:8080/api/v1/transactions/{{transaction1_id}}
```

**Expected Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439041",
  "origemContaId": "507f1f77bcf86cd799439021",
  "destinoContaId": "507f1f77bcf86cd799439022",
  "valor": 500.00,
  "descricao": "Pagamento aluguel",
  "dataHora": "2026-02-11T10:40:00Z",
  "status": "COMPLETED"
}
```

---

#### 4.4 Get Transactions by Account ID

**Request:**
```http
GET http://localhost:8080/api/v1/transactions/account/{{account1_id}}
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439041",
    "origemContaId": "507f1f77bcf86cd799439021",
    "destinoContaId": "507f1f77bcf86cd799439022",
    "valor": 500.00,
    "tipo": "DEBITO",
    "dataHora": "2026-02-11T10:40:00Z"
  },
  {
    "id": "507f1f77bcf86cd799439042",
    "origemContaId": "507f1f77bcf86cd799439022",
    "destinoContaId": "507f1f77bcf86cd799439021",
    "valor": 200.00,
    "tipo": "CREDITO",
    "dataHora": "2026-02-11T10:42:00Z"
  }
]
```

---

## 🧪 Error Testing Scenarios

### 5️⃣ Error Handling Tests

---

#### 5.1 Create User with Invalid CPF

**Request:**
```http
POST http://localhost:8080/api/v1/users
Content-Type: application/json

{
  "nome": "Teste Invalid",
  "cpf": "123"
}
```

**Expected Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-11T10:45:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "CPF inválido",
  "path": "/api/v1/users"
}
```

---

#### 5.2 Create Account for Non-existent User

**Request:**
```http
POST http://localhost:8080/api/v1/accounts
Content-Type: application/json

{
  "numero": "99999-9",
  "agencia": "0001",
  "saldo": 1000.00,
  "usuarioId": "000000000000000000000000"
}
```

**Expected Response (404 Not Found):**
```json
{
  "timestamp": "2026-02-11T10:46:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: '000000000000000000000000'",
  "path": "/api/v1/accounts"
}
```

---

#### 5.3 Transfer with Insufficient Balance

**Request:**
```http
POST http://localhost:8080/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account1_id}}",
  "destinoContaId": "{{account2_id}}",
  "valor": 99999.00,
  "descricao": "Tentativa saldo insuficiente"
}
```

**Expected Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-11T10:47:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient balance. Available: 4700.00, Required: 99999.00",
  "path": "/api/v1/transactions"
}
```

---

#### 5.4 Register Duplicate PIX Key

**Request:**
```http
POST http://localhost:8080/api/v1/pix-keys
Content-Type: application/json

{
  "tipo": "CPF",
  "valor": "12345678901",
  "contaId": "{{account2_id}}"
}
```

**Expected Response (409 Conflict):**
```json
{
  "timestamp": "2026-02-11T10:48:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "PIX key already registered: 12345678901",
  "path": "/api/v1/pix-keys"
}
```

---

#### 5.5 Get Non-existent Resource

**Request:**
```http
GET http://localhost:8080/api/v1/users/000000000000000000000000
```

**Expected Response (404 Not Found):**
```json
{
  "timestamp": "2026-02-11T10:49:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: '000000000000000000000000'",
  "path": "/api/v1/users/000000000000000000000000"
}
```

---

## 📊 Postman Environment Variables

### Create Environment: `Instant Payment - Local`

```json
{
  "name": "Instant Payment - Local",
  "values": [
    {
      "key": "gateway_url",
      "value": "http://localhost:8080",
      "enabled": true
    },
    {
      "key": "user_service_url",
      "value": "http://localhost:8081",
      "enabled": true
    },
    {
      "key": "account_service_url",
      "value": "http://localhost:8082",
      "enabled": true
    },
    {
      "key": "pixkey_service_url",
      "value": "http://localhost:8083",
      "enabled": true
    },
    {
      "key": "transaction_service_url",
      "value": "http://localhost:8084",
      "enabled": true
    },
    {
      "key": "user1_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "user2_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "account1_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "account2_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "pixkey1_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "pixkey2_id",
      "value": "",
      "enabled": true
    },
    {
      "key": "transaction1_id",
      "value": "",
      "enabled": true
    }
  ]
}
```

---

## 🔄 Automated Test Scripts

### Pre-request Script (Auto-save IDs)

Add this to your requests to automatically save response IDs:

**For Create User:**
```javascript
pm.test("Save user ID", function() {
    var jsonData = pm.response.json();
    pm.environment.set("user1_id", jsonData.id);
});
```

**For Create Account:**
```javascript
pm.test("Save account ID", function() {
    var jsonData = pm.response.json();
    pm.environment.set("account1_id", jsonData.id);
});
```

---

### Test Scripts (Assertions)

**For Create User:**
```javascript
pm.test("Status code is 201", function() {
    pm.response.to.have.status(201);
});

pm.test("Response has user ID", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.id).to.exist;
});

pm.test("CPF is correct", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.cpf).to.eql("12345678901");
});
```

**For Transfer:**
```javascript
pm.test("Status code is 201", function() {
    pm.response.to.have.status(201);
});

pm.test("Transaction completed", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.status).to.eql("COMPLETED");
});

pm.test("Value is correct", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.valor).to.eql(500.00);
});
```

---

## 📋 Testing Checklist

### ✅ User Service
- [ ] Create user with valid CPF
- [ ] Create user with invalid CPF (should fail)
- [ ] Get user by ID
- [ ] List all users
- [ ] Get non-existent user (should return 404)

### ✅ Account Service
- [ ] Create account for valid user
- [ ] Create account for invalid user (should fail)
- [ ] Get account by ID
- [ ] Get accounts by user ID
- [ ] List all accounts
- [ ] Get non-existent account (should return 404)

### ✅ PIX Key Service
- [ ] Register PIX key (CPF type)
- [ ] Register PIX key (Email type)
- [ ] Register PIX key (Phone type)
- [ ] Register PIX key (Random type)
- [ ] Register duplicate key (should fail)
- [ ] Get PIX key by ID
- [ ] Get PIX key by value
- [ ] Get PIX keys by account ID
- [ ] List all PIX keys

### ✅ Transaction Service
- [ ] Transfer using PIX key
- [ ] Transfer using account ID
- [ ] Transfer with insufficient balance (should fail)
- [ ] Transfer to non-existent account (should fail)
- [ ] Get transaction by ID
- [ ] Get transactions by account ID
- [ ] Verify balance updates

### ✅ API Gateway
- [ ] All requests route correctly through gateway
- [ ] Gateway returns proper error responses
- [ ] Gateway timeout handling

---

## 🎯 Performance Testing

### Load Testing Scenarios

**Concurrent User Creation:**
```
- 100 requests in 10 seconds
- Expected: All succeed with 201
```

**Concurrent Transfers:**
```
- 50 transfers simultaneously
- Expected: All process correctly, balances consistent
```

---

## 📊 Expected Results Summary

### Successful Operations
| Operation | HTTP Status | Response Time |
|-----------|-------------|---------------|
| Create User | 201 Created | < 200ms |
| Get User | 200 OK | < 100ms |
| Create Account | 201 Created | < 300ms |
| Get Account | 200 OK | < 100ms |
| Register PIX Key | 201 Created | < 300ms |
| Get PIX Key | 200 OK | < 100ms |
| Execute Transfer | 201 Created | < 500ms |
| Get Transaction | 200 OK | < 100ms |

### Error Responses
| Error Scenario | HTTP Status |
|----------------|-------------|
| Invalid CPF | 400 Bad Request |
| User Not Found | 404 Not Found |
| Duplicate PIX Key | 409 Conflict |
| Insufficient Balance | 400 Bad Request |
| Validation Error | 400 Bad Request |

---

## 🚀 Quick Test Commands (cURL)

### Alternative to Postman

```bash
# Create User
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","cpf":"12345678901"}'

# Get User
curl http://localhost:8080/api/v1/users/{userId}

# Create Account
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{"numero":"12345-6","agencia":"0001","saldo":5000,"usuarioId":"{userId}"}'

# Register PIX Key
curl -X POST http://localhost:8080/api/v1/pix-keys \
  -H "Content-Type: application/json" \
  -d '{"tipo":"CPF","valor":"12345678901","contaId":"{accountId}"}'

# Execute Transfer
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{"origemContaId":"{sourceId}","chavePix":"maria@email.com","valor":500}'
```

---

## 📞 Troubleshooting

### Common Issues

**Connection Refused:**
```
Solution: Check if services are running
curl http://localhost:8081/actuator/health
```

**404 Not Found:**
```
Solution: Verify endpoint URL and HTTP method
Check: MICROSERVICES_ARCHITECTURE.md for correct endpoints
```

**500 Internal Server Error:**
```
Solution: Check service logs
docker-compose logs -f user-service
```

**Timeout:**
```
Solution: Increase Postman timeout settings
Settings → General → Request timeout (ms)
```

---

**Version**: 2.0.0  
**Date**: February 11, 2026  
**Status**: ✅ READY FOR TESTING

**Happy Testing! 🧪**

