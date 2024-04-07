 package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import DB.MascotaDAO;
import DB.HospitalizacionDAO;
import model.Hospitalizacion;
import model.Mascota;
import java.util.List;

public class PanelInfoMascota extends JPanel {
    private MascotaDAO mascotaDao;
    private Mascota mascota;
    private JTabbedPane tabbedPane;
    private JTable tablaHospitalizaciones;

    public PanelInfoMascota(int idMascota) {
        super(new BorderLayout());
        mascotaDao = new MascotaDAO();
        this.mascota = mascotaDao.obtenerMascotaPorId(idMascota);
        if (mascota != null) {
            tabbedPane = new JTabbedPane();

            JPanel panelInfoGeneral = crearPanelInfoMascota();
            JPanel panelHospitalizaciones = crearPanelHospitalizaciones();
            JPanel panelHistorialMedico = crearPanelHistorialMedico();
            JPanel panelVacunas = crearPanelVacunas();
            JPanel panelCuidadosEspeciales = crearPanelCuidadosEspeciales();

            tabbedPane.addTab("General", null, panelInfoGeneral, "Información General");
            tabbedPane.addTab("Hospitalizaciones", null, panelHospitalizaciones, "Historial de hospitalizaciones");
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
    private JPanel crearPanelHospitalizaciones() {
        JPanel panelHospitalizaciones = new JPanel(new BorderLayout());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Motivo");

        // Aquí deberías llenar el modelo de la tabla con los datos existentes, si los hay.
        tablaHospitalizaciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaHospitalizaciones);
        panelHospitalizaciones.add(scrollPane, BorderLayout.CENTER);

        JButton btnAgregarHospitalizacion = new JButton("Añadir Hospitalización");
        btnAgregarHospitalizacion.addActionListener(e -> abrirDialogoDetalleHospitalizacion());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnAgregarHospitalizacion);
        panelHospitalizaciones.add(panelBoton, BorderLayout.SOUTH);
        
        tablaHospitalizaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablaHospitalizaciones.getSelectedRow() != -1) {
                    int selectedRow = tablaHospitalizaciones.getSelectedRow();
                    int idHospitalizacion = (Integer) tablaHospitalizaciones.getModel().getValueAt(selectedRow, 0); // Asumiendo que el ID está en la columna 0.
                    abrirDialogoDetalleHospitalizacion(idHospitalizacion);
                }
            }
        });

        return panelHospitalizaciones;
    }
    

    
    
    public void actualizarTablaHospitalizaciones() {
        HospitalizacionDAO hospitalizacionDAO = new HospitalizacionDAO();
        List<Hospitalizacion> hospitalizaciones = hospitalizacionDAO.recuperarTodasLasHospitalizaciones(); // O el método que necesites
        
        DefaultTableModel modelo = (DefaultTableModel) tablaHospitalizaciones.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de añadir las nuevas filas
        
        for (Hospitalizacion hospitalizacion : hospitalizaciones) {
            modelo.addRow(new Object[]{
                hospitalizacion.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hospitalizacion.getMotivo(),
                // Añade más datos según necesites
            });
        }
    }
    private void abrirDialogoDetalleHospitalizacion(int idHospitalizacion) {
        if (this.mascota != null) {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            HospitalizacionDAO hospitalizacionDAO = new HospitalizacionDAO();
            
            // No necesitas cargar la Hospitalizacion aquí si VentanaHospitalizadosDialogMascota
            // carga los detalles basándose en el ID de hospitalización.
            // Solo necesitas pasar el ID a la ventana.
            
            // Inicializar VentanaHospitalizadosDialogMascota con el ID de hospitalización
            VentanaHospitalizadosDialogMascota dialogo = new VentanaHospitalizadosDialogMascota(owner, true, idHospitalizacion, mascota.getNombre(), this);
            dialogo.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Mascota no está inicializada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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