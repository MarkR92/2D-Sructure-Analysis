import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar {
	  public JMenuBar createMenuBar() {
	    	JMenuBar menuBar = new JMenuBar();
	    	
	    	JMenu fileMenu = new JMenu("File");
	    	
	    	menuBar.add(fileMenu);
	    	return menuBar;
	    }
}
