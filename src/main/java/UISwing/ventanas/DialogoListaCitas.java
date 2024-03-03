package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Cita;
import DB.CitaDAO;

public class DialogoListaCitas extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private CitaDAO citaDAO;

    public DialogoListaCitas(Frame owner) {
        super(owner, "Listado de Citas", true);
        setSize(800, 400);
        setLocationRelativeTo(owner);

        citaDAO = new CitaDAO();
        initUI();
        cargarCitas();
    }

    private void initUI() {
        String[] columnNames = {"Titulo", "Fecha", "Hora", "Cliente", "Mascota", "Notas"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que ninguna celda sea editable directamente en la tabla
                return false;
            }
        };
        table = new JTable(model) {
            // Sobrescribe el método para mostrar tooltips personalizados
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    // Verifica si el índice de columna es el de las notas
                    if (colIndex == this.getColumn("Notas").getModelIndex()) {
                        tip = getValueAt(rowIndex, colIndex).toString();
                    } else {
                        // Usa el tooltip predeterminado para otras columnas si es necesario
                        tip = super.getToolTipText(e);
                    }
                } catch (RuntimeException ex) {
                    // En caso de una excepción, no establece un tooltip
                }

                return tip;
            }
        };
        table.setFillsViewportHeight(true);

        // Aplicar color de fondo al panel y color de texto a las celdas de la tabla
        table.setBackground(Color.decode("#7E88E2"));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(Color.decode("#7E88E2"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    

    private void cargarCitas() {
        List<Cita> citas = citaDAO.recuperarCitas(); // Asegúrate de que este método recupere las citas ordenadas por fecha y hora
        for (Cita cita : citas) {
            model.addRow(new Object[]{cita.getTitulo(), cita.getFecha(), cita.getHora(), cita.getNombreCliente(), cita.getNombreMascota(), cita.getNotas()});
        }
    }
}
