import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditForcePopupPanel extends JPanel {
	
	


	private JTextField size;
	
	private double magnitude2;
	private JLabel magLabel;
	
	
	
	public void createPopup(double magnitude) {
		
	this.magnitude2=magnitude;
		
		JPanel editpanel = new JPanel();
		
		magLabel = new JLabel("Magnitude: ");
		size = new JTextField(Double.toString(magnitude) );
		
		//Double.parseDouble(size.getText());
		
		editpanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		
		gc.fill = GridBagConstraints.NONE;
		
		gc.anchor = GridBagConstraints.LINE_END;
		editpanel.add(magLabel, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		
		
		gc.anchor = GridBagConstraints.LINE_START;
		editpanel.add(size,gc);
		
		
int action = JOptionPane.showConfirmDialog(null,editpanel, "Edit Force",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			 magnitude2 = Double.parseDouble(size.getText());
	}
		//return magnitude2;
	}



	public double getMagnitude() {
		// TODO Auto-generated method stub
		return magnitude2;
	}

}
