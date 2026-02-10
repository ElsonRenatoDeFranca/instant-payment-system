# 🚀 PUSH PARA GITHUB - RESUMO RÁPIDO

## ⚡ FORMA MAIS RÁPIDA (2 minutos)

### 1️⃣ Criar repositório no GitHub
- Acesse: https://github.com/new
- Nome: `pix-system`
- Clique: "Create repository"
- **Copie a URL** (ex: `https://github.com/seu-usuario/pix-system.git`)

### 2️⃣ Abra PowerShell e execute:

```powershell
cd C:\Users\elson.franca\dev\petprojects\pix

git init
git remote add origin https://github.com/seu-usuario/pix-system.git
git add .
git commit -m "Initial commit: Complete Pix system"
git branch -M main
git push -u origin main
```

### 3️⃣ Pronto! ✅

Seu projeto está em:
```
https://github.com/seu-usuario/pix-system
```

---

## 🤖 FORMA AUTOMÁTICA (Usar o script)

Se tiver o script `push-to-github.ps1` no diretório:

```powershell
cd C:\Users\elson.franca\dev\petprojects\pix
.\push-to-github.ps1
```

O script fará tudo automaticamente! 🎉

---

## 🔐 SE USAR SSH (Mais Seguro)

### Gerar chave (primeira vez)
```powershell
ssh-keygen -t ed25519 -C "seu-email@github.com"
# Pressione Enter para tudo
```

### Adicionar chave ao GitHub
1. Copie a chave:
```powershell
Get-Content $env:USERPROFILE\.ssh\id_ed25519.pub | Set-Clipboard
```

2. Vá para: https://github.com/settings/keys
3. Cole a chave em "New SSH key"

### Push via SSH
```powershell
git remote add origin git@github.com:seu-usuario/pix-system.git
git push -u origin main
```

---

## ✅ VERIFICAR SE FUNCIONOU

Acesse: `https://github.com/seu-usuario/pix-system`

Você deve ver:
- ✅ Todos os arquivos Java
- ✅ Documentação
- ✅ pom.xml
- ✅ Arquivos do projeto

---

## 🔄 PRÓXIMOS COMMITS

Depois do primeiro push, para fazer updates:

```powershell
# Fazer mudanças...

git add .
git commit -m "Descritivo da mudança"
git push origin main
```

---

## 📚 DOCUMENTAÇÃO COMPLETA

Para mais detalhes, leia: `GITHUB_PUSH_GUIDE.md`

---

**Escolha a forma mais rápida e execute!** 👆


