package Actividad6


import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.Actividad2.BaseDatos
import java.sql.Connection
import java.sql.SQLException

fun main(){


    val dataSource = HikariDataSource()
    dataSource.jdbcUrl = "jdbc:h2:./DataBase/mydb"
    dataSource.username = ""
    dataSource.password = ""
    dataSource.maximumPoolSize = 10
    dataSource.minimumIdle = 1

    val connection = dataSource.connection


    try {
        val sql = connection.prepareStatement("INSERT INTO USUARIO (NOMBRE, EMAIL) VALUES (?, ?)")
        sql.setString(1, "Reinaldo Girúndez")
        sql.setString(2, "reingir@mail.com")
        val resultado = sql.executeQuery()

    } catch (ex: SQLException) {
        println("No se ha podido crear el usuario...")
    }

}