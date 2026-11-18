USE `cuik`;

SET NAMES 'utf8';

INSERT INTO `Allergens` (id, name)
VALUES
    (UUID(), 'Leche'), (UUID(), 'Huevos'),
    (UUID(), 'Crustáceos'), (UUID(), 'Maní'),
    (UUID(), 'Trigo'), (UUID(), 'Soja'),
    (UUID(), 'Sésamo'), (UUID(), 'Nueces')
;
