package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class PanelCalendario extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public PanelCalendario() {
        setLayout(null);

        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(10, 11, 1092, 85);
        add(panelHeader);
        panelHeader.setLayout(null);

        JPanel panelCalendario = new JPanel();
        panelCalendario.setBounds(10, 107, 1092, 533);
        add(panelCalendario);
        panelCalendario.setLayout(new BorderLayout());
        
        // Calcula las fechas de la semana actual
        String[] columns = calculateWeekDaysWithDates();
        // Crear el modelo de tabla con las horas y medias horas
        model = new DefaultTableModel(null, columns);
        // Llenar el modelo con las horas del día
        fillModelWithTimes(model);
        
        // Crear la tabla
        table = new JTable(model);
        table.setRowHeight(30); // Ajusta la altura de las filas para que haya espacio para la media hora
        // Configurar la selección de celdas
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                // Acciones cuando se selecciona una celda
                // ... (tu código aquí)
            }
        });

        // Agregar la tabla a un JScrollPane y luego al panelCalendario
        JScrollPane scrollPane = new JScrollPane(table);
        panelCalendario.add(scrollPane, BorderLayout.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // Ajustar el ancho de la columna de horas
        int preferredWidthForDays = 150; // O cualquier valor que se ajuste a tu diseño
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(preferredWidthForDays);
        }
    }

    // Llenar el modelo con las horas y medias horas
    private void fillModelWithTimes(DefaultTableModel model) {
        for (int hour = 0; hour < 24; hour++) {
            model.addRow(new Object[]{String.format("%02d:00", hour), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{String.format("%02d:30", hour), "", "", "", "", "", "", ""});
        }
    }

    // Calcula los días de la semana con las fechas para las cabeceras de columna
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
}
