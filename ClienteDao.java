package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Cliente;

public class ClienteDao {

    private Conexion conexion;

    public ClienteDao() {
        conexion = new Conexion(); // Inicializa la clase Conexion
    }

    public Cliente obtenerClientePorId(int id) {
        Cliente cliente = null;
        try (Connection conn = conexion.getConexion()) { // Usar try-with-resources para manejo automático de la conexión
            String consulta = "SELECT * FROM clientes WHERE id_cliente = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();

            if (resultados.next()) {
                // Asumiendo que tienes un constructor adecuado en tu clase Cliente
                cliente = new Cliente(
                    resultados.getString("id_cliente"), // Asegúrate de que el tipo de dato coincida
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
                    resultados.getString("correo_electronico")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }

    public boolean insertarCliente(Cliente cliente) {
        try (Connection conn = conexion.getConexion()) { // Obtiene la conexión desde la clase Conexion
            String consulta = "INSERT INTO clientes (nombre, apellidos, fecha_nacimiento, dni, nif, direccion, poblacion, provincia, telefono_fijo, telefono_movil, correo_electronico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(consulta); // Usa conn para preparar la consulta
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellidos());
            // Asegúrate de convertir LocalDate a SQL Date
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
        try (Connection conn = conexion.getConexion()) { // Obtiene la conexión desde la clase Conexion
            String consulta = "UPDATE clientes SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, dni = ?, nif = ?, direccion = ?, poblacion = ?, provincia = ?, telefono_fijo = ?, telefono_movil = ?, correo_electronico = ? WHERE id_cliente = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            // Configuramos los valores
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellidos());
            statement.setDate(3, java.sql.Date.valueOf(cliente.getFechaNacimiento())); // Asegúrate de convertir LocalDate a SQL Date
            statement.setString(4, cliente.getDni());
            statement.setString(5, cliente.getNif());
            statement.setString(6, cliente.getDireccion());
            statement.setString(7, cliente.getPoblacion());
            statement.setString(8, cliente.getProvincia());
            statement.setString(9, cliente.getTelefonoFijo());
            statement.setString(10, cliente.getTelefonoMovil());
            statement.setString(11, cliente.getEmail());
            statement.setInt(12, Integer.parseInt(cliente.getId())); // Asegúrate de convertir el ID a int
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        try (Connection conn = conexion.getConexion()) { // Obtiene la conexión desde la clase Conexion
            String consulta = "DELETE FROM clientes WHERE id_cliente = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
