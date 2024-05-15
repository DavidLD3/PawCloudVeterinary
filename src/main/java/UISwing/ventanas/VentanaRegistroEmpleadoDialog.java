package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import DB.EmpleadoDAO;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import UISwing.recursos.RoundedPanel;
import model.Empleado; 

public class VentanaRegistroEmpleadoDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private RoundedPanel roundedPanel;
    private JTextField txtNombre, txtApellidos, txtDNI, txtTelefono, txtEmail,txtHorarioTrabajo;
    private JButton saveButton, cancelButton;
    private JDateChooser dateChooserContratacion;

    public VentanaRegistroEmpleadoDialog(Frame owner, boolean modal, PanelAdministracion panel) {
        super(owner, modal);
        setTitle("Registro de Empleado");
        setUndecorated(true);
        setSize(new Dimension(500, 400));
        setLocationRelativeTo(null);

        roundedPanel = new RoundedPanel(30, Color.decode("#4D94FF"));
        roundedPanel.setLayout(null);
        roundedPanel.setBounds(0, 0, 500, 400);
        roundedPanel.setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        getContentPane().add(roundedPanel);
        initComponents();
    }

    private void initComponents() {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setBounds(45, 30, 100, 25);
        roundedPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(45, 55, 200, 25);
        roundedPanel.add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setForeground(Color.WHITE);
        lblApellidos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblApellidos.setBounds(255, 30, 100, 25);
        roundedPanel.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(255, 55, 200, 25);
        roundedPanel.add(txtApellidos);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setForeground(Color.WHITE);
        lblDNI.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDNI.setBounds(45, 90, 100, 25);
        roundedPanel.add(lblDNI);

        txtDNI = new JTextField();
        txtDNI.setBounds(45, 115, 200, 25);
        roundedPanel.add(txtDNI);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(Color.WHITE);
        lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTelefono.setBounds(255, 90, 100, 25);
        roundedPanel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(255, 115, 200, 25);
        roundedPanel.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setBounds(45, 150, 100, 25);
        roundedPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(45, 175, 410, 25);
        roundedPanel.add(txtEmail);
        
        JLabel lblHorarioTrabajo = new JLabel("Horario de trabajo:");
        lblHorarioTrabajo.setForeground(Color.WHITE);
        lblHorarioTrabajo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHorarioTrabajo.setBounds(45, 256, 140, 25);
        roundedPanel.add(lblHorarioTrabajo);

        txtHorarioTrabajo = new JTextField(); 
        txtHorarioTrabajo.setBounds(45, 282, 408, 26);
        roundedPanel.add(txtHorarioTrabajo);
		
        JLabel lblFechaContratacion = new JLabel("Fecha de contratación:");
        lblFechaContratacion.setForeground(Color.WHITE);
        lblFechaContratacion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaContratacion.setBounds(45, 210, 180, 25);
        roundedPanel.add(lblFechaContratacion);

        dateChooserContratacion = new JDateChooser();
        dateChooserContratacion.setDateFormatString("d/M/yyyy");
        dateChooserContratacion.setBounds(225, 210, 230, 25);
        roundedPanel.add(dateChooserContratacion);
        
        
        JPanel centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
																			
				g2.dispose();
				super.paintComponent(g);
			}
		};
		centerPanel.setBackground(new Color(255, 255, 255, 80));
		centerPanel.setOpaque(false);
										
		centerPanel.setBounds(21, 21, 457, 360);
		roundedPanel.add(centerPanel);

        saveButton = new JButton("Guardar");
        saveButton.setBounds(353, 338, 100, 30);
        initButton(saveButton, "#0057FF", "#003366");
        roundedPanel.add(saveButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(216, 339, 100, 30);
        initButton(cancelButton, "#0057FF", "#003366");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEmpleado();
                dispose();
            }
        });
        cancelButton.addActionListener(e -> dispose());
        roundedPanel.add(cancelButton);
    }

    private void initButton(JButton button, String colorHex, String rolloverColorHex) {
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.decode(colorHex));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setRolloverEnabled(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.decode(rolloverColorHex));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.decode(colorHex));
            }
        });

        
    }

    private void guardarEmpleado() {
       
        if (dateChooserContratacion.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de contratación.", "Fecha de Contratación Requerida", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        Empleado empleado = new Empleado();
        empleado.setNombre(txtNombre.getText());
        empleado.setApellidos(txtApellidos.getText());
        empleado.setDni(txtDNI.getText());
        empleado.setTelefono(txtTelefono.getText());
        empleado.setEmail(txtEmail.getText());
        empleado.setHorarioTrabajo(txtHorarioTrabajo.getText());
        empleado.setFechaContratacion(new java.sql.Date(dateChooserContratacion.getDate().getTime()));

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        boolean result = empleadoDAO.insertarEmpleado(empleado);
        if (result) {
            JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el empleado.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDNI.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtHorarioTrabajo.setText("");
        dateChooserContratacion.setDate(null);
    }


    
}
