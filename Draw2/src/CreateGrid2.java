import java.awt.Color;
import java.awt.Graphics2D;

public class CreateGrid2 {
	 int vpw = 600;//viewport width
     int vph = 600;//viewport height
     int gw = 20;//grid width
     int gh = 20;//grid height
	
     int npx, npy;//current snap coords




public void create(int x, int y) {
	
	int xn = x, yn = y;
     int mx = xn % gw, my = yn % gh;
     if (mx<gw/2) npx = xn - mx;
     else npx = xn + (gw-mx);
     if (my<gh/2) npy = yn - my;
     else npy = yn + (gh-my);
    
	
}
public void drawGrid(Graphics2D g) {
	 g.clearRect(0, 0, vpw, vph);
     g.setColor(Color.DARK_GRAY);
     
     //grid vertical lines
     for (int i=gw;i<vpw;i+=gw) {
          g.drawLine(i, 0, i, vph);
     }
     //grid horizontal lines
     for (int j=gh;j<vph;j+=gh) {
          g.drawLine(0, j, vpw, j);
     }
     //show the snapped point
     g.setColor(Color.WHITE);
     if (npx>=0 && npy>=0 && npx<=vpw && npy<=vph) {
          g.drawOval(npx-4, npy-4, 8, 8);
     }
}
}