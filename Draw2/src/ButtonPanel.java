import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ButtonPanel extends JPanel{
	
	 public void setMinimumSize() {
		// setMinimumSize(new Dimension(80, 20));
	        
	    }
	 
	 public void createButtonFrame() {
		 
	        JFrame buttonframe = new JFrame();
	        buttonframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        
	        JButton draw = new JButton("Draw");
	        JButton delete = new JButton("Delete");
	        JButton save = new JButton("Save");
	        JButton load = new JButton("Load");
	        
	        
	        JPanel btnPanel = new JPanel();
	       // draw.setText("Drawing");
	       
	      // setMinimumSize();
			//draw.setSize(300, 20);
			//btnPanel.setLayout(new BoxLayout(buttonframe, BoxLayout.Y_AXIS));
	        buttonframe.add(btnPanel);
	        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
	        btnPanel.add(draw);
	        btnPanel.add(delete);
	        btnPanel.add(save);
	        btnPanel.add(load);
			//buttonframe.add(draw);
			//buttonframe.add(delete);
			//buttonframe.setName("Drawing");
			  draw.addActionListener(new ActionListener() {
		        	
		        	
		        	
		        	public void actionPerformed(ActionEvent e) {
		        		//System.out.println("Pressed");
		        		// isClicked();
		        		   boolean drawing = isClicked(); 
		        		  
		    		       if (drawing == true) {
		    		    	  draw.setText("Drawing");
		    		       }
		    		       if (drawing == false) {
		    		    	   draw.setText("Draw");
		    		       }
		    		      // System.out.println(isclicked);
		    		     // System.out.println(drawing);
		        		
		        	}
		        	
		        });
			  
			  
			  
			  
			//  buttonframe.add(bPanel);
			  buttonframe.setLocation(600,0);
			  draw.setSize(100,20);
			  buttonframe.pack();
		    
		      
		        buttonframe.setVisible(true);
		    
	        
	 }
	private int clicks = 0;
	 boolean isclicked = false;
	
	 public boolean isClicked() {
		 
		
		 clicks++;	
		// System.out.println(clicks);
 		
 		if (clicks == 1){
 			isclicked = true;
 			
 			
 		}
 		else {
 			isclicked = false;
 			clicks =0;
 		}
 	//System.out.println(isclicked);
			// JButton draw = new JButton("Draw");
			//  draw.setPreferredSize(new Dimension(80, 20));
		return isclicked;
		       
		       
		 }
	 public void addButton() {
		// JButton draw = new JButton("Draw");
		//  draw.setPreferredSize(new Dimension(80, 20));
	       
	       
	 }
	 
	 @Override
	 public Dimension getPreferredSize() {
		 
		// setMinimumSize(new Dimension(100, 100));
	        return new Dimension(150,20);
	    }
	
}
