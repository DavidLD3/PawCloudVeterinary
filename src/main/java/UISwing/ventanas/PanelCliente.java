package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;

import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import UISwing.recursos.GradientPanel2;
import UISwing.recursos.RoundedButton;
import UISwing.recursos.RoundedPanel;
import model.Cita;
import model.Cliente;
import model.Mascota;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelCliente extends JPanel implements CitaActualizadaListener {
    private ClienteDAO clienteDao;
    private Cliente cliente;
    private JTabbedPane tabbedPane;
    private JTable tablaMascotas;
    private JTable tablaCitas;
    private DefaultTableModel modeloTablaCitas;

    public PanelCliente(int idCliente) {
        // Inicializa el panel del cliente buscándolo por ID y prepara la UI.

        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        this.cliente = clienteDao.obtenerClientePorId(idCliente);
        initializeUI();
        actualizarTablaMascotas(); 
    }

    private void initializeUI() {
     // Configura las pestañas de la interfaz para detalles del cliente y sus mascotas.
        tabbedPane = new JTabbedPane();
        // Crea e inicializa los paneles que se añadirán como pestañas
        JPanel panelInfoCliente = crearPanelInfoCliente();
        JPanel panelMascotas = crearPanelMascotasTotales();  
        JPanel panelCitas = crearPanelCitas();
        // Añade los paneles al JTabbedPane
        tabbedPane.addTab("Cliente", panelInfoCliente);
        tabbedPane.addTab("Mascotas", panelMascotas);
        tabbedPane.addTab("Citas", panelCitas);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelInfoCliente() {
    // Crea y retorna un panel con los datos del cliente en un fondo con degradado.
    	 JPanel panel = new GradientPanel2(); 
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
     // Añade un campo de texto no editable para mostrar un dato del cliente.
        panel.add(new JLabel(etiqueta));
        JTextField textField = new JTextField(valor, 20);
        textField.setEditable(false);
        panel.add(textField);
    }

    private JPanel crearPanelMascotasTotales() {
    // Prepara y retorna un panel que lista las mascotas del cliente con opción a añadir más.
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
        panelBoton.setOpaque(false); 
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
    
    private JPanel crearPanelCitas() {
        // Ajustar las columnas de la tabla de citas según lo solicitado.
        String[] columnas = {"Mascota", "Fecha", "Título"};
        modeloTablaCitas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no deben ser editables.
            }
        };
        tablaCitas = new JTable(modeloTablaCitas);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        scrollPane.setPreferredSize(new Dimension(500, 400)); // Establecer el tamaño preferido del scrollPane.

        // Crear el panel principal con bordes redondeados.
        RoundedPanel panelCitas = new RoundedPanel(20);
        panelCitas.setBackground(Color.decode("#7E88E2"));
        panelCitas.setLayout(new BorderLayout()); // Usar BorderLayout para una mejor disposición de los componentes.

        // Añadir el scrollPane al centro del panel.
        panelCitas.add(scrollPane, BorderLayout.CENTER);

        // Botón para añadir citas, posicionado en el sur del panel.
        JButton btnAñadirCita = new JButton("Añadir Cita");
        personalizarBotonAñadirCita(btnAñadirCita); // Aplicar la personalización al botón.
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAñadirCita);
        panelCitas.add(panelBoton, BorderLayout.SOUTH); // Añadir el panel del botón en el sur.

        actualizarTablaCitas(); // Asegurarse de que la tabla se actualice con los datos actuales.

        return panelCitas;
    }
    
    @Override
    public void onCitaActualizada() {
        actualizarTablaCitas(); // Llama aquí al método para actualizar tu panel/tabla de citas
    }
    
    private void actualizarTablaCitas() {
        CitaDAO citaDao = new CitaDAO();
        // Asegúrate de que `cliente` no sea null y tenga un id válido.
        if(cliente != null && cliente.getId() > 0) {
            List<Cita> listaCitas = citaDao.recuperarCitasPorCliente(cliente.getId());
            modeloTablaCitas.setRowCount(0); // Limpiar la tabla antes de rellenarla.
        
            for (Cita cita : listaCitas) {
                Object[] fila = {
                    cita.getNombreMascota(), // Asume que Cita tiene un método para obtener el nombre de la mascota.
                    cita.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    cita.getTitulo(),
                };
                modeloTablaCitas.addRow(fila);
            }
        }
    }
    private void personalizarBotonAñadirCita(JButton btn) {
        // Personalización del efecto rollover del botón.
        btn.setRolloverEnabled(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.decode("#003366")); // Color más oscuro cuando el ratón está encima.
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE); // Volver al color original cuando el ratón se aleja.
                btn.setForeground(Color.decode("#0057FF"));
            }
        });

        btn.addActionListener(e -> abrirDialogoAñadirCita());
    }

    private void abrirDialogoAñadirCita() {
        // Suponiendo que VentanaCitasDialog es el diálogo para añadir/modificar citas.
        VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
        dialog.setTitle("Añadir Cita");
        dialog.setLocationRelativeTo(null);
        
        // Ajuste para actualizar la lista de citas cuando el diálogo se cierra.
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaCitas(); // Llama al método para actualizar la lista de citas.
            }
        });
        
        dialog.setVisible(true);
    }
    
    
    private void abrirPanelInfoMascota(int idMascota) {
    // Muestra una ventana con información detallada de la mascota seleccionada.
       
        PanelInfoMascota panelInfoMascota = new PanelInfoMascota(idMascota);
        JFrame frameInfoMascota = new JFrame("Información de la Mascota");
        frameInfoMascota.getContentPane().add(panelInfoMascota);
        frameInfoMascota.pack();
        frameInfoMascota.setLocationRelativeTo(null);
        frameInfoMascota.setVisible(true);
        frameInfoMascota.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void abrirPanelRegistroMascota() {
     // Abre una ventana para registrar una nueva mascota y actualiza la lista tras cerrarla

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
    // Refresca la tabla de mascotas con los datos actuales del cliente.
        if (tablaMascotas == null) {
            return; // Si tablaMascotas no se ha inicializado, sale del método.
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