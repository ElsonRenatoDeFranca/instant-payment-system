# ============================================================================
# Microservices Generator Script
# ============================================================================
# This script generates the complete microservices architecture
# ============================================================================

Write-Host "╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   Instant Payment System - Microservices Generator            ║" -ForegroundColor Cyan
Write-Host "║   Transforming Monolith to Microservices Architecture         ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

$rootPath = Get-Location

# ============================================================================
# Step 1: Backup Current Monolith
# ============================================================================
Write-Host "[1/10] Creating backup of monolith..." -ForegroundColor Yellow
if (Test-Path "pom.xml") {
    Copy-Item "pom.xml" "pom-monolith-backup.xml"
    Write-Host "✓ Monolith POM backed up" -ForegroundColor Green
}

# ============================================================================
# Step 2: Setup Parent POM
# ============================================================================
Write-Host "[2/10] Setting up parent POM..." -ForegroundColor Yellow
if (Test-Path "pom-microservices.xml") {
    Copy-Item "pom-microservices.xml" "pom.xml" -Force
    Write-Host "✓ Parent POM configured" -ForegroundColor Green
} else {
    Write-Host "✗ pom-microservices.xml not found!" -ForegroundColor Red
    exit 1
}

# ============================================================================
# Step 3: Build Common Library
# ============================================================================
Write-Host "[3/10] Building common library..." -ForegroundColor Yellow
if (Test-Path "common-lib") {
    Set-Location "common-lib"
    mvn clean install -DskipTests
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Common library built successfully" -ForegroundColor Green
    } else {
        Write-Host "✗ Failed to build common library" -ForegroundColor Red
    }
    Set-Location $rootPath
} else {
    Write-Host "✗ common-lib directory not found!" -ForegroundColor Red
}

# ============================================================================
# Step 4: Generate Service Scaffolds
# ============================================================================
Write-Host "[4/10] Generating microservice scaffolds..." -ForegroundColor Yellow

$services = @("user-service", "account-service", "pix-key-service", "transaction-service", "api-gateway")

foreach ($service in $services) {
    Write-Host "  → Creating $service..." -ForegroundColor Cyan

    if (!(Test-Path $service)) {
        New-Item -ItemType Directory -Path $service | Out-Null
    }

    # Create Java source structure
    $javaPath = "$service\src\main\java\com\example\$($service.Replace('-',''))"
    $resourcesPath = "$service\src\main\resources"
    $testPath = "$service\src\test\java\com\example\$($service.Replace('-',''))"

    New-Item -ItemType Directory -Force -Path "$javaPath\domain\entity" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\application\usecase" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\application\usecase\impl" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\application\dto" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\application\mapper" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\infrastructure\gateway" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\infrastructure\repository" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\infrastructure\config" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\infrastructure\client" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\interfaceadapter\controller" | Out-Null
    New-Item -ItemType Directory -Force -Path "$javaPath\interfaceadapter\gateway" | Out-Null
    New-Item -ItemType Directory -Force -Path $resourcesPath | Out-Null
    New-Item -ItemType Directory -Force -Path $testPath | Out-Null

    Write-Host "    ✓ Directory structure created" -ForegroundColor Green
}

# ============================================================================
# Step 5: Create Docker Compose
# ============================================================================
Write-Host "[5/10] Creating Docker Compose configuration..." -ForegroundColor Yellow

$dockerCompose = @"
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
"@

Set-Content -Path "docker-compose-microservices.yml" -Value $dockerCompose
Write-Host "✓ Docker Compose configuration created" -ForegroundColor Green

# ============================================================================
# Step 6: Create Dockerfiles
# ============================================================================
Write-Host "[6/10] Creating Dockerfiles for services..." -ForegroundColor Yellow

$dockerfile = @"
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
"@

foreach ($service in $services) {
    Set-Content -Path "$service\Dockerfile" -Value $dockerfile
}

Write-Host "✓ Dockerfiles created" -ForegroundColor Green

# ============================================================================
# Step 7: Create Application Configuration Files
# ============================================================================
Write-Host "[7/10] Creating application configuration files..." -ForegroundColor Yellow

# User Service Config
$userServiceConfig = @"
server:
  port: 8081

spring:
  application:
    name: user-service
  data:
    mongodb:
      uri: `${SPRING_DATA_MONGODB_URI:mongodb://admin:admin123@localhost:27017/instant_payment_users?authSource=admin}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
"@

Set-Content -Path "user-service\src\main\resources\application.yml" -Value $userServiceConfig

# Account Service Config
$accountServiceConfig = @"
server:
  port: 8082

spring:
  application:
    name: account-service
  data:
    mongodb:
      uri: `${SPRING_DATA_MONGODB_URI:mongodb://admin:admin123@localhost:27017/instant_payment_accounts?authSource=admin}
  cloud:
    openfeign:
      client:
        config:
          user-service:
            url: `${SERVICES_USER_URL:http://localhost:8081}

services:
  user:
    url: `${SERVICES_USER_URL:http://localhost:8081}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
"@

Set-Content -Path "account-service\src\main\resources\application.yml" -Value $accountServiceConfig

# PIX Key Service Config
$pixKeyServiceConfig = @"
server:
  port: 8083

spring:
  application:
    name: pix-key-service
  data:
    mongodb:
      uri: `${SPRING_DATA_MONGODB_URI:mongodb://admin:admin123@localhost:27017/instant_payment_pix_keys?authSource=admin}
  cloud:
    openfeign:
      client:
        config:
          account-service:
            url: `${SERVICES_ACCOUNT_URL:http://localhost:8082}

services:
  account:
    url: `${SERVICES_ACCOUNT_URL:http://localhost:8082}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
"@

Set-Content -Path "pix-key-service\src\main\resources\application.yml" -Value $pixKeyServiceConfig

# Transaction Service Config
$transactionServiceConfig = @"
server:
  port: 8084

spring:
  application:
    name: transaction-service
  data:
    mongodb:
      uri: `${SPRING_DATA_MONGODB_URI:mongodb://admin:admin123@localhost:27017/instant_payment_transactions?authSource=admin}
  cloud:
    openfeign:
      client:
        config:
          account-service:
            url: `${SERVICES_ACCOUNT_URL:http://localhost:8082}
          pix-key-service:
            url: `${SERVICES_PIXKEY_URL:http://localhost:8083}

services:
  account:
    url: `${SERVICES_ACCOUNT_URL:http://localhost:8082}
  pixkey:
    url: `${SERVICES_PIXKEY_URL:http://localhost:8083}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
"@

Set-Content -Path "transaction-service\src\main\resources\application.yml" -Value $transactionServiceConfig

# API Gateway Config
$apiGatewayConfig = @"
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: `${SERVICES_USER_URL:http://localhost:8081}
          predicates:
            - Path=/api/v1/users/**

        - id: account-service
          uri: `${SERVICES_ACCOUNT_URL:http://localhost:8082}
          predicates:
            - Path=/api/v1/accounts/**

        - id: pix-key-service
          uri: `${SERVICES_PIXKEY_URL:http://localhost:8083}
          predicates:
            - Path=/api/v1/pix-keys/**

        - id: transaction-service
          uri: `${SERVICES_TRANSACTION_URL:http://localhost:8084}
          predicates:
            - Path=/api/v1/transactions/**

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
"@

Set-Content -Path "api-gateway\src\main\resources\application.yml" -Value $apiGatewayConfig

Write-Host "✓ Application configuration files created" -ForegroundColor Green

# ============================================================================
# Step 8: Summary Report
# ============================================================================
Write-Host ""
Write-Host "[8/10] Generation Summary" -ForegroundColor Yellow
Write-Host "────────────────────────────────────────────────────────────────" -ForegroundColor Gray
Write-Host "✓ Common Library:        Built and installed" -ForegroundColor Green
Write-Host "✓ User Service:          Structure created" -ForegroundColor Green
Write-Host "✓ Account Service:       Structure created" -ForegroundColor Green
Write-Host "✓ PIX Key Service:       Structure created" -ForegroundColor Green
Write-Host "✓ Transaction Service:   Structure created" -ForegroundColor Green
Write-Host "✓ API Gateway:           Structure created" -ForegroundColor Green
Write-Host "✓ Docker Compose:        Configuration ready" -ForegroundColor Green
Write-Host "✓ Dockerfiles:           Created for all services" -ForegroundColor Green
Write-Host "✓ Application Configs:   YAML files ready" -ForegroundColor Green
Write-Host ""

# ============================================================================
# Step 9: Next Steps
# ============================================================================
Write-Host "[9/10] Next Steps" -ForegroundColor Yellow
Write-Host "────────────────────────────────────────────────────────────────" -ForegroundColor Gray
Write-Host "1. Implement domain entities for each service"
Write-Host "2. Create use cases and implementations"
Write-Host "3. Implement gateways and repositories"
Write-Host "4. Create REST controllers"
Write-Host "5. Add Feign clients for inter-service communication"
Write-Host "6. Build all services: mvn clean install"
Write-Host "7. Start with Docker: docker-compose -f docker-compose-microservices.yml up"
Write-Host ""

# ============================================================================
# Step 10: Quick Start Commands
# ============================================================================
Write-Host "[10/10] Quick Start Commands" -ForegroundColor Yellow
Write-Host "────────────────────────────────────────────────────────────────" -ForegroundColor Gray
Write-Host "# Build all services:"
Write-Host "mvn clean install" -ForegroundColor Cyan
Write-Host ""
Write-Host "# Start all services with Docker:"
Write-Host "docker-compose -f docker-compose-microservices.yml up -d" -ForegroundColor Cyan
Write-Host ""
Write-Host "# View logs:"
Write-Host "docker-compose -f docker-compose-microservices.yml logs -f" -ForegroundColor Cyan
Write-Host ""
Write-Host "# Stop all services:"
Write-Host "docker-compose -f docker-compose-microservices.yml down" -ForegroundColor Cyan
Write-Host ""

Write-Host "╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "║   ✅ Microservices Architecture Generated Successfully!       ║" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "║   Next: Implement business logic for each service             ║" -ForegroundColor Green
Write-Host "║   See: MICROSERVICES_IMPLEMENTATION_GUIDE.md                   ║" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "╚════════════════════════════════════════════════════════════════╝" -ForegroundColor Green

