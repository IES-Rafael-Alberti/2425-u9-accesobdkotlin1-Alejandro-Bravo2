package org.example
import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException
import com.zaxxer.hikari.HikariDataSource


fun main() {
    try {
        Class.forName("org.h2.Driver")
        val conexion = DriverManager.getConnection("jdbc:h2:./DataBase/mydb.mv.db")
        println("Conexión exitosa")
        conexion.close()
    } catch (e: SQLException) {
        println("No se encontró el driver jdbc: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver jdbc: ${e.message}")
    }
}