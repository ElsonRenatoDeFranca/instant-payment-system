# 🛡️ Fraud Detection Service

## 📋 Overview

The **Fraud Detection Service** is a microservice responsible for analyzing transactions in real-time to identify potentially fraudulent activities. It uses rule-based algorithms and patterns to return positive (fraud detected) or negative (legitimate transaction) results.

---

## 🏗️ Architecture

### Service Details
- **Port**: 8085
- **Database**: MongoDB (`instant_payment_fraud`)
- **Response Time**: < 100ms (critical for transaction flow)
- **Availability**: 99.9% uptime required

### Integration Point
```
Transaction Service → Fraud Detection Service → Response (POSITIVE/NEGATIVE)
                                    ↓
                              MongoDB (fraud_logs)
```

---

## 🎯 Fraud Detection Rules

### Rule 1: High Value Transactions
- Amount > R$ 10,000 in single transaction
- Risk Level: HIGH

### Rule 2: Velocity Check
- More than 5 transactions in 1 minute from same account
- Risk Level: MEDIUM

### Rule 3: Unusual Hours
- Transactions between 2 AM - 5 AM
- Risk Level: LOW

### Rule 4: New Account Activity
- Account created less than 24 hours ago
- First transaction > R$ 5,000
- Risk Level: HIGH

### Rule 5: Multiple Recipients
- Same account sending to > 10 different accounts in 1 hour
- Risk Level: HIGH

### Rule 6: Rapid Small Transactions
- More than 20 transactions of small amounts (< R$ 50) in 10 minutes
- Risk Level: MEDIUM (money laundering indicator)

---

## 📊 API Endpoints

### 1. Analyze Transaction
```http
POST /api/v1/fraud/analyze
Content-Type: application/json

{
  "transactionId": "uuid",
  "sourceAccountId": "uuid",
  "destinationAccountId": "uuid",
  "amount": 5000.00,
  "timestamp": "2026-02-11T10:00:00Z"
}
```

**Response - NEGATIVE (Legitimate):**
```json
{
  "analysisId": "uuid",
  "result": "NEGATIVE",
  "riskScore": 15,
  "riskLevel": "LOW",
  "reasons": [],
  "recommendation": "APPROVE",
  "analyzedAt": "2026-02-11T10:00:01Z"
}
```

**Response - POSITIVE (Fraud Detected):**
```json
{
  "analysisId": "uuid",
  "result": "POSITIVE",
  "riskScore": 85,
  "riskLevel": "HIGH",
  "reasons": [
    "High value transaction detected",
    "Account created less than 24 hours ago"
  ],
  "recommendation": "DENY",
  "analyzedAt": "2026-02-11T10:00:01Z"
}
```

### 2. Get Analysis by ID
```http
GET /api/v1/fraud/analysis/{analysisId}
```

### 3. Get Analysis History by Account
```http
GET /api/v1/fraud/history/account/{accountId}
```

### 4. Get Fraud Statistics
```http
GET /api/v1/fraud/statistics
```

**Response:**
```json
{
  "totalAnalyses": 10000,
  "fraudDetected": 150,
  "fraudRate": 1.5,
  "averageRiskScore": 25.3,
  "topReasons": [
    "High value transaction",
    "Velocity check failed"
  ]
}
```

---

## 🔄 Risk Scoring Algorithm

### Score Calculation
```
Base Score: 0

IF amount > R$ 10,000         → +50 points
IF amount > R$ 50,000         → +80 points
IF velocity > 5 txn/min       → +40 points
IF unusual hours (2-5 AM)     → +20 points
IF new account (< 24h)        → +30 points
IF first txn > R$ 5,000       → +40 points
IF multiple recipients (>10)  → +50 points
IF rapid small txns (>20)     → +35 points

Risk Levels:
0-30:    LOW      → APPROVE
31-60:   MEDIUM   → REVIEW
61-100:  HIGH     → DENY
```

---

## 🎨 Response Types

### Result Enum
```java
public enum FraudAnalysisResult {
    POSITIVE,  // Fraud detected
    NEGATIVE   // Legitimate transaction
}
```

### Risk Level Enum
```java
public enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
```

### Recommendation Enum
```java
public enum Recommendation {
    APPROVE,   // Allow transaction
    REVIEW,    // Manual review required
    DENY       // Block transaction
}
```

---

## 🔗 Integration with Transaction Service

### Updated Transaction Flow

```
1. User initiates transfer
2. Transaction Service validates accounts
3. → Call Fraud Detection Service
4. ← Receive fraud analysis result
5. IF result = POSITIVE (fraud) → Reject transaction
6. IF result = NEGATIVE → Continue with transfer
7. Update balances
8. Save transaction
```

### Transaction Service Code
```java
@Autowired
private FraudDetectionClient fraudClient;

public TransactionDTO executeTransfer(TransferRequest request) {
    // Step 1-2: Validate accounts
    validateAccounts(request);
    
    // Step 3: Check for fraud
    FraudAnalysisDTO fraudAnalysis = fraudClient.analyzeTransaction(
        FraudAnalysisRequest.builder()
            .sourceAccountId(request.getSourceAccountId())
            .destinationAccountId(request.getDestinationAccountId())
            .amount(request.getAmount())
            .timestamp(LocalDateTime.now())
            .build()
    );
    
    // Step 4: Decision based on fraud analysis
    if (fraudAnalysis.getResult() == FraudAnalysisResult.POSITIVE) {
        throw new FraudDetectedException(
            "Transaction blocked due to fraud detection: " + 
            String.join(", ", fraudAnalysis.getReasons())
        );
    }
    
    // Step 5-8: Continue with transfer
    return processTransfer(request);
}
```

---

## 📁 Project Structure

```
fraud-detection-service/
├── src/main/java/com/example/frauddetectionservice/
│   ├── FraudDetectionServiceApplication.java
│   │
│   ├── domain/
│   │   ├── entity/
│   │   │   ├── FraudAnalysis.java
│   │   │   └── FraudRule.java
│   │   ├── valueobject/
│   │   │   ├── FraudAnalysisResult.java
│   │   │   ├── RiskLevel.java
│   │   │   └── Recommendation.java
│   │   └── service/
│   │       └── RiskScoringService.java
│   │
│   ├── application/
│   │   ├── usecase/
│   │   │   ├── AnalyzeTransactionUseCase.java
│   │   │   ├── GetAnalysisHistoryUseCase.java
│   │   │   └── GetStatisticsUseCase.java
│   │   ├── usecase/impl/
│   │   │   ├── AnalyzeTransactionUseCaseImpl.java
│   │   │   ├── GetAnalysisHistoryUseCaseImpl.java
│   │   │   └── GetStatisticsUseCaseImpl.java
│   │   ├── dto/
│   │   │   ├── FraudAnalysisDTO.java
│   │   │   ├── FraudAnalysisRequest.java
│   │   │   └── FraudStatisticsDTO.java
│   │   └── mapper/
│   │       └── FraudAnalysisMapper.java
│   │
│   ├── infrastructure/
│   │   ├── gateway/
│   │   │   ├── FraudAnalysisGatewayImpl.java
│   │   │   └── AccountGatewayImpl.java
│   │   ├── repository/
│   │   │   └── FraudAnalysisRepository.java
│   │   ├── client/
│   │   │   └── AccountServiceClient.java
│   │   └── config/
│   │       ├── MongoConfig.java
│   │       └── FeignConfig.java
│   │
│   └── interfaceadapter/
│       ├── controller/
│       │   └── FraudDetectionController.java
│       └── gateway/
│           ├── FraudAnalysisGateway.java
│           └── AccountGateway.java
│
└── src/main/resources/
    └── application.yml
```

---

## 🗄️ Database Schema

### Collection: fraud_analyses
```json
{
  "_id": "ObjectId",
  "analysisId": "UUID",
  "transactionId": "UUID",
  "sourceAccountId": "UUID",
  "destinationAccountId": "UUID",
  "amount": 5000.00,
  "result": "POSITIVE|NEGATIVE",
  "riskScore": 85,
  "riskLevel": "LOW|MEDIUM|HIGH|CRITICAL",
  "reasons": ["High value transaction", "New account"],
  "recommendation": "APPROVE|REVIEW|DENY",
  "rulesTriggered": ["RULE_HIGH_VALUE", "RULE_NEW_ACCOUNT"],
  "analyzedAt": "2026-02-11T10:00:00Z",
  "createdAt": "2026-02-11T10:00:00Z"
}
```

### Indexes
```javascript
db.fraud_analyses.createIndex({ "transactionId": 1 }, { unique: true });
db.fraud_analyses.createIndex({ "sourceAccountId": 1, "analyzedAt": -1 });
db.fraud_analyses.createIndex({ "result": 1, "analyzedAt": -1 });
db.fraud_analyses.createIndex({ "riskLevel": 1 });
```

---

## 🚀 Deployment

### Docker Compose Update
```yaml
fraud-detection-service:
  build: ./fraud-detection-service
  container_name: fraud-detection-service
  ports:
    - "8085:8085"
  environment:
    SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/instant_payment_fraud?authSource=admin
    SERVICES_ACCOUNT_URL: http://account-service:8082
  depends_on:
    - mongodb
    - account-service
  networks:
    - instant-payment-network
```

### API Gateway Routes
```yaml
- id: fraud-detection-service
  uri: http://localhost:8085
  predicates:
    - Path=/api/v1/fraud/**
```

---

## 📊 Monitoring & Metrics

### Key Metrics
- Total analyses performed
- Fraud detection rate
- Average response time
- False positive rate
- Rule effectiveness

### Health Check
```http
GET http://localhost:8085/actuator/health
```

### Metrics Endpoint
```http
GET http://localhost:8085/actuator/metrics/fraud.analysis.total
GET http://localhost:8085/actuator/metrics/fraud.detection.rate
```

---

## 🧪 Testing Scenarios

### Scenario 1: Legitimate Transaction
```json
Request: {
  "sourceAccountId": "xxx",
  "destinationAccountId": "yyy",
  "amount": 500.00
}

Expected: {
  "result": "NEGATIVE",
  "riskScore": 10,
  "recommendation": "APPROVE"
}
```

### Scenario 2: High Value Fraud
```json
Request: {
  "sourceAccountId": "xxx",
  "destinationAccountId": "yyy",
  "amount": 50000.00
}

Expected: {
  "result": "POSITIVE",
  "riskScore": 80,
  "recommendation": "DENY"
}
```

### Scenario 3: Velocity Attack
```json
Request: 6 transactions within 1 minute

Expected: {
  "result": "POSITIVE",
  "riskScore": 40,
  "reasons": ["Velocity check failed"],
  "recommendation": "DENY"
}
```

---

## 🔐 Security Considerations

### API Security
- Internal service only (not exposed to public)
- Service-to-service authentication (future)
- Rate limiting on analysis endpoint

### Data Protection
- Encrypted fraud analysis logs
- PII masking in logs
- Retention policy: 90 days

---

## 📈 Future Enhancements

### Machine Learning Integration
- Train ML model on historical fraud data
- Real-time scoring with TensorFlow
- Adaptive learning from blocked transactions

### Advanced Features
- Geolocation analysis
- Device fingerprinting
- Behavioral biometrics
- Social graph analysis
- Blacklist/Whitelist management

### Integration Points
- External fraud detection APIs (Sift, Kount)
- Credit bureau integration
- Law enforcement reporting

---

## 📝 Response Examples

### Example 1: NEGATIVE (Approve)
```json
{
  "analysisId": "123e4567-e89b-12d3-a456-426614174000",
  "result": "NEGATIVE",
  "riskScore": 15,
  "riskLevel": "LOW",
  "reasons": [],
  "recommendation": "APPROVE",
  "rulesTriggered": [],
  "analyzedAt": "2026-02-11T10:30:00Z"
}
```

### Example 2: POSITIVE (Deny)
```json
{
  "analysisId": "123e4567-e89b-12d3-a456-426614174001",
  "result": "POSITIVE",
  "riskScore": 90,
  "riskLevel": "CRITICAL",
  "reasons": [
    "High value transaction detected (R$ 75,000)",
    "Account created less than 24 hours ago",
    "First transaction exceeds R$ 5,000"
  ],
  "recommendation": "DENY",
  "rulesTriggered": [
    "RULE_HIGH_VALUE",
    "RULE_NEW_ACCOUNT",
    "RULE_FIRST_TRANSACTION"
  ],
  "analyzedAt": "2026-02-11T10:31:00Z"
}
```

### Example 3: POSITIVE (Review)
```json
{
  "analysisId": "123e4567-e89b-12d3-a456-426614174002",
  "result": "POSITIVE",
  "riskScore": 55,
  "riskLevel": "MEDIUM",
  "reasons": [
    "Unusual transaction time (3:45 AM)",
    "Multiple recipients detected (12 in last hour)"
  ],
  "recommendation": "REVIEW",
  "rulesTriggered": [
    "RULE_UNUSUAL_HOURS",
    "RULE_MULTIPLE_RECIPIENTS"
  ],
  "analyzedAt": "2026-02-11T03:45:00Z"
}
```

---

**Version**: 2.0.0  
**Port**: 8085  
**Database**: instant_payment_fraud  
**Status**: ✅ READY FOR IMPLEMENTATION

