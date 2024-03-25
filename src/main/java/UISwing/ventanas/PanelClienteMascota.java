package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class PanelClienteMascota extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscarClientemascota;
	private JTable table;
	private JTextField txtBuscarCliente;
	private JTable table_1;

	/**
	 * Create the panel.
	 */
	public PanelClienteMascota() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(-10, 11, 1112, 653);
		add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Exportar");
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(256, 42, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Importar");
		btnNewButton_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_1.setBounds(355, 42, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Añadir");
		btnNewButton_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_2.setBounds(887, 42, 89, 23);
		panel.add(btnNewButton_2);
		
		
		  // Configuración del botón Añadir y su ActionListener
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        abrirPanelRegistro();
		    }
		});
		
	
		
		JButton btnNewButton_3 = new JButton("Mostrar por");
		btnNewButton_3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_3.setBounds(516, 42, 89, 23);
		panel.add(btnNewButton_3);
		
		txtBuscarClientemascota = new JTextField();
		txtBuscarClientemascota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtBuscarClientemascota.setText("Buscar mascota");
		txtBuscarClientemascota.setBounds(716, 43, 161, 20);
		panel.add(txtBuscarClientemascota);
		txtBuscarClientemascota.setColumns(10);
		
		txtBuscarCliente = new JTextField();
		txtBuscarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtBuscarCliente.setText("Buscar cliente");
		txtBuscarCliente.setBounds(42, 43, 166, 20);
		panel.add(txtBuscarCliente);
		txtBuscarCliente.setColumns(10);
		
		table = new JTable();
		table.setBounds(42, 105, 483, 451);
		panel.add(table);
		
		table_1 = new JTable();
		table_1.setBounds(535, 105, 483, 451);
		panel.add(table_1);

		 // Llama a los métodos para cargar los datos en las tablas al iniciar el panel
	    mostrarDatos();
	    mostrarDatosMascotas();
		
		
	}
	
	 private void mostrarDatos() {
	        // Datos para la conexión
	        String url = "jdbc:mysql://localhost:3306/pawcloud"; // URL de tu base de datos
	        String usuario = "tuUsuario"; // Tu usuario de la base de datos
	        String contraseña = "tuContraseña"; // Tu contraseña de la base de datos

	        // Consulta SQL para obtener los datos deseados
	        String consulta = "SELECT nombre, dni, telefono_movil FROM clientes;";

	        try {
	            // Establece la conexión con la base de datos
	            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

	            // Crea un Statement para poder ejecutar la consulta
	            Statement sentencia = conexion.createStatement();

	            // Ejecuta la consulta y obtiene el resultado
	            ResultSet resultado = sentencia.executeQuery(consulta);

	            // Poblar el JTable con los datos del resultado
	            DefaultTableModel modelo = new DefaultTableModel(new String[]{"Cliente", "DNI", "Teléfono Móvil"}, 0);
	            while(resultado.next()) {
	                String nombre = resultado.getString("nombre");
	                String dni = resultado.getString("dni");
	                String telefonoMovil = resultado.getString("telefono_movil");
	                modelo.addRow(new Object[]{nombre, dni, telefonoMovil});
	            }
	            table.setModel(modelo);

	            // Cierra las conexiones
	            resultado.close();
	            sentencia.close();
	            conexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	//prueba
	 private void mostrarDatosMascotas() {
		    // Datos para la conexión
		    String url = "jdbc:mysql://localhost:3306/pawcloud"; // URL de tu base de datos
		    String usuario = "tuUsuario"; // Tu usuario de la base de datos
		    String contraseña = "tuContraseña"; // Tu contraseña de la base de datos

		    // Consulta SQL para obtener los datos deseados de mascotas
		    String consulta = "SELECT nombre, especie, microchip FROM mascotas;";

		    try {
		        // Establece la conexión con la base de datos
		        Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

		        // Crea un Statement para poder ejecutar la consulta
		        Statement sentencia = conexion.createStatement();

		        // Ejecuta la consulta y obtiene el resultado
		        ResultSet resultado = sentencia.executeQuery(consulta);

		        // Poblar la segunda JTable con los datos del resultado
		        DefaultTableModel modeloMascotas = new DefaultTableModel(new String[]{"Nombre", "Especie", "Microchip"}, 0);
		        while(resultado.next()) {
		            String nombre = resultado.getString("nombre");
		            String especie = resultado.getString("especie");
		            String microchip = resultado.getString("microchip");
		            modeloMascotas.addRow(new Object[]{nombre, especie, microchip});
		        }
		        table_1.setModel(modeloMascotas);

		        // Cierra las conexiones
		        resultado.close();
		        sentencia.close();
		        conexion.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	 
	
	
	
	
	
    // Método para abrir el PanelRegistroClienteMascota.
     
	private void abrirPanelRegistro() {
	    JFrame frameRegistro = new JFrame("Registro de Cliente y Mascota");
	    PanelRegistroCliente panelRegistro = new PanelRegistroCliente();

	    frameRegistro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo este frame al cerrar
	    frameRegistro.setSize(800, 600); // Ajusta según la necesidad
	    frameRegistro.getContentPane().add(panelRegistro); // Agrega el panelRegistro al frame
	    frameRegistro.setVisible(true); // Hace visible el frame
	}
}