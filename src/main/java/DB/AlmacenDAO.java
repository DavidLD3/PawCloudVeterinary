package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Almacen;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AlmacenDAO {
	private Conexion conexion;

    public AlmacenDAO() {
        this.conexion = new Conexion(); // Inicializar la clase Conexion
    }
    // Método para insertar un nuevo producto en la base de datos
    public void insertarAlmacen(Almacen almacen) throws SQLException {
        String sql = "INSERT INTO almacen (nombre_producto, descripcion, categoria, cantidad_stock, precio_bruto, proveedor, fecha_ultima_compra, numero_lote, fecha_caducidad, codigo_barras, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Campos obligatorios
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setString(3, almacen.getCategoria().name());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setBigDecimal(5, almacen.getPrecioBruto());
            statement.setString(6, almacen.getProveedor());

            // La fecha de última compra es obligatoria
            statement.setDate(7, Date.valueOf(almacen.getFechaUltimaCompra()));

            // Campos opcionales
            statement.setString(8, almacen.getNumeroLote());

            if (almacen.getFechaCaducidad() != null) {
                statement.setDate(9, Date.valueOf(almacen.getFechaCaducidad()));
            } else {
                statement.setNull(9, java.sql.Types.DATE);
            }

            statement.setString(10, almacen.getCodigoBarras());
            statement.setString(11, almacen.getObservaciones());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating almacen failed, no rows affected.");
            }

            // Capturar el ID generado para el nuevo registro
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
        String sql = "UPDATE almacen SET nombre_producto = ?, descripcion = ?, categoria = ?, cantidad_stock = ?, precio_bruto = ?, proveedor = ?, fecha_ultima_compra = ?, numero_lote = ?, fecha_caducidad = ?, codigo_barras = ?, observaciones = ? WHERE id_almacen = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, almacen.getNombreProducto());
            statement.setString(2, almacen.getDescripcion());
            statement.setString(3, almacen.getCategoria().name());
            statement.setInt(4, almacen.getCantidadStock());
            statement.setBigDecimal(5, almacen.getPrecioBruto());
            statement.setString(6, almacen.getProveedor());
            statement.setDate(7, Date.valueOf(almacen.getFechaUltimaCompra()));
            statement.setString(8, almacen.getNumeroLote());
            statement.setDate(9, Date.valueOf(almacen.getFechaCaducidad()));
            statement.setString(10, almacen.getCodigoBarras());
            statement.setString(11, almacen.getObservaciones());
            statement.setInt(12, almacen.getIdAlmacen());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating almacen failed, no rows affected.");
            }
        }
    }

    // Método para eliminar un producto de la base de datos
    public void eliminarAlmacen(int idAlmacen) throws SQLException {
        String sql = "DELETE FROM almacen WHERE id_almacen = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
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
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idAlmacen);
            ResultSet resultados = statement.executeQuery();
            if (resultados.next()) {
                almacen = new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    resultados.getDate("fecha_ultima_compra").toLocalDate(),
                    resultados.getString("numero_lote"),
                    resultados.getDate("fecha_caducidad").toLocalDate(),
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                );
            }
        }
        return almacen;
    }
    public List<Almacen> obtenerProductosFiltradosYOrdenados() throws SQLException {
        List<Almacen> productos = new ArrayList<>();
        String sql = "SELECT * FROM almacen WHERE categoria IN ('Normal', 'Cobertura', 'Alimento', 'Medicamento', 'Suplemento', 'Producto_Higienico', 'Accesorio', 'Alimento_Especializado', 'Equipamiento_Medico', 'Articulo_Educativo') ORDER BY nombre_producto, fecha_caducidad, cantidad_stock";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultados = statement.executeQuery();
            while (resultados.next()) {
                java.sql.Date fechaUltimaCompraSql = resultados.getDate("fecha_ultima_compra");
                LocalDate fechaUltimaCompra = (fechaUltimaCompraSql != null) ? fechaUltimaCompraSql.toLocalDate() : null;

                java.sql.Date fechaCaducidadSql = resultados.getDate("fecha_caducidad");
                LocalDate fechaCaducidad = (fechaCaducidadSql != null) ? fechaCaducidadSql.toLocalDate() : null;

                productos.add(new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    fechaUltimaCompra,
                    resultados.getString("numero_lote"),
                    fechaCaducidad,
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                ));
            }
        }
        return productos;
    }
    
    public List<Almacen> buscarProductosPorNombre(String nombre) throws SQLException {
        List<Almacen> productos = new ArrayList<>();
        String sql = "SELECT * FROM almacen WHERE nombre_producto LIKE ? AND categoria IN ('Normal', 'Cobertura', 'Alimento', 'Medicamento', 'Suplemento', 'Producto_Higienico', 'Accesorio', 'Alimento_Especializado', 'Equipamiento_Medico', 'Articulo_Educativo')";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            ResultSet resultados = statement.executeQuery();
            while (resultados.next()) {
                // Verifica si las fechas son nulas antes de llamar a toLocalDate()
                java.sql.Date fechaUltimaCompraSql = resultados.getDate("fecha_ultima_compra");
                LocalDate fechaUltimaCompra = (fechaUltimaCompraSql != null) ? fechaUltimaCompraSql.toLocalDate() : null;

                java.sql.Date fechaCaducidadSql = resultados.getDate("fecha_caducidad");
                LocalDate fechaCaducidad = (fechaCaducidadSql != null) ? fechaCaducidadSql.toLocalDate() : null;

                // Crea el objeto Almacen con los valores obtenidos
                productos.add(new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    fechaUltimaCompra,
                    resultados.getString("numero_lote"),
                    fechaCaducidad,
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                ));
            }
        }
        return productos;
    }
    public List<Almacen> obtenerServiciosFiltradosYOrdenados() throws SQLException {
        List<Almacen> servicios = new ArrayList<>();
        String sql = "SELECT * FROM almacen WHERE categoria IN ('Servicio', 'Cargo', 'Estancia_Hospitalizacion', 'Estancia_Residencia', 'Peticion_Analitica', 'Intervencion', 'Vacuna', 'Prueba_Diagnostica', 'Servicio_Estetico', 'Plan_Salud') ORDER BY nombre_producto, fecha_caducidad, cantidad_stock";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultados = statement.executeQuery();
            while (resultados.next()) {
                java.sql.Date fechaUltimaCompraSql = resultados.getDate("fecha_ultima_compra");
                LocalDate fechaUltimaCompra = fechaUltimaCompraSql != null ? fechaUltimaCompraSql.toLocalDate() : null;

                java.sql.Date fechaCaducidadSql = resultados.getDate("fecha_caducidad");
                LocalDate fechaCaducidad = fechaCaducidadSql != null ? fechaCaducidadSql.toLocalDate() : null;

                servicios.add(new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    fechaUltimaCompra,
                    resultados.getString("numero_lote"),
                    fechaCaducidad,
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                ));
            }
        }
        return servicios;
    }

    public List<Almacen> buscarServiciosPorNombre(String nombre) throws SQLException {
        List<Almacen> servicios = new ArrayList<>();
        String sql = "SELECT * FROM almacen WHERE nombre_producto LIKE ? AND categoria IN ('Servicio', 'Cargo', 'Estancia_Hospitalizacion', 'Estancia_Residencia', 'Peticion_Analitica', 'Intervencion', 'Vacuna', 'Prueba_Diagnostica', 'Servicio_Estetico', 'Plan_Salud')";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            ResultSet resultados = statement.executeQuery();
            while (resultados.next()) {
                java.sql.Date fechaUltimaCompraSql = resultados.getDate("fecha_ultima_compra");
                LocalDate fechaUltimaCompra = fechaUltimaCompraSql != null ? fechaUltimaCompraSql.toLocalDate() : null;

                java.sql.Date fechaCaducidadSql = resultados.getDate("fecha_caducidad");
                LocalDate fechaCaducidad = fechaCaducidadSql != null ? fechaCaducidadSql.toLocalDate() : null;

                servicios.add(new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    fechaUltimaCompra,
                    resultados.getString("numero_lote"),
                    fechaCaducidad,
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                ));
            }
        }
        return servicios;
    }

    public Almacen obtenerProductoPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM almacen WHERE nombre_producto = ?";
        Almacen producto = null;
        try (Connection conn = Conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nombre);
            ResultSet resultados = statement.executeQuery();
            if (resultados.next()) {
                // Obtenemos las fechas asegurándonos de que no sean nulas antes de convertirlas
                Date fechaUltimaCompraSql = resultados.getDate("fecha_ultima_compra");
                LocalDate fechaUltimaCompra = (fechaUltimaCompraSql != null) ? fechaUltimaCompraSql.toLocalDate() : null;

                Date fechaCaducidadSql = resultados.getDate("fecha_caducidad");
                LocalDate fechaCaducidad = (fechaCaducidadSql != null) ? fechaCaducidadSql.toLocalDate() : null;

                producto = new Almacen(
                    resultados.getInt("id_almacen"),
                    resultados.getString("nombre_producto"),
                    resultados.getString("descripcion"),
                    Almacen.Categoria.valueOf(resultados.getString("categoria")),
                    resultados.getInt("cantidad_stock"),
                    resultados.getBigDecimal("precio_bruto"),
                    resultados.getString("proveedor"),
                    fechaUltimaCompra,
                    resultados.getString("numero_lote"),
                    fechaCaducidad,
                    resultados.getString("codigo_barras"),
                    resultados.getString("observaciones")
                );
            }
        }
        return producto;
    }

}
