package DB;

import model.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    private Conexion conexion = new Conexion();

    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT id, nombre, especie, raza, edad, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE nombre LIKE ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mascotas.add(crearMascotaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public List<Mascota> buscarMascotasPorCliente(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT id, nombre, especie, raza, edad, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE id_cliente = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mascotas.add(crearMascotaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public List<Mascota> obtenerMascotasPorClienteId(int clienteId) {
        return buscarMascotasPorCliente(clienteId); // Reutiliza el método existente para evitar duplicación de código
    }

    private Mascota crearMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        // Asumiendo que Mascota ahora usa LocalDate para fechaNacimiento
        return new Mascota(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("especie"),
            rs.getString("raza"),
            rs.getInt("edad"),
            rs.getInt("id_cliente"),
            rs.getString("microchip"),
            rs.getDate("fecha_nacimiento").toLocalDate(), // Convertir a LocalDate
            rs.getString("caracter"),
            rs.getString("color"),
            rs.getString("tipo_pelo"),
            rs.getString("sexo"),
            rs.getBoolean("esterilizado")
        );
    }
    
    public Mascota obtenerMascotaPorId(int idMascota) {
        Mascota mascota = null;
        String sql = "SELECT id, nombre, especie, raza, edad, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMascota);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mascota = crearMascotaDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascota;
    }

}
