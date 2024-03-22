package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;

import DB.ClienteDAO;
import model.Cliente;

public class PanelRegistroCliente extends JPanel {
    private JTextField tfRcliente_Nombre, tfRcliente_Apellidos, 
    tfRcliente_DNI, tfRcliente_NIF, tfRcliente_Direccion, tfRcliente_Poblacion,
    tfRcliente_Provincia, tfRcliente_Tfijo, tfRcliente_Tmovil, tfRcliente_Email;
    private JTextComponent tfRcliente_Fnacimiento;
    private JDateChooser dateChooser;

    public PanelRegistroCliente() {
        super(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        setBackground(new Color(134, 206, 249));

        add(crearPanelClientes(), BorderLayout.CENTER); // Agregar panelClientes al centro

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel();
        add(panelPrincipal, BorderLayout.NORTH);
        panelPrincipal.setLayout(null);
    }

    private JPanel crearPanelBotones() {
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar todos los campos?", "Eliminar", JOptionPane.YES_NO_OPTION);
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
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere cerrar sin guardar?", "Cerrar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);

        return panelBotones;
    }
    
    private void limpiarCampos() {
        tfRcliente_Nombre.setText("");
        tfRcliente_Apellidos.setText("");
        tfRcliente_Fnacimiento.setText("");
        tfRcliente_DNI.setText("");
        tfRcliente_NIF.setText("");
        tfRcliente_Direccion.setText("");
        tfRcliente_Poblacion.setText("");
        tfRcliente_Provincia.setText("");
        tfRcliente_Tfijo.setText("");
        tfRcliente_Tmovil.setText("");
        tfRcliente_Email.setText("");
    }
    
    private void guardarDatos() {
        LocalDate fechaNacimiento = null;
        if (dateChooser.getDate() != null) {
            fechaNacimiento = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        
        Cliente cliente = new Cliente(
        	    0,
        	    tfRcliente_Nombre.getText(),
        	    tfRcliente_Apellidos.getText(),
        	    fechaNacimiento,
        	    tfRcliente_DNI.getText(),
        	    tfRcliente_NIF.getText(),
        	    tfRcliente_Direccion.getText(),
        	    tfRcliente_Poblacion.getText(),
        	    tfRcliente_Provincia.getText(),
        	    tfRcliente_Tfijo.getText(),
        	    tfRcliente_Tmovil.getText(),
        	    tfRcliente_Email.getText()
        	);
        
        ClienteDAO clienteDao = new ClienteDAO();
        boolean exito = clienteDao.insertarCliente(cliente);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente guardado con éxito");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente");
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
			
		    JPanel Pcliente = new JPanel();
		    Pcliente.setLayout(null);
		    Pcliente.setBackground(new Color(255, 255, 255));

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
		    lbRCliente_NIF.setBounds(609, 95, 46, 14);
		    Pcliente.add(lbRCliente_NIF);
		   
		    tfRcliente_NIF.setBounds(602, 120, 86, 20);
		    Pcliente.add(tfRcliente_NIF);

		    // DNI
		    JLabel lbRCliente_DNI = new JLabel("DNI");
		    lbRCliente_DNI.setBounds(416, 95, 46, 14);
		    Pcliente.add(lbRCliente_DNI);
		
		    tfRcliente_DNI.setBounds(416, 120, 86, 20);
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
		    panel.setBounds(0, 0, 744, 20);
		    Pcliente.add(panel);
		    panel.setLayout(null);
		    
		    JLabel lbRegistroCliente = new JLabel("Registro Cliente");
		    lbRegistroCliente.setBounds(10, 0, 125, 20);
		    panel.add(lbRegistroCliente);

		    return Pcliente;
		
	}
	
	
	
	
	
		public static void main(String[] args) {
	        JFrame frame = new JFrame("Registro Cliente");
	        PanelRegistroCliente panelRegistro = new PanelRegistroCliente();

	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(800, 600); // Ajusta el tamaño según necesites
	        frame.getContentPane().add(panelRegistro); // Agrega el panel al frame
	        frame.setVisible(true); // Hace visible el frame
	    }
	}
	



