package model;

import DB.Conexion;
import UISwing.ventanas.ConfiguracionClinica;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    // Método para contar el número de usuarios registrados
	 public static int getUserCount() {
	        try (Connection conn = new Conexion().getConexion();
	             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM usuarios");
	             ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("total");
	            }
	        } catch (SQLException e) {
	            System.err.println("Error al contar los usuarios: " + e.getMessage());
	        }
	        return 0;  // Retorna 0 en caso de error para evitar nuevos registros
	    }

    public int registerUser(String username, String email, String password) {
        // Verificar si se ha alcanzado el límite antes de intentar registrar
        if (getUserCount() >= ConfiguracionClinica.getLimiteUsuarios()) {
            return -1; // Código de error para "límite alcanzado"
        }

        // Resto de la lógica de registro...
        try (Connection conn = new Conexion().getConexion()) {
            String sql = "INSERT INTO usuarios (Username, Email, PasswordHash) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    return 1; // Código para "registro exitoso"
                }
                return 0; // Código para "error en el registro"
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener la conexión o al ejecutar la consulta: " + ex.getMessage());
            return 0;
        }
    }

}
	