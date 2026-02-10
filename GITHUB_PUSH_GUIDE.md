# 🚀 GUIA: PUSH PARA GITHUB

## 📋 Pré-requisitos

1. ✅ Conta GitHub criada (https://github.com)
2. ✅ Git instalado no computador
3. ✅ Credenciais GitHub configuradas

---

## ⚡ OPÇÃO 1: Rápido (5 minutos)

### Passo 1: Criar repositório no GitHub
1. Acesse: https://github.com/new
2. Nome do repositório: `pix-system` (ou o nome que preferir)
3. Descrição: "Instant Payment System Pix - Java 21, Spring Boot 3.3, Hexagonal Architecture"
4. Escolha: Public ou Private
5. Clique: "Create repository"
6. **Copie a URL do repositório** (vai parecer: `https://github.com/seu-usuario/pix-system.git`)

### Passo 2: Abra PowerShell e execute

```powershell
# Ir para a pasta do projeto
cd C:\Users\elson.franca\dev\petprojects\pix

# Inicializar Git
git init

# Adicionar repositório remoto
git remote add origin https://github.com/seu-usuario/pix-system.git

# Adicionar todos os arquivos
git add .

# Fazer commit
git commit -m "Initial commit: Complete Pix system implementation with Hexagonal Architecture"

# Push para GitHub (main branch)
git branch -M main
git push -u origin main
```

### Pronto! 🎉

Seu projeto está no GitHub!

---

## 📝 OPÇÃO 2: Com .gitignore (Recomendado)

Se não tiver `.gitignore`, crie um para evitar enviar arquivos desnecessários:

### Passo 1: Criar `.gitignore`

```powershell
cd C:\Users\elson.franca\dev\petprojects\pix
```

Crie um arquivo `.gitignore` com este conteúdo:

```
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

# IDE - VSCode
.vscode/*
!.vscode/settings.json
!.vscode/tasks.json

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

# Maven
target/
.m2/
.mvn/wrapper/maven-wrapper.jar
```

### Passo 2: Push com .gitignore

```powershell
# Ir para a pasta
cd C:\Users\elson.franca\dev\petprojects\pix

# Inicializar Git
git init

# Adicionar remote
git remote add origin https://github.com/seu-usuario/pix-system.git

# Adicionar .gitignore
git add .gitignore

# Adicionar tudo (menos o que está em .gitignore)
git add .

# Commit
git commit -m "Initial commit: Complete Pix system with Hexagonal Architecture, Lombok, Spring Boot 3.3"

# Push
git branch -M main
git push -u origin main
```

---

## 🔐 OPÇÃO 3: Com SSH (Mais Seguro)

Se preferir usar SSH ao invés de HTTPS:

### Passo 1: Gerar chave SSH (se não tiver)

```powershell
# Gerar chave SSH
ssh-keygen -t ed25519 -C "seu-email@github.com"

# Pressione Enter para todas as perguntas
```

### Passo 2: Adicionar chave ao SSH agent

```powershell
# Iniciar SSH agent
Get-Service ssh-agent | Set-Service -StartupType Automatic
Start-Service ssh-agent

# Adicionar chave
ssh-add $env:USERPROFILE\.ssh\id_ed25519
```

### Passo 3: Adicionar chave ao GitHub

1. Copie a chave pública:
```powershell
Get-Content $env:USERPROFILE\.ssh\id_ed25519.pub | Set-Clipboard
```

2. Acesse: https://github.com/settings/keys
3. Clique: "New SSH key"
4. Cole a chave
5. Clique: "Add SSH key"

### Passo 4: Push via SSH

```powershell
cd C:\Users\elson.franca\dev\petprojects\pix

git init
git remote add origin git@github.com:seu-usuario/pix-system.git
git add .
git commit -m "Initial commit: Complete Pix system implementation"
git branch -M main
git push -u origin main
```

---

## ✅ VERIFICAR SE FUNCIONOU

```powershell
# Ver status
git status

# Ver commits
git log

# Ver remote
git remote -v
```

Acesse: `https://github.com/seu-usuario/pix-system`

Seu repositório deve estar lá! 🎉

---

## 📦 PRÓXIMOS COMMITS

Depois do primeiro push, para fazer novos commits:

```powershell
# Fazer mudanças nos arquivos...

# Adicionar mudanças
git add .

# Ou adicionar arquivo específico
git add src/main/java/...

# Commit
git commit -m "Descritivo da mudança"

# Push
git push origin main
```

---

## 🔀 BRANCHES (Opcional)

Para trabalhar em features separadas:

```powershell
# Criar branch
git checkout -b feature/mongodb-integration

# Fazer mudanças...

# Commit
git add .
git commit -m "feat: Add MongoDB integration"

# Push branch
git push origin feature/mongodb-integration

# No GitHub: Criar Pull Request
```

---

## 🆘 PROBLEMAS COMUNS

### "Permission denied (publickey)"
**Solução:**
```powershell
# Verificar conexão SSH
ssh -T git@github.com

# Se não funcionar, refaça a configuração SSH (Opção 3)
```

### "fatal: not a git repository"
**Solução:**
```powershell
git init
git remote add origin https://github.com/seu-usuario/pix-system.git
```

### "error: src refspec main does not match any"
**Solução:**
```powershell
git branch -M main
git push -u origin main
```

### "Please tell me who you are"
**Solução:**
```powershell
git config --global user.email "seu-email@gmail.com"
git config --global user.name "Seu Nome"
```

---

## 📚 REFERÊNCIAS

- GitHub: https://github.com
- Git Docs: https://git-scm.com/doc
- GitHub SSH: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

---

**Escolha a Opção 1 ou 2 e siga os passos!** 👆


