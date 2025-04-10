package Actividad5

import org.example.Actividad2.BaseDatos
import java.sql.Connection
import java.sql.SQLException

fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")


    if (conexion is Connection) {
        // MODIFICAR EL PRECIO DEL PRODUCTO
        val producto = "Abanico"
        try {
            val queryModificarPrecioProducto = conexion.prepareStatement("UPDATE PRODUCTO set precio = precio * 0.8 where nombre ILIKE ?;")
            queryModificarPrecioProducto.setString(1, producto)
            queryModificarPrecioProducto.executeUpdate()
            println("Modificación del precio exitosa")
        } catch (e: SQLException) {
            println("No se ha podido modificar el precio del producto por uno nuevo...")
        }


        // MODIFICAR ID
        val nuevaIdProducto = 2
        try {
            val queryModificarIdProducto = conexion.prepareStatement("UPDATE LINEAPEDIDO set idproducto = ?, precio = (select precio from producto where nombre ILIKE ?) * 2 where idproducto = (select id from producto where nombre ILIKE ?);")
            queryModificarIdProducto.setInt(1, nuevaIdProducto)
            queryModificarIdProducto.setString(2, producto)
            queryModificarIdProducto.setString(3, producto)
            queryModificarIdProducto.executeUpdate()
            println("Modificación del id producta exitosa")
        } catch (e: SQLException) {
            println("No se ha podido modificar el id del producto por uno nuevo...")
        }


    }

}