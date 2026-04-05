# 📦 Order Management Service — BTG Pactual

Microsserviço de gerenciamento de pedidos com **Spring Boot**, consumindo eventos via **RabbitMQ** e persistindo no **MongoDB**. Inspirado em desafio técnico do BTG Pactual, focado em arquitetura orientada a eventos.

---

## 💡 Sobre o Projeto

Serviço que recebe pedidos em tempo real via fila de mensagens, processa de forma assíncrona e expõe os dados por API REST com paginação e análise financeira por cliente.

**Conceitos praticados:** mensageria com RabbitMQ · persistência NoSQL · agregações MongoDB · design de APIs REST

---

## ✨ Funcionalidades

- Consumo de eventos de pedidos via fila RabbitMQ
- Persistência dos pedidos com itens no MongoDB
- Cálculo automático do total (quantidade × preço)
- Listagem paginada de pedidos por cliente
- Total gasto por cliente via agregação MongoDB
- Ambiente local com Docker Compose

---

## 🛠️ Stack

`Java 21` `Spring Boot 3` `Spring AMQP` `Spring Data MongoDB` `RabbitMQ 3.13` `MongoDB` `Docker`

---

## 🔌 Endpoint

### `GET /customers/{customerId}/orders`

| Parâmetro  | Padrão | Descrição        |
|------------|--------|------------------|
| `page`     | 0      | Número da página |
| `pageSize` | 10     | Itens por página |

```json
{
  "summary": { "totalOnOrders": 4750.00 },
  "data": [
    { "orderId": 1001, "customerId": 42, "total": 1250.00 },
    { "orderId": 1002, "customerId": 42, "total": 3500.00 }
  ],
  "pagination": { "page": 0, "pageSize": 10, "totalElements": 2, "totalPages": 1 }
}
```

---

## 📨 Evento RabbitMQ

**Fila:** `btg-pactual-order-created`

```json
{
  "codigoPedido": 1001,
  "codigoCliente": 42,
  "items": [
    { "produto": "Notebook", "quantidade": 1, "preco": 4500.00 },
    { "produto": "Mouse", "quantidade": 2, "preco": 125.00 }
  ]
}
```

---

## 🚀 Rodando Localmente

**Pré-requisitos:** Java 21+ · Maven · Docker

```bash
# 1. Subir infraestrutura
docker-compose up -d

# 2. Rodar a aplicação
./mvnw spring-boot:run
```

| Serviço             | URL                                     |
|---------------------|-----------------------------------------|
| API REST            | http://localhost:8080                   |
| RabbitMQ Management | http://localhost:15672 (admin / 123)    |
| MongoDB             | mongodb://localhost:27017               |

Para testar, publique o payload acima em **Queues → btg-pactual-order-created → Publish message** no painel do RabbitMQ.

---

## 📌 Próximos Passos

- [ ] Dead Letter Queue (DLQ) para tratamento de erros
- [ ] Idempotência no consumidor
- [ ] Observabilidade com Spring Actuator + Micrometer
- [ ] Testes de integração com Testcontainers
- [ ] Dockerizar a aplicação

---

## 👨‍💻 Autor

**[seu nome]** · [LinkedIn](https://linkedin.com/in/seuperfil) · [GitHub](https://github.com/seuusuario)
