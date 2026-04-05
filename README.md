📦 Order Management Service — BTG Pactual
Microsserviço de gerenciamento de pedidos desenvolvido com Spring Boot, consumindo eventos via RabbitMQ e persistindo os dados no MongoDB. Projeto inspirado em um desafio técnico do BTG Pactual, com foco em arquitetura orientada a eventos e boas práticas de desenvolvimento backend.

💡 Ideia do Projeto
O objetivo foi construir um serviço capaz de receber pedidos de clientes em tempo real através de uma fila de mensagens, processá-los de forma assíncrona e expor os dados via API REST para consulta paginada e análise financeira por cliente.
A motivação principal foi praticar conceitos de mensageria com RabbitMQ, persistência NoSQL com MongoDB e design de APIs REST em um contexto realista de mercado financeiro.

✨ Funcionalidades

✅ Consumo de eventos de pedidos criados via fila RabbitMQ
✅ Persistência dos pedidos com todos os itens no MongoDB
✅ Cálculo automático do total do pedido (quantidade × preço)
✅ Listagem paginada de pedidos por cliente
✅ Cálculo do valor total gasto por cliente (agregação MongoDB)
✅ Resposta padronizada com paginação e sumário financeiro
✅ Ambiente local completo com Docker Compose (MongoDB + RabbitMQ)

🏗️ Arquitetura
[Producer externo]
       │
       │  publica evento JSON
       ▼
  [ RabbitMQ ]
  Queue: btg-pactual-order-created
       │
       │  OrderCreatedListener consome
       ▼
  [ OrderService ]
  calcula total, monta entidade
       │
       ▼
  [ MongoDB ]
  collection: tb_orders
       │
       ▼
  [ REST API ]
  GET /customers/{id}/orders

📁 Estrutura do Projeto
src/main/java/tech/buildrun/btgpactual/orderms/
│
├── config/
│   └── RabbitMqConfig.java          ← declaração da fila
│
├── controller/
│   ├── OrderController.java         ← endpoint REST
│   └── dto/
│       ├── ApiResponse.java
│       ├── OrderResponse.java
│       └── PaginationResponse.java
│
├── entity/
│   ├── OrderEntity.java             ← documento MongoDB
│   └── OrderItem.java
│
├── listener/
│   ├── OrderCreatedListener.java    ← consumidor RabbitMQ
│   └── dto/
│       ├── OrderCreatedEvent.java
│       └── OrderItemEvent.java
│
├── repository/
│   └── OrderRepository.java
│
└── service/
    └── OrderService.java            ← regras de negócio + agregação

🔌 Endpoint disponível
GET /customers/{customerId}/orders
Lista todos os pedidos de um cliente com paginação e total gasto.
Query params:
ParâmetroTipoPadrãoDescriçãopageInteger0Número da páginapageSizeInteger10Itens por página
Exemplo de resposta:
json{
  "summary": {
    "totalOnOrders": 4750.00
  },
  "data": [
    {
      "orderId": 1001,
      "customerId": 42,
      "total": 1250.00
    },
    {
      "orderId": 1002,
      "customerId": 42,
      "total": 3500.00
    }
  ],
  "pagination": {
    "page": 0,
    "pageSize": 10,
    "totalElements": 2,
    "totalPages": 1
  }
}

📨 Evento consumido via RabbitMQ
Fila: btg-pactual-order-created
Formato do payload esperado:
json{
  "codigoPedido": 1001,
  "codigoCliente": 42,
  "items": [
    {
      "produto": "Notebook",
      "quantidade": 1,
      "preco": 4500.00
    },
    {
      "produto": "Mouse",
      "quantidade": 2,
      "preco": 125.00
    }
  ]
}

🚀 Como Rodar Localmente
Pré-requisitos

Java 21+
Maven
Docker

1. Subir a infraestrutura
bashdocker-compose up -d
Isso sobe o MongoDB na porta 27017 e o RabbitMQ na porta 5672 (management UI na 15672).
2. Rodar a aplicação
bash./mvnw spring-boot:run
3. Acessar os serviços
ServiçoURLAPI RESThttp://localhost:8080RabbitMQ Managementhttp://localhost:15672 (admin / 123)MongoDBmongodb://localhost:27017
4. Publicar um evento de teste
No painel do RabbitMQ (http://localhost:15672), vá em Queues → btg-pactual-order-created → Publish message e envie o payload de exemplo acima.

⚙️ Configuração (application.properties)
propertiesspring.data.mongodb.uri=mongodb://admin:123@localhost:27017/btgpactualdb?authSource=admin

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=123

Melhorias futuras planejadas:

 Adicionar Dead Letter Queue (DLQ) para tratamento de erros
 Implementar idempotência no consumidor (evitar duplicatas)
 Adicionar observabilidade com Spring Actuator + Micrometer
 Testes de integração com Testcontainers
 Dockerizar a própria aplicação com Dockerfile
