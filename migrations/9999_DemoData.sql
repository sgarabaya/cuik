USE `cuik`;

SET NAMES 'utf8';

-- Usuarios
INSERT INTO `Users`
    (`id`, `created`, `updated`, `name`, `email`, `password_hash`)
VALUES
    ('34c9dc8d-0899-4ae9-bd90-99e07c9821b6', '2021-05-12 14:20:00', '2021-05-12 14:20:00', 'Mateo Fernández', 'm.fernandez_88@gmail.com', '$2b$12$K9R6VvF9Wz6yQ8uTz5R2eO1aB2c3D4e5F6g7H8i9J0k1L2m3N4o5P'),
    ('d94ac2ca-2489-4c24-b6c6-70df0a2b0e4d', '2023-11-02 09:15:30', '2023-11-02 09:15:30', 'Sofía Rodríguez', 'sofia.rodriguez.arg@yahoo.com', '$2b$12$X7y8Z9a1B2c3D4e5F6g7H8i9J0k1L2m3N4o5P6q7R8s9T0u1V2w3'),
    ('313b54a4-fec6-414f-abbb-65e81cc52cea', '2020-03-25 18:45:10', '2020-03-25 18:45:10', 'Joaquín Martínez', 'j_martinez2024@outlook.com', '$2b$12$P0q1R2s3T4u5V6w7X8y9Z0a1B2c3D4e5F6g7H8i9J0k1L2m3N4o5P'),
    ('e2d6f2e2-d1f9-4af3-a334-b78feb6a4413', '2024-07-19 12:00:00', '2024-07-19 12:00:00', 'Martina González', 'martina.gonzalez!@hotmail.com', '$2b$12$M1n2O3p4Q5r6S7t8U9v0W1x2Y3z4A5b6C7d8E9f0G1h2I3j4K5l6M'),
    ('23d46dd7-81c9-46e0-930b-bc77801be05d', '2022-01-10 22:30:45', '2022-01-10 22:30:45', 'Lautaro Gómez', 'lautaro_gomez92@icloud.com', '$2b$12$A1b2C3d4E5f6G7h8I9j0J1k2L3m4N5o6P7q8R9s0T1u2V3w4X5y6Z'),
    ('a8b020f8-8860-4a18-9c2c-05602e3e7d60', '2025-02-28 08:12:15', '2025-02-28 08:12:15', 'Valentina López', 'valen.lopez.85@gmail.com', '$2b$12$S9t0U1v2W3x4Y5z6A7b8C9d0E1f2G3h4I5j6K7l8M9n0O1p2Q3r4S'),
    ('4507cf4b-646d-4d69-a267-dc4a09261770', '2023-06-14 16:55:00', '2023-06-14 16:55:00', 'Facundo Pérez', 'f.perez_arg@protonmail.com', '$2b$12$Z1a2B3c4D5e6F7g8H9i0I1j2K3l4M5n6O7p8Q9r0S1t2U3v4W5x6Y'),
    ('eb614893-d996-4c81-9302-4b9ed584d753', '2020-12-30 10:05:22', '2020-12-30 10:05:22', 'Delfina Sánchez', 'delfina_sanchez_22@hotmail.com', '$2b$12$L0k9J8i7H6g5F4e3D2c1C1b2A3z4Y5x6W7v8U9t0S1r2Q3p4O5n6'),
    ('93949c52-9ed0-40d8-b64a-ae40e069f7f8', '2024-01-05 13:40:00', '2024-01-05 13:40:00', 'Agustín Romero', 'agustin.romero.99@yahoo.com.ar', '$2b$12$R4s5T6u7V8w9X0y1Z2a3A4b5C6d7E8f9G0h1I2j3K4l5M6n7O8p9Q'),
    ('467b2677-89ff-40b5-8bb3-f2a299610c70', '2022-09-17 04:22:11', '2022-09-17 04:22:11', 'Milagros Álvarez', 'm.alvarez_!@gmail.com', '$2b$12$Q1w2E3r4T5y6U7i8O9p0P0o9I8u7Y6t5R4e3W2q1A1s2D3f4G5h6J')
;

-- Menus
INSERT INTO Menus (id, name, is_active)
VALUES ('94218e67-d8e0-4951-b5a3-7cbc8095fe4b','Comida', 1);

INSERT INTO MenuItems
    (id, menu_id, name, description, category, price, available, estimated_prep_time)
VALUES (
    '4b7ec8c2-78a8-4bda-8c59-1ef19b7985ba',
    '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
    'Empanadas de Carne Cortada a Cuchillo',
    'Empanadas tradicionales horneadas, rellenas de carne cortada a cuchillo, cebolla, aceitunas y huevo duro.',
    'Entradas',
    4000,
    1,
    15
);

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '4b7ec8c2-78a8-4bda-8c59-1ef19b7985ba' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE A.name = 'Gluten';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '4b7ec8c2-78a8-4bda-8c59-1ef19b7985ba' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Huevo';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'e7a29d5d-9942-4ecf-b58f-35447f01609e',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Provoleta',
        'Queso provolone asado a la parrilla con orégano y aceite de oliva.',
        'Entradas',
        8500,
        1,
        20
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'e7a29d5d-9942-4ecf-b58f-35447f01609e' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '119e3752-afab-486e-9c8b-ca10145c1471',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Choripán con Chimichurri',
        'Chorizo asado servido en pan francés con el clásico chimichurri.',
        'Entradas',
        5500,
        1,
        20
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '119e3752-afab-486e-9c8b-ca10145c1471' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'cd270d64-9187-4237-aa12-2fc13df72347',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Mollejas a la Parrilla',
        'Mollejas de vaca crocantes a la parrilla, condimentadas con sal y limón.',
        'Entradas',
        12000,
        1,
        30
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '1533bb85-dc60-4d9b-a1b2-117b52efe1e8',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Asado de Tira',
        'El clásico asado de tira argentino hecho a la leña, servido con chimichurri.',
        'Principales',
        22000,
        1,
        45
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'f9cc60fc-d1bb-4354-ab05-bf5a7b102134',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Milanesa a la Napolitana',
        'Milanesa de carne de vaca cubierta con jamón cocido, salsa de tomate y queso muzzarella derretido.',
        'Principales',
        16000,
        1,
        35
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'f9cc60fc-d1bb-4354-ab05-bf5a7b102134' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'f9cc60fc-d1bb-4354-ab05-bf5a7b102134' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'f9cc60fc-d1bb-4354-ab05-bf5a7b102134' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Huevo';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '482aa2ad-9ad7-45d0-9f23-6bec4b34ca8c',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Bife de Chorizo',
        'Bife de chorizo de corte grueso, hecho a la parrilla al punto deseado.',
        'Principales',
        25000,
        1,
        40
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'e6b1cf75-60fc-4a0d-bc6c-0b93c30bb986',
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Sorrentinos de Jamón y Queso',
        'Sorrentinos grandes rellenos de jamón y muzzarella, servidos con salsa tuco.',
        'Principales',
        14000,
        1,
        30
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'e6b1cf75-60fc-4a0d-bc6c-0b93c30bb986' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'e6b1cf75-60fc-4a0d-bc6c-0b93c30bb986' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'e6b1cf75-60fc-4a0d-bc6c-0b93c30bb986' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Huevo';

INSERT INTO
    Menus (id, name, is_active)
VALUES (
        '77fd9ec4-856b-41b8-baa2-516aaa6d8628',
        'Postres',
        1
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '23c99bb3-4737-42a2-b785-ecd17fa3da80',
        '77fd9ec4-856b-41b8-baa2-516aaa6d8628',
        'Flan Mixto',
        'Flan casero de vainilla con un copo de crema chantilly y mucho dulce de leche.',
        'Dulces',
        6000,
        1,
        10
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '23c99bb3-4737-42a2-b785-ecd17fa3da80' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '23c99bb3-4737-42a2-b785-ecd17fa3da80' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Huevo';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'fbdd7d32-8f26-49b7-8f43-a4aacaeda6cd',
        '77fd9ec4-856b-41b8-baa2-516aaa6d8628',
        'Panqueque de Dulce de Leche',
        'Panqueques tibios bien rellenos con dulce de leche.',
        'Dulces',
        6500,
        1,
        15
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'fbdd7d32-8f26-49b7-8f43-a4aacaeda6cd' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'fbdd7d32-8f26-49b7-8f43-a4aacaeda6cd' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'fbdd7d32-8f26-49b7-8f43-a4aacaeda6cd' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Huevo';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '7fbf5992-c01a-4539-b33e-5688733ade2d',
        '77fd9ec4-856b-41b8-baa2-516aaa6d8628',
        'Chocotorta',
        'Clásica torta argentina sin cocción con galletitas de chocolate, queso crema y dulce de leche.',
        'Dulces',
        7000,
        1,
        5
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '7fbf5992-c01a-4539-b33e-5688733ade2d' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '7fbf5992-c01a-4539-b33e-5688733ade2d' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '644f3892-cdab-4ce0-a57d-6393a7f2a977',
        '77fd9ec4-856b-41b8-baa2-516aaa6d8628',
        'Helado Artesanal',
        'Dos bochas de helado artesanal (sabores Dulce de Leche y Chocolate).',
        'Dulces',
        5500,
        1,
        5
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '644f3892-cdab-4ce0-a57d-6393a7f2a977' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Lácteos';

INSERT INTO
    Menus (id, name, is_active)
VALUES (
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Bebidas',
        1
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'db69e277-d83e-4f90-896e-e8141d249e6a',
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Vino Malbec',
        'Copa de vino tinto Malbec argentino de primera línea.',
        'Con Alcohol',
        9000,
        1,
        5
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT 'db69e277-d83e-4f90-896e-e8141d249e6a' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Sulfitos';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        'ed46674f-9e6a-4ecf-94e6-3d86fdb77a5d',
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Fernet con Coca',
        'El clásico trago argentino con Fernet Branca y Coca-Cola.',
        'Con Alcohol',
        7500,
        1,
        5
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '1933c659-d428-4462-a169-0883497176fa',
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Cerveza Rubia Artesanal',
        'Pinta de cerveza rubia artesanal tirada.',
        'Con Alcohol',
        5000,
        1,
        5
    );

INSERT INTO
    MenuItemAllergens (menu_item_id, allergen_id)
SELECT '1933c659-d428-4462-a169-0883497176fa' AS menu_item_id, A.id AS allergen_id
FROM Allergens A
WHERE
    A.name = 'Gluten';

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '8644df50-457c-413d-a472-1a62760dc944',
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Agua Mineral',
        'Botella de agua mineral con o sin gas.',
        'Sin Alcohol',
        2500,
        1,
        3
    );

INSERT INTO
    MenuItems (
        id,
        menu_id,
        name,
        description,
        category,
        price,
        available,
        estimated_prep_time
    )
VALUES (
        '29048ef1-26e3-410c-9ce2-6d4b235a3e53',
        'a97e4493-a9d7-49c1-9f4a-2ba8cce0cddc',
        'Limonada con Menta y Jengibre',
        'Limonada exprimida con hojas de menta fresca y jengibre.',
        'Sin Alcohol',
        4000,
        1,
        10
    );