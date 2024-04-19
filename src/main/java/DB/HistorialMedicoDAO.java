package DB;

import model.HistorialMedico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO {

    private Conexion conexion;

    public HistorialMedicoDAO() {
        conexion = new Conexion();
    }

    public HistorialMedico obtenerHistorialPorId(int id) {
        HistorialMedico historial = null;
        String consulta = "SELECT * FROM historial_medico WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement statement = conn.prepareStatement(consulta)) {
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();
            if (resultados.next()) {
                historial = new HistorialMedico(
                        resultados.getInt("id"),
                        resultados.getInt("id_mascota"),
                        resultados.getDate("fecha").toLocalDate(),
                        resultados.getString("diagnostico"),
                        resultados.getString("tratamiento")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el historial por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return historial;
    }

    public boolean insertarHistorial(HistorialMedico historial) {
        try (Connection conn = conexion.getConexion()) {
            String consulta = "INSERT INTO historial_medico (id_mascota, fecha, diagnostico, tratamiento) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setInt(1, historial.getIdMascota());
            statement.setDate(2, java.sql.Date.valueOf(historial.getFecha()));
            statement.setString(3, historial.getDiagnostico());
            statement.setString(4, historial.getTratamiento());
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarHistorial(HistorialMedico historial) {
        try (Connection conn = conexion.getConexion()) {
            String consulta = "UPDATE historial_medico SET fecha = ?, diagnostico = ?, tratamiento = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setDate(1, java.sql.Date.valueOf(historial.getFecha()));
            statement.setString(2, historial.getDiagnostico());
            statement.setString(3, historial.getTratamiento());
            statement.setInt(4, historial.getId());
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarHistorial(int id) {
        try (Connection conn = conexion.getConexion()) {
            String consulta = "DELETE FROM historial_medico WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HistorialMedico> obtenerHistorialesPorMascota(int idMascota) {
        List<HistorialMedico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico WHERE id_mascota = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMascota);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistorialMedico historial = new HistorialMedico(
                        rs.getInt("id"),
                        rs.getInt("id_mascota"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento")
                );
                historiales.add(historial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiales;
    }
}
