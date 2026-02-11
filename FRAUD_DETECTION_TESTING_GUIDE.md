# 🛡️ Fraud Detection API - Testing Guide

## 📋 Overview

This guide provides complete testing instructions for the **Fraud Detection Service** - a microservice that analyzes transactions and returns **POSITIVE** (fraud detected) or **NEGATIVE** (legitimate transaction) results.

---

## 🚀 Quick Start

### Service Information
- **Port**: 8085
- **Base URL**: http://localhost:8085
- **Via Gateway**: http://localhost:8080/api/v1/fraud
- **Database**: MongoDB (instant_payment_fraud)

### Start Service
```powershell
# With Docker Compose
docker-compose -f docker-compose-microservices.yml up -d fraud-detection-service

# Or standalone
cd fraud-detection-service
mvn spring-boot:run
```

### Health Check
```http
GET http://localhost:8085/actuator/health

Expected: {"status":"UP"}
```

---

## 🎯 API Endpoints

### 1. Analyze Transaction (Main Endpoint)

**Purpose**: Analyzes a transaction for fraud indicators

**Endpoint**: `POST /api/v1/fraud/analyze`

**Request Body**:
```json
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "sourceAccountId": "507f1f77bcf86cd799439021",
  "destinationAccountId": "507f1f77bcf86cd799439022",
  "amount": 500.00,
  "timestamp": "2026-02-11T10:00:00Z"
}
```

---

## 🧪 Test Scenarios

### Scenario 1: NEGATIVE Result (Legitimate Transaction)

**Description**: Small amount, normal hours, regular pattern

**Request**:
```http
POST http://localhost:8080/api/v1/fraud/analyze
Content-Type: application/json

{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "sourceAccountId": "{{account1_id}}",
  "destinationAccountId": "{{account2_id}}",
  "amount": 500.00,
  "timestamp": "2026-02-11T10:00:00Z"
}
```

**Expected Response (200 OK)**:
```json
{
  "analysisId": "abc-123-def-456",
  "result": "NEGATIVE",
  "riskScore": 10,
  "riskLevel": "LOW",
  "reasons": [],
  "recommendation": "APPROVE",
  "rulesTriggered": [],
  "analyzedAt": "2026-02-11T10:00:01Z"
}
```

**Postman Tests**:
```javascript
pm.test("Status code is 200", function() {
    pm.response.to.have.status(200);
});

pm.test("Result is NEGATIVE", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.result).to.eql("NEGATIVE");
});

pm.test("Risk level is LOW", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskLevel).to.eql("LOW");
});

pm.test("Recommendation is APPROVE", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.recommendation).to.eql("APPROVE");
});

pm.test("Risk score is below 30", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskScore).to.be.below(30);
});
```

---

### Scenario 2: POSITIVE Result (High Value Transaction)

**Description**: Amount exceeds R$ 10,000 threshold

**Request**:
```http
POST http://localhost:8080/api/v1/fraud/analyze
Content-Type: application/json

{
  "transactionId": "550e8400-e29b-41d4-a716-446655440001",
  "sourceAccountId": "{{account1_id}}",
  "destinationAccountId": "{{account2_id}}",
  "amount": 50000.00,
  "timestamp": "2026-02-11T10:00:00Z"
}
```

**Expected Response (200 OK)**:
```json
{
  "analysisId": "abc-123-def-457",
  "result": "POSITIVE",
  "riskScore": 80,
  "riskLevel": "HIGH",
  "reasons": [
    "High value transaction detected (R$ 50,000.00)"
  ],
  "recommendation": "DENY",
  "rulesTriggered": [
    "RULE_HIGH_VALUE"
  ],
  "analyzedAt": "2026-02-11T10:00:01Z"
}
```

**Postman Tests**:
```javascript
pm.test("Status code is 200", function() {
    pm.response.to.have.status(200);
});

pm.test("Result is POSITIVE", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.result).to.eql("POSITIVE");
});

pm.test("Risk level is HIGH or CRITICAL", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskLevel).to.be.oneOf(["HIGH", "CRITICAL"]);
});

pm.test("Recommendation is DENY", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.recommendation).to.eql("DENY");
});

pm.test("Has high value reason", function() {
    var jsonData = pm.response.json();
    var hasHighValue = jsonData.reasons.some(r => 
        r.toLowerCase().includes('high value')
    );
    pm.expect(hasHighValue).to.be.true;
});

pm.test("Risk score is above 60", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskScore).to.be.above(60);
});
```

---

### Scenario 3: POSITIVE Result (Unusual Hours)

**Description**: Transaction during suspicious hours (2 AM - 5 AM)

**Request**:
```http
POST http://localhost:8080/api/v1/fraud/analyze
Content-Type: application/json

{
  "transactionId": "550e8400-e29b-41d4-a716-446655440002",
  "sourceAccountId": "{{account1_id}}",
  "destinationAccountId": "{{account2_id}}",
  "amount": 1000.00,
  "timestamp": "2026-02-11T03:30:00Z"
}
```

**Expected Response (200 OK)**:
```json
{
  "analysisId": "abc-123-def-458",
  "result": "POSITIVE",
  "riskScore": 35,
  "riskLevel": "MEDIUM",
  "reasons": [
    "Unusual transaction time (03:30 AM)"
  ],
  "recommendation": "REVIEW",
  "rulesTriggered": [
    "RULE_UNUSUAL_HOURS"
  ],
  "analyzedAt": "2026-02-11T03:30:01Z"
}
```

**Postman Tests**:
```javascript
pm.test("Contains unusual hours reason", function() {
    var jsonData = pm.response.json();
    var hasUnusualHours = jsonData.reasons.some(r => 
        r.toLowerCase().includes('unusual')
    );
    pm.expect(hasUnusualHours).to.be.true;
});

pm.test("Result is POSITIVE", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.result).to.eql("POSITIVE");
});
```

---

### Scenario 4: POSITIVE Result (Multiple Indicators)

**Description**: High value + unusual hours (multiple red flags)

**Request**:
```http
POST http://localhost:8080/api/v1/fraud/analyze
Content-Type: application/json

{
  "transactionId": "550e8400-e29b-41d4-a716-446655440003",
  "sourceAccountId": "{{account1_id}}",
  "destinationAccountId": "{{account2_id}}",
  "amount": 75000.00,
  "timestamp": "2026-02-11T04:15:00Z"
}
```

**Expected Response (200 OK)**:
```json
{
  "analysisId": "abc-123-def-459",
  "result": "POSITIVE",
  "riskScore": 95,
  "riskLevel": "CRITICAL",
  "reasons": [
    "High value transaction detected (R$ 75,000.00)",
    "Unusual transaction time (04:15 AM)"
  ],
  "recommendation": "DENY",
  "rulesTriggered": [
    "RULE_HIGH_VALUE",
    "RULE_UNUSUAL_HOURS"
  ],
  "analyzedAt": "2026-02-11T04:15:01Z"
}
```

**Postman Tests**:
```javascript
pm.test("Risk level is CRITICAL", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskLevel).to.eql("CRITICAL");
});

pm.test("Multiple reasons detected", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.reasons).to.have.length.above(1);
});

pm.test("Risk score is above 90", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.riskScore).to.be.above(90);
});
```

---

### Scenario 5: Get Fraud Statistics

**Endpoint**: `GET /api/v1/fraud/statistics`

**Request**:
```http
GET http://localhost:8080/api/v1/fraud/statistics
```

**Expected Response (200 OK)**:
```json
{
  "totalAnalyses": 150,
  "fraudDetected": 12,
  "fraudRate": 8.0,
  "averageRiskScore": 28.5,
  "topReasons": [
    "High value transaction",
    "Unusual transaction time"
  ],
  "analysisBy RiskLevel": {
    "LOW": 120,
    "MEDIUM": 18,
    "HIGH": 10,
    "CRITICAL": 2
  }
}
```

**Postman Tests**:
```javascript
pm.test("Status code is 200", function() {
    pm.response.to.have.status(200);
});

pm.test("Has total analyses", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.totalAnalyses).to.exist;
    pm.expect(jsonData.totalAnalyses).to.be.a('number');
});

pm.test("Fraud rate is calculated", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.fraudRate).to.exist;
});
```

---

### Scenario 6: Get Analysis History by Account

**Endpoint**: `GET /api/v1/fraud/history/account/{accountId}`

**Request**:
```http
GET http://localhost:8080/api/v1/fraud/history/account/{{account1_id}}
```

**Expected Response (200 OK)**:
```json
[
  {
    "analysisId": "abc-123-def-456",
    "transactionId": "550e8400-e29b-41d4-a716-446655440000",
    "result": "NEGATIVE",
    "riskScore": 10,
    "riskLevel": "LOW",
    "analyzedAt": "2026-02-11T10:00:00Z"
  },
  {
    "analysisId": "abc-123-def-457",
    "transactionId": "550e8400-e29b-41d4-a716-446655440001",
    "result": "POSITIVE",
    "riskScore": 80,
    "riskLevel": "HIGH",
    "analyzedAt": "2026-02-11T10:05:00Z"
  }
]
```

**Postman Tests**:
```javascript
pm.test("Status code is 200", function() {
    pm.response.to.have.status(200);
});

pm.test("Response is an array", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.be.an('array');
});

pm.test("Each item has required fields", function() {
    var jsonData = pm.response.json();
    if (jsonData.length > 0) {
        pm.expect(jsonData[0]).to.have.property('analysisId');
        pm.expect(jsonData[0]).to.have.property('result');
        pm.expect(jsonData[0]).to.have.property('riskScore');
    }
});
```

---

## 📊 Result Types Reference

### Result Values
- **NEGATIVE**: Transaction is legitimate (approve)
- **POSITIVE**: Fraud indicators detected (review or deny)

### Risk Levels
- **LOW**: Risk score 0-30 (approve)
- **MEDIUM**: Risk score 31-60 (review recommended)
- **HIGH**: Risk score 61-90 (deny recommended)
- **CRITICAL**: Risk score 91-100 (deny immediately)

### Recommendations
- **APPROVE**: Allow transaction to proceed
- **REVIEW**: Flag for manual review
- **DENY**: Block transaction immediately

---

## 🎨 Complete Test Flow in Postman

### Test Sequence:
```
1. Health Check             → Verify service is running
2. Analyze - NEGATIVE       → Legitimate transaction (R$ 500)
3. Analyze - POSITIVE (HV)  → High value (R$ 50,000)
4. Analyze - POSITIVE (UH)  → Unusual hours (3 AM)
5. Analyze - POSITIVE (Both)→ Multiple indicators
6. Get Statistics           → View fraud metrics
7. Get History              → View account history
```

### Expected Results:
✅ Request 1: Service UP
✅ Request 2: NEGATIVE, APPROVE
✅ Request 3: POSITIVE, DENY (high value)
✅ Request 4: POSITIVE, REVIEW (unusual hours)
✅ Request 5: POSITIVE, DENY (critical)
✅ Request 6: Statistics returned
✅ Request 7: History array returned

---

## 🔄 Integration with Transaction Service

### Transaction Flow with Fraud Detection:

```
1. POST /api/v1/transactions
   ↓
2. Transaction Service validates accounts
   ↓
3. Transaction Service calls Fraud Detection
   ↓
4. Fraud Detection analyzes and returns result
   ↓
5a. IF POSITIVE → Transaction REJECTED
5b. IF NEGATIVE → Transaction APPROVED
   ↓
6. Response to user
```

### Testing Transaction with Fraud Check:

**Request**:
```http
POST http://localhost:8080/api/v1/transactions
Content-Type: application/json

{
  "origemContaId": "{{account1_id}}",
  "destinoContaId": "{{account2_id}}",
  "valor": 75000.00,
  "descricao": "High value test"
}
```

**Expected Response (if fraud detected)**:
```json
{
  "timestamp": "2026-02-11T10:00:00Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Transaction blocked due to fraud detection: High value transaction detected (R$ 75,000.00)",
  "path": "/api/v1/transactions"
}
```

---

## 🚨 Error Scenarios

### Invalid Request (Missing Fields)
```http
POST /api/v1/fraud/analyze
Body: {"amount": 500}

Expected: 400 Bad Request
```

### Non-existent Account
```http
POST /api/v1/fraud/analyze
Body: {
  "sourceAccountId": "000000000000000000000000",
  ...
}

Expected: 404 Not Found
```

---

## 📋 Testing Checklist

### Before Testing
- [ ] Fraud Detection Service running (port 8085)
- [ ] MongoDB running
- [ ] Have valid account IDs
- [ ] Postman collection imported

### Test Coverage
- [ ] NEGATIVE result (legitimate transaction)
- [ ] POSITIVE result (high value)
- [ ] POSITIVE result (unusual hours)
- [ ] POSITIVE result (multiple indicators)
- [ ] Get statistics
- [ ] Get history by account
- [ ] Error scenarios (invalid data)

### Validation Points
- [ ] Correct result type (POSITIVE/NEGATIVE)
- [ ] Risk score calculated
- [ ] Appropriate risk level
- [ ] Correct recommendation
- [ ] Reasons populated when POSITIVE
- [ ] Rules triggered listed
- [ ] Analysis saved to database

---

## 🎯 Response Time Benchmarks

| Scenario | Expected Time |
|----------|---------------|
| Simple analysis | < 50ms |
| Complex analysis (multiple rules) | < 100ms |
| Get statistics | < 100ms |
| Get history | < 150ms |

---

## 🔍 Debugging Tips

### Check Service Logs
```powershell
docker-compose logs -f fraud-detection-service
```

### Verify MongoDB Data
```javascript
// MongoDB shell
use instant_payment_fraud
db.fraud_analyses.find().limit(5)
```

### Common Issues

**Issue**: Always returns NEGATIVE
```
Solution: Verify rules are configured correctly
Check: Rule thresholds in service configuration
```

**Issue**: Service not responding
```
Solution: Check health endpoint
curl http://localhost:8085/actuator/health
```

**Issue**: High response times
```
Solution: Check MongoDB indexes
Check: Database connection pool settings
```

---

## 📊 Sample Test Data

### Legitimate Transactions (NEGATIVE)
```
Amount: R$ 50 to R$ 9,999
Time: 8 AM to 10 PM
Pattern: Normal frequency
```

### Fraudulent Patterns (POSITIVE)
```
High Value: > R$ 10,000
Unusual Hours: 2 AM to 5 AM
Velocity: > 5 txn/minute
New Account: < 24 hours old + high first transaction
```

---

## ✅ Success Criteria

After running all fraud detection tests:

✅ NEGATIVE results for normal transactions
✅ POSITIVE results for suspicious patterns
✅ Risk scores accurately calculated
✅ Recommendations align with risk levels
✅ Statistics accurately reflect analyses
✅ History correctly filtered by account
✅ Response times within benchmarks
✅ All analyses logged to database

---

**Version**: 2.0.0  
**Service Port**: 8085  
**Created**: February 11, 2026  
**Status**: ✅ READY FOR TESTING

**Happy Fraud Hunting! 🛡️**

