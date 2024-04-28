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
	private JTextField buscarProducto;
	private JTextField buscarServicio;
	private JTable tablaAlmacen;
	private JTable tablaServicios;
	private JTextField buscarFarmaco;
	private JTable tablaFarmacos;
	

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
	    
	    JPanel gestionProductos = new JPanel();    // Crea el panel para la pestaña "Gestión de Almacén"
	    tabbedPane.addTab("Gestion de Productos", null, gestionProductos, null);     // Agrega la pestaña "Gestión de Almacén" y su contenido al JTabbedPane
	    gestionProductos.setLayout(null);
	    
	    buscarProducto = new JTextField();
	    buscarProducto.setText("Buscar Producto");
	    buscarProducto.setBounds(0, 11, 130, 20);
	    gestionProductos.add(buscarProducto);
	    buscarProducto.setColumns(10);
	    
	 // Agregando el FocusListener a txtBuscarProducto
        buscarProducto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (buscarProducto.getText().equals("Buscar Producto")) {
                    buscarProducto.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (buscarProducto.getText().isEmpty()) {
                    buscarProducto.setText("Buscar Producto");
                }
            }
        });
	    
	    JButton añadirProducto = new JButton("Añadir producto");
	    añadirProducto.setBounds(183, 10, 130, 23);
	    gestionProductos.add(añadirProducto);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(0, 48, 1107, 577);
	    gestionProductos.add(scrollPane);
	    
	    tablaAlmacen = new JTable();
	    scrollPane.setViewportView(tablaAlmacen);
	    
	    JPanel gestionServicios = new JPanel();  // Crea otro panel para la pestaña "Gestión de Servicios"
	    tabbedPane.addTab("Gestion de Servicios", null, gestionServicios, null);  // Agrega la pestaña "Gestión de Servicios" y su contenido al JTabbedPane
	    gestionServicios.setLayout(null);
	    
	    buscarServicio = new JTextField();
	    buscarServicio.setText("Buscar Servicio");
	    buscarServicio.setBounds(0, 11, 130, 20);
	    gestionServicios.add(buscarServicio);
	    buscarServicio.setColumns(10);
	    
	    // Agregando el FocusListener a txtBuscarServicio
        buscarServicio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (buscarServicio.getText().equals("Buscar Servicio")) {
                    buscarServicio.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (buscarServicio.getText().isEmpty()) {
                    buscarServicio.setText("Buscar Servicio");
                }
            }
        });
	    
	    JButton añadirServicio = new JButton("Añadir Servicio");
	    añadirServicio.setBounds(183, 10, 130, 23);
	    gestionServicios.add(añadirServicio);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(0, 47, 1107, 578);
	    gestionServicios.add(scrollPane_1);
	    
	    tablaServicios = new JTable();
	    scrollPane_1.setViewportView(tablaServicios);
	    
	    JPanel gestionFarmacos = new JPanel();
	    gestionFarmacos.setToolTipText("");
	    tabbedPane.addTab("Gestion de Farmacos", null, gestionFarmacos, null);
	    gestionFarmacos.setLayout(null);
	    
	    JScrollPane scrollPane_2 = new JScrollPane();
	    scrollPane_2.setBounds(0, 50, 1107, 575);
	    gestionFarmacos.add(scrollPane_2);
	    
	    tablaFarmacos = new JTable();
	    scrollPane_2.setViewportView(tablaFarmacos);
	    
	    buscarFarmaco = new JTextField();
	    buscarFarmaco.setText("Buscar Farmaco");
	    buscarFarmaco.setBounds(0, 11, 130, 20);
	    gestionFarmacos.add(buscarFarmaco);
	    buscarFarmaco.setColumns(10);
	    
	    JButton añadirFarmaco = new JButton("Añadir Farmaco");
	    añadirFarmaco.setBounds(180, 10, 130, 23);
	    gestionFarmacos.add(añadirFarmaco);
	    
	    JPanel Historial = new JPanel();
	    tabbedPane.addTab("Historial", null, Historial, null);
	    Historial.setLayout(null);

	    // Resto de la configuración del PanelAlmacen...
	}
}
