package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import model.Almacen;  // Asegúrate de importar tu clase Almacen

public class DialogoInfoAlmacen extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

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
