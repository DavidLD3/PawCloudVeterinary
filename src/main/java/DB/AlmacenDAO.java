package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Almacen;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AlmacenDAO {
    private Connection connection;

    public AlmacenDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo producto en la base de datos
    public void insertarAlmacen(Almacen almacen) throws SQLException {
        String sql = "INSERT INTO almacen (nombre_producto, descripcion, categoria, cantidad_stock, precio_compra_sin_iva, precio_compra_con_iva, precio_venta_sin_iva, precio_venta_con_iva, proveedor, fecha_ultima_compra, numero_lote, fecha_caducidad, codigo_barras, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setString(3, almacen.getCategoria().name());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setBigDecimal(5, almacen.getPrecioCompraSinIVA());
            statement.setBigDecimal(6, almacen.getPrecioCompraConIVA());
            statement.setBigDecimal(7, almacen.getPrecioVentaSinIVA());
            statement.setBigDecimal(8, almacen.getPrecioVentaConIVA());
            statement.setString(9, almacen.getProveedor());
            statement.setDate(10, Date.valueOf(almacen.getFechaUltimaCompra()));
            statement.setString(11, almacen.getNumeroLote());
            statement.setDate(12, Date.valueOf(almacen.getFechaCaducidad()));
            statement.setString(13, almacen.getCodigoBarras());
            statement.setString(14, almacen.getObservaciones());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating almacen failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    almacen.setIdAlmacen(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating almacen failed, no ID obtained.");
                }
            }
        }
    }

    // Método para actualizar un producto existente en la base de datos
    public void actualizarAlmacen(Almacen almacen) throws SQLException {
        String sql = "UPDATE almacen SET nombre_producto = ?, descripcion = ?, categoria = ?, cantidad_stock = ?, precio_compra_sin_iva = ?, precio_compra_con_iva = ?, precio_venta_sin_iva = ?, precio_venta_con_iva = ?, proveedor = ?, fecha_ultima_compra = ?, numero_lote = ?, fecha_caducidad = ?, codigo_barras = ?, observaciones = ? WHERE id_almacen = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setString(3, almacen.getCategoria().name());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setBigDecimal(5, almacen.getPrecioCompraSinIVA());
            statement.setBigDecimal(6, almacen.getPrecioCompraConIVA());
            statement.setBigDecimal(7, almacen.getPrecioVentaSinIVA());
            statement.setBigDecimal(8, almacen.getPrecioVentaConIVA());
            statement.setString(9, almacen.getProveedor());
            statement.setDate(10, Date.valueOf(almacen.getFechaUltimaCompra()));
            statement.setString(11, almacen.getNumeroLote());
            statement.setDate(12, Date.valueOf(almacen.getFechaCaducidad()));
            statement.setString(13, almacen.getCodigoBarras());
            statement.setString(14, almacen.getObservaciones());
            statement.setInt(15, almacen.getIdAlmacen());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating almacen failed, no rows affected.");
            }
        }
    }

    // Método para eliminar un producto de la base de datos
    public void eliminarAlmacen(int idAlmacen) throws SQLException {
        String sql = "DELETE FROM almacen WHERE id_almacen = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAlmacen);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting almacen failed, no rows affected.");
            }
        }
    }

    // Método para buscar un producto por su ID
    public Almacen obtenerAlmacenPorId(int idAlmacen) throws SQLException {
        String sql = "SELECT * FROM almacen WHERE id_almacen = ?";
        Almacen almacen = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAlmacen);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                almacen = new Almacen();
                almacen.setIdAlmacen(resultSet.getInt("id_almacen"));
                almacen.setNombreProducto(resultSet.getString("nombre_producto"));
                almacen.setDescripcion(resultSet.getString("descripcion"));
                almacen.setCategoria(Almacen.Categoria.valueOf(resultSet.getString("categoria")));
                almacen.setCantidadStock(resultSet.getInt("cantidad_stock"));
                almacen.setPrecioCompraSinIVA(resultSet.getBigDecimal("precio_compra_sin_iva"));
                almacen.setPrecioCompraConIVA(resultSet.getBigDecimal("precio_compra_con_iva"));
                almacen.setPrecioVentaSinIVA(resultSet.getBigDecimal("precio_venta_sin_iva"));
                almacen.setPrecioVentaConIVA(resultSet.getBigDecimal("precio_venta_con_iva"));
                almacen.setProveedor(resultSet.getString("proveedor"));
                almacen.setFechaUltimaCompra(resultSet.getDate("fecha_ultima_compra").toLocalDate());
                almacen.setNumeroLote(resultSet.getString("numero_lote"));
                almacen.setFechaCaducidad(resultSet.getDate("fecha_caducidad").toLocalDate());
                almacen.setCodigoBarras(resultSet.getString("codigo_barras"));
                almacen.setObservaciones(resultSet.getString("observaciones"));
            }
        }
        return almacen;
    }
}