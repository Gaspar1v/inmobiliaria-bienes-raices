-- =====================================================================
-- Instalacion en MySQL 8 (ejecutar como root una sola vez)
--   mysql -u root -p < sql/01_crear_bd_mysql.sql
--   mysql -u root -p bienes_raices < src/main/resources/db/schema.sql
--   mysql -u root -p bienes_raices < src/main/resources/db/datos.sql
-- =====================================================================

CREATE DATABASE IF NOT EXISTS bienes_raices
    CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;

-- Usuario propio de la aplicacion con privilegio minimo:
-- solo puede leer y escribir datos, no crear ni borrar tablas.
-- Reemplace la contrasena antes de ejecutar y configurela en BR_DB_PASSWORD.
CREATE USER IF NOT EXISTS 'br_app'@'localhost' IDENTIFIED BY 'CambiarEstaClave#2026';
GRANT SELECT, INSERT, UPDATE, DELETE ON bienes_raices.* TO 'br_app'@'localhost';
FLUSH PRIVILEGES;
