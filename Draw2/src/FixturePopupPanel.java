import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FixturePopupPanel extends JPanel {
	
	private JComboBox<String> fixType;
	private String fixturetype;
	
	
	public String isFixture( ) {
		return fixturetype;
	}
	public void createPopup() {
		
		JPanel fixturepanel = new JPanel();
		
		fixType = new JComboBox<String>();
		
		//Setup Combo
		DefaultComboBoxModel<String> fixmodel = new DefaultComboBoxModel<String>();
		
		fixmodel.addElement("Fixed");
		fixmodel.addElement("Pinned");
		fixmodel.addElement("Sliding");
		fixmodel.addElement("Custom");
		
		fixType.setModel(fixmodel);
		
		fixturepanel.add(new JLabel("Fixture Type: "));
		fixturepanel.add(fixType);
		
		
		int action = JOptionPane.showConfirmDialog(null,fixturepanel, "Select Fixture",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			
		
				 fixturetype = (String) fixmodel.getSelectedItem();
			
				
				//System.out.println(fixturetype);
				 
				
			 }
			
		}
	
}
