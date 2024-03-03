package UISwing.ventanas;

import javax.swing.*;

import DB.CitaDAO;
import UISwing.recursos.CustomPanelOpaco;
import UISwing.recursos.RoundedPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import model.Cita;
import java.util.List;

public class PanelHome extends JPanel {
	
	private JPanel panelDatos;
	private JLabel lblHoraCita;
	private JLabel lblDiaCita;
	private JLabel lblMascotaCita;
	private JLabel lblClienteCita;
	private JLabel lblHoraCita_2;
	private JLabel lblDiaCita_2;
	private JLabel lblMascotaCita_2;
	private JLabel lblNombreCliente2;
	private CustomPanelOpaco panelOpacoCitas;

    public PanelHome() {
        setLayout(null); // Usando layout nulo para control total sobre la posición de los componentes
        setOpaque(false);

        inicializarPanelCitas();
        mostrarCitasProximas(); // Luego puedes llamar a mostrarCitasProximas()
        inicializarPanelHospitalizados();
        inicializarPanelVentas(); // Lo mismo que farmacos pero mas corto
        inicializarPanelFarmacos(); // Panel fármacos sin bordes ni scrollbar visible
    }

    private void inicializarPanelCitas() {
        RoundedPanel panelCitas = new RoundedPanel(20);
        panelCitas.setBackground(Color.decode("#7E88E2"));
        panelCitas.setBounds(0, 0, 322, 402);
        add(panelCitas);
        panelCitas.setLayout(null);
        
        panelOpacoCitas = new CustomPanelOpaco();
        panelOpacoCitas.setBounds(24, 77, 274, 192);
        panelCitas.add(panelOpacoCitas);
        panelOpacoCitas.setLayout(null);
        
        JLabel lblProximasCitas = new JLabel("Próximas Citas");
        lblProximasCitas.setForeground(new Color(255, 255, 255));
        lblProximasCitas.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblProximasCitas.setBounds(46, 11, 113, 27);
        panelOpacoCitas.add(lblProximasCitas);
        
        lblHoraCita = new JLabel("");
        lblHoraCita.setForeground(new Color(255, 255, 255));
        lblHoraCita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraCita.setBounds(66, 49, 46, 28);
        panelOpacoCitas.add(lblHoraCita);
        
        lblDiaCita = new JLabel("");
        lblDiaCita.setForeground(new Color(255, 255, 255));
        lblDiaCita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaCita.setBounds(147, 49, 71, 28);
        panelOpacoCitas.add(lblDiaCita);
        
        lblMascotaCita = new JLabel("");
        lblMascotaCita.setForeground(new Color(255, 255, 255));
        lblMascotaCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaCita.setBounds(66, 76, 58, 26);
        panelOpacoCitas.add(lblMascotaCita);
        
        lblClienteCita = new JLabel("");
        lblClienteCita.setForeground(new Color(255, 255, 255));
        lblClienteCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteCita.setBounds(147, 76, 84, 26);
        panelOpacoCitas.add(lblClienteCita);
        
        JLabel lblLogoCitaMascota = new JLabel("");
        lblLogoCitaMascota.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoCitaMascota.setBounds(46, 76, 20, 26);
        panelOpacoCitas.add(lblLogoCitaMascota);
        
        JLabel lblLogoCitaCliente = new JLabel("");
        lblLogoCitaCliente.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoCitaCliente.setBounds(122, 76, 20, 26);
        panelOpacoCitas.add(lblLogoCitaCliente);
        
        lblHoraCita_2 = new JLabel("");
        lblHoraCita_2.setForeground(new Color(255, 255, 255));
        lblHoraCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraCita_2.setBounds(66, 113, 46, 27);
        panelOpacoCitas.add(lblHoraCita_2);	
        
        JLabel lblLogoCitaMascota2 = new JLabel("");
        lblLogoCitaMascota2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoCitaMascota2.setBounds(46, 139, 20, 26);
        panelOpacoCitas.add(lblLogoCitaMascota2);
        
        lblMascotaCita_2 = new JLabel("");
        lblMascotaCita_2.setForeground(new Color(255, 255, 255));
        lblMascotaCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaCita_2.setBounds(66, 139, 58, 26);
        panelOpacoCitas.add(lblMascotaCita_2);
        
        lblDiaCita_2 = new JLabel("");
        lblDiaCita_2.setForeground(new Color(255, 255, 255));
        lblDiaCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaCita_2.setBounds(147, 113, 71, 27);
        panelOpacoCitas.add(lblDiaCita_2);
        
        lblNombreCliente2 = new JLabel("");
        lblNombreCliente2.setForeground(new Color(255, 255, 255));
        lblNombreCliente2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombreCliente2.setBounds(147, 139, 84, 26);
        panelOpacoCitas.add(lblNombreCliente2);
        
        JLabel lblLogoCitaCliente2 = new JLabel("");
        lblLogoCitaCliente2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoCitaCliente2.setBounds(122, 139, 20, 26);
        panelOpacoCitas.add(lblLogoCitaCliente2);
        
        JLabel lblListadoCitas = new JLabel("Ver Listado");
        lblListadoCitas.setForeground(new Color(255, 255, 255));
        lblListadoCitas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoCitas.setBounds(129, 359, 71, 14);
        panelCitas.add(lblListadoCitas);
        // Hacer que el cursor cambie a la forma de mano al pasar sobre el JLabel
        lblListadoCitas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Agregar MouseListener al JLabel
        lblListadoCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crea y muestra el diálogo de listado de citas al hacer clic en el JLabel
                DialogoListaCitas dialogoListaCitas = new DialogoListaCitas(JFrame.getFrames()[0]); // Asume que este es el frame principal
                dialogoListaCitas.setVisible(true);
            }
        });

        
        JButton btnAñadirCita = new JButton("Añadir Cita");
        btnAñadirCita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirCita.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirCita.setBounds(24, 297, 274, 31);
        btnAñadirCita.setBackground(Color.WHITE);
        btnAñadirCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirCita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirCita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirCita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirCita.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirCita.setRolloverEnabled(true);
        btnAñadirCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAñadirCita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAñadirCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAñadirCita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAñadirCita.setForeground(Color.decode("#0057FF"));
            }
        });
        
        btnAñadirCita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
                dialog.setTitle("Añadir Cita");
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });


        panelCitas.add(btnAñadirCita);
        
        JLabel lblLogoCitas = new JLabel("");
        lblLogoCitas.setBounds(263, 11, 35, 45);
        lblLogoCitas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelCitas.png")));
        panelCitas.add(lblLogoCitas);
        
        JLabel lbltextoCitaspendientes = new JLabel("Citas Pendientes");
        lbltextoCitaspendientes.setForeground(new Color(255, 255, 255));
        lbltextoCitaspendientes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbltextoCitaspendientes.setBounds(24, 19, 120, 37);
        panelCitas.add(lbltextoCitaspendientes);
        
      

    }

    private void inicializarPanelHospitalizados() {
        RoundedPanel panelHospitalizados = new RoundedPanel(20);
        panelHospitalizados.setBackground(Color.decode("#0483FF"));
        panelHospitalizados.setBounds(347, 0, 321, 402);
        panelHospitalizados.setLayout(null);
        add(panelHospitalizados);
        
        CustomPanelOpaco panelOpacoHospita = new CustomPanelOpaco();
        panelOpacoHospita.setLayout(null);
        panelOpacoHospita.setBounds(24, 77, 274, 192);
        panelHospitalizados.add(panelOpacoHospita);
        
        JLabel lbltextoHospitalizados = new JLabel("Últimas hospitalizaciones");
        lbltextoHospitalizados.setForeground(Color.WHITE);
        lbltextoHospitalizados.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbltextoHospitalizados.setBounds(46, 11, 185, 27);
        panelOpacoHospita.add(lbltextoHospitalizados);
        
        JLabel lblHoraHospitalizacion = new JLabel("19:30");
        lblHoraHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraHospitalizacion.setBounds(66, 49, 46, 28);
        panelOpacoHospita.add(lblHoraHospitalizacion);
        
        JLabel lblDiaHospitalizacion = new JLabel("24/02/2024");
        lblDiaHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaHospitalizacion.setBounds(147, 49, 71, 28);
        panelOpacoHospita.add(lblDiaHospitalizacion);
        
        JLabel lblMascotaHospitalizacion = new JLabel("Yara");
        lblMascotaHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaHospitalizacion.setBounds(66, 74, 58, 28);
        panelOpacoHospita.add(lblMascotaHospitalizacion);
        
        JLabel lblClienteHospitalizacion = new JLabel("David");
        lblClienteHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteHospitalizacion.setBounds(147, 74, 84, 28);
        panelOpacoHospita.add(lblClienteHospitalizacion);
        
        JLabel lblLogoHospitalizacionMascota = new JLabel("");
        lblLogoHospitalizacionMascota.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoHospitalizacionMascota.setBounds(46, 74, 20, 28);
        panelOpacoHospita.add(lblLogoHospitalizacionMascota);
        
        JLabel lblLogoHospitalizacionCliente = new JLabel("");
        lblLogoHospitalizacionCliente.setBounds(122, 74, 20, 28);
        lblLogoHospitalizacionCliente.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        panelOpacoHospita.add(lblLogoHospitalizacionCliente);
        
        JLabel lblHoraHospitalizacion_2 = new JLabel("gsdgsdgsdg");
        lblHoraHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraHospitalizacion_2.setBounds(66, 113, 46, 27);
        panelOpacoHospita.add(lblHoraHospitalizacion_2);
        
        JLabel lblLogolblDiaHospitalizacionMascota_2 = new JLabel("");
        lblLogolblDiaHospitalizacionMascota_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogolblDiaHospitalizacionMascota_2.setBounds(46, 137, 20, 28);
        panelOpacoHospita.add(lblLogolblDiaHospitalizacionMascota_2);
        
        JLabel lblMascotaHospitalizacion_2 = new JLabel("New label");
        lblMascotaHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaHospitalizacion_2.setBounds(66, 137, 58, 28);
        panelOpacoHospita.add(lblMascotaHospitalizacion_2);
        
        JLabel lblDiaHospitalizacion_2 = new JLabel("gdsgsdgsdg");
        lblDiaHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaHospitalizacion_2.setBounds(147, 113, 71, 27);
        panelOpacoHospita.add(lblDiaHospitalizacion_2);
        
        JLabel lblClienteHospitalizacion_2 = new JLabel("New label");
        lblClienteHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteHospitalizacion_2.setBounds(147, 137, 84, 28);
        panelOpacoHospita.add(lblClienteHospitalizacion_2);
        
        JLabel lblLogoHospitalizacionCliente_2 = new JLabel("");
        lblLogoHospitalizacionCliente_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoHospitalizacionCliente_2.setBounds(122, 137, 20, 28);
        panelOpacoHospita.add(lblLogoHospitalizacionCliente_2);
        
        JButton btnAñadirHospita = new JButton("Añadir Cita");
        btnAñadirHospita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirHospita.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirHospita.setBounds(24, 297, 274, 31);
        btnAñadirHospita.setBackground(Color.WHITE);
        btnAñadirHospita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirHospita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirHospita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirHospita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirHospita.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirHospita.setRolloverEnabled(true);
        btnAñadirHospita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnAñadirHospita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnAñadirHospita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
            }
        });

        panelHospitalizados.add(btnAñadirHospita);
        
        JLabel lblListadoHospitalizaciones = new JLabel("Ver Listado");
        lblListadoHospitalizaciones.setForeground(Color.WHITE);
        lblListadoHospitalizaciones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoHospitalizaciones.setBounds(129, 359, 71, 14);
        panelHospitalizados.add(lblListadoHospitalizaciones);
        
        JLabel lblLogoHospita = new JLabel("");
        lblLogoHospita.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoHospita.png")));
        lblLogoHospita.setBounds(263, 11, 35, 45);
        panelHospitalizados.add(lblLogoHospita);
        
        JLabel lbltextoHospitapendientes = new JLabel("Hospitalizados");
        lbltextoHospitapendientes.setForeground(Color.WHITE);
        lbltextoHospitapendientes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbltextoHospitapendientes.setBounds(24, 11, 120, 37);
        panelHospitalizados.add(lbltextoHospitapendientes);

    }

    private void inicializarPanelVentas() {
        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#577BD1"));
        panelVentas.setBounds(692, 0, 420, 402);
        panelVentas.setLayout(null);
        add(panelVentas);
        
        JLabel lblListadoVentas = new JLabel("Ver Listado");
        lblListadoVentas.setForeground(Color.WHITE);
        lblListadoVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoVentas.setBounds(183, 359, 74, 14);
        panelVentas.add(lblListadoVentas);
        
        JButton btnAñadirVentas = new JButton("Añadir Venta");
        btnAñadirVentas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirVentas.setBounds(24, 297, 374, 31);
        btnAñadirVentas.setBackground(Color.WHITE);
        btnAñadirVentas.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirVentas.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirVentas.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirVentas.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirVentas.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirVentas.setRolloverEnabled(true);
        btnAñadirVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnAñadirVentas.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnAñadirVentas.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
            }
        });
        panelVentas.add(btnAñadirVentas);
        
        CustomPanelOpaco panelOpacoVentas = new CustomPanelOpaco();
        panelOpacoVentas.setLayout(null);
        panelOpacoVentas.setBounds(24, 77, 374, 191);
        panelVentas.add(panelOpacoVentas);
          
        JLabel lbltextoVentas = new JLabel("Últimas ventas");
        lbltextoVentas.setForeground(Color.WHITE);
        lbltextoVentas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbltextoVentas.setBounds(24, 17, 120, 31);
        panelVentas.add(lbltextoVentas);
        
        JLabel lblLogoPanelVentas = new JLabel("");
        lblLogoPanelVentas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelVentas.png")));
        lblLogoPanelVentas.setBounds(363, 17, 35, 45);
        panelVentas.add(lblLogoPanelVentas);
        
        JPanel panelEncabezadosVentas = crearPanelEncabezadosVentas();
        panelEncabezadosVentas.setOpaque(false);
        panelEncabezadosVentas.setBounds(0, 10, 376, 30); // Establece la posición y el tamaño del panel de encabezados de ventas
        panelOpacoVentas.add(panelEncabezadosVentas);

        JPanel panelDatosVentas = new JPanel();
        panelDatosVentas.setLayout(new BoxLayout(panelDatosVentas, BoxLayout.Y_AXIS));
        panelDatosVentas.setBounds(0, 40, 376, 152); // Establece la posición y el tamaño del panel de datos de ventas
        panelDatosVentas.setOpaque(false);
        panelOpacoVentas.add(panelDatosVentas);

        // Agrega algunos datos de ventas de ejemplo
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:00", "Alimento para perros", "2", "$20.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$15.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$15.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$150.00"});
       
    }

    private void inicializarPanelFarmacos() {
        // Panel principal de fármacos que contendrá todo
    	RoundedPanel panelFarmacos = new RoundedPanel(20); // Radio Borde
        panelFarmacos.setLayout(null);
        panelFarmacos.setBackground(Color.decode("#5C8CCD"));
        panelFarmacos.setBounds(0, 413, 1112, 240); 
        add(panelFarmacos);

        // Panel opaco que contendrá tanto el panel de encabezados como el panel de datos
        CustomPanelOpaco panelOpaco = new CustomPanelOpaco();
        panelOpaco.setLayout(null); 
        panelOpaco.setBounds(24, 57, 1065, 160);
        panelOpaco.setBackground(new Color(255, 255, 255, 80)); // El color se establece aquí pero la opacidad en paintComponent
        panelFarmacos.add(panelOpaco);

        // Panel de encabezados
        JPanel panelEncabezados = crearPanelEncabezados();
        panelEncabezados.setOpaque(false);
        panelEncabezados.setBounds(10, 10, 1034, 30);
        panelOpaco.add(panelEncabezados);

        // Panel de datos que contendrá las filas de datos
        panelDatos = new JPanel(); // Asignar el valor al campo de la clase
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBounds(10, 40, 1034, 110); // Ajusta el tamaño y la posición según tus necesidades
        panelDatos.setOpaque(false); // Hacer el panel transparente
        panelOpaco.add(panelDatos);
        
        JLabel lbltextoUltimosFarmacos = new JLabel("Ultimos fármacos utilizados");
        lbltextoUltimosFarmacos.setForeground(new Color(255, 255, 255));
        lbltextoUltimosFarmacos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbltextoUltimosFarmacos.setBounds(24, 11, 199, 28);
        panelFarmacos.add(lbltextoUltimosFarmacos);
        
        JLabel lblLogoUltimosFarmacos = new JLabel("");
        lblLogoUltimosFarmacos.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFarmacos.png")));
        lblLogoUltimosFarmacos.setBounds(1054, 11, 35, 28);
        panelFarmacos.add(lblLogoUltimosFarmacos);

        // Agregar algunos datos de ejemplo
        agregarFilaDatos(new String[]{"10:00", "2024-02-23", "Dr. Smith", "Fido", "Paracetamol", "2", "50mg", "12345"});
        agregarFilaDatos(new String[]{"11:00", "2024-02-23", "Dr. Jones", "Rex", "Ibuprofeno", "1", "200mg", "67890"});
        
    }
    
    private void mostrarCitasProximas() {
        CitaDAO citaDAO = new CitaDAO();
        List<Cita> citasProximas = citaDAO.recuperarCitasFuturas();

        if (!citasProximas.isEmpty()) {
            Cita primeraCita = citasProximas.get(0);
            lblHoraCita.setText(primeraCita.getHora() != null ? primeraCita.getHora().toString() : "Hora no disponible");
            lblDiaCita.setText(primeraCita.getFecha() != null ? primeraCita.getFecha().toString() : "Fecha no disponible");
            lblMascotaCita.setText(primeraCita.getNombreMascota());
            lblClienteCita.setText(primeraCita.getNombreCliente());

            if (citasProximas.size() > 1) {
                Cita segundaCita = citasProximas.get(1);
                lblHoraCita_2.setText(segundaCita.getHora() != null ? segundaCita.getHora().toString() : "Hora no disponible");
                lblDiaCita_2.setText(segundaCita.getFecha() != null ? segundaCita.getFecha().toString() : "Fecha no disponible");
                lblMascotaCita_2.setText(segundaCita.getNombreMascota());
                lblNombreCliente2.setText(segundaCita.getNombreCliente());
            }
        }

        panelOpacoCitas.revalidate();
        panelOpacoCitas.repaint();
    }


  


    private JPanel crearPanelEncabezados() {
        JPanel panelEncabezados = new JPanel();
        panelEncabezados.setLayout(new GridLayout(1, 8, 0, 0)); // 8 columnas como en tus datos
        panelEncabezados.setBackground(new Color(75, 75, 153)); // Un color de fondo
        panelEncabezados.setBounds(10, 10, 785, 30); // Tamaño y posición
        Font fuenteEncabezado = new Font("Segoe UI", Font.BOLD, 15);  //  Fuente del encabezado

        String[] encabezados = {"Hora", "Fecha", "Veterinario", "Paciente", "Nombre", "Cantidad", "Dosis", "Código"};
        for (String encabezado : encabezados) {
            JLabel labelEncabezado = new JLabel(encabezado, SwingConstants.CENTER);
            labelEncabezado.setForeground(Color.WHITE); // Color de texto
            labelEncabezado.setFont(fuenteEncabezado);
            panelEncabezados.add(labelEncabezado);
        }

        return panelEncabezados;
    }
    private void agregarFilaDatos(String[] datos) {
        JPanel panelFila = new JPanel();
        panelFila.setLayout(new GridLayout(1, datos.length, 0, 0)); // Asignar un GridLayout
        panelFila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Altura de la fila
        panelFila.setOpaque(false); // Hacer el panel transparente

        for (String dato : datos) {
            JLabel labelDato = new JLabel(dato, SwingConstants.CENTER);
            labelDato.setForeground(Color.WHITE); // Color de texto
            panelFila.add(labelDato);
        }

        panelDatos.add(panelFila);
        panelDatos.revalidate();
        panelDatos.repaint();
    }
    private JPanel crearPanelEncabezadosVentas() {
        JPanel panelEncabezadosVentas = new JPanel();
        panelEncabezadosVentas.setLayout(new GridLayout(1, 4, 0, 0)); // 4 columnas para las ventas
        panelEncabezadosVentas.setBackground(new Color(75, 75, 153)); // Color de fondo azul oscuro

        // Lista de encabezados para ventas
        String[] encabezadosVentas = {"Hora", "Producto", "Cantidad", "Precio"};
        for (String encabezado : encabezadosVentas) {
            JLabel labelEncabezado = new JLabel(encabezado, SwingConstants.CENTER);
            labelEncabezado.setForeground(Color.WHITE);
            labelEncabezado.setFont(new Font("Segoe UI", Font.BOLD, 15));
            panelEncabezadosVentas.add(labelEncabezado);
        }
        
        return panelEncabezadosVentas;
    }
    private void agregarFilaDatosVentas(JPanel panelDatosVentas, String[] datosVentas) {
        JPanel panelFilaVentas = new JPanel();
        panelFilaVentas.setLayout(new GridLayout(1, 4, 0, 0)); // 4 columnas para las ventas
        panelFilaVentas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ajusta la altura de la fila
        panelFilaVentas.setOpaque(false);

        for (String dato : datosVentas) {
            JLabel labelDato = new JLabel(dato, SwingConstants.CENTER);
            labelDato.setForeground(Color.WHITE);
            panelFilaVentas.add(labelDato);
        }

        panelDatosVentas.add(panelFilaVentas);
        panelDatosVentas.revalidate();
        panelDatosVentas.repaint();
    }
   


}
