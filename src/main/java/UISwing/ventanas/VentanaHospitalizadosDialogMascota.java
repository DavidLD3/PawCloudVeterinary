package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import DB.ClienteDAO;
import DB.FarmacoDAO;
import DB.HospitalizacionDAO;
import DB.MascotaDAO;
import DB.VeterinarioDAO;
import model.Farmaco;
import model.Hospitalizacion;
import model.Veterinario;
import UISwing.recursos.RoundedPanel;
import java.util.List;
import javax.swing.JOptionPane;


public class VentanaHospitalizadosDialogMascota extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private RoundedPanel roundedPanel;
	private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JDateChooser dateChooserIngreso;
    private JDateChooser dateChooserSalida; // Opcional, puede ser null al inicio
    private JTextArea textAreaMotivo;
    private JTextArea textAreaTratamiento;
    private JComboBox<String> comboBoxEstado;
    private JTextArea textAreaNotas;
    private JButton saveButton;
    private JButton cancelButton;
	private HospitalizacionDAO hospitalizacionDAO;
	private JButton btnAddFarmaco;
	private FarmacoDAO farmacoDAO;
	private JComboBox<Veterinario> comboBoxVeterinarios;
	private String nombreMascota;
	private int idMascota;
	private HospitalizacionDAO hospitalizacionDao;
	private int idHospitalizacion = -1;

    
	public VentanaHospitalizadosDialogMascota(Frame owner, boolean modal, int idMascota, String nombreMascota) {
	    super(owner, modal);
	    this.idMascota = idMascota;
	    this.nombreMascota = nombreMascota;
	    
	    
	    setTitle("Hospitalizaciones - " + nombreMascota);
	        setTitle("Hospitalizaciones");
	        farmacoDAO = new FarmacoDAO();
	        setUndecorated(true);
	        setSize(new Dimension(888,455));
	        
	        setLocationRelativeTo(null);
	        
	        
	        
	        roundedPanel = new RoundedPanel(30, Color.decode("#5694F9"));
	        roundedPanel.setLayout(null);
	        roundedPanel.setBounds(0, 0, 888, 399); 
	        roundedPanel.setOpaque(false);
	        setBackground(new java.awt.Color(0, 0, 0, 0));
	        clienteDAO = new ClienteDAO();
	        mascotaDAO = new MascotaDAO();
	        hospitalizacionDAO = new HospitalizacionDAO();
	        
	        getContentPane().add(roundedPanel);
	        initDialogComponents();
	       
	    }
	       
	
	 private void initDialogComponents() {
	     	
			
		  JLabel labelNombreMascota = new JLabel(nombreMascota);
		  labelNombreMascota.setForeground(new Color(255, 255, 255));
		  labelNombreMascota.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
		    labelNombreMascota.setBounds(50, 53, 300, 25);
		    roundedPanel.add(labelNombreMascota);


	        // Selector de fecha de ingreso
			dateChooserIngreso = new JDateChooser();
			dateChooserIngreso.setDate(new Date()); 
			dateChooserIngreso.setBounds(40, 127, 244, 30);
	        // Selector de fecha de salida (opcional al inicio)
	        dateChooserSalida = new JDateChooser();
	        dateChooserSalida.setBounds(319, 127, 200, 30);

	    
	        textAreaMotivo = new JTextArea();
	        textAreaMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaMotivo.setLineWrap(true);
	        textAreaMotivo.setWrapStyleWord(true);
	        JScrollPane scrollPaneMotivo = new JScrollPane(textAreaMotivo);
	        scrollPaneMotivo.setBounds(568, 55, 280, 30);
	        

	     
	        // ComboBox para el estado inicial de la mascota
	        comboBoxEstado = new JComboBox<>(new String[]{"Estable", "Crítico", "En observación"});
	        comboBoxEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        comboBoxEstado.setBounds(319, 55, 200, 30);
	        

	        // Botones
	        saveButton = new JButton("Guardar");
	        saveButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	        saveButton.setBounds(748, 392, 100, 30);
	        saveButton.setBackground(Color.WHITE);
	        saveButton.setForeground(Color.decode("#0057FF")); // Letras en color azul
	        saveButton.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
	        saveButton.setBorderPainted(false); // Evita que se pinte el borde predeterminado
	        saveButton.setContentAreaFilled(false); // Evita que se pinte el área de contenido
	        saveButton.setOpaque(true);
	        saveButton.setRolloverEnabled(true);
	        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                saveButton.setBackground(Color.decode("#003366"));
	                saveButton.setForeground(Color.WHITE);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                saveButton.setBackground(Color.WHITE);
	                saveButton.setForeground(Color.decode("#0057FF"));
	            }
	        });
	        saveButton.addActionListener(e -> {
	            guardarHospitalizacion();
	        });

	        cancelButton = new JButton("Cancelar");
	        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	        cancelButton.setBounds(590, 392, 100, 30);
	        cancelButton.setBackground(Color.WHITE);
	        cancelButton.setForeground(Color.decode("#0057FF")); // Letras en color azul
	        cancelButton.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
	        cancelButton.setBorderPainted(false); // Evita que se pinte el borde predeterminado
	        cancelButton.setContentAreaFilled(false); // Evita que se pinte el área de contenido
	        cancelButton.setOpaque(true);
	        cancelButton.setRolloverEnabled(true);
	        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                cancelButton.setBackground(Color.decode("#003366"));
	                cancelButton.setForeground(Color.WHITE);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                cancelButton.setBackground(Color.WHITE); 
	                cancelButton.setForeground(Color.decode("#0057FF"));
	            }
	        });
	        cancelButton.addActionListener(e -> {
	            dispose(); 
	        });

	       
	        
	        // Área de texto para el tratamiento
	        textAreaTratamiento = new JTextArea();
	        textAreaTratamiento.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaTratamiento.setLineWrap(true);
	        textAreaTratamiento.setWrapStyleWord(true);
	        JScrollPane scrollPaneTratamiento = new JScrollPane(textAreaTratamiento);
	        scrollPaneTratamiento.setBounds(40, 200, 808, 87);
	        roundedPanel.add(scrollPaneTratamiento);
	        	        
	        // Área de texto para notas adicionales
	        textAreaNotas = new JTextArea();
	        textAreaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaNotas.setLineWrap(true);
	        textAreaNotas.setWrapStyleWord(true);
	        JScrollPane scrollPaneNotas = new JScrollPane(textAreaNotas);
	        scrollPaneNotas.setBounds(40, 320, 808, 50);
	        roundedPanel.add(scrollPaneNotas);
	        
	        comboBoxVeterinarios = new JComboBox<>();
	        comboBoxVeterinarios.setBounds(569, 127, 279, 30);
	        roundedPanel.add(comboBoxVeterinarios);
	        llenarComboBoxVeterinarios();
	        
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
	        centerPanel.setBackground(new Color(255, 255, 255, 80));
	        centerPanel.setOpaque(false); 
	        centerPanel.setBounds(21, 21, 846, 414);
	        
	        // Agregar los componentes al panel
	        
	          
	        roundedPanel.add(dateChooserIngreso);
	        roundedPanel.add(dateChooserSalida);
	        roundedPanel.add(scrollPaneMotivo);
	        roundedPanel.add(scrollPaneTratamiento);
	        roundedPanel.add(comboBoxEstado);
	        roundedPanel.add(scrollPaneNotas);
	        roundedPanel.add(saveButton);
	        roundedPanel.add(cancelButton);
	        roundedPanel.add(centerPanel);
	        
	        JLabel lblEstado = new JLabel("Estado:");
	        lblEstado.setForeground(new Color(255, 255, 255));
	        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblEstado.setBounds(319, 26, 79, 30);
	        roundedPanel.add(lblEstado);
	        
	        JLabel lblMotivo = new JLabel("Motivo:");
	        lblMotivo.setForeground(new Color(255, 255, 255));
	        lblMotivo.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblMotivo.setBounds(568, 26, 79, 30);
	        roundedPanel.add(lblMotivo);
	        
	        JLabel lblFechaIngreso = new JLabel("Fecha ingreso:");
	        lblFechaIngreso.setForeground(new Color(255, 255, 255));
	        lblFechaIngreso.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblFechaIngreso.setBounds(40, 96, 111, 30);
	        roundedPanel.add(lblFechaIngreso);
	        
	        JLabel lblFechaSalida = new JLabel("Fecha salida:");
	        lblFechaSalida.setForeground(new Color(255, 255, 255));
	        lblFechaSalida.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblFechaSalida.setBounds(319, 94, 111, 30);
	        roundedPanel.add(lblFechaSalida);
	        
	        JLabel lblTratamiento = new JLabel("Tratamiento:");
	        lblTratamiento.setForeground(new Color(255, 255, 255));
	        lblTratamiento.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblTratamiento.setBounds(40, 168, 111, 30);
	        roundedPanel.add(lblTratamiento);
	        
	        JLabel lblNotas = new JLabel("Notas:");
	        lblNotas.setForeground(new Color(255, 255, 255));
	        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblNotas.setBounds(40, 286, 79, 30);
	        roundedPanel.add(lblNotas);
	        
	        JLabel lblVeterinario = new JLabel("Veterinario:");
	        lblVeterinario.setForeground(new Color(255, 255, 255));
	        lblVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblVeterinario.setBounds(569, 96, 79, 30);
	        roundedPanel.add(lblVeterinario);
	        
	        btnAddFarmaco = new JButton("Añadir Fármaco");
	        btnAddFarmaco.setFont(new Font("Tahoma", Font.BOLD, 12));
	        btnAddFarmaco.setBounds(369, 396, 150, 25); 
	        btnAddFarmaco.setBackground(Color.WHITE);
	        btnAddFarmaco.setForeground(Color.decode("#0057FF"));
	        btnAddFarmaco.setFocusPainted(false); 
	        btnAddFarmaco.setBorderPainted(false);
	        btnAddFarmaco.setContentAreaFilled(false);
	        btnAddFarmaco.setOpaque(true);
	        btnAddFarmaco.setRolloverEnabled(true);
	        btnAddFarmaco.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                btnAddFarmaco.setBackground(Color.decode("#003366"));
	                btnAddFarmaco.setForeground(Color.WHITE);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                btnAddFarmaco.setBackground(Color.WHITE);
	                btnAddFarmaco.setForeground(Color.decode("#0057FF"));
	            }
	        });
	        btnAddFarmaco.addActionListener(e -> abrirDialogoSeleccionFarmaco());
	        roundedPanel.add(btnAddFarmaco);
	        
	        JLabel lblMascota = new JLabel("Mascota:");
	        lblMascota.setForeground(Color.WHITE);
	        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblMascota.setBounds(50, 26, 79, 30);
	        roundedPanel.add(lblMascota);
	      
	    }
	 public VentanaHospitalizadosDialogMascota(Frame owner, boolean modal, int idMascota, String nombreMascota, int idHospitalizacion) {
		    super(owner, modal);
		    this.idMascota = idMascota;
		    this.nombreMascota = nombreMascota;
		    this.idHospitalizacion = idHospitalizacion;
		    this.hospitalizacionDao = new HospitalizacionDAO();

		    setTitle("Hospitalizaciones - " + nombreMascota);
		    farmacoDAO = new FarmacoDAO();
		    setUndecorated(true);
		    setSize(new Dimension(888,455));
		    
		    setLocationRelativeTo(null);
		    
		    roundedPanel = new RoundedPanel(30, Color.decode("#5694F9"));
		    roundedPanel.setLayout(null);
		    roundedPanel.setBounds(0, 0, 888, 399); 
		    roundedPanel.setOpaque(false);
		    setBackground(new java.awt.Color(0, 0, 0, 0));
		    clienteDAO = new ClienteDAO();
		    mascotaDAO = new MascotaDAO();
		    hospitalizacionDAO = new HospitalizacionDAO();
		    
		    getContentPane().add(roundedPanel);
		    initDialogComponents();

		   
		    if (idHospitalizacion > 0) {
		        
		        cargarDatosHospitalizacion(idHospitalizacion);
		        saveButton.setText("Modificar");
		        
		        for (ActionListener al : saveButton.getActionListeners()) {
		            saveButton.removeActionListener(al);
		        }
		        saveButton.addActionListener(e -> actualizarHospitalizacion());
		    } else {
		        
		        saveButton.setText("Guardar");
		        
		        for (ActionListener al : saveButton.getActionListeners()) {
		            saveButton.removeActionListener(al);
		        }
		        saveButton.addActionListener(e -> guardarHospitalizacion());
		    }
		}
	 private void cargarDatosHospitalizacion(int idHospitalizacion) {
		    Hospitalizacion hospitalizacion = hospitalizacionDao.obtenerHospitalizacionPorId(idHospitalizacion);
		    if (hospitalizacion != null) {
		        
		        dateChooserIngreso.setDate(Date.from(hospitalizacion.getFechaIngreso().atZone(ZoneId.systemDefault()).toInstant()));
		        if (hospitalizacion.getFechaSalida() != null) {
		            dateChooserSalida.setDate(Date.from(hospitalizacion.getFechaSalida().atZone(ZoneId.systemDefault()).toInstant()));
		        }
		        textAreaMotivo.setText(hospitalizacion.getMotivo());
		        textAreaTratamiento.setText(hospitalizacion.getTratamiento());
		        comboBoxEstado.setSelectedItem(hospitalizacion.getEstado()); // Asegúrate de que los valores coincidan con los de tu ComboBox
		        textAreaNotas.setText(hospitalizacion.getNotas());
		        
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo cargar la información de la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	
	 private void actualizarHospitalizacion() {
		    
		    Hospitalizacion hospitalizacion = new Hospitalizacion();
		    
		    
		    hospitalizacion.setId(this.idHospitalizacion);
		    
		    
		    LocalDateTime fechaIngresoLocalDateTime = dateChooserIngreso.getDate().toInstant()
		        .atZone(ZoneId.systemDefault()).toLocalDateTime();
		    hospitalizacion.setFechaIngreso(fechaIngresoLocalDateTime);
		    
		   
		    if (dateChooserSalida.getDate() != null) {
		        LocalDateTime fechaSalidaLocalDateTime = dateChooserSalida.getDate().toInstant()
		            .atZone(ZoneId.systemDefault()).toLocalDateTime();
		        hospitalizacion.setFechaSalida(fechaSalidaLocalDateTime);
		    } else {
		        hospitalizacion.setFechaSalida(null);
		    }
		    
		   
		    hospitalizacion.setMotivo(textAreaMotivo.getText());
		    hospitalizacion.setTratamiento(textAreaTratamiento.getText());
		    hospitalizacion.setEstado(comboBoxEstado.getSelectedItem().toString());
		    hospitalizacion.setNotas(textAreaNotas.getText());
		    
		   
		    boolean exito = hospitalizacionDao.actualizarHospitalizacion(hospitalizacion);
		    if (exito) {
		        JOptionPane.showMessageDialog(this, "Hospitalización actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		        this.dispose();
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo actualizar la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	 private void abrirDialogoSeleccionFarmaco() {
		    try {
		        DialogoSeleccionFarmaco dialogo = new DialogoSeleccionFarmaco((Frame) this.getOwner(), true, farmacoDAO);
		        dialogo.setVisible(true);
		        if (dialogo.isSeleccionado()) {
		            Farmaco farmacoSeleccionado = dialogo.getFarmacoSeleccionado();
		            String dosis = dialogo.getDosis();
		            String frecuencia = dialogo.getFrecuencia();
		            
		            textAreaTratamiento.append(farmacoSeleccionado.getNombre() + " - Dosis: " + dosis + ", Frecuencia: " + frecuencia + "\n");
		            
		            boolean stockActualizado = farmacoDAO.actualizarStockFarmaco(farmacoSeleccionado.getId(), 1); 
		            
		           
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el diálogo de selección de fármaco: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	 
	 
	 private void llenarComboBoxVeterinarios() {
		    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
		    List<Veterinario> veterinarios = veterinarioDAO.obtenerTodosLosVeterinarios();
		    comboBoxVeterinarios.removeAllItems();
		    for (Veterinario veterinario : veterinarios) {
		        comboBoxVeterinarios.addItem(veterinario);
		    }
		}



		    

	 
	 
	 private void guardarHospitalizacion() {
		    try {
		        LocalDateTime fechaIngreso = LocalDateTime.now();
		        LocalDateTime fechaSalida = (dateChooserSalida.getDate() != null) ? dateChooserSalida.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
		        String motivo = textAreaMotivo.getText();
		        String tratamiento = textAreaTratamiento.getText();
		        String estado = (String) comboBoxEstado.getSelectedItem();
		        String notas = textAreaNotas.getText();

		        Veterinario veterinarioSeleccionado = (Veterinario) comboBoxVeterinarios.getSelectedItem();
		        int idVeterinarioSeleccionado = veterinarioSeleccionado != null ? veterinarioSeleccionado.getId() : -1; // Asegúrate de que esto se maneja correctamente

		        Hospitalizacion hospitalizacion = new Hospitalizacion(
		            0,
		            idMascota,
		            fechaIngreso,
		            fechaSalida,
		            motivo,
		            tratamiento,
		            estado,
		            notas
		        );

		        boolean resultadoInsertar = hospitalizacionDAO.insertarHospitalizacion(hospitalizacion, idVeterinarioSeleccionado);
		        if (resultadoInsertar) {
		            JOptionPane.showMessageDialog(this, "Hospitalización guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(this, "No se pudo guardar la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Error al guardar la hospitalización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
		}


		private void actualizarFechaSalida(int idMascota, Date fechaSalida) {
		    
		    boolean resultado = hospitalizacionDAO.actualizarFechaSalidaHospitalizacion(idMascota, new java.sql.Date(fechaSalida.getTime()));
		    if (resultado) {
		        JOptionPane.showMessageDialog(this, "Fecha de salida actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo actualizar la fecha de salida.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
}