package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import DB.MascotaDAO;
import DB.HistorialMedicoDAO;
import DB.HospitalizacionDAO;
import model.HistorialMedico;
import model.Hospitalizacion;
import model.Mascota;
import java.util.List;

public class PanelInfoMascota extends JPanel {
    private MascotaDAO mascotaDao;
    private Mascota mascota;
    private JTabbedPane tabbedPane;
    private HospitalizacionDAO hospitalizacionDao;
    private HistorialMedicoDAO historialMedicoDAO;
    private JTable tablaHistorial;

    public PanelInfoMascota(int idMascota) {
        super(new BorderLayout());
        mascotaDao = new MascotaDAO();
        hospitalizacionDao = new HospitalizacionDAO(); 
        this.historialMedicoDAO = new HistorialMedicoDAO();
        
        this.mascota = mascotaDao.obtenerMascotaPorId(idMascota);
        if (mascota != null) {
            tabbedPane = new JTabbedPane();

            JPanel panelInfoGeneral = crearPanelInfoMascota();           
            JPanel panelHistorialMedico = crearPanelHistorialMedico();
            JPanel panelVacunas = crearPanelVacunas();
            JPanel panelHospitalizaciones = crearPanelHospitalizaciones();
            

            tabbedPane.addTab("General", null, panelInfoGeneral, "Información General");
            tabbedPane.addTab("Hospitalizaciones", null, panelHospitalizaciones, "Historial de hospitalizaciones");
            tabbedPane.addTab("Historial Médico", null, panelHistorialMedico, "Historial médico de la mascota");
            tabbedPane.addTab("Vacunas", null, panelVacunas, "Registro de vacunas");
            

            add(tabbedPane, BorderLayout.CENTER);
        }   else {
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

    private JPanel crearPanelHospitalizaciones() {
        JPanel panelHospitalizaciones = new JPanel(new BorderLayout());
        
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Desactiva la edición en todas las celdas
                return false;
            }
        };
        
        modeloTabla.addColumn("ID"); // ID de la hospitalización, oculto si lo prefieres
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Motivo");

        List<Hospitalizacion> hospitalizaciones = hospitalizacionDao.obtenerHospitalizacionesPorIdMascota(mascota.getId());
        for (Hospitalizacion hospitalizacion : hospitalizaciones) {
            modeloTabla.addRow(new Object[]{
                hospitalizacion.getId(), // Asumiendo que tienes un getter para el ID
                hospitalizacion.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hospitalizacion.getMotivo()
            });
        }

        JTable tablaHospitalizaciones = new JTable(modeloTabla);
        tablaHospitalizaciones.removeColumn(tablaHospitalizaciones.getColumnModel().getColumn(0)); // Oculta la columna de ID si no deseas mostrarla
        JScrollPane scrollPane = new JScrollPane(tablaHospitalizaciones);
        panelHospitalizaciones.add(scrollPane, BorderLayout.CENTER);

        JButton btnAgregarHospitalizacion = new JButton("Añadir Hospitalización");
        btnAgregarHospitalizacion.addActionListener(e -> abrirDialogoDetalleHospitalizacion());
        tablaHospitalizaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int filaSeleccionada = tablaHospitalizaciones.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int idHospitalizacion = (int) modeloTabla.getValueAt(filaSeleccionada, 0); // Asegúrate de obtener el ID correctamente
                        // Aquí podrías buscar la información de la hospitalización usando idHospitalizacion
                        // Y luego pasar esa información al diálogo si es necesario
                        abrirDialogoDetalleHospitalizacionConId(idHospitalizacion);
                    }
                }
            }
        });
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnAgregarHospitalizacion);
        
        panelHospitalizaciones.add(panelBoton, BorderLayout.SOUTH);

        return panelHospitalizaciones;
    }
        
    public interface ActualizacionListener {
        void onActualizacion();
    }
    private void abrirDialogoDetalleHospitalizacionConId(int idHospitalizacion) {
      
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        // Asumiendo que `VentanaHospitalizadosDialogMascota` tiene un constructor que acepta un ID de hospitalización
        VentanaHospitalizadosDialogMascota dialogo = new VentanaHospitalizadosDialogMascota(owner, true, mascota.getId(), mascota.getNombre(), idHospitalizacion);
        dialogo.setVisible(true);
    }

    private void abrirDialogoDetalleHospitalizacion() {
        if (this.mascota != null) {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            // Asumiendo que `Mascota` tiene un método getId() para obtener su ID
            int idDeLaMascota = this.mascota.getId();
            String nombreDeLaMascota = this.mascota.getNombre();
            VentanaHospitalizadosDialogMascota dialogo = new VentanaHospitalizadosDialogMascota(owner, true, idDeLaMascota, nombreDeLaMascota);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Mascota no está inicializada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearPanelHistorialMedico() {
        JPanel panelHistorialMedico = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Fecha", "Diagnóstico", "Tratamiento"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Para evitar edición directa en la tabla
            }
        };

        tablaHistorial = new JTable(modeloTabla); // Asegurarse de que tablaHistorial es accesible a nivel de clase
        tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(0)); // Oculta la columna ID
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        panelHistorialMedico.add(scrollPane, BorderLayout.CENTER);

        // Evento de doble clic para editar registros
        tablaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int filaSeleccionada = tablaHistorial.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int idHistorial = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        abrirDialogoAgregarEditarRegistro(idHistorial);
                    }
                }
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Añadir Registro");
        btnAgregar.addActionListener(e -> abrirDialogoAgregarEditarRegistro(null)); // null para un nuevo registro
        panelBotones.add(btnAgregar);

        panelHistorialMedico.add(panelBotones, BorderLayout.SOUTH);

        // Carga los registros del historial médico de la base de datos
        cargarDatosHistorialMedico(modeloTabla, tablaHistorial);

        return panelHistorialMedico;
    }


    private void cargarDatosHistorialMedico(DefaultTableModel modeloTabla, JTable tablaHistorial) {
        List<HistorialMedico> registros = historialMedicoDAO.obtenerHistorialesPorMascota(mascota.getId());
        for (HistorialMedico registro : registros) {
            modeloTabla.addRow(new Object[]{
                    registro.getId(),
                    registro.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    registro.getDiagnostico(),
                    registro.getTratamiento()
            });
        }
    }
    private void abrirDialogoAgregarEditarRegistro(Integer idHistorial) {
        HistorialMedico registro = null;
        if (idHistorial != null) {
            registro = historialMedicoDAO.obtenerHistorialPorId(idHistorial);
        }
        DialogoHistorialMedico dialogo = new DialogoHistorialMedico((Frame) SwingUtilities.getWindowAncestor(this), true, registro);
        dialogo.setVisible(true);
        if (dialogo.isConfirmado()) {
            cargarDatosHistorialMedico((DefaultTableModel) tablaHistorial.getModel(), tablaHistorial); 
        }
    }

    private JPanel crearPanelVacunas() {
        JPanel panel = new JPanel(new BorderLayout());
        // Aquí tu código para mostrar el registro de vacunas...
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