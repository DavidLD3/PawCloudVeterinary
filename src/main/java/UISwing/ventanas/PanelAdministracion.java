package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;

// Importa las clases necesarias para los paneles personalizados
import UISwing.recursos.RoundedPanel;
import UISwing.recursos.CustomPanelOpaco;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelAdministracion extends JPanel {

    public PanelAdministracion() {
        setLayout(null);
        setOpaque(false);

        // Panel Veterinarios con bordes redondeados
        RoundedPanel panelVeterinarios = new RoundedPanel(20);
        panelVeterinarios.setBackground(Color.decode("#6C7BED"));
        panelVeterinarios.setBounds(38, 24, 496, 607);
        panelVeterinarios.setLayout(null);
        add(panelVeterinarios);

        // Panel opaco interno para Veterinarios
        CustomPanelOpaco panelOpacoVeterinarios = new CustomPanelOpaco();
        panelOpacoVeterinarios.setLayout(null);
        panelOpacoVeterinarios.setBounds(41, 72, 415, 434);
        panelOpacoVeterinarios.setBackground(new Color(255, 255, 255, 70)); // Semi-transparente
        panelVeterinarios.add(panelOpacoVeterinarios);

        // Componentes dentro del panel opaco de Veterinarios
        // Estos labels y separadores se colocan sobre el panel opaco
        JLabel lblImageVet = new JLabel("Imagen");
        lblImageVet.setBounds(23, 11, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(82, 11, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.WHITE);
        separator.setBounds(10, 103, 395, 2);
        panelOpacoVeterinarios.add(separator);
        
        JLabel lblImageVet_1 = new JLabel("Imagen");
        lblImageVet_1.setBounds(23, 60, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_1);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(82, 60, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_1);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(Color.WHITE);
        separator_1.setBounds(10, 208, 395, 2);
        panelOpacoVeterinarios.add(separator_1);
        
        JLabel lblImageVet_1_1 = new JLabel("Imagen");
        lblImageVet_1_1.setBounds(23, 165, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_1_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("New label");
        lblNewLabel_1_1.setBounds(82, 165, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(82, 116, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_2);
        
        JLabel lblImageVet_2 = new JLabel("Imagen");
        lblImageVet_2.setBounds(23, 116, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_2);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setForeground(Color.WHITE);
        separator_2.setBounds(10, 313, 395, 2);
        panelOpacoVeterinarios.add(separator_2);
        
        JLabel lblImageVet_1_2 = new JLabel("Imagen");
        lblImageVet_1_2.setBounds(23, 270, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_1_2);
        
        JLabel lblNewLabel_1_2 = new JLabel("New label");
        lblNewLabel_1_2.setBounds(82, 270, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_1_2);
        
        JLabel lblNewLabel_3 = new JLabel("New label");
        lblNewLabel_3.setBounds(82, 221, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_3);
        
        JLabel lblImageVet_3 = new JLabel("Imagen");
        lblImageVet_3.setBounds(23, 221, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_3);
        
        JLabel lblImageVet_1_6 = new JLabel("Imagen");
        lblImageVet_1_6.setBounds(23, 375, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_1_6);
        
        JLabel lblNewLabel_1_6 = new JLabel("New label");
        lblNewLabel_1_6.setBounds(82, 375, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_1_6);
        
        JLabel lblNewLabel_7 = new JLabel("New label");
        lblNewLabel_7.setBounds(82, 326, 278, 38);
        panelOpacoVeterinarios.add(lblNewLabel_7);
        
        JLabel lblImageVet_7 = new JLabel("Imagen");
        lblImageVet_7.setBounds(23, 326, 38, 38);
        panelOpacoVeterinarios.add(lblImageVet_7);

        // Botones en el panel principal de Veterinarios (no en el opaco)
        JButton btnAgregarVeterinario = new JButton("Agregar Veterinario");
        btnAgregarVeterinario.setBounds(261, 539, 195, 37);
        btnAgregarVeterinario.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAgregarVeterinario.setBackground(Color.WHITE);
        btnAgregarVeterinario.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAgregarVeterinario.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAgregarVeterinario.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAgregarVeterinario.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAgregarVeterinario.setOpaque(true);
        btnAgregarVeterinario.setRolloverEnabled(true);
        btnAgregarVeterinario.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarVeterinario.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAgregarVeterinario.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarVeterinario.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAgregarVeterinario.setForeground(Color.decode("#0057FF"));
            }
        });
        btnAgregarVeterinario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el diálogo de registro de veterinario
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelAdministracion.this);
                VentanaRegistroVeterinarioDialog dialogoRegistroVeterinario = new VentanaRegistroVeterinarioDialog(frame, true);
                dialogoRegistroVeterinario.setVisible(true);
            }
        });
        panelVeterinarios.add(btnAgregarVeterinario);

        JButton btnListaVeterinarios = new JButton("Lista Veterinarios");
        btnListaVeterinarios.setBounds(41, 539, 195, 37);
        btnListaVeterinarios.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnListaVeterinarios.setBackground(Color.WHITE);
        btnListaVeterinarios.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnListaVeterinarios.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnListaVeterinarios.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnListaVeterinarios.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnListaVeterinarios.setOpaque(true);
        btnListaVeterinarios.setRolloverEnabled(true);
        btnListaVeterinarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListaVeterinarios.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnListaVeterinarios.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListaVeterinarios.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnListaVeterinarios.setForeground(Color.decode("#0057FF"));
            }
        });
        panelVeterinarios.add(btnListaVeterinarios);

        // Panel Empleados con bordes redondeados
        RoundedPanel panelEmpleados = new RoundedPanel(20);
        panelEmpleados.setBackground(Color.decode("#0483FF"));
        panelEmpleados.setBounds(578, 24, 496, 607);
        panelEmpleados.setLayout(null);
        add(panelEmpleados);

        // Panel opaco interno para Empleados
        CustomPanelOpaco panelOpacoEmpleados = new CustomPanelOpaco();
        panelOpacoEmpleados.setLayout(null);
        panelOpacoEmpleados.setBounds(41, 72, 415, 434);
        panelOpacoEmpleados.setBackground(new Color(255, 255, 255, 70));
        panelEmpleados.add(panelOpacoEmpleados);
        
        JSeparator separator_3 = new JSeparator();
        separator_3.setForeground(Color.WHITE);
        separator_3.setBounds(10, 103, 395, 2);
        panelOpacoEmpleados.add(separator_3);
        
        JLabel lblImageVet_1_3 = new JLabel("Imagen");
        lblImageVet_1_3.setBounds(23, 60, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_1_3);
        
        JLabel lblNewLabel_1_3 = new JLabel("New label");
        lblNewLabel_1_3.setBounds(82, 60, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_1_3);
        
        JLabel lblNewLabel_4 = new JLabel("New label");
        lblNewLabel_4.setBounds(82, 11, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_4);
        
        JLabel lblImageVet_4 = new JLabel("Imagen");
        lblImageVet_4.setBounds(23, 11, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_4);
        
        JSeparator separator_4 = new JSeparator();
        separator_4.setForeground(Color.WHITE);
        separator_4.setBounds(10, 208, 395, 2);
        panelOpacoEmpleados.add(separator_4);
        
        JLabel lblImageVet_1_4 = new JLabel("Imagen");
        lblImageVet_1_4.setBounds(23, 165, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_1_4);
        
        JLabel lblNewLabel_1_4 = new JLabel("New label");
        lblNewLabel_1_4.setBounds(82, 165, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_1_4);
        
        JLabel lblNewLabel_5 = new JLabel("New label");
        lblNewLabel_5.setBounds(82, 116, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_5);
        
        JLabel lblImageVet_5 = new JLabel("Imagen");
        lblImageVet_5.setBounds(23, 116, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_5);
        
        JSeparator separator_5 = new JSeparator();
        separator_5.setForeground(Color.WHITE);
        separator_5.setBounds(10, 313, 395, 2);
        panelOpacoEmpleados.add(separator_5);
        
        JLabel lblImageVet_1_5 = new JLabel("Imagen");
        lblImageVet_1_5.setBounds(23, 270, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_1_5);
        
        JLabel lblNewLabel_1_5 = new JLabel("New label");
        lblNewLabel_1_5.setBounds(82, 270, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_1_5);
        
        JLabel lblNewLabel_6 = new JLabel("New label");
        lblNewLabel_6.setBounds(82, 221, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_6);
        
        JLabel lblImageVet_6 = new JLabel("Imagen");
        lblImageVet_6.setBounds(23, 221, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_6);
        
        JLabel lblImageVet_1_7 = new JLabel("Imagen");
        lblImageVet_1_7.setBounds(23, 375, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_1_7);
        
        JLabel lblNewLabel_1_7 = new JLabel("New label");
        lblNewLabel_1_7.setBounds(82, 375, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_1_7);
        
        JLabel lblNewLabel_8 = new JLabel("New label");
        lblNewLabel_8.setBounds(82, 326, 278, 38);
        panelOpacoEmpleados.add(lblNewLabel_8);
        
        JLabel lblImageVet_8 = new JLabel("Imagen");
        lblImageVet_8.setBounds(23, 326, 38, 38);
        panelOpacoEmpleados.add(lblImageVet_8);

        // Botones en el panel principal de Empleados (no en el opaco)
        JButton btnAgregarEmpleado = new JButton("Agregar Empleado");
        btnAgregarEmpleado.setBounds(262, 539, 195, 37);
        btnAgregarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAgregarEmpleado.setBackground(Color.WHITE);
        btnAgregarEmpleado.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAgregarEmpleado.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAgregarEmpleado.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAgregarEmpleado.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAgregarEmpleado.setOpaque(true);
        btnAgregarEmpleado.setRolloverEnabled(true);
        btnAgregarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarEmpleado.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAgregarEmpleado.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarEmpleado.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAgregarEmpleado.setForeground(Color.decode("#0057FF"));
            }
        });
        btnAgregarEmpleado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crea y muestra el diálogo de registro de empleado
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelAdministracion.this);
                VentanaRegistroEmpleadoDialog dialogoRegistroEmpleado = new VentanaRegistroEmpleadoDialog(frame, true);
                dialogoRegistroEmpleado.setVisible(true);
            }
        });
        panelEmpleados.add(btnAgregarEmpleado);

        JButton btnListaEmpleados = new JButton("Lista Empleados");
        btnListaEmpleados.setBounds(41, 539, 195, 37);
        btnListaEmpleados.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnListaEmpleados.setBackground(Color.WHITE);
        btnListaEmpleados.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnListaEmpleados.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnListaEmpleados.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnListaEmpleados.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnListaEmpleados.setOpaque(true);
        btnListaEmpleados.setRolloverEnabled(true);
        btnListaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListaEmpleados.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnListaEmpleados.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListaEmpleados.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnListaEmpleados.setForeground(Color.decode("#0057FF"));
            }
        });
        btnListaEmpleados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirListaEmpleados();
            }
        });
        panelEmpleados.add(btnListaEmpleados);
        
    	JLabel lblVetImage = new JLabel("New label");
		lblVetImage.setBounds(41, 24, 46, 37);
		panelVeterinarios.add(lblVetImage);
		
		JLabel lblHorarioVeterinario = new JLabel("Horario Veterinario");
		lblHorarioVeterinario.setForeground(new Color(255, 255, 255));
		lblHorarioVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblHorarioVeterinario.setBounds(91, 24, 172, 37);
		panelVeterinarios.add(lblHorarioVeterinario);

		JLabel lblHorarioEmpleados = new JLabel("Horario Empleados");
		lblHorarioEmpleados.setForeground(new Color(255, 255, 255));
		lblHorarioEmpleados.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblHorarioEmpleados.setBounds(87, 24, 207, 37);
		panelEmpleados.add(lblHorarioEmpleados);
		
		JLabel lblEmpleadoImage = new JLabel("New label");
		lblEmpleadoImage.setBounds(41, 24, 46, 37);
		panelEmpleados.add(lblEmpleadoImage);
    }
    
    private void abrirListaEmpleados() {
        Frame frame = JOptionPane.getFrameForComponent(this);  // Encuentra el JFrame contenedor
        DialogoListaEmpleados dialog = new DialogoListaEmpleados(frame);
        dialog.setVisible(true);
    }

}
