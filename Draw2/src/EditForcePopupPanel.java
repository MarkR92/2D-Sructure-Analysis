import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditForcePopupPanel extends JPanel {
	
	


	private JTextField size;
	private JTextField anglefield;
	
	private double magnitude;
	private JLabel magLabel;
	
	private double angle;
	private JLabel anglelabel;
	
	
	
	public void createPopup(double magnitude,double angle) {

		JPanel editpanel = new JPanel();
		
		magLabel = new JLabel("Magnitude: ");
		size = new JTextField(Double.toString(magnitude) );
		
		anglelabel = new JLabel("Angle: ");
		anglefield = new JTextField(Double.toString(angle) );
		
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
/////////////////////////////////////////////////////////////////////////////////		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridx = 0;
		gc.gridy = 1;
	
		gc.fill = GridBagConstraints.NONE;
		
		gc.anchor = GridBagConstraints.LINE_END;
		editpanel.add(anglelabel, gc);
		gc.gridx = 1;
		gc.gridy = 1;
		
		
		gc.anchor = GridBagConstraints.LINE_START;
		editpanel.add(anglefield,gc);
		
		
		int action = JOptionPane.showConfirmDialog(null,editpanel, "Edit Force",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION)
		{
			 this.magnitude = Double.parseDouble(size.getText());
			 this.angle=Double.parseDouble(anglefield.getText());
		}
		
	}



	public double getMagnitude() 
	{
		return magnitude;
	}
	public double getDirection()
	{
		return angle;
	}

}
