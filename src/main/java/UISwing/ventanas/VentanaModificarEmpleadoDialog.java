package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import DB.EmpleadoDAO;
import model.Empleado;
import UISwing.recursos.RoundedPanel;

public class VentanaModificarEmpleadoDialog extends JDialog {

    private JTextField txtNombre, txtApellidos, txtDNI, txtTelefono, txtEmail, txtHorarioTrabajo;
    private JDateChooser dateChooserContratacion;
    private JButton btnSave, btnCancel, btnDelete;
    private Empleado empleadoActual;
    private EmpleadoDAO empleadoDAO;
    private RoundedPanel roundedPanel;

    public VentanaModificarEmpleadoDialog(Frame owner, boolean modal, Empleado empleado) {
        super(owner, modal);
        this.empleadoActual = empleado;
        setTitle("Modificar/Eliminar Empleado");
        setUndecorated(true);
        setSize(new Dimension(888, 399));
        setLocationRelativeTo(null);
        empleadoDAO = new EmpleadoDAO();
        initComponents();
        cargarDatosEmpleado();
    }
    private void initComponents() {
        roundedPanel = new RoundedPanel(30, Color.decode("#7E88E2"));
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundedPanel.setOpaque(false);
        getContentPane().add(roundedPanel);
        roundedPanel.setLayout(null);

        JLabel label = new JLabel("Nombre:");
        label.setBounds(53, 23, 282, 38);
        roundedPanel.add(label);
        txtNombre = new JTextField(20);
        txtNombre.setBounds(53, 61, 156, 35);
        roundedPanel.add(txtNombre);

        JLabel label_1 = new JLabel("Apellidos:");
        label_1.setBounds(258, 23, 282, 38);
        roundedPanel.add(label_1);
        txtApellidos = new JTextField(20);
        txtApellidos.setBounds(258, 59, 282, 35);
        roundedPanel.add(txtApellidos);

        JLabel label_2 = new JLabel("DNI:");
        label_2.setBounds(607, 23, 223, 38);
        roundedPanel.add(label_2);
        txtDNI = new JTextField(20);
        txtDNI.setBounds(607, 61, 223, 35);
        roundedPanel.add(txtDNI);

        JLabel label_3 = new JLabel("Teléfono:");
        label_3.setBounds(53, 120, 156, 38);
        roundedPanel.add(label_3);
        txtTelefono = new JTextField(20);
        txtTelefono.setBounds(53, 160, 156, 35);
        roundedPanel.add(txtTelefono);

        JLabel label_4 = new JLabel("Email:");
        label_4.setBounds(258, 120, 282, 38);
        roundedPanel.add(label_4);
        txtEmail = new JTextField(20);
        txtEmail.setBounds(258, 160, 282, 35);
        roundedPanel.add(txtEmail);

        JLabel label_5 = new JLabel("Horario de trabajo:");
        label_5.setBounds(53, 218, 110, 38);
        roundedPanel.add(label_5);
        txtHorarioTrabajo = new JTextField(20);
        txtHorarioTrabajo.setBounds(53, 254, 777, 48);
        roundedPanel.add(txtHorarioTrabajo);

        JLabel label_6 = new JLabel("Fecha de contratación:");
        label_6.setBounds(607, 120, 146, 38);
        roundedPanel.add(label_6);
        dateChooserContratacion = new JDateChooser();
        dateChooserContratacion.setBounds(607, 160, 223, 35);
        roundedPanel.add(dateChooserContratacion);

        btnSave = new JButton("Guardar Cambios");
        btnSave.setBounds(691, 339, 139, 30);
        btnSave.addActionListener(this::guardarCambios);  // Correct method reference
        roundedPanel.add(btnSave);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(53, 339, 120, 30);
        btnCancel.addActionListener(e -> setVisible(false));
        roundedPanel.add(btnCancel);

        btnDelete = new JButton("Eliminar Empleado");
        btnDelete.setBounds(500, 339, 139, 30);
        btnDelete.addActionListener(this::eliminarEmpleado);  // Correct method reference
        roundedPanel.add(btnDelete);
    }

    private void cargarDatosEmpleado() {
        if (empleadoActual != null) {
            txtNombre.setText(empleadoActual.getNombre());
            txtApellidos.setText(empleadoActual.getApellidos());
            txtDNI.setText(empleadoActual.getDni());
            txtTelefono.setText(empleadoActual.getTelefono());
            txtEmail.setText(empleadoActual.getEmail());
            txtHorarioTrabajo.setText(empleadoActual.getHorarioTrabajo());
            // Usar directamente el Date sin conversiones de LocalDate
            dateChooserContratacion.setDate(empleadoActual.getFechaContratacion());
        }
    }


    private void guardarCambios(ActionEvent e) {
        try {
            empleadoActual.setNombre(txtNombre.getText());
            empleadoActual.setApellidos(txtApellidos.getText());
            empleadoActual.setDni(txtDNI.getText());
            empleadoActual.setTelefono(txtTelefono.getText());
            empleadoActual.setEmail(txtEmail.getText());
            empleadoActual.setHorarioTrabajo(txtHorarioTrabajo.getText());
            // Obtener directamente la fecha de JDateChooser y asegurarse que no sea null
            Date fecha = dateChooserContratacion.getDate();
            if (fecha != null) {
                empleadoActual.setFechaContratacion(fecha);
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de contratación válida.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (empleadoDAO.actualizarEmpleado(empleadoActual)) {
                JOptionPane.showMessageDialog(this, "Empleado actualizado con éxito.");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar los datos del empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor, asegúrese de que todos los campos están correctamente llenados.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void eliminarEmpleado(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este empleado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (empleadoDAO.eliminarEmpleado(empleadoActual.getId())) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado con éxito.");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
