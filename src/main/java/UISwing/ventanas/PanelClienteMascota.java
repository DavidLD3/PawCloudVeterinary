package UISwing.ventanas;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import DB.ClienteDAO;
import model.Cliente;
import DB.MascotaDAO;
import model.Mascota;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import UISwing.recursos.RoundedPanel;


public class PanelClienteMascota extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtBuscarClientemascota;
    private JTextField txtBuscarCliente;
    private JTable tablaClientes; 
    private JTable tablaMascotas; 
    private DefaultTableModel modeloTablaClientes;
    private ClienteDAO clienteDao;
    private DefaultTableModel modeloTablaMascotas;
    private MascotaDAO mascotaDAO;
    private JScrollPane scrollPaneClientes;
 
    public PanelClienteMascota() {
    	  setLayout(new BorderLayout());
          setBackground(new Color(255, 255, 255, 0));

          clienteDao = new ClienteDAO();
          mascotaDAO = new MascotaDAO();

          RoundedPanel roundedBackground = new RoundedPanel(20);
          roundedBackground.setLayout(new BorderLayout());
          roundedBackground.setBackground(Color.decode("#577BD1"));
          roundedBackground.setOpaque(false);
          add(roundedBackground, BorderLayout.CENTER);

          JPanel panel = new JPanel();
          panel.setLayout(null);
          panel.setOpaque(false);
          roundedBackground.add(panel, BorderLayout.CENTER);

        JButton btnAnadir = new JButton("Registrar Cliente");
        btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAnadir.setBounds(336, 44, 144, 30);
        btnAnadir.setBackground(Color.WHITE);
        btnAnadir.setForeground(Color.decode("#0057FF"));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setBorderPainted(false);
        btnAnadir.setContentAreaFilled(false);
        btnAnadir.setOpaque(true);
        btnAnadir.setRolloverEnabled(true);
        btnAnadir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAnadir.setBackground(Color.decode("#003366"));
                btnAnadir.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAnadir.setBackground(Color.WHITE);
                btnAnadir.setForeground(Color.decode("#0057FF"));
            }
        });
        panel.setLayout(null);
        panel.add(btnAnadir);
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                PanelRegistroCliente dialog = new PanelRegistroCliente((Frame) SwingUtilities.getWindowAncestor(PanelClienteMascota.this), "Registro de Cliente", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(578, 450);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });


        txtBuscarClientemascota = new JTextField("Escribe el nombre de la mascota");
        txtBuscarClientemascota.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        txtBuscarClientemascota.setBounds(579, 43, 235, 30);
        txtBuscarClientemascota.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtBuscarClientemascota.getText().equals("Escribe el nombre de la mascota")) {
                    txtBuscarClientemascota.setText("");
                }
            }
        });
        txtBuscarClientemascota.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarClientemascota.getText().isEmpty()) {
                    txtBuscarClientemascota.setText("Escribe el nombre de la mascota");
                    cargarDatosMascotas();  // Método para cargar todos los datos de las mascotas
                }
            }
        });
        txtBuscarClientemascota.addActionListener(e -> {
            String searchText = txtBuscarClientemascota.getText().trim().toLowerCase();

            boolean found = false;
            for (int row = 0; row < tablaMascotas.getRowCount(); row++) {
                String nombreMascota = tablaMascotas.getValueAt(row, 0).toString().toLowerCase();
                if (nombreMascota.equals(searchText)) {

                    tablaMascotas.setRowSelectionInterval(row, row);
                    
                    Rectangle rect = tablaMascotas.getCellRect(row, 0, true);
                    tablaMascotas.scrollRectToVisible(rect);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                tablaMascotas.clearSelection();
            }
        });
        panel.add(txtBuscarClientemascota);

        txtBuscarCliente = new JTextField("Escribe el nombre del cliente"); 
        txtBuscarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        txtBuscarCliente.setBounds(10, 43, 235, 30);
        txtBuscarCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if (txtBuscarCliente.getText().equals("Escribe el nombre del cliente")) {
                    txtBuscarCliente.setText("");
                }
            }
        });
        txtBuscarCliente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarCliente.getText().isEmpty()) {
                    txtBuscarCliente.setText("Escribe el nombre del cliente");
                    cargarDatosClientes();
                }
            }
        });
        txtBuscarClientemascota.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                realizarBusqueda();
            }

            public void removeUpdate(DocumentEvent e) {
                realizarBusqueda();
            }

            public void changedUpdate(DocumentEvent e) {
              
            }

            private void realizarBusqueda() {
            	 String searchText = txtBuscarClientemascota.getText().trim().toLowerCase();
                 if (!searchText.isEmpty()) {
                     List<Mascota> resultados = mascotaDAO.buscarMascotasPorNombreConDueño(searchText);
                     actualizarTablaMascotas(resultados);
                 } else {
                     cargarDatosMascotas();
                 }
             }
         });
        txtBuscarCliente.addActionListener(e -> {
            String searchText = txtBuscarCliente.getText().trim().toLowerCase();
            if (searchText.isEmpty()) {
                List<Cliente> todosLosClientes = clienteDao.obtenerTodosLosClientes();
                actualizarTablaClientes(todosLosClientes);
            } else {
                List<Cliente> clientesFiltrados = clienteDao.buscarClientes(searchText);
                actualizarTablaClientes(clientesFiltrados);

                
                if (!clientesFiltrados.isEmpty()) {
                    tablaClientes.setRowSelectionInterval(0, 0);
                    Rectangle rect = tablaClientes.getCellRect(0, 0, true);
                    tablaClientes.scrollRectToVisible(rect);
                } else {
                    
                    tablaClientes.clearSelection();
                }
            }
        });
        txtBuscarCliente.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarCliente();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarCliente();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }

            private void buscarCliente() {
                String searchText = txtBuscarCliente.getText().trim().toLowerCase();
                if (searchText.isEmpty()) {
                    List<Cliente> todosLosClientes = clienteDao.obtenerTodosLosClientes();
                    actualizarTablaClientes(todosLosClientes);
                } else {
                    List<Cliente> clientesFiltrados = clienteDao.buscarClientes(searchText);
                    actualizarTablaClientes(clientesFiltrados);
                }
            }
        });

        panel.add(txtBuscarCliente);  

        inicializarComponentesClientes(panel);
        inicializarComponentesMascotas(panel); 
        scrollPaneClientes = new JScrollPane();
        scrollPaneClientes.setBounds(10, 105, 549, 537); 
        panel.add(scrollPaneClientes); 

        JScrollPane scrollPaneMascotas = new JScrollPane(); 
        scrollPaneMascotas.setBounds(579, 105, 518, 537);
        panel.add(scrollPaneMascotas);
        
        JLabel lblBuscarCliente = new JLabel("Buscar Cliente:");
        lblBuscarCliente.setForeground(new Color(255, 255, 255));
        lblBuscarCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBuscarCliente.setBounds(10, 25, 100, 14);
        panel.add(lblBuscarCliente);
        
        JLabel lblBuscasMascota = new JLabel("Buscar Mascota:");
        lblBuscasMascota.setForeground(new Color(255, 255, 255));
        lblBuscasMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBuscasMascota.setBounds(579, 25, 135, 14);
        panel.add(lblBuscasMascota);
        setPreferredSize(new Dimension(1112, 674)); 
    }

    private void inicializarComponentesClientes(JPanel panel) {
        modeloTablaClientes = new DefaultTableModel(new Object[]{"Nombre", "Apellidos", "DNI"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTablaClientes);
        personalizarTabla(tablaClientes);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String dni = (String) modeloTablaClientes.getValueAt(filaSeleccionada, 2); 
                        abrirPanelDetalleClientePorDni(dni);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBounds(10, 105, 549, 537); 
        panel.add(scrollPane);

        cargarDatosClientes();
    }
    
    private void inicializarComponentesMascotas(JPanel panel) {
    	 
    	modeloTablaMascotas = new DefaultTableModel(new Object[]{"Nombre", "Microchip", "Dueño"}, 0) {
    
    	    public boolean isCellEditable(int row, int column) {
    	        return false;  
    	    }
    	};

        tablaMascotas = new JTable(modeloTablaMascotas);
        personalizarTabla(tablaMascotas); 
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMascotas.addMouseListener(new MouseAdapter() {
  
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String microchip = (String) modeloTablaMascotas.getValueAt(filaSeleccionada, 1);  
                        abrirPanelDetalleMascotaPorMicrochip(microchip);
                    }
                }
            }
        });

        JScrollPane scrollPaneMascotas = new JScrollPane(tablaMascotas);
        scrollPaneMascotas.setBounds(579, 105, 518, 537);
        panel.add(scrollPaneMascotas);
        panel.add(scrollPaneMascotas);

        cargarDatosMascotas();
    }

    private void cargarDatosClientes() {
        List<Cliente> listaClientes = clienteDao.obtenerTodosLosClientes();
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0);

        for (Cliente cliente : listaClientes) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getDni()
            });
        }
    }
    
    private void cargarDatosMascotas() {
        try {
            List<Mascota> listaMascotas = mascotaDAO.obtenerMascotasOrdenadasPorNombreMicrochip();
            actualizarTablaMascotas(listaMascotas);
        } catch (SQLException e) {
            e.printStackTrace(); 
            
        }
    }

    private void abrirPanelDetalleClientePorDni(String dni) {
        PanelCliente panelCliente = new PanelCliente(dni);
        JFrame frame = new JFrame("Detalles del Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panelCliente);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // Método para abrir el panel de detalles de la mascota por microchip
    private void abrirPanelDetalleMascotaPorMicrochip(String microchip) {
        Mascota mascota = mascotaDAO.obtenerMascotaPorMicrochip(microchip);
        if (mascota != null) {
            JFrame frame = new JFrame("Detalles de la Mascota");
            PanelInfoMascota panelInfoMascota = new PanelInfoMascota(mascota.getId());
            frame.getContentPane().add(panelInfoMascota);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } 
    }
    private void actualizarTablaClientes(List<Cliente> listaClientes) {
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0);
        for (Cliente cliente : listaClientes) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getDni()
            });
        }
    }
    private void actualizarTablaMascotas(List<Mascota> listaMascotas) {
        DefaultTableModel modelo = (DefaultTableModel) tablaMascotas.getModel();
        modelo.setRowCount(0);
        for (Mascota mascota : listaMascotas) {
            modelo.addRow(new Object[]{
                mascota.getNombre(),
                mascota.getMicrochip(),
                mascota.getNombreDueño()
            });
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Cliente Mascota");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelClienteMascota panel = new PanelClienteMascota();
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private void configurarEstiloTablas() {
        
        UIManager.put("TableHeader.background", new Color(75, 110, 175));
        UIManager.put("TableHeader.foreground", Color.WHITE); 
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14)); 

        
        UIManager.put("Table.background", new Color(245, 245, 245)); 
        UIManager.put("Table.foreground", Color.BLACK);
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Table.selectionBackground", new Color(183, 207, 237));
        UIManager.put("Table.selectionForeground", Color.DARK_GRAY);
        UIManager.put("Table.gridColor", new Color(200, 200, 200));
    }
    private void personalizarTabla(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.setBackground(new Color(245, 245, 245));
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(183, 207, 237));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setGridColor(new Color(200, 200, 200));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(75, 110, 175));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}