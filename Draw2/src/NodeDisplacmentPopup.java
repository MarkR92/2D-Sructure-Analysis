import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NodeDisplacmentPopup extends JPanel {
	
	private NodeDisplacmentPopupListener listener;
	private JTextField displacementField ;
	private String displacemnt="0.0";
	
	public void setNodeDisplacmentPopupListener(NodeDisplacmentPopupListener listener) {
		this.listener = listener;
	}
	public void createPopup() {
		JPanel nodepanel = new JPanel();
		
		displacementField = new JTextField(displacemnt);
		
		nodepanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		
		
		nodepanel.add(new JLabel ("dy:"), gc);
		gc.gridx++;
		nodepanel.add(displacementField,gc);
		
		int action = JOptionPane.showConfirmDialog(null,nodepanel, "Edit Displacement",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			listener.stringEmitted("OK");
			 
			 //System.out.println(direction2);
			
		}
	}

}
