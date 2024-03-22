package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import DB.MascotaDAO;
import model.Mascota;

public class PanelInfoMascota extends JPanel {
    private MascotaDAO mascotaDao;
    private Mascota mascota;
    private JTabbedPane tabbedPane;

    public PanelInfoMascota(int idMascota) {
        super(new BorderLayout());
        mascotaDao = new MascotaDAO();
        this.mascota = mascotaDao.obtenerMascotaPorId(idMascota);
        if (mascota != null) {
            tabbedPane = new JTabbedPane();

            JPanel panelInfoGeneral = crearPanelInfoMascota();
            JPanel panelVisitasVet = crearPanelVisitasVet();
            JPanel panelHistorialMedico = crearPanelHistorialMedico();
            JPanel panelVacunas = crearPanelVacunas();
            JPanel panelCuidadosEspeciales = crearPanelCuidadosEspeciales();

            tabbedPane.addTab("General", null, panelInfoGeneral, "Información General");
            tabbedPane.addTab("Visitas Veterinarias", null, panelVisitasVet, "Historial de visitas al veterinario");
            tabbedPane.addTab("Historial Médico", null, panelHistorialMedico, "Historial médico de la mascota");
            tabbedPane.addTab("Vacunas", null, panelVacunas, "Registro de vacunas");
            tabbedPane.addTab("Cuidados Especiales", null, panelCuidadosEspeciales, "Cuidados especiales de la mascota");

            add(tabbedPane, BorderLayout.CENTER);
        } else {
            add(new JLabel("Mascota no encontrada."), BorderLayout.CENTER);
        }
    }

    private JPanel crearPanelInfoMascota() {
        JPanel panel = new JPanel(new GridLayout(0, 2)); // GridLayout para organizar los campos y etiquetas

        // Añadir los campos de texto y etiquetas al panel
        agregarCampo(panel, "Nombre:", mascota.getNombre());
        agregarCampo(panel, "Especie:", mascota.getEspecie());
        agregarCampo(panel, "Raza:", mascota.getRaza());
        agregarCampo(panel, "Edad:", String.valueOf(mascota.getEdad()));
        agregarCampo(panel, "Microchip:", mascota.getMicrochip());
        agregarCampo(panel, "Fecha de Nacimiento:", mascota.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        agregarCampo(panel, "Carácter:", mascota.getCaracter());
        agregarCampo(panel, "Color:", mascota.getColor());
        agregarCampo(panel, "Tipo de pelo:", mascota.getTipoPelo());
        agregarCampo(panel, "Sexo:", mascota.getSexo().name());
        agregarCampo(panel, "Esterilizado:", mascota.isEsterilizado() ? "Sí" : "No");

        return panel;
    }

    private void agregarCampo(JPanel panel, String etiqueta, String valor) {
        panel.add(new JLabel(etiqueta));
        JTextField textField = new JTextField(valor, 20);
        textField.setEditable(false);
        panel.add(textField);
    }

    // Métodos para crear paneles específicos
    private JPanel crearPanelVisitasVet() {
        JPanel panel = new JPanel(new BorderLayout());
        // Aquí tu código para mostrar la información de las visitas al veterinario...
        return panel;
    }

    private JPanel crearPanelHistorialMedico() {
        JPanel panel = new JPanel(new BorderLayout());
        // Aquí tu código para mostrar el historial médico...
        return panel;
    }

    private JPanel crearPanelVacunas() {
        JPanel panel = new JPanel(new BorderLayout());
        // Aquí tu código para mostrar el registro de vacunas...
        return panel;
    }

    private JPanel crearPanelCuidadosEspeciales() {
        JPanel panel = new JPanel(new BorderLayout());
        // Aquí tu código para mostrar los cuidados especiales...
        return panel;
    }
    // Método para obtener el nombre de la ventana basado en la mascota
    public String obtenerTituloVentana() {
        if (mascota != null) {
            return "Info " + mascota.getNombre();
        } else {
            return "Mascota no encontrada";
        }
    }
}