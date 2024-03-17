package DB;

import model.Hospitalizacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

public class HospitalizacionDAO {
    
    private Conexion conexion;

    public HospitalizacionDAO() {
        this.conexion = new Conexion(); // Asumiendo que tienes una clase que maneja la conexión
    }

    public boolean insertarHospitalizacion(Hospitalizacion hospitalizacion, int idVeterinario) {
        String sql = "INSERT INTO hospitalizaciones (id_mascota, fecha_ingreso, fecha_salida, motivo, tratamiento, estado, notas, id_veterinario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hospitalizacion.getIdMascota());
            pstmt.setTimestamp(2, Timestamp.valueOf(hospitalizacion.getFechaIngreso()));
            if (hospitalizacion.getFechaSalida() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(hospitalizacion.getFechaSalida()));
            } else {
                pstmt.setTimestamp(3, null);
            }
            pstmt.setString(4, hospitalizacion.getMotivo());
            pstmt.setString(5, hospitalizacion.getTratamiento());
            pstmt.setString(6, hospitalizacion.getEstado());
            pstmt.setString(7, hospitalizacion.getNotas());
            pstmt.setInt(8, idVeterinario);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean actualizarFechaSalidaHospitalizacion(int idMascota, Date fechaSalida) {
        String sql = "UPDATE hospitalizaciones SET fecha_salida = ? WHERE id_mascota = ? AND fecha_salida IS NULL";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, new java.sql.Date(fechaSalida.getTime()));
            pstmt.setInt(2, idMascota);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Hospitalizacion> recuperarHospitalizacionesSinFechaSalida() {
        List<Hospitalizacion> hospitalizaciones = new ArrayList<>();
        String sql = "SELECT * FROM hospitalizaciones WHERE fecha_salida IS NULL";

        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Hospitalizacion hospitalizacion = new Hospitalizacion(
                    rs.getInt("id"),
                    rs.getInt("id_mascota"),
                    rs.getTimestamp("fecha_ingreso").toLocalDateTime(), // Convertido a LocalDateTime
                    rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null, // Manejo de null
                    rs.getString("motivo"),
                    rs.getString("tratamiento"),
                    rs.getString("estado"),
                    rs.getString("notas")
                );
                hospitalizaciones.add(hospitalizacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitalizaciones;
    }
    
    public List<Hospitalizacion> recuperarTodasLasHospitalizaciones() {
        List<Hospitalizacion> hospitalizaciones = new ArrayList<>();
        String sql = "SELECT c.id, c.fecha_ingreso, c.fecha_salida, c.motivo, c.tratamiento, c.estado, c.notas, c.id_mascota, m.nombre AS nombre_mascota " +
                     "FROM hospitalizaciones c " +
                     "JOIN mascotas m ON m.id = c.id_mascota " + // Asegúrate de que el nombre del campo id en mascotas sea correcto
                     "ORDER BY c.fecha_ingreso DESC, c.fecha_salida DESC"; // Asegúrate de que las columnas fecha_ingreso y fecha_salida existan y sean las que quieres ordenar.

        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Hospitalizacion hospitalizacion = new Hospitalizacion(
                    rs.getInt("id"),
                    rs.getInt("id_mascota"),
                    rs.getTimestamp("fecha_ingreso").toLocalDateTime(),
                    rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null,
                    rs.getString("motivo"),
                    rs.getString("tratamiento"),
                    rs.getString("estado"),
                    rs.getString("notas"),
                    rs.getString("nombre_mascota") // Asumiendo que has añadido este campo a tu clase Hospitalizacion
                );
                hospitalizaciones.add(hospitalizacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitalizaciones;
    }
    
    public int obtenerIdHospitalizacionActual(int idMascota) {
        String sql = "SELECT id FROM hospitalizaciones WHERE id_mascota = ? AND fecha_salida IS NULL ORDER BY fecha_ingreso DESC LIMIT 1";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMascota);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indica que no se encontró una hospitalización actual
    }





}
