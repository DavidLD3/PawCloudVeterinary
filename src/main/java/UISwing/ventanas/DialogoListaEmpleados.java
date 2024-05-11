package UISwing.ventanas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.EmpleadoDAO;
import model.Empleado;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/LogoOscuroPawCloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        table.setBackground(Color.decode("#96B8F6"));
        table.setForeground(Color.decode("#1B2582"));
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(table.getRowHeight() + 10);
        table.getTableHeader().setBackground(Color.decode("#0483FF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                    int id = empleadoIds.get(modelRow);
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
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    empleadoIds.clear();
                    model.setRowCount(0);
                    for (Empleado empleado : empleados) {
                        String fechaContratacion = empleado.getFechaContratacion() != null ? formatter.format(empleado.getFechaContratacion()) : "N/A";
                        empleadoIds.add(empleado.getId());
                        model.addRow(new Object[]{
                            empleado.getNombre(), empleado.getApellidos(), empleado.getDni(),
                            empleado.getTelefono(), empleado.getEmail(),
                            empleado.getHorarioTrabajo(), fechaContratacion
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar los datos de empleados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }




    private void mostrarDialogoDetalleEmpleado(int empleadoId) {
        new SwingWorker<Empleado, Void>() {
            @Override
            protected Empleado doInBackground() throws Exception {
                return empleadoDAO.obtenerEmpleadoPorId(empleadoId);
            }

            @Override
            protected void done() {
                try {
                    Empleado empleado = get();
                    if (empleado != null) {
                        Frame frame = JOptionPane.getFrameForComponent(DialogoListaEmpleados.this);
                        VentanaModificarEmpleadoDialog dialogoDetalle = new VentanaModificarEmpleadoDialog(frame, true, empleado);
                        dialogoDetalle.setVisible(true);
                        cargarEmpleados();
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
        }.execute();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DialogoListaEmpleados(null).setVisible(true));
    }
}
