# Dendê Eventos

API REST para gerenciamento de eventos, desenvolvida em Java com Spring Boot pela **Equipe Tokyo** como projeto acadêmico da disciplina de Desenvolvimento Web Orientada a Objetos .

## Sobre o projeto

O Dendê Eventos permite que organizadores cadastrem e gerenciem eventos presenciais ou online, e que usuários comuns comprem e cancelem ingressos. Suporta sub-eventos vinculados a um evento principal, controle de capacidade, estorno configurável e ciclo de vida completo dos eventos (inativo → ativo → encerrado/cancelado).

## Tecnologias

- Java 21
- Spring Boot 
- Spring Data JPA
- Hibernate
- Lombok
- Springdoc OpenAPI (Swagger)
- MySQL

## Como rodar

**Pré-requisitos:** Java 21 e MySQL instalados.

**1. Clone o repositório**
```bash
git clone https://github.com/equipe-tokyo/dende-eventos.git
cd dende-eventos
```

**2. Configure o banco de dados**

Crie um banco MySQL e ajuste o `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dende_eventos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

**3. Rode a aplicação**
```bash
./gradlew bootRun
```

A API sobe em `http://localhost:8080`.

**4. Acesse o Swagger**

```
http://localhost:8080/swagger-ui/index.html
```

## Endpoints principais

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/eventos` | Feed público de eventos ativos |
| POST | `/usuarios` | Cadastrar usuário comum |
| POST | `/organizadores` | Cadastrar organizador |
| POST | `/organizadores/{email}/eventos` | Cadastrar evento |
| PATCH | `/organizadores/{email}/eventos/{id}/status?acao=ativar` | Alterar status do evento |
| POST | `/ingressos/usuario/{email}/evento/{id}` | Comprar ingresso |
| PATCH | `/ingressos/{id}/cancelar?email=...` | Cancelar ingresso |

A documentação completa de todos os endpoints está disponível no Swagger.

## Equipe

| Membro | GitHub |
|--------|--------|
| Valeria | [— ](https://github.com/valeriasoars)|
| Anna Beatriz  |[ — ](https://github.com/biaslima)|
| Ian |[ —](https://github.com/IanSalomao) |
| Fátima | [—](https://github.com/Fatimapsp) |
| Rebeca |[ —](https://github.com/rebecaheIen) |

