package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;

import com.toedter.calendar.JDateChooser;
import DB.FarmacoDAO;
import model.Farmaco;

public class DialogoRegistroFarmaco extends JDialog {
    private JTextField tfCodigo, tfNombre, tfDescripcion, tfCantidad, tfDosisRecomendada, tfUnidadMedida, tfPrecio;
    private JDateChooser tfFechaCaducidad;

    public DialogoRegistroFarmaco() {
        setTitle("Registro de Fármacos");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(crearPanelCentral(), BorderLayout.CENTER);
        getContentPane().add(crearPanelBotones(), BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tfCodigo = new JTextField();
        tfNombre = new JTextField();
        tfDescripcion = new JTextField();
        tfCantidad = new JTextField();
        tfDosisRecomendada = new JTextField();
        tfUnidadMedida = new JTextField();
        tfPrecio = new JTextField();
        tfFechaCaducidad = new JDateChooser();

        panel.add(new JLabel("Código:"));
        panel.add(tfCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(tfNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(tfDescripcion);
        panel.add(new JLabel("Cantidad:"));
        panel.add(tfCantidad);
        panel.add(new JLabel("Dosis Recomendada:"));
        panel.add(tfDosisRecomendada);
        panel.add(new JLabel("Unidad de Medida:"));
        panel.add(tfUnidadMedida);
        panel.add(new JLabel("Precio:"));
        panel.add(tfPrecio);
        panel.add(new JLabel("Fecha de Caducidad:"));
        panel.add(tfFechaCaducidad);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        btnGuardar.addActionListener(this::guardarDatos);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        return panel;
    }

    private void guardarDatos(ActionEvent e) {
        if (validarDatos()) {
            Farmaco nuevoFarmaco = recolectarDatos();
            FarmacoDAO dao = new FarmacoDAO();
            try {
                if (dao.insertarFarmaco(nuevoFarmaco)) {
                    JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar los datos.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor revise la información proporcionada.");
        }
    }

    private boolean validarDatos() {
        // Realizar validaciones específicas de los campos del fármaco
        // Ejemplo: Validar que el nombre no esté vacío
        if (tfNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Validar que la cantidad sea un número entero válido y positivo
        try {
            int cantidad = Integer.parseInt(tfCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el precio sea un número decimal válido y no negativo
        try {
            BigDecimal precio = new BigDecimal(tfPrecio.getText().trim());
            if (precio.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que la fecha de caducidad no sea pasada
        if (tfFechaCaducidad.getDate() != null && tfFechaCaducidad.getDate().before(new java.util.Date())) {
            JOptionPane.showMessageDialog(this, "La fecha de caducidad no puede ser pasada.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }       

        return true; // Modificar según las validaciones
    }

    private Farmaco recolectarDatos() {
        Date fechaCaducidad = (tfFechaCaducidad.getDate() != null) ? new Date(tfFechaCaducidad.getDate().getTime()) : null;

        return new Farmaco(
            0, // ID es autoincremental en la base de datos
            tfCodigo.getText().trim(),
            tfNombre.getText().trim(),
            tfDescripcion.getText().trim(),
            Integer.parseInt(tfCantidad.getText().trim()),
            tfDosisRecomendada.getText().trim(),
            tfUnidadMedida.getText().trim(),
            fechaCaducidad,
            new BigDecimal(tfPrecio.getText().trim())
        );
    }

    private void limpiarCampos() {
        tfCodigo.setText("");
        tfNombre.setText("");
        tfDescripcion.setText("");
        tfCantidad.setText("");
        tfDosisRecomendada.setText("");
        tfUnidadMedida.setText("");
        tfPrecio.setText("");
        tfFechaCaducidad.setDate(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DialogoRegistroFarmaco dialog = new DialogoRegistroFarmaco();
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
