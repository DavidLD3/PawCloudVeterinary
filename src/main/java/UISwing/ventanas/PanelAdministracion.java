package UISwing.ventanas;

import javax.swing.*;

import DB.EmpleadoDAO;
import DB.VeterinarioDAO;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.color.ColorSpace;

// Importa las clases necesarias para los paneles personalizados
import UISwing.recursos.RoundedPanel;
import model.Empleado;
import model.Veterinario;
import UISwing.recursos.CustomPanelOpaco;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class PanelAdministracion extends JPanel {
	private List<Veterinario> veterinarios;
    private List<Empleado> empleados;
	
	private JLabel lblNombreVet_1, lblHorarioVet_1,lblNombreVet_2,lblHorarioVet_2,lblNombreVet_3,lblHorarioVet_3,lblNombreVet_4,lblHorarioVet_4;
	private JLabel lblNombreEmpleado_1, lblHorarioEmpleado_1,lblNombreEmpleado_2,lblHorarioEmpleado_2,lblNombreEmpleado_3,lblHorarioEmpleado_3,lblNombreEmpleado_4,lblHorarioEmpleado_4;

    public PanelAdministracion() {
        setLayout(null);
        setOpaque(false);
        initComponents();
        cargarDatos();

    }
        private void initComponents() {
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
        	JLabel lblImageVet_1 = new JLabel("");
        	lblImageVet_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageVet_1.setBounds(33, 11, 28, 38);
        	panelOpacoVeterinarios.add(lblImageVet_1);
        	
        	lblNombreVet_1 = new JLabel("");
        	lblNombreVet_1.setBounds(82, 11, 278, 38);
        	lblNombreVet_1.setForeground(Color.WHITE);
            lblNombreVet_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblNombreVet_1);
        	
        	JSeparator separator = new JSeparator();
        	separator.setForeground(Color.WHITE);
        	separator.setBounds(10, 103, 395, 2);
        	panelOpacoVeterinarios.add(separator);
        	
        	JLabel lblVetHora_1 = new JLabel("");
        	lblVetHora_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblVetHora_1.setBounds(33, 60, 28, 38);
        	panelOpacoVeterinarios.add(lblVetHora_1);
        	
        	lblHorarioVet_1 = new JLabel("");
        	lblHorarioVet_1.setBounds(82, 60, 278, 38);
        	lblHorarioVet_1.setForeground(Color.WHITE);
            lblHorarioVet_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblHorarioVet_1);
        	
        	JSeparator separator_1 = new JSeparator();
        	separator_1.setForeground(Color.WHITE);
        	separator_1.setBounds(10, 208, 395, 2);
        	panelOpacoVeterinarios.add(separator_1);
        	
        	JLabel lblVetHora_2 = new JLabel("");
        	lblVetHora_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblVetHora_2.setBounds(33, 165, 28, 38);
        	panelOpacoVeterinarios.add(lblVetHora_2);
        	
        	lblHorarioVet_2 = new JLabel("");
        	lblHorarioVet_2.setBounds(82, 165, 278, 38);
        	lblHorarioVet_2.setForeground(Color.WHITE);
            lblHorarioVet_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblHorarioVet_2);
        	
        	lblNombreVet_2 = new JLabel("");
        	lblNombreVet_2.setBounds(82, 116, 278, 38);
        	lblNombreVet_2.setForeground(Color.WHITE);
            lblNombreVet_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblNombreVet_2);
        	
        	JLabel lblImageVet_2 = new JLabel("");
        	lblImageVet_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageVet_2.setBounds(33, 116, 28, 38);
        	panelOpacoVeterinarios.add(lblImageVet_2);
        	
        	JSeparator separator_2 = new JSeparator();
        	separator_2.setForeground(Color.WHITE);
        	separator_2.setBounds(10, 313, 395, 2);
        	panelOpacoVeterinarios.add(separator_2);
        	
        	JLabel lblVetHora_3 = new JLabel("");
        	lblVetHora_3.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblVetHora_3.setBounds(33, 270, 28, 38);
        	panelOpacoVeterinarios.add(lblVetHora_3);
        	
        	lblHorarioVet_3 = new JLabel("");
        	lblHorarioVet_3.setBounds(82, 270, 278, 38);
        	lblHorarioVet_3.setForeground(Color.WHITE);
            lblHorarioVet_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblHorarioVet_3);
        	
        	lblNombreVet_3 = new JLabel("");
        	lblNombreVet_3.setBounds(82, 221, 278, 38);
        	lblNombreVet_3.setForeground(Color.WHITE);
            lblNombreVet_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblNombreVet_3);
        	
        	JLabel lblImageVet_3 = new JLabel("");
        	lblImageVet_3.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageVet_3.setBounds(33, 221, 28, 38);
        	panelOpacoVeterinarios.add(lblImageVet_3);
        	
        	JLabel lblVetHora_4 = new JLabel("");
        	lblVetHora_4.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblVetHora_4.setBounds(33, 375, 28, 38);
        	panelOpacoVeterinarios.add(lblVetHora_4);
        	
        	lblHorarioVet_4 = new JLabel("");
        	lblHorarioVet_4.setBounds(82, 375, 278, 38);
        	lblHorarioVet_4.setForeground(Color.WHITE);
            lblHorarioVet_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblHorarioVet_4);
        	
        	lblNombreVet_4 = new JLabel("");
        	lblNombreVet_4.setBounds(82, 326, 278, 38);
        	lblNombreVet_4.setForeground(Color.WHITE);
            lblNombreVet_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoVeterinarios.add(lblNombreVet_4);
        	
        	JLabel lblImageVet_4 = new JLabel("");
        	lblImageVet_4.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageVet_4.setBounds(33, 326, 28, 38);
        	panelOpacoVeterinarios.add(lblImageVet_4);
        	
        	// Botones en el panel principal de Veterinarios (no en el opaco)
        	JButton btnAgregarVeterinario = new JButton("Agregar Veterinario");
            btnAgregarVeterinario.setBounds(261, 539, 195, 37);
            btnAgregarVeterinario.setFont(new Font("Tahoma", Font.BOLD, 12));
            btnAgregarVeterinario.setBackground(Color.WHITE);
            btnAgregarVeterinario.setForeground(Color.decode("#0057FF"));
            btnAgregarVeterinario.setFocusPainted(false);
            btnAgregarVeterinario.setBorderPainted(false);
            btnAgregarVeterinario.setContentAreaFilled(false);
            btnAgregarVeterinario.setOpaque(true);
            btnAgregarVeterinario.setRolloverEnabled(true);
            btnAgregarVeterinario.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnAgregarVeterinario.setBackground(Color.decode("#003366"));
                    btnAgregarVeterinario.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnAgregarVeterinario.setBackground(Color.WHITE);
                    btnAgregarVeterinario.setForeground(Color.decode("#0057FF"));
                }
            });
            btnAgregarVeterinario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelAdministracion.this);
                    VentanaRegistroVeterinarioDialog dialogoRegistroVeterinario = new VentanaRegistroVeterinarioDialog(frame, true, PanelAdministracion.this);
                    dialogoRegistroVeterinario.setVisible(true);
                }
            });
            panelVeterinarios.add(btnAgregarVeterinario);

        	
        	JButton btnListaVeterinarios = new JButton("Lista Veterinarios");
        	btnListaVeterinarios.setFont(new Font("Tahoma", Font.BOLD, 12));
        	btnListaVeterinarios.setBackground(Color.WHITE);
        	btnListaVeterinarios.setForeground(Color.decode("#0057FF")); // Color azul para el texto
        	btnListaVeterinarios.setFocusPainted(false);
        	btnListaVeterinarios.setBorderPainted(false);
        	btnListaVeterinarios.setContentAreaFilled(false);
        	btnListaVeterinarios.setOpaque(true);
        	btnListaVeterinarios.setBounds(41, 539, 195, 37);
        	btnListaVeterinarios.addMouseListener(new java.awt.event.MouseAdapter() {
        		@Override
        		public void mouseEntered(java.awt.event.MouseEvent evt) {
        			btnListaVeterinarios.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
        			btnListaVeterinarios.setForeground(Color.WHITE);
        		}
        		
        		@Override
        		public void mouseExited(java.awt.event.MouseEvent evt) {
        			btnListaVeterinarios.setBackground(Color.WHITE);
        			btnListaVeterinarios.setForeground(Color.decode("#0057FF"));
        		}
        	});
        	btnListaVeterinarios.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			abrirListaVeterinarios();
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
        	
        	JLabel lblIEmpHora_1 = new JLabel("");
        	lblIEmpHora_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblIEmpHora_1.setBounds(33, 60, 28, 38);
        	panelOpacoEmpleados.add(lblIEmpHora_1);
        	
        	lblHorarioEmpleado_1 = new JLabel("");
        	lblHorarioEmpleado_1.setBounds(82, 60, 278, 38);
        	lblHorarioEmpleado_1.setForeground(Color.WHITE);
            lblHorarioEmpleado_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblHorarioEmpleado_1);
        	
        	lblNombreEmpleado_1 = new JLabel("");
        	lblNombreEmpleado_1.setBounds(82, 11, 278, 38);
        	lblNombreEmpleado_1.setForeground(Color.WHITE);
            lblNombreEmpleado_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblNombreEmpleado_1);
        	
        	JLabel lblImageEmp_1 = new JLabel("");
        	lblImageEmp_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageEmp_1.setBounds(33, 11, 28, 38);
        	panelOpacoEmpleados.add(lblImageEmp_1);
        	
        	JSeparator separator_4 = new JSeparator();
        	separator_4.setForeground(Color.WHITE);
        	separator_4.setBounds(10, 208, 395, 2);
        	panelOpacoEmpleados.add(separator_4);
        	
        	JLabel lblIEmpHora_2 = new JLabel("");
        	lblIEmpHora_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblIEmpHora_2.setBounds(33, 165, 28, 38);
        	panelOpacoEmpleados.add(lblIEmpHora_2);
        	
        	lblHorarioEmpleado_2 = new JLabel("");
        	lblHorarioEmpleado_2.setBounds(82, 165, 278, 38);
        	lblHorarioEmpleado_2.setForeground(Color.WHITE);
            lblHorarioEmpleado_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblHorarioEmpleado_2);
        	
        	lblNombreEmpleado_2 = new JLabel("");
        	lblNombreEmpleado_2.setBounds(82, 116, 278, 38);
        	lblNombreEmpleado_2.setForeground(Color.WHITE);
            lblNombreEmpleado_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblNombreEmpleado_2);
        	
        	JLabel lblImageEmp_2 = new JLabel("");
        	lblImageEmp_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageEmp_2.setBounds(33, 116, 28, 38);
        	panelOpacoEmpleados.add(lblImageEmp_2);
        	
        	JSeparator separator_5 = new JSeparator();
        	separator_5.setForeground(Color.WHITE);
        	separator_5.setBounds(10, 313, 395, 2);
        	panelOpacoEmpleados.add(separator_5);
        	
        	JLabel lblIEmpHora_3 = new JLabel("");
        	lblIEmpHora_3.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblIEmpHora_3.setBounds(33, 270, 28, 38);
        	panelOpacoEmpleados.add(lblIEmpHora_3);
        	
        	lblHorarioEmpleado_3 = new JLabel("");
        	lblHorarioEmpleado_3.setBounds(82, 270, 278, 38);
        	lblHorarioEmpleado_3.setForeground(Color.WHITE);
            lblHorarioEmpleado_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblHorarioEmpleado_3);
        	
        	lblNombreEmpleado_3 = new JLabel("");
        	lblNombreEmpleado_3.setBounds(82, 221, 278, 38);
        	lblNombreEmpleado_3.setForeground(Color.WHITE);
            lblNombreEmpleado_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblNombreEmpleado_3);
        	
        	JLabel lblImageEmp_3 = new JLabel("");
        	lblImageEmp_3.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageEmp_3.setBounds(33, 221, 28, 38);
        	panelOpacoEmpleados.add(lblImageEmp_3);
        	
        	JLabel lblEmpHora_4 = new JLabel("");
        	lblEmpHora_4.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        	lblEmpHora_4.setBounds(33, 375, 28, 38);
        	panelOpacoEmpleados.add(lblEmpHora_4);
        	
        	lblHorarioEmpleado_4 = new JLabel("");
        	lblHorarioEmpleado_4.setBounds(82, 375, 278, 38);
        	lblHorarioEmpleado_4.setForeground(Color.WHITE);
            lblHorarioEmpleado_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblHorarioEmpleado_4);
        	
        	lblNombreEmpleado_4 = new JLabel("");
        	lblNombreEmpleado_4.setBounds(82, 326, 278, 38);
        	lblNombreEmpleado_4.setForeground(Color.WHITE);
            lblNombreEmpleado_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	panelOpacoEmpleados.add(lblNombreEmpleado_4);
        	
        	JLabel lblImageEmp_4 = new JLabel("");
        	lblImageEmp_4.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblImageEmp_4.setBounds(33, 326, 28, 38);
        	panelOpacoEmpleados.add(lblImageEmp_4);
        	
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
        	        VentanaRegistroEmpleadoDialog dialogoRegistroEmpleado = new VentanaRegistroEmpleadoDialog(frame, true, PanelAdministracion.this);
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
        	
        	JLabel lblVetImage = new JLabel("");
        	lblVetImage.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblVetImage.setBounds(41, 24, 24, 37);
        	panelVeterinarios.add(lblVetImage);
        	
        	JLabel lblHorarioVeterinario = new JLabel("Horario Veterinario");
        	lblHorarioVeterinario.setForeground(new Color(255, 255, 255));
        	lblHorarioVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 18));
        	lblHorarioVeterinario.setBounds(66, 24, 172, 37);
        	panelVeterinarios.add(lblHorarioVeterinario);
        	
        	JLabel lblHorarioEmpleados = new JLabel("Horario Empleados");
        	lblHorarioEmpleados.setForeground(new Color(255, 255, 255));
        	lblHorarioEmpleados.setFont(new Font("Segoe UI", Font.BOLD, 18));
        	lblHorarioEmpleados.setBounds(65, 24, 207, 37);
        	panelEmpleados.add(lblHorarioEmpleados);
        	
        	JLabel lblEmpleadoImage = new JLabel("");
        	lblEmpleadoImage.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        	lblEmpleadoImage.setBounds(41, 24, 24, 37);
        	panelEmpleados.add(lblEmpleadoImage);
        	
        }

    private void abrirListaEmpleados() {
        Frame frame = JOptionPane.getFrameForComponent(this);  // Encuentra el JFrame contenedor
        DialogoListaEmpleados dialog = new DialogoListaEmpleados(frame);
        dialog.setVisible(true);
    }
    private void abrirListaVeterinarios() {
        Frame frame = JOptionPane.getFrameForComponent(this);  // Encuentra el JFrame contenedor
        DialogoListaVeterinarios dialog = new DialogoListaVeterinarios(frame);
        dialog.setVisible(true);
    }
    
    private void cargarDatos() {
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        veterinarios = veterinarioDAO.obtenerTodosLosVeterinarios();
        empleados = empleadoDAO.obtenerTodosLosEmpleados();

        actualizarVistaVeterinarios(veterinarios);
        actualizarVistaEmpleados(empleados);
    }

    private void actualizarVistaVeterinarios(List<Veterinario> veterinarios) {
        JLabel[] nombresVet = {lblNombreVet_1, lblNombreVet_2, lblNombreVet_3, lblNombreVet_4};
        JLabel[] horariosVet = {lblHorarioVet_1, lblHorarioVet_2, lblHorarioVet_3, lblHorarioVet_4};
        int count = Math.min(veterinarios.size(), nombresVet.length);

        for (int i = 0; i < count; i++) {
            Veterinario v = veterinarios.get(i);
            nombresVet[i].setText(v.getNombre() + " " + v.getApellidos());
            horariosVet[i].setText(v.getHorarioTrabajo());
        }

        for (int i = count; i < nombresVet.length; i++) {
            nombresVet[i].setText("");
            horariosVet[i].setText("");
        }
    }
    private void actualizarVistaEmpleados(List<Empleado> empleados) {
        JLabel[] nombresEmp = {lblNombreEmpleado_1, lblNombreEmpleado_2, lblNombreEmpleado_3, lblNombreEmpleado_4};
        JLabel[] horariosEmp = {lblHorarioEmpleado_1, lblHorarioEmpleado_2, lblHorarioEmpleado_3, lblHorarioEmpleado_4};
        int count = Math.min(empleados.size(), nombresEmp.length);

        for (int i = 0; i < count; i++) {
            Empleado e = empleados.get(i);
            nombresEmp[i].setText(e.getNombre() + " " + e.getApellidos());
            horariosEmp[i].setText(e.getHorarioTrabajo());
        }

        for (int i = count; i < nombresEmp.length; i++) {
            nombresEmp[i].setText("");
            horariosEmp[i].setText("");
        }
    }
    public void agregarVeterinario(Veterinario veterinario) {
        // Initialize the list if it's not already initialized
        if (veterinarios == null) {
            veterinarios = new ArrayList<>();
        }
        veterinarios.add(veterinario);
        actualizarVistaVeterinarios(veterinarios);
    }
    public void agregarEmpleado(Empleado empleado) {
        if (empleados == null) {
            empleados = new ArrayList<>();
        }
        empleados.add(empleado);
        actualizarVistaEmpleados(empleados);
    }
}
