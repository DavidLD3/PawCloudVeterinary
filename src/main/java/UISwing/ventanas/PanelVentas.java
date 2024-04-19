package UISwing.ventanas;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import UISwing.recursos.RoundedPanel;
import UISwing.recursos.CustomPanelOpaco;

public class PanelVentas extends JPanel {

    public PanelVentas() {
        setLayout(null);
        setBackground(new Color(255, 255, 255, 0)); // Transparente o ajusta según necesidad

        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#577BD1"));
        panelVentas.setBounds(20, 20, 1081, 610); // Ajusta la posición y tamaño según necesites
        panelVentas.setLayout(null);
        add(panelVentas);

        JButton btnAñadirVentas = new JButton("Añadir Venta");
        btnAñadirVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirVentas.setBounds(24, 73, 374, 31);
        btnAñadirVentas.setBackground(Color.WHITE);
        btnAñadirVentas.setForeground(Color.decode("#0057FF"));
        btnAñadirVentas.setFocusPainted(false);
        btnAñadirVentas.setBorderPainted(false);
        btnAñadirVentas.setContentAreaFilled(false);
        btnAñadirVentas.setOpaque(true);
        panelVentas.add(btnAñadirVentas);

        CustomPanelOpaco panelOpacoVentas = new CustomPanelOpaco();
        panelOpacoVentas.setLayout(null);
        panelOpacoVentas.setBounds(24, 134, 640, 300);
        panelOpacoVentas.setBackground(new Color(255, 255, 255, 70));
        panelVentas.add(panelOpacoVentas);

        JLabel lbltextoVentas = new JLabel("Ventas");
        lbltextoVentas.setForeground(Color.WHITE);
        lbltextoVentas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoVentas.setBounds(48, 21, 171, 31);
        panelVentas.add(lbltextoVentas);

        JLabel lblLogoPanelVentas = new JLabel("");
        lblLogoPanelVentas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelVentas.png")));
        lblLogoPanelVentas.setBounds(24, 21, 22, 31);
        panelVentas.add(lblLogoPanelVentas);

        JPanel panelDatosVentas = new JPanel();
        panelDatosVentas.setLayout(new BoxLayout(panelDatosVentas, BoxLayout.Y_AXIS));
        panelDatosVentas.setBounds(0, 40, 640, 249);
        panelDatosVentas.setOpaque(false);
        panelOpacoVentas.add(panelDatosVentas);
    }
}