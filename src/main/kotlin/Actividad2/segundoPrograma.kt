package org.example.Actividad2
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


fun main(){
    Class.forName("org.h2.Driver")
    val baseDatos = BaseDatos()
    val conexion = baseDatos.conectarseBaseDatos("jdbc:h2:./DataBase/mydb")

    if (conexion is Connection){


        val plantillaCreacionUsuarios = conexion.prepareStatement("INSERT INTO USUARIO (nombre, email) VALUES (?, ?)")
        val plantillaCreacionProductos = conexion.prepareStatement("INSERT INTO PRODUCTO (nombre, precio, stock) VALUES (?, ?, ?)")
        val plantillaCreacionPedido = conexion.prepareStatement("INSERT INTO PEDIDO (precioTotal, idusuario) VALUES (?, ?)")
        val plantillaCreacionLineaPedido = conexion.prepareStatement("INSERT INTO LINEAPEDIDO (cantidad, precio, idpedido, idproducto) VALUES (?, ?, ?,?)")


        val usuario1 = Usuario("Facundo Pérez", "facuper@mail.com")
        val usuario2 = Usuario("Ataulfo Rodríguez", "ataurod@mail.com")
        val usuario3 = Usuario("Cornelio Ramírez", "Cornram@mail.com")

        val producto1 = Producto("Ventilador", 10.0F, 2)
        val producto2 = Producto("Abanico", 150.0F, 47)
        val producto3 = Producto("Estufa", 24.99F, 1)

        val pedidos1 = Pedido(160.0F, 1)
        val pedidos2 = Pedido(20.0F, 2)
        val pedidos3 = Pedido(150.0F, 3)


        val lineaPedido = LineaPedido(1, 10.0F, 1, 1) // TENGO QUE CAMBIAR LOS ID POR LOS DE CADA CLASE
        val lineaPedido2 = LineaPedido(1, 150.0F, 1, 1)
        val lineaPedido3 = LineaPedido(2, 20.0F, 2, 2)
        val lineaPedido4 = LineaPedido(1, 150.0F, 3, 3)


        val listaUsuarios = mutableListOf(usuario1, usuario2, usuario3)
        val listaProductos = mutableListOf(producto1, producto2, producto3)
        val listaPedidos = mutableListOf(pedidos1, pedidos2, pedidos3)
        val listaLineaPedidos = mutableListOf(lineaPedido, lineaPedido2, lineaPedido3, lineaPedido4)

        for (usuario in listaUsuarios){
            plantillaCreacionUsuarios.setString(1, usuario.nombre)
            plantillaCreacionUsuarios.setString(2, usuario.email)
            plantillaCreacionUsuarios.executeUpdate()
        }

        for (producto in listaProductos){
            plantillaCreacionProductos.setString(1, producto.nombre)
            plantillaCreacionProductos.setFloat(2, producto.precio)
            plantillaCreacionProductos.setInt(3, producto.stock)
            plantillaCreacionProductos.executeUpdate()
        }

        for (pedido in listaPedidos){
            plantillaCreacionPedido.setFloat(1, pedido.precioTotal)
            plantillaCreacionPedido.setInt(2, pedido.idUsuario)
            plantillaCreacionPedido.executeUpdate()
        }

        for (listaPedidoLineas in listaLineaPedidos){
            plantillaCreacionLineaPedido.setInt(1, listaPedidoLineas.cantidad)
            plantillaCreacionLineaPedido.setFloat(2, listaPedidoLineas.precio)
            plantillaCreacionLineaPedido.setInt(3, listaPedidoLineas.idPedido)
            plantillaCreacionLineaPedido.setInt(4, listaPedidoLineas.idProducto)
            plantillaCreacionLineaPedido.executeUpdate()
        }

        plantillaCreacionUsuarios.close()
        plantillaCreacionProductos.close()
        plantillaCreacionPedido.close()
        plantillaCreacionLineaPedido.close()

        conexion.close()




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
}

class Usuario(val nombre: String, val email: String){
    val id : Int = idGenerado

    companion object{
        private var idGenerado = 0
            get() = field++
    }
}
class Producto(val nombre: String, val precio: Float, val stock : Int){
    val id : Int = idGenerado

    companion object{
        private var idGenerado = 0
            get() = field++
    }
}
class Pedido(val precioTotal : Float, val idUsuario : Int){
    val id : Int = idGenerado

    companion object{
        private var idGenerado = 0
            get() = field++
    }
}
class LineaPedido(val cantidad: Int, val precio: Float, val idPedido: Int, val idProducto: Int){
    val id : Int = idGenerado

    companion object{
        private var idGenerado = 0
            get() = field++
    }

}