package UISwing.ventanas;

// file path: package UISwing.ventanas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import DB.VentasDAO;
import UISwing.recursos.RoundedPanel;
import model.Cita;
import model.Cliente;
import model.Mascota;
import model.VentaDetalle;

public class PanelCliente extends JPanel implements CitaActualizadaListener {
    private ClienteDAO clienteDao;
    private Cliente cliente;
    private JTabbedPane tabbedPane;
    private JTable tablaMascotas;
    private JTable tablaCitas;
    private JTable tablaVentas;
    private DefaultTableModel modeloTablaCitas;
    private DefaultTableModel modeloTablaVentas;

    public PanelCliente(int idCliente) {
        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        this.cliente = clienteDao.obtenerClientePorId(idCliente);
        initializeUI();
        actualizarTablaMascotas(); 
        actualizarTablaVentas();
    }

    private void initializeUI() {
        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#7E88E2"));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Cliente", crearPanelInfoCliente());
        tabbedPane.addTab("Mascotas", crearPanelMascotasTotales());
        tabbedPane.addTab("Citas", crearPanelCitas());
        tabbedPane.addTab("Ventas", crearPanelVentas());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel crearPanelInfoCliente() {
        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(new GridLayout(0, 2));
        panel.setBackground(Color.decode("#7E88E2"));

        if (this.cliente != null) {
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
        RoundedPanel panelMascotas = new RoundedPanel(20);
        panelMascotas.setLayout(new BorderLayout());
        panelMascotas.setBackground(Color.decode("#7E88E2"));
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
        scrollPane.setPreferredSize(new Dimension(500, 400));
        panelMascotas.add(scrollPane, BorderLayout.CENTER);

        JButton botonAgregarMascota = crearBotonPersonalizado("Añadir Mascota", e -> abrirPanelRegistroMascota());
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(botonAgregarMascota);
        panelMascotas.add(panelBoton, BorderLayout.SOUTH);

        tablaMascotas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int idMascota = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
                        abrirPanelInfoMascota(idMascota);
                    }
                }
            }
        });

        return panelMascotas;
    }

    private JPanel crearPanelCitas() {
        String[] columnas = {"Mascota", "Fecha", "Título"};
        modeloTablaCitas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCitas = new JTable(modeloTablaCitas);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        RoundedPanel panelCitas = new RoundedPanel(20);
        panelCitas.setBackground(Color.decode("#7E88E2"));
        panelCitas.setLayout(new BorderLayout());

        panelCitas.add(scrollPane, BorderLayout.CENTER);

        JButton btnAñadirCita = crearBotonPersonalizado("Añadir Cita", e -> abrirDialogoAñadirCita());
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAñadirCita);
        panelCitas.add(panelBoton, BorderLayout.SOUTH);

        actualizarTablaCitas();

        return panelCitas;
    }

    private JPanel crearPanelVentas() {
        String[] columnas = {"Hora", "Producto", "Cantidad", "Precio"};
        modeloTablaVentas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTablaVentas);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#7E88E2"));
        panelVentas.setLayout(new BorderLayout());

        panelVentas.add(scrollPane, BorderLayout.CENTER);

        actualizarTablaVentas();

        return panelVentas;
    }

    private JButton crearBotonPersonalizado(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.decode("#0057FF"));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setRolloverEnabled(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.decode("#003366"));
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.decode("#0057FF"));
            }
        });
        btn.addActionListener(listener);
        return btn;
    }

    private void actualizarTablaVentas() {
        VentasDAO ventasDAO = new VentasDAO();
        modeloTablaVentas.setRowCount(0);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        if (cliente != null && cliente.getId() > 0) {
            try {
                // Fetch all sales
                List<VentaDetalle> listaVentas = ventasDAO.obtenerUltimasVentas();
                for (VentaDetalle venta : listaVentas) {
                    // Check if the sale matches the client's ID
                    if (ventasDAO.obtenerIdClientePorIdVenta(venta.getIdVenta()) == cliente.getId()) {
                        Object[] fila = {
                            timeFormat.format(venta.getFechaVenta()),
                            venta.getProducto(),
                            venta.getCantidad(),
                            String.format("%.2f €", venta.getPrecioUnitario())
                        };
                        modeloTablaVentas.addRow(fila);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCitaActualizada() {
        actualizarTablaCitas();
    }

    private void actualizarTablaCitas() {
        CitaDAO citaDao = new CitaDAO();
        if (cliente != null && cliente.getId() > 0) {
            List<Cita> listaCitas = citaDao.recuperarCitasPorCliente(cliente.getId());
            modeloTablaCitas.setRowCount(0);

            for (Cita cita : listaCitas) {
                Object[] fila = {
                    cita.getNombreMascota(),
                    cita.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    cita.getTitulo(),
                };
                modeloTablaCitas.addRow(fila);
            }
        }
    }

    private void abrirDialogoAñadirCita() {
        VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
        dialog.setTitle("Añadir Cita");
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaCitas();
            }
        });
        dialog.setVisible(true);
    }

    private void abrirPanelInfoMascota(int idMascota) {
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
        frameRegistroMascota.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    }

    public void actualizarTablaMascotas() {
        if (tablaMascotas == null) {
            return;
        }
        MascotaDAO mascotaDao = new MascotaDAO();
        List<Mascota> listaMascotas = mascotaDao.obtenerMascotasPorClienteId(this.cliente.getId());
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaMascotas.getModel();
        modeloTabla.setRowCount(0);

        for (Mascota mascota : listaMascotas) {
            modeloTabla.addRow(new Object[]{mascota.getId(), mascota.getNombre(), mascota.getEspecie(), mascota.getRaza()});
        }
    }

    public PanelCliente(String dni) {
        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        this.cliente = clienteDao.obtenerClientePorDni(dni);
        if (this.cliente != null) {
            initializeUI();
            actualizarTablaMascotas();
            actualizarTablaVentas();
        } else {
            add(new JLabel("Cliente no encontrado."), BorderLayout.CENTER);
        }
    }
}
