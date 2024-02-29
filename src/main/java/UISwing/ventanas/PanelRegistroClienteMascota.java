package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

import DB.ClienteDAO;
import model.Cliente;



public class PanelRegistroClienteMascota extends JPanel {
	private JTextField tfRcliente_Nombre, tfRcliente_Apellidos, 
    tfRcliente_DNI, tfRcliente_NIF, tfRcliente_Direccion, tfRcliente_Poblacion,
    tfRcliente_Provincia, tfRcliente_Tfijo, tfRcliente_Tmovil, tfRcliente_Email;
	private JTextComponent tfRcliente_Fnacimiento;
	private JDateChooser dateChooser;

private JTabbedPane tabbedPane;
    public PanelRegistroClienteMascota() {
        super(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        this.tabbedPane = new JTabbedPane(); // Inicializar JTabbedPane
        setBackground(new Color(134, 206, 249));

        add(tabbedPane, BorderLayout.CENTER); // Agregar tabbedPane al centro

        crearYAgregarPaneles();

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel();
        add(panelPrincipal, BorderLayout.NORTH);
        panelPrincipal.setLayout(null);
    }

    private void crearYAgregarPaneles() {
        // Ahora agrega paneles al JTabbedPane
        tabbedPane.addTab("Clientes", crearPanelClientes());
        tabbedPane.addTab("Mascotas", crearPanelMascotasTotales());
    }

    private JPanel crearPanelBotones() {
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");

        // Listener para el botón Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar todos los campos?", "Eliminar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    limpiarCampos();
                }
            }
        });

        // Listener para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });

        // Listener para el botón Cerrar
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere cerrar sin guardar?", "Cerrar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // O cerrar este panel específico, dependiendo de la lógica de tu aplicación
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
        // Asumimos que dateChooser.getDate() no devuelve null, deberías añadir una comprobación aquí
    	
    	LocalDate fechaNacimiento = null;
    	if (dateChooser.getDate() != null) {
    	    fechaNacimiento = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	}

        
        // Suponiendo que el ID se genera automáticamente o no es necesario para la inserción, lo omitiremos o usaremos null/valor temporal
        String idTemporal = "temp"; // Este valor debe ser ajustado según tu lógica de negocio
        
        // Creación del objeto Cliente con los datos recogidos de la UI
        Cliente cliente = new Cliente(
        	    0, // ID temporal o indicador para generar un nuevo ID
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

        
        // Instanciación de ClienteDao y llamada al método para insertar el cliente
        ClienteDAO clienteDao = new ClienteDAO();
        boolean exito = clienteDao.insertarCliente(cliente);
        
        // Mensaje de confirmación o error
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente guardado con éxito");
            limpiarCampos(); // Opcional: Limpia los campos después de guardar
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
		    lbRCliente_Nombre.setBounds(105, 11, 50, 14);
		    Pcliente.add(lbRCliente_Nombre);
		 
		    tfRcliente_Nombre.setBounds(105, 36, 86, 20);
		    Pcliente.add(tfRcliente_Nombre);

		    // Apellidos
		    JLabel lbRCliente_Apellidos = new JLabel("Apellidos");
		    lbRCliente_Apellidos.setBounds(387, 11, 60, 14);
		    Pcliente.add(lbRCliente_Apellidos);
		  
		    tfRcliente_Apellidos.setBounds(387, 36, 218, 20);
		    Pcliente.add(tfRcliente_Apellidos);

		    // NIF
		    JLabel lbRCliente_NIF = new JLabel("NIF");
		    lbRCliente_NIF.setBounds(511, 66, 46, 14);
		    Pcliente.add(lbRCliente_NIF);
		   
		    tfRcliente_NIF.setBounds(505, 88, 86, 20);
		    Pcliente.add(tfRcliente_NIF);

		    // DNI
		    JLabel lbRCliente_DNI = new JLabel("DNI");
		    lbRCliente_DNI.setBounds(387, 66, 46, 14);
		    Pcliente.add(lbRCliente_DNI);
		
		    tfRcliente_DNI.setBounds(387, 88, 86, 20);
		    Pcliente.add(tfRcliente_DNI);

		    // Fecha de Nacimiento
		    JLabel lbRCliente_Fnacimiento = new JLabel("Fecha de Nacimiento");
		    lbRCliente_Fnacimiento.setBounds(105, 78, 130, 14);
		    Pcliente.add(lbRCliente_Fnacimiento);
		    
		    // Inicialización de JDateChooser
		    dateChooser = new JDateChooser();
		    dateChooser.setBounds(105, 103, 120, 20); // Ajusta según sea necesario
		    Pcliente.add(dateChooser);
		    

		    // Dirección
		    JLabel lbRCliente_Direccion = new JLabel("Direccion");
		    lbRCliente_Direccion.setBounds(22, 146, 60, 14);
		    Pcliente.add(lbRCliente_Direccion);

		    tfRcliente_Direccion.setBounds(21, 171, 218, 20);
		    Pcliente.add(tfRcliente_Direccion);

		    // Población
		    JLabel lbRCliente_Poblacion = new JLabel("Poblacion");
		    lbRCliente_Poblacion.setBounds(283, 146, 60, 14);
		    Pcliente.add(lbRCliente_Poblacion);
	
		    tfRcliente_Poblacion.setBounds(283, 171, 86, 20);
		    Pcliente.add(tfRcliente_Poblacion);

		    // Provincia
		    JLabel lbRCliente_Provincia = new JLabel("Provincia");
		    lbRCliente_Provincia.setBounds(450, 146, 60, 14);
		    Pcliente.add(lbRCliente_Provincia);
		 
		    tfRcliente_Provincia.setBounds(450, 171, 86, 20);
		    Pcliente.add(tfRcliente_Provincia);

		    // Teléfono Fijo
		    JLabel lbRCliente_Tfijo = new JLabel("Telefono Fijo");
		    lbRCliente_Tfijo.setBounds(22, 245, 75, 14);
		    Pcliente.add(lbRCliente_Tfijo);
		
		    tfRcliente_Tfijo.setBounds(10, 270, 86, 20);
		    Pcliente.add(tfRcliente_Tfijo);

		    // Teléfono Móvil
		    JLabel lbRCliente_Tmovil = new JLabel("Telefono Movil");
		    lbRCliente_Tmovil.setBounds(158, 245, 85, 14);
		    Pcliente.add(lbRCliente_Tmovil);
		
		    tfRcliente_Tmovil.setBounds(158, 270, 86, 20);
		    Pcliente.add(tfRcliente_Tmovil);

		    // Correo Electrónico
		    JLabel lbRCliente_email = new JLabel("Correo Electronico");
		    lbRCliente_email.setBounds(356, 245, 120, 14);
		    Pcliente.add(lbRCliente_email);
		  
		    tfRcliente_Email.setBounds(356, 270, 186, 20);
		    Pcliente.add(tfRcliente_Email);

		    return Pcliente;
		
	}
	
	
	private JPanel crearPanelMascotasTotales() {
	    JPanel PMascotasTotales = new JPanel();
	    PMascotasTotales.setBackground(Color.WHITE);

	    // Modelo de la tabla
	    String[] columnNames = {"Nombre", "Chip"};
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	    // Añadir datos de ejemplo al modelo
	    tableModel.addRow(new Object[]{"Mascota 1", "1234567890"});
	    tableModel.addRow(new Object[]{"Mascota 2", "0987654321"});
	    PMascotasTotales.setLayout(null);

	    // Tabla
	    JTable tableMascotas = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(tableMascotas);
	    scrollPane.setBounds(35, 103, 620, 214);
	    PMascotasTotales.add(scrollPane);
	    
	    JButton btnR_AMascota = new JButton("Añadir Mascota");
	    btnR_AMascota.setBounds(35, 47, 113, 23);
	    PMascotasTotales.add(btnR_AMascota);

	    // Botón para añadir mascotas
	    JButton btnAnadirMascota = new JButton("Añadir mascota");
	    btnAnadirMascota.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Aquí va el código para añadir una nueva mascota
	        }
	    });

	    // Panel para el botón de añadir mascotas
	    JPanel panelAnadir = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    panelAnadir.add(btnAnadirMascota);
	    panelAnadir.setOpaque(false);
    

	  

	    return PMascotasTotales;
	}
	
	
		public static void main(String[] args){
	        JFrame frame = new JFrame("Registro Cliente y Mascota");
	        PanelRegistroClienteMascota panelRegistro = new PanelRegistroClienteMascota();

	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(800, 600); // Ajusta el tamaño según necesites
	        frame.getContentPane().add(panelRegistro); // Agrega el panel al frame
	        frame.setVisible(true); // Hace visible el frame
	    
	}
	}



