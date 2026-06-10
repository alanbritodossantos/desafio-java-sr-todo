# Desafio Técnico - Java Sênior - To-Do API

Projeto desenvolvido para o desafio técnico de Desenvolvedor Java Sênior.

A solução consiste em uma aplicação de gerenciamento de tarefas com backend em Java/Spring Boot, banco PostgreSQL, migrations com Flyway, fluxo orientado a eventos, frontend em React + TypeScript, testes automatizados e pipeline CI/CD com GitHub Actions.

---

## Tecnologias utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Flyway
* Bean Validation
* Spring Boot Actuator
* Lombok
* JUnit
* Mockito

### Frontend

* React
* TypeScript
* Vite
* Axios

### DevOps

* Docker
* Docker Compose
* GitHub Actions

---

## Funcionalidades

A aplicação permite:

* Criar tarefas
* Listar tarefas
* Atualizar tarefas
* Remover tarefas

A estrutura da tarefa contém:

* ID
* Título
* Descrição
* Status
* Data de criação

Os status disponíveis são:

* `PENDING`
* `IN_PROGRESS`
* `COMPLETED`

---

## Arquitetura

O backend foi organizado em camadas para separar responsabilidades e facilitar evolução da aplicação.

```text
src/main/java/com/alanbrito/todoapi
├── api
│   ├── controller
│   └── dto
├── application
│   ├── event
│   └── service
├── domain
│   ├── enums
│   ├── model
│   └── repository
└── infrastructure
    └── exception
```

### Camadas

#### API

Responsável por expor os endpoints REST e receber as requisições HTTP.

Principais classes:

* `TaskController`
* `CreateTaskRequest`
* `UpdateTaskRequest`
* `TaskResponse`

#### Application

Responsável por coordenar os casos de uso da aplicação.

Principais classes:

* `TaskService`
* `TaskChangedEvent`
* `TaskChangedEventHandler`

#### Domain

Responsável pelas regras e estruturas centrais do domínio.

Principais classes:

* `Task`
* `TaskStatus`
* `TaskRepository`

#### Infrastructure

Responsável por aspectos técnicos de suporte, como tratamento global de exceções.

Principais classes:

* `GlobalExceptionHandler`
* `ErrorResponse`

---

## Decisões técnicas

### Spring Boot

Embora o desafio mencione Micronaut como desejável, foi utilizado Spring Boot por ser uma solução madura, amplamente adotada no mercado e adequada para desenvolvimento rápido de APIs REST com boa organização arquitetural.

### PostgreSQL com Docker

O PostgreSQL foi utilizado como banco relacional principal, executado via Docker Compose para facilitar a execução local sem necessidade de instalação manual do banco.

### Flyway

Foi utilizado Flyway para versionamento do banco de dados. A criação da tabela `tasks` é realizada pela migration:

```text
V1__create_tasks_table.sql
```

### Separação em camadas

A aplicação foi separada em API, Application, Domain e Infrastructure para reduzir acoplamento, melhorar legibilidade e permitir evolução futura.

### DTOs

Foram utilizados DTOs para entrada e saída da API, evitando expor diretamente a entidade de domínio nos contratos HTTP.

### Tratamento global de exceções

Foi criado um handler global para padronizar respostas de erro, como tarefas não encontradas e erros de validação.

### Event-driven

Foi implementado um fluxo orientado a eventos usando `ApplicationEventPublisher` do Spring.

Ao criar ou atualizar uma tarefa, a aplicação publica um evento `TaskChangedEvent`, que é processado pelo `TaskChangedEventHandler`.

Essa abordagem mantém o serviço de tarefas desacoplado do processamento do evento e permite evolução futura para Kafka, RabbitMQ ou Redpanda.

### Frontend simples

O frontend foi implementado com React + TypeScript usando Vite. A interface permite listar tarefas e criar novas tarefas, demonstrando a integração com o backend.

### CI/CD

Foi configurado um pipeline com GitHub Actions para executar os testes automaticamente a cada push ou pull request na branch `main`.

---

## Como executar o projeto localmente

### Pré-requisitos

É necessário ter instalado:

* Java 17
* Docker Desktop
* Node.js
* Git

---

## Subir o banco de dados

Na raiz do projeto, execute:

```bash
docker compose up -d
```

Para verificar se o PostgreSQL está rodando:

```bash
docker ps
```

O container esperado é:

```text
todo-postgres
```

---

## Rodar o backend

Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

No Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Healthcheck:

```text
http://localhost:8080/actuator/health
```

Endpoint principal:

```text
http://localhost:8080/api/tasks
```

---

## Rodar o frontend

Entre na pasta do frontend:

```bash
cd todo-frontend
```

Instale as dependências:

```bash
npm install
```

Execute o frontend:

```bash
npm run dev
```

A aplicação ficará disponível em:

```text
http://localhost:5173
```

---

## Testes automatizados

Para executar os testes:

```bash
./mvnw test
```

No Windows PowerShell:

```powershell
.\mvnw.cmd test
```

---

## Endpoints da API

### Criar tarefa

```http
POST /api/tasks
```

Exemplo de body:

```json
{
  "title": "Nova tarefa",
  "description": "Descrição da tarefa"
}
```

---

### Listar tarefas

```http
GET /api/tasks
```

---

### Atualizar tarefa

```http
PUT /api/tasks/{id}
```

Exemplo de body:

```json
{
  "title": "Tarefa atualizada",
  "description": "Descrição atualizada",
  "status": "IN_PROGRESS"
}
```

---

### Remover tarefa

```http
DELETE /api/tasks/{id}
```

---

## Pipeline CI/CD

O projeto possui pipeline configurado em:

```text
.github/workflows/ci.yml
```

O pipeline executa:

* Checkout do repositório
* Setup do Java 17
* Inicialização do PostgreSQL
* Execução dos testes automatizados

---

## Uso de IA

Durante o desenvolvimento, ferramentas de IA foram utilizadas como apoio para:

* Organização do plano de implementação
* Revisão de estrutura de pastas
* Apoio na escrita de documentação
* Apoio na identificação e correção de erros durante a execução local
* Sugestões de boas práticas para arquitetura, testes e pipeline

As decisões técnicas, validações locais, commits, execução dos testes e ajustes finais foram conduzidos e revisados pelo desenvolvedor.

---

## Histórico de commits

O projeto foi desenvolvido com commits incrementais para demonstrar a evolução da solução, incluindo:

* Configuração inicial com PostgreSQL e Flyway
* Criação do domínio de tarefas
* Implementação do CRUD
* Tratamento global de exceções
* Fluxo orientado a eventos
* Testes automatizados
* Pipeline CI/CD
* Frontend React integrado ao backend

---

## Observações finais

A solução prioriza clareza, organização, separação de responsabilidades e facilidade de execução local.

O fluxo event-driven foi implementado de forma simplificada com eventos internos do Spring, mantendo baixo acoplamento e permitindo evolução futura para mensageria real, como Kafka ou Redpanda.
