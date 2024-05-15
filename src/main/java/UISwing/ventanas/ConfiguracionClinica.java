package UISwing.ventanas;

import javax.swing.JOptionPane;
import model.UserModel;
import java.util.prefs.Preferences;

public class ConfiguracionClinica {
    private static final Preferences prefs = Preferences.userRoot().node("MiAppConfig");
    private static final String LIMITE_USUARIOS_KEY = "limiteUsuarios";
    private static final int DEFAULT_LIMITE_USUARIOS = 3;

    static {
        if (prefs.getInt(LIMITE_USUARIOS_KEY, -1) == -1) {
            prefs.putInt(LIMITE_USUARIOS_KEY, DEFAULT_LIMITE_USUARIOS);
        }
    }

    public static int getLimiteUsuarios() {
        return prefs.getInt(LIMITE_USUARIOS_KEY, DEFAULT_LIMITE_USUARIOS);
    }

    public static void setLimiteUsuarios(int nuevoLimite) {
        int currentUsers = UserModel.getUserCount();
        if (nuevoLimite < currentUsers) {
            JOptionPane.showMessageDialog(null, "No se puede establecer el límite porque hay más usuarios registrados que el nuevo límite: " + currentUsers, "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        prefs.putInt(LIMITE_USUARIOS_KEY, nuevoLimite);
    }
}
