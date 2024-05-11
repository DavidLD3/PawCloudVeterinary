package UISwing.ventanas;

import javax.swing.JOptionPane;
import model.UserModel;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.File;

public class ConfiguracionClinica {
    private static final String APPDATA_PATH = System.getenv("APPDATA");
    private static final String CONFIG_DIR = Paths.get(APPDATA_PATH, "MiAppConfig").toString();
    private static final String CONFIG_FILE = Paths.get(CONFIG_DIR, "config.properties").toString();
    private static int limiteUsuarios = 3;

    static {
        ensureConfigFileExists();
        loadLimit();
    }

    public static int getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public static void setLimiteUsuarios(int nuevoLimite) {
        int currentUsers = UserModel.getUserCount();
        if (nuevoLimite < currentUsers) {
            JOptionPane.showMessageDialog(null, "No se puede establecer el límite porque hay más usuarios registrados que el nuevo límite: " + currentUsers, "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        limiteUsuarios = nuevoLimite;
        saveLimit();
    }

    private static void ensureConfigFileExists() {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();  // Crea el directorio si no existe
        }
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            setDefaultProperties();
            setFileHidden();  // También asegura ocultar el archivo al crearlo
        }
    }

    private static void loadLimit() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            props.load(in);
            limiteUsuarios = Integer.parseInt(props.getProperty("limiteUsuarios", String.valueOf(limiteUsuarios)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la configuración: " + e.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            setDefaultProperties();
        }
    }

    private static void saveLimit() {
        Properties props = new Properties();
        props.setProperty("limiteUsuarios", String.valueOf(limiteUsuarios));
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Settings");
            setFileHidden();  // Oculta el archivo cada vez que se guarda
        } catch (Exception e) {
            System.err.println("Error al guardar la configuración: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración: " + e.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void setFileHidden() {
        Path path = Paths.get(CONFIG_FILE);
        try {
            Files.setAttribute(path, "dos:hidden", true);
        } catch (IOException e) {
            System.err.println("No se pudo ocultar el archivo de configuración: " + e.getMessage());
        }
    }

    private static void setDefaultProperties() {
        Properties props = new Properties();
        props.setProperty("limiteUsuarios", "3"); // Establece tu valor predeterminado
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Configuración Inicial");
            setFileHidden();  // Oculta el archivo después de crearlo
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el archivo de configuración: " + e.getMessage(), "Error de Configuración", JOptionPane.ERROR_MESSAGE);
        }
    }
}
