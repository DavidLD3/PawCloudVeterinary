package DB;

import model.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private Connection connection;

    public EmpleadoDAO() {
        try {
            this.connection = Conexion.getConexion();
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellidos, dni, telefono, email, horario_trabajo, fecha_contratacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.getNombre());
            statement.setString(2, empleado.getApellidos());
            statement.setString(3, empleado.getDni());
            statement.setString(4, empleado.getTelefono());
            statement.setString(5, empleado.getEmail());
            statement.setString(6, empleado.getHorarioTrabajo());
            statement.setDate(7, empleado.getFechaContratacion() != null ? new java.sql.Date(empleado.getFechaContratacion().getTime()) : null);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar el empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellidos(resultSet.getString("apellidos"));
                empleado.setDni(resultSet.getString("dni"));
                empleado.setTelefono(resultSet.getString("telefono"));
                empleado.setEmail(resultSet.getString("email"));
                empleado.setHorarioTrabajo(resultSet.getString("horario_trabajo"));
                empleado.setFechaContratacion(resultSet.getDate("fecha_contratacion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }
    
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellidos = ?, dni = ?, telefono = ?, email = ?, horario_trabajo = ?, fecha_contratacion = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.getNombre());
            statement.setString(2, empleado.getApellidos());
            statement.setString(3, empleado.getDni());
            statement.setString(4, empleado.getTelefono());
            statement.setString(5, empleado.getEmail());
            statement.setString(6, empleado.getHorarioTrabajo());
            statement.setDate(7, new java.sql.Date(empleado.getFechaContratacion().getTime()));
            statement.setInt(8, empleado.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarEmpleado(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    public Empleado obtenerEmpleadoPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellidos(resultSet.getString("apellidos"));
                empleado.setDni(resultSet.getString("dni"));
                empleado.setTelefono(resultSet.getString("telefono"));
                empleado.setEmail(resultSet.getString("email"));
                empleado.setHorarioTrabajo(resultSet.getString("horario_trabajo"));
                empleado.setFechaContratacion(resultSet.getDate("fecha_contratacion"));
                return empleado;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



}
