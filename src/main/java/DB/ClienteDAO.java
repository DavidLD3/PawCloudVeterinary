package DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDAO {

    public ClienteDAO() {

    }

    public Cliente obtenerClientePorId(int id) {
        Cliente cliente = null;
        String consulta = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement statement = conn.prepareStatement(consulta)) {
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();
            if (resultados.next()) {
                cliente = new Cliente(
                    resultados.getInt("id"),
                    resultados.getString("nombre"),
                    resultados.getString("apellidos"),
                    resultados.getDate("fecha_nacimiento").toLocalDate(),
                    resultados.getString("dni"),
                    resultados.getString("nif"),
                    resultados.getString("direccion"),
                    resultados.getString("poblacion"),
                    resultados.getString("provincia"),
                    resultados.getString("telefono_fijo"),
                    resultados.getString("telefono_movil"),
                    resultados.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }

    public boolean insertarCliente(Cliente cliente) {
        try (Connection conn = Conexion.getConexion()) {
            String consulta = "INSERT INTO clientes (nombre, apellidos, fecha_nacimiento, dni, nif, direccion, poblacion, provincia, telefono_fijo, telefono_movil, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellidos());
            statement.setDate(3, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            statement.setString(4, cliente.getDni());
            statement.setString(5, cliente.getNif());
            statement.setString(6, cliente.getDireccion());
            statement.setString(7, cliente.getPoblacion());
            statement.setString(8, cliente.getProvincia());
            statement.setString(9, cliente.getTelefonoFijo());
            statement.setString(10, cliente.getTelefonoMovil());
            statement.setString(11, cliente.getEmail());
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        try (Connection conn = Conexion.getConexion()) {
            String consulta = "UPDATE clientes SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, dni = ?, nif = ?, direccion = ?, poblacion = ?, provincia = ?, telefono_fijo = ?, telefono_movil = ?, email = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellidos());
            statement.setDate(3, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            statement.setString(4, cliente.getDni());
            statement.setString(5, cliente.getNif());
            statement.setString(6, cliente.getDireccion());
            statement.setString(7, cliente.getPoblacion());
            statement.setString(8, cliente.getProvincia());
            statement.setString(9, cliente.getTelefonoFijo());
            statement.setString(10, cliente.getTelefonoMovil());
            statement.setString(11, cliente.getEmail());
            statement.setInt(12, cliente.getId());
            
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarCliente(int id) {
        try (Connection conn = Conexion.getConexion()) {
            String consulta = "DELETE FROM clientes WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                );
                clientes.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public Cliente buscarClientePorId(int idCliente) {
        Cliente cliente = null;
        String sql = "SELECT id, nombre, apellidos, fecha_nacimiento, dni, nif, direccion, poblacion, provincia, telefono_fijo, telefono_movil, email FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(), 
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nombre, apellidos, fecha_nacimiento, dni, nif, direccion, poblacion, provincia, telefono_fijo, telefono_movil, email FROM clientes";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"), 
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(), 
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                );
                clientes.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    public List<Cliente> buscarClientesPorNombreApellido(String texto) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE LOWER(nombre) LIKE ? OR LOWER(apellidos) LIKE ? OR LOWER(CONCAT(nombre, ' ', apellidos)) LIKE ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String textoBusqueda = "%" + texto.toLowerCase() + "%";
            stmt.setString(1, textoBusqueda);
            stmt.setString(2, textoBusqueda);
            stmt.setString(3, textoBusqueda);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }


    public boolean existeCliente(int idCliente) {
        String sql = "SELECT COUNT(id) FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cliente obtenerClientePorDni(String dni) {
        Cliente cliente = null;
        String consulta = "SELECT * FROM clientes WHERE dni = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public List<Cliente> buscarClientes(String texto) {   //Metodo mas flexible para buscar por nombre, apellidos y dni simultaneamente
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE LOWER(nombre) LIKE ? OR LOWER(apellidos) LIKE ? OR LOWER(CONCAT(nombre, ' ', apellidos)) LIKE ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String textoBusqueda = "%" + texto.toLowerCase() + "%";
            stmt.setString(1, textoBusqueda);
            stmt.setString(2, textoBusqueda);
            stmt.setString(3, textoBusqueda);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getString("nif"),
                    rs.getString("direccion"),
                    rs.getString("poblacion"),
                    rs.getString("provincia"),
                    rs.getString("telefono_fijo"),
                    rs.getString("telefono_movil"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

}
