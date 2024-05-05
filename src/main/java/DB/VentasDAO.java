package DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Almacen;
import model.Farmaco;

public class VentasDAO {
    private Conexion conexion;

    public VentasDAO() {
        this.conexion = new Conexion();
    }

    // Método para buscar productos y fármacos por nombre
    public List<Object> buscarProductosYFarmacos(String nombre) throws SQLException {
        List<Object> resultados = new ArrayList<>();
        resultados.addAll(buscarProductosPorNombre(nombre));
        resultados.addAll(buscarFarmacos(nombre));
        return resultados;
    }

    // Método privado para buscar en Almacen
    private List<Almacen> buscarProductosPorNombre(String nombre) throws SQLException {
        List<Almacen> productos = new ArrayList<>();
        String sql = "SELECT * FROM almacen WHERE nombre_producto LIKE ?";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                productos.add(new Almacen(
                    rs.getInt("id_almacen"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    Almacen.Categoria.valueOf(rs.getString("categoria")),
                    rs.getInt("cantidad_stock"),
                    rs.getBigDecimal("precio_bruto"),
                    rs.getString("proveedor"),
                    rs.getDate("fecha_ultima_compra").toLocalDate(),
                    rs.getString("numero_lote"),
                    rs.getDate("fecha_caducidad").toLocalDate(),
                    rs.getString("codigo_barras"),
                    rs.getString("observaciones")
                ));
            }
        }
        return productos;
    }

    // Método privado para buscar en Farmaco
    private List<Farmaco> buscarFarmacos(String nombre) throws SQLException {
        List<Farmaco> farmacos = new ArrayList<>();
        String sql = "SELECT * FROM farmacos WHERE nombre LIKE ?";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                farmacos.add(new Farmaco(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("cantidad"),
                    rs.getString("dosis_recomendada"),
                    rs.getString("unidad_medida"),
                    rs.getDate("fecha_caducidad"),
                    rs.getBigDecimal("precio")
                ));
            }
        }
        return farmacos;
    }
    
    public int insertarVenta(Integer idCliente, Integer idMascota, Date fechaVenta, String metodoPago, BigDecimal total) throws SQLException {
        String sql = "INSERT INTO ventas (id_cliente, id_mascota, fecha_venta, metodo_pago, total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (idCliente == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, idCliente);
            }
            if (idMascota == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, idMascota);
            }
            ps.setDate(3, fechaVenta);
            ps.setString(4, metodoPago);
            ps.setBigDecimal(5, total);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the auto-generated ID of the sale
            }
            throw new SQLException("Creating sale failed, no ID obtained.");
        }
    }


    public void insertarDetalleVenta(int idVenta, Integer idAlmacen, Integer idFarmaco, int cantidad, BigDecimal precioUnitario) throws SQLException {
        // Definición del SQL para insertar el detalle de la venta
        String sql = "INSERT INTO detalles_ventas (id_venta, id_almacen, id_farmaco, cantidad, precio_unitario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenta); // Establece el ID de la venta

            // Establece el ID del almacen o nulo si no se proporciona
            if (idAlmacen != null) {
                ps.setInt(2, idAlmacen);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            // Establece el ID del farmaco o nulo si no se proporciona
            if (idFarmaco != null) {
                ps.setInt(3, idFarmaco);
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.setInt(4, cantidad); // Establece la cantidad de producto
            ps.setBigDecimal(5, precioUnitario); // Establece el precio unitario del producto
            ps.executeUpdate(); // Ejecuta la inserción
        }
    }





}
