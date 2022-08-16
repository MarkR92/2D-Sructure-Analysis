import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * @deprecated
 * @see tjacobs.ui.mod.ZoomPanel
 * @author tjacobs
 *
 */
@SuppressWarnings("serial")
public class Zoomable
			extends JComponent 
			implements MouseWheelListener {

	double zoom = 1.0;
	
	public Zoomable() {
		setLayout(new GridLayout(1,1));
		addMouseWheelListener(this);
	}
	
	public Zoomable(Component c) {
		this();
		c.addMouseWheelListener(this);
		add(c);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int ticks = e.getWheelRotation();
		zoom *= Math.pow(1.2, ticks);
		repaint();
	}
	
	public void paintChildren(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(zoom, zoom);
		super.paintChildren(g2);
	}
	
	public static void main(String[] args) throws IOException {
		JLabel hello = new JLabel("Hello");
		Zoomable zm = new Zoomable(hello);
			}
}