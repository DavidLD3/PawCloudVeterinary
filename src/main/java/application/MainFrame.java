package application;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UISwing.recursos.GradientPanel2;
import UISwing.recursos.RoundedPanel;
import UISwing.ventanas.ConfigDialog;
import UISwing.ventanas.ConfiguracionClinica;
import UISwing.ventanas.PanelAdministracion;
import UISwing.ventanas.PanelAlmacen;
import UISwing.ventanas.PanelCalendario;
import UISwing.ventanas.PanelClienteMascota;
import UISwing.ventanas.PanelHome;
import UISwing.ventanas.PanelVentas;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.time.LocalDateTime;
import javax.swing.JButton;


public class MainFrame extends JFrame  {

    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout = new CardLayout(); 
    private JPanel cardPanel;
    private JLabel lblNombreUsuario;
    private JLabel lblRealTime;
    private PanelHome panelHome;
    private PanelVentas panelVentas;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainFrame() {
    	getContentPane().setBackground(Color.decode("#DAE7F7"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1367, 768);
        setLocationRelativeTo(null);
        setUndecorated(true);
        getContentPane().setLayout(null);
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false); 
        cardPanel.setBounds(234, 104, 1112, 653);
        getContentPane().add(cardPanel);
        
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/LogoOscuroPawCloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Inicialización y adición de PanelHome al cardPanel
        panelHome = new PanelHome(this);
        cardPanel.add(panelHome, "PanelHome");
        PanelVentas panelVentas = new PanelVentas();
        cardPanel.add(panelVentas, "PanelVentas");
        PanelClienteMascota panelClienteMascota = new PanelClienteMascota();
        cardPanel.add(panelClienteMascota, "PanelClienteMascota");
        PanelCalendario panelCalendario = new PanelCalendario();
        cardPanel.add(panelCalendario, "PanelCalendario");
        panelCalendario.addCitaActualizadaListener(panelHome);
        PanelAdministracion panelAdministracion = new PanelAdministracion();
        cardPanel.add(panelAdministracion, "PanelAdministracion");
        PanelAlmacen panelAlmacen = new PanelAlmacen();
        cardPanel.add(panelAlmacen, "PanelAlmacen");
        cardLayout.show(cardPanel, "PanelHome");
        
        
        GradientPanel2 gradientPanel = new GradientPanel2();
        gradientPanel.setBounds(0, 0, 215, 768);
        getContentPane().add(gradientPanel);
        gradientPanel.setLayout(null);
        
        JPanel panelMenu = new JPanel();
        panelMenu.setBounds(0, 0, 215, 768);
        gradientPanel.add(panelMenu);
        panelMenu.setLayout(null);
        panelMenu.setOpaque(false);
        
        JLabel lblnombreLogo = new JLabel("");
        lblnombreLogo.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo_horizontal.png")));
        lblnombreLogo.setFont(new Font("Dialog", Font.BOLD, 21));
        lblnombreLogo.setForeground(new Color(255, 255, 255));
        lblnombreLogo.setBounds(25, 19, 165, 63);
        panelMenu.add(lblnombreLogo);
        
        JLabel lblHome = new JLabel("Home");
        lblHome.setForeground(new Color(255, 255, 255));
        lblHome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHome.setBounds(55, 105, 75, 28);
        panelMenu.add(lblHome);
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelHome.actualizarTodo();
                cardLayout.show(cardPanel, "PanelHome");
            }
        });
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoHome.png")));
        lblNewLabel.setBounds(25, 105, 20, 28);
        panelMenu.add(lblNewLabel);
        
        JLabel lblclientemascotas = new JLabel("Cliente/Mascotas");
        lblclientemascotas.setForeground(new Color(255, 255, 255));
        lblclientemascotas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblclientemascotas.setBounds(55, 144, 150, 28);
        panelMenu.add(lblclientemascotas);
        lblclientemascotas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "PanelClienteMascota");
            }
        });
        
        JLabel lblLogoCliente = new JLabel("");
        lblLogoCliente.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoMascotas.png")));
        lblLogoCliente.setBounds(25, 144, 20, 28);
        panelMenu.add(lblLogoCliente);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCalendario.png")));
        lblNewLabel_1.setBounds(25, 183, 20, 28);
        panelMenu.add(lblNewLabel_1);
        
      
     // Ícono menú de Ventas
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoVentas.png")));
        lblNewLabel_2.setBounds(25, 222, 20, 28);
        panelMenu.add(lblNewLabel_2);

  
        JLabel lblVentas = new JLabel("Ventas");
        lblVentas.setForeground(Color.WHITE);
        lblVentas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblVentas.setBounds(55, 222, 130, 28);
        panelMenu.add(lblVentas);
        lblVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "PanelVentas");
            }
        });
        
        JLabel lblcalendario = new JLabel("Calendario");
        lblcalendario.setForeground(new Color(255, 255, 255));
        lblcalendario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblcalendario.setBounds(55, 183, 130, 28);
        panelMenu.add(lblcalendario);
        lblcalendario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "PanelCalendario");
            }
        });

        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoAlmacen.png")));
        lblNewLabel_3.setBounds(25, 261, 20, 28);
        panelMenu.add(lblNewLabel_3);
        
        JLabel lblAlmacen = new JLabel("Almacén");
        lblAlmacen.setForeground(new Color(255, 255, 255));
        lblAlmacen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAlmacen.setBounds(55, 261, 130, 28);
        panelMenu.add(lblAlmacen);
        lblAlmacen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "PanelAlmacen");
            }
        });
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoConfiguracion.png")));
        lblNewLabel_4.setBounds(25, 300, 20, 28);
        panelMenu.add(lblNewLabel_4);
        
        JLabel lblAdministracion = new JLabel("Administración");
        lblAdministracion.setForeground(new Color(255, 255, 255));
        lblAdministracion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAdministracion.setBounds(55, 300, 150, 28);
        panelMenu.add(lblAdministracion);
        lblAdministracion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "PanelAdministracion");
            }
        });

        JButton btnLogOut = new JButton("Salir");
        btnLogOut.setBounds(25, 714, 165, 28);
        btnLogOut.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogOut.setBackground(Color.WHITE);
        btnLogOut.setForeground(Color.decode("#0057FF"));
        btnLogOut.setFocusPainted(false);
        btnLogOut.setBorderPainted(false);
        btnLogOut.setContentAreaFilled(false);
        btnLogOut.setOpaque(true);
        btnLogOut.setRolloverEnabled(true);
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogOut.setBackground(Color.decode("#003366")); 
                btnLogOut.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogOut.setBackground(Color.WHITE);
                btnLogOut.setForeground(Color.decode("#0057FF"));
            }
        });
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnLogOut.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoLogout2.png")));
        btnLogOut.setIconTextGap(10);
        btnLogOut.setMargin(new Insets(0, 10, 0, 10));
        panelMenu.add(btnLogOut);

        RoundedPanel panelHeader = new RoundedPanel(20);
        panelHeader.setBounds(234, 11, 1112, 82);
        panelHeader.setBackground(new Color(255, 255, 255, 123));
        getContentPane().add(panelHeader);
        panelHeader.setLayout(null);
        
        lblNombreUsuario = new JLabel("Usuario");
        lblNombreUsuario.setBounds(1042, 11, 60, 60);
        panelHeader.add(lblNombreUsuario);
        
        
        lblRealTime = new JLabel("");
        lblRealTime.setBounds(20, 11, 200, 60);
        lblRealTime.setForeground(Color.decode("#909090"));
        lblRealTime.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelHeader.add(lblRealTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm:ss");
        
        new Timer(1000, e -> lblRealTime.setText(LocalDateTime.now().format(dtf))).start();
        
        
        JLabel lbllogoHeader = new JLabel("");
        lbllogoHeader.setBounds(1000, 11, 35, 60);
        lbllogoHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showConfigDialog();
            }
        });

        panelHeader.add(lbllogoHeader);
        lbllogoHeader.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoHeader.png")));
        

       
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        JPanel centerPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
               
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); 
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        centerPanel1.setBackground(new Color(255, 255, 255, 80));
        centerPanel1.setOpaque(false);
        centerPanel1.setBounds(10, 11, 195, 746);
        gradientPanel.add(centerPanel1);
        
        
    }
    public void changePanel(String name) {
        cardLayout.show(cardPanel, name);
    }
	 // Método para actualizar el nombre del usuario en el JLabel
	    public void setNombreUsuario(String nombreUsuario) {
	        lblNombreUsuario.setText(nombreUsuario);
	    }
	    public void switchPanel(String panelName) {
	        cardLayout.show(cardPanel, panelName);
	    }
	    public void showConfigDialog() {
	        ConfigDialog configDialog = new ConfigDialog(this, ConfiguracionClinica.getLimiteUsuarios());
	        configDialog.setVisible(true);
	    }


	    public void updateUserLimit(int newLimit) {
	        ConfiguracionClinica.setLimiteUsuarios(newLimit);
	        JOptionPane.showMessageDialog(this, "El límite de usuarios se ha actualizado a: " + newLimit);
	    }




}