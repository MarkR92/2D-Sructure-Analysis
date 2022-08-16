import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditMaterialPopup extends JPanel {
	
	private JComboBox<String> materialList=new JComboBox<String>();
	private JList<String> eList;
	private EditMaterialPopupListener listener;
	private JTextField Etextfield;
	private JTextField Atextfield;
	private JTextField Itextfield;
	
	private DefaultComboBoxModel<String> materialmodel = new DefaultComboBoxModel<String>();
	private DefaultListModel<String> eModel = new DefaultListModel<String>();
	
	public void setEditMaterialPopupListener(EditMaterialPopupListener listener) {
		this.listener = listener;
	}
	public void createNameList(String Name) {
		
	materialmodel.addElement(Name);
	materialList.setModel(materialmodel);
	
	}
	public void createMaterialList(String E) {
		eModel.addElement(E);
		eList.setModel(eModel);
		
		
	}
	public void createPopup(Double E,Double A,Double I) {
		
		JPanel editpanel = new JPanel();
	
		Etextfield = new JTextField(E.toString());
		//Etextfield.setEditable(false);
		Atextfield = new JTextField(A.toString());
		//Atextfield.setEditable(false);
		Itextfield = new JTextField(I.toString());
		//Itextfield.setEditable(false);

		
		editpanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx=1;
		gc.weighty=1;
		
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		
		
		editpanel.add(new JLabel ("Name:"), gc);
		gc.gridx++;
		editpanel.add(materialList,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		editpanel.add(new JLabel ("Young's Modulus(GPa):"),gc);
		gc.gridx++;
		editpanel.add(Etextfield,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		editpanel.add(new JLabel ("Area(m^2):"),gc);
		gc.gridx++;
		editpanel.add(Atextfield,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		editpanel.add(new JLabel ("2nd Moment of Area(m^4):"),gc);
		gc.gridx++;
		editpanel.add(Itextfield,gc);
		
int action = JOptionPane.showConfirmDialog(null,editpanel, "Select Material",JOptionPane.OK_CANCEL_OPTION);
		
		if (action == JOptionPane.OK_OPTION) {
			
			listener.stringEmitted("OK");
			 
			 //System.out.println(direction2);
			
		}
	}
	public String getSelectedName() {
		
		
		return this.materialmodel.getSelectedItem().toString();
		
	}
	public String getSelectedE() {
		
		
		return Etextfield.getText();
		
	}
	public String getSelectedA() {
		
		
		return Atextfield.getText();
		
	}
	public String getSelectedI() {
	
	
	return Itextfield.getText();
	
}
	public void setSelectedE(String E) {
		
		//System.out.println(Etextfield.getText());
		Etextfield.setText(E);
		
	}

}
