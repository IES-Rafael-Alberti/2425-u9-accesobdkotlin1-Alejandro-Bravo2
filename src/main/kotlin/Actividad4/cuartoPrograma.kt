package org.example.Actividad4

import org.example.Actividad2.BaseDatos
import org.example.Actividad2.LineaPedido
import java.sql.Connection

fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection) {
        // ELIMINACION USUARIO
        val eliminarUsuario = conexion.prepareStatement("DELETE FROM USUARIO WHERE NOMBRE ILIKE ?")
        val nombreUsuario = "Cornelio Ramírez"
        eliminarUsuario.setString(1, nombreUsuario)
        val resultado = eliminarUsuario.executeUpdate()
        if (resultado != 0) {
            println("Usuario eliminado correctamente")
        }

        // ELIMINACION PRODUCTO POR PRECIO
        val queryEliminarProducto = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE PRECIO = ?")
        val precioProducto = 24.99F
        queryEliminarProducto.setFloat(1, precioProducto)
        val resultadoEliminacionProducto = queryEliminarProducto.executeUpdate()
        if (resultadoEliminacionProducto != 0) {
            println("Producto eliminado correctamente por su campo precio")
        }
        // REVISAR SI TODOS ESTOS FUNCIONAN ADEMÁS DE COJER Y CAMBIARLOS PARA QUE DEVUELVA LOS NOMBRES DE LOS ELIMINADOS Y LOS MUESTRE

        // ELIMINAR PRODUCTO CON ID 3
        val queryEliminarProductoPorId = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?")
        val idBorrarAhora = 3
        queryEliminarProductoPorId.setInt(1, idBorrarAhora)
        val resultadoBorrarPorId = queryEliminarProductoPorId.executeUpdate()
        if (resultadoBorrarPorId != 0) {
            println("Producto eliminado correctamente")
        }
    }
}