package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import UISwing.recursos.RoundedPanel;

import com.toedter.calendar.JDateChooser;
import DB.FarmacoDAO;
import model.Farmaco;

public class DialogoRegistroFarmaco extends JDialog {
    private JTextField tfCodigo, tfNombre, tfDescripcion, tfCantidad, tfDosisRecomendada, tfUnidadMedida, tfPrecio;
    private JDateChooser tfFechaCaducidad;

    public DialogoRegistroFarmaco() {
        setUndecorated(true);
        setTitle("Registro de Fármacos");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setBackground(new Color(0, 0, 0, 0));

        RoundedPanel roundedBackground = new RoundedPanel(20);
        roundedBackground.setLayout(new BorderLayout());
        roundedBackground.setBackground(Color.decode("#577BD1"));
        roundedBackground.setOpaque(false);

        setContentPane(roundedBackground);

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

        tfCodigo = new JTextField();
        tfNombre = new JTextField();
        tfDescripcion = new JTextField();
        tfCantidad = new JTextField();
        tfDosisRecomendada = new JTextField();
        tfUnidadMedida = new JTextField();
        tfPrecio = new JTextField();
        tfFechaCaducidad = new JDateChooser();
        tfFechaCaducidad.setDateFormatString("dd/MM/yyyy"); // Formato de fecha

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCodigo.setForeground(Color.WHITE);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(Color.WHITE);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDescripcion.setForeground(Color.WHITE);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCantidad.setForeground(Color.WHITE);

        JLabel lblDosisRecomendada = new JLabel("Dosis Recomendada:");
        lblDosisRecomendada.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDosisRecomendada.setForeground(Color.WHITE);

        JLabel lblUnidadMedida = new JLabel("Unidad de Medida:");
        lblUnidadMedida.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUnidadMedida.setForeground(Color.WHITE);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPrecio.setForeground(Color.WHITE);

        JLabel lblFechaCaducidad = new JLabel("Fecha de Caducidad:");
        lblFechaCaducidad.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFechaCaducidad.setForeground(Color.WHITE);

        panel.add(lblCodigo);
        panel.add(tfCodigo);
        panel.add(lblNombre);
        panel.add(tfNombre);
        panel.add(lblDescripcion);
        panel.add(tfDescripcion);
        panel.add(lblCantidad);
        panel.add(tfCantidad);
        panel.add(lblDosisRecomendada);
        panel.add(tfDosisRecomendada);
        panel.add(lblUnidadMedida);
        panel.add(tfUnidadMedida);
        panel.add(lblPrecio);
        panel.add(tfPrecio);
        panel.add(lblFechaCaducidad);
        panel.add(tfFechaCaducidad);

        return panel;
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

        rightPanel.add(btnGuardar);  // Cambio de orden: Guardar junto a Limpiar
        rightPanel.add(btnLimpiar);
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
        // Validar que el código no sea nulo ni negativo
        String codigo = tfCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Código' no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int codigoInt = Integer.parseInt(codigo);
            if (codigoInt <= 0) {
                JOptionPane.showMessageDialog(this, "El código debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el nombre no esté vacío
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

        return true; // Ajusta según las validaciones necesarias
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
