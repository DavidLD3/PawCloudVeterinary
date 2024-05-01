package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import model.Farmaco;

public class DialogoInfoFarmaco extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Crea el diálogo con datos de Farmaco.
     */
    public DialogoInfoFarmaco(Frame owner, Farmaco farmaco) {
        super(owner, "Detalles del Fármaco", true); // true para modal
        setBounds(100, 100, 650, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new GridLayout(0, 2));  // Usar GridLayout para un formato ordenado
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Agregar etiquetas y valores como componentes al panel
        if (farmaco != null) {
            addLabelAndValue("Código:", farmaco.getCodigo());
            addLabelAndValue("Nombre:", farmaco.getNombre());
            addLabelAndValue("Descripción:", farmaco.getDescripcion());
            addLabelAndValue("Cantidad:", String.valueOf(farmaco.getCantidad()));
            addLabelAndValue("Dosis Recomendada:", farmaco.getDosisRecomendada());
            addLabelAndValue("Unidad de Medida:", farmaco.getUnidadMedida());
            addLabelAndValue("Precio:", farmaco.getPrecio().toString());
            addLabelAndValue("Fecha de Caducidad:", farmaco.getFechaCaducidad().toString());
        }

        // Configuración de los botones
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton closeButton = new JButton("Cerrar");
            closeButton.addActionListener(e -> dispose());
            buttonPane.add(closeButton);        
        }
        setLocationRelativeTo(owner);  // Asegura que el diálogo se centre respecto al frame pasado como 'owner'
    }

    /**
     * Método auxiliar para agregar etiquetas y campos de texto al panel.
     */
    private void addLabelAndValue(String label, String value) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        contentPanel.add(jLabel);
        contentPanel.add(textField);
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
