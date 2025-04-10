package org.example.Actividad4

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection) {
        val nombreUsuario = "Cornelio Ramírez"
        val precioProducto = 24.99F
        val idBorrarAhora = 3


        // ELIMINACION USUARIO
        val eliminarUsuario = baseDatos.eliminarUsuario(conexion, nombreUsuario)
        if (eliminarUsuario){
            println("Usuario eliminado correctamente")
        } else{
            println("Error, no se ha podido eliminar el usuario")
        }

        // ELIMINACION PRODUCTO POR PRECIO
        val eliminarProductoPrecio = baseDatos.eliminarProductoPrecio(conexion, precioProducto)
        if (eliminarProductoPrecio){
            println("Eliminado correctamente todos los productos con ese precio")
        } else{
            println("Error, no se ha podido eliminar producto o no se ha encontrado ningún producto con ese precio")
        }

        // ELIMINAR PRODUCTO CON ID 3
        val resultadoEliminarProductoPorId = baseDatos.eliminarProductoPorId(conexion, idBorrarAhora)
        if (resultadoEliminarProductoPorId){
            println("Eliminado correctamente todos los productos con ese id")
        } else{
            println("Error, no se ha podido eliminar producto por id o no se ha encontrado ningún producto con ese id")
        }
    }
}




class BaseDatos(){
    fun conectarseBaseDatos(url : String) : Connection?{
        var conexion : Connection? = null
        try {
            Class.forName("org.h2.Driver")
            conexion = DriverManager.getConnection(url)
            println("Conexión exitosa")
        } catch (e: SQLException) {
            println("No se encontró el driver jdbc: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver jdbc: ${e.message}")
        }

        return conexion
    }

    fun eliminarUsuario(conexion: Connection, nombreUsuario: String) : Boolean{
        try {
            val eliminarUsuario = conexion.prepareStatement("DELETE FROM USUARIO WHERE NOMBRE ILIKE ?")
            eliminarUsuario.setString(1, nombreUsuario)
            val resultado = eliminarUsuario.executeUpdate()
            if (resultado != 0) {
                return false
            }
        } catch (e: SQLException) {
            return false
        }
        return true

    }

    fun eliminarProductoPrecio(conexion: Connection, precioProducto: Float) : Boolean{
        try {
            val queryEliminarProducto = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE PRECIO = ?")
            queryEliminarProducto.setFloat(1, precioProducto)
            val resultadoEliminacionProducto = queryEliminarProducto.executeUpdate()
            if (resultadoEliminacionProducto != 0) {
                return true
            }
        } catch (e: SQLException) {
            return false
        }
        return false

    }

    fun eliminarProductoPorId(conexion: Connection, idBorrarAhora: Int) : Boolean {
        try {
            val queryEliminarProductoPorId = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?")
            queryEliminarProductoPorId.setInt(1, idBorrarAhora)
            val resultadoBorrarPorId = queryEliminarProductoPorId.executeUpdate()
            if (resultadoBorrarPorId != 0) {
                return true
            }
        } catch (e: SQLException) {
            return false
        }
        return false
    }
}