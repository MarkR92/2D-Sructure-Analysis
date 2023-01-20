import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ForcePopupPanel extends JPanel {
	
	private JComboBox<String> forceType;
	private JComboBox<String> directionType;
	
	private JTextField size;
	private JTextField sizeangle;
	
	private String force;
	private String direction;
	private String direction2;
	
	public double angle;
	private double magnitude;
	
	
	private JLabel forceLabel;
	private JLabel magLabel;
	private JLabel directionLabel;
	private JLabel angleLabel;
	//private int size;
	private JSlider degreeSlider;
	
public String getForceType( ) {
	return force;
}

public String getDirection( ) {
	return direction;
}
public String getDirection2( ) {
	return direction2;
}
public double getAngle( ) {
	return angle;
}

public double getMagnitude( ) {
	return magnitude;
}
	
public void createPopup() {
		
		JPanel forcepanel = new JPanel();
		
		degreeSlider = new JSlider(JSlider.HORIZONTAL,0,90,0);
		
		degreeSlider.setMajorTickSpacing(15);
		degreeSlider.setMinorTickSpacing(5);
		degreeSlider.setPaintTicks(true);
		degreeSlider.setPaintLabels(true);
	
		
		//sizeangle.setText(String.valueOf(degreeSlider.getValue()));
		
		forceType = new JComboBox<String>();
		directionType = new JComboBox<String>();
		
		forceLabel =new JLabel("Force Type: ");
		magLabel = new JLabel("Magnitude: ");
		directionLabel = new JLabel("Direction: ");
		angleLabel= new JLabel("Direction(deg):");
		
		size = new JTextField(10);
		sizeangle= new JTextField(10);
		sizeangle.setText("0");
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
		//sizeangle.setText(String.valueOf(angle));
//		forcepanel.setLayout(new BoxLayout(forcepanel, BoxLayout.PAGE_AXIS));
//		forcepanel.add(new JLabel("Force Type: "));
//		forcepanel.add(forceType);
//		
//		forcepanel.add(new JLabel("Magnitude: "));
//		forcepanel.add(size);
//		
//		forcepanel.add(new JLabel("Direction: "));
//		forcepanel.add(directionType);
		degreeSlider.addChangeListener(new ChangeListener() {
	         public void stateChanged(ChangeEvent ce) {
	        	 sizeangle.setText(String.valueOf(degreeSlider.getValue()));
	            repaint();
	            
	         }
	      });
		
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
//		gc.weightx=1;
//		gc.weighty=2;
//		gc.gridx = 0;
//		gc.gridy = 3;
//		gc.anchor = GridBagConstraints.LINE_END;
//		forcepanel.add(directionLabel,gc);
//		
//		gc.gridx = 1;
//		gc.gridy = 3;
//		gc.anchor = GridBagConstraints.LINE_START;
//		forcepanel.add(directionType, gc);
		
		//////////////////////////////////////////////////////////////////
		gc.weightx=1;
		gc.weighty=4;
		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_END;
		forcepanel.add(angleLabel,gc);
		
		gc.gridx = 1;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_START;
		forcepanel.add(degreeSlider, gc);
		
		//////////////////////////////////////////////////////////////////
		gc.weightx=1;
		gc.weighty=5;
		gc.gridx = 0;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.LINE_END;
	//	forcepanel.add(angleLabel,gc);
		
		gc.gridx = 1;
		gc.gridy =5;
		gc.anchor = GridBagConstraints.LINE_START;
		forcepanel.add(sizeangle, gc);
		

int action = JOptionPane.showConfirmDialog(null,forcepanel, "Select Force",JOptionPane.OK_CANCEL_OPTION);
			
		if (action == JOptionPane.OK_OPTION) {
			
			 force = (String) forcemodel.getSelectedItem();
			 
			 direction2 = (String) directionmodel.getSelectedItem();
			 
			 if(size.getText().isEmpty()) {
				 magnitude=0;
			 }else {
			 magnitude = Double.parseDouble(size.getText());
			 }
			 angle=(degreeSlider.getValue());
			 
			 
			 if (magnitude < 0) {
				 direction = "Up";
			 }else {
				 direction = "Down";	 
			 }
		
			
		}
}
}
