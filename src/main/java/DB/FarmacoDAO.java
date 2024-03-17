package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Farmaco;

public class FarmacoDAO {

    public List<Farmaco> buscarFarmacos(String nombre) {
        List<Farmaco> farmacos = new ArrayList<>();
        String sql = "SELECT * FROM farmacos WHERE nombre LIKE ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Farmaco farmaco = new Farmaco();
                farmaco.setId(resultSet.getInt("id"));
                farmaco.setCodigo(resultSet.getString("codigo"));
                farmaco.setNombre(resultSet.getString("nombre"));
                farmaco.setDescripcion(resultSet.getString("descripcion"));
                farmaco.setCantidad(resultSet.getInt("cantidad"));
                farmaco.setDosisRecomendada(resultSet.getString("dosis_recomendada"));
                farmaco.setUnidadMedida(resultSet.getString("unidad_medida"));
                farmaco.setFechaCaducidad(resultSet.getDate("fecha_caducidad"));
                farmaco.setPrecio(resultSet.getBigDecimal("precio"));
                farmacos.add(farmaco);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar fármacos: " + e.getMessage());
            e.printStackTrace();
        }
        return farmacos;
    }
    
    public boolean actualizarStockFarmaco(int idFarmaco, int cantidadUsada) {
        String sql = "UPDATE farmacos SET cantidad = cantidad - ? WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cantidadUsada);
            statement.setInt(2, idFarmaco);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registrarUsoFarmaco(int idFarmaco, int idHospitalizacion, int idVeterinario, int cantidadUsada, LocalDateTime fechaHoraUso) {
        String sql = "INSERT INTO uso_farmacos (id_farmaco, id_hospitalizacion, id_veterinario, cantidad_usada, fecha_hora_uso) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFarmaco);
            pstmt.setInt(2, idHospitalizacion);
            pstmt.setInt(3, idVeterinario);
            pstmt.setInt(4, cantidadUsada);
            pstmt.setTimestamp(5, Timestamp.valueOf(fechaHoraUso));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
