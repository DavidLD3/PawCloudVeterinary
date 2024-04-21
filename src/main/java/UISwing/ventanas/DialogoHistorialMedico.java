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

import DB.FarmacoDAO;
import DB.HistorialMedicoDAO;
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

	    // Mejorar la disposición del botón y el campo de tratamiento
	    JPanel panelBotonesTratamiento = new JPanel(new BorderLayout());
	    panelBotonesTratamiento.add(scrollTratamiento, BorderLayout.CENTER);  // Campo de texto en el centro
	    JButton btnSeleccionarFarmaco = new JButton("Añadir Fármaco");
	    btnSeleccionarFarmaco.addActionListener(this::abrirDialogoSeleccionFarmaco);
	    panelBotonesTratamiento.add(btnSeleccionarFarmaco, BorderLayout.SOUTH); // Botón debajo del campo de texto

	    // Agregar este panel modificado
	    panelCampos.add(panelBotonesTratamiento);

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
        
    private void abrirDialogoSeleccionFarmaco(ActionEvent e) {
        // Crear una instancia del diálogo con referencia a this como owner y modalidad
        DialogoSeleccionFarmaco dialogoFarmaco = new DialogoSeleccionFarmaco((Frame) SwingUtilities.getWindowAncestor(this), true, new FarmacoDAO());
        dialogoFarmaco.setVisible(true);

        // Comprobar si el diálogo fue confirmado
        if (dialogoFarmaco.isSeleccionado()) {
            // Obtener la información del fármaco seleccionado y los detalles proporcionados
            Farmaco farmacoSeleccionado = dialogoFarmaco.getFarmacoSeleccionado();
            String dosis = dialogoFarmaco.getDosis();
            String frecuencia = dialogoFarmaco.getFrecuencia();
            
            // Formatear la descripción del tratamiento con la información del fármaco
            if (farmacoSeleccionado != null) {
                String descripcionFarmaco = String.format("Fármaco: %s, Dosis: %s, Frecuencia: %s",
                                                          farmacoSeleccionado.getNombre(), dosis, frecuencia);
                // Añadir esta descripción al campo de texto de tratamiento existente
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
