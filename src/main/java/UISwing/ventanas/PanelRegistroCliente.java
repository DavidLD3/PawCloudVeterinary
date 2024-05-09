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
        this.setSize(578, 393); // Tamaño ajustado según necesidad
        this.setLocationRelativeTo(null);
    }

    private void initializeUI() {
        setBackground(new Color(147, 112, 219)); // Tono azul suave
        getContentPane().setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(147, 112, 219));
        mainPanel.setOpaque(false); // Transparencia
        mainPanel.add(crearPanelClientes(), BorderLayout.CENTER);
        mainPanel.add(crearPanelBotones(), BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel crearPanelBotones() {
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");

        // Aplicar estilos de botones al estilo de VentanaRegistroVeterinarioDialog
        initButton(btnLimpiar, "#0057FF", "#003366");
        initButton(btnGuardar, "#0057FF", "#003366");
        initButton(btnCerrar, "#0057FF", "#003366");

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere limpiar todos los campos?", "Limpiar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    limpiarCampos();
                }
            }
        });


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creación de un JPanel personalizado para el contenido del diálogo
                JPanel confirmPanel = new JPanel();
                confirmPanel.add(new JLabel("¿Está seguro de que quiere cerrar sin guardar?"));

                // Opciones personalizadas para los botones
                String[] options = {"Sí", "No"};

                // Mostrar el diálogo personalizado
                int confirm = JOptionPane.showOptionDialog(null, confirmPanel, "Cerrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // Uso directo de dispose() para cerrar la ventana actual
                }
            }
        });


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(147, 112, 219));
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);

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
        // Resetear campos de texto
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

        // Resetear el componente JDateChooser
        dateChooser.setCalendar(null);  // Esto asegura que la selección de fecha también se limpie
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
			tfRcliente_Fnacimiento = new JTextField(10); // Asegúrate de que este campo se maneje correctamente como una fecha
			tfRcliente_DNI = new JTextField(10);
			tfRcliente_NIF = new JTextField(10);
			tfRcliente_Direccion = new JTextField(10);
			tfRcliente_Poblacion = new JTextField(10);
			tfRcliente_Provincia = new JTextField(10);
			tfRcliente_Tfijo = new JTextField(10);
			tfRcliente_Tmovil = new JTextField(10);
			tfRcliente_Email = new JTextField(10);
			
			Border bordeExterior = BorderFactory.createLineBorder(Color.BLACK); // Puedes cambiar el color y el estilo del borde exterior
		    Border bordeInterior = BorderFactory.createEmptyBorder(30, 30, 30, 30); // Espacio interior para separar los componentes del borde
		    Border bordeCompuesto = new CompoundBorder(bordeExterior, bordeInterior);
		    
			  JPanel Pcliente = new JPanel();
		        Pcliente.setLayout(null);
		        Pcliente.setOpaque(false);

		    // Nombre
		    JLabel lbRCliente_Nombre = new JLabel("Nombre");
		    lbRCliente_Nombre.setBounds(22, 39, 50, 14);
		    Pcliente.add(lbRCliente_Nombre);
		 
		    tfRcliente_Nombre.setBounds(22, 64, 86, 20);
		    Pcliente.add(tfRcliente_Nombre);

		    // Apellidos
		    JLabel lbRCliente_Apellidos = new JLabel("Apellidos");
		    lbRCliente_Apellidos.setBounds(180, 39, 60, 14);
		    Pcliente.add(lbRCliente_Apellidos);
		  
		    tfRcliente_Apellidos.setBounds(189, 64, 218, 20);
		    Pcliente.add(tfRcliente_Apellidos);

		    // NIF
		    JLabel lbRCliente_NIF = new JLabel("NIF");
		    lbRCliente_NIF.setBounds(416, 120, 46, 14);
		    Pcliente.add(lbRCliente_NIF);
		   
		    tfRcliente_NIF.setBounds(416, 136, 86, 20);
		    Pcliente.add(tfRcliente_NIF);

		    // DNI
		    JLabel lbRCliente_DNI = new JLabel("DNI");
		    lbRCliente_DNI.setBounds(283, 120, 46, 14);
		    Pcliente.add(lbRCliente_DNI);
		
		    tfRcliente_DNI.setBounds(283, 136, 86, 20);
		    Pcliente.add(tfRcliente_DNI);

		    // Fecha de Nacimiento
		    JLabel lbRCliente_Fnacimiento = new JLabel("Fecha de Nacimiento");
		    lbRCliente_Fnacimiento.setBounds(22, 104, 130, 14);
		    Pcliente.add(lbRCliente_Fnacimiento);
		    
		    // Inicialización de JDateChooser
		    dateChooser = new JDateChooser();
		    dateChooser.setBounds(22, 136, 120, 20); // Ajusta según sea necesario
		    Pcliente.add(dateChooser);
		    

		    // Dirección
		    JLabel lbRCliente_Direccion = new JLabel("Direccion");
		    lbRCliente_Direccion.setBounds(22, 188, 60, 14);
		    Pcliente.add(lbRCliente_Direccion);

		    tfRcliente_Direccion.setBounds(22, 213, 218, 20);
		    Pcliente.add(tfRcliente_Direccion);

		    // Población
		    JLabel lbRCliente_Poblacion = new JLabel("Poblacion");
		    lbRCliente_Poblacion.setBounds(283, 188, 60, 14);
		    Pcliente.add(lbRCliente_Poblacion);
	
		    tfRcliente_Poblacion.setBounds(283, 213, 86, 20);
		    Pcliente.add(tfRcliente_Poblacion);

		    // Provincia
		    JLabel lbRCliente_Provincia = new JLabel("Provincia");
		    lbRCliente_Provincia.setBounds(442, 188, 60, 14);
		    Pcliente.add(lbRCliente_Provincia);
		 
		    tfRcliente_Provincia.setBounds(442, 213, 86, 20);
		    Pcliente.add(tfRcliente_Provincia);

		    // Teléfono Fijo
		    JLabel lbRCliente_Tfijo = new JLabel("Telefono Fijo");
		    lbRCliente_Tfijo.setBounds(21, 273, 75, 14);
		    Pcliente.add(lbRCliente_Tfijo);
		
		    tfRcliente_Tfijo.setBounds(22, 308, 86, 20);
		    Pcliente.add(tfRcliente_Tfijo);

		    // Teléfono Móvil
		    JLabel lbRCliente_Tmovil = new JLabel("Telefono Movil");
		    lbRCliente_Tmovil.setBounds(158, 273, 85, 14);
		    Pcliente.add(lbRCliente_Tmovil);
		
		    tfRcliente_Tmovil.setBounds(158, 308, 86, 20);
		    Pcliente.add(tfRcliente_Tmovil);

		    // Correo Electrónico
		    JLabel lbRCliente_email = new JLabel("Correo Electronico");
		    lbRCliente_email.setBounds(310, 273, 120, 14);
		    Pcliente.add(lbRCliente_email);
		  
		    tfRcliente_Email.setBounds(310, 308, 186, 20);
		    Pcliente.add(tfRcliente_Email);
		    
		    JPanel panel = new JPanel();
		    panel.setBackground(new Color(147, 112, 219));
		    panel.setBounds(0, 0, 744, 20);
		    Pcliente.add(panel);
		    panel.setLayout(null);
		    
		    JLabel lbRegistroCliente = new JLabel("Registro Cliente");
		    lbRegistroCliente.setBounds(10, 0, 125, 20);
		    panel.add(lbRegistroCliente);

		    return Pcliente;
		
	}
	
	
	
	
		 public static void main(String[] args) {
		        SwingUtilities.invokeLater(() -> {
		            PanelRegistroCliente dialog = new PanelRegistroCliente(null, "Panel de Registro del Cliente", true);
		            dialog.setVisible(true);
		        });
		    }
			
}

