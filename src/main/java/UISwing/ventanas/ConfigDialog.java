package UISwing.ventanas;

import javax.swing.*;
import application.MainFrame;
import java.awt.*;
import java.awt.event.*;

public class ConfigDialog extends JDialog {
    private final JTextField txtMaxUsers;
    private final JButton btnSave;
    private final JButton btnCancel;

    public ConfigDialog(Frame parent, int currentLimit) {
        super(parent, "Configuración de Usuarios", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);      
        getContentPane().setBackground(Color.decode("#577BD1"));
        getContentPane().setLayout(null);

        JLabel lblMaxUsers = new JLabel("Número de Usuarios Registro :");
        lblMaxUsers.setForeground(Color.WHITE);
        lblMaxUsers.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMaxUsers.setBounds(38, 39, 199, 14);
        getContentPane().add(lblMaxUsers);

        txtMaxUsers = new JTextField(String.valueOf(currentLimit), 10);
        txtMaxUsers.setHorizontalAlignment(SwingConstants.CENTER);
        txtMaxUsers.setBounds(38, 64, 189, 20);
        getContentPane().add(txtMaxUsers);

        btnSave = new JButton("Guardar");
        btnSave.setBounds(186, 126, 90, 23);
        setupButtonStyle(btnSave);
        btnSave.addActionListener(e -> {
            int newLimit = Integer.parseInt(txtMaxUsers.getText().trim());
            ((MainFrame) parent).updateUserLimit(newLimit);
            setVisible(false);
        });
        getContentPane().add(btnSave);

        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(9, 127, 90, 23);
        setupButtonStyle(btnCancel);
        btnCancel.addActionListener(e -> setVisible(false));
        getContentPane().add(btnCancel);
    }

    private void setupButtonStyle(JButton button) {
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
}
