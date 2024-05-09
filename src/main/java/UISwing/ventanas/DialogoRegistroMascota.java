package UISwing.ventanas;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;

import DB.MascotaDAO;
import UISwing.recursos.RoundedPanel;
import model.Mascota;

public class DialogoRegistroMascota extends JDialog {

    private RoundedPanel roundedPanel;
    private JTextField tfMascotaNombre, tfMascotaEspecie, tfMascotaRaza, tfMascotapasaporte, tfMascotaMicrochip, tfMascotaCaracter, tfMascotaColor, tfMascotaTipoPelo;
    private JCheckBox cbMascotaEsterilizado;
    private JDateChooser dateChooserNacimiento;
    private JComboBox<String> cbMascotaSexo;
    private JButton btnGuardar, btnLimpiar, btnCerrar;
    private int idCliente;

    public DialogoRegistroMascota(Frame owner, boolean modal, int idCliente) {
        super(owner, modal);
        this.idCliente = idCliente;
        setTitle("Registro de Mascota");
        setUndecorated(true);
        setSize(new Dimension(549, 649));
        setLocationRelativeTo(null);

        // Configuración del fondo transparente
        setLayout(new BorderLayout());
        ((JPanel)getContentPane()).setOpaque(false);
        getRootPane().setOpaque(false);
        getContentPane().setBackground(new Color(0, 0, 0, 0)); // Color transparente

        roundedPanel = new RoundedPanel(30, new Color(112, 116, 178));
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.setOpaque(false); // Hacer el panel redondeado no opaco
        getContentPane().add(roundedPanel);

        initComponents();
    }

    private void initComponents() {
        JPanel panelDatos = new JPanel(new GridLayout(0, 2, 10, 10));
        panelDatos.setOpaque(false);
        roundedPanel.add(panelDatos, BorderLayout.CENTER);
        

        tfMascotaNombre = new JTextField();
        tfMascotaEspecie = new JTextField();
        tfMascotaRaza = new JTextField();
        tfMascotapasaporte = new JTextField();
        tfMascotaMicrochip = new JTextField();
        dateChooserNacimiento = new JDateChooser();
        tfMascotaCaracter = new JTextField();
        tfMascotaColor = new JTextField();
        tfMascotaTipoPelo = new JTextField();
        
        // Crear el JComboBox antes de agregarlo al panelDatos
        cbMascotaSexo = new JComboBox<>(new String[] {"Macho", "Hembra"});
        cbMascotaEsterilizado = new JCheckBox("Esterilizado");

        panelDatos.add(new JLabel("Sexo:"));
        panelDatos.add(cbMascotaSexo);

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(tfMascotaNombre);
        panelDatos.add(new JLabel("Especie:"));
        panelDatos.add(tfMascotaEspecie);
        panelDatos.add(new JLabel("Raza:"));
        panelDatos.add(tfMascotaRaza);
        panelDatos.add(new JLabel("Pasaporte:"));
        panelDatos.add(tfMascotapasaporte);
        panelDatos.add(new JLabel("Microchip:"));
        panelDatos.add(tfMascotaMicrochip);
        panelDatos.add(new JLabel("Fecha de Nacimiento:"));
        panelDatos.add(dateChooserNacimiento);
        panelDatos.add(new JLabel("Carácter:"));
        panelDatos.add(tfMascotaCaracter);
        panelDatos.add(new JLabel("Color:"));
        panelDatos.add(tfMascotaColor);
        panelDatos.add(new JLabel("Tipo de Pelo:"));
        panelDatos.add(tfMascotaTipoPelo);
        panelDatos.add(new JLabel()); // Esta línea estaba duplicada
        panelDatos.add(cbMascotaEsterilizado);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setOpaque(false);
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        // Configuración y eventos de los botones
        initButton(btnGuardar, "#0057FF", "#003366");
        initButton(btnLimpiar, "#0057FF", "#003366");
        initButton(btnCerrar, "#0057FF", "#003366");

        btnGuardar.addActionListener(e -> guardarMascota());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        roundedPanel.add(panelBotones, BorderLayout.SOUTH);  // Agrega el panel de botones al sur del roundedPanel
    }


    private void limpiarCampos() {
        tfMascotaNombre.setText("");
        tfMascotaEspecie.setText("");
        tfMascotaRaza.setText("");
        tfMascotapasaporte.setText("");
        tfMascotaMicrochip.setText("");
        dateChooserNacimiento.setCalendar(null);
        tfMascotaCaracter.setText("");
        tfMascotaColor.setText("");
        tfMascotaTipoPelo.setText("");
        cbMascotaSexo.setSelectedIndex(0); // Selecciona el primer elemento del combo box
        cbMascotaEsterilizado.setSelected(false);
    }

    private void guardarMascota() {
        // Suponiendo que has validado la entrada del usuario
        Mascota nuevaMascota = new Mascota();
        // Utiliza trim() en cada uno de los campos de texto para eliminar espacios innecesarios
        nuevaMascota.setNombre(tfMascotaNombre.getText().trim());
        nuevaMascota.setEspecie(tfMascotaEspecie.getText().trim());
        nuevaMascota.setRaza(tfMascotaRaza.getText().trim());
        nuevaMascota.setPasaporte(tfMascotapasaporte.getText().trim()); // Aquí se ha cambiado para tratar el pasaporte como una cadena
        nuevaMascota.setMicrochip(tfMascotaMicrochip.getText().trim());
        if (dateChooserNacimiento.getDate() != null) {
            nuevaMascota.setFechaNacimiento(dateChooserNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de nacimiento.");
            return; // Previene el cierre si la fecha de nacimiento no es válida
        }
        nuevaMascota.setCaracter(tfMascotaCaracter.getText().trim());
        nuevaMascota.setColor(tfMascotaColor.getText().trim());
        nuevaMascota.setTipoPelo(tfMascotaTipoPelo.getText().trim());
        // Asegúrate de que la conversión a enum aquí sea segura
        nuevaMascota.setSexo(Mascota.Sexo.valueOf(cbMascotaSexo.getSelectedItem().toString().toUpperCase()));
        nuevaMascota.setEsterilizado(cbMascotaEsterilizado.isSelected());
        nuevaMascota.setIdCliente(this.idCliente); // Asegúrate de que esta línea esté correctamente implementada

        MascotaDAO mascotaDao = new MascotaDAO();
        boolean exito = mascotaDao.insertarMascota(nuevaMascota);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Mascota guardada con éxito");
            limpiarCampos();
            // Cierra la ventana después de un guardado exitoso
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la mascota");
        }
    }

    private void initButton(JButton button, String colorHex, String rolloverColorHex) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.decode(colorHex));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setRolloverEnabled(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode(rolloverColorHex));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.decode(colorHex));
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        DialogoRegistroMascota dialogo = new DialogoRegistroMascota(frame, true, 123);
        dialogo.setVisible(true);
    }
}