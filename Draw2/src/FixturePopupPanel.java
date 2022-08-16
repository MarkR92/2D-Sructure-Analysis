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
public class FixturePopupPanel extends JPanel {
	
	private JComboBox<String> fixType;
	private String fixturetype;
	private JSlider degreeSlider;
	private JLabel angleLabel;
	private JLabel fixtureLabel;
	private JTextField sizeangle;
	public double angle;
	
	public String isFixture( ) {
		return fixturetype;
	}
	public void createPopup() {
		
		JPanel fixturepanel = new JPanel();
		
		degreeSlider = new JSlider(JSlider.HORIZONTAL,0,90,0);
		
		degreeSlider.setMajorTickSpacing(15);
		degreeSlider.setMinorTickSpacing(5);
		degreeSlider.setPaintTicks(true);
		degreeSlider.setPaintLabels(true);
		
		fixType = new JComboBox<String>();
		
		//Setup Combo
		DefaultComboBoxModel<String> fixmodel = new DefaultComboBoxModel<String>();
		
		fixmodel.addElement("Fixed");
		fixmodel.addElement("Pinned");
		fixmodel.addElement("Sliding");
		fixmodel.addElement("Custom");
		
		angleLabel= new JLabel("Direction(deg):");
		fixtureLabel =new JLabel("Fixture Type: ");
		
		sizeangle= new JTextField(10);
		sizeangle.setText("0");
		
		fixType.setModel(fixmodel);
		
		//fixturepanel.add(new JLabel("Fixture Type: "));
		//fixturepanel.add(fixType);
		
		degreeSlider.addChangeListener(new ChangeListener() {
	         public void stateChanged(ChangeEvent ce) {
	        	 sizeangle.setText(String.valueOf(degreeSlider.getValue()));
	            repaint();
	            
	         }
	      });
		
		fixturepanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
///////////////////////////////////////////////////////////////
		
gc.weightx=1;
gc.weighty=1;

gc.gridx = 0;
gc.gridy = 0;



gc.fill = GridBagConstraints.NONE;

gc.anchor = GridBagConstraints.LINE_END;
fixturepanel.add(fixtureLabel, gc);
gc.gridx = 1;
gc.gridy = 0;


gc.anchor = GridBagConstraints.LINE_START;
fixturepanel.add(fixType,gc);
///////////////////////////////////////////////////////////////////
gc.weightx=1;
gc.weighty=4;
gc.gridx = 0;
gc.gridy = 4;
gc.anchor = GridBagConstraints.LINE_END;
fixturepanel.add(angleLabel,gc);

gc.gridx = 1;
gc.gridy = 4;
gc.anchor = GridBagConstraints.LINE_START;
//fixturepanel.add(degreeSlider, gc);

//////////////////////////////////////////////////////////////////
gc.weightx=1;
gc.weighty=5;
gc.gridx = 0;
gc.gridy = 5;
gc.anchor = GridBagConstraints.LINE_END;
//	forcepanel.add(angleLabel,gc);

gc.gridx = 1;
gc.gridy =4;
gc.anchor = GridBagConstraints.LINE_START;
fixturepanel.add(sizeangle, gc);

		
		int action = JOptionPane.showConfirmDialog(null,fixturepanel, "Select Fixture",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			
		
				 fixturetype = (String) fixmodel.getSelectedItem();
			
				 angle=(Double.valueOf(sizeangle.getText()));
				//System.out.println(fixturetype);
				 
				
			 }
			
		}
	public double getAngle( ) {
		return angle;
	}		
}
