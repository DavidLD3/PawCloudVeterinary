package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DB.AlmacenDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDate;

import model.Almacen;  // Asegúrate de importar tu clase Almacen

public class DialogoInfoAlmacen extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Almacen almacen;  // Almacenar el objeto Almacen localmente

    /**
     * Create the dialog with Almacen data.
     */
    public DialogoInfoAlmacen(Almacen almacen) {
        setTitle("Detalles del Almacén");
        setBounds(100, 100, 650, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new GridLayout(0, 2));  // Usar GridLayout para un formato ordenado
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Agregar etiquetas y valores como componentes al panel
        if (almacen != null) {
            addLabelAndValue("Nombre del Producto:", almacen.getNombreProducto());
            addLabelAndValue("Categoría:", almacen.getCategoria().toString());
            addLabelAndValue("Descripción:", almacen.getDescripcion());
            addLabelAndValue("Cantidad en Stock:", String.valueOf(almacen.getCantidadStock()));
            addLabelAndValue("Precio Bruto:", almacen.getPrecioBruto().toString());
            addLabelAndValue("Proveedor:", almacen.getProveedor());
            addLabelAndValue("Fecha Última Compra:", almacen.getFechaUltimaCompra().toString());
            addLabelAndValue("Número de Lote:", almacen.getNumeroLote());
            addLabelAndValue("Fecha de Caducidad:", almacen.getFechaCaducidad().toString());
            addLabelAndValue("Código de Barras:", almacen.getCodigoBarras());
            addLabelAndValue("Observaciones:", almacen.getObservaciones());
        }

        // Configuración de los botones
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton closeButton = new JButton("Cerrar");
            closeButton.addActionListener(e -> dispose());
            buttonPane.add(closeButton);
            JButton deleteButton = new JButton("Eliminar");
            deleteButton.addActionListener(this::eliminarRegistro);
            buttonPane.add(deleteButton);
        }
    }
    private void eliminarRegistro(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try {
                AlmacenDAO dao = new AlmacenDAO();
                dao.eliminarAlmacen(almacen.getIdAlmacen());
                JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.", "Eliminación completada", JOptionPane.INFORMATION_MESSAGE);
                dispose();  // Cierra el diálogo
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " + ex.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Helper method to add labels and text fields to the panel.
     */
    private void addLabelAndValue(String label, String value) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        contentPanel.add(jLabel);
        contentPanel.add(textField);
    }

    /**
     * Launch the dialog for testing.
     */
    public static void main(String[] args) {
        try {
            Almacen testAlmacen = new Almacen(1, "Producto XYZ", "Descripción del producto", Almacen.Categoria.Normal, 100, new BigDecimal("19.99"), "Proveedor XYZ", LocalDate.now(), "Lote123", LocalDate.now().plusYears(1), "1234567890", "Sin observaciones");
            DialogoInfoAlmacen dialog = new DialogoInfoAlmacen(testAlmacen);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
