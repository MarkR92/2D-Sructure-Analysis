import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class FPanel extends JPanel {

private Dimension preferredSize = new Dimension(400, 400);    
private Rectangle2D[] rects = new Rectangle2D[50];

public static void main(String[] args) {        
    JFrame jf = new JFrame("test");
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setSize(400, 400);
    jf.add(new JScrollPane(new FPanel()));
    jf.setVisible(true);
}    

public FPanel() {
    // generate rectangles with pseudo-random coords
    for (int i=0; i<rects.length; i++) {
        rects[i] = new Rectangle2D.Double(
                Math.random()*.8, Math.random()*.8, 
                Math.random()*.2, Math.random()*.2);
    }
    // mouse listener to detect scrollwheel events
    addMouseWheelListener(new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent e) {
            updatePreferredSize(e.getWheelRotation(), e.getPoint());
        }
    });
}

private void updatePreferredSize(int n, Point p) {
	double d = (double) n * 1.08;
    d = (n > 0) ? 1 / d : -d;

    int w = (int) (getWidth() * d);
    int h = (int) (getHeight() * d);
    preferredSize.setSize(w, h);
System.out.println(w+","+h);
    int offX = (int)(p.x * d) - p.x;
    int offY = (int)(p.y * d) - p.y;
    setLocation(getLocation().x-offX,getLocation().y-offY);

    getParent().doLayout();
}

public Dimension getPreferredSize() {
    return preferredSize;
}

private Rectangle2D r = new Rectangle2D.Float();
public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.red);
    int w = getWidth();
    int h = getHeight();
    for (Rectangle2D rect : rects) {
        r.setRect(rect.getX() * w, rect.getY() * h, 
                rect.getWidth() * w, rect.getHeight() * h);
        ((Graphics2D)g).draw(r);
    }       
  }
}