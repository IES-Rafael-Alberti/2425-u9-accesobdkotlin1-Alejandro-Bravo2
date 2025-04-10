package org.example.Actividad7

interface IPedidoRepositoy {
    fun insertar(pedido: Pedido, lineas: MutableList<LineaPedido>)
    fun mostrar(idUsuario: Int):MutableList<Map<Pedido, MutableList<LineaPedido>>>
    fun eliminar(pedido: Pedido)
}