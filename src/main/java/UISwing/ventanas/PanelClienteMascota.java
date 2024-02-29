package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;

public class PanelClienteMascota extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;

	/**
	 * Create the panel.
	 */
	public PanelClienteMascota() {
		setLayout(null);
		
		JButton btnAñadirMascotas = new JButton("Añadiurmascota");
		btnAñadirMascotas.setBounds(238, 50, 192, 23);
		add(btnAñadirMascotas);
		
		table = new JTable();
		table.setBounds(41, 309, 685, 314);
		add(table);
		
		table_1 = new JTable();
		table_1.setBounds(796, 240, 306, 387);
		add(table_1);
		
		JLabel lblListaCliente = new JLabel("Clientes");
		lblListaCliente.setBounds(49, 273, 46, 14);
		add(lblListaCliente);
		
		JLabel lbl = new JLabel("New label");
		lbl.setBounds(802, 213, 46, 14);
		add(lbl);

	}
}
