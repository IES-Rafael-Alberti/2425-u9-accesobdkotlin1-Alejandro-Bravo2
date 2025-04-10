package org.example.Actividad3

import org.example.Actividad2.BaseDatos
import org.example.Actividad2.LineaPedido
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


fun main() {
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection) {
        val obtenerLineasPedido = conexion.prepareStatement("SELECT * FROM LINEAPEDIDO WHERE idpedido = ?")
        obtenerLineasPedido.setInt(1, 1)
        val resultadoQuery = obtenerLineasPedido.executeQuery()
        var lineasPedidoTransformadas = mutableListOf<LineaPedido>()
        while (resultadoQuery?.next() == true) {
            val id = resultadoQuery.getInt("id")
            val idPedidoResult = resultadoQuery.getInt("idpedido")
            val idProducto = resultadoQuery.getInt("idproducto")
            val cantidad = resultadoQuery.getInt("cantidad")
            val precio = resultadoQuery.getDouble("precio")
            lineasPedidoTransformadas.add(LineaPedido(cantidad, precio.toFloat(), idPedidoResult, idProducto)) // Creamos un nuevo objeto LineaPedido con el id
        }
        println("Cantidad de lineas de pedidos con id: 1 son: ${lineasPedidoTransformadas.size}")

        // PARTE 2
        val nombreBuscar = "Ataulfo Rodríguez"
        val sumaImporte = conexion.prepareStatement("SELECT SUM(PRECIOTOTAL) as sumatotalprecio FROM USUARIO INNER JOIN PEDIDO on PEDIDO.idusuario = USUARIO.id  WHERE nombre ILIKE ?")
        sumaImporte.setString(1, nombreBuscar)
        val resultadoImporteTotal = sumaImporte.executeQuery()
        if (resultadoImporteTotal?.next() == true) {
            println("La suma total del importe del usuario: $nombreBuscar es: ${resultadoImporteTotal.getInt("sumatotalprecio")}$")
        }

        // PARTE 3
        val queryObtenerUsuariosQueHayanComprado = conexion.prepareStatement("SELECT USUARIO.NOMBRE as nombrePersona FROM USUARIO INNER JOIN PEDIDO ON PEDIDO.IDUSUARIO = USUARIO.ID INNER JOIN LINEAPEDIDO ON LINEAPEDIDO.IDPEDIDO = PEDIDO.ID INNER JOIN PRODUCTO ON PRODUCTO.ID = LINEAPEDIDO.IDPRODUCTO WHERE PRODUCTO.NOMBRE ILIKE ?")

        val nombreProducto = "Abanico"

        queryObtenerUsuariosQueHayanComprado.setString(1, nombreProducto)
        val personasConComprasPorProducto = queryObtenerUsuariosQueHayanComprado.executeQuery()
        if (personasConComprasPorProducto?.next() == true) {
            var listaUsuariosEncontrados = mutableListOf<String>()
            val listaUsuarios = personasConComprasPorProducto.getString("nombrePersona").split(" ")
            for (usuario in listaUsuarios) {
                listaUsuariosEncontrados.add(usuario)
            }
            println("Estas son las personas que han comprado $nombreProducto: ${listaUsuariosEncontrados.joinToString(", ")}")
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
}