package application;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import UISwing.recursos.GradientPanel;
import UISwing.ventanas.RecuperarCuenta;
import UISwing.ventanas.RegistrosLogin;
import model.Authentication;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;



public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textUsuario;
    private JPasswordField textcontraseña;
    private JLabel lblusuario;
    private JLabel lblcontraseña;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginFrame() {
        setBounds(100, 100, 497, 524);
        setUndecorated(true);
        
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/LogoOscuroPawCloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        GradientPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        JLabel lblLogoVertical = new JLabel("");
        lblLogoVertical.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo_vertical.png")));
        lblLogoVertical.setBounds(173, 76, 165, 111);
        gradientPanel.add(lblLogoVertical);

        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        textUsuario = new JTextField();
        textUsuario.setBounds(148, 218, 204, 30);
        gradientPanel.add(textUsuario);
        textUsuario.setColumns(10);
        textUsuario.setBorder(roundedBorder);
        textUsuario.setOpaque(false);
        textUsuario.setForeground(Color.WHITE);

        textcontraseña = new JPasswordField();
        textcontraseña.setBounds(148, 277, 204, 30);
        gradientPanel.add(textcontraseña);
        textcontraseña.setColumns(10);
        textcontraseña.setBorder(roundedBorder);
        textcontraseña.setOpaque(false);
        textcontraseña.setForeground(Color.WHITE);

        lblusuario = new JLabel("Usuario");
        lblusuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblusuario.setForeground(new Color(255, 255, 255));
        lblusuario.setBounds(148, 198, 46, 14);
        gradientPanel.add(lblusuario);

        lblcontraseña = new JLabel("Contraseña");
        lblcontraseña.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña.setForeground(new Color(255, 255, 255));
        lblcontraseña.setBounds(148, 259, 84, 14);
        gradientPanel.add(lblcontraseña);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBounds(148, 339, 204, 34);
        btnLogin.setBackground(Color.decode("#0057FF"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnLogin.addActionListener(e -> iniciarSesion());
        gradientPanel.add(btnLogin);

        JLabel lbllogocerrar = new JLabel("");
        lbllogocerrar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        lbllogocerrar.setBounds(445, 11, 26, 30);
        gradientPanel.add(lbllogocerrar);
        
        lbllogocerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                System.exit(0);
            }
        });


        JLabel lblolvidaste = new JLabel("¿Olvidaste la contraseña?");
        lblolvidaste.setForeground(new Color(255, 255, 255));
        lblolvidaste.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblolvidaste.setBounds(186, 390, 166, 21);
        gradientPanel.add(lblolvidaste);
        lblolvidaste.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RecuperarCuenta recuperarCuenta = new RecuperarCuenta();
                recuperarCuenta.setVisible(true);
                LoginFrame.this.setVisible(false);
            }
        });


        JLabel lblnotienescuenta = new JLabel("¿No tienes cuenta?");
        lblnotienescuenta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblnotienescuenta.setForeground(new Color(255, 255, 255));
        lblnotienescuenta.setBounds(173, 445, 121, 21);
        gradientPanel.add(lblnotienescuenta);

        JLabel lblRegistrar = new JLabel("Regístrate");
        lblRegistrar.setForeground(new Color(255, 255, 255));
        lblRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRegistrar.setBounds(279, 448, 63, 14);
        gradientPanel.add(lblRegistrar);
        lblRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegistrosLogin registro = new RegistrosLogin();
                registro.setVisible(true);
                LoginFrame.this.setVisible(false);
            }
        });
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
               
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        centerPanel.setBackground(new Color(255, 255, 255, 80));
        centerPanel.setOpaque(false);
        centerPanel.setBounds(95, 53, 308, 392);
        gradientPanel.add(centerPanel);
    }

    private void iniciarSesion() {
        String username = textUsuario.getText().trim();
        String password = new String((textcontraseña).getPassword()).trim();

        Authentication auth = new Authentication();

        if (auth.authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            MainFrame mainFrame = new MainFrame();
            mainFrame.setNombreUsuario(username);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
