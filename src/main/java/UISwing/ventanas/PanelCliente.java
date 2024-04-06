package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import DB.ClienteDAO;
import DB.MascotaDAO;
import UISwing.recursos.GradientPanel2;
import UISwing.recursos.RoundedButton;
import model.Cliente;
import model.Mascota;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelCliente extends JPanel {
    private ClienteDAO clienteDao;
    private Cliente cliente;
    private JTabbedPane tabbedPane;
    private JTable tablaMascotas;

    public PanelCliente(int idCliente) {
        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        this.cliente = clienteDao.obtenerClientePorId(idCliente);
        initializeUI();
        actualizarTablaMascotas(); 
    }

    private void initializeUI() {
        tabbedPane = new JTabbedPane();
        // Crea e inicializa los paneles que se añadirán como pestañas
        JPanel panelInfoCliente = crearPanelInfoCliente();
        JPanel panelMascotas = crearPanelMascotasTotales(); // Ejemplo de panel para mascotas
        
        // Añade los paneles al JTabbedPane
        tabbedPane.addTab("Cliente", panelInfoCliente);
        tabbedPane.addTab("Mascotas", panelMascotas);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelInfoCliente() {
    	 JPanel panel = new GradientPanel2(); // Utiliza GradientPanel2 para un fondo con degradado
         panel.setLayout(new GridLayout(0, 2)); 
        if (this.cliente != null) {
            // Añadir los campos de texto y etiquetas al panel
            agregarCampo(panel, "Nombre:", cliente.getNombre());
            agregarCampo(panel, "Apellidos:", cliente.getApellidos());
            agregarCampo(panel, "DNI:", cliente.getDni());
            agregarCampo(panel, "NIF:", cliente.getNif());
            agregarCampo(panel, "Dirección:", cliente.getDireccion());
            agregarCampo(panel, "Población:", cliente.getPoblacion());
            agregarCampo(panel, "Provincia:", cliente.getProvincia());
            agregarCampo(panel, "Tel. Fijo:", cliente.getTelefonoFijo());
            agregarCampo(panel, "Tel. Móvil:", cliente.getTelefonoMovil());
            agregarCampo(panel, "Email:", cliente.getEmail());
            agregarCampo(panel, "Fecha de Nacimiento:", cliente.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            panel.add(new JLabel("Cliente no encontrado."));
        }

        return panel;
    }

    private void agregarCampo(JPanel panel, String etiqueta, String valor) {
        panel.add(new JLabel(etiqueta));
        JTextField textField = new JTextField(valor, 20);
        textField.setEditable(false);
        panel.add(textField);
    }

    private JPanel crearPanelMascotasTotales() {
        JPanel panelMascotas = new GradientPanel2(); // Usa GradientPanel2 para el fondo
        panelMascotas.setLayout(new BorderLayout());
        panelMascotas.add(new JLabel("Lista de mascotas"), BorderLayout.NORTH);

        String[] columnas = {"ID Mascota", "Nombre", "Especie", "Raza"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMascotas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMascotas);
        panelMascotas.add(scrollPane, BorderLayout.CENTER);

        RoundedButton botonAgregarMascota = new RoundedButton("Añadir Mascota"); // Usa RoundedButton
        botonAgregarMascota.addActionListener(e -> abrirPanelRegistroMascota());
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false); // Hazlo transparente para que se muestre el fondo degradado
        panelBoton.add(botonAgregarMascota);
        panelMascotas.add(panelBoton, BorderLayout.SOUTH);

        tablaMascotas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifica si el evento es un doble clic
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) { // Verifica si hay una fila seleccionada
                        int idMascota = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0); // Obtiene el ID de la mascota seleccionada
                        abrirPanelInfoMascota(idMascota); // Abre el panel de información de la mascota usando el ID obtenido
                    }
                }
            }
        });

        return panelMascotas;
    }
    private void abrirPanelInfoMascota(int idMascota) {
        // Aquí debes implementar la lógica para abrir un panel con información detallada de la mascota
        // Puedes crear un nuevo panel o ventana para mostrar la información de la mascota
        // Por ejemplo:
        PanelInfoMascota panelInfoMascota = new PanelInfoMascota(idMascota);
        JFrame frameInfoMascota = new JFrame("Información de la Mascota");
        frameInfoMascota.getContentPane().add(panelInfoMascota);
        frameInfoMascota.pack();
        frameInfoMascota.setLocationRelativeTo(null);
        frameInfoMascota.setVisible(true);
        frameInfoMascota.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void abrirPanelRegistroMascota() {
        PanelRegistroMascota panelRegistroMascota = new PanelRegistroMascota(this.cliente.getId());
        JFrame frameRegistroMascota = new JFrame("Registro de Mascota");
        frameRegistroMascota.getContentPane().add(panelRegistroMascota);
        frameRegistroMascota.pack();
        frameRegistroMascota.setLocationRelativeTo(null);
        frameRegistroMascota.setVisible(true);

        frameRegistroMascota.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaMascotas();
            }
        });

        frameRegistroMascota.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Asegúrate de que la ventana se dispose al cerrar
    }

    public void actualizarTablaMascotas() {
        if (tablaMascotas == null) {
            return; // Si tablaMascotas no se ha inicializado, sal del método.
        }
        MascotaDAO mascotaDao = new MascotaDAO();
        List<Mascota> listaMascotas = mascotaDao.obtenerMascotasPorClienteId(this.cliente.getId());
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaMascotas.getModel();
        modeloTabla.setRowCount(0); // Limpia la tabla antes de rellenar

        for (Mascota mascota : listaMascotas) {
            modeloTabla.addRow(new Object[]{mascota.getId(), mascota.getNombre(), mascota.getEspecie(), mascota.getRaza()});
        }
    }
    
    public PanelCliente(String dni) {
        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        this.cliente = clienteDao.obtenerClientePorDni(dni); // Asegúrate de implementar obtenerClientePorDni en ClienteDAO
        if (this.cliente != null) {
            initializeUI();
            actualizarTablaMascotas();
        } else {
            add(new JLabel("Cliente no encontrado."), BorderLayout.CENTER);
        }
    }
    
    
    
    
}