package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import com.toedter.calendar.JCalendar;
import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import DB.VeterinarioDAO;
import model.Cita;
import model.Cliente;
import model.Mascota;
import model.Mascota.MascotaContenedor;
import model.Veterinario;
import UISwing.recursos.RoundedPanel;

public class VentanaModificarCitaDialog extends JDialog {

    private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JTextField textFieldTituloVisita;
    private JTextPane textPaneNotas;
    private JComboBox<Veterinario> comboBoxDoctor;
    private JComboBox<Cliente> comboBoxClientes;
    private JComboBox<Mascota.MascotaContenedor> comboBoxMascotas;
    private JComboBox<String> comboBoxTipo;
    private RoundedPanel roundedPanel;
    private Cita citaActual;
    private List<CitaActualizadaListener> listeners = new ArrayList<>();


    public VentanaModificarCitaDialog(Frame owner, boolean modal, Cita citaActual) {
        super(owner, modal);
        this.citaActual = citaActual;
        setTitle("Modificar/Eliminar Cita");
        setUndecorated(true);
        setSize(new Dimension(888, 399));
        setLocationRelativeTo(null);


        roundedPanel = new RoundedPanel(30, Color.decode("#7E88E2"));
        roundedPanel.setLayout(null);
        roundedPanel.setBounds(0, 0, 888, 399);
        roundedPanel.setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        clienteDAO = new ClienteDAO();
        mascotaDAO = new MascotaDAO();

        inicializarComponentesUI(); 
        cargarVeterinarios();
        cargarDatosCita(); 
        getContentPane().add(roundedPanel); 
    }

	private void inicializarComponentesUI() {
    	
		JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(new Color(255, 255, 255));
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipo.setBounds(56, 37, 80, 34);
        roundedPanel.add(lblTipo);

        comboBoxTipo = new JComboBox<>(new String[]{"Visita", "Consulta", "Urgencia"});
        comboBoxTipo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxTipo.setBounds(54, 70, 120, 25);
        roundedPanel.add(comboBoxTipo);
        
        JLabel lblDoctor = new JLabel("Doctor:");
        lblDoctor.setForeground(new Color(255, 255, 255));
        lblDoctor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDoctor.setBounds(230, 37, 80, 34);
        roundedPanel.add(lblDoctor);
        
        comboBoxDoctor = new JComboBox<>();
        comboBoxDoctor.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxDoctor.setBounds(228, 70, 120, 25);
        roundedPanel.add(comboBoxDoctor);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setBounds(414, 37, 80, 34);
        roundedPanel.add(lblCliente);
        
     
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setBounds(657, 37, 80, 34);
        roundedPanel.add(lblMascota);
        
     // ComboBox CLientes
        comboBoxClientes = new JComboBox<Cliente>();
        comboBoxClientes.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxClientes.setEditable(true);
        comboBoxClientes.setBounds(410, 70, 179, 25);
        JTextField textEditorClientes = (JTextField) comboBoxClientes.getEditor().getEditorComponent();
        textEditorClientes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textEditorClientes.getText();
                if (text.trim().length() >= 4) {
                   
                    List<Cliente> filtrado = clienteDAO.buscarClientesPorNombreApellido(text); 
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
        comboBoxClientes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Cliente clienteSeleccionado = (Cliente) event.getItem();
                    actualizarMascotasPorCliente(clienteSeleccionado.getId());
                }
            }
        });

        comboBoxClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getApellidos() + ", " + cliente.getNombre()); 
                }
                return this;
            }
        });
        roundedPanel.add(comboBoxClientes);
   
        // Combobox de Mascotas
        comboBoxMascotas = new JComboBox<>();
        comboBoxMascotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxMascotas.setEditable(true);
        comboBoxMascotas.setBounds(654, 71, 179, 25);
        comboBoxMascotas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof MascotaContenedor) {
                    MascotaContenedor contenedor = (MascotaContenedor) value;
                    setText(contenedor.getMascota().getNombre());
                }
                return this;
            }
        });


        roundedPanel.add(comboBoxMascotas);
        

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(new Color(255, 255, 255));
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFecha.setBounds(414, 104, 44, 34);
        roundedPanel.add(lblFecha);

       

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(new Color(255, 255, 255));
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHora.setBounds(657, 104, 80, 34);
        roundedPanel.add(lblHora);
        configurarSeleccionFechaHora();
        
        JLabel lblTituloVisita = new JLabel("Título de la visita:");
        lblTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloVisita.setForeground(new Color(255, 255, 255));
        lblTituloVisita.setBounds(56, 106, 150, 30);
        roundedPanel.add(lblTituloVisita);

        textFieldTituloVisita = new JTextField();
        textFieldTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldTituloVisita.setBounds(54, 137, 294, 25);
        roundedPanel.add(textFieldTituloVisita);
        
        JLabel lblNotas = new JLabel("Nota");
        lblNotas.setForeground(new Color(255, 255, 255));
        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotas.setBounds(56, 181, 46, 34);
        roundedPanel.add(lblNotas);
        
        textPaneNotas = new JTextPane();
        textPaneNotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textPaneNotas.setBounds(54, 218, 779, 73);
        roundedPanel.add(textPaneNotas);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancelar.setBounds(56, 328, 120, 30);
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnCancelar.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnCancelar.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnCancelar.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnCancelar.setOpaque(true);
        btnCancelar.setRolloverEnabled(true);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(Color.decode("#003366"));
                btnCancelar.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(Color.WHITE);
                btnCancelar.setForeground(Color.decode("#0057FF"));
            }
        });
        btnCancelar.addActionListener(e -> {
        	dispose();
        });
        
        roundedPanel.add(btnCancelar);
        
        
        
        JButton btneliminarCita = new JButton("Eliminar");
        btneliminarCita.setRolloverEnabled(true);
        btneliminarCita.setOpaque(true);
        btneliminarCita.setForeground(new Color(0, 87, 255));
        btneliminarCita.setFont(new Font("Tahoma", Font.BOLD, 12));
        btneliminarCita.setFocusPainted(false);
        btneliminarCita.setContentAreaFilled(true);
        btneliminarCita.setBorderPainted(false);
        btneliminarCita.setBackground(Color.WHITE);
        btneliminarCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btneliminarCita.setBounds(556, 328, 120, 30);
        btneliminarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btneliminarCita.setBackground(Color.decode("#003366")); 
                btneliminarCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btneliminarCita.setBackground(Color.WHITE); 
                btneliminarCita.setForeground(Color.decode("#0057FF"));
            }
        });
        btneliminarCita.addActionListener(e -> eliminarCita());
        roundedPanel.add(btneliminarCita);
        
        JButton btnModificarCita = new JButton("Modificar");
        btnModificarCita.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnModificarCita.setBounds(713, 328, 120, 30);
        btnModificarCita.setBackground(Color.WHITE);
        btnModificarCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnModificarCita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnModificarCita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnModificarCita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnModificarCita.setOpaque(true);
        btnModificarCita.setRolloverEnabled(true);
        btnModificarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarCita.setBackground(Color.decode("#003366")); 
                btnModificarCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarCita.setBackground(Color.WHITE); 
                btnModificarCita.setForeground(Color.decode("#0057FF"));
            }
        });
        btnModificarCita.addActionListener(e -> modificarCita());
        roundedPanel.add(btnModificarCita);
        
        
 
       JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
               
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); 
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); 
                g2.dispose();
                super.paintComponent(g);
            }
        };
        centerPanel.setBackground(new Color(255, 255, 255, 70));
        centerPanel.setOpaque(false); 
        centerPanel.setBounds(24, 24, 841, 352);
        roundedPanel.add(centerPanel);
 
    }
	
	
	
	private void configurarSeleccionFechaHora() {
	    dateSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
	    dateSpinner.setEditor(dateEditor);
	    dateSpinner.setBounds(414, 137, 153, 25);
	    roundedPanel.add(dateSpinner);

	    calendarButton = new JButton(new ImageIcon(getClass().getResource("/imagenes/logoBotonCalendario.png")));
	    calendarButton.setBounds(568, 137, 25, 25);
	    calendarButton.addActionListener(e -> mostrarCalendario());
	    roundedPanel.add(calendarButton);

	    timeSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
	    timeSpinner.setEditor(timeEditor);
	    timeSpinner.setBounds(657, 138, 179, 25);
	    roundedPanel.add(timeSpinner);
	}

	private void mostrarCalendario() {
	    JDialog dialog = new JDialog();
	    dialog.setModal(true); 
	    dialog.setTitle("Seleccionar Fecha");
	    dialog.setSize(300, 300);
	    dialog.setLocationRelativeTo(null); 
	    JCalendar calendar = new JCalendar();
	    dialog.getContentPane().add(calendar, BorderLayout.CENTER);
	    
	    JButton okButton = new JButton("OK");
	    okButton.addActionListener(e -> {
	        dateSpinner.setValue(calendar.getDate());
	        dialog.dispose(); 
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
	                    comboBoxMascotas.addItem(new Mascota.MascotaContenedor(mascota));
	                }

	                if (citaActual != null && clienteId == citaActual.getClienteId()) {
	                    cargarYSeleccionarMascota(citaActual.getMascotaId());
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}	
	private void cargarDatosCita() {
	  
	    if (citaActual != null) {
	        textFieldTituloVisita.setText(citaActual.getTitulo());
	        textPaneNotas.setText(citaActual.getNotas());
	        LocalDate fecha = citaActual.getFecha();
	        LocalTime hora = citaActual.getHora();
	        dateSpinner.setValue(java.util.Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	        timeSpinner.setValue(java.util.Date.from(hora.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
	        seleccionarVeterinarioEnComboBox(citaActual.getVeterinarioId());
	        cargarYSeleccionarCliente(citaActual.getClienteId());
	        cargarYSeleccionarMascota(citaActual.getMascotaId());
	        comboBoxTipo.setSelectedItem(citaActual.getTipo());
	    }
	}
	
	
	private void seleccionarVeterinarioEnComboBox(int veterinarioId) {
	    for (int i = 0; i < comboBoxDoctor.getItemCount(); i++) {
	        Veterinario veterinario = comboBoxDoctor.getItemAt(i);
	        if (veterinario.getId() == veterinarioId) {
	            comboBoxDoctor.setSelectedIndex(i);
	            break;
	        }
	    }
	}
	
	private void cargarVeterinarios() {
	    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
	    List<Veterinario> veterinarios = veterinarioDAO.obtenerTodosLosVeterinarios();
	    for (Veterinario veterinario : veterinarios) {
	        comboBoxDoctor.addItem(veterinario);
	    }

	    comboBoxDoctor.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (value instanceof Veterinario) {
	                Veterinario veterinario = (Veterinario) value;
	                setText(veterinario.getNombre() + " " + veterinario.getApellidos());
	            }
	            return this;
	        }
	    });
	}

	
	private void cargarYSeleccionarCliente(int clienteId) {
	    List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
	    comboBoxClientes.removeAllItems();
	    Cliente clienteSeleccionado = null;
	    for (Cliente cliente : clientes) {
	        comboBoxClientes.addItem(cliente);
	        if (cliente.getId() == clienteId) {
	            clienteSeleccionado = cliente;
	        }
	    }
	    comboBoxClientes.setSelectedItem(clienteSeleccionado);
	}
	
	private void cargarYSeleccionarMascota(int mascotaId) {
	    for (int i = 0; i < comboBoxMascotas.getItemCount(); i++) {
	        Mascota.MascotaContenedor mascotaContenedor = comboBoxMascotas.getItemAt(i);
	        if (mascotaContenedor.getMascota().getId() == mascotaId) {
	            comboBoxMascotas.setSelectedIndex(i);
	            return;
	        }
	    }
	}

	private void modificarCita() {
	    try {
	        CitaDAO citaDAO = new CitaDAO();
	        citaActual.setTitulo(textFieldTituloVisita.getText());
	        
	        java.util.Date fecha = (java.util.Date) dateSpinner.getValue();
	        citaActual.setFecha(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	        
	        java.util.Date hora = (java.util.Date) timeSpinner.getValue();
	        citaActual.setHora(hora.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
	        
	        citaActual.setNotas(textPaneNotas.getText());
	        
	        Cliente clienteSeleccionado = (Cliente) comboBoxClientes.getSelectedItem();
	        MascotaContenedor mascotaSeleccionada = (MascotaContenedor) comboBoxMascotas.getSelectedItem();
	        
	        if (clienteSeleccionado != null) {
	            citaActual.setClienteId(clienteSeleccionado.getId());
	        }
	        
	        if (mascotaSeleccionada != null) {
	            citaActual.setMascotaId(mascotaSeleccionada.getMascota().getId());
	        }
	        
	        citaActual.setTipo(comboBoxTipo.getSelectedItem().toString());
	        
	        citaDAO.actualizarCita(citaActual);
	        
	        
	        JOptionPane.showMessageDialog(this, "Cita modificada con éxito.");
	        notificarCitaActualizada();
	        this.dispose();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al modificar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	


	private void eliminarCita() {
	    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta cita?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
	    if (confirmacion == JOptionPane.YES_OPTION) {
	        try {
	            CitaDAO citaDAO = new CitaDAO();
	            citaDAO.eliminarCita(citaActual.getId());
	            JOptionPane.showMessageDialog(this, "Cita eliminada con éxito.");
	            notificarCitaActualizada();
	            this.dispose();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error al eliminar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	public void addCitaActualizadaListener(CitaActualizadaListener listener) {
        listeners.add(listener);
    }
	
	private void notificarCitaActualizada() {
        for (CitaActualizadaListener listener : listeners) {
            listener.onCitaActualizada();
        }
    }
	
	

	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
	        Cita citaDePrueba = new Cita(); 
	        citaDePrueba.setId(1); 
	        citaDePrueba.setTitulo("Consulta de prueba");
	        citaDePrueba.setFecha(LocalDate.now());
	        citaDePrueba.setHora(LocalTime.now());
	        VentanaModificarCitaDialog dialog = new VentanaModificarCitaDialog(null, true, citaDePrueba);
	        dialog.setVisible(true);
	    });
	}

}