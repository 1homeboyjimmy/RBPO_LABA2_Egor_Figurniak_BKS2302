INSERT INTO warehouses (name, location) VALUES ('Склад 1', 'Москва');
INSERT INTO warehouses (name, location) VALUES ('Склад 2', 'СПб');

INSERT INTO items (name, description, category) VALUES ('Ноутбук', 'Игровой', 'Электроника');
INSERT INTO items (name, description, category) VALUES ('Мышка', 'Беспроводная', 'Периферия');

INSERT INTO stocks (warehouse_id, item_id, quantity) VALUES (1, 1, 10);
INSERT INTO stocks (warehouse_id, item_id, quantity) VALUES (2, 2, 20);