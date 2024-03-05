package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import model.Cita;
import model.Cliente;
import model.Mascota;
import model.Mascota.MascotaContenedor;
import UISwing.recursos.RoundedPanel;
import java.util.List;

public class VentanaHospitalizadosDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private RoundedPanel roundedPanel;
	private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JComboBox<Mascota> comboBoxMascotas;
    private JDateChooser dateChooserIngreso;
    private JDateChooser dateChooserSalida; // Opcional, puede ser null al inicio
    private JTextField textFieldMotivo;
    private JTextArea textAreaTratamiento;
    private JComboBox<String> comboBoxEstado;
    private JTextArea textAreaNotas;
    private JButton saveButton;
    private JButton cancelButton;
	
	 public VentanaHospitalizadosDialog(Frame owner, boolean modal) {
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
	        
	        
	        getContentPane().add(roundedPanel);
	        initDialogComponents();
		}
	 
	 private void initDialogComponents() {
	        // Inicialización de componentes aquí
 
		 	 JTextField txtBuscarMascota = new JTextField();
		     txtBuscarMascota.setBounds(50, 10, 200, 30);
		     
		     comboBoxMascotas = new JComboBox<>();
		     comboBoxMascotas.setBounds(260, 10, 200, 30);
		     JButton btnBuscar = new JButton("Buscar");
		     btnBuscar.setBounds(50, 51, 100, 30);	
			 btnBuscar.addActionListener(e -> {
		     String textoBusqueda = txtBuscarMascota.getText().trim();
		     List<Mascota> resultadoBusqueda = mascotaDAO.buscarMascotasPorNombre(textoBusqueda);
		     
		     comboBoxMascotas.removeAllItems(); // Limpiar los ítems anteriores del combo box
		     for (Mascota mascota : resultadoBusqueda) {
		         comboBoxMascotas.addItem(mascota); // Añadir las mascotas encontradas al combo box
		     }
		 });

	        // Selector de fecha de ingreso
			dateChooserIngreso = new JDateChooser();
			dateChooserIngreso.setDate(new Date()); // Establecer a la fecha actual
			dateChooserIngreso.setBounds(50, 148, 200, 30);
	        // Selector de fecha de salida (opcional al inicio)
	        dateChooserSalida = new JDateChooser();
	        dateChooserSalida.setBounds(277, 148, 200, 30);

	        // Campo de texto para el motivo de hospitalización
	        textFieldMotivo = new JTextField(20);
	        textFieldMotivo.setBounds(403, 220, 200, 30);
	        JScrollPane scrollPaneTratamiento = new JScrollPane();
	        scrollPaneTratamiento.setBounds(50, 223, 300, 110);

	        // ComboBox para el estado inicial de la mascota
	        comboBoxEstado = new JComboBox<>(new String[]{"Estable", "Crítico", "En observación"});
	        comboBoxEstado.setBounds(647, 220, 200, 30);
	        JScrollPane scrollPaneNotas = new JScrollPane();
	        scrollPaneNotas.setBounds(555, 32, 300, 150);	

	        // Botones
	        saveButton = new JButton("Guardar");
	        saveButton.setBounds(596, 319, 100, 30);
	        saveButton.addActionListener(e -> {
	            guardarHospitalizacion();
	        });

	        cancelButton = new JButton("Cancelar");
	        cancelButton.setBounds(739, 319, 100, 30);	
	        cancelButton.addActionListener(e -> {
	            dispose(); // Cerrar el diálogo
	        });

	        // Agregar los componentes al panel
	        roundedPanel.add(txtBuscarMascota);
	        roundedPanel.add(comboBoxMascotas);
	        roundedPanel.add(btnBuscar);
	        roundedPanel.add(dateChooserIngreso);
	        roundedPanel.add(dateChooserSalida);
	        roundedPanel.add(textFieldMotivo);
	        roundedPanel.add(scrollPaneTratamiento);
	        roundedPanel.add(comboBoxEstado);
	        roundedPanel.add(scrollPaneNotas);
	        roundedPanel.add(saveButton);
	        roundedPanel.add(cancelButton);
	        
	        	        // Área de texto para el tratamiento
	        	        textAreaTratamiento = new JTextArea();
	        	        textAreaTratamiento.setBounds(50, 223, 298, 108);
	        	        roundedPanel.add(textAreaTratamiento);
	        	        textAreaTratamiento.setLineWrap(true);
	        	        textAreaTratamiento.setWrapStyleWord(true);
	        	        
	        	        	        // Área de texto para notas adicionales
	        	        	        textAreaNotas = new JTextArea();
	        	        	        textAreaNotas.setBounds(557, 32, 298, 148);
	        	        	        roundedPanel.add(textAreaNotas);
	        	        	        textAreaNotas.setLineWrap(true);
	        	        	        textAreaNotas.setWrapStyleWord(true);

	        // Configura la posición y tamaño de cada componente aquí...
	    }
	 
	 private void guardarHospitalizacion() {
	        // Implementa la lógica de guardado aquí.
	        // Por ejemplo, crear un objeto Hospitalizacion con los datos de la UI y pasar a un DAO para guardar en DB.
	    }

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	VentanaHospitalizadosDialog dialog = new VentanaHospitalizadosDialog(null, true);
            dialog.setVisible(true);
        });
    }


}
