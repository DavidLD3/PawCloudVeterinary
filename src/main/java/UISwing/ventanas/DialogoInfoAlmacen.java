package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DB.AlmacenDAO;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Almacen;  // Asegúrate de importar tu clase Almacen

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
    	  super(owner, "Detalles del Almacén", true); // true para modal	
    	  this.almacen = almacen;
          setBounds(100, 100, 650, 400);
          getContentPane().setLayout(new BorderLayout());
          contentPanel.setLayout(new GridLayout(0, 2));  // Usar GridLayout para un formato ordenado
          contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
          getContentPane().add(contentPanel, BorderLayout.CENTER);

          // Inicializar campos de texto y añadirlos al panel
          txtNombreProducto = addLabelAndTextField("Nombre del Producto:", almacen.getNombreProducto());
          txtCategoria = addLabelAndTextField("Categoría:", almacen.getCategoria().toString());
          txtDescripcion = addLabelAndTextField("Descripción:", almacen.getDescripcion());
          txtCantidadStock = addLabelAndTextField("Cantidad en Stock:", String.valueOf(almacen.getCantidadStock()));
          txtPrecioBruto = addLabelAndTextField("Precio Bruto:", almacen.getPrecioBruto().toString());
          txtProveedor = addLabelAndTextField("Proveedor:", almacen.getProveedor());
          txtFechaUltimaCompra = addLabelAndTextField("Fecha Última Compra:", almacen.getFechaUltimaCompra().toString());
          txtNumeroLote = addLabelAndTextField("Número de Lote:", almacen.getNumeroLote());
          txtFechaCaducidad = addLabelAndTextField("Fecha de Caducidad:", almacen.getFechaCaducidad().toString());
          txtCodigoBarras = addLabelAndTextField("Código de Barras:", almacen.getCodigoBarras());
          txtObservaciones = addLabelAndTextField("Observaciones:", almacen.getObservaciones());


        // Configuración de los botones
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
        	  // Botón para habilitar la edición
            JButton editButton = new JButton("Editar");
            editButton.addActionListener(e -> habilitarEdicion(true));
            buttonPane.add(editButton);

            // Botón para guardar los cambios
            saveButton = new JButton("Guardar");
            saveButton.setEnabled(false); // Desactivado hasta que se presione "Editar"
            saveButton.addActionListener(e -> guardarCambios());
            buttonPane.add(saveButton);       	
            
            JButton closeButton = new JButton("Cerrar");
            closeButton.addActionListener(e -> dispose());
            buttonPane.add(closeButton);        
            
         // Botón Eliminar
            JButton deleteButton = new JButton("Eliminar");
            deleteButton.addActionListener(e -> eliminarAlmacen());
            buttonPane.add(deleteButton);
        }
        setLocationRelativeTo(owner);  // Asegura que el diálogo se centre respecto al frame pasado como 'owner'
    }
   
    /**
     * Helper method to add labels and text fields to the panel.
     */

    private JTextField addLabelAndTextField(String label, String value) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField(value);
        textField.setEditable(false); // No editable por defecto
        contentPanel.add(jLabel);
        contentPanel.add(textField);
        return textField;
    }
    
    private void eliminarAlmacen() {
        if (almacen != null) {
            int idAlmacen = almacen.getIdAlmacen();
            AlmacenDAO almacenDAO = new AlmacenDAO();
            try {
                almacenDAO.eliminarAlmacen(idAlmacen);
                JOptionPane.showMessageDialog(this, "Almacén eliminado con éxito.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cierra el diálogo después de eliminar
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el almacén: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el almacén para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void habilitarEdicion(boolean editable) {
        txtNombreProducto.setEditable(editable);
        txtCategoria.setEditable(editable);
        txtDescripcion.setEditable(editable);
        txtCantidadStock.setEditable(editable);
        txtPrecioBruto.setEditable(editable);
        txtProveedor.setEditable(editable);
        txtFechaUltimaCompra.setEditable(editable);
        txtNumeroLote.setEditable(editable);
        txtFechaCaducidad.setEditable(editable);
        txtCodigoBarras.setEditable(editable);
        txtObservaciones.setEditable(editable);
        saveButton.setEnabled(editable); // Habilitar el botón de guardar
    }
    private void guardarCambios() {
        try {
            // Actualizar los datos del almacen desde los campos de texto
            almacen.setNombreProducto(txtNombreProducto.getText());
            almacen.setCategoria(Almacen.Categoria.valueOf(txtCategoria.getText()));
            almacen.setDescripcion(txtDescripcion.getText());
            almacen.setCantidadStock(Integer.parseInt(txtCantidadStock.getText()));
            almacen.setPrecioBruto(new BigDecimal(txtPrecioBruto.getText()));
            almacen.setProveedor(txtProveedor.getText());
            almacen.setFechaUltimaCompra(LocalDate.parse(txtFechaUltimaCompra.getText()));
            almacen.setNumeroLote(txtNumeroLote.getText());
            almacen.setFechaCaducidad(LocalDate.parse(txtFechaCaducidad.getText()));
            almacen.setCodigoBarras(txtCodigoBarras.getText());
            almacen.setObservaciones(txtObservaciones.getText());

            // Guardar cambios en la base de datos
            AlmacenDAO almacenDAO = new AlmacenDAO();
            almacenDAO.actualizarAlmacen(almacen);

            JOptionPane.showMessageDialog(this, "Almacén actualizado con éxito.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra el diálogo después de guardar
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el almacén: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Launch the dialog for testing.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Crear un objeto Almacen para prueba
                Almacen testAlmacen = new Almacen(1, "Producto XYZ", "Descripción del producto", Almacen.Categoria.Normal, 100, new BigDecimal("19.99"), "Proveedor XYZ", LocalDate.now(), "Lote123", LocalDate.now().plusYears(1), "1234567890", "Sin observaciones");
                
                // Crear un JFrame temporal para usar como owner
                JFrame frame = new JFrame("Test Frame");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(200, 200); // Dimensiones mínimas para que sea visible
                frame.setLocationRelativeTo(null); // Centrar en la pantalla

                // Crear y mostrar el diálogo
                DialogoInfoAlmacen dialog = new DialogoInfoAlmacen(frame, testAlmacen);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                
                // Asegurarse de que el JFrame se cierra cuando cerramos el diálogo
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
