

# Acceso a base de datos con kotlin

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


## Requisitos del programa

Instalación, configuración y conexión con H2.

1. Instala la base de datos en tu IDE.
2. Configura la base de datos para que funcione en modo fichero.
3. Cambia el PATH de la base de datos. Pista: usa ./Directorio para guardarlo en tu proyecto.
4. Crea un programa en Kotlin para establecer conexión con H2, manejando excepciones.



# Actividad 2
1. Crea las tablas que siguen a continuación en tu base de datos:
```
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
```

## Ejemplo de código

##### FUNCIÓN MAIN
https://github.com/IES-Rafael-Alberti/2425-u9-accesobdkotlin1-Alejandro-Bravo2/blob/49c65df085e40b5d30e83b0e3fd461670b6744a0/src/main/kotlin/Actividad5/quintoPrograma.kt#L7-L34
En la función main se encarga de crear las instancias de las clases necesarias para el flujo del programa y de mostrar por terminal
los resultados, de dichas operaciones. Para informar al usuario. 


##### CLASE BASE DATOS
https://github.com/IES-Rafael-Alberti/2425-u9-accesobdkotlin1-Alejandro-Bravo2/blob/49c65df085e40b5d30e83b0e3fd461670b6744a0/src/main/kotlin/Actividad5/quintoPrograma.kt#L38-L80
La clase base de datos se encarga de tener los métodos para trabajar con la base de datos empotrada, a esta base de datos empotrada
se conecta usando el método conectarBaseDatos.

Los demás métodos son los encargados de ejecutar las peticiones sobre esa base de datos.

##### FLUJO DE EJEMPLO DEL PROGRAMA
https://github.com/IES-Rafael-Alberti/2425-u9-accesobdkotlin1-Alejandro-Bravo2/blob/49c65df085e40b5d30e83b0e3fd461670b6744a0/src/main/kotlin/Actividad5/quintoPrograma.kt#L8-L10
1. Indicamos al programa que drivers necesita para la base de datos
2. Instanciamos las clases necesarias para el flujo del programa.

https://github.com/IES-Rafael-Alberti/2425-u9-accesobdkotlin1-Alejandro-Bravo2/blob/49c65df085e40b5d30e83b0e3fd461670b6744a0/src/main/kotlin/Actividad5/quintoPrograma.kt#L14-L21
1. Creamos una clase que contenga el contenido a usarse en los métodos de la base de datos en este caso un nombre de un producto.
2. Usamos el método de la clase de base de datos para realizar la petición a la base de datos.
3. Usamos el resultado que nos devuelva el método para informar al usuario acerca del resultado de la petición.

https://github.com/IES-Rafael-Alberti/2425-u9-accesobdkotlin1-Alejandro-Bravo2/blob/49c65df085e40b5d30e83b0e3fd461670b6744a0/src/main/kotlin/Actividad5/quintoPrograma.kt#L23-L29
1. Creamos una variable que contenga el id a buscar en la base de datos.
2. Usamos el método de la clase de base de datos para realizar la petición a la base de datos y almacenamos el resultado en una variable.
3. En caso de que el resultado sea true informaremos al usuario de que el id se ha modificado exitosamente para ese nombre de producto. 
4. En caso de que el resultado sea false informaremos al usuario de que no se ha podido modificar el id para ese nombre de producto.

Este es el flujo que siguen todos los ejercicios realizados en este repositorio.

