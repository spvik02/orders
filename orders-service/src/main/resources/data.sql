INSERT INTO orders (id, order_number, total_amount, order_date, recipient, delivery_address, payment_type, delivery_type)
VALUES (1, '3408020250102', 38.0, '2025-01-02', 'Zagreus', 'Underworld', 'cash', 'delivery'),
       (2, '3408020250202', 106.0, '2025-02-02', 'Cerberus', 'Underworld', 'cash', 'pickup'),
       (3, '3408020250302', 8.0, '2025-03-02', 'Hades', 'Underworld', 'card', 'delivery');

INSERT INTO order_details (id, product_code, product_name, quantity, unit_price, order_id)
VALUES (1, 1, 'coffee', 3, 6.00, 1),
       (2, 3, 'food', 2, 10.00, 1),
       (3, 2, 'milk', 3, 2.00, 2),
       (4, 3, 'food', 10, 10.00, 2),
       (5, 1, 'coffee', 1, 6.00, 3),
       (6, 2, 'milk', 1, 2.00, 3);

select setval('orders_id_seq', (select(max(id)) from orders));
select setval('order_details_id_seq', (select(max(id)) from order_details));
