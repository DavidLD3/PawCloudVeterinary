package UISwing.ventanas;

import javax.swing.JOptionPane;

import model.UserModel;

import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class ConfiguracionClinica {
    private static int limiteUsuarios = 3;
    private static final String CONFIG_FILE = "config.properties";

    static {
        loadLimit();
    }

    public static int getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public static void setLimiteUsuarios(int nuevoLimite) {
        int currentUsers = UserModel.getUserCount();
        if (nuevoLimite < currentUsers) {
            // Muestra el mensaje de error y termina la ejecución del método aquí
            JOptionPane.showMessageDialog(null, "No se puede establecer el límite porque hay más usuarios registrados que el nuevo límite: " + currentUsers, "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return; // Asegura que no se continúe con el guardado
        }
        
        limiteUsuarios = nuevoLimite;
        saveLimit(); // Solo se ejecuta si no hay errores
    }

    private static void loadLimit() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            props.load(in);
            limiteUsuarios = Integer.parseInt(props.getProperty("limiteUsuarios", "3"));
        } catch (Exception e) {
            System.err.println("Error al cargar la configuración: " + e.getMessage());
            // Aquí podrías considerar mostrar un mensaje de error también.
            JOptionPane.showMessageDialog(null, "Error al cargar la configuración: " + e.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void saveLimit() {
        System.out.println("Guardando el límite: " + limiteUsuarios);
        Properties props = new Properties();
        props.setProperty("limiteUsuarios", String.valueOf(limiteUsuarios));
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Settings");
        } catch (Exception e) {
            System.err.println("Error al guardar la configuración: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración: " + e.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarLimiteUsuarios(int nuevoLimite) {
        ConfiguracionClinica.setLimiteUsuarios(nuevoLimite);
        // Verifica si el límite realmente cambió
        if (ConfiguracionClinica.getLimiteUsuarios() == nuevoLimite) {
            JOptionPane.showMessageDialog(null, "El límite de usuarios se ha actualizado a: " + nuevoLimite);
        }
    }


}
