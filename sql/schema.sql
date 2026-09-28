-- =============================================================
-- Base de datos "escuela" para el CRUD de usuarios y materias
-- Motor: MySQL 8
-- =============================================================

CREATE DATABASE IF NOT EXISTS escuela
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE escuela;

CREATE TABLE IF NOT EXISTS usuario (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    email   VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS materia (
    id_materia        INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(100) NOT NULL,
    ciclo             INT          NOT NULL CHECK (ciclo > 0),
    clave_de_materia  VARCHAR(20)  NOT NULL UNIQUE,
    total_de_horas    INT          NOT NULL CHECK (total_de_horas > 0)
);

-- Datos de ejemplo (ficticios)
INSERT IGNORE INTO usuario (nombre, email) VALUES
    ('Ana López',   'ana@ejemplo.com'),
    ('Luis Martínez', 'luis@ejemplo.com');

INSERT IGNORE INTO materia (nombre, ciclo, clave_de_materia, total_de_horas) VALUES
    ('Bases de Datos',              3, 'BD-301',  80),
    ('Paradigmas de Programación',  2, 'PP-201',  64);
