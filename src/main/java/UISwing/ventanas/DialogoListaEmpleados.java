package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.EmpleadoDAO;
import model.Empleado;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DialogoListaEmpleados extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private EmpleadoDAO empleadoDAO;
    private List<Integer> empleadoIds;

    public DialogoListaEmpleados(Frame owner) {
        super(owner, "Listado de Empleados", true);
        setSize(1024, 600);
        setLocationRelativeTo(owner);
        empleadoDAO = new EmpleadoDAO();
        empleadoIds = new ArrayList<>();
        initUI();
        cargarEmpleados();
    }

    private void initUI() {
        String[] columnNames = {"Nombre", "Apellidos", "DNI", "Teléfono", "Email", "Horario de Trabajo", "Fecha de Contratación"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        configuraTabla();
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void configuraTabla() {
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 4));
        table.setBackground(Color.decode("#5694F9"));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(table.getRowHeight() + 10);
        table.getTableHeader().setBackground(Color.decode("#0483FF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                    int id = empleadoIds.get(modelRow); // Obtener el ID utilizando la lista de IDs
                    mostrarDialogoDetalleEmpleado(id);
                }
            }
        });
    }

    private void cargarEmpleados() {
        new SwingWorker<List<Empleado>, Void>() {
            @Override
            protected List<Empleado> doInBackground() throws Exception {
                return empleadoDAO.obtenerTodosLosEmpleados();
            }

            @Override
            protected void done() {
                try {
                    List<Empleado> empleados = get();
                    empleadoIds.clear(); // Limpiar la lista de IDs
                    model.setRowCount(0); // Limpiar el modelo de la tabla
                    for (Empleado empleado : empleados) {
                        empleadoIds.add(empleado.getId());
                        model.addRow(new Object[]{
                            empleado.getNombre(), empleado.getApellidos(), empleado.getDni(),
                            empleado.getTelefono(), empleado.getEmail(),
                            empleado.getHorarioTrabajo(), empleado.getFechaContratacion() != null ? empleado.getFechaContratacion().toString() : "N/A"
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar los datos de empleados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private void mostrarDialogoDetalleEmpleado(int empleadoId) {
        // SwingWorker para realizar la operación de base de datos en un hilo separado
        new SwingWorker<Empleado, Void>() {
            @Override
            protected Empleado doInBackground() throws Exception {
                // Realiza la consulta en un hilo de fondo
                return empleadoDAO.obtenerEmpleadoPorId(empleadoId);
            }

            @Override
            protected void done() {
                try {
                    // Una vez que el empleado está cargado, se accede en el hilo de la interfaz de usuario
                    Empleado empleado = get();
                    if (empleado != null) {
                        Frame frame = JOptionPane.getFrameForComponent(DialogoListaEmpleados.this);
                        VentanaModificarEmpleadoDialog dialogoDetalle = new VentanaModificarEmpleadoDialog(frame, true, empleado);
                        dialogoDetalle.setVisible(true);
                        cargarEmpleados(); // Recargar la lista de empleados para reflejar posibles cambios
                    } else {
                        JOptionPane.showMessageDialog(DialogoListaEmpleados.this,
                                                      "No se encontró el empleado solicitado.",
                                                      "Empleado no encontrado",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(DialogoListaEmpleados.this,
                                                  "Error al recuperar los detalles del empleado: " + e.getMessage(),
                                                  "Error de Búsqueda",
                                                  JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }.execute(); // Inicia el SwingWorker
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DialogoListaEmpleados(null).setVisible(true));
    }
}
