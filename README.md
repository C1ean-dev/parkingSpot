
# Parking Control API

Está é uma API de controle de vagas de estacionamento! Esta API permite gerenciar vagas de estacionamento com endpoints para criar, listar, atualizar e deletar registros. 
Abaixo está uma visão geral dos endpoints disponíveis e como utilizá-los.

## Endpoints

### Criar uma nova vaga de estacionamento
**POST** `/parking-spot/`

- **Descrição:** Cria uma nova vaga de estacionamento.
- **Corpo da requisição (JSON):**
  ```json
  {
    "parkingSpotNumber": "String",
    "licensePlateCar": "String",
    "brandCar": "String",
    "modelCar": "String",
    "colorCar": "String",
    "responsibleName": "String",
    "apartment": "String",
    "block": "String"
  }
  ```
- **Respostas:**
  - **201 Created:** Vaga de estacionamento criada com sucesso.
  - **409 Conflict:** Dados conflitantes (ex.: vaga ou placa já cadastrada).

---

### Listar todas as vagas de estacionamento
**GET** `/parking-spot/`

- **Descrição:** Retorna uma lista paginada de todas as vagas de estacionamento.
- **Parâmetros de consulta:**
  - `page` (opcional, padrão: `0`): Número da página.
  - `pageSize` (opcional, padrão: `10`): Tamanho da página.
- **Corpo do retorno (JSON):**
  ```json
  {
    "content": [
      {
        "id": "UUID",
        "parkingSpotNumber": "String",
        "licensePlateCar": "String",
        "brandCar": "String",
        "modelCar": "String",
        "colorCar": "String",
        "registrationDate": "DATETIME",
        "responsibleName": "String",
        "apartment": "String",
        "block": "String"
      }
    ]
  }
  ```
- **Respostas:**
  - **200 OK:** Lista de vagas de estacionamento.

---

### Obter uma vaga de estacionamento por ID
**GET** `/parking-spot/{id}`

- **Descrição:** Retorna os detalhes de uma vaga de estacionamento específica.
- **Parâmetros de rota:**
  - `id` (UUID): Identificador único da vaga.
- **Respostas:**
  - **200 OK:** Dados da vaga de estacionamento.
  - **404 Not Found:** Vaga de estacionamento não encontrada.

---

### Deletar uma vaga de estacionamento
**DELETE** `/parking-spot/{id}`

- **Descrição:** Remove uma vaga de estacionamento do sistema.
- **Parâmetros de rota:**
  - `id` (UUID): Identificador único da vaga.
- **Respostas:**
  - **200 OK:** Vaga de estacionamento deletada com sucesso.
  - **404 Not Found:** Vaga de estacionamento não encontrada.

---

### Atualizar uma vaga de estacionamento
**PUT** `/parking-spot/{id}`

- **Descrição:** Atualiza os dados de uma vaga de estacionamento existente.
- **Parâmetros de rota:**
  - `id` (UUID): Identificador único da vaga.
- **Corpo da requisição (JSON):**
  ```json
  {
    "parkingSpotNumber": "String",
    "licensePlateCar": "String",
    "brandCar": "String",
    "modelCar": "String",
    "colorCar": "String",
    "responsibleName": "String",
    "apartment": "String",
    "block": "String"
  }
  ```
- **Respostas:**
  - **200 OK:** Vaga de estacionamento atualizada com sucesso.
  - **404 Not Found:** Vaga de estacionamento não encontrada.
  - **409 Conflict:** Dados conflitantes (ex.: vaga ou placa já cadastrada).

## Tecnologias Utilizadas
- **Java** com **Spring Boot**
- **Jakarta Validation** para validação de dados
- **Spring Data JPA** para manipulação de banco de dados
- **Postgres Database** (pode ser configurado para outro banco)
- **Maven** para gerenciamento de dependências

## Configurações Adicionais
- **CORS:** Configurado para permitir acesso de todas as origens.
- **Paginação:** Padrão de 10 registros por página, com suporte para customização.

## Como Executar 
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/parking-control-api.git
   cd parking-control-api
   ```
2. Instale as dependências com Maven:
   ```bash
   mvn clean install
   ```
3. Inicie a aplicação:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse a API em `http://localhost:8080/`.

## Docker
Um arquivo de compose já está preparado para buildar e rodar tanto a aplicação quanto o banco de dados 
  ```bash
   docker-compose -f "docker-compose.yml" up -d --build
   ```
Caso queira rodar apenas um serviço de cada vez:

postgres
   ```bash
   docker-compose -f "docker-compose.yml" up -d --build postgres
   ```
java
  ```bash
   docker-compose -f "docker-compose.yml" up -d --build app
   ```

## Licença
Este projeto é licenciado sob a [MIT License](LICENSE).
