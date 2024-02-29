package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import model.Cita;

public class PanelCalendario extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private List<Cita> citas; // Asumimos que esta lista se llena con las citas recuperadas de tu base de datos

    public PanelCalendario() {
        setLayout(null);
        inicializarComponentes();
        fillModelWithTimes(); // Llenar el modelo con las horas del día al inicializar
        cargarCitas(); // Si tienes un método para cargar citas desde la base de datos
    }

    private void inicializarComponentes() {
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(10, 11, 1092, 85);
        add(panelHeader);
        panelHeader.setLayout(null);

        JPanel panelCalendario = new JPanel();
        panelCalendario.setBounds(10, 107, 1092, 533);
        add(panelCalendario);
        panelCalendario.setLayout(new BorderLayout());

        String[] columns = calculateWeekDaysWithDates();
        model = new DefaultTableModel(null, columns);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no deben ser editables
            }
        };
        table.setRowHeight(30);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultRenderer(Object.class, new CitaTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        panelCalendario.add(scrollPane, BorderLayout.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(150);
        }
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!value.toString().isEmpty()) { // Suponiendo que las celdas con citas tienen texto
                    setBackground(Color.YELLOW); // Color de fondo para las citas
                    setForeground(Color.BLACK); // Color del texto
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });

    }

    private String[] calculateWeekDaysWithDates() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM");
        String[] weekDaysWithDates = new String[8];
        weekDaysWithDates[0] = "Hora";
        for (int i = 1; i <= 7; i++) {
            weekDaysWithDates[i] = monday.plusDays(i - 1).format(formatter);
        }
        return weekDaysWithDates;
    }
    
    private void fillModelWithTimes() {
        // Asegúrate de que el modelo de la tabla esté inicializado antes de llamar a este método
        for (int hour = 8; hour < 24; hour++) { // Comienza desde las 8 de la mañana
            String horaCompleta = String.format("%02d:00", hour);
            String mediaHora = String.format("%02d:30", hour);
            model.addRow(new Object[]{horaCompleta, "", "", "", "", "", "", ""});
            if (hour < 24) { // Asegura que después de las 23:00 solo se agregue la hora completa
                model.addRow(new Object[]{mediaHora, "", "", "", "", "", "", ""});
            }
        }
    }
    


    private void cargarCitas() {
        // Aquí debes cargar tus citas de la base de datos a la lista `citas`
        // Por ejemplo: citas = citaDAO.recuperarCitas();
        actualizarVistaCitas();
    }

    private void actualizarVistaCitas() {
        // Aquí deberías recorrer tus citas y añadirlas al modelo en la fecha y hora correspondiente
        // Ten en cuenta que necesitarás convertir la fecha y hora de la cita en índices de fila y columna en el modelo
    }

    class CitaTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Aquí puedes decidir el color basándote en el contenido de `value`, que sería el título de la cita
            if (value instanceof String && !((String) value).isEmpty()) {
                c.setBackground(Color.YELLOW); // Un ejemplo, ajusta según la lógica que necesites
            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
        }
    }
}
