# ✅ Postman Testing Package - Complete

## 📦 What's Included

I've created a **complete Postman testing package** for your microservices architecture. Everything is ready to import and test!

---

## 📄 Files Created

### 1. **POSTMAN_TESTING_GUIDE.md** (Main Guide)
Complete testing documentation including:
- ✅ Quick setup instructions
- ✅ All API endpoints with examples
- ✅ Request/response examples
- ✅ Error scenario testing
- ✅ Health check endpoints
- ✅ Test assertions
- ✅ Troubleshooting guide

**Pages**: 800+ lines of documentation

---

### 2. **Instant-Payment-System.postman_collection.json** (Postman Collection)
Ready-to-import collection with:
- ✅ 34 pre-configured requests (+6 new fraud detection requests)
- ✅ 7 folders (User, Account, PIX Key, Transaction, Fraud Detection, Errors, Health)
- ✅ Automatic test scripts
- ✅ Auto-save variables
- ✅ Response validation

**Requests**: 34 total (+6 fraud detection)
**Folders**: 7 organized sections

---

### 3. **Instant-Payment-Local.postman_environment.json** (Environment)
Pre-configured environment with:
- ✅ All service URLs
- ✅ Variable placeholders for IDs
- ✅ Ready for local development

**Variables**: 12 pre-configured

---

### 4. **POSTMAN_QUICK_REFERENCE.md** (Quick Reference)
One-page cheat sheet with:
- ✅ 3-step quick start
- ✅ Complete user journey
- ✅ Endpoint list
- ✅ Error codes
- ✅ Tips & tricks

**Format**: Quick reference card

---

## 🚀 Quick Start (3 Steps)

### Step 1: Import into Postman
```
1. Open Postman
2. Click "Import" button
3. Select these files:
   - Instant-Payment-System.postman_collection.json
   - Instant-Payment-Local.postman_environment.json
```

### Step 2: Select Environment
```
1. Click environment dropdown (top right)
2. Select "Instant Payment - Local"
```

### Step 3: Start Testing!
```
1. Start services: docker-compose -f docker-compose-microservices.yml up -d
2. Run health checks (folder 6)
3. Execute complete user journey (folders 1-4 in order)
```

---

## 📊 Collection Overview

### Folder 1: User Service (4 requests)
- Create User - João
- Create User - Maria
- Get User by ID
- List All Users

### Folder 2: Account Service (5 requests)
- Create Account - João
- Create Account - Maria
- Get Account by ID
- Get Accounts by User ID
- List All Accounts

### Folder 3: PIX Key Service (7 requests)
- Register PIX Key - CPF (João)
- Register PIX Key - Email (Maria)
- Register PIX Key - Phone
- Get PIX Key by ID
- Get PIX Key by Value
- Get PIX Keys by Account
- List All PIX Keys

### Folder 4: Transaction Service (4 requests)
- Transfer - João to Maria (PIX Key)
- Transfer - Maria to João (Account ID)
- Get Transaction by ID
- Get Transactions by Account

### Folder 5: Fraud Detection Service (5 requests) 🆕
- Analyze Transaction - NEGATIVE (Legitimate)
- Analyze Transaction - POSITIVE (High Value)
- Analyze Transaction - POSITIVE (Unusual Hours)
- Get Fraud Statistics
- Get Analysis History by Account

### Folder 6: Error Scenarios (3 requests)
- Create User - Invalid CPF
- Transfer - Insufficient Balance
- Get Non-existent User

### Folder 7: Health Checks (6 requests)
- User Service Health
- Account Service Health
- PIX Key Service Health
- Transaction Service Health
- API Gateway Health
- Fraud Detection Service Health 🆕

**Total**: 34 requests ready to use (+6 fraud detection)

---

## 🎯 Complete User Journey Test

Execute these 8 requests in order:

```
1. Create User - João          → Saves user1_id
2. Create User - Maria         → Saves user2_id
3. Create Account - João       → Saves account1_id
4. Create Account - Maria      → Saves account2_id
5. Register PIX Key - João     → Saves pixkey1_id
6. Register PIX Key - Maria    → Saves pixkey2_id
7. Transfer João → Maria       → Executes R$ 500 transfer
8. Get Transaction History     → Shows all transactions
```

**Expected Results:**
- ✅ All requests return 200/201
- ✅ João's balance: R$ 5,000 → R$ 4,500
- ✅ Maria's balance: R$ 2,000 → R$ 2,500
- ✅ Transaction recorded

---

## 🧪 Built-in Test Assertions

Each request includes automatic tests:

### User Creation
```javascript
✅ Status code is 201
✅ Response has user ID
✅ User ID saved to environment
✅ CPF format validated
```

### Account Creation
```javascript
✅ Status code is 201
✅ Response has account ID
✅ Account ID saved to environment
✅ Balance is correct
```

### PIX Key Registration
```javascript
✅ Status code is 201
✅ PIX key ID saved
✅ Key type validated
```

### Transfer Execution
```javascript
✅ Status code is 201
✅ Transaction ID saved
✅ Status is COMPLETED
✅ Amount is correct
```

---

## 📋 Environment Variables

### Pre-configured URLs
- `gateway_url`: http://localhost:8080
- `user_service_url`: http://localhost:8081
- `account_service_url`: http://localhost:8082
- `pixkey_service_url`: http://localhost:8083
- `transaction_service_url`: http://localhost:8084

### Auto-saved IDs (populated during tests)
- `user1_id` - João's user ID
- `user2_id` - Maria's user ID
- `account1_id` - João's account ID
- `account2_id` - Maria's account ID
- `pixkey1_id` - João's PIX key ID
- `pixkey2_id` - Maria's PIX key ID
- `transaction1_id` - Transaction ID

---

## 🎨 Request Examples

### Create User
```http
POST {{gateway_url}}/api/v1/users
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "12345678901"
}
```

### Transfer Money
```http
POST {{gateway_url}}/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account1_id}}",
  "chavePix": "maria.santos@email.com",
  "valor": 500.00,
  "descricao": "Pagamento"
}
```

---

## ✅ Testing Checklist

### Before Testing
- [ ] Services running (docker-compose up -d)
- [ ] MongoDB running
- [ ] Postman installed
- [ ] Collection imported
- [ ] Environment selected

### During Testing
- [ ] Run health checks first
- [ ] Execute requests in order
- [ ] Verify auto-saved variables
- [ ] Check response codes
- [ ] Review test results

### After Testing
- [ ] All tests passed
- [ ] Variables populated
- [ ] Balances correct
- [ ] No errors in logs

---

## 🚨 Error Testing Examples

### Invalid CPF (400 Bad Request)
```http
POST {{gateway_url}}/api/v1/users
Body: {"nome":"Invalid","cpf":"123"}

Expected: 400 - "CPF inválido"
```

### Insufficient Balance (400 Bad Request)
```http
POST {{gateway_url}}/api/v1/transactions
Body: {"origemContaId":"xxx","destinoContaId":"yyy","valor":999999}

Expected: 400 - "Insufficient balance"
```

### Resource Not Found (404 Not Found)
```http
GET {{gateway_url}}/api/v1/users/000000000000000000000000

Expected: 404 - "User not found"
```

---

## 📊 Expected Response Times

| Endpoint | Expected Time |
|----------|---------------|
| Create User | < 200ms |
| Get User | < 100ms |
| Create Account | < 300ms |
| Register PIX Key | < 300ms |
| Execute Transfer | < 500ms |
| Health Check | < 50ms |

---

## 🎓 Features

### Automatic Variable Management
- IDs automatically saved after creation
- Use `{{variable}}` in subsequent requests
- No manual copying needed

### Test Validation
- Status codes validated
- Response structure verified
- Business logic checked
- Error scenarios tested

### Organized Structure
- Folders by service
- Logical request order
- Easy to navigate
- Runner-friendly

### Complete Coverage
- All CRUD operations
- Happy path scenarios
- Error scenarios
- Health monitoring

---

## 📚 Documentation Files

| File | Purpose | Size |
|------|---------|------|
| POSTMAN_TESTING_GUIDE.md | Complete testing guide | 800+ lines |
| POSTMAN_QUICK_REFERENCE.md | Quick cheat sheet | 1-page |
| Instant-Payment-System.postman_collection.json | Postman collection | 34 requests (+6 fraud) |
| Instant-Payment-Local.postman_environment.json | Environment config | 12 variables |

---

## 🎯 Success Metrics

After running the complete collection:

✅ **34/34 requests** should succeed (+6 fraud detection)
✅ **12 variables** auto-populated
✅ **0 errors** in error-free scenarios
✅ **3 errors** in error scenarios (expected)
✅ **< 6 seconds** total execution time (including fraud checks)

---

## 🆘 Quick Troubleshooting

### Services not responding?
```powershell
docker-compose -f docker-compose-microservices.yml up -d
docker-compose logs -f
```

### Variable not set?
```
Run requests in order (folder 1 → 2 → 3 → 4)
```

### Timeout errors?
```
Settings → Request timeout → 30000ms
```

### Wrong environment?
```
Select "Instant Payment - Local" environment
```

---

## 🎉 You're Ready to Test!

### What You Have:
✅ Complete Postman collection (34 requests, +6 fraud detection)
✅ Pre-configured environment
✅ Automatic test assertions
✅ Comprehensive documentation
✅ Quick reference guide
✅ Fraud detection testing scenarios 🆕

### Next Steps:
1. **Import** collection and environment
2. **Start** services with Docker
3. **Run** health checks
4. **Execute** complete user journey
5. **Verify** all tests pass

---

## 📖 Additional Resources

- **Full Testing Guide**: POSTMAN_TESTING_GUIDE.md
- **Quick Reference**: POSTMAN_QUICK_REFERENCE.md
- **Architecture**: MICROSERVICES_ARCHITECTURE.md
- **Implementation**: MICROSERVICES_IMPLEMENTATION_GUIDE.md

---

**Version**: 2.0.0  
**Created**: February 11, 2026  
**Format**: Postman Collection v2.1  
**Status**: ✅ READY TO USE

**Happy Testing! 🧪**

