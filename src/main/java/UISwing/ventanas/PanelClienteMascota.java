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

public class PanelClienteMascota extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscarClientemascota;
	private JTable table;
	private JTextField txtBuscarCliente;

	/**
	 * Create the panel.
	 */
	public PanelClienteMascota() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1041, 794);
		add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(42, 172, 970, 570);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 470, 570);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Cliente");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(41, 41, 49, 14);
		panel_3.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("DNI");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(194, 41, 49, 14);
		panel_3.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Teléfono");
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(292, 41, 49, 14);
		panel_3.add(lblNewLabel_5);
		
		table = new JTable();
		table.setBounds(41, 82, 387, 449);
		panel_3.add(table);
		
		JLabel lblNewLabel_6 = new JLabel("Nombre");
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(553, 47, 49, 14);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Especie");
		lblNewLabel_7.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_7.setBounds(733, 48, 49, 14);
		panel_2.add(lblNewLabel_7);
		
		JButton btnNewButton = new JButton("Exportar");
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(303, 117, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Importar");
		btnNewButton_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_1.setBounds(414, 117, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Añadir");
		btnNewButton_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_2.setBounds(885, 117, 89, 23);
		panel.add(btnNewButton_2);
		
		
		  // Configuración del botón Añadir y su ActionListener
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        abrirPanelRegistro();
		    }
		});
		
	
		
		JButton btnNewButton_3 = new JButton("Mostrar por");
		btnNewButton_3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton_3.setBounds(553, 117, 89, 23);
		panel.add(btnNewButton_3);
		
		txtBuscarClientemascota = new JTextField();
		txtBuscarClientemascota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtBuscarClientemascota.setText("Buscar mascota");
		txtBuscarClientemascota.setBounds(683, 118, 161, 20);
		panel.add(txtBuscarClientemascota);
		txtBuscarClientemascota.setColumns(10);
		
		txtBuscarCliente = new JTextField();
		txtBuscarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtBuscarCliente.setText("Buscar cliente");
		txtBuscarCliente.setBounds(42, 118, 166, 20);
		panel.add(txtBuscarCliente);
		txtBuscarCliente.setColumns(10);

		
		
		
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