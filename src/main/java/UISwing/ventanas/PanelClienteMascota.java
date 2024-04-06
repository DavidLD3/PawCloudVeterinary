package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import DB.ClienteDAO;
import model.Cliente;
import DB.MascotaDAO;
import model.Mascota;

public class PanelClienteMascota extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtBuscarClientemascota;
    private JTextField txtBuscarCliente;
    private JTable tablaClientes;  // JTable para clientes
    private JTable tablaMascotas;  // JTable para mascotas, asumiendo que lo usarás
    private DefaultTableModel modeloTablaClientes;
    private ClienteDAO clienteDao;
    private DefaultTableModel modeloTablaMascotas; // Nuevo modelo para mascotas
    private MascotaDAO mascotaDAO;
    /**
     * Create the panel.
     */
    public PanelClienteMascota() {
        setLayout(null);

        clienteDao = new ClienteDAO(); // Asume que tienes esta clase para la gestión de la base de datos
        mascotaDAO = new MascotaDAO();
        
        JPanel panel = new JPanel();
        panel.setBounds(0, -11, 1112, 664);
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

        inicializarComponentesClientes(panel);
        inicializarComponentesMascotas(panel); // Agregar inicialización de componentes de mascotas
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(569, 105, 518, 537);
        panel.add(scrollPane);
    }

    private void inicializarComponentesClientes(JPanel panel) {
        // Configura el modelo de la tabla sin mostrar el ID
        modeloTablaClientes = new DefaultTableModel(new Object[]{"Nombre", "Apellidos", "DNI"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que la tabla no sea editable
            }
        };

        tablaClientes = new JTable(modeloTablaClientes);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Doble clic
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String dni = (String) modeloTablaClientes.getValueAt(filaSeleccionada, 2);  // Asumiendo que el DNI está en la columna 2
                        abrirPanelDetalleClientePorDni(dni);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBounds(10, 105, 549, 537); // Ajusta las dimensiones según necesites
        panel.add(scrollPane);

        cargarDatosClientes(); // Carga los datos de los clientes después de inicializar la tabla
    }
    
    private void inicializarComponentesMascotas(JPanel panel) {
        // Configura el modelo de la tabla para mascotas
        modeloTablaMascotas = new DefaultTableModel(new Object[]{"Nombre", "Especie", "Microchip"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que la tabla no sea editable
            }
        };

        tablaMascotas = new JTable(modeloTablaMascotas);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPaneMascotas = new JScrollPane(tablaMascotas);
        scrollPaneMascotas.setBounds(569, 105, 518, 537); // Ajusta las dimensiones según necesites
        panel.add(scrollPaneMascotas);

        cargarDatosMascotas(); // Carga los datos de las mascotas en la tabla
    }

    private void cargarDatosClientes() {
        List<Cliente> listaClientes = clienteDao.obtenerTodosLosClientes(); // Obtiene los datos desde la base de datos a través del DAO
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        for (Cliente cliente : listaClientes) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getDni()
            });
        }
    }
    
    private void cargarDatosMascotas() {
        try {
            List<Mascota> listaMascotas = mascotaDAO.obtenerMascotasOrdenadasPorNombreEspecieMicrochip();

            DefaultTableModel modelo = (DefaultTableModel) tablaMascotas.getModel();
            modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            for (Mascota mascota : listaMascotas) {
                modelo.addRow(new Object[]{
                        mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getMicrochip()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja la excepción de SQL
            // Aquí podrías mostrar un mensaje de error al usuario si la carga de datos falla.
        }
    }

    private void abrirPanelDetalleClientePorDni(String dni) {
        PanelCliente panelCliente = new PanelCliente(dni);
        JFrame frame = new JFrame("Detalles del Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panelCliente);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Cliente Mascota");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelClienteMascota panel = new PanelClienteMascota();
            frame.getContentPane().add(panel);
            frame.setSize(800, 600); // Ajusta el tamaño según necesites
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}