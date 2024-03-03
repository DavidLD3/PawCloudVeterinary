package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import com.toedter.calendar.JCalendar;
import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import model.Cita;
import model.Cliente;
import model.Mascota;
import UISwing.recursos.RoundedPanel;
import java.util.List;


public class VentanaCitasDialog extends JDialog {

    private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JTextField textFieldTituloVisita;
    private JTextPane textPaneNotas;
    
    private JComboBox<Cliente> comboBoxClientes;
    private JComboBox<Mascota> comboBoxMascotas;
    
    private boolean cargaInicialCompleta = false;
    private RoundedPanel roundedPanel;

    public VentanaCitasDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("Programar Cita");
        setUndecorated(true); // Esto elimina la decoración de la ventana
        setSize(new Dimension(888, 399));
        
        setLocationRelativeTo(null);
        
        
        
        roundedPanel = new RoundedPanel(30, Color.decode("#7E88E2"));
        roundedPanel.setLayout(null);
        roundedPanel.setBounds(0, 0, 888, 399); // Ajustar al tamaño del JDialog
        roundedPanel.setOpaque(false); // Hacer el fondo transparente
        setBackground(new java.awt.Color(0, 0, 0, 0));
        clienteDAO = new ClienteDAO();
        mascotaDAO = new MascotaDAO();
        
        inicializarComponentesUI();
        cargarClientesInicialmente();
        configurarSeleccionFechaHora();
        
        // Agrega el RoundedPanel al JDialog
        getContentPane().add(roundedPanel);
	}
	
	private void inicializarComponentesUI() {
    	
		JLabel lblTipo = new JLabel("Tipo:");
	    lblTipo.setForeground(new Color(255, 255, 255));
	    lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    lblTipo.setBounds(62, 50, 80, 25);
	    roundedPanel.add(lblTipo);
        
        JComboBox<String> comboBoxTipo = new JComboBox<>(new String[]{"Visita", "Consulta", "Urgencia"});
        comboBoxTipo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxTipo.setBounds(60, 74, 120, 25);
        roundedPanel.add(comboBoxTipo);
        
        JLabel lblDoctor = new JLabel("Doctor:");
        lblDoctor.setForeground(new Color(255, 255, 255));
        lblDoctor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDoctor.setBounds(236, 50, 80, 25);
        roundedPanel.add(lblDoctor);
        
        JComboBox<String> comboBoxDoctor = new JComboBox<>(new String[]{"Doctor 1", "Doctor 2", "Doctor 3"});
        comboBoxDoctor.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxDoctor.setBounds(234, 74, 120, 25);
        roundedPanel. add(comboBoxDoctor);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setBounds(420, 50, 80, 25);
        roundedPanel.add(lblCliente);
        
     
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setBounds(663, 50, 80, 25);
        roundedPanel.add(lblMascota);
        
     // Dentro de tu método inicializarComponentesUI
        comboBoxClientes = new JComboBox<Cliente>();
        comboBoxClientes.setEditable(true);
        comboBoxClientes.setBounds(420, 74, 179, 25);
        JTextField textEditorClientes = (JTextField) comboBoxClientes.getEditor().getEditorComponent();
        textEditorClientes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textEditorClientes.getText();
                if (text.trim().length() >= 4) {
                   
                    List<Cliente> filtrado = clienteDAO.buscarClientesPorApellido(text); 
                    comboBoxClientes.removeAllItems();
                    for (Cliente cliente : filtrado) {
                        comboBoxClientes.addItem(cliente);
                    }
                    if (!filtrado.isEmpty()) {
                        comboBoxClientes.showPopup();
                    }
                } else if (text.trim().isEmpty()) {
                    comboBoxClientes.hidePopup();
                    comboBoxClientes.removeAllItems();
                }
            }
        });
        comboBoxClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getApellidos() + ", " + cliente.getNombre()); // Formato apellidos, nombre
                }
                return this;
            }
        });
        roundedPanel.add(comboBoxClientes);
   

        comboBoxMascotas = new JComboBox<>();
        comboBoxMascotas.setEditable(true);
        comboBoxMascotas.setBounds(660, 74, 179, 25);
        roundedPanel.add(comboBoxMascotas);
        

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(new Color(255, 255, 255));
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFecha.setBounds(420, 132, 44, 25);
        roundedPanel.add(lblFecha);

       

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(new Color(255, 255, 255));
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHora.setBounds(663, 132, 80, 25);
        roundedPanel.add(lblHora);
        
        JLabel lblTituloVisita = new JLabel("Título de la visita:");
        lblTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloVisita.setForeground(new Color(255, 255, 255));
        lblTituloVisita.setBounds(58, 132, 150, 25);
        roundedPanel.add(lblTituloVisita);

        textFieldTituloVisita = new JTextField();
        textFieldTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldTituloVisita.setBounds(60, 158, 294, 42);
        roundedPanel.add(textFieldTituloVisita);
        
        JLabel lblNotas = new JLabel("Nota");
        lblNotas.setForeground(new Color(255, 255, 255));
        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotas.setBounds(62, 229, 46, 14);
        roundedPanel.add(lblNotas);
        
        textPaneNotas = new JTextPane();
        textPaneNotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textPaneNotas.setBounds(62, 254, 537, 73);
        roundedPanel.add(textPaneNotas);
        
        JButton btnGuardarCita = new JButton("Guardar Cita");
        btnGuardarCita.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGuardarCita.setBounds(663, 285, 176, 42);
        btnGuardarCita.setBackground(Color.WHITE);
        btnGuardarCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnGuardarCita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnGuardarCita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnGuardarCita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnGuardarCita.setOpaque(true);
        btnGuardarCita.setRolloverEnabled(true);
        btnGuardarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnGuardarCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnGuardarCita.setForeground(Color.decode("#0057FF"));
            }
        });
        btnGuardarCita.addActionListener(e -> guardarCita());
        roundedPanel.add(btnGuardarCita);
        
        JLabel lbllogocerrar = new JLabel("");
        lbllogocerrar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        lbllogocerrar.setBounds(852, 0, 26, 41);
        roundedPanel.add(lbllogocerrar);
        lbllogocerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dispose(); // para cerrar solo el dialog	
            }
        });
 
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Personaliza aquí tu componente
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Ajusta la opacidad aquí
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Puedes ajustar el radio de las esquinas si es necesario
                g2.dispose();
                super.paintComponent(g);
            }
        };
        centerPanel.setBackground(new Color(255, 255, 255, 80)); // Color de fondo con opacidad
        centerPanel.setOpaque(false); // Hace que el panel no pinte todos sus píxeles, lo que permite que se vea el fondo.
        centerPanel.setBounds(34, 38, 826, 320);
        roundedPanel.add(centerPanel);
 
    }
	
	private void configurarSeleccionFechaHora() {
	    dateSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
	    dateSpinner.setEditor(dateEditor);
	    dateSpinner.setBounds(420, 168, 153, 25);
	    roundedPanel.add(dateSpinner);

	    calendarButton = new JButton(new ImageIcon(getClass().getResource("/imagenes/logoBotonCalendario.png")));
	    calendarButton.setBounds(574, 168, 25, 25);
	    calendarButton.addActionListener(e -> mostrarCalendario());
	    roundedPanel.add(calendarButton);

	    timeSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
	    timeSpinner.setEditor(timeEditor);
	    timeSpinner.setBounds(660, 167, 179, 25);
	    roundedPanel.add(timeSpinner);
	}

	private void mostrarCalendario() {
	    JDialog dialog = new JDialog();
	    dialog.setModal(true); // Hacer el diálogo modal para mantener el foco
	    dialog.setTitle("Seleccionar Fecha");
	    dialog.setSize(300, 300);
	    dialog.setLocationRelativeTo(null); // Centrar respecto a la pantalla o usar dialog.setLocationRelativeTo(this) para centrar respecto a la ventana principal
	    JCalendar calendar = new JCalendar();
	    dialog.getContentPane().add(calendar, BorderLayout.CENTER);
	    
	    JButton okButton = new JButton("OK");
	    okButton.addActionListener(e -> {
	        dateSpinner.setValue(calendar.getDate());
	        dialog.dispose(); // Cierra el diálogo una vez seleccionada la fecha
	    });
	    dialog.getContentPane().add(okButton, BorderLayout.SOUTH);

	    dialog.setVisible(true);
	}
	


    
   
  
	private void actualizarMascotasPorCliente(int clienteId) {
        new SwingWorker<List<Mascota>, Void>() {
            @Override
            protected List<Mascota> doInBackground() throws Exception {
                return mascotaDAO.obtenerMascotasPorClienteId(clienteId);
            }

            @Override
            protected void done() {
                try {
                    List<Mascota> mascotas = get();
                    comboBoxMascotas.removeAllItems();
                    for (Mascota mascota : mascotas) {
                        comboBoxMascotas.addItem(mascota);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }


	private void cargarClientesInicialmente() {
	    new SwingWorker<List<Cliente>, Void>() {
	        @Override
	        protected List<Cliente> doInBackground() throws Exception {
	            return clienteDAO.obtenerTodosLosClientes();
	        }

	        @Override
	        protected void done() {
	            try {
	                List<Cliente> clientes = get();
	                comboBoxClientes.removeAllItems(); // Asegúrate de limpiar antes de añadir
	                for (Cliente cliente : clientes) {
	                	comboBoxClientes.setSelectedIndex(-1);
	                    comboBoxClientes.addItem(cliente);
	                }
	                cargaInicialCompleta = true; // Indica que la carga inicial ha terminado
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}

    
    

	private void actualizarListaClientes(String texto) {
	    // Verifica si la carga inicial aún no se ha completado para evitar ejecución innecesaria
	    if (!cargaInicialCompleta) return;

	    new SwingWorker<List<Cliente>, Void>() {
	        @Override
	        protected List<Cliente> doInBackground() throws Exception {
	            // Simula una búsqueda en base al texto ingresado por el usuario
	            return clienteDAO.buscarClientesPorNombre(texto);
	        }

	        @Override
	        protected void done() {
	            try {
	                // Obtiene el resultado de la búsqueda
	                List<Cliente> clientes = get();
	                // Limpia el JComboBox antes de añadir nuevos elementos
	                comboBoxClientes.removeAllItems();
	                for (Cliente cliente : clientes) {
	                    // Añade cada cliente encontrado al JComboBox
	                    comboBoxClientes.addItem(cliente);
	                }
	                // Si hay clientes encontrados, muestra el popup del JComboBox
	                if (!clientes.isEmpty()) {
	                    comboBoxClientes.showPopup();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}

    private void guardarCita() {
        try {
            String titulo = textFieldTituloVisita.getText();
            Date fecha = (Date) dateSpinner.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) timeSpinner.getValue());
            LocalTime hora = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
            
            String notas = (textPaneNotas != null) ? textPaneNotas.getText() : "";
            Cliente cliente = (Cliente) comboBoxClientes.getSelectedItem();
            Mascota mascota = (Mascota) comboBoxMascotas.getSelectedItem();

            if (cliente == null || mascota == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente y una mascota.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cita cita = new Cita(0, titulo, fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), hora, notas, cliente.getId(), mascota.getId());
            new CitaDAO().insertarCita(cita);

            JOptionPane.showMessageDialog(this, "Cita guardada con éxito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
            dialog.setVisible(true);
        });
    }

}