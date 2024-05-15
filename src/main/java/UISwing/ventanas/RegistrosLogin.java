package UISwing.ventanas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UISwing.recursos.GradientPanel;
import application.LoginFrame;
import model.UserModel;

public class RegistrosLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrosLogin frame = new RegistrosLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegistrosLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        lblLogoVertical.setBounds(170, 36, 165, 111);
        gradientPanel.add(lblLogoVertical);
        
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        JTextField textUsuario = new JTextField();
        textUsuario.setBounds(148, 178, 204, 30);
        gradientPanel.add(textUsuario);
        textUsuario.setColumns(10);
        textUsuario.setBorder(roundedBorder);
        textUsuario.setOpaque(false);
        textUsuario.setForeground(Color.white);
        
        JTextField textcorreo = new JTextField();
        textcorreo.setOpaque(false);
        textcorreo.setColumns(10);
        textcorreo.setBounds(148, 239, 204, 30);
        gradientPanel.add(textcorreo);
        textcorreo.setBorder(roundedBorder);
        textcorreo.setOpaque(false);
        textcorreo.setForeground(Color.WHITE);
        
        JPasswordField textcontraseña = new JPasswordField();
        textcontraseña.setOpaque(false);
        textcontraseña.setColumns(10);
        textcontraseña.setBounds(148, 299, 204, 30);
        gradientPanel.add(textcontraseña);
        textcontraseña.setBorder(roundedBorder);
        textcontraseña.setForeground(Color.WHITE);

        JPasswordField textrepeatcontraseña = new JPasswordField();
        textrepeatcontraseña.setBounds(148, 359, 204, 30);
        gradientPanel.add(textrepeatcontraseña);
        textrepeatcontraseña.setColumns(10);
        textrepeatcontraseña.setBorder(roundedBorder);
        textrepeatcontraseña.setOpaque(false);
        textrepeatcontraseña.setForeground(Color.WHITE);
        
        
        
        JLabel lblusuario = new JLabel("Usuario");
        lblusuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblusuario.setForeground(new Color(255, 255, 255));
        lblusuario.setBounds(148, 158, 46, 14);
        gradientPanel.add(lblusuario);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setForeground(Color.WHITE);
        lblemail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblemail.setBounds(148, 219, 46, 14);
        gradientPanel.add(lblemail);
        
        JLabel lblcontraseña_2 = new JLabel("Contraseña");
        lblcontraseña_2.setForeground(Color.WHITE);
        lblcontraseña_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña_2.setBounds(148, 280, 84, 14);
        gradientPanel.add(lblcontraseña_2);

        JLabel lblcontraseña = new JLabel("Repetir contraseña");
        lblcontraseña.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña.setForeground(new Color(255, 255, 255));
        lblcontraseña.setBounds(148, 340, 126, 14);
        gradientPanel.add(lblcontraseña);
        
        JButton btnRegistrarse = new JButton("Regístrarse");
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrarse.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnRegistrarse.setBackground(new Color(0, 87, 255));
        btnRegistrarse.setBounds(148, 407, 204, 34);
        gradientPanel.add(btnRegistrarse);
        btnRegistrarse.addActionListener(e -> {
            String username = textUsuario.getText().trim();
            String email = textcorreo.getText().trim();
            String password = new String(textcontraseña.getPassword()).trim();
            String repeatPassword = new String(textrepeatcontraseña.getPassword()).trim();

            // Verifica campos vacíos
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verifica que el email contenga "@"
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Introduce un email válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica la longitud mínima de la contraseña
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserModel userModel = new UserModel();
            int result = userModel.registerUser(username, email, password);

            switch (result) {
                case 1: 
                    JOptionPane.showMessageDialog(this, "Registro exitoso. Por favor, inicie sesión.", "Registro", JOptionPane.INFORMATION_MESSAGE);
                    
                    break;
                case -1: 
                    JOptionPane.showMessageDialog(this, "Se ha alcanzado el número máximo de registros permitidos. Hable con el administrador.", "Límite Alcanzado", JOptionPane.ERROR_MESSAGE);
                    break;
                default: 
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario. Por favor, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });

        
        JLabel lblVolver = new JLabel("Volver");
        lblVolver.setForeground(new Color(255, 255, 255));
        lblVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolver.setBounds(235, 462, 48, 14);
        gradientPanel.add(lblVolver);
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame login = new LoginFrame(); 
                login.setVisible(true); 
                RegistrosLogin.this.dispose();
            }
        });

      
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
        centerPanel.setBounds(95, 21, 308, 480);
        gradientPanel.add(centerPanel);
 
	}
	
	
}
