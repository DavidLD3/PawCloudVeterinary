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
    private AutoCompleteComboBox<Cliente> autoCompleteClientes;
    private AutoCompleteComboBox<Mascota> autoCompleteMascotas;
    
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
        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setEditable(true);
        comboBoxClientes.setBounds(420, 74, 179, 25);
        JTextField textEditorClientes = (JTextField) comboBoxClientes.getEditor().getEditorComponent();
        textEditorClientes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textEditorClientes.getText();
                // Cambia la condición para activar la búsqueda con 4 o más caracteres
                if (text.trim().length() >= 4) {
                    actualizarListaClientes(text);
                } else if (text.trim().isEmpty()) {
                    // Opcional: puedes decidir limpiar el listado de clientes si el usuario borra el texto
                    comboBoxClientes.removeAllItems();
                }
            }
        });
        comboBoxClientes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Object item = comboBoxClientes.getSelectedItem();
                    if (item instanceof Cliente) {
                        Cliente clienteSeleccionado = (Cliente) item;
                        actualizarMascotasPorCliente(clienteSeleccionado.getId());
                    } else {
                        // Limpia el comboBox de mascotas si no hay un cliente seleccionado correctamente
                        comboBoxMascotas.removeAllItems();
                    }
                }
            }
        });
        roundedPanel.add(comboBoxClientes);
        cargarClientes();

        comboBoxMascotas = new JComboBox<>();
        comboBoxMascotas.setEditable(true);
        comboBoxMascotas.setBounds(660, 74, 179, 25);
        roundedPanel.add(comboBoxMascotas);
        
        
        
        autoCompleteClientes = new AutoCompleteComboBox<>(new Vector<Cliente>(clienteDAO.obtenerTodosLosClientes()));
        autoCompleteClientes.setBounds(420, 74, 179, 25);
        roundedPanel.add(autoCompleteClientes);
        
        autoCompleteClientes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Cliente clienteSeleccionado = (Cliente) event.getItem();
                    if (clienteSeleccionado != null) {
                        actualizarMascotasPorCliente(clienteSeleccionado.getId());
                    } else {
                        autoCompleteMascotas.removeAllItems(); // Limpia el comboBox de mascotas si no hay un cliente seleccionado
                    }
                }
            }
        });


        autoCompleteMascotas = new AutoCompleteComboBox<>(new Vector<>());
        autoCompleteMascotas.setBounds(660, 74, 179, 25);
        roundedPanel.add(autoCompleteMascotas);
        
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
	public static class AutoCompleteComboBox<E> extends JComboBox<E> {
        public AutoCompleteComboBox(Vector<E> items) {
            super(items);
            setEditable(true);
            JTextComponent editorComponent = (JTextComponent) getEditor().getEditorComponent();
            editorComponent.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent evt) {
                    if (evt.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
                        return;
                    }
                    int caretPosition = editorComponent.getCaretPosition();
                    String text = editorComponent.getText();
                    for (int i = 0; i < getItemCount(); i++) {
                        String item = getItemAt(i).toString();
                        if (item.toLowerCase().startsWith(text.toLowerCase())) {
                            setSelectedIndex(i);
                            editorComponent.setText(item);
                            editorComponent.setCaretPosition(item.length());
                            editorComponent.moveCaretPosition(caretPosition);
                            break;
                        }
                    }
                }
            });
        }
    }
    
   
    
   
    
	public void actualizarMascotasPorCliente(int clienteId) {
	    comboBoxMascotas.removeAllItems(); // Asume que esto es un JComboBox
	    List<Mascota> mascotas = mascotaDAO.obtenerMascotasPorClienteId(clienteId);
	    for (Mascota mascota : mascotas) {
	        comboBoxMascotas.addItem(mascota);
	    }
	}



    
    
	private void cargarClientes() {
	    List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
	    comboBoxClientes.removeAllItems();
	    for (Cliente cliente : clientes) {
	        comboBoxClientes.addItem(cliente);
	    }
	}

    
    

    private void actualizarListaClientes(String texto) {
        List<Cliente> clientes = clienteDAO.buscarClientesPorNombre(texto);
        comboBoxClientes.removeAllItems();
        for (Cliente cliente : clientes) {
            comboBoxClientes.addItem(cliente);
        }
        if (!clientes.isEmpty()) {
            comboBoxClientes.showPopup();
        }
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
        dialog.setTitle("Seleccionar Fecha");
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);
        JCalendar calendar = new JCalendar();
        dialog.getContentPane().add(calendar);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dateSpinner.setValue(calendar.getDate());
            dialog.dispose();
        });
        dialog.getContentPane().add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
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
