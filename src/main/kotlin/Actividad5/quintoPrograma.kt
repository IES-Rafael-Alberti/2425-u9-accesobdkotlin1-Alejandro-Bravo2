package Actividad5

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger


private val logger = Logger.getLogger("Actividad5")

fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")


    if (conexion is Connection) {
        val producto = "Abanico"
        val resultadoModificarPrecio = baseDatos.modificarPrecioProducto(conexion, producto)

        if (resultadoModificarPrecio) {
            println("Precio modificado exitosamente")
        } else{
            logger.log(Level.WARNING,"Error, no se ha podido modificar el precio")
        }

        val nuevaIdProducto = 2
        val resultadoModificacionId = baseDatos.modificarIdProducto(conexion, nuevaIdProducto, producto)
        if (resultadoModificacionId) {
            println("ID modificado exitosamente")
        } else{
            logger.log(Level.WARNING,"Error, no podido modificar el ID")
        }


    }

}



class BaseDatos(){
    fun conectarseBaseDatos(url : String) : Connection?{
        var conexion : Connection? = null
        try {
            Class.forName("org.h2.Driver")
            conexion = DriverManager.getConnection(url)
            logger.log(Level.INFO,"Conexión exitosa")
        } catch (e: SQLException) {
            logger.log(Level.WARNING,"No se encontró el driver jdbc: ${e.message}")
        } catch (e: ClassNotFoundException) {
            logger.log(Level.WARNING,"No se encontró el driver jdbc: ${e.message}")
        }

        return conexion
    }
    fun modificarPrecioProducto(conexion: Connection, producto: String) : Boolean {
        try {
            val queryModificarPrecioProducto =
                conexion.prepareStatement("UPDATE PRODUCTO set precio = precio * 0.8 where nombre ILIKE ?;")
            queryModificarPrecioProducto.setString(1, producto)
            queryModificarPrecioProducto.executeUpdate()
        } catch (e: SQLException) {
            return false
        }
        return true
    }


    fun modificarIdProducto(conexion: Connection, nuevaIdProducto: Int, producto: String) : Boolean {
        try {
            val queryModificarIdProducto =
                conexion.prepareStatement("UPDATE LINEAPEDIDO set idproducto = ?, precio = (select precio from producto where nombre ILIKE ?) * 2 where idproducto = (select id from producto where nombre ILIKE ?);")
            queryModificarIdProducto.setInt(1, nuevaIdProducto)
            queryModificarIdProducto.setString(2, producto)
            queryModificarIdProducto.setString(3, producto)
            queryModificarIdProducto.executeUpdate()
        } catch (e: SQLException) {
            logger.log(Level.WARNING,"No se ha podido modificar el id del producto por uno nuevo...")
            return false
        }
        return true
    }
}