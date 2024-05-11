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
        getContentPane().setLayout(new BorderLayout());
        ((JPanel)getContentPane()).setOpaque(false);
        getRootPane().setOpaque(false);
        setBackground(new Color(0, 0, 0, 0)); // Color transparente

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
        
        dateChooserNacimiento = new JDateChooser();
        dateChooserNacimiento.setDateFormatString("dd/MM/yyyy");


        JLabel label_9 = new JLabel("Sexo:");
        label_9.setForeground(new Color(255, 255, 255));
        label_9.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_9);
        panelDatos.add(cbMascotaSexo);

        JLabel label_8 = new JLabel("Nombre:");
        label_8.setForeground(new Color(255, 255, 255));
        label_8.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_8);
        panelDatos.add(tfMascotaNombre);
        JLabel label_7 = new JLabel("Especie:");
        label_7.setForeground(new Color(255, 255, 255));
        label_7.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_7);
        panelDatos.add(tfMascotaEspecie);
        JLabel label_6 = new JLabel("Raza:");
        label_6.setForeground(new Color(255, 255, 255));
        label_6.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_6);
        panelDatos.add(tfMascotaRaza);
        JLabel label_5 = new JLabel("Pasaporte:");
        label_5.setForeground(new Color(255, 255, 255));
        label_5.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_5);
        panelDatos.add(tfMascotapasaporte);
        JLabel label_4 = new JLabel("Microchip:");
        label_4.setForeground(new Color(255, 255, 255));
        label_4.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_4);
        panelDatos.add(tfMascotaMicrochip);
        JLabel label_3 = new JLabel("Fecha de Nacimiento:");
        label_3.setForeground(new Color(255, 255, 255));
        label_3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_3);
        panelDatos.add(dateChooserNacimiento);
        JLabel label_2 = new JLabel("Carácter:");
        label_2.setForeground(new Color(255, 255, 255));
        label_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_2);
        panelDatos.add(tfMascotaCaracter);
        JLabel label_1 = new JLabel("Color:");
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelDatos.add(label_1);
        panelDatos.add(tfMascotaColor);
        JLabel label = new JLabel("Tipo de Pelo:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(255, 255, 255));
        panelDatos.add(label);
        panelDatos.add(tfMascotaTipoPelo);
        panelDatos.add(new JLabel()); // Esta línea estaba duplicada
        panelDatos.add(cbMascotaEsterilizado);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
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

        panelBotones.add(btnCerrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);

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