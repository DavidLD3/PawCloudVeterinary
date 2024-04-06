package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTable;

public class PanelClienteMascota extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtBuscarClientemascota;
    private JTable PanelClientes;
    private JTextField txtBuscarCliente;
    private JTable PanelMascotas;

    /**
     * Create the panel.
     */
    public PanelClienteMascota() {
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(-10, 0, 1112, 664);
        add(panel);
        panel.setLayout(null);
        
        JButton btnExportar = new JButton("Exportar");
        btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnExportar.setBounds(256, 42, 89, 23);
        panel.add(btnExportar);
        
        JButton btnImportar = new JButton("Importar");
        btnImportar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnImportar.setBounds(355, 42, 89, 23);
        panel.add(btnImportar);
        
        JButton btnAnadir = new JButton("Añadir");
        btnAnadir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAnadir.setBounds(887, 42, 89, 23);
        panel.add(btnAnadir);
        
        JButton btnMostrarPor = new JButton("Mostrar por");
        btnMostrarPor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnMostrarPor.setBounds(516, 42, 89, 23);
        panel.add(btnMostrarPor);
        
        txtBuscarClientemascota = new JTextField("Buscar mascota");
        txtBuscarClientemascota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscarClientemascota.setBounds(716, 43, 161, 20);
        panel.add(txtBuscarClientemascota);
        
        txtBuscarCliente = new JTextField("Buscar cliente");
        txtBuscarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscarCliente.setBounds(42, 43, 166, 20);
        panel.add(txtBuscarCliente);
        
        PanelClientes = new JTable();
        PanelClientes.setBounds(10, 105, 549, 537);
        panel.add(PanelClientes);
        
        PanelMascotas = new JTable();
        PanelMascotas.setBounds(575, 105, 549, 537);
        panel.add(PanelMascotas);
    }
    
}