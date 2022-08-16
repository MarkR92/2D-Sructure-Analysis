import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MaterialDialog extends JDialog implements ActionListener{
	
	
	
	private JComboBox<String> materialname;
	private DefaultComboBoxModel<String> materialmodel = new DefaultComboBoxModel<String>();
	
	//private JButton okButton;
	private JButton cancelButton;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	
	
	private String E;
	private String A;
	private String I;
	private String Name;
	
	private JTextField Etextfield;
	private JTextField nametextfield;
	private JTextField Atextfield;
	private JTextField Itextfield;
	
	private boolean toAdd=false;
	private boolean toAdd2=false;
	
	private MaterialDialogListener listener;
	
	
	public MaterialDialog(JFrame parent) {
		super(parent, "Material Libary",true);
		
		setSize(500,400);
		setLocationRelativeTo(parent);
		E="";
		A="";
		I="";
	
		cancelButton = new JButton("Cancel");
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		
		
		
		materialname = new JComboBox<String>();
		
		nametextfield = new JTextField(10);
		nametextfield.setEditable(false);
		
		Etextfield = new JTextField(10);
		Etextfield.setEditable(false);
		
		Atextfield = new JTextField(10);
		Atextfield.setEditable(false);
		
		Itextfield = new JTextField(10);
		Itextfield.setEditable(false);
		
		materialmodel.addElement("Default");
		
		materialname.setModel(materialmodel);
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		
		gc.weightx=1;
		gc.weighty=1;
		
		
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		
		
		add(new JLabel ("Current:"), gc);
		gc.gridx++;
		add(materialname,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		add(new JLabel ("Name:"),gc);
		gc.gridx++;
		add(nametextfield,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		add(new JLabel ("Young's Modulus(GPa):"),gc);
		gc.gridx++;
		add(Etextfield,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		add(new JLabel ("Area(m2):"),gc);
		gc.gridx++;
		add(Atextfield,gc);
		
		gc.gridy++;
		gc.gridx = 0;
		add(new JLabel ("I Value(m4):"),gc);
		gc.gridx++;
		add(Itextfield,gc);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////
		gc.gridy++;
		gc.gridx = 0;
		add(addButton,gc);
		addButton.addActionListener(this);
		gc.gridx++;
		add(editButton,gc);
		gc.gridx++;
		add(deleteButton,gc);
		gc.gridx++;
		add(cancelButton,gc);
		
		addButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
			addMaterial();
			}
			
		});
		
		editButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				
				editMaterial();
				
			}
			
		});
		
		deleteButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				deleteMaterial();
				
			}
			
		});

		
		cancelButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
			setVisible(false);
				
			}
			
		});
	}
	
	public void addMaterial() {
	
		for (int i =0;i<materialmodel.getSize();i++) {
			
			if(materialmodel.getElementAt(i).matches(nametextfield.getText())) {
				toAdd = false;
				
			}else {
				toAdd = true;
				
			}
		}
	
		if(nametextfield.getText().isBlank()||
		   Etextfield.getText().isBlank()||	
		   Atextfield.getText().isBlank()||
		   Itextfield.getText().isBlank()||
		   toAdd==false) {
			
			System.out.println("blank");
			}else {
			materialmodel.addElement(nametextfield.getText());
			E= Etextfield.getSelectedText();
			A= Atextfield.getSelectedText() ;
			I= Itextfield.getSelectedText() ;
			Name=nametextfield.getText();
			toAdd2=true;
			}
	}
	public void editMaterial() {
		nametextfield.setEditable(true);
		Etextfield.setEditable(true);
		Atextfield.setEditable(true);
		Itextfield.setEditable(true);
	}
	public void deleteMaterial() {
		if(materialmodel.getSelectedItem().toString().matches("Default")) {
			System.out.println("Can not Delete Default");
		}else {
			materialmodel.removeElement(materialmodel.getSelectedItem());
			listener.stringEmitted("Delete");
		}
	}
	
	
	public String getMaterialModel() {
		return materialmodel.getSelectedItem().toString();
	}
	public String getE() {
		
		return E;
	}
	public String getA() {
		
		return A;
	}
	public String getI() {
		
		return I;
	}
	public String getName() {
		
		return Name;
	}
	public void setMaterialListener(MaterialDialogListener listener) {
		
		this.listener = listener;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton isclicked =  (JButton) e.getSource();
		
		if( isclicked == addButton && toAdd2==true) {
			
	
				listener.stringEmitted("Add");
				
			
		}
		
	}

}
