package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import DB.EmpleadoDAO;
import model.Empleado;
import UISwing.recursos.CustomRoundedBorder;
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
        setBackground(new Color(0, 0, 0, 0));
        empleadoDAO = new EmpleadoDAO();
        initComponents();
        cargarDatosEmpleado();
    }
    private void initComponents() {
    	
    	roundedPanel = new RoundedPanel(30, Color.decode("#3199FF"));
        roundedPanel.setBorder(new CustomRoundedBorder(Color.decode("#1C3E96"), 17, 1));
        roundedPanel.setOpaque(false);
        roundedPanel.setLayout(null);
        getContentPane().add(roundedPanel);

        JLabel label = new JLabel("Nombre:");
        label.setBounds(53, 23, 282, 38);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        roundedPanel.add(label);
        txtNombre = new JTextField(20);
        txtNombre.setBounds(53, 61, 156, 35);
        txtNombre.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtNombre);

        JLabel label_1 = new JLabel("Apellidos:");
        label_1.setBounds(258, 23, 282, 38);
        label_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_1.setForeground(Color.WHITE);
        roundedPanel.add(label_1);
        txtApellidos = new JTextField(20);
        txtApellidos.setBounds(258, 59, 282, 35);
        txtApellidos.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtApellidos);

        JLabel label_2 = new JLabel("DNI:");
        label_2.setBounds(607, 23, 223, 38);
        label_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_2.setForeground(Color.WHITE);
        roundedPanel.add(label_2);
        txtDNI = new JTextField(20);
        txtDNI.setBounds(607, 61, 223, 35);
        txtDNI.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtDNI);

        JLabel label_3 = new JLabel("Teléfono:");
        label_3.setBounds(53, 120, 156, 38);
        label_3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_3.setForeground(Color.WHITE);
        roundedPanel.add(label_3);
        txtTelefono = new JTextField(20);
        txtTelefono.setBounds(53, 160, 156, 35);
        txtTelefono.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtTelefono);

        JLabel label_4 = new JLabel("Email:");
        label_4.setBounds(258, 120, 282, 38);
        label_4.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_4.setForeground(Color.WHITE);
        roundedPanel.add(label_4);
        txtEmail = new JTextField(20);
        txtEmail.setBounds(258, 160, 282, 35);
        txtEmail.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtEmail);

        JLabel label_5 = new JLabel("Horario de trabajo:");
        label_5.setBounds(53, 214, 110, 38);
        label_5.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_5.setForeground(Color.WHITE);
        roundedPanel.add(label_5);
        txtHorarioTrabajo = new JTextField(20);
        txtHorarioTrabajo.setBounds(53, 250, 777, 48);
        txtHorarioTrabajo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(txtHorarioTrabajo);

        JLabel label_6 = new JLabel("Fecha de contratación:");
        label_6.setBounds(607, 120, 146, 38);
        label_6.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label_6.setForeground(Color.WHITE);
        roundedPanel.add(label_6);
        dateChooserContratacion = new JDateChooser();
        dateChooserContratacion.setBounds(607, 160, 223, 35);
        dateChooserContratacion.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 11));
        roundedPanel.add(dateChooserContratacion);

        btnSave = new JButton("Guardar Cambios");
        btnSave.setBounds(691, 328, 139, 30);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSave.setBackground(Color.WHITE);
        btnSave.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnSave.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnSave.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnSave.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnSave.setOpaque(true);
        btnSave.setRolloverEnabled(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnSave.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnSave.setForeground(Color.decode("#0057FF"));
            }
        });
        btnSave.addActionListener(this::guardarCambios);  // Correct method reference
        roundedPanel.add(btnSave);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(53, 328, 139, 30);
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnCancel.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnCancel.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnCancel.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnCancel.setOpaque(true);
        btnCancel.setRolloverEnabled(true);
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnCancel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnCancel.setForeground(Color.decode("#0057FF"));
            }
        });
        btnCancel.addActionListener(e -> setVisible(false));
        roundedPanel.add(btnCancel);

        btnDelete = new JButton("Eliminar Empleado");
        btnDelete.setBounds(499, 328, 139, 30);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnDelete.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnDelete.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnDelete.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnDelete.setOpaque(true);
        btnDelete.setRolloverEnabled(true);
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDelete.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnDelete.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDelete.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnDelete.setForeground(Color.decode("#0057FF"));
            }
        });
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
