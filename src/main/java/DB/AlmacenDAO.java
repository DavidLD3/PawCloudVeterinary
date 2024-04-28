package DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Almacen;

public class AlmacenDAO {
    private Conexion conexion;

    public AlmacenDAO() {
        conexion = new Conexion(); // Inicializa la clase Conexion
    }

    public boolean insertarAlmacen(Almacen almacen) {
        String sql = "INSERT INTO almacen (nombre_producto, descripcion, categoria_id, cantidad_stock, unidad_medida, punto_reorden, precio_compra_sin_iva, precio_compra_con_iva, precio_venta_sin_iva, precio_venta_con_iva, proveedor, fecha_ultima_compra, numero_lote, fecha_caducidad, ubicacion, codigo_barras, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setInt(3, almacen.getCategoriaId());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setString(5, almacen.getUnidadMedida());
            statement.setInt(6, almacen.getPuntoReorden());
            statement.setBigDecimal(7, almacen.getPrecioCompraSinIva());
            statement.setBigDecimal(8, almacen.getPrecioCompraConIva());
            statement.setBigDecimal(9, almacen.getPrecioVentaSinIva());
            statement.setBigDecimal(10, almacen.getPrecioVentaConIva());
            statement.setString(11, almacen.getProveedor());
            statement.setDate(12, java.sql.Date.valueOf(almacen.getFechaUltimaCompra()));
            statement.setString(13, almacen.getNumeroLote());
            statement.setDate(14, java.sql.Date.valueOf(almacen.getFechaCaducidad()));
            statement.setString(15, almacen.getUbicacion());
            statement.setString(16, almacen.getCodigoBarras());
            statement.setString(17, almacen.getObservaciones());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Almacen obtenerAlmacenPorId(int id) {
        Almacen almacen = null;
        String sql = "SELECT * FROM almacen WHERE id_almacen = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                almacen = new Almacen();
                almacen.setIdAlmacen(rs.getInt("id_almacen"));
                almacen.setNombreProducto(rs.getString("nombre_producto"));
                almacen.setDescripcion(rs.getString("descripcion"));
                almacen.setCategoriaId(rs.getInt("categoria_id"));
                almacen.setCantidadStock(rs.getInt("cantidad_stock"));
                almacen.setUnidadMedida(rs.getString("unidad_medida"));
                almacen.setPuntoReorden(rs.getInt("punto_reorden"));
                almacen.setPrecioCompraSinIva(rs.getBigDecimal("precio_compra_sin_iva"));
                almacen.setPrecioCompraConIva(rs.getBigDecimal("precio_compra_con_iva"));
                almacen.setPrecioVentaSinIva(rs.getBigDecimal("precio_venta_sin_iva"));
                almacen.setPrecioVentaConIva(rs.getBigDecimal("precio_venta_con_iva"));
                almacen.setProveedor(rs.getString("proveedor"));
                almacen.setFechaUltimaCompra(rs.getDate("fecha_ultima_compra").toLocalDate());
                almacen.setNumeroLote(rs.getString("numero_lote"));
                almacen.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
                almacen.setUbicacion(rs.getString("ubicacion"));
                almacen.setCodigoBarras(rs.getString("codigo_barras"));
                almacen.setObservaciones(rs.getString("observaciones"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el almacen por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return almacen;
    }

    public boolean actualizarAlmacen(Almacen almacen) {
        String sql = "UPDATE almacen SET nombre_producto = ?, descripcion = ?, categoria_id = ?, cantidad_stock = ?, unidad_medida = ?, punto_reorden = ?, precio_compra_sin_iva = ?, precio_compra_con_iva = ?, precio_venta_sin_iva = ?, precio_venta_con_iva = ?, proveedor = ?, fecha_ultima_compra = ?, numero_lote = ?, fecha_caducidad = ?, ubicacion = ?, codigo_barras = ?, observaciones = ? WHERE id_almacen = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setInt(3, almacen.getCategoriaId());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setString(5, almacen.getUnidadMedida());
            statement.setInt(6, almacen.getPuntoReorden());
            statement.setBigDecimal(7, almacen.getPrecioCompraSinIva());
            statement.setBigDecimal(8, almacen.getPrecioCompraConIva());
            statement.setBigDecimal(9, almacen.getPrecioVentaSinIva());
            statement.setBigDecimal(10, almacen.getPrecioVentaConIva());
            statement.setString(11, almacen.getProveedor());
            statement.setDate(12, java.sql.Date.valueOf(almacen.getFechaUltimaCompra()));
            statement.setString(13, almacen.getNumeroLote());
            statement.setDate(14, java.sql.Date.valueOf(almacen.getFechaCaducidad()));
            statement.setString(15, almacen.getUbicacion());
            statement.setString(16, almacen.getCodigoBarras());
            statement.setString(17, almacen.getObservaciones());
            statement.setInt(18, almacen.getIdAlmacen());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAlmacen(int id) {
        String sql = "DELETE FROM almacen WHERE id_almacen = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}