package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class PanelAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscarProducto;
	private JTextField txtBuscarProducto_1;
	private JTextField txtBuscarServicio;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelAlmacen() {
	    setLayout(null); // Layout absoluto

	    // Crea el JTabbedPane y le asigna un tamaño y posición
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setBounds(0, 0, 1112, 653); // Tamaño ampliado para incluir el área de contenido

	    // Añade el tabbedPane al PanelAlmacen
	    add(tabbedPane);

	    // Crea el panel para la pestaña "Gestión de Almacén"
	    JPanel panelGestionAlmacen = new JPanel();
	    panelGestionAlmacen.setLayout(null); // Podrías considerar usar un Layout Manager
	    
	    // Aquí añadirías los componentes al panelGestionAlmacen, como textfields, buttons, etc.

	    // Agrega la pestaña "Gestión de Almacén" y su contenido al JTabbedPane
	    tabbedPane.addTab("Gestión de Almacén", null, panelGestionAlmacen, null);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(0, 0, 1107, 48);
	    panelGestionAlmacen.add(panel);
	    panel.setLayout(null);
	    
	    txtBuscarProducto_1 = new JTextField();
	    txtBuscarProducto_1.setText("Buscar producto");
	    txtBuscarProducto_1.setBounds(0, 11, 136, 20);
	    panel.add(txtBuscarProducto_1);
	    txtBuscarProducto_1.setColumns(10);
	    
	    JButton btnNewButton = new JButton("Añadir producto");
	    btnNewButton.setBounds(206, 10, 130, 23);
	    panel.add(btnNewButton);
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBounds(0, 47, 1107, 578);
	    panelGestionAlmacen.add(panel_1);
	    panel_1.setLayout(null);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(0, 0, 1107, 578);
	    panel_1.add(scrollPane);
	    
	    table = new JTable();
	    scrollPane.setColumnHeaderView(table);

	    // Crea otro panel para la pestaña "Gestión de Servicios"
	    JPanel panelGestionServicios = new JPanel();
	    panelGestionServicios.setLayout(null); // Podrías considerar usar un Layout Manager

	    // Aquí añadirías los componentes al panelGestionServicios

	    // Agrega la pestaña "Gestión de Servicios" y su contenido al JTabbedPane
	    tabbedPane.addTab("Gestión de Servicios", null, panelGestionServicios, null);
	    
	    JPanel panel_2 = new JPanel();
	    panel_2.setBounds(0, 0, 1107, 48);
	    panelGestionServicios.add(panel_2);
	    panel_2.setLayout(null);
	    
	    txtBuscarServicio = new JTextField();
	    txtBuscarServicio.setText("Buscar servicio");
	    txtBuscarServicio.setBounds(0, 11, 136, 20);
	    panel_2.add(txtBuscarServicio);
	    txtBuscarServicio.setColumns(10);
	    
	    JButton btnNewButton_1 = new JButton("Añadir servicio");
	    btnNewButton_1.setBounds(206, 10, 130, 23);
	    panel_2.add(btnNewButton_1);
	    
	    JPanel panel_3 = new JPanel();
	    panel_3.setBounds(0, 46, 1107, 579);
	    panelGestionServicios.add(panel_3);
	    panel_3.setLayout(null);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(0, 0, 1107, 579);
	    panel_3.add(scrollPane_1);

	    // Resto de la configuración del PanelAlmacen...
	}
}
