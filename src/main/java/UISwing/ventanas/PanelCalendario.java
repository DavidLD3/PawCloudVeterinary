package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.CitaDAO;
import model.Cita;

public class PanelCalendario extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocalDate fechaInicioSemana;
    private JLabel lblCurrentDate;
    private CitaDAO citaDAO = new CitaDAO();
    private Map<Point, Integer> posicionACitaId = new HashMap<>();
    private List<CitaActualizadaListener> listeners = new ArrayList<>();

    public PanelCalendario() {
    	setPreferredSize(new Dimension(1112, 640));
        fechaInicioSemana = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        setLayout(null);
        setOpaque(false); 
        inicializarModeloYTabla();
        inicializarComponentes();
        cargarCitas();
    }
    private void inicializarModeloYTabla() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setDefaultRenderer(Object.class, new CitaTableCellRenderer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = table.rowAtPoint(e.getPoint());
                int columna = table.columnAtPoint(e.getPoint());

                LocalDate fechaSeleccionada = fechaInicioSemana.plusDays(columna - 1);
                LocalTime horaSeleccionada = LocalTime.of(8 + (fila / 4), (fila % 4) * 15);

                if (columna > 0) {
                    Point clave = new Point(fila, columna);
                    if (posicionACitaId.containsKey(clave)) {
                        int idCita = posicionACitaId.get(clave);
                        Cita cita = citaDAO.obtenerCitaPorId(idCita);
                        if (cita != null) {
                            VentanaModificarCitaDialog ventanaModificar = new VentanaModificarCitaDialog(null, true, cita);
                            ventanaModificar.addCitaActualizadaListener(() -> {
                                cargarCitas();
                                notificarCitaActualizada();
                            });
                            ventanaModificar.setVisible(true);
                        }
                    } else {
                        java.util.Date fecha = java.util.Date.from(fechaSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        java.util.Date hora = java.util.Date.from(LocalDateTime.of(fechaSeleccionada, horaSeleccionada).atZone(ZoneId.systemDefault()).toInstant());
                        
                        VentanaCitasDialog nuevaCitaDialog = new VentanaCitasDialog(null, true);
                        nuevaCitaDialog.setFecha(fecha); 
                        nuevaCitaDialog.setHora(hora); 
                        nuevaCitaDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                cargarCitas();
                                notificarCitaActualizada();
                            }
                        });
                        nuevaCitaDialog.setVisible(true);
                    }
                }
            }
        });


    }



    private void inicializarComponentes() {
        JPanel panelHeader = crearPanelHeader();
        panelHeader.setBounds(0, 0, 1112, 50);
        add(panelHeader);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 50, 1112, 590);
        add(scrollPane);
        actualizarModeloTabla();
    }

    private JPanel crearPanelHeader() {
        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        centerPanel.setOpaque(false);

        JButton prevWeekButton = new JButton("<");
        styleButton(prevWeekButton, "#0057FF", "#003366");
        centerPanel.add(prevWeekButton);

        lblCurrentDate = new JLabel("Fecha Actual", SwingConstants.CENTER);
        lblCurrentDate.setFont(new Font("Tahoma", Font.BOLD, 12));
        centerPanel.add(lblCurrentDate);

        JButton nextWeekButton = new JButton(">");
        styleButton(nextWeekButton, "#0057FF", "#003366");
        centerPanel.add(nextWeekButton);

        gbc.gridx = 1;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.CENTER;
        panelHeader.add(centerPanel, gbc);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        legendPanel.setOpaque(false);
        addColorLegend(legendPanel, new Color(0, 153, 0), "Visita");
        addColorLegend(legendPanel, Color.BLUE, "Consulta");
        addColorLegend(legendPanel, Color.RED, "Urgencia");
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        panelHeader.add(legendPanel, gbc);

        return panelHeader;
    }


    private void styleButton(JButton button, String textColor, String hoverColor) {
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setForeground(Color.decode(textColor));
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode(hoverColor));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.decode(textColor));
            }
        });
        button.addActionListener(e -> navegar(button.getText().equals(">") ? 1 : -1));
    }

    private void addColorLegend(JPanel panel, Color color, String text) {
        JLabel label = new JLabel(text, new ColorIcon(color), JLabel.LEFT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(label);
    }

    class ColorIcon implements Icon {
        private final int ICON_SIZE = 10;
        private Color color;

        public ColorIcon(Color color) {
            this.color = color;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(this.color);
            g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
        }

        public int getIconWidth() {
            return ICON_SIZE;
        }

        public int getIconHeight() {
            return ICON_SIZE;
        }
    }


    private void navegar(int semanas) {
        fechaInicioSemana = fechaInicioSemana.plusWeeks(semanas);
        actualizarModeloTabla();
        cargarCitas();
    }

    private void actualizarFechaActual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        lblCurrentDate.setText(String.format("Semana del %s al %s",
                fechaInicioSemana.format(formatter),
                fechaInicioSemana.plusDays(6).format(formatter)));
    }

    private void cargarCitas() {
        List<Cita> citas = citaDAO.recuperarCitasPorSemana(fechaInicioSemana, fechaInicioSemana.plusDays(6));
        actualizarTablaConCitas(citas);
    }

    private void actualizarModeloTabla() {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM");
        LocalDate diaDeLaSemana = fechaInicioSemana;
        String[] columnNames = new String[8];
        columnNames[0] = "Hora";
        for (int i = 1; i <= 7; i++) {
            columnNames[i] = diaDeLaSemana.format(formatter);
            diaDeLaSemana = diaDeLaSemana.plusDays(1);
        }
        model.setColumnIdentifiers(columnNames);
        actualizarTablaConHoras();
        actualizarFechaActual();
    }

    private void actualizarTablaConHoras() {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 8; i <= 22; i++) {
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 0)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 15)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 30)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 45)), "", "", "", "", "", "", ""});
        }
    }




    private void actualizarTablaConCitas(List<Cita> citas) {
        posicionACitaId.clear(); 
        for (int i = 1; i < model.getColumnCount(); i++) {
            for (int j = 0; j < model.getRowCount(); j++) {
                model.setValueAt("", j, i);
            }
        }

        citas.forEach(cita -> {
            LocalDate fechaCita = cita.getFecha();
            LocalTime horaCita = cita.getHora();
            int columna = fechaCita.getDayOfWeek().getValue();
            int fila = ((horaCita.getHour() - 8) * 4) + (horaCita.getMinute() / 15);
            if (fila < model.getRowCount() && columna < model.getColumnCount()) {
                String infoCita = cita.getNombreMascota() + " - " + cita.getTipo();
                model.setValueAt(infoCita, fila, columna);
                posicionACitaId.put(new Point(fila, columna), cita.getId());
            } else {
                System.out.println("Índice fuera de límites intentado: Fila " + fila + ", Columna " + columna);
            }
        });
    }


    
    public void addCitaActualizadaListener(CitaActualizadaListener listener) {
        listeners.add(listener);
    }

    private void notificarCitaActualizada() {
        for (CitaActualizadaListener listener : listeners) {
            listener.onCitaActualizada();
        }
    }



    class CitaTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                String[] parts = value.toString().split(" - ");
                if (parts.length > 1) {
                    String tipo = parts[1];
                    switch (tipo) {
                        case "Visita":
                            setBackground(new Color(0, 153, 0));
                            setForeground(Color.WHITE);
                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                            break;
                        case "Consulta":
                            setBackground(Color.BLUE);
                            setForeground(Color.WHITE);
                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                            break;
                        case "Urgencia":
                            setBackground(Color.RED);
                            setForeground(Color.WHITE);
                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                            break;
                        default:
                            setBackground(Color.LIGHT_GRAY);
                            setForeground(Color.BLACK);
                            setFont(new Font("Segoe UI", Font.PLAIN, 12));
                            break;
                    }
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }
            return this;
        }
    }
    
    


}
