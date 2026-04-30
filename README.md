# 🛒 E-Commerce Backend Service

A RESTful e-commerce backend service built with **Spring Boot 3.4.2** that supports core e-commerce operations — managing customers, product inventory, carts, orders, payments, and file attachments.

---

## 📑 Table of Contents

- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Environment Variables](#-environment-variables)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [API Endpoints](#-api-endpoints)
  - [Customer](#customer-endpoints)
  - [Product Inventory](#product-inventory-endpoints)
  - [Cart](#cart-endpoints)
  - [Order](#order-endpoints)
  - [Payment](#payment-endpoints)
  - [File Attachment](#file-attachment-endpoints)
- [Database Schema](#-database-schema)
- [Project Structure](#-project-structure)

---

## 🛠 Tech Stack

| Technology              | Version  |
|-------------------------|----------|
| Java                    | 17       |
| Spring Boot             | 3.4.2    |
| Spring Data JPA         | 3.4.x    |
| MySQL                   | 8.x      |
| MapStruct               | 1.5.5    |
| Lombok                  | 1.18.30  |
| SpringDoc OpenAPI (Swagger) | 2.8.5 |
| Spring Dotenv           | 4.0.0    |
| Maven                   | 3.x      |

---

## 🏗 Architecture

```
Controller → Service → Repository → MySQL Database
    ↕            ↕
   DTO  ←→  MapStruct Mapper  ←→  Entity
```

- **DTOs** decouple the API layer from JPA entities.
- **MapStruct** handles entity ↔ DTO mapping at compile time.
- **Specification pattern** is used for dynamic querying (e.g., filter orders by status).
- **BaseEntityAudit** provides automatic audit fields (`dateCreated`, `dateModified`, `createdById`, `modifiedById`) via JPA entity listeners.
- **Global Exception Handler** (`@RestControllerAdvice`) returns consistent error responses.

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.x**
- **MySQL 8.x** running on port `3346` (or configure via env vars)

### Clone & Run

```bash
git clone <repository-url>
cd E-Commerce
```

1. Create a `.env` file in the project root (see [Environment Variables](#-environment-variables)).
2. Build and run:

```bash
# Using Maven Wrapper
./mvnw clean install
./mvnw spring-boot:run

# Or using Maven directly
mvn clean install
mvn spring-boot:run
```

The application starts on **`http://localhost:8080`** by default.

---

## 🔐 Environment Variables

The project uses **[spring-dotenv](https://github.com/paulschwarz/spring-dotenv-java)** to load environment variables from a `.env` file. The `.env` file is git-ignored to keep secrets safe.

Create a `.env` file in the project root:

```env
DB_URL=jdbc:mysql://localhost:3346/ecommerce
DB_USERNAME=root
DB_PASSWORD=your_password_here
```

| Variable      | Default                                | Description                 |
|---------------|----------------------------------------|-----------------------------|
| `DB_URL`      | `jdbc:mysql://localhost:3346/ecommerce`| MySQL JDBC connection URL   |
| `DB_USERNAME` | `root`                                 | Database username           |
| `DB_PASSWORD` | *(required)*                           | Database password           |

> **Note:** Never commit the `.env` file. It is already listed in `.gitignore`.

---

## 📖 API Documentation (Swagger)

Once the application is running, access the interactive API docs:

| Resource         | URL                                           |
|------------------|-----------------------------------------------|
| **Swagger UI**   | http://localhost:8080/swagger-ui/index.html   |
| **OpenAPI JSON** | http://localhost:8080/api-docs                |
| **OpenAPI YAML** | http://localhost:8080/api-docs.yaml           |

---

## 📡 API Endpoints

### Customer Endpoints

| Method | Endpoint              | Description              | Request Body   | Response          |
|--------|-----------------------|--------------------------|----------------|-------------------|
| `POST` | `/customers/create`   | Create a new customer    | `CustomerDto`  | `201 Created`     |

**Sample Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "username": "johndoe",
  "password": "secret123"
}
```

---

### Product Inventory Endpoints

| Method | Endpoint                      | Description                            | Parameters / Body             | Response          |
|--------|-------------------------------|----------------------------------------|-------------------------------|-------------------|
| `POST` | `/product-inventory/save`     | Add new product inventory (batch)      | `List<ProductInventoryDto>`   | `201 Created`     |
| `GET`  | `/product-inventory`          | Get paginated product inventory        | `page`, `pageSize`, `sortBy`, `direction` | `200 OK` (Page)  |
| `GET`  | `/product-inventory/search`   | Search products by term                | `searchTerm`, `page`, `pageSize`, `sortBy`, `direction` | `200 OK` (Page)  |

**Sample Request Body (POST):**
```json
[
  {
    "productId": "PROD-001",
    "name": "Wireless Mouse",
    "description": "Ergonomic wireless mouse",
    "price": 29.99,
    "quantity": 100,
    "status": "AVAILABLE",
    "fileAttachment": [
      { "id": 1 }
    ]
  }
]
```

**Product Statuses:** `AVAILABLE`, `OUT_OF_STOCK`

---

### Cart Endpoints

| Method | Endpoint          | Description                          | Parameters / Body              | Response          |
|--------|-------------------|--------------------------------------|--------------------------------|-------------------|
| `POST` | `/cart/add-to-cart`| Add an item to the cart              | `CartDto`                      | `201 Created`     |
| `GET`  | `/cart`            | Get cart items by visibility status  | `isVisible`, `page`, `pageSize`, `sortBy`, `direction` | `200 OK` (Page)  |

**Sample Request Body (POST):**
```json
{
  "customer": { "id": 1 },
  "productId": "PROD-001",
  "quantity": 2,
  "isVisible": true
}
```

---

### Order Endpoints

| Method | Endpoint          | Description                                   | Parameters / Body              | Response          |
|--------|-------------------|-----------------------------------------------|--------------------------------|-------------------|
| `POST` | `/orders/create`  | Create a new order                            | `OrderDto`                     | `201 Created`     |
| `GET`  | `/orders`         | Get paginated orders for a customer           | `customerId`, `page`, `pageSize`, `sortBy`, `direction` | `200 OK` (Page)  |
| `GET`  | `/orders/spec`    | Get orders filtered by status (Specification) | `status`                       | `200 OK` (List)   |

**Sample Request Body (POST):**
```json
{
  "customer": { "id": 1 },
  "products": [
    {
      "productId": "PROD-001",
      "name": "Wireless Mouse",
      "description": "Ergonomic wireless mouse",
      "price": 29.99,
      "quantity": 2
    }
  ],
  "quantity": 2,
  "status": "CREATED",
  "totalPrice": 59.98
}
```

**Order Statuses:** `CREATED`, `PLACED`, `SHIPPED`, `IN_TRANSIT`, `DELIVERED`

---

### Payment Endpoints

| Method | Endpoint            | Description            | Request Body   | Response          |
|--------|---------------------|------------------------|----------------|-------------------|
| `POST` | `/payments/create`  | Make a new payment     | `PaymentDto`   | `201 Created`     |

**Sample Request Body:**
```json
{
  "customer": { "id": 1 },
  "order": { "id": 1 },
  "paymentDate": "2026-04-07T10:00:00.000+00:00",
  "status": "PROCESSING"
}
```

**Payment Statuses:** `PROCESSING`, `SUCCESS`, `FAILED`

---

### File Attachment Endpoints

| Method | Endpoint                     | Description                      | Request Body              | Response          |
|--------|------------------------------|----------------------------------|---------------------------|-------------------|
| `POST` | `/file-attachments/upload`   | Upload files (multipart)         | `List<MultipartFile>`     | `201 Created`     |

Files are stored locally in the `uploads/` directory. The metadata (file name, path, type, size) is persisted in the `file_attachment` table.

> **Future Enhancement:** File storage will be migrated to **AWS S3**.

---

## 🗄 Database Schema

### Entity Relationship Overview

```
Customer ──< Order ──< Product
Customer ──< Cart
Customer ──< Payment ──── Order
ProductInventory >──< FileAttachment  (Many-to-Many via product_inventory_file)
```

### Tables

| Table                         | Description                                                  |
|-------------------------------|--------------------------------------------------------------|
| `customer`                    | Stores customer details (name, email, username, password)    |
| `customer_order`              | Stores order details, linked to a customer                   |
| `product`                     | Stores products within an order                              |
| `cart`                        | Cart items per customer (unique constraint on customer + product) |
| `payment`                     | Payments linked to a customer and order                      |
| `product_inventory`           | Product catalog/inventory with stock status                  |
| `file_attachment`             | Stores file metadata (name, path, type, size)                |
| `product_inventory_file`      | Join table for ProductInventory ↔ FileAttachment (M:N)       |

### Audit Fields (BaseEntityAudit)

Entities extending `BaseEntityAudit` automatically track:
- `date_created` — Timestamp of creation
- `date_modified` — Timestamp of last modification
- `created_by_id` — Customer who created the record
- `modified_by_id` — Customer who last modified the record

Audited entities: **Order**, **Product**, **Cart**, **Payment**

---

## 📂 Project Structure

```
src/main/java/com/modmed/training/ecommerce/
├── ECommerceApplication.java          # Main application entry point + OpenAPI config
├── controller/
│   ├── CartController.java            # Cart REST endpoints
│   ├── CustomerController.java        # Customer REST endpoints
│   ├── FileAttachmentController.java  # File upload endpoint
│   ├── OrderController.java           # Order REST endpoints
│   ├── PaymentController.java         # Payment REST endpoints
│   ├── ProductController.java         # Product REST endpoints
│   └── ProductInventoryController.java# Product Inventory REST endpoints
├── dto/                               # Data Transfer Objects
│   ├── CartDto.java
│   ├── CustomerDto.java
│   ├── FileAttachmentDto.java
│   ├── OrderDto.java
│   ├── PaymentDto.java
│   ├── ProductDto.java
│   └── ProductInventoryDto.java
├── exception/                         # Global exception handling
│   ├── ApplicationExceptionHandler.java
│   ├── CustomerNotFoundException.java
│   ├── ProductNotFoundException.java
│   ├── ProductOutOfStockException.java
│   └── dto/
├── mappers/                           # MapStruct mappers (Entity ↔ DTO)
│   ├── CartMapper.java
│   ├── CustomerMapper.java
│   ├── FileAttachmentMapper.java
│   ├── OrderMapper.java
│   ├── PaymentMapper.java
│   ├── ProductInventoryMapper.java
│   └── ProductMapper.java
├── model/                             # JPA Entities
│   ├── AdminUser.java
│   ├── Cart.java
│   ├── Customer.java
│   ├── Order.java
│   ├── Payment.java
│   ├── Product.java
│   ├── audit/
│   │   ├── BaseEntityAudit.java       # Audit superclass
│   │   ├── CustomerAware.java         # Interface for customer-aware entities
│   │   └── Listeners/                 # JPA entity listeners
│   ├── enums/
│   │   ├── OrderStatus.java           # CREATED, PLACED, SHIPPED, IN_TRANSIT, DELIVERED
│   │   ├── PaymentStatus.java         # PROCESSING, SUCCESS, FAILED
│   │   └── ProductStatus.java         # AVAILABLE, OUT_OF_STOCK
│   └── productInventory/
│       ├── FileAttachment.java
│       └── ProductInventory.java
├── pagination/
│   └── request/
│       └── PaginationRequest.java     # Reusable pagination params (page, pageSize, sortBy, direction)
├── repo/                              # Spring Data JPA Repositories
│   ├── CartRepo.java
│   ├── CustomerRepo.java
│   ├── FileAttachmentRepo.java
│   ├── OrderRepo.java
│   ├── PaymentRepo.java
│   ├── ProductInventoryRepo.java
│   └── ProductRepo.java
├── service/                           # Business logic (interfaces + impl/)
│   ├── CartService.java
│   ├── CustomerService.java
│   ├── FileAttachmentService.java
│   ├── OrderService.java
│   ├── PaymentService.java
│   ├── ProductInventoryService.java
│   └── ProductService.java
└── specification/                     # JPA Specifications for dynamic queries
    ├── cart/
    ├── order/
    ├── payment/
    ├── product/
    └── productInventory/
```

---

## 📝 Pagination

All paginated endpoints accept the following query parameters via `PaginationRequest`:

| Parameter   | Type      | Default | Description                            |
|-------------|-----------|---------|----------------------------------------|
| `page`      | Integer   | `0`     | Page number (0-based)                  |
| `pageSize`  | Integer   | `10`    | Number of items per page               |
| `sortBy`    | String    | `id`    | Field to sort by                       |
| `direction` | String    | `ASC`   | Sort direction (`ASC` or `DESC`)       |

---

## ⚠️ Error Handling

The application uses a global exception handler (`ApplicationExceptionHandler`) that returns consistent error responses:

```json
{
  "statusCode": "400 BAD_REQUEST",
  "errorMessage": "Product not found with id: PROD-999"
}
```

| Exception                    | HTTP Status             |
|------------------------------|-------------------------|
| `ProductOutOfStockException` | `400 Bad Request`       |
| `ProductNotFoundException`   | `400 Bad Request`       |
| `CustomerNotFoundException`  | `400 Bad Request`       |
| `RuntimeException` (generic) | `500 Internal Server Error` |

---

## 📜 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

---

## 👤 Author

**Yandakuditi Sandeep**
- LinkedIn: [sandeep-yandakuditi](https://www.linkedin.com/in/sandeep-yandakuditi-9a1b65209/)
- Email: sandeep@email.com
