package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class ServicioRestablecimiento {
    private Connection conexion; // Asume que ya tienes una conexión a la base de datos

    public boolean verificarToken(String token) {
        // Verificar si el token es válido y no ha expirado
        String sql = "SELECT UserID FROM reset_tokens WHERE Token = ? AND Expiry > NOW()";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Devuelve true si el token es válido
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarContraseña(String token, String nuevaContraseña) {
        // Primero, verifica el token
        if (!verificarToken(token)) {
            return false;
        }
        // Aquí deberías hashear la nueva contraseña antes de almacenarla
        String hashedPassword = hashPassword(nuevaContraseña); // Implementa esta función según tu lógica de hashing

        // Actualizar la contraseña del usuario
        String sql = "UPDATE usuarios SET PasswordHash = ? WHERE UserID = (SELECT UserID FROM reset_tokens WHERE Token = ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, token);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Devuelve true si la contraseña se actualizó correctamente
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Implementa esta función según tu lógica de hashing
    private String hashPassword(String password) {
        // Placeholder para tu lógica de hashing real
        return password; // Esto es solo un ejemplo, no debes devolver la contraseña directamente
    }
}

