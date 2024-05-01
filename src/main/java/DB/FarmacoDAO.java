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
import model.UsoFarmaco;

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
    
    public boolean insertarFarmaco(Farmaco farmaco) {
        String sql = "INSERT INTO farmacos (codigo, nombre, descripcion, cantidad, dosis_recomendada, unidad_medida, fecha_caducidad, precio) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, farmaco.getCodigo());
            statement.setString(2, farmaco.getNombre());
            statement.setString(3, farmaco.getDescripcion());
            statement.setInt(4, farmaco.getCantidad());
            statement.setString(5, farmaco.getDosisRecomendada());
            statement.setString(6, farmaco.getUnidadMedida());
            statement.setDate(7, farmaco.getFechaCaducidad());
            statement.setBigDecimal(8, farmaco.getPrecio());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Método para eliminar un fármaco por su ID
    public boolean eliminarFarmaco(int id) throws SQLException {
        String sql = "DELETE FROM farmacos WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Método para actualizar los detalles de un fármaco existente
    public boolean actualizarFarmaco(Farmaco farmaco) throws SQLException {
        String sql = "UPDATE farmacos SET codigo = ?, nombre = ?, descripcion = ?, cantidad = ?, dosis_recomendada = ?, unidad_medida = ?, fecha_caducidad = ?, precio = ? WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, farmaco.getCodigo());
            statement.setString(2, farmaco.getNombre());
            statement.setString(3, farmaco.getDescripcion());
            statement.setInt(4, farmaco.getCantidad());
            statement.setString(5, farmaco.getDosisRecomendada());
            statement.setString(6, farmaco.getUnidadMedida());
            statement.setDate(7, farmaco.getFechaCaducidad());
            statement.setBigDecimal(8, farmaco.getPrecio());
            statement.setInt(9, farmaco.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    public Farmaco buscarFarmacoPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM farmacos WHERE nombre = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombre); // Setear el parámetro de la consulta SQL
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Si se encuentra un registro
                return new Farmaco(
                    resultSet.getInt("id"),
                    resultSet.getString("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getString("descripcion"),
                    resultSet.getInt("cantidad"),
                    resultSet.getString("dosis_recomendada"),
                    resultSet.getString("unidad_medida"),
                    resultSet.getDate("fecha_caducidad"),
                    resultSet.getBigDecimal("precio")
                );
            }
            return null; // Retorna null si no hay coincidencia
        }
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
    
    public boolean registrarUsoFarmaco(UsoFarmaco uso) {
        String sql = "INSERT INTO uso_farmacos (id_farmaco, id_hospitalizacion, cantidad_usada, fecha_hora_uso, frecuencia) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, uso.getIdFarmaco());
            pstmt.setInt(2, uso.getIdHospitalizacion());
            pstmt.setInt(3, uso.getCantidadUsada());
            pstmt.setTimestamp(4, Timestamp.valueOf(uso.getFechaHoraUso()));
            pstmt.setString(5, uso.getFrecuencia());  // Guardar la frecuencia
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    
    public List<UsoFarmaco> obtenerUltimosUsosFarmacos() {
        List<UsoFarmaco> usos = new ArrayList<>();
        // Asegúrate de incluir la frecuencia en la selección
        String sql = "SELECT uf.id_farmaco, f.nombre AS nombre_farmaco, uf.cantidad_usada, uf.fecha_hora_uso, " +
                     "m.nombre AS nombre_mascota, uf.frecuencia " +
                     "FROM uso_farmacos uf " +
                     "JOIN farmacos f ON uf.id_farmaco = f.id " +
                     "JOIN hospitalizaciones h ON uf.id_hospitalizacion = h.id " +
                     "JOIN mascotas m ON h.id_mascota = m.id " +
                     "ORDER BY uf.fecha_hora_uso DESC LIMIT 5";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UsoFarmaco uso = new UsoFarmaco();
                uso.setIdFarmaco(rs.getInt("id_farmaco"));
                uso.setNombreFarmaco(rs.getString("nombre_farmaco"));
                uso.setCantidadUsada(rs.getInt("cantidad_usada"));
                uso.setFechaHoraUso(rs.getTimestamp("fecha_hora_uso").toLocalDateTime());
                uso.setNombreMascota(rs.getString("nombre_mascota"));
                uso.setFrecuencia(rs.getString("frecuencia"));  // Agregar la frecuencia recuperada de la base de datos
                usos.add(uso);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los últimos usos de fármacos: " + e.getMessage());
            e.printStackTrace();
        }
        return usos;
    }


    public List<Farmaco> obtenerFarmacosOrdenados() throws SQLException {
        List<Farmaco> farmacos = new ArrayList<>();
        String sql = "SELECT * FROM farmacos ORDER BY nombre, fecha_caducidad, cantidad DESC";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        }
        return farmacos;
    }


    
    

    
    
    
}
