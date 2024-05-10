package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import DB.VeterinarioDAO;
import model.Veterinario;
import UISwing.recursos.RoundedPanel;

public class VentanaModificarVeterinarioDialog extends JDialog {

    private JTextField txtNombre, txtApellidos, txtLicencia, txtTelefono, txtEmail, txtEspecialidades, txtHorarioTrabajo;
    private JDateChooser dateChooserContratacion;
    private JButton btnSave, btnCancel, btnDelete;
    private Veterinario veterinarioActual;
    private VeterinarioDAO veterinarioDAO;
    private RoundedPanel roundedPanel;

    public VentanaModificarVeterinarioDialog(Frame owner, boolean modal, Veterinario veterinario) {
        super(owner, modal);
        this.veterinarioActual = veterinario;
        setTitle("Modificar/Eliminar Veterinario");
        setUndecorated(true);
        setSize(new Dimension(888, 450));
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        veterinarioDAO = new VeterinarioDAO();
        initComponents();
        cargarDatosVeterinario();
    }

    private void initComponents() {
        roundedPanel = new RoundedPanel(30, Color.decode("#4C59CC"));
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundedPanel.setOpaque(false);
        roundedPanel.setBounds(0, 0, 888, 450);
        getContentPane().add(roundedPanel);
        roundedPanel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(60, 29, 100, 30);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(Color.WHITE);
        roundedPanel.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtNombre.setBounds(60, 61, 200, 35);
        roundedPanel.add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(323, 29, 100, 30);
        lblApellidos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblApellidos.setForeground(Color.WHITE);
        roundedPanel.add(lblApellidos);
        txtApellidos = new JTextField();
        txtApellidos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtApellidos.setBounds(323, 61, 232, 35);
        roundedPanel.add(txtApellidos);

        JLabel lblLicencia = new JLabel("Licencia:");
        lblLicencia.setBounds(323, 144, 100, 30);
        lblLicencia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLicencia.setForeground(Color.WHITE);
        roundedPanel.add(lblLicencia);
        txtLicencia = new JTextField();
        txtLicencia.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtLicencia.setBounds(323, 174, 232, 35);
        roundedPanel.add(txtLicencia);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(617, 29, 100, 30);
        lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTelefono.setForeground(Color.WHITE);
        roundedPanel.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtTelefono.setBounds(617, 61, 200, 35);
        roundedPanel.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(60, 144, 100, 30);
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEmail.setForeground(Color.WHITE);
        roundedPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtEmail.setBounds(60, 174, 200, 35);
        roundedPanel.add(txtEmail);

        JLabel lblEspecialidades = new JLabel("Especialidades:");
        lblEspecialidades.setBounds(617, 259, 110, 30);
        lblEspecialidades.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEspecialidades.setForeground(Color.WHITE);
        roundedPanel.add(lblEspecialidades);
        txtEspecialidades = new JTextField();
        txtEspecialidades.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtEspecialidades.setBounds(617, 287, 200, 35);
        roundedPanel.add(txtEspecialidades);

        JLabel lblHorarioTrabajo = new JLabel("Horario de trabajo:");
        lblHorarioTrabajo.setBounds(63, 259, 197, 30);
        lblHorarioTrabajo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHorarioTrabajo.setForeground(Color.WHITE);
        roundedPanel.add(lblHorarioTrabajo);
        txtHorarioTrabajo = new JTextField();
        txtHorarioTrabajo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        txtHorarioTrabajo.setBounds(60, 288, 495, 35);
        roundedPanel.add(txtHorarioTrabajo);

        JLabel lblFechaContratacion = new JLabel("Fecha de contratación:");
        lblFechaContratacion.setBounds(617, 144, 165, 30);
        lblFechaContratacion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFechaContratacion.setForeground(Color.WHITE);
        roundedPanel.add(lblFechaContratacion);
        dateChooserContratacion = new JDateChooser();
        dateChooserContratacion.setBounds(617, 174, 200, 35);
        dateChooserContratacion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        dateChooserContratacion.setDateFormatString("dd/MM/yyyy");
        roundedPanel.add(dateChooserContratacion);

        btnSave = new JButton("Guardar Cambios");
        btnSave.setBounds(667, 376, 150, 30);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSave.setBackground(Color.WHITE);
        btnSave.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnSave.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnSave.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnSave.setContentAreaFilled(false); 
        btnSave.setOpaque(true);
        btnSave.setRolloverEnabled(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(Color.decode("#003366"));
                btnSave.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(Color.WHITE); 
                btnSave.setForeground(Color.decode("#0057FF"));
            }
        });
        btnSave.addActionListener(this::guardarCambios);
        roundedPanel.add(btnSave);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(60, 376, 145, 30);
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnCancel.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnCancel.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnCancel.setContentAreaFilled(false); 
        btnCancel.setOpaque(true);
        btnCancel.setRolloverEnabled(true);
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(Color.decode("#003366"));
                btnCancel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(Color.WHITE);
                btnCancel.setForeground(Color.decode("#0057FF"));
            }
        });
        btnCancel.addActionListener(e -> dispose());
        roundedPanel.add(btnCancel);

        btnDelete = new JButton("Eliminar Veterinario");
        btnDelete.setBounds(458, 376, 154, 30);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnDelete.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnDelete.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnDelete.setContentAreaFilled(false); 
        btnDelete.setOpaque(true);
        btnDelete.setRolloverEnabled(true);
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDelete.setBackground(Color.decode("#003366"));
                btnDelete.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDelete.setBackground(Color.WHITE);
                btnDelete.setForeground(Color.decode("#0057FF"));
            }
        });
        btnDelete.addActionListener(this::eliminarVeterinario);
        roundedPanel.add(btnDelete);
        
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.6f));  
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  
                g2.dispose();
            }
        };
        centerPanel.setBackground(new Color(255, 255, 255, 70));  
        centerPanel.setOpaque(false);
        centerPanel.setBounds(32, 29, 822, 395); 
        roundedPanel.add(centerPanel);

        
    }

    private void cargarDatosVeterinario() {
        if (veterinarioActual != null) {
            txtNombre.setText(veterinarioActual.getNombre());
            txtApellidos.setText(veterinarioActual.getApellidos());
            txtLicencia.setText(veterinarioActual.getLicencia());
            txtTelefono.setText(veterinarioActual.getTelefono());
            txtEmail.setText(veterinarioActual.getEmail());
            txtEspecialidades.setText(veterinarioActual.getEspecialidades());
            txtHorarioTrabajo.setText(veterinarioActual.getHorarioTrabajo());
            dateChooserContratacion.setDate(veterinarioActual.getFechaContratacion());
        }
    }

    private void guardarCambios(ActionEvent e) {
        try {
            veterinarioActual.setNombre(txtNombre.getText());
            veterinarioActual.setApellidos(txtApellidos.getText());
            veterinarioActual.setLicencia(txtLicencia.getText());
            veterinarioActual.setTelefono(txtTelefono.getText());
            veterinarioActual.setEmail(txtEmail.getText());
            veterinarioActual.setEspecialidades(txtEspecialidades.getText());
            veterinarioActual.setHorarioTrabajo(txtHorarioTrabajo.getText());
            Date fecha = dateChooserContratacion.getDate();
            if (fecha != null) {
                veterinarioActual.setFechaContratacion(new java.sql.Date(fecha.getTime()));
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de contratación válida.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (veterinarioDAO.actualizarVeterinario(veterinarioActual)) {
                JOptionPane.showMessageDialog(this, "Veterinario actualizado con éxito.");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar los datos del veterinario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor, asegúrese de que todos los campos están correctamente llenados.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVeterinario(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este veterinario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (veterinarioDAO.eliminarVeterinario(veterinarioActual.getId())) {
                JOptionPane.showMessageDialog(this, "Veterinario eliminado con éxito.");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el veterinario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
