package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PanelAlmacen extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelAlmacen() {
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1112, 45);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 50, 1112, 603);
		add(panel);

	}
}
