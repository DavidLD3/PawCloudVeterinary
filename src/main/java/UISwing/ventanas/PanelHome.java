package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JButton;

public class PanelHome extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelHome() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 619, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(154, 192, 89, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(747, 338, 89, 23);
		add(btnNewButton_2);

	}

}
