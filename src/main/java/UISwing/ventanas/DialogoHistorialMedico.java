package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import DB.FarmacoDAO;
import DB.HistorialMedicoDAO;
import UISwing.recursos.GradientPanel2;
import UISwing.recursos.RoundedPanel;
import model.Farmaco;
import model.HistorialMedico;

public class DialogoHistorialMedico extends JDialog {
    private JDateChooser dateChooser;
    private JTextArea txtDiagnostico, txtTratamiento;
    private JComboBox<String> comboTipoIntervencion;
    private HistorialMedicoDAO historialMedicoDAO;
    private HistorialMedico historialMedicoActual;
    private boolean confirmado = false;
    private int idMascota;

    public DialogoHistorialMedico(Frame owner, boolean modal, int idMascota, HistorialMedico historialMedico) {
        super(owner, modal);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 600, 400, 20, 20));
        this.idMascota = idMascota;
        this.historialMedicoActual = historialMedico;
        this.historialMedicoDAO = new HistorialMedicoDAO();
        inicializarComponentes();
        cargarDatosHistorial(historialMedico);
    }

    private void inicializarComponentes() {
        setTitle("Historial Médico");

        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#7E88E2"));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 25));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        panelCampos.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCampos.add(new JLabel("Tipo de intervención:"), gbc);
        gbc.gridx = 1;
        comboTipoIntervencion = new JComboBox<>(new String[]{
            "Evaluación general y anamnesis", "Examen físico", "Vacunación",
            "Desparasitación", "Pruebas de diagnóstico",
            "Asesoramiento nutricional y de comportamiento", "Planificación preventiva",
            "Recomendaciones de cuidado general"
        });
        comboTipoIntervencion.setPreferredSize(new Dimension(150, 25));
        panelCampos.add(comboTipoIntervencion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCampos.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1;
        txtDiagnostico = new JTextArea(5, 20);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);
        panelCampos.add(new JScrollPane(txtDiagnostico), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCampos.add(new JLabel("Tratamiento:"), gbc);
        gbc.gridx = 1;
        txtTratamiento = new JTextArea(5, 20);
        txtTratamiento.setLineWrap(true);
        txtTratamiento.setWrapStyleWord(true);
        JScrollPane scrollTratamiento = new JScrollPane(txtTratamiento);

        JPanel panelBotonesTratamiento = new JPanel(new BorderLayout());
        panelBotonesTratamiento.setOpaque(false);
        panelBotonesTratamiento.add(scrollTratamiento, BorderLayout.CENTER);
        JButton btnSeleccionarFarmaco = crearBotonPersonalizado("Añadir Fármaco", this::abrirDialogoSeleccionFarmaco);
        panelBotonesTratamiento.add(btnSeleccionarFarmaco, BorderLayout.SOUTH);
        panelCampos.add(panelBotonesTratamiento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.add(crearBotonPersonalizado("Guardar", this::guardarRegistro));
        panelBotones.add(crearBotonPersonalizado("Cancelar", e -> setVisible(false)));
        panelCampos.add(panelBotones, gbc);

        mainPanel.add(panelCampos, BorderLayout.CENTER);

        GradientPanel2 gradientPanel = new GradientPanel2();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(mainPanel, BorderLayout.CENTER);
        add(gradientPanel, BorderLayout.CENTER);

        pack();
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

    private void abrirDialogoSeleccionFarmaco(ActionEvent e) {
        DialogoSeleccionFarmaco dialogoFarmaco = new DialogoSeleccionFarmaco((Frame) SwingUtilities.getWindowAncestor(this), true, new FarmacoDAO());
        dialogoFarmaco.setVisible(true);

        if (dialogoFarmaco.isSeleccionado()) {
            Farmaco farmacoSeleccionado = dialogoFarmaco.getFarmacoSeleccionado();
            String dosis = dialogoFarmaco.getDosis();
            String frecuencia = dialogoFarmaco.getFrecuencia();

            if (farmacoSeleccionado != null) {
                String descripcionFarmaco = String.format("Fármaco: %s, Dosis: %s, Frecuencia: %s",
                        farmacoSeleccionado.getNombre(), dosis, frecuencia);
                txtTratamiento.setText(txtTratamiento.getText() + "\n" + descripcionFarmaco);
            }
        }
    }

    private void guardarRegistro(ActionEvent e) {
        try {
            LocalDate fecha = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String diagnostico = txtDiagnostico.getText();
            String tratamiento = txtTratamiento.getText();
            String tipoIntervencion = (String) comboTipoIntervencion.getSelectedItem();

            if (historialMedicoActual == null) {
                historialMedicoActual = new HistorialMedico(idMascota, fecha, diagnostico, tratamiento, tipoIntervencion);
                if (historialMedicoDAO.insertarHistorial(historialMedicoActual)) {
                    confirmado = true;
                    setVisible(false);
                }
            } else {
                historialMedicoActual.setFecha(fecha);
                historialMedicoActual.setDiagnostico(diagnostico);
                historialMedicoActual.setTratamiento(tratamiento);
                historialMedicoActual.setTipoIntervencion(tipoIntervencion);
                if (historialMedicoDAO.actualizarHistorial(historialMedicoActual)) {
                    confirmado = true;
                    setVisible(false);
                }
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Utilice dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    private void cargarDatosHistorial(HistorialMedico historial) {
        if (historial != null) {
            dateChooser.setDate(Date.from(historial.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            txtDiagnostico.setText(historial.getDiagnostico());
            txtTratamiento.setText(historial.getTratamiento());
            comboTipoIntervencion.setSelectedItem(historial.getTipoIntervencion());
        }
    }
}
