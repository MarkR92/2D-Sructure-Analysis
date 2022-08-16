import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class DrawExample extends JFrame {
/**
 * 
 */
private static final long serialVersionUID = 1L;
private double zoom = 1d;
private int tranx;
private int trany;
DrawPanel drawPanel;
int snapmousepositionx, snapmousepositiony;//current snap coords
int currentmousepositionx,currentmousepositiony;

public DrawExample() {
    drawPanel = new DrawPanel();
    JPanel containerPanel = new JPanel();
    JFrame frame = new JFrame(); // Instance of a JFrame
    
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    frame.setSize(800, 800);
    //drawPanel.setSize(800, 800);
    

    containerPanel.setLayout(new GridBagLayout());
    containerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 0, 1)));
    
    
    
    containerPanel.add(drawPanel);
    frame.add(new JScrollPane(containerPanel));

    
    drawPanel.addMouseWheelListener(new MouseAdapter() {            //add wheel listener to drawPanel
         @Override
        public void mouseWheelMoved(MouseWheelEvent e) {            //when wheel is moved
        
            if (e.getPreciseWheelRotation() < 0) {
                
               zoom += 0.1;
           } else {
               zoom -= 0.1;
           }

           if (zoom < 0.01) {
               zoom = 0.01;
         }
         

          drawPanel.repaint();
           
         }
    
          
    });
    drawPanel.addMouseMotionListener(new MouseAdapter() {
        
        public void mouseMoved(MouseEvent me) {
            super.mouseMoved(me);
        
            
        
            drawPanel.createSnapGrid(me.getPoint().x, me.getPoint().y);
        
            tranx=me.getPoint().x;
            trany=me.getPoint().y;

            drawPanel.repaint();
        }
        
    });

    
    
    frame.setVisible(true);
    
    
    
}
 public class DrawPanel extends JPanel {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 800);
        }

          
      @Override
      protected void paintComponent(Graphics grphcs) {
           super.paintComponent(grphcs);
            Graphics2D g2d = (Graphics2D) grphcs;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            zoom=Math.round(zoom*10.0)/10.0;

            AffineTransform at = g2d.getTransform();

            at.translate(tranx, trany);
            at.scale(zoom, zoom);
            at.translate(-tranx, -trany);
            g2d.setTransform(at);
         
            g2d.setColor(Color.lightGray);
            drawGrid(g2d);
      }
      public void createSnapGrid(int x, int y) {
            
             currentmousepositionx = x;
             currentmousepositiony = y;
             
             int remainderx = currentmousepositionx % 10, remaindery = currentmousepositiony % 10;
             
             if (remainderx<800/2) setSnapX(currentmousepositionx - remainderx) ;
             else setSnapX(currentmousepositionx + (10-remainderx));
             
             if (remaindery<800/2) setSnapY(currentmousepositiony - remaindery);
             else setSnapY(currentmousepositiony + (10)-remaindery);
            
            
        }

    }


  public void drawGrid(Graphics2D g) {
        g.setColor(Color.lightGray);
        g.clearRect(0, 0, 800, 800);
        
        System.out.println(getHeight());
        //grid vertical lines
        for (int i= (10);i<800;i+=10) {
             g.drawLine(i, 0, i, 800);
             
        }
       
        //grid horizontal lines
        for (int j= (10);j<800;j+=10) {
             g.drawLine(0, j, 800, j);
        }

        //show the snapped point
        g.setColor(Color.BLACK);
        if ( getSnapX()>=0 &&  getSnapY()>=0 &&  getSnapX()<=800 && getSnapY()<=800) {
            // result =true;
             g.drawOval((int) ( getSnapX())-4, (int) (getSnapY()-4), 8, 8);
        }
             
        }
  
  
        public int getSnapX(){
            
            return (this.snapmousepositionx);
        }
        public int getSnapY(){
            
            return  (this.snapmousepositiony);
        }
        
        
        public void setSnapX(int snap){
            this.snapmousepositionx=(int) (snap);
            
            
        }
        public void setSnapY(int snap){
            this.snapmousepositiony=(int) (snap);
            
            
        }
 
  
 
 

public static void main(String args[]) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new DrawExample();
            
            
            
        }
    });
}



}