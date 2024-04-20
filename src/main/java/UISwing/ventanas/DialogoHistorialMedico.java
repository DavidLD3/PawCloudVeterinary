package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser; 
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import DB.HistorialMedicoDAO;
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
	    this.idMascota = idMascota;  // Asegúrate de tener un campo idMascota en la clase DialogoHistorialMedico
	    this.historialMedicoActual = historialMedico;
	    this.historialMedicoDAO = new HistorialMedicoDAO();
	    inicializarComponentes();
	    cargarDatosHistorial(historialMedico);
	}

    private void inicializarComponentes() {
        setTitle("Historial Médico");
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(0, 2));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());

        txtDiagnostico = new JTextArea(5, 20);
        txtTratamiento = new JTextArea(5, 20);
        JScrollPane scrollDiagnostico = new JScrollPane(txtDiagnostico);
        JScrollPane scrollTratamiento = new JScrollPane(txtTratamiento);

        comboTipoIntervencion = new JComboBox<>(new String[]{
            "Evaluación general y anamnesis", "Examen físico", "Vacunación",
            "Desparasitación", "Pruebas de diagnóstico",
            "Asesoramiento nutricional y de comportamiento", "Planificación preventiva",
            "Recomendaciones de cuidado general"
        });

        panelCampos.add(new JLabel("Fecha:"));
        panelCampos.add(dateChooser);
        panelCampos.add(new JLabel("Tipo de intervención:"));
        panelCampos.add(comboTipoIntervencion);
        panelCampos.add(new JLabel("Diagnóstico:"));
        panelCampos.add(scrollDiagnostico);
        panelCampos.add(new JLabel("Tratamiento:"));
        panelCampos.add(scrollTratamiento);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this::guardarRegistro); 
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> setVisible(false));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
    }
        
    private void guardarRegistro(ActionEvent e) {
        try {
            LocalDate fecha = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String diagnostico = txtDiagnostico.getText();
            String tratamiento = txtTratamiento.getText();
            String tipoIntervencion = (String) comboTipoIntervencion.getSelectedItem();

            if (historialMedicoActual == null) {
                // Crear un nuevo registro con el ID de la mascota adecuado
                historialMedicoActual = new HistorialMedico(idMascota, fecha, diagnostico, tratamiento, tipoIntervencion);
                if (historialMedicoDAO.insertarHistorial(historialMedicoActual)) {
                    confirmado = true;
                    setVisible(false);
                }
            } else {
                // Actualizar el registro existente
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
