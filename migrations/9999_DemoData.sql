USE `cuik`;

SET NAMES 'utf8';

INSERT INTO
    Menus (id, name, is_active)
VALUES (
        '94218e67-d8e0-4951-b5a3-7cbc8095fe4b',
        'Comida',
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
WHERE
    A.name = 'Gluten';

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