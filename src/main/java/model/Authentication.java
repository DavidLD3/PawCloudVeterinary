package model;

import DB.Conexion;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT PasswordHash FROM usuarios WHERE Username = ?";
        
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPasswordHash = rs.getString("PasswordHash");
                return BCrypt.checkpw(password, storedPasswordHash);
            }
            
        } catch (SQLException e) {
        	 System.err.println("Error al autenticar al usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    public int getUserCount() {
        String sql = "SELECT COUNT(*) AS total FROM usuarios";
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar los usuarios: " + e.getMessage());
        }
        return 0;  // Retornar 0 en caso de error para evitar nuevos registros
    }
}
