CREATE DATABASE sistema_examenes;

USE sistema_examenes;

SELECT * FROM usuarios;

SELECT * FROM roles;
SELECT * FROM usuario_rol;


ALTER TABLE roles ADD rol_nombre VARCHAR(50) NOT NULL;
