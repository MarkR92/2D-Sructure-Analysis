import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class LabelPanel extends JPanel{

	private JLabel coordinatelabel;
	private JLabel fileloadedlabel;
	private JTextField entryfield;
	
	private TextEntryListener textEntryListener;
	 

	public LabelPanel() {
	 coordinatelabel= new JLabel(" ");
	 fileloadedlabel= new JLabel(" ");
	 entryfield =  new JTextField(20);
	
	 setLayout(new GridLayout(0,3,280,0));
	 
	 AbstractAction action = new AbstractAction()
	 {
	     @Override
	     public void actionPerformed(ActionEvent e)
	     {
	         //System.out.println(entryfield.getText());
	         if(textEntryListener != null) {
				 
				  textEntryListener.stringEmitted(entryfield.getText());
			  }
	         entryfield.setText("");
	         
	     }
	 };

	
	 entryfield.addActionListener( action );
//	 	entryfield.getDocument().addDocumentListener(new DocumentListener() {
//
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println(entryfield.getText());
//				
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//	 		
//	 	});
		add(fileloadedlabel);
		add(entryfield);
	
		add(coordinatelabel);

	}
	public void setTextEntryListener(TextEntryListener listener) {
		this.textEntryListener = listener;
	}
	public void setCorordinateLabelText(double x, double y) {
		coordinatelabel.setText("[" + (x/10)/2 + " , " + (y/10)/2 + "]");

	}
	public void setLoadedLabelText(String filename) {
		fileloadedlabel.setText(" " + filename);

	}
	
	public String getLoadedLabelText() {
		return fileloadedlabel.getText();

	}
	@Override
	public Dimension getPreferredSize() {
	  return new Dimension(200, 20);
	}

}
