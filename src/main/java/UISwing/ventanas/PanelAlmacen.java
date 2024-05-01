package UISwing.ventanas;

import java.awt.Dimension;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import DB.AlmacenDAO;
import DB.FarmacoDAO;
import model.Almacen;
import model.Farmaco;
import javax.swing.event.DocumentListener;
import UISwing.ventanas.DialogoRegistroAlmacen;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;
import UISwing.ventanas.DialogoRegistroFarmaco;

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
    private DefaultTableModel modeloTablaServicios; // Modelo para la tabla de servicios
    private DefaultTableModel modeloTablaFarmacos;
    private FarmacoDAO farmacoDao;
	/**
	 * Create the panel.
	 */
	public PanelAlmacen() {

		setPreferredSize(new Dimension(1112, 653));  
        setLayout(null);
        setOpaque(false);
        farmacoDao = new FarmacoDAO();
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
	    
	    buscarProducto.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscar();
            }

            public void removeUpdate(DocumentEvent e) {
                buscar();
            }

            public void changedUpdate(DocumentEvent e) {
                buscar();
            }

            private void buscar() {
                try {
                    String nombreProducto = buscarProducto.getText().trim();
                    if (!nombreProducto.isEmpty() && !nombreProducto.equals("Buscar Producto")) {
                        List<Almacen> productos = almacenDao.buscarProductosPorNombre(nombreProducto);
                        actualizarTablaProductos(productos);
                    } else {
                        cargarDatosProductos();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(PanelAlmacen.this, "Error al buscar productos: " + ex.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	    
	    
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
        añadirProductoServicio.addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
                abrirDialogoRegistroAlmacen();
            }
        });
        gestionProductos.add(añadirProductoServicio);
	    
	    scrollPaneProductos = new JScrollPane();  // Usar la versión de clase aquí
        scrollPaneProductos.setBounds(0, 48, 1107, 577);
        gestionProductos.add(scrollPaneProductos);
	    
	    tablaProductos = new JTable();
	    scrollPaneProductos.setViewportView(tablaProductos);
	    
	    JPanel gestionServicios = new JPanel();  // Crea otro panel para la pestaña "Gestión de Servicios"
	    tabbedPane.addTab("Gestion de Servicios", null, gestionServicios, null);  // Agrega la pestaña "Gestión de Servicios" y su contenido al JTabbedPane
	    gestionServicios.setLayout(null);
	    
	    inicializarComponentesServicios(gestionServicios);
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
        buscarServicio.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscarServicio();
            }

            public void removeUpdate(DocumentEvent e) {
                buscarServicio();
            }

            public void changedUpdate(DocumentEvent e) {
                buscarServicio();
            }

            private void buscarServicio() {
                try {
                    String nombreServicio = buscarServicio.getText().trim();
                    if (!nombreServicio.isEmpty() && !nombreServicio.equals("Buscar Servicio")) {
                        List<Almacen> servicios = almacenDao.buscarServiciosPorNombre(nombreServicio); // Usamos el mismo método para buscar servicios
                        actualizarTablaServicios(servicios);
                    } else {
                        cargarDatosServicios();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(PanelAlmacen.this, "Error al buscar servicios: " + ex.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
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
	    
	    inicializarComponentesFarmacos(gestionFarmacos);
	    
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
	    
	    buscarFarmaco.getDocument().addDocumentListener(new DocumentListener() {
	        public void insertUpdate(DocumentEvent e) {
	            buscarFarmaco();
	        }

	        public void removeUpdate(DocumentEvent e) {
	            buscarFarmaco();
	        }

	        public void changedUpdate(DocumentEvent e) {
	            buscarFarmaco();
	        }

	        private void buscarFarmaco() {
	            String texto = buscarFarmaco.getText().trim();
	            if (!texto.isEmpty() && !texto.equals("Buscar Farmaco")) {
	                List<Farmaco> farmacos = farmacoDao.buscarFarmacos(texto); // Asume que este método existe en FarmacoDAO
					actualizarTablaFarmacos(farmacos);
	            } else {
	                cargarDatosFarmacos(); // Recargar todos los datos si el campo de búsqueda está vacío
	            }
	        }
	    });
	    buscarFarmaco.addFocusListener(new FocusAdapter() {
	        public void focusGained(FocusEvent e) {
	            if (buscarFarmaco.getText().equals("Buscar Farmaco")) {
	                buscarFarmaco.setText("");
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if (buscarFarmaco.getText().isEmpty()) {
	                buscarFarmaco.setText("Buscar Farmaco");
	            }
	        }
	    });
	    JButton añadirFarmaco = new JButton("Añadir Farmaco");
	    añadirFarmaco.setBounds(180, 10, 130, 23);
	    gestionFarmacos.add(añadirFarmaco);
	    añadirFarmaco.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            abrirDialogoRegistroFarmaco();
	        }
	    });

	    // Resto de la configuración del PanelAlmacen...
	    inicializarComponentesProductos(gestionProductos);
	    
	}
	
	private void inicializarComponentesProductos(JPanel panel) {
	    modeloTablaProductos = new DefaultTableModel(new Object[]{"Nombre Producto", "Categoría", "Fecha Última Compra", "Fecha Caducidad", "Cantidad Stock"}, 0) {
	        public boolean isCellEditable(int row, int column) {
	            return false; // Hacer que la tabla no sea editable
	        }
	    };

	    tablaProductos = new JTable(modeloTablaProductos);
	    tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    tablaProductos.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) { // Verificar si fue un doble clic
	                JTable target = (JTable)e.getSource();
	                int row = target.getSelectedRow();
	                TableModel model = target.getModel();
	                String nombreProducto = (String) model.getValueAt(row, 0); // Obtener el nombre del producto seleccionado
	                abrirDialogoInfoAlmacen(nombreProducto);
	            }
	        }
	    });
	    scrollPaneProductos.setViewportView(tablaProductos);
	    almacenDao = new AlmacenDAO();
	    cargarDatosProductos();
	}
	// Método para abrir el DialogoInfoAlmacen con la información del producto seleccionado
	// Reutilizamos el método abrirDialogoInfoAlmacen para mostrar la información tanto de productos como de servicios
	private void abrirDialogoInfoAlmacen(String nombreProducto) {
	    try {
	        Almacen producto = almacenDao.obtenerProductoPorNombre(nombreProducto); // Obtener el producto por su nombre
	        if (producto != null) {
	            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // Buscar el JFrame ancestro
	            DialogoInfoAlmacen dialogo = new DialogoInfoAlmacen(frame, producto); // Corrección aquí
	            dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialogo.setVisible(true);
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al obtener el producto: " + ex.getMessage(), "Error de Obtención", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void inicializarComponentesServicios(JPanel panel) {
	    modeloTablaServicios = new DefaultTableModel(new Object[]{"Nombre Servicio", "Categoría", "Cantidad Stock", "Precio Bruto"}, 0) {
	        public boolean isCellEditable(int row, int column) {
	            return false; // Hacer que la tabla no sea editable
	        }
	    };

	    tablaServicios = new JTable(modeloTablaServicios);
	    tablaServicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane scrollPaneServicios = new JScrollPane(tablaServicios);
	    scrollPaneServicios.setBounds(0, 48, 1107, 578);
	    panel.add(scrollPaneServicios);
	    
	    // Añadir MouseListener para doble clic
	    tablaServicios.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) { // Verificar si fue un doble clic
	                JTable target = (JTable)e.getSource();
	                int row = target.getSelectedRow();
	                if (row != -1) {  // Asegurar que una fila está seleccionada
	                    String nombreServicio = (String)target.getModel().getValueAt(row, 0); // Obtener el nombre del servicio
	                    abrirDialogoInfoAlmacen(nombreServicio); // Usar el mismo método que para los productos
	                }
	            }
	        }
	    });
	    
	    almacenDao = new AlmacenDAO();
	    cargarDatosServicios();
	}
	private void inicializarComponentesFarmacos(JPanel panel) {
	    modeloTablaFarmacos = new DefaultTableModel(new Object[]{"Nombre", "Cantidad", "Fecha Caducidad", "Precio"}, 0) {
	        public boolean isCellEditable(int row, int column) {
	            return false; // Hacer que la tabla no sea editable
	        }
	    };

	    tablaFarmacos = new JTable(modeloTablaFarmacos);
	    tablaFarmacos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane scrollPaneFarmacos = new JScrollPane(tablaFarmacos);
	    scrollPaneFarmacos.setBounds(0, 48, 1107, 578);
	    panel.add(scrollPaneFarmacos);

	    // Añadir MouseListener para doble clic
	    tablaFarmacos.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) { // Verificar si fue un doble clic
	                JTable target = (JTable) e.getSource();
	                int row = target.getSelectedRow();
	                if (row != -1) {  // Asegurar que una fila está seleccionada
	                    abrirDialogoInfoFarmaco(row); // Pasar el índice de la fila seleccionada
	                }
	            }
	        }
	    });

	    cargarDatosFarmacos(); // Cargar datos en la tabla
	}
	private void cargarDatosFarmacos() {
	    try {
	        // Usar farmacoDao para obtener los farmacos ordenados
	        List<Farmaco> farmacos = farmacoDao.obtenerFarmacosOrdenados(); // Asegúrate de que este método existe en FarmacoDAO
	        actualizarTablaFarmacos(farmacos);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al cargar datos de fármacos: " + ex.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void actualizarTablaFarmacos(List<Farmaco> farmacos) {
	    modeloTablaFarmacos.setRowCount(0);
	    for (Farmaco farmaco : farmacos) {
	        modeloTablaFarmacos.addRow(new Object[]{
	            farmaco.getNombre(),
	            farmaco.getCantidad(),
	            farmaco.getFechaCaducidad().toString(),
	            farmaco.getPrecio().toPlainString()
	        });
	    }
	}
	private void abrirDialogoInfoFarmaco(int rowIndex) {
	    // Obtener los datos de la fila seleccionada
	    String nombre = (String) modeloTablaFarmacos.getValueAt(rowIndex, 0);
	    
	    try {
	        // Buscar el objeto Farmaco por nombre (o por otro criterio si es más adecuado)
	        Farmaco farmaco = farmacoDao.buscarFarmacoPorNombre(nombre); // Debes implementar este método en FarmacoDAO
	        
	        if (farmaco != null) {
	            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // Obtener el JFrame padre
	            DialogoInfoFarmaco dialogo = new DialogoInfoFarmaco(frame, farmaco); // Crear el diálogo con el Farmaco
	            dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialogo.setVisible(true);
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al obtener los detalles del fármaco: " + ex.getMessage(), "Error de Obtención", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void cargarDatosProductos() {
	    try {
	        List<Almacen> productos = almacenDao.obtenerProductosFiltradosYOrdenados();
	        actualizarTablaProductos(productos);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al cargar datos de productos: " + ex.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void cargarDatosServicios() {
	    try {
	        List<Almacen> servicios = almacenDao.obtenerServiciosFiltradosYOrdenados();
	        actualizarTablaServicios(servicios);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al cargar datos de servicios: " + ex.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
 // Método para buscar productos por nombre y cargarlos en la tabla
    private void buscarProductosPorNombre(String nombre) throws SQLException {
        List<Almacen> productos = almacenDao.buscarProductosPorNombre(nombre);
        modeloTablaProductos.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        for (Almacen almacen : productos) {
            modeloTablaProductos.addRow(new Object[]{
                almacen.getNombreProducto(),
                almacen.getFechaCaducidad(),
                almacen.getCantidadStock()
            });
        }
    }
    private void buscarServicio() {
        try {
            String nombreServicio = buscarServicio.getText().trim();
            if (!nombreServicio.isEmpty() && !nombreServicio.equals("Buscar Servicio")) {
                List<Almacen> servicios = almacenDao.buscarServiciosPorNombre(nombreServicio);
                actualizarTablaServicios(servicios);
            } else {
                cargarDatosServicios();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(PanelAlmacen.this, "Error al buscar servicios: " + ex.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarTablaProductos(List<Almacen> productos) {
        modeloTablaProductos.setRowCount(0);
        for (Almacen almacen : productos) {
            modeloTablaProductos.addRow(new Object[]{
                almacen.getNombreProducto(),
                almacen.getCategoria().name(),
                almacen.getFechaUltimaCompra(),
                almacen.getFechaCaducidad(),
                almacen.getCantidadStock()
            });
        }
    }
    private void actualizarTablaServicios(List<Almacen> servicios) {
        modeloTablaServicios.setRowCount(0);
        for (Almacen servicio : servicios) {
            modeloTablaServicios.addRow(new Object[]{
                servicio.getNombreProducto(),
                servicio.getCategoria().name(),
                servicio.getCantidadStock(),
                servicio.getPrecioBruto().toPlainString() // Agrega el precio bruto como cadena
            });
        }
    }
    private void abrirDialogoRegistroAlmacen() {
        DialogoRegistroAlmacen dialogo = new DialogoRegistroAlmacen();
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setVisible(true);
    }
    private void abrirDialogoRegistroFarmaco() {
        DialogoRegistroFarmaco dialogo = new DialogoRegistroFarmaco();
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setVisible(true);
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

