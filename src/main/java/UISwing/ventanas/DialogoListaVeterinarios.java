package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import DB.VeterinarioDAO;
import model.Veterinario;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DialogoListaVeterinarios extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private VeterinarioDAO veterinarioDAO;
    private List<Integer> veterinarioIds;

    public DialogoListaVeterinarios(Frame owner) {
        super(owner, "Listado de Veterinarios", true);
        setSize(1024, 600);
        setLocationRelativeTo(owner);
        veterinarioDAO = new VeterinarioDAO();
        veterinarioIds = new ArrayList<>();
        initUI();
        cargarVeterinarios();
    }

    private void initUI() {
        String[] columnNames = {"Nombre", "Apellidos", "Licencia", "Teléfono", "Email", "Especialidades", "Horario de Trabajo", "Fecha de Contratación"};
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
                    int id = veterinarioIds.get(modelRow);
                    mostrarDialogoDetalleVeterinario(id);
                }
            }
        });
    }

    private void cargarVeterinarios() {
        new SwingWorker<List<Veterinario>, Void>() {
            @Override
            protected List<Veterinario> doInBackground() throws Exception {
                return veterinarioDAO.obtenerTodosLosVeterinarios();
            }

            @Override
            protected void done() {
                try {
                    List<Veterinario> veterinarios = get();
                    veterinarioIds.clear();
                    model.setRowCount(0);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    for (Veterinario veterinario : veterinarios) {
                        veterinarioIds.add(veterinario.getId());
                        String fechaContratacion = veterinario.getFechaContratacion() != null ? formatter.format(veterinario.getFechaContratacion()) : "N/A";
                        model.addRow(new Object[]{
                            veterinario.getNombre(), veterinario.getApellidos(), veterinario.getLicencia(),
                            veterinario.getTelefono(), veterinario.getEmail(), veterinario.getEspecialidades(),
                            veterinario.getHorarioTrabajo(), fechaContratacion
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar los datos de veterinarios.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private void mostrarDialogoDetalleVeterinario(int veterinarioId) {
        new SwingWorker<Veterinario, Void>() {
            @Override
            protected Veterinario doInBackground() throws Exception {
                return veterinarioDAO.obtenerVeterinarioPorId(veterinarioId);
            }

            @Override
            protected void done() {
                try {
                    Veterinario veterinario = get();
                    if (veterinario != null) {
                        Frame frame = JOptionPane.getFrameForComponent(DialogoListaVeterinarios.this);
                        VentanaModificarVeterinarioDialog dialogoDetalle = new VentanaModificarVeterinarioDialog(frame, true, veterinario);
                        dialogoDetalle.setVisible(true);
                        cargarVeterinarios();
                    } else {
                        JOptionPane.showMessageDialog(DialogoListaVeterinarios.this,
                                                      "No se encontró el veterinario solicitado.",
                                                      "Veterinario no encontrado",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(DialogoListaVeterinarios.this,
                                                  "Error al recuperar los detalles del veterinario: " + e.getMessage(),
                                                  "Error de Búsqueda",
                                                  JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DialogoListaVeterinarios(null).setVisible(true));
    }
}
