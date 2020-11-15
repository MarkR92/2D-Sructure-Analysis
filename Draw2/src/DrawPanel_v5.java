import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DrawPanel_v5 {

    public DrawPanel_v5() {
    	
    	CreateGrid grid = new CreateGrid();
		grid.createGridList();
		grid.gridIntersections();
		
		
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(" ");
        DrawP drawPanel = new DrawP();

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                super.mouseMoved(me);
               
				label.setText("["+ me.getX() + " , " +me.getY()+"]");
				
				
				ArrayList<Integer> xx=CreateGrid.XInter;
				ArrayList<Integer> yy=CreateGrid.YInter;
				int ss=xx.size();
				int xc = me.getX();
				int yc =me.getY();
				for (int ii = 0; ii<ss; ii++)	{
					
					
					//Check if mouse is near a grid intersect.
					SelectGraphic sg3 =
								
								new SelectGraphic(xc,yc, xx.get(ii)-2, yy.get(ii)-2, xx.get(ii)+2, yy.get(ii)+2);
					sg3.isSelected();
								// boolean result = sg3.result;
							    
								//If mouse is near a grid intersect snap to intersect.
								if(sg3.result == true) {
									
									SnaptoGrid snap1 = new SnaptoGrid (xx.get(ii),yy.get(ii), sg3.result);
									
									snap1.snap();
									
								 }
								
							
							}
                for (Node n : drawPanel.getNodes()) {//iterate through each ball
                    if (n.getBounds().contains(me.getPoint()) && n.isSelected() == false) {//get the ball bounds and check if mouse click was within its bounds
                        if (!n.isHighlighted()) {//check if ball has been clicked on
                            n.setHighlighted(true);
  
                        }
                    
                    } else {
                    	
                    	
                    	n.setHighlighted(false);
                    	
                    
                }
                    
                   // drawPanel.repaint();
                }
                drawPanel.repaint();

            }
            
        });
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                for (Node n : drawPanel.getNodes()) {//iterate through each ball
                    if (n.getBounds().contains(me.getPoint())) {//get the ball bounds and check if mouse click was within its bounds
                        if (!n.isSelected()) {//check if ball has been clicked on
                            //n.setSelected(true);
                            //n.setHighlighted(true);
                        } 
                            
                        else {
                           // n.setSelected(false);
                        }
                       
                       drawPanel.repaint();//so ball color change will be shown
                    }
                    //drawPanel.repaint();
                }
               // drawPanel.repaint();

            }
            
        });

        JPanel controlPanel = new JPanel();

       // JButton createBallButton = new JButton("Add ball");
        drawPanel.addMouseListener(new MouseAdapter(){
        
            Random rand = new Random();
            private int counter = 1;

            public void mousePressed(MouseEvent e) {

                int ballRadius = 10;
                int x = e.getX();
                int y = e.getY();

                //check that we dont go offscreen by subtarcting its radius unless its x and y are not bigger than radius
                if (y > ballRadius) {
                    y -= ballRadius;
                }
                if (x > ballRadius) {
                    x -= ballRadius;
                }

                drawPanel.addNode(new Node(x, y, ballRadius, counter));//add ball to panel to be drawn
               
               //node. drawPanel.
             //ArrayList<Node>  a= drawPanel.getNodes();
             //a.get(0);
            // System.out.println(a.get(0));
                counter++;//increase the ball numberNode
            }
        });

       // final JTextArea jtf = new JTextArea(5, 10);
       // jtf.setEditable(false);
        //JButton printSelectedBallButton = new JButton("Print selected balls");
        //printSelectedBallButton.addActionListener(new ActionListener() {
          //  Random rand = new Random();
            //private int counter = 1;

            //public void actionPerformed(ActionEvent e) {
              //  jtf.setText("");
                //for (Node n : drawPanel.getNodes()) {
                  //  if (n.isSelected()) {
                    //    jtf.append("Selected: " + n.getNumber() + "\n");
                 //   }
                //}

            //}
        //});

        //controlPanel.add(createBallButton);
        //controlPanel.add(printSelectedBallButton);
       // JScrollPane jsp = new JScrollPane(jtf);
       // controlPanel.add(jsp);

        frame.add(drawPanel);
        frame.add(controlPanel, BorderLayout.SOUTH);
        label.setLocation(100, 100);
		label.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(label, BorderLayout.SOUTH);
		
        frame.pack();
        frame.setVisible(true);
        
		

    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawPanel_v5();
            }
        });
    }
}



