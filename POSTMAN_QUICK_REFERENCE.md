# 🧪 Postman Quick Reference Card

## 🚀 Quick Start (3 Steps)

### Step 1: Import Files into Postman
1. Open Postman
2. Click **Import** button (top left)
3. Drag & drop these files:
   - `Instant-Payment-System.postman_collection.json`
   - `Instant-Payment-Local.postman_environment.json`

### Step 2: Select Environment
1. Click environment dropdown (top right)
2. Select **"Instant Payment - Local"**

### Step 3: Start Services
```powershell
docker-compose -f docker-compose-microservices.yml up -d
```

---

## 📋 Complete User Journey (8 Requests)

Execute in this order:

### 1. Create User - João
```
POST {{gateway_url}}/api/v1/users
Body: {"nome":"João Silva","cpf":"12345678901"}
✅ Saves user1_id automatically
```

### 2. Create User - Maria
```
POST {{gateway_url}}/api/v1/users
Body: {"nome":"Maria Santos","cpf":"98765432109"}
✅ Saves user2_id automatically
```

### 3. Create Account - João
```
POST {{gateway_url}}/api/v1/accounts
Body: {"numero":"12345-6","agencia":"0001","saldo":5000,"usuarioId":"{{user1_id}}"}
✅ Saves account1_id automatically
```

### 4. Create Account - Maria
```
POST {{gateway_url}}/api/v1/accounts
Body: {"numero":"65432-1","agencia":"0001","saldo":2000,"usuarioId":"{{user2_id}}"}
✅ Saves account2_id automatically
```

### 5. Register PIX Key - João (CPF)
```
POST {{gateway_url}}/api/v1/pix-keys
Body: {"tipo":"CPF","valor":"12345678901","contaId":"{{account1_id}}"}
✅ Saves pixkey1_id automatically
```

### 6. Register PIX Key - Maria (Email)
```
POST {{gateway_url}}/api/v1/pix-keys
Body: {"tipo":"EMAIL","valor":"maria.santos@email.com","contaId":"{{account2_id}}"}
✅ Saves pixkey2_id automatically
```

### 7. Transfer - João → Maria (R$ 500)
```
POST {{gateway_url}}/api/v1/transactions
Body: {"origemContaId":"{{account1_id}}","chavePix":"maria.santos@email.com","valor":500}
✅ Transaction executed
```

### 8. Get Transaction History
```
GET {{gateway_url}}/api/v1/transactions/account/{{account1_id}}
✅ Shows all transactions
```

---

## 📊 Expected Balance Flow

| Step | João's Balance | Maria's Balance |
|------|----------------|-----------------|
| Initial | R$ 5,000.00 | R$ 2,000.00 |
| After Transfer | R$ 4,500.00 | R$ 2,500.00 |

---

## 🔍 Individual Service Tests

### User Service (Port 8081)
```
POST   /api/v1/users              - Create user
GET    /api/v1/users/{id}         - Get user
GET    /api/v1/users              - List users
```

### Account Service (Port 8082)
```
POST   /api/v1/accounts           - Create account
GET    /api/v1/accounts/{id}      - Get account
GET    /api/v1/accounts/user/{id} - Get by user
GET    /api/v1/accounts           - List accounts
```

### PIX Key Service (Port 8083)
```
POST   /api/v1/pix-keys           - Register key
GET    /api/v1/pix-keys/{id}      - Get by ID
GET    /api/v1/pix-keys/value/{v} - Get by value
GET    /api/v1/pix-keys/account/{id} - Get by account
GET    /api/v1/pix-keys           - List keys
```

### Transaction Service (Port 8084)
```
POST   /api/v1/transactions       - Execute transfer
GET    /api/v1/transactions/{id}  - Get transaction
GET    /api/v1/transactions/account/{id} - Get history
```

---

## ✅ Health Check Commands

```
GET http://localhost:8081/actuator/health - User Service
GET http://localhost:8082/actuator/health - Account Service
GET http://localhost:8083/actuator/health - PIX Key Service
GET http://localhost:8084/actuator/health - Transaction Service
GET http://localhost:8080/actuator/health - API Gateway
```

**Expected Response:**
```json
{"status":"UP"}
```

---

## 🎯 PIX Key Types

| Type | Example | Format |
|------|---------|--------|
| CPF | 12345678901 | 11 digits |
| EMAIL | user@email.com | Valid email |
| PHONE | +5511987654321 | E.164 format |
| RANDOM | UUID | 550e8400-e29b-41d4-a716-446655440000 |

---

## 🧪 Test Assertions (Built-in)

The collection includes automatic tests:

✅ Status code validation
✅ Auto-save IDs to environment
✅ Response structure validation
✅ Business logic validation

---

## 🚨 Common Error Codes

| Code | Meaning | Example |
|------|---------|---------|
| 200 | Success (GET) | Resource found |
| 201 | Success (POST) | Resource created |
| 400 | Bad Request | Invalid CPF format |
| 404 | Not Found | User doesn't exist |
| 409 | Conflict | Duplicate PIX key |
| 500 | Server Error | Service unavailable |

---

## 🎨 Collection Structure

```
📁 Instant Payment System
  ├─ 📁 1. User Service (4 requests)
  ├─ 📁 2. Account Service (5 requests)
  ├─ 📁 3. PIX Key Service (7 requests)
  ├─ 📁 4. Transaction Service (4 requests)
  ├─ 📁 5. Error Scenarios (3 requests)
  └─ 📁 6. Health Checks (5 requests)

Total: 28 requests
```

---

## 🔄 Variables (Auto-managed)

| Variable | Purpose | Auto-saved |
|----------|---------|------------|
| gateway_url | API Gateway base URL | No |
| user1_id | João's user ID | Yes |
| user2_id | Maria's user ID | Yes |
| account1_id | João's account ID | Yes |
| account2_id | Maria's account ID | Yes |
| pixkey1_id | João's PIX key ID | Yes |
| pixkey2_id | Maria's PIX key ID | Yes |
| transaction1_id | Transaction ID | Yes |

---

## 📝 Tips & Tricks

### Run All Tests in Sequence
1. Click folder name (e.g., "1. User Service")
2. Click "Run" button
3. Review results in Runner

### Export Results
1. After running collection
2. Click "Export Results"
3. Save as JSON for reports

### Monitor Services
1. Use "Health Checks" folder
2. Run periodically
3. Ensure all services UP

### Debug Failed Requests
1. Check "Console" (bottom left)
2. View request/response details
3. Check service logs

---

## 🎯 Success Criteria

After running complete journey:

✅ All 8 requests return success (200/201)
✅ IDs saved to environment variables
✅ Balances updated correctly
✅ Transaction history accurate
✅ No 4xx or 5xx errors

---

## 🆘 Troubleshooting

### "Connection refused"
```
✅ Solution: Start services
docker-compose -f docker-compose-microservices.yml up -d
```

### "404 Not Found"
```
✅ Solution: Check service is running
curl http://localhost:8081/actuator/health
```

### "Variable not set"
```
✅ Solution: Run requests in order
Execute "Create User - João" before "Create Account - João"
```

### "Timeout"
```
✅ Solution: Increase timeout
Postman Settings → Request timeout (ms) → 30000
```

---

## 📚 Full Documentation

For complete details, see: **POSTMAN_TESTING_GUIDE.md**

---

## 🎉 You're Ready to Test!

1. ✅ Import collection & environment
2. ✅ Start services
3. ✅ Run health checks
4. ✅ Execute user journey
5. ✅ Verify results

**Happy Testing! 🧪**

---

**Version**: 2.0.0  
**Created**: February 11, 2026  
**Format**: Postman Collection v2.1

