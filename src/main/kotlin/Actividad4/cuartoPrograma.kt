package org.example.Actividad4

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("Actividad4")
fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection) {
        logger.log(Level.INFO, "Conexión exitosa con la base de datos")
        val nombreUsuario = "Cornelio Ramírez"
        val precioProducto = 24.99F
        val idBorrarAhora = 3


        try {

            // ELIMINACION USUARIO
            logger.log(Level.INFO, "Procediendo a borrar el usuario de la base de datos")
            val eliminarUsuario = baseDatos.eliminarUsuario(conexion, nombreUsuario)
            if (eliminarUsuario == 1){
                logger.log(Level.INFO,"Usuario eliminado correctamente")
            } else if (eliminarUsuario == 0){
                logger.log(Level.WARNING,"Error, no se ha podido eliminar el usuario")
            } else{
                logger.log(Level.WARNING,"Error, en la base de datos...")
            }

            // ELIMINACION PRODUCTO POR PRECIO
            val eliminarProductoPrecio = baseDatos.eliminarProductoPrecio(conexion, precioProducto)
            if (eliminarProductoPrecio){
                logger.log(Level.INFO,"Eliminado correctamente todos los productos con ese precio")
            } else{
                logger.log(Level.WARNING,"Error, no se ha podido eliminar producto o no se ha encontrado ningún producto con ese precio")
            }

            // ELIMINAR PRODUCTO CON ID 3
            val resultadoEliminarProductoPorId = baseDatos.eliminarProductoPorId(conexion, idBorrarAhora)
            if (resultadoEliminarProductoPorId){
                println("Eliminado correctamente todos los productos con ese id")
            } else{
                println("Error, no se ha podido eliminar producto por id o no se ha encontrado ningún producto con ese id")
            }
        } catch (e: Exception) {
            logger.log(Level.WARNING,"Error: ${e.message}")
        }
    }
}




class BaseDatos(){
    fun conectarseBaseDatos(url : String) : Connection?{
        var conexion : Connection? = null
        try {
            Class.forName("org.h2.Driver")
            conexion = DriverManager.getConnection(url)
        } catch (e: SQLException) {
            logger.log(Level.WARNING,"No se encontró el driver jdbc: ${e.message}")
        } catch (e: ClassNotFoundException) {
            logger.log(Level.INFO,"No se encontró el driver jdbc: ${e.message}")
        }

        return conexion
    }

    fun eliminarUsuario(conexion: Connection, nombreUsuario: String) : Int{
        try {
            val STeliminarUsuario = conexion.prepareStatement("DELETE FROM USUARIO WHERE NOMBRE ILIKE ?")
            STeliminarUsuario.setString(1, nombreUsuario)
            val resultado = STeliminarUsuario.executeUpdate()
            if (resultado == 0) {
                return 0
            }
        } catch (e: SQLException) {
            return -1
        }
        return 1

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