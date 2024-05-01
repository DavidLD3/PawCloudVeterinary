package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import model.Farmaco;
import DB.FarmacoDAO;

public class DialogoInfoFarmaco extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Farmaco farmaco;
    
 // Campos editables para los datos del fármaco
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtCantidad;
    private JTextField txtDosisRecomendada;
    private JTextField txtUnidadMedida;
    private JTextField txtPrecio;
    private JTextField txtFechaCaducidad;

    private JButton saveButton;

    /**
     * Crea el diálogo con datos de Farmaco.
     */
    public DialogoInfoFarmaco(Frame owner, Farmaco farmaco) {
        super(owner, "Detalles del Fármaco", true); // true para modal
        this.farmaco = farmaco;
        setBounds(100, 100, 650, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new GridLayout(0, 2));  
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Inicializar campos de texto y añadirlos al panel
        txtCodigo = addLabelAndTextField("Código:", farmaco.getCodigo());
        txtNombre = addLabelAndTextField("Nombre:", farmaco.getNombre());
        txtDescripcion = addLabelAndTextField("Descripción:", farmaco.getDescripcion());
        txtCantidad = addLabelAndTextField("Cantidad:", String.valueOf(farmaco.getCantidad()));
        txtDosisRecomendada = addLabelAndTextField("Dosis Recomendada:", farmaco.getDosisRecomendada());
        txtUnidadMedida = addLabelAndTextField("Unidad de Medida:", farmaco.getUnidadMedida());
        txtPrecio = addLabelAndTextField("Precio:", farmaco.getPrecio().toString());
        txtFechaCaducidad = addLabelAndTextField("Fecha de Caducidad:", farmaco.getFechaCaducidad().toString());

        // Panel para los botones
        JPanel buttonPane = new JPanel(new BorderLayout());
        JPanel leftPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Botón para eliminar
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarFarmaco());
        leftPane.add(deleteButton);

        // Botón para cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        rightPane.add(closeButton);

        buttonPane.add(leftPane, BorderLayout.WEST);
        buttonPane.add(rightPane, BorderLayout.EAST);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }


    /**
     * Método auxiliar para agregar etiquetas y campos de texto al panel.
     */
    private JTextField addLabelAndTextField(String label, String value) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField(value);
        textField.setEditable(false); // No editable por defecto
        contentPanel.add(jLabel);
        contentPanel.add(textField);
        return textField;
    }
    
    private void eliminarFarmaco() {
        if (farmaco != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este fármaco?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                FarmacoDAO farmacoDAO = new FarmacoDAO();
                try {
                    farmacoDAO.eliminarFarmaco(farmaco.getId());
                    JOptionPane.showMessageDialog(this, "Fármaco eliminado con éxito.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el fármaco: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el fármaco para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método principal para pruebas.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Crear un objeto Farmaco para prueba
                Farmaco testFarmaco = new Farmaco(1, "F001", "Paracetamol", "Analgésico", 100, "500 mg", "mg", 
                Date.valueOf(LocalDate.now().plusYears(1)), new BigDecimal("5.99"));
                
                // Crear un JFrame temporal para usar como owner
                JFrame frame = new JFrame("Test Frame");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(200, 200); // Dimensiones mínimas para que sea visible
                frame.setLocationRelativeTo(null); // Centrar en la pantalla

                // Crear y mostrar el diálogo
                DialogoInfoFarmaco dialog = new DialogoInfoFarmaco(frame, testFarmaco);
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
