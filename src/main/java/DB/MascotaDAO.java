package DB;

import model.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {
    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE nombre LIKE ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setEdad(rs.getInt("edad"));
                mascota.setIdCliente(rs.getInt("id_cliente"));
                mascotas.add(mascota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mascotas;
    }

    public List<Mascota> buscarMascotasPorCliente(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE id_cliente = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setEdad(rs.getInt("edad"));
                mascota.setIdCliente(rs.getInt("id_cliente"));
                mascotas.add(mascota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mascotas;
    }
    
}
