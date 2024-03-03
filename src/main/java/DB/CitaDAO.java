package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cita;

public class CitaDAO {

    public void insertarCita(Cita cita) {
        String sql = "INSERT INTO citas (titulo, fecha, hora, notas, id_cliente,id_mascota) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cita.getTitulo());
            pstmt.setDate(2, Date.valueOf(cita.getFecha()));
            pstmt.setTime(3, Time.valueOf(cita.getHora()));
            pstmt.setString(4, cita.getNotas());
            pstmt.setInt(5, cita.getClienteId());
            pstmt.setInt(6, cita.getMascotaId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Cita> recuperarCitas() {
        List<Cita> citas = new ArrayList<>();
        // Ajuste en la consulta SQL para incluir JOIN con las tablas clientes y mascotas
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.notas, c.id_cliente, c.id_mascota, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON cl.id = c.id_cliente " + // Asegúrate de que el nombre del campo id en clientes sea correcto
                     "JOIN mascotas m ON m.id = c.id_mascota " + // Asegúrate de que el nombre del campo id en mascotas sea correcto
                     "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"), // Recuperamos el nombre del cliente
                    rs.getString("nombre_mascota") // Recuperamos el nombre de la mascota
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }



    
    public List<Cita> recuperarCitasHome() {
        List<Cita> citas = new ArrayList<>();
        // Modifica la consulta SQL para incluir JOIN con las tablas clientes y mascotas
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.notas, c.id_cliente, c.id_mascota, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                		
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"),
                    rs.getString("nombre_mascota")
                );
                cita.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
                cita.setHora(rs.getTime("hora") != null ? rs.getTime("hora").toLocalTime() : null);
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
    
    
    public List<Cita> recuperarCitasFuturas() {
        List<Cita> citas = new ArrayList<>();
        // Asume que tienes una columna 'fecha' y una columna 'hora' en tu tabla 'citas'
        String sql = "SELECT c.*, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "WHERE (c.fecha > CURRENT_DATE) OR (c.fecha = CURRENT_DATE AND c.hora >= CURRENT_TIME) " +
                     "ORDER BY c.fecha ASC, c.hora ASC";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"),
                    rs.getString("nombre_mascota")
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }






    
    
}

