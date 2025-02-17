# Тестовое задание

Разработано 2 сервиса на Spring Boot. 
- Приложение orders-service реализует функционал управления заказами и предоставляет REST API для клиента.
- Приложение number-generate-service реализует функционал для генерации и выдачи номера заказа и предоставляет REST API для
сервиса orders.

Подробнее:
- Конфигурация производится стандартным образом через YAML-файл
- Данные о заказах хранятся в реляционной базе данных PostgreSQL. Для работы с ней используется Spring JDBC.
- Добавлены Unit-тесты
- Swagger-контракт для приложения находится в ресурсах приложения: /resources/static/swagger.yaml
- Удобное отображения REST API приложения в браузере: [ui](http://localhost:8080/swagger-ui/index.html#/) 
- Приложение собирается с помощью Gradle
- Для сервисов написаны Dockerfile и docker-compose.
Для запуска в корне проекта нужно выполнить команду `docker-compose up --build`

## Cервис по управлению заказами orders-service порт 8080

Для сервиса orders-service реализовано следующее API (/orders):

1. Создание заказа 
`POST /orders`

Request body:
```
{
  "recipient": "recipient",
  "deliveryAddress": "address",
  "paymentType": "cash",
  "deliveryType": "pickup"
}
```

- Для paymentType возможны значение ('card', 'cash')
- Для deliveryType возможны значение ('pickup', 'delivery')
- order_number будет сгенерировано и выдано number-generate-service
- total_amount будет установлено в 0
- order_date будет устанволено в соответствии с датой содания заказа 

1.1. Доплнительно была реализована возможность добавления деталей к заказу. После добавления деталей общая сумма заказа будет пересчитана. 

`POST /orders/details`

Request body: 
```
{
  "productCode": 109,
  "productName": "name",
  "quantity": 1,
  "unitPrice": 9,
  "orderId": 3
}
```


2. Получение заказа по его идентификатору 
`GET /orders/{id}`

Для удобства тестирования были добавлены записи с id: 1, 2, 3;

3. Получение заказов за заданную дату и больше заданной общей суммы заказа
`GET /orders/filter/by-date-and-min-amount`

Пример: `http://localhost:8080/orders/filter/by-date-and-min-amount?date=2025-02-17&minAmount=6`

4. Получение списка заказов, не содержащих заданный товар и поступивших в заданный временной период
`GET /orders/filter/exclude-product-and-in-time-period`

Пример: `http://localhost:8080/orders/filter/exclude-product-and-in-time-period?productName=coffee&startDate=2025-01-01&endDate=2025-02-20`

## Сервис генерации номер заказа number-generate-service порт 8081
Реализован GET метод `/numbers`, который будет возвращать сгенерированный номер заказа (5 символов) и текущую дату (формат YYYYMMDD), пример 1111120241212. Для хранения сгенерированных номеров использована NoSQL база данных Redis.
