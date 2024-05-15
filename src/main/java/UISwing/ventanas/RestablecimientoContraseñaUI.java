package UISwing.ventanas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.mysql.cj.x.protobuf.Mysqlx.ClientMessagesOrBuilder;

import DB.BaseDatosServicio;
import UISwing.recursos.GradientPanel;
import application.LoginFrame;

public class RestablecimientoContraseñaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textcorreo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestablecimientoContraseñaUI frame = new RestablecimientoContraseñaUI();
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
	public RestablecimientoContraseñaUI() {
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
        lblLogoVertical.setBounds(170, 35, 165, 111);
        gradientPanel.add(lblLogoVertical);
        
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) 
        );
        
        
        textcorreo = new JTextField();
        textcorreo.setOpaque(false);
        textcorreo.setForeground(Color.WHITE);
        textcorreo.setColumns(10);
        textcorreo.setBounds(148, 177, 204, 30);
        gradientPanel.add(textcorreo);
        textcorreo.setBorder(roundedBorder);
        
        JTextField texttoken = new JTextField();
        texttoken.setOpaque(false);
        texttoken.setColumns(10);
        texttoken.setBounds(148, 238, 204, 30);
        gradientPanel.add(texttoken);
        texttoken.setBorder(roundedBorder);
        texttoken.setOpaque(false);
        texttoken.setForeground(Color.WHITE);
        
        JPasswordField textcontraseña = new JPasswordField();
        textcontraseña.setOpaque(false);
        textcontraseña.setBounds(148, 298, 204, 30);
        gradientPanel.add(textcontraseña);
        textcontraseña.setBorder(roundedBorder);
        textcontraseña.setForeground(Color.WHITE);

        JPasswordField textrepeatcontraseña = new JPasswordField();
        textrepeatcontraseña.setBounds(148, 358, 204, 30);
        gradientPanel.add(textrepeatcontraseña);
        textrepeatcontraseña.setBorder(roundedBorder);
        textrepeatcontraseña.setOpaque(false);
        textrepeatcontraseña.setForeground(Color.WHITE);
        
        JLabel lbltoken = new JLabel("Codigo");
        lbltoken.setForeground(Color.WHITE);
        lbltoken.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbltoken.setBounds(148, 218, 46, 14);
        gradientPanel.add(lbltoken);
        
        JLabel lblcontraseñaNueva = new JLabel("Nueva Contraseña");
        lblcontraseñaNueva.setForeground(Color.WHITE);
        lblcontraseñaNueva.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseñaNueva.setBounds(148, 279, 106, 14);
        gradientPanel.add(lblcontraseñaNueva);

        JLabel lblcontraseñaConfirmar = new JLabel("Confirmar Contraseña");
        lblcontraseñaConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseñaConfirmar.setForeground(new Color(255, 255, 255));
        lblcontraseñaConfirmar.setBounds(148, 339, 126, 14);
        gradientPanel.add(lblcontraseñaConfirmar);
        
        JButton btnRestablecer = new JButton("Restablecer Contraseña");
        btnRestablecer.setForeground(Color.WHITE);
        btnRestablecer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRestablecer.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnRestablecer.setBackground(new Color(0, 87, 255));
        btnRestablecer.setBounds(148, 406, 204, 34);
        gradientPanel.add(btnRestablecer);
        
     
        btnRestablecer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textcorreo.getText().trim();
                String token = texttoken.getText().trim();
                char[] nuevaContraseñaArray = textcontraseña.getPassword();  
                String nuevaContraseña = new String(nuevaContraseñaArray); 
                char[] confirmarContraseñaArray = textrepeatcontraseña.getPassword();
                String confirmarContraseña = new String(confirmarContraseñaArray);

                if (!nuevaContraseña.equals(confirmarContraseña)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    return;
                }

                if (BaseDatosServicio.verificarToken(email, token)) {
                    if (BaseDatosServicio.actualizarContraseña(email, nuevaContraseña)) {
                        JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente.");
                        LoginFrame login = new LoginFrame();
                        login.setVisible(true);
                        RestablecimientoContraseñaUI.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar la contraseña.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Token inválido o expirado.");
                }
            }
        });
    
        JLabel lblVolver = new JLabel("Volver");
        lblVolver.setForeground(new Color(255, 255, 255));
        lblVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolver.setBounds(226, 463, 48, 14);
        gradientPanel.add(lblVolver);
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
                RestablecimientoContraseñaUI.this.dispose();
            }
        });

      
        JLabel lbllogocerrar = new JLabel("");
        lbllogocerrar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        lbllogocerrar.setBounds(445, 11, 26, 30);
        gradientPanel.add(lbllogocerrar);
        
       
        JLabel lblemail = new JLabel("Email");
        lblemail.setForeground(Color.WHITE);
        lblemail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblemail.setBounds(148, 157, 46, 14);
        gradientPanel.add(lblemail);
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
