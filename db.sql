CREATE DATABASE IF NOT EXISTS `cuik`;

USE `cuik`;

/*
All entities in the system will contain:
> id (uuid) VARCHAR(36)
> created DATETIME
> updated DATETIME
*/

CREATE TABLE IF NOT EXISTS `Users` (
    `id` VARCHAR(36) PRIMARY KEY,
    `created` DATETIME DEFAULT CURRENT_TIME,
    `updated` DATETIME DEFAULT CURRENT_TIME,
    `name` VARCHAR(256) NOT NULL UNIQUE,
    `email` VARCHAR(256) NOT NULL UNIQUE,
    `passwordHash` VARCHAR(512) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Employee` (
    `id` VARCHAR(36) PRIMARY KEY,
    `created` DATETIME DEFAULT CURRENT_TIME,
    `updated` DATETIME DEFAULT CURRENT_TIME,
    `name` VARCHAR(256) NOT NULL UNIQUE,
    `email` VARCHAR(256) NOT NULL UNIQUE,
    `passwordHash` VARCHAR(512) NOT NULL,
    `isAdmin` BIT DEFAULT 0
);

/*
Insert test records:
*/

INSERT IGNORE INTO
    `Employee` (
        `id`,
        `name`,
        `email`,
        `passwordHash`,
        `isAdmin`
    )
VALUES (
        '00000000-0000-0000-0000-000000000000',
        'admin',
        'admin@cuik.food',
        '$argon2i$v=19$m=65536,t=10,p=1$yS/ozAZroTl+zQVklkg5Hg$oNf3i23mu9kUnzVCuTnA0Gtlh1LDq4RnpDSR8a2ytvg', -- pwd: "admin"
        1
    );

\! echo 'Done.';