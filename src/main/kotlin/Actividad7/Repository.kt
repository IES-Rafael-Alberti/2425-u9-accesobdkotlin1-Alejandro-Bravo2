package org.example.Actividad7
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger


private val logger = Logger.getLogger("Actividad7Repositorio")


class Conexion
{
    val enlaceBD = Class.forName("org.h2.Driver")
    lateinit var c: Connection
    private val url : String = "jdbc:h2:./DataBase/mydb"
    init {
        c = DriverManager.getConnection(url)

    }
}

class LineaDAO(private val c: Connection) // TENGO QUE RELLENAR TODOS LOS MÉTODOS
{
    fun insertar(lineaPedido: LineaPedido) {
        try {
            val queryInsertar = c.prepareStatement("INSERT INTO LINEAPEDIDO (CANTIDAD, PRECIO, IDPEDIDO, IDPRODUCTO) VALUES(?,?,?,?);")
            queryInsertar.use {
                it.setInt(1, lineaPedido.cantidad)
                it.setDouble(2, lineaPedido.precio)
                it.setInt(3, lineaPedido.idPedido)
                it.setInt(4, lineaPedido.idProducto)
                it.executeUpdate()
            }
        } catch (e: SQLException) {logger.log(Level.WARNING,"Error de BBDD")}
    }

    fun mostrar(idPedido: Int):MutableList<LineaPedido> {
        val listaLineasPedidos = mutableListOf<LineaPedido>()
        try {
            val queryObtener = c.prepareStatement("SELECT * FROM LINEAPEDIDO where IDPEDIDO=?;")
            queryObtener.use {
                it.setInt(1, idPedido)
                val resultado = it.executeQuery()
                while (resultado?.next() == true) {
                    val idExtraido = resultado.getInt("id")
                    val precioExtraido = resultado.getDouble("precio")
                    val idProductoExtraido = resultado.getInt("idProducto")
                    val idPedidoExtraido = resultado.getInt("idPedido")
                    val cantidadExtraido = resultado.getInt("cantidad")
                    val lineaPedidoCreado = LineaPedido(idExtraido, cantidadExtraido, precioExtraido, idPedidoExtraido, idProductoExtraido)
                    listaLineasPedidos.add(lineaPedidoCreado)
                }
            }
        } catch (e: SQLException) {
            logger.log(Level.WARNING,"Error de BBDD")
        }
        return listaLineasPedidos
    }

    fun eliminar(idPedido: Int) {
        try {
            val queryBorrar = c.prepareStatement("DELETE FROM LINEAPEDIDO WHERE ID=?")
            queryBorrar.use {
                it.setInt(1, idPedido)
                it.executeUpdate() // REVISAR
            }
        } catch (e: SQLException) {
            logger.log(Level.WARNING,"Error de BBDD")
        }
    }

}

class PedidoDAO(private val c: Connection)
{
    fun insertar(pedido: Pedido) {
        try {
            val queryInsertar = c.prepareStatement("INSERT INTO PEDIDO (PRECIOTOTAL, IDUSUARIO) VALUES(?,?);")
            queryInsertar.use {
                it.setDouble(1, pedido.precioTotal)
                it.setInt(2, pedido.idUsuario)
                it.executeUpdate()
            }
        } catch (e: SQLException) {println("Error de BBDD")}
    }

    fun mostrar(idUsuario: Int):MutableList<Pedido> {
        val listaPedidos = mutableListOf<Pedido>()
        try {
            val queryObtener = c.prepareStatement("SELECT * FROM PEDIDO where ID=?;")
            queryObtener.use {
                it.setInt(1, idUsuario)
                val resultado = it.executeQuery()
                while (resultado?.next() == true) {
                    val idExtraido = resultado.getInt("id")
                    val preciototalExtraido = resultado.getDouble("preciototal")
                    val idUsuarioExtraido = resultado.getInt("idusuario")
                    val lineaPedidoCreado = Pedido(idExtraido, preciototalExtraido, idUsuarioExtraido)
                    listaPedidos.add(lineaPedidoCreado)
                }
            }
        } catch (e: SQLException) {
            println("Error de BBDD")
        }
        return listaPedidos
    }

    fun eliminar(pedido: Pedido) {
        try {
            val queryBorrar = c.prepareStatement("DELETE FROM PEDIDO WHERE ID=?")
            val resultado = queryBorrar.executeUpdate()
            queryBorrar.use {
                it.setInt(1, pedido.id)
                it.executeUpdate()
            }
        } catch (e: SQLException) {
            println("Error de BBDD")
        }
    }
}

class PedidoRepository(private val c: Connection):IPedidoRepositoy
{
    private val pedidoDAO = PedidoDAO(c)
    private val lineaPedidoDAO = LineaDAO(c)

    override fun insertar(pedido: Pedido, lineas: MutableList<LineaPedido>) {
        pedidoDAO.insertar(pedido)
        for (lineaPedido in lineas) {
            lineaPedidoDAO.insertar(lineaPedido)
        }
    }

    override fun mostrar(idUsuario: Int):MutableList<Map<Pedido, MutableList<LineaPedido>>> {
        val listaResultado : MutableList<Map<Pedido, MutableList<LineaPedido>>> = mutableListOf()
        val pedidos = pedidoDAO.mostrar(idUsuario)
        for (pedidoConcreto in pedidos){
            val lineasPedido = lineaPedidoDAO.mostrar(pedidoConcreto.id)
            val resultado : Map<Pedido, MutableList<LineaPedido>> = mapOf(Pair(pedidoConcreto, lineasPedido))
            listaResultado.add(resultado)
        }
        return listaResultado
    }

    override fun eliminar(pedido: Pedido) {
        pedidoDAO.eliminar(pedido)
    }

}