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
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import DB.AlmacenDAO;
import model.Almacen;
public class PanelAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField buscarProducto;
	private JTextField buscarServicio;
	private JTable tablaProductos;
	private JTable tablaServicios;
	private JTextField buscarFarmaco;
	private JTable tablaFarmacos;
	private JScrollPane scrollPaneProductos;
    private DefaultTableModel modeloTablaProductos;
    private AlmacenDAO almacenDao;

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
          
            public void focusGained(FocusEvent e) {
                if (buscarProducto.getText().equals("Buscar Producto")) {
                    buscarProducto.setText("");
                }
            }
          
            public void focusLost(FocusEvent e) {
                if (buscarProducto.getText().isEmpty()) {
                    buscarProducto.setText("Buscar Producto");
                }
            }
        });
	    
	    JButton añadirProductoServicio = new JButton("Añadir al almacen");
	    añadirProductoServicio.setBounds(183, 10, 153, 23);
	    gestionProductos.add(añadirProductoServicio);
	    
	    scrollPaneProductos = new JScrollPane();  // Usar la versión de clase aquí
        scrollPaneProductos.setBounds(0, 48, 1107, 577);
        gestionProductos.add(scrollPaneProductos);
	    
	    tablaProductos = new JTable();
	    scrollPaneProductos.setViewportView(tablaProductos);
	    
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
          
            public void focusGained(FocusEvent e) {
                if (buscarServicio.getText().equals("Buscar Servicio")) {
                    buscarServicio.setText("");
                }
            }
           
            public void focusLost(FocusEvent e) {
                if (buscarServicio.getText().isEmpty()) {
                    buscarServicio.setText("Buscar Servicio");
                }
            }
        });
	    
	    JScrollPane scrollPaneServicios = new JScrollPane();
	    scrollPaneServicios.setBounds(0, 47, 1107, 578);
	    gestionServicios.add(scrollPaneServicios);
	    
	    tablaServicios = new JTable();
	    scrollPaneServicios.setViewportView(tablaServicios);
	    
	    JPanel gestionFarmacos = new JPanel();
	    gestionFarmacos.setToolTipText("");
	    tabbedPane.addTab("Gestion de Farmacos", null, gestionFarmacos, null);
	    gestionFarmacos.setLayout(null);
	    
	    JScrollPane scrollPaneFarmacos = new JScrollPane();
	    scrollPaneFarmacos.setBounds(0, 50, 1107, 575);
	    gestionFarmacos.add(scrollPaneFarmacos);
	    
	    tablaFarmacos = new JTable();
	    scrollPaneFarmacos.setViewportView(tablaFarmacos);
	    
	    buscarFarmaco = new JTextField();
	    buscarFarmaco.setText("Buscar Farmaco");
	    buscarFarmaco.setBounds(0, 11, 130, 20);
	    gestionFarmacos.add(buscarFarmaco);
	    buscarFarmaco.setColumns(10);
	    
	    JButton añadirFarmaco = new JButton("Añadir Farmaco");
	    añadirFarmaco.setBounds(180, 10, 130, 23);
	    gestionFarmacos.add(añadirFarmaco);

	    // Resto de la configuración del PanelAlmacen...
	    inicializarComponentesProductos(gestionProductos);
	}
	
	private void inicializarComponentesProductos(JPanel panel) {
        modeloTablaProductos = new DefaultTableModel(new Object[]{"Nombre Producto", "Fecha Caducidad", "Cantidad Stock"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        tablaProductos = new JTable(modeloTablaProductos);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPaneProductos.setViewportView(tablaProductos);

        almacenDao = new AlmacenDAO();  // Suponiendo que AlmacenDAO gestiona su propia conexión
        cargarDatosProductos(); // Método para cargar los datos de los productos en la tabla
    }

    private void cargarDatosProductos() {
        try {
            List<Almacen> productos = almacenDao.obtenerProductosFiltradosYOrdenados();
            modeloTablaProductos.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            for (Almacen almacen : productos) {
                modeloTablaProductos.addRow(new Object[]{
                    almacen.getNombreProducto(),
                    almacen.getFechaCaducidad(),
                    almacen.getCantidadStock()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de productos: " + ex.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Agregamos el método main para ejecutar y probar la interfaz
    public static void main(String[] args) {
        // Creamos el marco de la ventana principal
        JFrame frame = new JFrame("Gestión de Almacén");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700); // Tamaño del marco
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Creamos una instancia del panel
        PanelAlmacen panelAlmacen = new PanelAlmacen();
        frame.add(panelAlmacen); // Añade el panel al marco

        // Hace visible la ventana
        frame.setVisible(true);
    }
    
}

