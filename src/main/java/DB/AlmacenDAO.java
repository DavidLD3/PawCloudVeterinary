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

    public Almacen obtenerAlmacenPorId(int id) {
        Almacen almacen = null;
        String sql = "SELECT * FROM almacen WHERE id_almacen = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                almacen = new Almacen(
                    rs.getInt("id_almacen"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad_stock"),
                    rs.getString("unidad_medida"),
                    rs.getInt("punto_reorden"),
                    rs.getDouble("precio_compra_sin_iva"),
                    rs.getDouble("precio_compra_con_iva"),
                    rs.getDouble("precio_venta_sin_iva"),
                    rs.getDouble("precio_venta_con_iva"),
                    rs.getString("proveedor"),
                    rs.getDate("fecha_ultima_compra") != null ? rs.getDate("fecha_ultima_compra").toLocalDate() : null,
                    rs.getString("numero_lote"),
                    rs.getDate("fecha_caducidad") != null ? rs.getDate("fecha_caducidad").toLocalDate() : null,
                    rs.getString("ubicacion"),
                    rs.getString("codigo_barras"),
                    rs.getString("observaciones")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return almacen;
    }

    public boolean insertarAlmacen(Almacen almacen) {
        String sql = "INSERT INTO almacen (nombre_producto, descripcion, categoria, cantidad_stock, unidad_medida, punto_reorden, precio_compra_sin_iva, precio_compra_con_iva, precio_venta_sin_iva, precio_venta_con_iva, proveedor, fecha_ultima_compra, numero_lote, fecha_caducidad, ubicacion, codigo_barras, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, almacen.getNombreProducto());
            stmt.setString(2, almacen.getDescripcion());
            stmt.setString(3, almacen.getCategoria());
            stmt.setInt(4, almacen.getCantidadStock());
            stmt.setString(5, almacen.getUnidadMedida());
            stmt.setInt(6, almacen.getPuntoReorden());
            stmt.setDouble(7, almacen.getPrecioCompraSinIVA());
            stmt.setDouble(8, almacen.getPrecioCompraConIVA());
            stmt.setDouble(9, almacen.getPrecioVentaSinIVA());
            stmt.setDouble(10, almacen.getPrecioVentaConIVA());
            stmt.setString(11, almacen.getProveedor());
            stmt.setDate(12, java.sql.Date.valueOf(almacen.getFechaUltimaCompra()));
            stmt.setString(13, almacen.getNumeroLote());
            stmt.setDate(14, java.sql.Date.valueOf(almacen.getFechaCaducidad()));
            stmt.setString(15, almacen.getUbicacion());
            stmt.setString(16, almacen.getCodigoBarras());
            stmt.setString(17, almacen.getObservaciones());
            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAlmacen(Almacen almacen) {
        String sql = "UPDATE almacen SET nombre_producto = ?, descripcion = ?, categoria = ?, cantidad_stock = ?, unidad_medida = ?, punto_reorden = ?, precio_compra_sin_iva = ?, precio_compra_con_iva = ?, precio_venta_sin_iva = ?, precio_venta_con_iva = ?, proveedor = ?, fecha_ultima_compra = ?, numero_lote = ?, fecha_caducidad = ?, ubicacion = ?, codigo_barras = ?, observaciones = ? WHERE id_almacen = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, almacen.getNombreProducto());
            stmt.setString(2, almacen.getDescripcion());
            stmt.setString(3, almacen.getCategoria());
            stmt.setInt(4, almacen.getCantidadStock());
            stmt.setString(5, almacen.getUnidadMedida());
            stmt.setInt(6, almacen.getPuntoReorden());
            stmt.setDouble(7, almacen.getPrecioCompraSinIVA());
            stmt.setDouble(8, almacen.getPrecioCompraConIVA());
            stmt.setDouble(9, almacen.getPrecioVentaSinIVA());
            stmt.setDouble(10, almacen.getPrecioVentaConIVA());
            stmt.setString(11, almacen.getProveedor());
            stmt.setDate(12, java.sql.Date.valueOf(almacen.getFechaUltimaCompra()));
            stmt.setString(13, almacen.getNumeroLote());
            stmt.setDate(14, java.sql.Date.valueOf(almacen.getFechaCaducidad()));
            stmt.setString(15, almacen.getUbicacion());
            stmt.setString(16, almacen.getCodigoBarras());
            stmt.setString(17, almacen.getObservaciones());
            stmt.setInt(18, almacen.getIdAlmacen());
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos para eliminar, listar y buscar productos pueden seguir una estructura similar
}