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

    public DialogoInfoFarmaco(Frame owner, Farmaco farmaco) {
        super(owner, "Detalles del Fármaco", true);
        this.farmaco = farmaco;
        setBounds(100, 100, 650, 400);
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout());

        JPanel roundedBackground = new JPanel(new BorderLayout());
        roundedBackground.setBorder(new EmptyBorder(10, 10, 10, 10));
        roundedBackground.setBackground(Color.decode("#577BD1"));
        getContentPane().add(roundedBackground, BorderLayout.CENTER);

        contentPanel.setLayout(new GridLayout(0, 2));
        contentPanel.setOpaque(false);  // Esto asegura que el panel no afecte la visibilidad de los componentes dentro de él.
        roundedBackground.add(contentPanel, BorderLayout.CENTER);

        // Configuración de campos de texto
        JTextField txtCodigo = addLabelAndTextField("Código:", farmaco.getCodigo());
        JTextField txtNombre = addLabelAndTextField("Nombre:", farmaco.getNombre());
        JTextField txtDescripcion = addLabelAndTextField("Descripción:", farmaco.getDescripcion());
        JTextField txtCantidad = addLabelAndTextField("Cantidad:", String.valueOf(farmaco.getCantidad()));
        JTextField txtDosisRecomendada = addLabelAndTextField("Dosis Recomendada:", farmaco.getDosisRecomendada());
        JTextField txtUnidadMedida = addLabelAndTextField("Unidad de Medida:", farmaco.getUnidadMedida());
        JTextField txtPrecio = addLabelAndTextField("Precio:", farmaco.getPrecio().toString());
        JTextField txtFechaCaducidad = addLabelAndTextField("Fecha de Caducidad:", farmaco.getFechaCaducidad() != null ? farmaco.getFechaCaducidad().toString() : "N/A");

        JPanel buttonPane = setupButtonPanel();
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }

    private JTextField addLabelAndTextField(String label, String value) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jLabel.setForeground(Color.WHITE);
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        textField.setOpaque(true); // Asegura que el fondo sea visible
        textField.setBackground(Color.WHITE); // Fondo blanco para los campos de texto
        contentPanel.add(jLabel);
        contentPanel.add(textField);
        return textField;
    }

    private JPanel setupButtonPanel() {
        // Panel principal para los botones con disposición BorderLayout
        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.setOpaque(true);  // Asegura que el panel de botones sea opaco
        buttonPane.setBackground(Color.decode("#577BD1")); // Fondo azul para coincidir con el roundedBackground

        // Adjust the horizontal gap to move the button slightly to the right
        JPanel leftPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5)); // Increase the horizontal gap
        leftPane.setOpaque(false); // Hacer que el panel izquierdo sea transparente para no ver su fondo
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarFarmaco());
        personalizarBoton(deleteButton);
        leftPane.add(deleteButton);

        // Adjust the horizontal gap to move the button slightly to the left
        JPanel rightPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5)); // Increase the horizontal gap
        rightPane.setOpaque(false); // Hacer que el panel derecho sea transparente
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        personalizarBoton(closeButton);
        rightPane.add(closeButton);

        // Agregar los subpaneles al panel principal de botones
        buttonPane.add(leftPane, BorderLayout.WEST);
        buttonPane.add(rightPane, BorderLayout.EAST);

        return buttonPane;
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
