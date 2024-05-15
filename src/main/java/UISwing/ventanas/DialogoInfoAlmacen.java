package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DB.AlmacenDAO;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Almacen;  

public class DialogoInfoAlmacen extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Almacen almacen;   
    
    // Campos editables para los datos del almacen
    private JTextField txtNombreProducto;
    private JTextField txtCategoria;
    private JTextField txtDescripcion;
    private JTextField txtCantidadStock;
    private JTextField txtPrecioBruto;
    private JTextField txtProveedor;
    private JTextField txtFechaUltimaCompra;
    private JTextField txtNumeroLote;
    private JTextField txtFechaCaducidad;
    private JTextField txtCodigoBarras;
    private JTextField txtObservaciones;

    private JButton saveButton;

    /**
     * Create the dialog with Almacen data.
     */
    public DialogoInfoAlmacen(Frame owner, Almacen almacen) {
        super(owner, "Detalles del Almacén", true);
        this.almacen = almacen;
        setBounds(100, 100, 650, 400);
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout());
        JPanel roundedBackground = new JPanel(new BorderLayout());
        roundedBackground.setBorder(new EmptyBorder(10, 10, 10, 10));
        roundedBackground.setBackground(Color.decode("#577BD1"));
        roundedBackground.setOpaque(true);
        getContentPane().add(roundedBackground, BorderLayout.CENTER);
        
        contentPanel.setLayout(new GridLayout(0, 2));  
        roundedBackground.add(contentPanel, BorderLayout.CENTER);

        // Inicializar campos de texto y añadirlos al panel
        txtNombreProducto = addLabelAndTextField("Nombre del Producto:", almacen.getNombreProducto());
        txtCategoria = addLabelAndTextField("Categoría:", almacen.getCategoria().toString());
        txtDescripcion = addLabelAndTextField("Descripción:", almacen.getDescripcion());
        txtCantidadStock = addLabelAndTextField("Cantidad en Stock:", String.valueOf(almacen.getCantidadStock()));
        txtPrecioBruto = addLabelAndTextField("Precio Bruto:", almacen.getPrecioBruto().toString());
        txtProveedor = addLabelAndTextField("Proveedor:", almacen.getProveedor());
        
        // Validación para las fechas
        String fechaUltimaCompraStr = almacen.getFechaUltimaCompra() != null ? almacen.getFechaUltimaCompra().toString() : "Sin fecha última compra";
        txtFechaUltimaCompra = addLabelAndTextField("Fecha Última Compra:", fechaUltimaCompraStr);

        String fechaCaducidadStr = almacen.getFechaCaducidad() != null ? almacen.getFechaCaducidad().toString() : "Sin fecha de caducidad";
        txtFechaCaducidad = addLabelAndTextField("Fecha de Caducidad:", fechaCaducidadStr);

        txtNumeroLote = addLabelAndTextField("Número de Lote:", almacen.getNumeroLote());
        txtObservaciones = addLabelAndTextField("Observaciones:", almacen.getObservaciones());

     // Configuración de los botones
        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.setOpaque(false);
        JPanel leftPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPane.setOpaque(false);
        JPanel rightPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPane.setOpaque(false);

        // Botón para eliminar
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarAlmacen());
        personalizarBoton(deleteButton);
        leftPane.add(deleteButton);

        // Botón para cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        personalizarBoton(closeButton);
        rightPane.add(closeButton);

        buttonPane.add(leftPane, BorderLayout.WEST);
        buttonPane.add(rightPane, BorderLayout.EAST);
        roundedBackground.add(buttonPane, BorderLayout.SOUTH);


        setLocationRelativeTo(owner);
        
        contentPanel.setOpaque(false);
    }
    private JTextField addLabelAndTextField(String label, String value) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jLabel.setForeground(Color.WHITE);
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        contentPanel.add(jLabel);
        contentPanel.add(textField);
        return textField;
    }
    
    private void personalizarBoton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.decode("#0057FF"));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);  
        button.setRolloverEnabled(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#003366"));  
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);  
                button.setForeground(Color.decode("#0057FF"));
            }
        });
    }
    
    private void eliminarAlmacen() {
        if (almacen != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este almacén?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                AlmacenDAO almacenDAO = new AlmacenDAO();
                try {
                    almacenDAO.eliminarAlmacen(almacen.getIdAlmacen());
                    JOptionPane.showMessageDialog(this, "Almacén eliminado con éxito.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el almacén: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el almacén para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Almacen testAlmacen = new Almacen(1, "Producto XYZ", "Descripción del producto", Almacen.Categoria.Normal, 100, new BigDecimal("19.99"), "Proveedor XYZ", LocalDate.now(), "Lote123", LocalDate.now().plusYears(1), "1234567890", "Sin observaciones");         
                JFrame frame = new JFrame("Test Frame");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                DialogoInfoAlmacen dialog = new DialogoInfoAlmacen(frame, testAlmacen);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);             
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
