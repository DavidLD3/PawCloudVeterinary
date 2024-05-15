package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;
import DB.ClienteDAO;
import model.Cliente;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.BorderFactory;

class RoundedPanel extends JPanel {
    private int radius;
    private Color backgroundColor;

    public RoundedPanel(int radius, Color backgroundColor) {
        super();
        this.radius = radius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            graphics.setColor(getBackground());
        }
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}

public class PanelRegistroCliente extends JDialog {
    private JTextField tfRcliente_Nombre, tfRcliente_Apellidos,
            tfRcliente_DNI, tfRcliente_NIF, tfRcliente_Direccion, tfRcliente_Poblacion,
            tfRcliente_Provincia, tfRcliente_Tfijo, tfRcliente_Tmovil, tfRcliente_Email;
    private JTextComponent tfRcliente_Fnacimiento;
    private JDateChooser dateChooser;

    public PanelRegistroCliente(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setUndecorated(true);
        getContentPane().setBackground(new Color(147, 112, 219));
        setModal(true);
        initializeUI();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(620, 420);
        this.setLocationRelativeTo(null);
    }

    private void initializeUI() {
        
        RoundedPanel mainPanel = new RoundedPanel(30, new Color(147, 112, 219));
        mainPanel.setLayout(null);

        
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
        centerPanel.setBackground(new Color(255, 255, 255, 70)); 
        centerPanel.setOpaque(false); 
        centerPanel.setBounds(15, 15, 570, 394);
        centerPanel.setLayout(null);

        
        JPanel panelClientes = crearPanelClientes();
        panelClientes.setBounds(10, 10, 548, 381);
        centerPanel.add(panelClientes);

        JPanel panelBotones = crearPanelBotones();
        panelBotones.setBounds(22, 341, 522, 50);
        centerPanel.add(panelBotones);

        mainPanel.add(centerPanel);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(57, 363, 87, 23);
        mainPanel.add(btnCerrar);
        initButton(btnCerrar, "#0057FF", "#003366");
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(343, 363, 87, 23);
        mainPanel.add(btnLimpiar);
        
                
                initButton(btnLimpiar, "#0057FF", "#003366");
                JButton btnGuardar = new JButton("Guardar");
                btnGuardar.setBounds(463, 364, 87, 23);
                mainPanel.add(btnGuardar);
                initButton(btnGuardar, "#0057FF", "#003366");
                
                        btnGuardar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                guardarDatos();
                            }
                        });
                
                        btnLimpiar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere limpiar todos los campos?", "Limpiar", JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    limpiarCampos();
                                }
                            }
                        });
        
                btnCerrar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        JPanel confirmPanel = new JPanel();
                        confirmPanel.add(new JLabel("¿Está seguro de que quiere cerrar sin guardar?"));
        
                       
                        String[] options = {"Sí", "No"};
        
                        
                        int confirm = JOptionPane.showOptionDialog(null, confirmPanel, "Cerrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (confirm == JOptionPane.YES_OPTION) {
                            dispose();
                        }
                    }
                });
    }

    private JPanel crearPanelBotones() {

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(147, 112, 219));
        panelBotones.setOpaque(false);
        panelBotones.setLayout(null);

        return panelBotones;
    }

    // Método para inicializar y configurar los estilos de los botones
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

    private void limpiarCampos() {
        tfRcliente_Nombre.setText("");
        tfRcliente_Apellidos.setText("");
        tfRcliente_DNI.setText("");
        tfRcliente_NIF.setText("");
        tfRcliente_Direccion.setText("");
        tfRcliente_Poblacion.setText("");
        tfRcliente_Provincia.setText("");
        tfRcliente_Tfijo.setText("");
        tfRcliente_Tmovil.setText("");
        tfRcliente_Email.setText("");
        dateChooser.setCalendar(null);
    }

    private void guardarDatos() {
        try {
            LocalDate fechaNacimiento = null;
            if (dateChooser.getDate() != null) {
                fechaNacimiento = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            Cliente cliente = new Cliente(
                    0,
                    tfRcliente_Nombre.getText().trim(),
                    tfRcliente_Apellidos.getText().trim(),
                    fechaNacimiento,
                    tfRcliente_DNI.getText().trim(),
                    tfRcliente_NIF.getText().trim(),
                    tfRcliente_Direccion.getText().trim(),
                    tfRcliente_Poblacion.getText().trim(),
                    tfRcliente_Provincia.getText().trim(),
                    tfRcliente_Tfijo.getText().trim(),
                    tfRcliente_Tmovil.getText().trim(),
                    tfRcliente_Email.getText().trim()
            );

            ClienteDAO clienteDao = new ClienteDAO();
            boolean exito = clienteDao.insertarCliente(cliente);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente guardado con éxito");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + e.getMessage());
        }
    }

    private JPanel crearPanelClientes() {
        tfRcliente_Nombre = new JTextField(10);
        tfRcliente_Apellidos = new JTextField(10);
        tfRcliente_Fnacimiento = new JTextField(10);
        tfRcliente_DNI = new JTextField(10);
        tfRcliente_NIF = new JTextField(10);
        tfRcliente_Direccion = new JTextField(10);
        tfRcliente_Poblacion = new JTextField(10);
        tfRcliente_Provincia = new JTextField(10);
        tfRcliente_Tfijo = new JTextField(10);
        tfRcliente_Tmovil = new JTextField(10);
        tfRcliente_Email = new JTextField(10);

        Border bordeExterior = BorderFactory.createLineBorder(Color.BLACK);
        Border bordeInterior = BorderFactory.createEmptyBorder(30, 30, 30, 30);
        Border bordeCompuesto = new CompoundBorder(bordeExterior, bordeInterior);

        JPanel Pcliente = new JPanel();
        Pcliente.setLayout(null);
        Pcliente.setOpaque(false);

        // Nombre
        JLabel lbRCliente_Nombre = new JLabel("Nombre:");
        lbRCliente_Nombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Nombre.setForeground(new Color(255, 255, 255));
        lbRCliente_Nombre.setBounds(31, 23, 86, 25);
        Pcliente.add(lbRCliente_Nombre);

        tfRcliente_Nombre.setBounds(31, 53, 200, 25);
        Pcliente.add(tfRcliente_Nombre);

        // Apellidos
        JLabel lbRCliente_Apellidos = new JLabel("Apellidos");
        lbRCliente_Apellidos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Apellidos.setForeground(new Color(255, 255, 255));
        lbRCliente_Apellidos.setBounds(324, 23, 195, 25);
        Pcliente.add(lbRCliente_Apellidos);

        tfRcliente_Apellidos.setBounds(324, 53, 200, 25);
        Pcliente.add(tfRcliente_Apellidos);

        // NIF
        JLabel lbRCliente_NIF = new JLabel("NIF");
        lbRCliente_NIF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_NIF.setForeground(new Color(255, 255, 255));
        lbRCliente_NIF.setBounds(427, 89, 92, 25);
        Pcliente.add(lbRCliente_NIF);

        tfRcliente_NIF.setBounds(427, 119, 100, 25);
        Pcliente.add(tfRcliente_NIF);

        // DNI
        JLabel lbRCliente_DNI = new JLabel("DNI:");
        lbRCliente_DNI.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_DNI.setForeground(new Color(255, 255, 255));
        lbRCliente_DNI.setBounds(248, 89, 103, 25);
        Pcliente.add(lbRCliente_DNI);

        tfRcliente_DNI.setBounds(248, 119, 103, 25);
        Pcliente.add(tfRcliente_DNI);

        // Fecha de Nacimiento
        JLabel lbRCliente_Fnacimiento = new JLabel("Fecha de Nacimiento:");
        lbRCliente_Fnacimiento.setForeground(new Color(255, 255, 255));
        lbRCliente_Fnacimiento.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Fnacimiento.setBounds(31, 92, 149, 25);
        Pcliente.add(lbRCliente_Fnacimiento);

        // Inicialización de JDateChooser
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBounds(31, 119, 149, 25);
        Pcliente.add(dateChooser);

        // Dirección
        JLabel lbRCliente_Direccion = new JLabel("Direccion:");
        lbRCliente_Direccion.setForeground(new Color(255, 255, 255));
        lbRCliente_Direccion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Direccion.setBounds(29, 177, 103, 25);
        Pcliente.add(lbRCliente_Direccion);

        tfRcliente_Direccion.setBounds(29, 202, 218, 25);
        Pcliente.add(tfRcliente_Direccion);

        // Población
        JLabel lbRCliente_Poblacion = new JLabel("Poblacion:");
        lbRCliente_Poblacion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Poblacion.setForeground(new Color(255, 255, 255));
        lbRCliente_Poblacion.setBounds(286, 177, 103, 25);
        Pcliente.add(lbRCliente_Poblacion);

        tfRcliente_Poblacion.setBounds(286, 202, 103, 25);
        Pcliente.add(tfRcliente_Poblacion);

        // Provincia
        JLabel lbRCliente_Provincia = new JLabel("Provincia:");
        lbRCliente_Provincia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Provincia.setForeground(new Color(255, 255, 255));
        lbRCliente_Provincia.setBounds(427, 177, 92, 25);
        Pcliente.add(lbRCliente_Provincia);

        tfRcliente_Provincia.setBounds(427, 202, 100, 25);
        Pcliente.add(tfRcliente_Provincia);

        // Teléfono Fijo
        JLabel lbRCliente_Tfijo = new JLabel("Teléfono Fijo:");
        lbRCliente_Tfijo.setForeground(new Color(255, 255, 255));
        lbRCliente_Tfijo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Tfijo.setBounds(31, 258, 86, 25);
        Pcliente.add(lbRCliente_Tfijo);

        tfRcliente_Tfijo.setBounds(31, 284, 86, 25);
        Pcliente.add(tfRcliente_Tfijo);

        // Teléfono Móvil
        JLabel lbRCliente_Tmovil = new JLabel("Teléfono Movil");
        lbRCliente_Tmovil.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_Tmovil.setForeground(new Color(255, 255, 255));
        lbRCliente_Tmovil.setBounds(164, 259, 110, 25);
        Pcliente.add(lbRCliente_Tmovil);

        tfRcliente_Tmovil.setBounds(167, 284, 92, 25);
        Pcliente.add(tfRcliente_Tmovil);

        // Correo Electrónico
        JLabel lbRCliente_email = new JLabel("Correo Electronico");
        lbRCliente_email.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbRCliente_email.setForeground(new Color(255, 255, 255));
        lbRCliente_email.setBounds(330, 259, 120, 24);
        Pcliente.add(lbRCliente_email);

        tfRcliente_Email.setBounds(330, 284, 197, 25);
        Pcliente.add(tfRcliente_Email);

        return Pcliente;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PanelRegistroCliente dialog = new PanelRegistroCliente(null, "Panel de Registro del Cliente", true);
            dialog.setVisible(true);
        });
    }
}
