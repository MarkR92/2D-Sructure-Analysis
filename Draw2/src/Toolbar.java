import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Toolbar extends JPanel implements ActionListener{
	
	
	private JButton drawButton;
	private JButton deleteButton;
	private JButton fixtureButton;
	private JButton forceButton;
	private JButton selectAllButton;
	private JButton calculateButton;
	private JButton resultsButton;
	
	private ToolBarListener buttonListener;
	
	
	boolean isclicked = false;
	boolean isdelete = false;
	boolean isfixtured = false;
	boolean isforce = false;
	
	
	
	public Toolbar() {
		
		drawButton = new JButton ("Draw");
		deleteButton = new JButton ("Delete");
		fixtureButton = new JButton ("Fixture");
		forceButton = new JButton ("Force");
		selectAllButton = new JButton("Select All");
		calculateButton = new JButton("Calculate");
		resultsButton = new JButton("Results");
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(drawButton);
		add(deleteButton);
		add(fixtureButton);
		add(forceButton);
		add(selectAllButton);
		add(calculateButton);
		add(resultsButton);
		
		 drawButton.addActionListener(this);
		 deleteButton.addActionListener(this);
		 fixtureButton.addActionListener(this);
		 forceButton.addActionListener(this);
		 selectAllButton.addActionListener(this);
		 calculateButton.addActionListener(this);
		 resultsButton.addActionListener(this);
	        	
	        	
	        	
	        	
		 
		  
	}
	
	public void setToolBarListener(ToolBarListener listener) {
		this.buttonListener = listener;
	}
	
	
	 public boolean isDraw() { 
		return isdrawing;

	 }
	 
	 public boolean isDelete() {
		return isdelete;

	 }
	 public boolean isFixtured() {
			return isfixtured;

		 }
	 
private int count;
boolean isdrawing = false;
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		//System.out.println(clicked);
		  if (clicked == drawButton && count == 0) {
			  drawButton.setText("Drawing");
			  count++;
			  isdrawing = true;
			  
       }
		  else if (clicked == drawButton && count == 1) {
			  drawButton.setText("Draw");
			  count--;
			  isdrawing = false;
       }
		  if (clicked == deleteButton && count == 0) {
			 
			  if(buttonListener != null) {
				 
				  buttonListener.stringEmitted("Delete");
			  }
			  
			
			  
		  }
		  if (clicked == selectAllButton && count == 0) {
				 
			  if(buttonListener != null) {
				 
				  buttonListener.stringEmitted("Select");
			  }
			  
			
			  
		  }
		  if (clicked == calculateButton && count == 0) {
				 
			  if(buttonListener != null) {
				 
				  buttonListener.stringEmitted("Calculate");
			  }
			  
			
			  
		  }
		  if (clicked == fixtureButton && count == 0) {
			 
			  if(buttonListener != null) {
					 
					  buttonListener.stringEmitted("Fixture");
				  }
			  
			  isfixtured=true;
			 
			  
		  }
		  
		  if (clicked == forceButton && count == 0) {
			  //System.out.println("Bound");
			  if(buttonListener != null) {
					 // System.out.println("Clicked");
					  buttonListener.stringEmitted("Force");
				  }
			  
			 // isfixtured=true;
			 
			  
		  }
		  if (clicked == resultsButton && count == 0) {
			  //System.out.println("Bound");
			  if(buttonListener != null) {
					 // System.out.println("Clicked");
					  buttonListener.stringEmitted("Result");
				  }
			  
			 // isfixtured=true;
			 
			  
		  }
    		
    	}
    	

		
	}

