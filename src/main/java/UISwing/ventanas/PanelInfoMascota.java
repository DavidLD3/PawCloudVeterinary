package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
import UISwing.recursos.RoundedPanel;

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
            tabbedPane.addTab("General", null, crearPanelInfoMascota(), "Información General");
            tabbedPane.addTab("Hospitalizaciones", null, crearPanelHospitalizaciones(), "Historial de hospitalizaciones");
            tabbedPane.addTab("Historial Médico", null, crearPanelHistorialMedico(), "Historial médico de la mascota");
            tabbedPane.addTab("Vacunas", null, crearPanelVacunas(), "Registro de vacunas");
            RoundedPanel mainPanel = new RoundedPanel(20);
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(Color.decode("#7E88E2"));
            mainPanel.add(tabbedPane, BorderLayout.CENTER);
            add(mainPanel, BorderLayout.CENTER);
        } else {
            add(new JLabel("Mascota no encontrada."), BorderLayout.CENTER);
        }
    }

    private JPanel crearPanelInfoMascota() {
        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(new GridLayout(0, 2));
        panel.setBackground(Color.decode("#7E88E2"));
        agregarCampo(panel, "Nombre:", mascota.getNombre());
        agregarCampo(panel, "Especie:", mascota.getEspecie());
        agregarCampo(panel, "Raza:", mascota.getRaza());
        agregarCampo(panel, "Pasaporte:", String.valueOf(mascota.getPasaporte()));
        agregarCampo(panel, "Microchip:", mascota.getMicrochip());
        agregarCampo(panel, "Fecha de Nacimiento:", mascota.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        agregarCampo(panel, "Edad:", calcularEdad(mascota.getFechaNacimiento()));
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
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13)); 
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        panel.add(label);
        
        JTextField textField = new JTextField(valor, 20);
        textField.setEditable(false);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(textField);
    }


    private JPanel crearPanelHospitalizaciones() {
        RoundedPanel panelHospitalizaciones = new RoundedPanel(20);
        panelHospitalizaciones.setLayout(new BorderLayout());
        panelHospitalizaciones.setBackground(Color.decode("#7E88E2"));

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Motivo");

        List<Hospitalizacion> hospitalizaciones = hospitalizacionDao.obtenerHospitalizacionesPorIdMascota(mascota.getId());
        for (Hospitalizacion hospitalizacion : hospitalizaciones) {
            modeloTabla.addRow(new Object[]{
                hospitalizacion.getId(),
                hospitalizacion.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hospitalizacion.getMotivo()
            });
        }

        JTable tablaHospitalizaciones = new JTable(modeloTabla);
        tablaHospitalizaciones.removeColumn(tablaHospitalizaciones.getColumnModel().getColumn(0));
        JScrollPane scrollPane = new JScrollPane(tablaHospitalizaciones);
        panelHospitalizaciones.add(scrollPane, BorderLayout.CENTER);

        JButton btnAgregarHospitalizacion = crearBotonPersonalizado("Añadir Hospitalización", e -> abrirDialogoDetalleHospitalizacionConId(null));
        tablaHospitalizaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int filaSeleccionada = tablaHospitalizaciones.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int idHospitalizacion = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        abrirDialogoDetalleHospitalizacionConId(idHospitalizacion);
                    }
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAgregarHospitalizacion);
        panelHospitalizaciones.add(panelBoton, BorderLayout.SOUTH);

        return panelHospitalizaciones;
    }

    private void abrirDialogoDetalleHospitalizacionConId(Integer idHospitalizacion) {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        VentanaHospitalizadosDialogMascota dialogo;
        if (idHospitalizacion == null) {
            dialogo = new VentanaHospitalizadosDialogMascota(owner, true, mascota.getId(), mascota.getNombre());
        } else {
            dialogo = new VentanaHospitalizadosDialogMascota(owner, true, mascota.getId(), mascota.getNombre(), idHospitalizacion);
        }
        dialogo.setVisible(true);
    }


    private JPanel crearPanelHistorialMedico() {
        RoundedPanel panelHistorialMedico = new RoundedPanel(20);
        panelHistorialMedico.setLayout(new BorderLayout());
        panelHistorialMedico.setBackground(Color.decode("#7E88E2"));

        String[] columnas = {"ID", "Fecha", "Tipo de Intervención", "Diagnóstico"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(0));
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        panelHistorialMedico.add(scrollPane, BorderLayout.CENTER);

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

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        JButton btnAgregar = crearBotonPersonalizado("Añadir Registro", e -> abrirDialogoAgregarEditarRegistro(null));
        JButton btnEliminar = crearBotonPersonalizado("Eliminar Registro", this::accionEliminarRegistro);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelHistorialMedico.add(panelBotones, BorderLayout.SOUTH);
        cargarDatosHistorialMedico(modeloTabla, tablaHistorial);

        return panelHistorialMedico;
    }

    private JPanel crearPanelVacunas() {
        RoundedPanel panelVacunas = new RoundedPanel(20);
        panelVacunas.setLayout(new BorderLayout());
        panelVacunas.setBackground(Color.decode("#7E88E2"));

        String[] columnasVacunas = {"Fecha", "Tipo de Intervención", "Fármaco"};
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
        modeloVacunas.setRowCount(0);

        List<HistorialMedico> registros = historialMedicoDAO.obtenerHistorialesPorMascota(mascota.getId());
        for (HistorialMedico registro : registros) {
            if (registro.getTipoIntervencion().equals("Vacunación")) {
                modeloVacunas.addRow(new Object[]{
                    registro.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    registro.getTipoIntervencion(),
                    registro.getTratamiento()
                });
            }
        }
    }

    private void cargarDatosHistorialMedico(DefaultTableModel modeloTabla, JTable tablaHistorial) {
        modeloTabla.setRowCount(0);
        List<HistorialMedico> registros = historialMedicoDAO.obtenerHistorialesPorMascota(mascota.getId());
        for (HistorialMedico registro : registros) {
            modeloTabla.addRow(new Object[]{
                registro.getId(),
                registro.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                registro.getTipoIntervencion(),
                registro.getDiagnostico()
            });
        }
    }

    private JButton crearBotonPersonalizado(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.decode("#0057FF"));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setRolloverEnabled(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.decode("#003366"));
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.decode("#0057FF"));
            }
        });
        btn.addActionListener(listener);
        return btn;
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

    private void abrirDialogoAgregarEditarRegistro(Integer idHistorial) {
        HistorialMedico registro = null;
        if (idHistorial != null) {
            registro = historialMedicoDAO.obtenerHistorialPorId(idHistorial);
        }
        DialogoHistorialMedico dialogo = new DialogoHistorialMedico((Frame) SwingUtilities.getWindowAncestor(this), true, mascota.getId(), registro);
        dialogo.setLocationRelativeTo((Frame) SwingUtilities.getWindowAncestor(this));
        dialogo.setVisible(true);

        if (dialogo.isConfirmado()) {
            cargarDatosHistorialMedico((DefaultTableModel) tablaHistorial.getModel(), tablaHistorial);
            actualizarTablas();
        }
    }

    private void actualizarTablas() {
        JPanel panelVacunas = (JPanel) tabbedPane.getComponentAt(3);
        JScrollPane scrollPane = (JScrollPane) panelVacunas.getComponent(0);
        JTable tablaVacunas = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel modeloVacunas = (DefaultTableModel) tablaVacunas.getModel();
        cargarDatosVacunas(modeloVacunas);
    }

    public String obtenerTituloVentana() {
        if (mascota != null) {
            return "Info " + mascota.getNombre();
        } else {
            return "Mascota no encontrada";
        }
    }
}
