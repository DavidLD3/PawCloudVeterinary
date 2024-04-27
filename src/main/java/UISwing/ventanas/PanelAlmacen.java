package UISwing.ventanas;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PanelAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscarProducto;
	private JTextField txtBuscarServicio;
	private JTable tablaAlmacen;
	private JTable tablaServicios;
	

	/**
	 * Create the panel.
	 */
	public PanelAlmacen() {

		setPreferredSize(new Dimension(1112, 653));  
        setLayout(null);
        setOpaque(false);

	    // Crea el JTabbedPane y le asigna un tamaño y posición
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setBounds(0, 0, 1112, 653); // Tamaño ampliado para incluir el área de contenido
	    add(tabbedPane);
	    
	    JPanel GestionAlmacen = new JPanel();    // Crea el panel para la pestaña "Gestión de Almacén"
	    tabbedPane.addTab("Gestion de Almacen", null, GestionAlmacen, null);     // Agrega la pestaña "Gestión de Almacén" y su contenido al JTabbedPane
	    GestionAlmacen.setLayout(null);
	    
	    txtBuscarProducto = new JTextField();
	    txtBuscarProducto.setText("Buscar Producto");
	    txtBuscarProducto.setBounds(0, 11, 130, 20);
	    GestionAlmacen.add(txtBuscarProducto);
	    txtBuscarProducto.setColumns(10);
	    
	 // Agregando el FocusListener a txtBuscarProducto
        txtBuscarProducto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBuscarProducto.getText().equals("Buscar Producto")) {
                    txtBuscarProducto.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarProducto.getText().isEmpty()) {
                    txtBuscarProducto.setText("Buscar Producto");
                }
            }
        });
	    
	    JButton btnAñadirProducto = new JButton("Añadir producto");
	    btnAñadirProducto.setBounds(183, 10, 130, 23);
	    GestionAlmacen.add(btnAñadirProducto);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(0, 48, 1107, 577);
	    GestionAlmacen.add(scrollPane);
	    
	    tablaAlmacen = new JTable();
	    scrollPane.setViewportView(tablaAlmacen);
	    
	    JPanel GestionServicios = new JPanel();  // Crea otro panel para la pestaña "Gestión de Servicios"
	    tabbedPane.addTab("Gestion de Servicios", null, GestionServicios, null);  // Agrega la pestaña "Gestión de Servicios" y su contenido al JTabbedPane
	    GestionServicios.setLayout(null);
	    
	    txtBuscarServicio = new JTextField();
	    txtBuscarServicio.setText("Buscar Servicio");
	    txtBuscarServicio.setBounds(0, 11, 130, 20);
	    GestionServicios.add(txtBuscarServicio);
	    txtBuscarServicio.setColumns(10);
	    
	    // Agregando el FocusListener a txtBuscarServicio
        txtBuscarServicio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBuscarServicio.getText().equals("Buscar Servicio")) {
                    txtBuscarServicio.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarServicio.getText().isEmpty()) {
                    txtBuscarServicio.setText("Buscar Servicio");
                }
            }
        });
	    
	    JButton btnAñadirServicio = new JButton("Añadir Servicio");
	    btnAñadirServicio.setBounds(183, 10, 130, 23);
	    GestionServicios.add(btnAñadirServicio);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(0, 47, 1107, 578);
	    GestionServicios.add(scrollPane_1);
	    
	    tablaServicios = new JTable();
	    scrollPane_1.setViewportView(tablaServicios);

	    // Resto de la configuración del PanelAlmacen...
	}
}
