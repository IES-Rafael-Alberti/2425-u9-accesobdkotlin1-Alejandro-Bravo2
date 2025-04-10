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