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
import UISwing.recursos.RoundedPanel; // Asegúrate de que esta ruta sea correcta

public class DialogoRegistroAlmacen extends JDialog {
    private JTextField tfRnombre, tfRN_Lote, tfRcantidad, tfRproveedor, tfRCodigo_Barras, tfRprecio_Bruto, tfRobservaciones, tfRdescripcion;
    private JDateChooser tfRfechaUltCompra, tfrfecha_Caducidad;
    private JComboBox<Categoria> cbCategoria;

    public DialogoRegistroAlmacen() {
        setUndecorated(true); // Quitar decoración estándar, incluyendo marcos y botones de título
        setTitle("Registro en Almacén");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setBackground(new Color(0, 0, 0, 0)); // Hacer el fondo del diálogo transparente

        RoundedPanel roundedBackground = new RoundedPanel(20);
        roundedBackground.setLayout(new BorderLayout());
        roundedBackground.setBackground(Color.decode("#577BD1"));
        roundedBackground.setOpaque(false);

        setContentPane(roundedBackground); // Usar RoundedPanel como el panel de contenido

        JPanel centralPanel = crearPanelCentral();
        centralPanel.setOpaque(false);
        getContentPane().add(centralPanel, BorderLayout.CENTER);

        JPanel buttonPanel = crearPanelBotones();
        buttonPanel.setOpaque(false);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setOpaque(false);

        // Añadir campos y configurar estilos
        añadirCampoConEtiqueta(panel, "Nombre:", tfRnombre = new JTextField());
        añadirCampoConEtiqueta(panel, "Categoría:", cbCategoria = new JComboBox<>(Categoria.values()));
        añadirCampoConEtiqueta(panel, "Descripción:", tfRdescripcion = new JTextField());
        añadirCampoConEtiqueta(panel, "Fecha de Compra:", tfRfechaUltCompra = new JDateChooser());
        añadirCampoConEtiqueta(panel, "Número Lote:", tfRN_Lote = new JTextField());
        añadirCampoConEtiqueta(panel, "Cantidad:", tfRcantidad = new JTextField());
        añadirCampoConEtiqueta(panel, "Fecha Caducidad:", tfrfecha_Caducidad = new JDateChooser());
        añadirCampoConEtiqueta(panel, "Proveedor:", tfRproveedor = new JTextField());
        añadirCampoConEtiqueta(panel, "Código de Barras:", tfRCodigo_Barras = new JTextField());
        añadirCampoConEtiqueta(panel, "Precio Bruto:", tfRprecio_Bruto = new JTextField());
        añadirCampoConEtiqueta(panel, "Observaciones:", tfRobservaciones = new JTextField());

        return panel;
    }

    private void añadirCampoConEtiqueta(JPanel panel, String labelText, Component field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(field);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#577BD1"));  // Fondo azul para el panel completo
        panel.setOpaque(true);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 15));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 15));

        rightPanel.setOpaque(false);  // Asegurar transparencia para ver el fondo azul
        leftPanel.setOpaque(false);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        btnGuardar.addActionListener(this::guardarDatos);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());

        personalizarBoton(btnGuardar);
        personalizarBoton(btnLimpiar);
        personalizarBoton(btnCerrar);

        rightPanel.add(btnLimpiar);
        rightPanel.add(btnGuardar);
        leftPanel.add(btnCerrar);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }
    private void personalizarBoton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.decode("#0057FF"));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);  // Necesario para ver el color de fondo
        button.setRolloverEnabled(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#003366"));  // Cambio a azul oscuro cuando el ratón está encima
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);  // Restaurar a blanco cuando el ratón se aleja
                button.setForeground(Color.decode("#0057FF"));
            }
        });
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
