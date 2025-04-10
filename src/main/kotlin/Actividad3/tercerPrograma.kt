package org.example.Actividad3

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException


fun main() {
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection) {
        val id = 1
        val lineasEncontradas = baseDatos.obtenerLineasPedidosPorID(conexion, id)
        println("Cantidad de lineas de pedidos con el id $id: $lineasEncontradas")


        // PARTE 2
        val nombreBuscar = "Ataulfo Rodríguez"
        val precioTotalPorUsuario = baseDatos.obtenerPrecioTotalPorUsuario(conexion, nombreBuscar)
        if (precioTotalPorUsuario != null) {
            println("El precio total de las compras de $nombreBuscar es de: $precioTotalPorUsuario")
        } else{
            println("No se ha podido encontrar el usuario o no tiene productos comprados")
        }

        // PARTE 3
        val nombreProducto = "Abanico"
        val listaUsuariosEncontrados = baseDatos.obtenerUsuariosQueHayanComprado(conexion, baseDatos, nombreProducto)
        if (listaUsuariosEncontrados != null) {
            println("Estas son las personas que han comprado el $nombreProducto: ${listaUsuariosEncontrados.joinToString(", ")}")
        } else{
            println("Error, no se ha encontrado personas que hayan comprado ese producto")
        }

    }
}


data class LineaPedido(val cantidad: Int, val precio: Float, val idProducto: Int, val id: Int)

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


    fun obtenerLineasPedidosPorID(conexion: Connection, idEntrada : Int): Int {
        val obtenerLineasPedido = conexion.prepareStatement("SELECT * FROM LINEAPEDIDO WHERE idpedido = ?")
        obtenerLineasPedido.setInt(1, idEntrada)
        val resultadoQuery = obtenerLineasPedido.executeQuery()
        var lineasPedidoTransformadas = mutableListOf<LineaPedido>()
        while (resultadoQuery?.next() == true) {
            val id = resultadoQuery.getInt("id")
            val idPedidoResult = resultadoQuery.getInt("idpedido")
            val idProducto = resultadoQuery.getInt("idproducto")
            val cantidad = resultadoQuery.getInt("cantidad")
            val precio = resultadoQuery.getDouble("precio")
            lineasPedidoTransformadas.add(
                LineaPedido(
                    cantidad,
                    precio.toFloat(),
                    idPedidoResult,
                    idProducto
                )
            ) // Creamos un nuevo objeto LineaPedido con el id
        }
        return lineasPedidoTransformadas.size
    }

    fun obtenerPrecioTotalPorUsuario(conexion: Connection, nombreBuscar: String): Int? {
        val sumaImporte =
            conexion.prepareStatement("SELECT SUM(PRECIOTOTAL) as sumatotalprecio FROM USUARIO INNER JOIN PEDIDO on PEDIDO.idusuario = USUARIO.id  WHERE nombre ILIKE ?")
        sumaImporte.setString(1, nombreBuscar)
        val resultadoImporteTotal = sumaImporte.executeQuery()
        if (resultadoImporteTotal?.next() == true) {
            return resultadoImporteTotal.getInt("sumatotalprecio")
        }
        return null
    }


    fun obtenerNombresCompradores(queryObtenerUsuariosQueHayanComprado: PreparedStatement, nombreProducto: String): MutableList<String>? {
        queryObtenerUsuariosQueHayanComprado.setString(1, nombreProducto)
        val personasConComprasPorProducto = queryObtenerUsuariosQueHayanComprado.executeQuery()
        if (personasConComprasPorProducto?.next() == true) {
            var listaUsuariosEncontrados = mutableListOf<String>()
            val listaUsuarios = personasConComprasPorProducto.getString("nombrePersona").split(" ")
            for (usuario in listaUsuarios) {
                listaUsuariosEncontrados.add(usuario)
            }
            return listaUsuariosEncontrados
        }
        return null
    }


    fun obtenerUsuariosQueHayanComprado(conexion: Connection, baseDatos: BaseDatos, nombreProducto : String): MutableList<String>? {
        val queryObtenerUsuariosQueHayanComprado =
            conexion.prepareStatement("SELECT USUARIO.NOMBRE as nombrePersona FROM USUARIO INNER JOIN PEDIDO ON PEDIDO.IDUSUARIO = USUARIO.ID INNER JOIN LINEAPEDIDO ON LINEAPEDIDO.IDPEDIDO = PEDIDO.ID INNER JOIN PRODUCTO ON PRODUCTO.ID = LINEAPEDIDO.IDPRODUCTO WHERE PRODUCTO.NOMBRE ILIKE ?")

        val precioTotalPorProducto = baseDatos.obtenerPrecioTotalPorUsuario(conexion, nombreProducto)
        if (precioTotalPorProducto == null) {
            println("Error, no se ha podido encontrar el precio total de ese producto")
        } else {
            println("El precio total de ese producto es: $precioTotalPorProducto")
        }

        val listaUsuariosEncontrados =
            baseDatos.obtenerNombresCompradores(queryObtenerUsuariosQueHayanComprado, nombreProducto)
        if (listaUsuariosEncontrados == null) {
            return null
        } else {
            return listaUsuariosEncontrados
        }
    }


}