CREATE DATABASE IF NOT EXISTS `cuik`;

USE `cuik`;

-- All entities in the system will contain:
-- > id (uuid) VARCHAR(36)
-- > created DATETIME
-- > updated DATETIME

CREATE TABLE IF NOT EXISTS `Users` (
    `id` VARCHAR(36) PRIMARY KEY,
    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `name` VARCHAR(256) NOT NULL UNIQUE,
    `email` VARCHAR(256) NOT NULL UNIQUE,
    `password_hash` VARCHAR(512) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Employee` (
    `id` VARCHAR(36) PRIMARY KEY,
    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `name` VARCHAR(256) NOT NULL UNIQUE,
    `email` VARCHAR(256) NOT NULL UNIQUE,
    `password_hash` VARCHAR(512) NOT NULL,
    `isAdmin` BIT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `Menu` (
    `id` VARCHAR(36) PRIMARY KEY,
    `created` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `name` VARCHAR(40) NOT NULL UNIQUE,
    `description` TEXT,
    `is_active` BIT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS `MenuItem` (
    `id` VARCHAR(36) PRIMARY KEY,
    `menu_id` VARCHAR(36) NOT NULL,
    `name` VARCHAR(40) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10,2) NOT NULL,
    `available` BIT DEFAULT 1,
    `prep_time_estimation` INT,

    FOREIGN KEY(`menu_id`) REFERENCES `Menu`(`id`)
);

CREATE TABLE IF NOT EXISTS `MenuItemImage` (
    `id` VARCHAR(36) PRIMARY KEY,
    `menu_item_id` VARCHAR(36) NOT NULL,
    `url` VARCHAR(40) NOT NULL,

    FOREIGN KEY(`menu_item_id`) REFERENCES `MenuItem`(`id`)
);

CREATE TABLE IF NOT EXISTS `NutritionalFact` (
    `id` VARCHAR(36) PRIMARY KEY,
    `menu_item_id` VARCHAR(36) NOT NULL,
    `description` TEXT NOT NULL,
    `portion` FLOAT NOT NULL,
    `calories` FLOAT NOT NULL,
    `protein` FLOAT DEFAULT 0,
    `fats` FLOAT DEFAULT 0,
    `is_vegan` BIT DEFAULT 0,
    `is_gluten_free` BIT DEFAULT 0,

    FOREIGN KEY(`menu_item_id`) REFERENCES `MenuItem`(`id`)
);

CREATE TABLE IF NOT EXISTS `Allergens` (
    `id` VARCHAR(36) PRIMARY KEY,
    `description` TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS `MenuItemAllergens` (
    `menu_item_id` VARCHAR(36) NOT NULL,
    `allergen_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY(`menu_item_id`, `allergen_id`)
);

/*
Insert test records:
*/

INSERT IGNORE INTO
    `Employee` (
        `id`,
        `name`,
        `email`,
        `password_hash`,
        `isAdmin`
    )
VALUES (
        '00000000-0000-0000-0000-000000000000',
        'admin',
        'admin@cuik.food',
        '$argon2i$v=19$m=65536,t=10,p=1$yS/ozAZroTl+zQVklkg5Hg$oNf3i23mu9kUnzVCuTnA0Gtlh1LDq4RnpDSR8a2ytvg', -- pwd: "admin"
        1
    );
