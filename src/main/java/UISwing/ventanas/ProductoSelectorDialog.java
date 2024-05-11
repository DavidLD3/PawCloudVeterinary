package UISwing.ventanas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.VentasDAO;
import model.Almacen;
import model.Farmaco;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoSelectorDialog extends JDialog {
    private JTable productTable;
    private DefaultTableModel model;
    private JTextField searchField;
    private JButton selectButton;
    private List<Object> productos;
    private PanelVentas ownerPanel;

    public ProductoSelectorDialog(Frame owner, PanelVentas ownerPanel, boolean modal) {
        super(owner, modal);
        this.ownerPanel = ownerPanel;
        setTitle("Seleccionar Producto");
        setSize(600, 400);
        setLocationRelativeTo(owner);
        productos = new ArrayList<>();
        initComponents();
        
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/MediaPawcloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        searchField = new JTextField(20);
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Buscar:"));
        topPanel.add(searchField);
        getContentPane().add(topPanel, BorderLayout.NORTH);


        model = new DefaultTableModel(new String[]{"Producto", "Precio", "Descripción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        productTable = new JTable(model);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        JScrollPane scrollPane = new JScrollPane(productTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        selectButton = new JButton("Agregar Producto");
        selectButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectButton.setBackground(Color.WHITE);
        selectButton.setForeground(Color.decode("#0057FF")); 
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setOpaque(true);
        selectButton.setRolloverEnabled(true);

        
        selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(Color.decode("#003366")); 
                selectButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(Color.WHITE);
                selectButton.setForeground(Color.decode("#0057FF"));
            }
        });

        selectButton.addActionListener(e -> agregarProductoSeleccionado());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateProductList(searchField.getText());
            }
        });
    }


    private void updateProductList(String text) {
        try {
            VentasDAO ventasDao = new VentasDAO();
            List<Object> productosYFarmacos = ventasDao.buscarProductosYFarmacos(text);
            model.setRowCount(0);
            productos.clear(); 
            for (Object item : productosYFarmacos) {
                productos.add(item); 
                if (item instanceof Almacen) {
                    Almacen prod = (Almacen) item;
                    model.addRow(new Object[]{prod.getNombreProducto(), prod.getPrecioBruto(), prod.getDescripcion()});
                } else if (item instanceof Farmaco) {
                    Farmaco farm = (Farmaco) item;
                    model.addRow(new Object[]{farm.getNombre(), farm.getPrecio(), farm.getDescripcion()});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProductoSeleccionado() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < productos.size()) {
            Object selectedProduct = productos.get(selectedRow);
            ownerPanel.addProductToTable(selectedProduct);
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto primero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
