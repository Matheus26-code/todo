# ✅ Todo API — API REST de Gerenciamento de Tarefas

API RESTful para gerenciamento de tarefas construída com **Java 17**, **Spring Boot 3.5** e **PostgreSQL**, seguindo princípios de arquitetura em camadas e boas práticas do mercado.

> Projeto desenvolvido do zero como portfólio para demonstrar habilidades em engenharia back-end: arquitetura em camadas, migrations de banco de dados, tratamento de exceções, testes unitários e infraestrutura containerizada.

---

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.5.11 |
| Banco de Dados | PostgreSQL 16 |
| ORM | Spring Data JPA + Hibernate |
| Migrations | Flyway |
| Containerização | Docker + Docker Compose |
| Testes | JUnit 5 + Mockito |
| Build | Maven |
| Utilitários | Lombok |

---

## 🏗️ Decisões Arquiteturais

O projeto segue uma **arquitetura em camadas** com separação clara de responsabilidades:

```
com.mycrud.todo
├── controller      # Camada HTTP — recebe requisições e delega ao service
├── service         # Camada de negócio — regras e orquestração
├── repository      # Camada de dados — comunicação com o banco
├── model           # Entidades de domínio — espelho das tabelas do banco
├── dto             # Objetos de transferência — contratos de entrada e saída da API
├── exception       # Exceções customizadas — erros específicos do domínio
└── handler         # Handler global — respostas de erro padronizadas
```

**Decisões técnicas tomadas:**

- **Injeção via construtor** em vez de `@Autowired` por campo — garante imutabilidade e facilita testes
- **Padrão DTO** — desacopla os contratos da API das entidades do banco, evitando exposição desnecessária de dados
- **Records para DTOs** — imutáveis por natureza, sem boilerplate
- **Flyway para migrations** — versionamento do schema do banco, seguro para ambientes de produção
- **Spring Profiles** — configurações separadas para os ambientes `dev` e `prod`
- **GlobalExceptionHandler** — tratamento centralizado de erros com respostas HTTP semânticas

---

## 📋 Endpoints

URL base: `http://localhost:8080`

### Tarefas

| Método | Endpoint | Descrição | Status HTTP |
|---|---|---|---|
| `POST` | `/tasks` | Criar uma nova tarefa | `201 Created` |
| `GET` | `/tasks` | Listar todas as tarefas | `200 OK` |
| `GET` | `/tasks/{id}` | Buscar tarefa por ID | `200 OK` |
| `PUT` | `/tasks/{id}` | Atualizar uma tarefa | `200 OK` |
| `DELETE` | `/tasks/{id}` | Deletar uma tarefa | `204 No Content` |

### Corpo da Requisição — `POST /tasks` e `PUT /tasks/{id}`

```json
{
  "title": "Estudar Spring Boot",
  "description": "Concluir o módulo de arquitetura em camadas"
}
```

### Corpo da Resposta — `GET /tasks/{id}`

```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Concluir o módulo de arquitetura em camadas",
  "status": "PENDING",
  "createdAt": "2026-03-20T10:31:14.819512"
}
```

### Valores de Status da Tarefa

| Valor | Descrição |
|---|---|
| `PENDING` | Tarefa criada, ainda não iniciada (padrão) |
| `IN_PROGRESS` | Tarefa em andamento |
| `DONE` | Tarefa concluída |

### Resposta de Erro

```json
{
  "status": 404,
  "message": "Task não encontrada com id: 99",
  "timestamp": "2026-03-20T10:52:28.041737"
}
```

---

## 🚀 Como Rodar Localmente

### Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Maven (ou use o wrapper `./mvnw` incluso no projeto)

### 1. Clone o repositório

```bash
git clone https://github.com/Matheus26-code/todo.git
cd todo
```

### 2. Configure o ambiente local

Crie o arquivo `src/main/resources/application-dev.properties` com suas credenciais locais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

> ⚠️ Este arquivo está no `.gitignore` e nunca será commitado.

### 3. Suba o banco de dados

```bash
docker compose up -d
```

Isso inicia um container PostgreSQL 16. O Flyway executa automaticamente as migrations e cria a tabela `tasks` na primeira inicialização.

### 4. Rode a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 🧪 Executando os Testes

```bash
./mvnw test
```

Os testes unitários cobrem a camada `TaskService` usando **JUnit 5** e **Mockito**, com o repositório mockado — nenhum banco de dados é necessário para rodar os testes.

**Casos de teste implementados:**

- `deveCriarTaskComSucesso` — valida criação de tarefa e status padrão `PENDING`
- `deveRetornarExcecaoQuandoTaskNaoEncontrada` — valida comportamento 404
- `deveDeletarTaskComSucesso` — valida o fluxo de deleção com verificação do mock

---

## 🌍 Perfis de Ambiente

| Perfil | Uso |
|---|---|
| `dev` | Desenvolvimento local com credenciais diretas no `application-dev.properties` |
| `prod` | Produção utilizando variáveis de ambiente |

### Variáveis de ambiente para produção

| Variável | Descrição |
|---|---|
| `DB_URL` | URL JDBC completa — ex: `jdbc:postgresql://host:5432/todo` |
| `DB_USERNAME` | Usuário do banco de dados |
| `DB_PASSWORD` | Senha do banco de dados |

---

## 📁 Schema do Banco de Dados

Gerenciado pelo Flyway. Arquivo de migration: `V1__create_tasks_table.sql`

```sql
CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP NOT NULL
);
```

---

## 👨‍💻 Autor

**Matheus** — Desenvolvedor Back-end & Engenheiro Cloud

- GitHub: [@Matheus26-code](https://github.com/Matheus26-code)
- Portfolio: [matheus26-code.github.io](https://matheus26-code.github.io)
- LinkedIn: [linkedin.com/in/seu-perfil](https://linkedin.com/in/seu-perfil)

---

## 📄 Licença

Este projeto é open source e está disponível sob a [Licença MIT](LICENSE).