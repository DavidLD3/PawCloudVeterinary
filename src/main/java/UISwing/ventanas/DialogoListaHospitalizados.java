package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Hospitalizacion;
import DB.HospitalizacionDAO;

public class DialogoListaHospitalizados extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private HospitalizacionDAO HospitalizacionDAO;

    public DialogoListaHospitalizados(Frame owner) {
        super(owner, "Listado de Hospitalizados", true);
        setSize(800, 400);
        setLocationRelativeTo(owner);

        HospitalizacionDAO = new HospitalizacionDAO();
        initUI();
        cargarHospitalizados();
    }

    private void initUI() {
        String[] columnNames = {"Mascota", "Fecha ingreso", "Fecha salida", "Motivo", "Tratamiento", "Estado", "Notas"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que ninguna celda sea editable directamente en la tabla
                return false;
            }
        };
        table = new JTable(model) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    // Usa el valor de la celda como tooltip si la celda corresponde a las columnas especificadas
                    if (colIndex == this.getColumn("Notas").getModelIndex() ||
                        colIndex == this.getColumn("Fecha ingreso").getModelIndex() ||
                        colIndex == this.getColumn("Fecha salida").getModelIndex() ||
                        colIndex == this.getColumn("Motivo").getModelIndex() ||
                        colIndex == this.getColumn("Tratamiento").getModelIndex()) {
                        tip = getValueAt(rowIndex, colIndex).toString();
                    } else {
                        // Si no, usa el tooltip predeterminado
                        tip = super.getToolTipText(e);
                    }
                } catch (RuntimeException ex) {
                    // En caso de una excepción, por ejemplo, si rowIndex o colIndex no son válidos, no establece un tooltip
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
    

    private void cargarHospitalizados() {
        List<Hospitalizacion> hospitalizaciones = HospitalizacionDAO.recuperarTodasLasHospitalizaciones();
        for (Hospitalizacion hospitalizacion : hospitalizaciones) {
            model.addRow(new Object[]{
                hospitalizacion.getNombreMascota(),
                hospitalizacion.getFechaIngreso(),
                hospitalizacion.getFechaSalida(),
                hospitalizacion.getMotivo(),
                hospitalizacion.getTratamiento(),
                hospitalizacion.getEstado(),
                hospitalizacion.getNotas()
            });
        }
    }


}
