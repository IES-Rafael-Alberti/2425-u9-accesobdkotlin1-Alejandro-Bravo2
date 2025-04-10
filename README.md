Instalación, configuración y conexión con H2.

1. Instala la base de datos en tu IDE.
2. Configura la base de datos para que funcione en modo fichero.
3. Cambia el PATH de la base de datos. Pista: usa ./Directorio para guardarlo en tu proyecto.
4. Crea un programa en Kotlin para establecer conexión con H2, manejando excepciones.

# DOCUMENTAR LUEGO CON PRIMER PROGRAMA


# Actividad 2
1. Crea las tablas que siguen a continuación en tu base de datos:

CREATE TABLE Usuario (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE
);

CREATE TABLE Producto (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
precio DECIMAL NOT NULL,
stock INT NOT NULL
);

CREATE TABLE Pedido (
id INT AUTO_INCREMENT PRIMARY KEY,
precioTotal DECIMAL NOT NULL,
idUsuario INT,
FOREIGN KEY (idUsuario) REFERENCES Usuario(id)
);

CREATE TABLE LineaPedido (
id INT AUTO_INCREMENT PRIMARY KEY,
cantidad INT NOT NULL,
precio DECIMAL NOT NULL,
idPedido INT,
idProducto INT,
FOREIGN KEY (idPedido) REFERENCES Pedido(id),
FOREIGN KEY (idProducto) REFERENCES Producto(id)
);



# Título de la Actividad

## Identificación de la Actividad

- **ID de la Actividad:** UD 11 - Acceso a base de datos
- **Módulo:** PROG
- **Unidad de Trabajo:** UD 11 - Acceso a base de datos
- **Fecha de Creación:** 06/4/2025
- **Fecha de Entrega:** 10/4/2025
- **Alumno(s):**
    - **Nombre y Apellidos:** Alejandro Bravo Calderón
    - **Correo electrónico:**  abracal@g.educaand.es
    - **Iniciales del Alumno/Grupo:** abc

## Descripción de la Actividad

Esta actividad consiste en realizar pruebas usando programación para conectarnos a una base de datos empotrada. Para esto haremos uso
de la base de datos h2 y realizando preparestatemts para realizar plantillas para hacer las peticiones a la base de datos. 
También aprenderemos a realizar pull de conexiones con HikariCP.

## Instrucciones de Compilación y Ejecución

1. **Requisitos Previos:**

    - Kotlin
    - Intellij IDEA
    - JVM 23
2. **Pasos para Ejecutar el Código:**
   Ejecútalo con intellij idea por lo que solo tendrás que usar el atajo de teclas de ctrl + F5 o pulsando en el botón de start

## Desarrollo de la Actividad
La actividad se ha desarrollado usando orientado objeto para la estructura del programa.
Toda la actividad se ha desarrollado para evitar errores inexperados y posibles ataques inyeccion sql.


