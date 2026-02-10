#!/usr/bin/env pwsh
# GitHub Push Script para Projeto Pix
# Este script automatiza o processo de fazer push do projeto para GitHub

Write-Host "╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║                                                                ║" -ForegroundColor Cyan
Write-Host "║         🚀 GITHUB PUSH SCRIPT - PROJETO PIX 🚀               ║" -ForegroundColor Cyan
Write-Host "║                                                                ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Solicitar informações do usuário
Write-Host "📋 Informações Necessárias:" -ForegroundColor Yellow
Write-Host ""

$githubUrl = Read-Host "Digite a URL do repositório GitHub (ex: https://github.com/usuario/pix-system.git)"
$commitMessage = Read-Host "Digite a mensagem do commit (default: 'Initial commit: Complete Pix system')"

if ([string]::IsNullOrEmpty($commitMessage)) {
    $commitMessage = "Initial commit: Complete Pix system with Hexagonal Architecture"
}

$branch = "main"

Write-Host ""
Write-Host "📝 Resumo:" -ForegroundColor Yellow
Write-Host "  • Repositório: $githubUrl"
Write-Host "  • Branch: $branch"
Write-Host "  • Mensagem: $commitMessage"
Write-Host ""

$confirm = Read-Host "Prosseguir? (S/n)"

if ($confirm -eq "n") {
    Write-Host "❌ Operação cancelada." -ForegroundColor Red
    exit
}

Write-Host ""
Write-Host "🔄 Iniciando processo..." -ForegroundColor Green
Write-Host ""

# Passo 1: Verificar se está no diretório correto
$projectPath = "C:\Users\elson.franca\dev\petprojects\pix"
if (!(Test-Path $projectPath)) {
    Write-Host "❌ Diretório do projeto não encontrado: $projectPath" -ForegroundColor Red
    exit
}

Set-Location $projectPath
Write-Host "✅ Diretório do projeto: $projectPath" -ForegroundColor Green

# Passo 2: Configurar Git (se necessário)
Write-Host ""
Write-Host "📧 Configurando Git..." -ForegroundColor Yellow

$userEmail = Read-Host "Digite seu email GitHub"
$userName = Read-Host "Digite seu nome (para commits)"

git config user.email "$userEmail"
git config user.name "$userName"
Write-Host "✅ Configuração Git atualizada" -ForegroundColor Green

# Passo 3: Inicializar repositório Git
Write-Host ""
Write-Host "🔧 Inicializando repositório Git..." -ForegroundColor Yellow

if (!(Test-Path ".git")) {
    git init
    Write-Host "✅ Repositório Git inicializado" -ForegroundColor Green
} else {
    Write-Host "✅ Repositório Git já existe" -ForegroundColor Green
}

# Passo 4: Adicionar remote
Write-Host ""
Write-Host "🌐 Adicionando repositório remoto..." -ForegroundColor Yellow

# Remover remote existente se houver
git remote remove origin 2>$null

git remote add origin "$githubUrl"
Write-Host "✅ Repositório remoto adicionado: $githubUrl" -ForegroundColor Green

# Passo 5: Criar .gitignore se não existir
Write-Host ""
Write-Host "📄 Verificando .gitignore..." -ForegroundColor Yellow

if (!(Test-Path ".gitignore")) {
    Write-Host "   Criando .gitignore..."

    @"
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
.flattened-pom.xml

# IDEs
.idea/
.vscode/
*.swp
*.swo
*~
.DS_Store
*.iml
*.classpath
.settings/
.project

# Compiled class files
*.class

# Log file
*.log
log/

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs
hs_err_pid*
"@ | Out-File -Encoding UTF8 ".gitignore"

    Write-Host "✅ .gitignore criado" -ForegroundColor Green
} else {
    Write-Host "✅ .gitignore já existe" -ForegroundColor Green
}

# Passo 6: Adicionar arquivos
Write-Host ""
Write-Host "📦 Adicionando arquivos..." -ForegroundColor Yellow

git add .
Write-Host "✅ Arquivos adicionados ao staging" -ForegroundColor Green

# Passo 7: Fazer commit
Write-Host ""
Write-Host "💾 Fazendo commit..." -ForegroundColor Yellow

git commit -m "$commitMessage"
Write-Host "✅ Commit realizado" -ForegroundColor Green

# Passo 8: Renomear branch para main
Write-Host ""
Write-Host "🔀 Verificando branch..." -ForegroundColor Yellow

git branch -M main
Write-Host "✅ Branch: main" -ForegroundColor Green

# Passo 9: Push para GitHub
Write-Host ""
Write-Host "🚀 Fazendo push para GitHub..." -ForegroundColor Yellow
Write-Host "   (Isso pode levar alguns minutos...)" -ForegroundColor Gray

git push -u origin main

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Push concluído com sucesso!" -ForegroundColor Green
} else {
    Write-Host "❌ Erro ao fazer push" -ForegroundColor Red
    Write-Host "   Verifique sua conexão e credenciais" -ForegroundColor Gray
    exit
}

# Passo 10: Resumo final
Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "║              ✅ PUSH CONCLUÍDO COM SUCESSO! ✅               ║" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "║  Acesse seu repositório:                                      ║" -ForegroundColor Green
Write-Host "║  $githubUrl" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "║  Próximos passos:                                             ║" -ForegroundColor Green
Write-Host "║  1. Verifique os arquivos no GitHub                          ║" -ForegroundColor Green
Write-Host "║  2. Configure as configurações do repositório                ║" -ForegroundColor Green
Write-Host "║  3. (Opcional) Configure Actions/CI-CD                       ║" -ForegroundColor Green
Write-Host "║                                                                ║" -ForegroundColor Green
Write-Host "╚════════════════════════════════════════════════════════════════╝" -ForegroundColor Green

Write-Host ""
Write-Host "📊 Estatísticas:" -ForegroundColor Cyan
git log --oneline
Write-Host ""
Write-Host "🌐 Remote:" -ForegroundColor Cyan
git remote -v
Write-Host ""

