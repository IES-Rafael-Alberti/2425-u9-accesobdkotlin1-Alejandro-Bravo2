package org.example.Actividad7


data class Pedido(val id: Int, val precioTotal: Double, val idUsuario: Int)
data class LineaPedido(val id: Int, val cantidad: Int, val precio:Double, val idPedido: Int, val idProducto:Int)


fun main() {
    val c = Conexion()
    val repositorio = PedidoRepository(c.c)

    val pedido1 = Pedido(1, 20.09, 1)
    val pedido2 = Pedido(2, 900.09, 2)
    val pedido3 = Pedido(3, 1231.09, 3)

    val lineaPedido1 = LineaPedido(1, 5, 100.45, 1, 1)
    val lineaPedido2 = LineaPedido(2, 3, 2700.27, 1, 2)
    val lineaPedido3 = LineaPedido(3, 3, 3693.27, 1, 3)

    val listaLineasPedidos = mutableListOf(lineaPedido1, lineaPedido2, lineaPedido3)
    repositorio.insertar(pedido1, listaLineasPedidos)


    val lineaPedido4 = LineaPedido(4, 5, 100.45, 2, 1)
    val lineaPedido5 = LineaPedido(5, 3, 2700.27, 2, 2)
    val lineaPedido6 = LineaPedido(6, 3, 3693.27, 2, 3)

    val listaLineasPedidos2 = mutableListOf(lineaPedido4, lineaPedido5, lineaPedido6)

    repositorio.insertar(pedido2,listaLineasPedidos2)

    val lineaPedido7 = LineaPedido(7, 5, 100.45, 2, 1)
    val lineaPedido8 = LineaPedido(8, 3, 2700.27, 2, 2)
    val lineaPedido9 = LineaPedido(9, 3, 3693.27, 2, 3)

    val listaLineasPedidos3 = mutableListOf(lineaPedido7, lineaPedido8, lineaPedido9)

    repositorio.insertar(pedido3, listaLineasPedidos3)



    if(c.c.isValid(10)){
        c.c.use {
            val listaUsuarios = repositorio.mostrar(1)
            for(p in listaUsuarios){
                println(p.keys)
                for(l in p.values.elementAt(0))
                    println(l)
            }

            val listaUsuarios2 = repositorio.mostrar(1)
            // TENGO QUE HACER INSERTAR UNOS CUANTOS PEDIDOS Y UNAS CUANTAS LINEAS DE PEDIDO Y LUEGO MOSRTARLAS
            for(p in listaUsuarios2){
                println(p.keys)
                for(l in p.values.elementAt(0))
                    println(l)
            }

            val listaUsuarios3 = repositorio.mostrar(1)
            // TENGO QUE HACER INSERTAR UNOS CUANTOS PEDIDOS Y UNAS CUANTAS LINEAS DE PEDIDO Y LUEGO MOSRTARLAS
            for(p in listaUsuarios3){
                println(p.keys)
                for(l in p.values.elementAt(0))
                    println(l)
            }
        }
    }
    else
        println("Error de conexión")

}