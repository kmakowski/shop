# Shop products service

### Requirements

1. Java 11
2. Apache maven (tested using version 3.6.0)
3. docker (used to perform integration tests with mongodb)
4. docker-compose (used to quickly spin up local mongodb)

### How to build

```
$ mvn clean package
```

### How to test
```
$ mvn test
```
Tests should be safe to be executed in parallel as random free port is 
used to launch app and mongodb for testing.

### How to run

Run docker compose with app dependencies (MongoDB) firstly
```
$ docker-compose up -d
```
Run the application
```
$ mvn spring-boot:run
```

### REST API
#### Create product

```
POST /products 
{
    "name":"someName",
    "priceCents": 123
}
```
```
$ curl -d '{"name":"someName","priceCents":123}' -H 'Content-Type: application/json' localhost:8080/products
```
#### List products

```
GET /products
GET /products?page=1&size=10
```
```
$ curl localhost:8080/products
```
#### Delete product

```
DELETE /products/{productId}
```
```
$ curl -XDELETE localhost:8080/products/1070ea0c-70a3-4a8d-9ac2-579b5b4f9124
```
