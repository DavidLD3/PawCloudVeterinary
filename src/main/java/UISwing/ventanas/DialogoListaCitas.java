package UISwing.ventanas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Cita;
import DB.CitaDAO;

public class DialogoListaCitas extends JDialog implements CitaActualizadaListener{
    private JTable table;
    private DefaultTableModel model;
    private CitaDAO citaDAO;
    private CitaActualizadaListener citaActualizadaListener;

    public DialogoListaCitas(Frame owner) {
        super(owner, "Listado de Citas", true);
        setSize(1024, 600);
        setLocationRelativeTo(owner);

        citaDAO = new CitaDAO();
        initUI();
        cargarCitas();
        try {
            Image img = ImageIO.read(getClass().getResource("/imagenes/LogoOscuroPawCloud.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    class PaddingTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (component instanceof JLabel) {
                ((JLabel) component).setBorder(new EmptyBorder(0, 15, 0, 15));
            }
            return component;
        }
    }

    private void initUI() {
        String[] columnNames = {"Id","Titulo", "Fecha", "Hora", "Cliente", "Mascota", "Notas"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    if (colIndex == this.getColumn("Notas").getModelIndex()) {
                        tip = getValueAt(rowIndex, colIndex).toString();
                    } else {
                        tip = super.getToolTipText(e);
                    }
                } catch (RuntimeException ex) {
                }

                return tip;
            }
        };
      
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        int idCita = (Integer) model.getValueAt(row, 0);
                        abrirVentanaModificarCita(idCita);
                    }
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 4));
        table.setBackground(Color.decode("#5694F9"));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(table.getRowHeight() + 10);
        table.getTableHeader().setBackground(Color.decode("#0483FF")); 
        table.getTableHeader().setForeground(Color.WHITE); 
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        PaddingTableCellRenderer cellRenderer = new PaddingTableCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    
    
    private void abrirVentanaModificarCita(int idCita) {
        CitaDAO citaDAO = new CitaDAO();
        Cita cita = citaDAO.obtenerCitaPorId(idCita);
        if (cita != null) {
            VentanaModificarCitaDialog ventanaModificar = new VentanaModificarCitaDialog(null, true, cita);
            ventanaModificar.addCitaActualizadaListener(citaActualizadaListener);
            ventanaModificar.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void cargarCitas() {
        List<Cita> citas = citaDAO.recuperarCitas();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 

        model.setRowCount(0);
        for (Cita cita : citas) { //formatea la fecha y la hora
            String fechaFormateada = cita.getFecha().format(dateFormatter);
            String horaFormateada = cita.getHora().format(timeFormatter);

            model.addRow(new Object[]{
                cita.getId(),
                cita.getTitulo(), fechaFormateada, horaFormateada, cita.getNombreCliente(), cita.getNombreMascota(), cita.getNotas()
            });
        }
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
    }

    public void setCitaActualizadaListener(CitaActualizadaListener listener) {
        this.citaActualizadaListener = listener;
    }
    @Override
    public void onCitaActualizada() {
        cargarCitas(); 
    }
    
    @Override
    public void dispose() {
        if (citaActualizadaListener != null) {
            citaActualizadaListener.onCitaActualizada();
        }
        super.dispose();
    }


}
