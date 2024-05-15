package UISwing.ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import DB.AlmacenDAO;
import DB.FarmacoDAO;
import model.Almacen;
import model.Farmaco;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

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
    private DefaultTableModel modeloTablaServicios;
    private DefaultTableModel modeloTablaFarmacos;
    private FarmacoDAO farmacoDao;

    public PanelAlmacen() {

        setPreferredSize(new Dimension(1112, 653));
        setLayout(null);
        setOpaque(false);
        farmacoDao = new FarmacoDAO();
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 1112, 653);
        add(tabbedPane);

        JPanel gestionProductos = new JPanel();
        gestionProductos.setBackground(new Color(204, 229, 255));
        gestionProductos.setOpaque(true);
        tabbedPane.addTab("Gestion de Productos", null, gestionProductos, null);
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
        añadirProductoServicio.setFont(new Font("Tahoma", Font.BOLD, 12));
        añadirProductoServicio.setBackground(Color.WHITE);
        añadirProductoServicio.setForeground(Color.decode("#0057FF"));
        añadirProductoServicio.setFocusPainted(false);
        añadirProductoServicio.setBorderPainted(false);
        añadirProductoServicio.setContentAreaFilled(false);
        añadirProductoServicio.setOpaque(true);
        añadirProductoServicio.setRolloverEnabled(true);
        añadirProductoServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                añadirProductoServicio.setBackground(Color.decode("#003366"));
                añadirProductoServicio.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                añadirProductoServicio.setBackground(Color.WHITE);
                añadirProductoServicio.setForeground(Color.decode("#0057FF"));
            }
        });
        añadirProductoServicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDialogoRegistroAlmacen();
            }
        });

        gestionProductos.add(añadirProductoServicio);

        scrollPaneProductos = new JScrollPane();
        scrollPaneProductos.setBounds(0, 48, 1107, 577);
        scrollPaneProductos.getViewport().setBackground(new Color(230, 242, 255));
        scrollPaneProductos.setBackground(new Color(200, 220, 255));
        gestionProductos.add(scrollPaneProductos);

        tablaProductos = new JTable();
        scrollPaneProductos.setViewportView(tablaProductos);

        JPanel gestionServicios = new JPanel();
        tabbedPane.addTab("Gestion de Servicios", null, gestionServicios, null);
        gestionServicios.setLayout(null);

        inicializarComponentesServicios(gestionServicios);
        buscarServicio = new JTextField();
        buscarServicio.setText("Buscar Servicio");
        buscarServicio.setBounds(0, 11, 130, 20);
        gestionServicios.add(buscarServicio);
        buscarServicio.setColumns(10);

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
                        List<Almacen> servicios = almacenDao.buscarServiciosPorNombre(nombreServicio);
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
                    List<Farmaco> farmacos = farmacoDao.buscarFarmacos(texto);
                    actualizarTablaFarmacos(farmacos);
                } else {
                    cargarDatosFarmacos();
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
        añadirFarmaco.setFont(new Font("Tahoma", Font.BOLD, 12));
        añadirFarmaco.setBackground(Color.WHITE);
        añadirFarmaco.setForeground(Color.decode("#0057FF"));
        añadirFarmaco.setFocusPainted(false);
        añadirFarmaco.setBorderPainted(false);
        añadirFarmaco.setContentAreaFilled(false);
        añadirFarmaco.setOpaque(true);
        añadirFarmaco.setRolloverEnabled(true);
        añadirFarmaco.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                añadirFarmaco.setBackground(Color.decode("#003366"));
                añadirFarmaco.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                añadirFarmaco.setBackground(Color.WHITE);
                añadirFarmaco.setForeground(Color.decode("#0057FF"));
            }
        });
        añadirFarmaco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDialogoRegistroFarmaco();
            }
        });

        gestionFarmacos.add(añadirFarmaco);
        inicializarComponentesProductos(gestionProductos);
    }

    private void inicializarComponentesProductos(JPanel panel) {
        modeloTablaProductos = new DefaultTableModel(new Object[]{"Nombre Producto", "Categoría", "Fecha Última Compra", "Fecha Caducidad", "Cantidad Stock"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTablaProductos);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    TableModel model = target.getModel();
                    String nombreProducto = (String) model.getValueAt(row, 0);
                    abrirDialogoInfoAlmacen(nombreProducto);
                }
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaProductos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        tablaProductos.getColumnModel().getColumn(3).setCellRenderer(new FechaCaducidadRenderer());
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new RightAlignedRenderer());

        JTableHeader header = tablaProductos.getTableHeader();
        header.setBackground(new Color(75, 110, 175)); 
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPaneProductos.setViewportView(tablaProductos);
        almacenDao = new AlmacenDAO();
        cargarDatosProductos();
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        legendPanel.setBounds(800, 10, 300, 30);
        legendPanel.setOpaque(false);

        addColorLegend(legendPanel, Color.RED, "Caducado");
        addColorLegend(legendPanel, Color.YELLOW, "<1 semana");
        addColorLegend(legendPanel, Color.GREEN, ">1 semana");
        addColorLegend(legendPanel, Color.WHITE, "Sin caducidad");

        panel.add(legendPanel);
    }


    // Método para abrir el DialogoInfoAlmacen con la información del producto seleccionado
    private void abrirDialogoInfoAlmacen(String nombreProducto) {
        try {
            Almacen producto = almacenDao.obtenerProductoPorNombre(nombreProducto);
            if (producto != null) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                DialogoInfoAlmacen dialogo = new DialogoInfoAlmacen(frame, producto);
                dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialogo.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el producto: " + ex.getMessage(), "Error de Obtención", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarComponentesServicios(JPanel panel) {
        panel.setBackground(new Color(204, 229, 255));
        panel.setOpaque(true);
        modeloTablaServicios = new DefaultTableModel(new Object[] { "Nombre Servicio", "Categoría", "Precio Bruto" }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaServicios = new JTable(modeloTablaServicios);
        tablaServicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader headerServicios = tablaServicios.getTableHeader();
        headerServicios.setBackground(new Color(75, 110, 175));
        headerServicios.setForeground(Color.WHITE);
        headerServicios.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaServicios.getColumnModel().getColumn(2).setCellRenderer(new PrecioRenderer());

        JScrollPane scrollPaneServicios = new JScrollPane(tablaServicios);
        scrollPaneServicios.setBounds(0, 48, 1107, 578);
        scrollPaneServicios.getViewport().setBackground(new Color(230, 242, 255)); 
        scrollPaneServicios.setBackground(new Color(200, 220, 255));
        panel.add(scrollPaneServicios);	

        tablaServicios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String nombreServicio = (String) target.getModel().getValueAt(row, 0);
                        abrirDialogoInfoAlmacen(nombreServicio);
                    }
                }
            }
        });

        almacenDao = new AlmacenDAO();
        cargarDatosServicios();
    }



    private void inicializarComponentesFarmacos(JPanel panel) {
        panel.setBackground(new Color(204, 229, 255));
        panel.setOpaque(true);
        modeloTablaFarmacos = new DefaultTableModel(new Object[] { "Nombre", "Cantidad", "Fecha Caducidad", "Precio" }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaFarmacos = new JTable(modeloTablaFarmacos);
        tablaFarmacos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaFarmacos.getColumnModel().getColumn(2).setCellRenderer(new FechaCaducidadRenderer());

        JTableHeader headerFarmacos = tablaFarmacos.getTableHeader();
        headerFarmacos.setBackground(new Color(75, 110, 175));
        headerFarmacos.setForeground(Color.WHITE);
        headerFarmacos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaFarmacos.getColumnModel().getColumn(1).setCellRenderer(new RightAlignedRenderer());
        tablaFarmacos.getColumnModel().getColumn(3).setCellRenderer(new PrecioRenderer());

        JScrollPane scrollPaneFarmacos = new JScrollPane(tablaFarmacos);
        scrollPaneFarmacos.setBounds(0, 48, 1107, 578);
        scrollPaneFarmacos.getViewport().setBackground(new Color(230, 242, 255));
        scrollPaneFarmacos.setBackground(new Color(200, 220, 255));
        panel.add(scrollPaneFarmacos);
        tablaFarmacos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) { 
                        abrirDialogoInfoFarmaco(row);
                    }
                }
            }
        });

        cargarDatosFarmacos();
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        legendPanel.setBounds(800, 10, 300, 30);
        legendPanel.setOpaque(false);

        addColorLegend(legendPanel, Color.RED, "Caducado");
        addColorLegend(legendPanel, Color.YELLOW, "<1 semana");
        addColorLegend(legendPanel, Color.GREEN, ">1 semana");
        addColorLegend(legendPanel, Color.WHITE, "Sin caducidad");

        panel.add(legendPanel);
    }


    private void cargarDatosFarmacos() {
        try {
            List<Farmaco> farmacos = farmacoDao.obtenerFarmacosOrdenados();
            actualizarTablaFarmacos(farmacos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de fármacos: " + ex.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaFarmacos(List<Farmaco> farmacos) {
        modeloTablaFarmacos.setRowCount(0);
        for (Farmaco farmaco : farmacos) {
            Object[] fila = new Object[4];
            fila[0] = farmaco.getNombre();
            fila[1] = farmaco.getCantidad();

            LocalDate fechaCaducidad = farmaco.getFechaCaducidad() != null ? farmaco.getFechaCaducidad().toLocalDate() : null;
            fila[2] = (fechaCaducidad != null) ? fechaCaducidad.toString() : "";

            fila[3] = farmaco.getPrecio().toPlainString();
            modeloTablaFarmacos.addRow(fila);
        }
    }

    private void abrirDialogoInfoFarmaco(int rowIndex) {
        String nombre = (String) modeloTablaFarmacos.getValueAt(rowIndex, 0);

        try {
            Farmaco farmaco = farmacoDao.buscarFarmacoPorNombre(nombre);

            if (farmaco != null) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                DialogoInfoFarmaco dialogo = new DialogoInfoFarmaco(frame, farmaco);
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
        modeloTablaProductos.setRowCount(0);

        for (Almacen almacen : productos) {
            modeloTablaProductos.addRow(new Object[] {
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
            modeloTablaProductos.addRow(new Object[] {
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
            Object[] fila = new Object[] {
                    servicio.getNombreProducto(),
                    servicio.getCategoria().name(),
                    servicio.getPrecioBruto().toPlainString()
            };
            modeloTablaServicios.addRow(fila);
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
    class FechaCaducidadRenderer extends DefaultTableCellRenderer {
        private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                try {
                    LocalDate fecha = LocalDate.parse(value.toString());
                    value = fecha.format(formatoFecha);
                    LocalDate ahora = LocalDate.now();
                    if (fecha.isBefore(ahora)) {
                        setBackground(Color.RED);
                    } else if (fecha.isBefore(ahora.plusWeeks(1))) {
                        setBackground(Color.YELLOW);
                    } else {
                        setBackground(Color.GREEN);
                    }
                } catch (DateTimeParseException e) {
                    setBackground(Color.WHITE);
                }
            } else {
                setBackground(Color.WHITE);
            }

            setHorizontalAlignment(JLabel.CENTER);
            setForeground(Color.BLACK);

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class FechaRenderer extends DefaultTableCellRenderer {
        private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                try {
                    LocalDate fecha = LocalDate.parse(value.toString());
                    value = fecha.format(formatoFecha);
                } catch (DateTimeParseException e) {
                    
                }
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class PrecioRenderer extends DefaultTableCellRenderer {
        private NumberFormat formatoPrecio = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                try {
                    double precio = Double.parseDouble(value.toString());
                    value = String.format("%s €", formatoPrecio.format(precio).replace("€", "").trim());
                } catch (NumberFormatException e) {
                
                }
            }
            setHorizontalAlignment(JLabel.RIGHT);
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }


    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }
    }

    private void addColorLegend(JPanel panel, Color color, String text) {
        JLabel label = new JLabel(text, new ColorIcon(color), JLabel.LEFT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(label);
    }

    class ColorIcon implements Icon {
        private final int ICON_SIZE = 10;
        private Color color;

        public ColorIcon(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
        }

        @Override
        public int getIconWidth() {
            return ICON_SIZE;
        }

        @Override
        public int getIconHeight() {
            return ICON_SIZE;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Almacén");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        PanelAlmacen panelAlmacen = new PanelAlmacen();
        frame.getContentPane().add(panelAlmacen);
        frame.setVisible(true);
    }
}
