package Actividad6


import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

fun main(){
    val dataSource = HikariDataSource()
    dataSource.jdbcUrl = "jdbc:h2:./DataBase/mydb"
    dataSource.maximumPoolSize = 10
    dataSource.minimumIdle = 1

    val baseDatos = BaseDatos(dataSource)
    val resultado = baseDatos.agregarUsuario(4,"Reinaldo Girúndez", "reingir@mail.com" )
    if (resultado){
        println("Usuario agregado exitosamente")
    } else{
        println("No se ha podido agregar el usuario")
    }

}


class BaseDatos(val dataSource: HikariDataSource) {
    fun agregarUsuario(id : Int, nombre : String, email : String) : Boolean {
        val connection = dataSource.connection
        try {
            val sql = connection.prepareStatement("INSERT INTO USUARIO (NOMBRE, EMAIL) VALUES (?, ?)")
            sql.setString(1,nombre )
            sql.setString(2, email)
            val resultado = sql.executeUpdate()
            sql.close()

        } catch (ex: SQLException) {
            return false
        }
        return true
    }
}