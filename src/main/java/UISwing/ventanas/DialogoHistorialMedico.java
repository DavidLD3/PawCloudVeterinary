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
        private JDateChooser dateChooser; // Cambia JTextField por JDateChooser
        private JTextArea txtDiagnostico;
        private JTextArea txtTratamiento;
        private boolean confirmado = false;
        private HistorialMedicoDAO historialMedicoDAO;
        private HistorialMedico historialMedicoActual;
        public DialogoHistorialMedico(Frame owner, boolean modal, HistorialMedico historialMedico) {
            super(owner, modal);
            this.historialMedicoActual = historialMedico; // Cambia `this.historialMedico` a `this.historialMedicoActual`
            this.historialMedicoDAO = new HistorialMedicoDAO(); // Asegúrate de inicializar el DAO si aún no está hecho.
            inicializarComponentes();
            cargarDatosHistorial(historialMedico);
        }
        private void inicializarComponentes() {
            setTitle("Historial Médico");
            setLayout(new BorderLayout());

            // Panel de campos
            JPanel panelCampos = new JPanel(new GridLayout(0, 2));
            dateChooser = new JDateChooser(); // Crea el JDateChooser
            dateChooser.setDateFormatString("dd/MM/yyyy"); // Establece el formato de la fecha
            dateChooser.setDate(new Date()); // Establece la fecha actual como predeterminada

            txtDiagnostico = new JTextArea(5, 20);
            txtTratamiento = new JTextArea(5, 20);
            JScrollPane scrollDiagnostico = new JScrollPane(txtDiagnostico);
            JScrollPane scrollTratamiento = new JScrollPane(txtTratamiento);

            panelCampos.add(new JLabel("Fecha:"));
            panelCampos.add(dateChooser); // Agrega el JDateChooser al panel
            panelCampos.add(new JLabel("Diagnóstico:"));
            panelCampos.add(scrollDiagnostico);
            panelCampos.add(new JLabel("Tratamiento:"));
            panelCampos.add(scrollTratamiento);

            // Panel de botones
            JPanel panelBotones = new JPanel();
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");

            btnGuardar.addActionListener(e -> {
                confirmado = true;
                if (guardarRegistro()) {
                    setVisible(false);
                }
            });

            btnCancelar.addActionListener(e -> setVisible(false));

            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);

            add(panelCampos, BorderLayout.CENTER);
            add(panelBotones, BorderLayout.SOUTH);

            pack();
        }
        
    private boolean guardarRegistro() {
    	 try {
             LocalDate fecha = LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault()); // Obtiene la fecha del JDateChooser
             String diagnostico = txtDiagnostico.getText();
             String tratamiento = txtTratamiento.getText();

            if (historialMedicoActual == null) {
                // Supongamos que necesitamos el ID de la mascota para crear un nuevo registro.
                // Deberías tener una manera de obtener este ID (por ejemplo, pasándolo al constructor del diálogo).
                int idMascota = 1; // Este valor debe ser obtenido adecuadamente
                historialMedicoActual = new HistorialMedico(idMascota, fecha, diagnostico, tratamiento);
                return historialMedicoDAO.insertarHistorial(historialMedicoActual);
            } else {
                historialMedicoActual.setFecha(fecha);
                historialMedicoActual.setDiagnostico(diagnostico);
                historialMedicoActual.setTratamiento(tratamiento);
                return historialMedicoDAO.actualizarHistorial(historialMedicoActual);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Utilice dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    // Métodos para cargar datos en caso de edición
    private void cargarDatosHistorial(HistorialMedico historial) {
        if (historial != null) {
            dateChooser.setDate(Date.from(historial.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            txtDiagnostico.setText(historial.getDiagnostico());
            txtTratamiento.setText(historial.getTratamiento());
        } else {
            // Establece valores predeterminados para un nuevo registro
            dateChooser.setDate(new Date());
            txtDiagnostico.setText("");
            txtTratamiento.setText("");
        }
    }
 }


