drop table if exists order_details;
drop table if exists orders;

CREATE TABLE orders
(
    id               BIGSERIAL PRIMARY KEY,
    order_number     CHAR(13)       NOT NULL UNIQUE CHECK (LENGTH(TRIM(order_number)) = 13),
    total_amount     NUMERIC(10, 2) NOT NULL CHECK (total_amount >= 0),
    order_date       DATE           NOT NULL,
    recipient        VARCHAR(100)   NOT NULL,
    delivery_address VARCHAR(255),
    payment_type     VARCHAR(4) NOT NULL CHECK (payment_type IN ('card', 'cash')),
    delivery_type    VARCHAR(8) NOT NULL CHECK (delivery_type IN ('pickup', 'delivery'))
);

CREATE TABLE order_details
(
    id           BIGSERIAL PRIMARY KEY,
    product_code INTEGER        NOT NULL,
    product_name VARCHAR(100)   NOT NULL,
    quantity     INTEGER        NOT NULL CHECK (quantity > 0),
    unit_price   NUMERIC(10, 2) NOT NULL CHECK (unit_price > 0),
    order_id     INTEGER        NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);
