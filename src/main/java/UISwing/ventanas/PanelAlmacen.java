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
	private JTextField BuscarProducto;
	private JTextField BuscarServicio;
	private JTable tablaAlmacen;
	private JTable tablaServicios;
	private JTextField BuscarFarmaco;
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
	    
	    JPanel GestionAlmacen = new JPanel();    // Crea el panel para la pestaña "Gestión de Almacén"
	    tabbedPane.addTab("Gestion de Almacen", null, GestionAlmacen, null);     // Agrega la pestaña "Gestión de Almacén" y su contenido al JTabbedPane
	    GestionAlmacen.setLayout(null);
	    
	    BuscarProducto = new JTextField();
	    BuscarProducto.setText("Buscar Producto");
	    BuscarProducto.setBounds(0, 11, 130, 20);
	    GestionAlmacen.add(BuscarProducto);
	    BuscarProducto.setColumns(10);
	    
	 // Agregando el FocusListener a txtBuscarProducto
        BuscarProducto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (BuscarProducto.getText().equals("Buscar Producto")) {
                    BuscarProducto.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (BuscarProducto.getText().isEmpty()) {
                    BuscarProducto.setText("Buscar Producto");
                }
            }
        });
	    
	    JButton AñadirProducto = new JButton("Añadir producto");
	    AñadirProducto.setBounds(183, 10, 130, 23);
	    GestionAlmacen.add(AñadirProducto);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(0, 48, 1107, 577);
	    GestionAlmacen.add(scrollPane);
	    
	    tablaAlmacen = new JTable();
	    scrollPane.setViewportView(tablaAlmacen);
	    
	    JPanel GestionServicios = new JPanel();  // Crea otro panel para la pestaña "Gestión de Servicios"
	    tabbedPane.addTab("Gestion de Servicios", null, GestionServicios, null);  // Agrega la pestaña "Gestión de Servicios" y su contenido al JTabbedPane
	    GestionServicios.setLayout(null);
	    
	    BuscarServicio = new JTextField();
	    BuscarServicio.setText("Buscar Servicio");
	    BuscarServicio.setBounds(0, 11, 130, 20);
	    GestionServicios.add(BuscarServicio);
	    BuscarServicio.setColumns(10);
	    
	    // Agregando el FocusListener a txtBuscarServicio
        BuscarServicio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (BuscarServicio.getText().equals("Buscar Servicio")) {
                    BuscarServicio.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (BuscarServicio.getText().isEmpty()) {
                    BuscarServicio.setText("Buscar Servicio");
                }
            }
        });
	    
	    JButton AñadirServicio = new JButton("Añadir Servicio");
	    AñadirServicio.setBounds(183, 10, 130, 23);
	    GestionServicios.add(AñadirServicio);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(0, 47, 1107, 578);
	    GestionServicios.add(scrollPane_1);
	    
	    tablaServicios = new JTable();
	    scrollPane_1.setViewportView(tablaServicios);
	    
	    JPanel GestionFarmacos = new JPanel();
	    GestionFarmacos.setToolTipText("");
	    tabbedPane.addTab("Gestion de Farmacos", null, GestionFarmacos, null);
	    GestionFarmacos.setLayout(null);
	    
	    JScrollPane scrollPane_2 = new JScrollPane();
	    scrollPane_2.setBounds(0, 50, 1107, 575);
	    GestionFarmacos.add(scrollPane_2);
	    
	    tablaFarmacos = new JTable();
	    scrollPane_2.setViewportView(tablaFarmacos);
	    
	    BuscarFarmaco = new JTextField();
	    BuscarFarmaco.setText("Buscar Farmaco");
	    BuscarFarmaco.setBounds(0, 11, 130, 20);
	    GestionFarmacos.add(BuscarFarmaco);
	    BuscarFarmaco.setColumns(10);
	    
	    JButton AñadirFarmaco = new JButton("Añadir Farmaco");
	    AñadirFarmaco.setBounds(180, 10, 130, 23);
	    GestionFarmacos.add(AñadirFarmaco);
	    
	    JPanel Historial = new JPanel();
	    tabbedPane.addTab("Historial", null, Historial, null);
	    Historial.setLayout(null);

	    // Resto de la configuración del PanelAlmacen...
	}
}
