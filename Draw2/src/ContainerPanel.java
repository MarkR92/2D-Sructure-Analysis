import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ContainerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContainerPanel() {
		setSize(800,800);
		//setBackground(Color.BLUE);
		setLayout(new GridBagLayout());
		setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 0, 1)));
	}
}
