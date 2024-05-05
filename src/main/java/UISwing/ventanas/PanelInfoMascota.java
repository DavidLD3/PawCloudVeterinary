package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;
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
        agregarCampo(panel, "Pasaporte:", String.valueOf(mascota.getPasaporte()));
        agregarCampo(panel, "Microchip:", mascota.getMicrochip());
        
        // Formatear y mostrar la fecha de nacimiento
        String fechaNacimiento = mascota.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        agregarCampo(panel, "Fecha de Nacimiento:", fechaNacimiento);

        // Calcular y mostrar la edad
        String edad = calcularEdad(mascota.getFechaNacimiento());
        agregarCampo(panel, "Edad:", edad);

        agregarCampo(panel, "Carácter:", mascota.getCaracter());
        agregarCampo(panel, "Color:", mascota.getColor());
        agregarCampo(panel, "Tipo de pelo:", mascota.getTipoPelo());
        agregarCampo(panel, "Sexo:", mascota.getSexo().name());
        agregarCampo(panel, "Esterilizado:", mascota.isEsterilizado() ? "Sí" : "No");

        return panel;
    }
    private String calcularEdad(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, hoy);
        return String.format("%d años, %d meses, %d días", periodo.getYears(), periodo.getMonths(), periodo.getDays());
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
        btnAgregarHospitalizacion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarHospitalizacion.setBackground(Color.WHITE);
        btnAgregarHospitalizacion.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAgregarHospitalizacion.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAgregarHospitalizacion.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAgregarHospitalizacion.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAgregarHospitalizacion.setOpaque(true);
        btnAgregarHospitalizacion.setRolloverEnabled(true);
        btnAgregarHospitalizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarHospitalizacion.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAgregarHospitalizacion.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarHospitalizacion.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAgregarHospitalizacion.setForeground(Color.decode("#0057FF"));
            }
        });
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

        String[] columnas = {"ID", "Fecha", "Tipo de Intervención", "Diagnóstico"};  // Asegúrate de que el orden de los nombres de las columnas sea correcto
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Para evitar edición directa en la tabla
            }
        };
        
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(0));  // Eliminamos la columna ID que no queremos mostrar
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        panelHistorialMedico.add(scrollPane, BorderLayout.CENTER);

        // Evento de doble clic para editar registros
        tablaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int filaSeleccionada = tablaHistorial.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Obtener el ID del registro seleccionado a partir del modelo (nota: ajusta el índice si es necesario)
                        int idHistorial = (int) modeloTabla.getValueAt(filaSeleccionada, 0); // Aquí asumimos que el ID está en la columna 0 del modelo
                        abrirDialogoAgregarEditarRegistro(idHistorial);
                    }
                }
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Añadir Registro");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregar.setBackground(Color.WHITE);
        btnAgregar.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAgregar.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAgregar.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAgregar.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAgregar.setOpaque(true);
        btnAgregar.setRolloverEnabled(true);
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregar.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAgregar.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregar.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAgregar.setForeground(Color.decode("#0057FF"));
            }
        });
        JButton btnEliminar = new JButton("Eliminar Registro");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnEliminar.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnEliminar.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnEliminar.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnEliminar.setOpaque(true);
        btnEliminar.setRolloverEnabled(true);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnEliminar.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnEliminar.setForeground(Color.decode("#0057FF"));
            }
        });
        btnAgregar.addActionListener(e -> abrirDialogoAgregarEditarRegistro(null)); // null para un nuevo registro
        btnEliminar.addActionListener(this::accionEliminarRegistro);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelHistorialMedico.add(panelBotones, BorderLayout.SOUTH);

        // Cargar los registros del historial médico desde la base de datos
        cargarDatosHistorialMedico(modeloTabla, tablaHistorial);

        return panelHistorialMedico;
    }

    private void accionEliminarRegistro(ActionEvent e) {
        int filaSeleccionada = tablaHistorial.getSelectedRow();
        if (filaSeleccionada != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este registro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int idHistorial = (int) tablaHistorial.getModel().getValueAt(filaSeleccionada, 0);
                if (historialMedicoDAO.eliminarHistorial(idHistorial)) {
                    ((DefaultTableModel) tablaHistorial.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un registro para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarDatosHistorialMedico(DefaultTableModel modeloTabla, JTable tablaHistorial) {
        modeloTabla.setRowCount(0); // Limpia el modelo de la tabla primero

        List<HistorialMedico> registros = historialMedicoDAO.obtenerHistorialesPorMascota(mascota.getId());
        for (HistorialMedico registro : registros) {
            modeloTabla.addRow(new Object[]{
                registro.getId(),  // ID de la hospitalización, no visible en la tabla
                registro.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                registro.getTipoIntervencion(),
                registro.getDiagnostico()
            });
        }
    }
    private void abrirDialogoAgregarEditarRegistro(Integer idHistorial) {
        HistorialMedico registro = null;
        if (idHistorial != null) {
            registro = historialMedicoDAO.obtenerHistorialPorId(idHistorial);
        }
        // Pasa el ID de la mascota al constructor del diálogo
        DialogoHistorialMedico dialogo = new DialogoHistorialMedico((Frame) SwingUtilities.getWindowAncestor(this), true, mascota.getId(), registro);

        // Centrando el diálogo
        dialogo.setLocationRelativeTo((Frame) SwingUtilities.getWindowAncestor(this));

        dialogo.setVisible(true);
        if (dialogo.isConfirmado()) {
            cargarDatosHistorialMedico((DefaultTableModel) tablaHistorial.getModel(), tablaHistorial);
            actualizarTablas();  // Este método ahora maneja la actualización correcta
        }
    }
    private JPanel crearPanelVacunas() {
        JPanel panelVacunas = new JPanel(new BorderLayout());
        String[] columnasVacunas = {"Fecha", "Tipo de Intervención", "Diagnóstico"};
        DefaultTableModel modeloVacunas = new DefaultTableModel(columnasVacunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaVacunas = new JTable(modeloVacunas);
        JScrollPane scrollPaneVacunas = new JScrollPane(tablaVacunas);
        panelVacunas.add(scrollPaneVacunas, BorderLayout.CENTER);

        cargarDatosVacunas(modeloVacunas);
        return panelVacunas;
    }
    private void cargarDatosVacunas(DefaultTableModel modeloVacunas) {
        modeloVacunas.setRowCount(0); // Limpiar tabla antes de cargar datos

        List<HistorialMedico> registros = historialMedicoDAO.obtenerHistorialesPorMascota(mascota.getId());
        for (HistorialMedico registro : registros) {
            if (registro.getTipoIntervencion().equals("Vacunación")) {
                modeloVacunas.addRow(new Object[]{
                    registro.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    registro.getTipoIntervencion(),
                    registro.getDiagnostico()
                });
            }
        }
    }
    private void actualizarTablas() {
        // Accede a la pestaña correcta del JTabbedPane
        JPanel panelVacunas = (JPanel) tabbedPane.getComponentAt(3); // Asumiendo que 3 es el índice de la pestaña de vacunas
        JScrollPane scrollPane = (JScrollPane) panelVacunas.getComponent(0); // Asumiendo que el JScrollPane es el primer (y único) componente
        JTable tablaVacunas = (JTable) scrollPane.getViewport().getView();

        // Ahora puedes acceder al modelo de la tabla
        DefaultTableModel modeloVacunas = (DefaultTableModel) tablaVacunas.getModel();
        cargarDatosVacunas(modeloVacunas);
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