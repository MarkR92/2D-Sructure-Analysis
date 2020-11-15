import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ForcePopupPanel extends JPanel {
	
	private JComboBox<String> forceType;
	private JComboBox<String> directionType;
	
	private JTextField size;
	
	private String force;
	private String direction;
	private String direction2;
	private double magnitude;
	
	
	private JLabel forceLabel;
	private JLabel magLabel;
	private JLabel directionLabel;
	
	//private int size;
	
public String getForce( ) {
	return force;
}

public String getDirection( ) {
	return direction;
}
public String getDirection2( ) {
	return direction2;
}

public double getMagnitude( ) {
	return magnitude;
}
	
public void createPopup() {
		
		JPanel forcepanel = new JPanel();
		
		forceType = new JComboBox<String>();
		directionType = new JComboBox<String>();
		
		forceLabel =new JLabel("Force Type: ");
		magLabel = new JLabel("Magnitude: ");
		directionLabel = new JLabel("Direction: ");
		
		size = new JTextField(10);
		//Setup Combo
		DefaultComboBoxModel<String> forcemodel = new DefaultComboBoxModel<String>();
		DefaultComboBoxModel<String> directionmodel = new DefaultComboBoxModel<String>();
		
		forcemodel.addElement("Point");
		forcemodel.addElement("UDL");
		forcemodel.addElement("Moment");
		
		directionmodel.addElement("Perpendicular");
		directionmodel.addElement("Parallel");
		
		
		
		forceType.setModel(forcemodel);
		directionType.setModel(directionmodel);
		
//		forcepanel.setLayout(new BoxLayout(forcepanel, BoxLayout.PAGE_AXIS));
//		forcepanel.add(new JLabel("Force Type: "));
//		forcepanel.add(forceType);
//		
//		forcepanel.add(new JLabel("Magnitude: "));
//		forcepanel.add(size);
//		
//		forcepanel.add(new JLabel("Direction: "));
//		forcepanel.add(directionType);
		
		//////////////////////////////////////////////////////////////////
		
		forcepanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
///////////////////////////////////////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		
		gc.fill = GridBagConstraints.NONE;
		
		gc.anchor = GridBagConstraints.LINE_END;
		forcepanel.add(forceLabel, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		
		
		gc.anchor = GridBagConstraints.LINE_START;
		forcepanel.add(forceType,gc);
///////////////////////////////////////////////////////////////////
		gc.weightx=1;
		gc.weighty=1;
		gc.gridx = 0;
		gc.gridy = 1;
		
		gc.anchor = GridBagConstraints.LINE_END;
		forcepanel.add(magLabel, gc);
		gc.gridx = 1;
		gc.gridy = 1;
		
		gc.anchor = GridBagConstraints.LINE_START;
		forcepanel.add(size,gc);
///////////////////////////////////////////////////////////////////
		gc.weightx=1;
		gc.weighty=2;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_END;
		forcepanel.add(directionLabel,gc);
		
		gc.gridx = 1;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_START;
		forcepanel.add(directionType, gc);
		
		//////////////////////////////////////////////////////////////////
		
		

int action = JOptionPane.showConfirmDialog(null,forcepanel, "Select Force",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			 force = (String) forcemodel.getSelectedItem();
			 
			 direction2 = (String) directionmodel.getSelectedItem();
			 magnitude = Double.parseDouble(size.getText());
					//System.out.println(magnitude); 
			 
			 if (magnitude < 0) {
				 direction = "Up";
			 }else {
				 direction = "Down";	 
			 }
			 
			 //System.out.println(direction2);
			
		}
}
}
