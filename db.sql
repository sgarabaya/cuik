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

\! echo 'Done.';