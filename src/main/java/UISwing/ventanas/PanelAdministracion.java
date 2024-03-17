package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PanelAdministracion extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelAdministracion() {
		
		JButton btnAgregarVeterinario = new JButton("Agregar Veterinario");
	    btnAgregarVeterinario.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Crear y mostrar el diálogo de registro de veterinario
	            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelAdministracion.this);
	            VentanaRegistroVeterinarioDialog dialogoRegistroVeterinario = new VentanaRegistroVeterinarioDialog(frame, true);
	            dialogoRegistroVeterinario.setVisible(true);
	        }
	    });
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(93)
					.addComponent(btnAgregarVeterinario)
					.addContainerGap(930, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(btnAgregarVeterinario)
					.addContainerGap(532, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
