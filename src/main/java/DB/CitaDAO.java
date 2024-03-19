package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
        String sql = "SELECT * FROM citas";
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota")
                );
                citas.add(cita); // Añadir la cita a la lista
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




    
    
}

