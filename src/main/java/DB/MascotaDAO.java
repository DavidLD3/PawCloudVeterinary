package DB;

import model.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    private Conexion conexion = new Conexion();

    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT id, nombre, especie, raza, pasaporte, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE nombre LIKE ?";
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
        String sql = "SELECT id, nombre, especie, raza, pasaporte, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE id_cliente = ?";
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

    public List<Mascota> obtenerMascotasPorClienteId(int idCliente) {
        return buscarMascotasPorCliente(idCliente);
    }

    private Mascota crearMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        String sexoStr = rs.getString("sexo");
        Mascota.Sexo sexo = null;
        if (sexoStr != null && !sexoStr.isEmpty()) { // Comprobamos que la cadena no sea nula y no esté vacía
            try {
                sexo = Mascota.Sexo.valueOf(sexoStr.trim().toUpperCase()); // Usamos trim y toUpperCase para asegurar compatibilidad
            } catch (IllegalArgumentException e) {
                // Si el valor no corresponde a ningún enum, manejar aquí el error, p.ej., loguearlo o usar un valor por defecto
                System.err.println("Valor no válido para el campo 'sexo': " + sexoStr);
            }
        }
        // Continúa con el resto de los campos
        return new Mascota(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("especie"),
            rs.getString("raza"),
            rs.getString("pasaporte"),
            rs.getInt("id_cliente"),
            rs.getString("microchip"),
            rs.getDate("fecha_nacimiento").toLocalDate(),
            rs.getString("caracter"),
            rs.getString("color"),
            rs.getString("tipo_pelo"),
            sexo, // Usa la variable local sexo que podría ser null
            rs.getBoolean("esterilizado")
        );
    }

    public boolean insertarMascota(Mascota mascota) {
        String sql = "INSERT INTO mascotas (nombre, especie, raza, pasaporte, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setString(4, mascota.getPasaporte());  // Cambiado de setInt a setString
            stmt.setInt(5, mascota.getIdCliente());
            stmt.setString(6, mascota.getMicrochip());
            stmt.setObject(7, mascota.getFechaNacimiento());
            stmt.setString(8, mascota.getCaracter());
            stmt.setString(9, mascota.getColor());
            stmt.setString(10, mascota.getTipoPelo());
            stmt.setString(11, mascota.getSexo().name());
            stmt.setBoolean(12, mascota.isEsterilizado());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.err.println("Error: El ID del cliente proporcionado no existe.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean actualizarMascota(Mascota mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, especie = ?, raza = ?, pasaporte = ?, id_cliente = ?, microchip = ?, fecha_nacimiento = ?, caracter = ?, color = ?, tipo_pelo = ?, sexo = ?, esterilizado = ? WHERE id = ?";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setString(4, mascota.getPasaporte());  // Cambiado de setInt a setString
            stmt.setInt(5, mascota.getIdCliente());
            stmt.setString(6, mascota.getMicrochip());
            stmt.setObject(7, mascota.getFechaNacimiento());
            stmt.setString(8, mascota.getCaracter());
            stmt.setString(9, mascota.getColor());
            stmt.setString(10, mascota.getTipoPelo());
            stmt.setString(11, mascota.getSexo().name());
            stmt.setBoolean(12, mascota.isEsterilizado());
            stmt.setInt(13, mascota.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarMascota(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Mascota obtenerMascotaPorId(int idMascota) {
        Mascota mascota = null;
        String sql = "SELECT id, nombre, especie, raza, pasaporte, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE id = ?";
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
    
    public List<Mascota> obtenerMascotasOrdenadasPorNombreEspecieMicrochip() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT nombre, especie, microchip FROM mascotas ORDER BY nombre, especie, microchip";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setMicrochip(rs.getString("microchip"));
                mascotas.add(mascota);
            }
        }
        return mascotas;
    }
    
    public Mascota obtenerMascotaPorMicrochip(String microchip) {
        Mascota mascota = null;
        String sql = "SELECT id, nombre, especie, raza, pasaporte, id_cliente, microchip, fecha_nacimiento, caracter, color, tipo_pelo, sexo, esterilizado FROM mascotas WHERE microchip = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, microchip);
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
