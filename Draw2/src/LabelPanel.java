import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LabelPanel extends JPanel{

	private JLabel coordinatelabel;
	private JLabel fileloadedlabel;
	 

	public LabelPanel() {
	 coordinatelabel= new JLabel(" ");
	 fileloadedlabel= new JLabel(" ");
	
	 setLayout(new GridLayout(0,2,630,0));
	
		add(fileloadedlabel);
		
	
		add(coordinatelabel);

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
