package DB;

import model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Connection conn;

    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Categoria> obtenerCategorias() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, tipo, categoria_nombre FROM Categoria";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getBoolean("tipo"),
                    rs.getString("categoria_nombre")
                );
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    public boolean agregarCategoria(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categoria (tipo, categoria_nombre) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, categoria.isTipo());
            pstmt.setString(2, categoria.getNombreCategoria());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}