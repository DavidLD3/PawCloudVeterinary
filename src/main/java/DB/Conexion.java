package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://pawcloud-database.cba04gsqsxz3.eu-west-3.rds.amazonaws.com:3306/pawcloud?useSSL=false&serverTimezone=UTC";
            String usuario = "adminpawcloud";
            String contrasenia = "Paw0349#8D9J-";
            return DriverManager.getConnection(url, usuario, contrasenia);
        } catch (ClassNotFoundException e) {
            System.err.println("No se encuentra el controlador");
            e.printStackTrace();
            throw new SQLException("Driver de conexión no encontrado", e);
        } catch (SQLException e) {
            System.err.println("Error en la conexión");
            e.printStackTrace();
            throw e;
        }
    }
}
