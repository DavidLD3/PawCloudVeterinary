package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import UISwing.recursos.RoundedPanel;
import UISwing.recursos.CustomPanelOpaco;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class PanelVentas extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPrecio;
    public PanelVentas() {
        setLayout(null);
        setBackground(new Color(255, 255, 255, 0)); // Transparente o ajusta según necesidad

        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#577BD1"));
        panelVentas.setBounds(0, 0, 1112, 653); // Ajusta la posición y tamaño según necesites
        panelVentas.setLayout(null);
        add(panelVentas);
        
        
        String[] columnNames = {"Producto", "Descripción", "Cantidad", "Importe", "Total"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex >= 2 ? Double.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3; // Permitir edición solo en cantidad e importe
            }
        };

        table = new JTable(tableModel);
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && (e.getColumn() == 2 || e.getColumn() == 3)) {
                    int row = e.getFirstRow();
                    double cantidad = (Double) tableModel.getValueAt(row, 2);
                    double importe = (Double) tableModel.getValueAt(row, 3);
                    double total = cantidad * importe;
                    tableModel.setValueAt(total, row, 4);
                    actualizarTotal();
                }
            }
        });
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(24, 144, 1067, 360);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panelVentas.add(scrollPane);

        JButton btnAñadirProducto = new JButton("Añadir Producto");
        btnAñadirProducto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirProducto.setBounds(844, 96, 247, 31);
        btnAñadirProducto.setBackground(Color.WHITE);
        btnAñadirProducto.setForeground(Color.decode("#0057FF"));
        btnAñadirProducto.setFocusPainted(false);
        btnAñadirProducto.setBorderPainted(false);
        btnAñadirProducto.setContentAreaFilled(false);
        btnAñadirProducto.setOpaque(true);
        panelVentas.add(btnAñadirProducto);

        CustomPanelOpaco panelOpacoVentas = new CustomPanelOpaco();
        panelOpacoVentas.setLayout(null);
        panelOpacoVentas.setBounds(24, 138, 1067, 491);
        panelOpacoVentas.setBackground(new Color(255, 255, 255, 70));
        panelVentas.add(panelOpacoVentas);

        JLabel lbltextoVentas = new JLabel("Ventas");
        lbltextoVentas.setForeground(Color.WHITE);
        lbltextoVentas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoVentas.setBounds(48, 21, 171, 31);
        panelVentas.add(lbltextoVentas);

        JLabel lblLogoPanelVentas = new JLabel("");
        lblLogoPanelVentas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelVentas.png")));
        lblLogoPanelVentas.setBounds(24, 21, 22, 31);
        panelVentas.add(lblLogoPanelVentas);

        JPanel panelDatosVentas = new JPanel();
        panelDatosVentas.setLayout(new BoxLayout(panelDatosVentas, BoxLayout.Y_AXIS));
        panelDatosVentas.setBounds(0, 27, 1067, 360);
        panelDatosVentas.setOpaque(false);
        panelOpacoVentas.add(panelDatosVentas);
        
                lblTotalPrecio = new JLabel("0.00");
                lblTotalPrecio.setBounds(1021, 382, 46, 31);
                panelOpacoVentas.add(lblTotalPrecio);
                lblTotalPrecio.setForeground(Color.WHITE);
                lblTotalPrecio.setFont(new Font("Segoe UI", Font.BOLD, 13));
                
                JLabel lblNewLabel_1 = new JLabel("Total:");
                lblNewLabel_1.setForeground(new Color(255, 255, 255));
                lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lblNewLabel_1.setBounds(978, 383, 36, 29);
                panelOpacoVentas.add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("Metodo de pago");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel.setBounds(775, 510, 138, 14);
        panelVentas.add(lblNewLabel);
        
        JCheckBox chckbxefectivo = new JCheckBox("Efectivo");
        chckbxefectivo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chckbxefectivo.setBounds(775, 541, 65, 23);
        chckbxefectivo.setOpaque(false); // Hacer el fondo del checkbox transparente
        chckbxefectivo.setBackground(new Color(0, 0, 0, 0)); // Asegurarse de que el fondo es completamente transparente
        panelVentas.add(chckbxefectivo);
        
        JCheckBox chckbxTarjerta = new JCheckBox("Tarjeta");
        chckbxTarjerta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chckbxTarjerta.setBounds(848, 541, 65, 23);
        chckbxTarjerta.setOpaque(false); // Hacer el fondo del checkbox transparente
        chckbxTarjerta.setBackground(new Color(0, 0, 0, 0)); // Asegurarse de que el fondo es completamente transparente
        panelVentas.add(chckbxTarjerta);
        
        JButton btnLimpiarDatos = new JButton("Limpiar Datos");
        btnLimpiarDatos.setBounds(34, 590, 148, 23);
        btnLimpiarDatos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
        
        panelVentas.add(btnLimpiarDatos);
        
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
        
        panelVentas.add(btnValidar);
        
        JComboBox comboBoxCliente = new JComboBox();
        comboBoxCliente.setBounds(24, 100, 195, 31);
        panelVentas.add(comboBoxCliente);
        
        JComboBox comboBoxMascota = new JComboBox();
        comboBoxMascota.setBounds(269, 100, 195, 31);
        panelVentas.add(comboBoxMascota);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setBounds(24, 75, 78, 14);
        panelVentas.add(lblCliente);
        
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setBounds(269, 75, 99, 14);
        panelVentas.add(lblMascota);
        
      
    }
    
    private void actualizarTotal() {
        double totalGeneral = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double totalFila = (Double) tableModel.getValueAt(i, 4);
            totalGeneral += totalFila;
        }
        lblTotalPrecio.setText("Total: $" + String.format("%.2f", totalGeneral));
    }
}