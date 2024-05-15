package UISwing.ventanas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import DB.FarmacoDAO;
import model.UsoFarmaco;

public class DialogoListaUsosFarmacos extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private FarmacoDAO farmacoDAO;

    public DialogoListaUsosFarmacos(Frame owner) {
        super(owner, "Listado de Últimos Usos de Fármacos", true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        farmacoDAO = new FarmacoDAO();
        initUI();
        cargarUsosFarmacos();
        
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/LogoOscuroPawCloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        String[] columnNames = {"ID Uso", "Nombre Fármaco", "Cantidad Usada", "Fecha de Uso", "Frecuencia", "Mascota"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setBackground(Color.decode("#577BD1"));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(Color.decode("#0483FF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }


    private void cargarUsosFarmacos() {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        java.util.List<UsoFarmaco> usos = farmacoDAO.obtenerUltimosUsosFarmacos();
        for (UsoFarmaco uso : usos) {
            String fechaFormateada = uso.getFechaHoraUso().format(formatter);
            model.addRow(new Object[]{
                uso.getIdFarmaco(),
                uso.getNombreFarmaco(),
                uso.getCantidadUsada(),
                fechaFormateada,
                uso.getFrecuencia(),
                uso.getNombreMascota()
            });
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DialogoListaUsosFarmacos dialog = new DialogoListaUsosFarmacos(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
