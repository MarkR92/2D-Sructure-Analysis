import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ResultPopupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> resultType;
	
	
	private JTextField size;
	
	private String resulttype;
	
	
	
	private JLabel resultLabel;
	
	
	public String getResultType( ) {
		return resulttype;
	}

		public void createPopup() {
		
		JPanel resultpanel = new JPanel();
		
		resultType = new JComboBox<String>();
		
		
		resultLabel =new JLabel("Result Type: ");
		
		
		size = new JTextField(10);
		//Setup Combo
		DefaultComboBoxModel<String> resultmodel = new DefaultComboBoxModel<String>();
		
		
		resultmodel.addElement("Reactions");
		resultmodel.addElement("Displacements");
		resultmodel.addElement("Shear Diagram");
		resultmodel.addElement("Bending Diagram");
		
		
		
		
		resultType.setModel(resultmodel);
		
		resultpanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
///////////////////////////////////////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		
		gc.fill = GridBagConstraints.NONE;
		
		gc.anchor = GridBagConstraints.LINE_END;
		resultpanel.add(resultLabel, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		
		
		gc.anchor = GridBagConstraints.LINE_START;
		resultpanel.add(resultType,gc);
		
int action = JOptionPane.showConfirmDialog(null,resultpanel, "Select Result",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			 resulttype = (String) resultmodel.getSelectedItem();
			
			 
			 //System.out.println(direction2);
			
		}
}
		}

