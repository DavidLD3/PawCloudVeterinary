package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelEvent;
import java.util.List;
import model.Almacen;
import model.Cliente;
import model.Farmaco;
import model.Mascota;
import model.Mascota.MascotaContenedor;
import DB.ClienteDAO;
import DB.MascotaDAO;
import DB.VentasDAO;
import UISwing.recursos.RoundedPanel;

public class PanelVentas extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPrecio;
    private JComboBox<Cliente> comboBoxCliente;
    private JComboBox<Mascota.MascotaContenedor> comboBoxMascota;
    private ClienteDAO clienteDAO = new ClienteDAO();
    private MascotaDAO mascotaDAO = new MascotaDAO();
    private VentasDAO ventasDAO = new VentasDAO();
    private JCheckBox chckbxefectivo;  
    private JCheckBox chckbxTarjeta; 
    private boolean cargaInicialCompleta = false;

    public PanelVentas() {
        setLayout(null);
        setBackground(new Color(255, 255, 255, 0)); // Transparente
        

        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#577BD1"));
        panelVentas.setBounds(0, 0, 1112, 653);
        panelVentas.setLayout(null);
        add(panelVentas);

        // Configuración inicial de componentes
        initComponents(panelVentas);
        
      

        String[] columnNames = {"ID","Tipo","Producto", "Descripción", "Cantidad", "Precio Unitario", "Total"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:  // ID del producto
                        return Integer.class;
                    case 3:  // Cantidad
                    case 4:  // Precio unitario
                    case 5:  // Total
                        return BigDecimal.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;  // Permite editar solo la cantidad y el precio unitario
            }
        };


        table = new JTable(tableModel);
        TableColumn idColumn = table.getColumnModel().getColumn(0);  // Obtiene la columna de ID por su índice
        table.removeColumn(idColumn);
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                // Asegurarse que la columna modificada sea Cantidad (índice 4 según el modelo) o Precio Unitario (índice 5)
                if (column == 4 || column == 5) {
                    try {
                        BigDecimal cantidad = new BigDecimal(tableModel.getValueAt(row, 4).toString());
                        BigDecimal precioUnitario = new BigDecimal(tableModel.getValueAt(row, 5).toString());
                        BigDecimal total = cantidad.multiply(precioUnitario).setScale(2, RoundingMode.HALF_UP);
                        tableModel.setValueAt(total, row, 6); // Asegurarse que el índice de Total sea 6
                        actualizarTotal();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, introduzca un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Aplicar el renderizador a la columna de "Total"
        int totalColumnIndex = table.getColumnModel().getColumnIndex("Total"); // Obtén el índice visual correcto
        table.getColumnModel().getColumn(totalColumnIndex).setCellRenderer(rightRenderer);
        

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(24, 144, 1067, 360);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panelVentas.add(scrollPane);

        setUpButtons(panelVentas);
        setUpLabels(panelVentas);
    }

    private void initComponents(RoundedPanel panelVentas) {
        comboBoxCliente = new JComboBox<>();
        comboBoxCliente.setEditable(true);
        comboBoxCliente.setBounds(24, 100, 195, 31);
        JTextField textEditor = (JTextField) comboBoxCliente.getEditor().getEditorComponent();
        textEditor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textEditor.getText();
                if (text.length() >= 4) {
                    updateClientList(text);
                } else {
                    comboBoxMascota.removeAllItems(); // Limpia también las mascotas asociadas
                }
            }
        });

        comboBoxCliente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object selectedItem = e.getItem();
                    if (selectedItem instanceof Cliente) {
                        Cliente selectedClient = (Cliente) selectedItem;
                        updatePetList(selectedClient.getId());
                    } else {
                        comboBoxMascota.removeAllItems(); // Limpia el comboBox de mascotas si el objeto seleccionado no es un Cliente
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    comboBoxMascota.removeAllItems(); // También limpia las mascotas si no hay selección
                }
            }
        });


        // Setup combo box for pets
        comboBoxMascota = new JComboBox<>();
        comboBoxMascota.setBounds(269, 100, 195, 31);
        panelVentas.add(comboBoxCliente);
        panelVentas.add(comboBoxMascota);
        
        
        chckbxefectivo = new JCheckBox("Efectivo");
        chckbxefectivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chckbxefectivo.setForeground(Color.WHITE);
        chckbxefectivo.setBounds(800, 526, 72, 23);
        chckbxefectivo.setOpaque(false); // Hacer el fondo del checkbox transparente
        chckbxefectivo.setBackground(new Color(0, 0, 0, 0)); // Asegurarse de que el fondo es completamente transparente
        panelVentas.add(chckbxefectivo);
      
        
        chckbxTarjeta = new JCheckBox("Tarjeta");
        chckbxTarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chckbxTarjeta.setForeground(Color.WHITE);
        chckbxTarjeta.setBounds(873, 526, 65, 23);
        chckbxTarjeta.setOpaque(false); // Hacer el fondo del checkbox transparente
        chckbxTarjeta.setBackground(new Color(0, 0, 0, 0)); // Asegurarse de que el fondo es completamente transparente
        panelVentas.add(chckbxTarjeta);
    }

    private void updateClientList(String searchQuery) {
        SwingWorker<List<Cliente>, Void> worker = new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() throws Exception {
                return clienteDAO.buscarClientesPorApellido(searchQuery);
            }
            @Override
            protected void done() {
                try {
                    List<Cliente> clients = get();
                    comboBoxCliente.removeAllItems();
                    for (Cliente client : clients) {
                        comboBoxCliente.addItem(client);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void updatePetList(int clientId) {
        SwingWorker<List<Mascota>, Void> worker = new SwingWorker<List<Mascota>, Void>() {
            @Override
            protected List<Mascota> doInBackground() throws Exception {
                return mascotaDAO.obtenerMascotasPorClienteId(clientId);
            }
            @Override
            protected void done() {
                try {
                    List<Mascota> pets = get();
                    comboBoxMascota.removeAllItems();
                    for (Mascota pet : pets) {
                        comboBoxMascota.addItem(new Mascota.MascotaContenedor(pet));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar las mascotas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();  // Esto imprimirá el rastreo de pila en la consola si hay un error
                }
            }
        };
        worker.execute();
    }


    private void setUpButtons(JPanel panel) {
        // Configuración de botones, similar a lo que ya tenías
    	 JButton btnAñadirProducto = new JButton("Añadir Producto");
         btnAñadirProducto.setFont(new Font("Segoe UI", Font.BOLD, 12));
         btnAñadirProducto.setBounds(865, 99, 210, 31);
         btnAñadirProducto.setBackground(Color.WHITE);
         btnAñadirProducto.setForeground(Color.decode("#0057FF"));
         btnAñadirProducto.setFocusPainted(false);
         btnAñadirProducto.setBorderPainted(false);
         btnAñadirProducto.setContentAreaFilled(false);
         btnAñadirProducto.setOpaque(true);
         btnAñadirProducto.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 abrirDialogoDeProducto();
             }
         });
         panel.add(btnAñadirProducto);
         
         JButton btnLimpiarDatos = new JButton("Limpiar Datos");
         btnLimpiarDatos.setBounds(34, 590, 148, 23);
         btnLimpiarDatos.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 limpiarTabla();
             }
         });

         btnLimpiarDatos.setFont(new Font("Segoe UI", Font.BOLD, 12));
         btnLimpiarDatos.setBounds(34, 590, 148, 31);
         btnLimpiarDatos.setBackground(Color.WHITE);
         btnLimpiarDatos.setForeground(Color.decode("#0057FF")); // Letras en color azul
         btnLimpiarDatos.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
         btnLimpiarDatos.setBorderPainted(false); // Evita que se pinte el borde predeterminado
         btnLimpiarDatos.setContentAreaFilled(false); // Evita que se pinte el área de contenido
         btnLimpiarDatos.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

         // Personalización del efecto rollover
         btnLimpiarDatos.setRolloverEnabled(true);
         btnLimpiarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
             @Override
             public void mouseEntered(java.awt.event.MouseEvent evt) {
                 btnLimpiarDatos.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                 btnLimpiarDatos.setForeground(Color.WHITE);
             }

             @Override
             public void mouseExited(java.awt.event.MouseEvent evt) {
                 btnLimpiarDatos.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                 btnLimpiarDatos.setForeground(Color.decode("#0057FF"));
             }
         });
         
         panel.add(btnLimpiarDatos);

              
         JButton btnValidar = new JButton("Validar");
         btnValidar.setBounds(927, 590, 148, 31);
         btnValidar.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         	}
         });
         btnValidar.setFont(new Font("Segoe UI", Font.BOLD, 12));
         btnValidar.setBackground(Color.WHITE);
         btnValidar.setForeground(Color.decode("#0057FF")); // Letras en color azul
         btnValidar.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
         btnValidar.setBorderPainted(false); // Evita que se pinte el borde predeterminado
         btnValidar.setContentAreaFilled(false); // Evita que se pinte el área de contenido
         btnValidar.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

         // Personalización del efecto rollover
         btnValidar.setRolloverEnabled(true);
         btnValidar.addMouseListener(new java.awt.event.MouseAdapter() {
             @Override
             public void mouseEntered(java.awt.event.MouseEvent evt) {
                 btnValidar.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                 btnValidar.setForeground(Color.WHITE);
             }

             @Override
             public void mouseExited(java.awt.event.MouseEvent evt) {
                 btnValidar.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                 btnValidar.setForeground(Color.decode("#0057FF"));
             }
         });
         btnValidar.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	        registrarVenta();
        	    }
        	});

         panel.add(btnValidar);

    }

    private void setUpLabels(JPanel panel) {
        // Configuración de etiquetas, similar a lo que ya tenías
    	JLabel lbltextoVentas = new JLabel("Ventas");
        lbltextoVentas.setForeground(Color.WHITE);
        lbltextoVentas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoVentas.setBounds(48, 21, 171, 31);
        panel.add(lbltextoVentas);

       

        JLabel lblLogoPanelVentas = new JLabel("");
        lblLogoPanelVentas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelVentas.png")));
        lblLogoPanelVentas.setBounds(24, 21, 22, 31);
        panel.add(lblLogoPanelVentas);

     
        lblTotalPrecio = new JLabel("0.00");
        lblTotalPrecio.setBounds(1021, 523, 46, 31);
        lblTotalPrecio.setForeground(Color.WHITE);
        lblTotalPrecio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lblTotalPrecio);

     
        
        JLabel lblNewLabel_1 = new JLabel("Total:");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel_1.setBounds(978, 523, 36, 29);
        panel.add(lblNewLabel_1);

    

        JLabel lblNewLabel = new JLabel("Metodo de pago:");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel.setBounds(683, 530, 111, 14);
        panel.add(lblNewLabel);
        
      
 
       
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setBounds(24, 75, 78, 14);
        panel.add(lblCliente);

      
        
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setBounds(269, 75, 99, 14);
        panel.add(lblMascota);

    }

    private void abrirDialogoDeProducto() {
        ProductoSelectorDialog dialog = new ProductoSelectorDialog(JFrame.getFrames()[0], this, true); // Pass `this` as the ownerPanel
        dialog.setVisible(true);
    }

 // Método para añadir productos a la tabla desde el diálogo
    public void addProductToTable(Object product) {
        String tipoProducto = null;
        Integer idProducto = null;
        String nombre = null;
        String descripcion = null;
        BigDecimal precioUnitario = BigDecimal.ZERO;

        if (product instanceof Almacen) {
            Almacen almacen = (Almacen) product;
            tipoProducto = "Almacen";
            idProducto = almacen.getIdAlmacen();
            nombre = almacen.getNombreProducto();
            descripcion = almacen.getDescripcion();
            precioUnitario = almacen.getPrecioBruto();
        } else if (product instanceof Farmaco) {
            Farmaco farmaco = (Farmaco) product;
            tipoProducto = "Farmaco";
            idProducto = farmaco.getId();
            nombre = farmaco.getNombre();
            descripcion = farmaco.getDescripcion();
            precioUnitario = farmaco.getPrecio();
        }

        if (tipoProducto != null) {
            Object[] rowData = {
                idProducto,  // ID del producto
                tipoProducto,  // Tipo de producto
                nombre,  // Nombre del producto
                descripcion,  // Descripción
                1,  // Cantidad inicial
                precioUnitario,  // Precio unitario
                precioUnitario  // Total inicial
            };
            tableModel.addRow(rowData);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(null, "Producto desconocido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    

    private void actualizarTotal() {
        BigDecimal totalGeneral = BigDecimal.ZERO;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            BigDecimal totalFila = new BigDecimal(tableModel.getValueAt(i, 5).toString());
            totalGeneral = totalGeneral.add(totalFila);
        }
        lblTotalPrecio.setText(totalGeneral.setScale(2, RoundingMode.HALF_UP).toString() + " €");
    }

    private void limpiarTabla() {
        tableModel.setRowCount(0); // Elimina todas las filas
        actualizarTotal(); // Actualiza el total para reflejar que la tabla está vacía
    }
    
    private void registrarVenta() {
        try {
            Integer idCliente = null;
            Integer idMascota = null;
            BigDecimal total = new BigDecimal(lblTotalPrecio.getText().replace(" €", ""));
            Date fechaVenta = new Date(System.currentTimeMillis());
            String metodoPago = obtenerMetodoPagoSeleccionado();

            Cliente cliente = (Cliente) comboBoxCliente.getSelectedItem();
            if (cliente != null) {
                idCliente = cliente.getId();
            }

            Mascota.MascotaContenedor mascotaContenedor = (Mascota.MascotaContenedor) comboBoxMascota.getSelectedItem();
            if (mascotaContenedor != null) {
                idMascota = mascotaContenedor.getMascota().getId();
            }

            int idVenta = ventasDAO.insertarVenta(idCliente, idMascota, fechaVenta, metodoPago, total);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Integer idProducto = (Integer) tableModel.getValueAt(i, 0); // ID de producto
                String tipoProducto = (String) tableModel.getValueAt(i, 1); // Tipo de producto
                int cantidad = ((Number) tableModel.getValueAt(i, 4)).intValue();
                BigDecimal precioUnitario = (BigDecimal) tableModel.getValueAt(i, 5);

                Integer idAlmacen = tipoProducto.equals("Almacen") ? idProducto : null;
                Integer idFarmaco = tipoProducto.equals("Farmaco") ? idProducto : null;

                ventasDAO.insertarDetalleVenta(idVenta, idAlmacen, idFarmaco, cantidad, precioUnitario);
            }

            JOptionPane.showMessageDialog(this, "Venta registrada con éxito", "Venta Registrada", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }















    private String obtenerMetodoPagoSeleccionado() {
        if (chckbxefectivo.isSelected()) {
            return "Efectivo";
        } else if (chckbxTarjeta.isSelected()) {
            return "Tarjeta";
        }
        return "Desconocido"; // O maneja esta situación como prefieras
    }
    
    
    
}
