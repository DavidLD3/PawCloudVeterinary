package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;
import DB.AlmacenDAO;
import model.Almacen;
import model.Almacen.Categoria;

public class DialogoRegistroAlmacen extends JDialog {
    private JTextField tfRnombre, tfRcategoria, tfRN_Lote, tfRcantidad, tfRproveedor, tfRCodigo_Barras, tfRprecio_Bruto, tfRobservaciones, tfRdescripcion;
    private JDateChooser tfRfechaUltCompra, tfrfecha_Caducidad;
    private JComboBox<Categoria> cbCategoria;
    
    public DialogoRegistroAlmacen() {
        setTitle("Registro en Almacén");
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

        tfRnombre = new JTextField();
        cbCategoria = new JComboBox<>(Categoria.values());  // Llenar el JComboBox con los valores del enum Categoria
        tfRN_Lote = new JTextField();
        tfRcantidad = new JTextField();
        tfRproveedor = new JTextField();
        tfRCodigo_Barras = new JTextField();
        tfRprecio_Bruto = new JTextField();
        tfRobservaciones = new JTextField();
        tfRdescripcion = new JTextField();
        tfRfechaUltCompra = new JDateChooser();
        tfrfecha_Caducidad = new JDateChooser();

        panel.add(new JLabel("Nombre:"));
        panel.add(tfRnombre);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);
        panel.add(new JLabel("Descripción:"));
        panel.add(tfRdescripcion);
        panel.add(new JLabel("Fecha de Compra:"));
        panel.add(tfRfechaUltCompra);
        panel.add(new JLabel("Número Lote:"));
        panel.add(tfRN_Lote);
        panel.add(new JLabel("Cantidad:"));
        panel.add(tfRcantidad);
        panel.add(new JLabel("Fecha Caducidad:"));
        panel.add(tfrfecha_Caducidad);
        panel.add(new JLabel("Proveedor:"));
        panel.add(tfRproveedor);
        panel.add(new JLabel("Código de Barras:"));
        panel.add(tfRCodigo_Barras);
        panel.add(new JLabel("Precio Bruto:"));
        panel.add(tfRprecio_Bruto);
        panel.add(new JLabel("Observaciones:"));
        panel.add(tfRobservaciones);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        btnGuardar.addActionListener(this::guardarDatos);
        btnEliminar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnEliminar);
        panel.add(btnCerrar);

        return panel;
    }

    private void guardarDatos(ActionEvent e) {
        if (validarDatos()) {
            Almacen nuevoAlmacen = recolectarDatos();
            AlmacenDAO dao = new AlmacenDAO();
            try {
                dao.insertarAlmacen(nuevoAlmacen);
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor revise la información proporcionada.");
        }
    }

    private boolean validarDatos() {
        // Validar que el nombre del producto no esté vacío
        if (tfRnombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que la cantidad sea un número entero válido y positivo
        try {
            int cantidad = Integer.parseInt(tfRcantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el precio bruto sea un número decimal válido y no negativo
        try {
            BigDecimal precio = new BigDecimal(tfRprecio_Bruto.getText().trim());
            if (precio.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "El precio bruto no puede ser negativo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio bruto debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que la fecha de última compra no sea futura
        if (tfRfechaUltCompra.getDate() != null && tfRfechaUltCompra.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "La fecha de última compra no puede ser futura.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación exitosa de todos los campos
        return true;
    }


    private Almacen recolectarDatos() {
        LocalDate fechaCompra = (tfRfechaUltCompra.getDate() != null) ? tfRfechaUltCompra.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate fechaCaducidad = (tfrfecha_Caducidad.getDate() != null) ? tfrfecha_Caducidad.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        Categoria categoria = (Categoria) cbCategoria.getSelectedItem();  // Obtener el valor seleccionado en el JComboBox

        return new Almacen(
            0, // ID es autoincremental en la base de datos
            tfRnombre.getText().trim(),
            tfRdescripcion.getText().trim(),
            categoria, // Usar el valor obtenido del JComboBox
            Integer.parseInt(tfRcantidad.getText().trim()),
            new BigDecimal(tfRprecio_Bruto.getText().trim()),
            tfRproveedor.getText().trim(),
            fechaCompra,
            tfRN_Lote.getText().trim(),
            fechaCaducidad,
            tfRCodigo_Barras.getText().trim(),
            tfRobservaciones.getText().trim()
        );
    }


    private void limpiarCampos() {
        tfRnombre.setText("");
        cbCategoria.setSelectedIndex(0); // Establece la selección al primer ítem del combo box
        tfRN_Lote.setText("");
        tfRcantidad.setText("");
        tfRproveedor.setText("");
        tfRCodigo_Barras.setText("");
        tfRprecio_Bruto.setText("");
        tfRobservaciones.setText("");
        tfRdescripcion.setText("");
        tfRfechaUltCompra.setDate(null);  // Asegúrate de que estos campos de fecha estén limpios
        tfrfecha_Caducidad.setDate(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DialogoRegistroAlmacen dialog = new DialogoRegistroAlmacen();
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
