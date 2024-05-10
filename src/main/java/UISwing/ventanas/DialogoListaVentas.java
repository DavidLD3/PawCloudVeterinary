package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import DB.VentasDAO;
import model.VentaDetalle;

public class DialogoListaVentas extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private VentasDAO ventasDAO;

    public DialogoListaVentas(Frame owner) {
        super(owner, "Listado de Ventas", true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        ventasDAO = new VentasDAO();
        initUI();
        cargarVentas();
    }

    private void initUI() {
        String[] columnNames = {"ID Venta", "Fecha", "Método de Pago", "Producto", "Cantidad", "Precio Unitario", "Total"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int leftPadding = 10;
                int rightPadding = 10;
               
                if (column == 1) { 
                    leftPadding = 0; 
                    rightPadding = 0;
                }
                if (component instanceof JLabel) {
                    ((JLabel) component).setBorder(new EmptyBorder(5, leftPadding, 5, rightPadding));
                }
                return component;
            }
        };
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setBackground(Color.decode("#577BD1"));
        table.setForeground(Color.decode("#FFFFFF"));
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(Color.decode("#0483FF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Ocultar la columna del ID Venta
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }


    private void cargarVentas() {
        model.setRowCount(0); 
        List<VentaDetalle> ventas = null;
		try {
			ventas = ventasDAO.obtenerUltimasVentas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (VentaDetalle venta : ventas) {
            String fechaFormateada = formatter.format(venta.getFechaVenta());
            String metodoPago = venta.getMetodoPago();
            String producto = venta.getProducto();
            Integer cantidad = venta.getCantidad();
            String precioUnitario = venta.getPrecioUnitario() != null ? venta.getPrecioUnitario().toPlainString() : "N/A";
            String total = venta.getTotal() != null ? venta.getTotal().toPlainString() : "N/A";

            model.addRow(new Object[]{
                venta.getIdVenta(),
                fechaFormateada,
                metodoPago,
                producto,
                cantidad,
                precioUnitario,
                total
            });
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DialogoListaVentas dialog = new DialogoListaVentas(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
