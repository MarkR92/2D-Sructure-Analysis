import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DrawShear {
	
	private double Rlocal[];					//Displacement vector
	private Point beamstart;
	private Point beamend;
	private Point midpoint;

	private double angle;

	private double length;
	
	
	DrawShear(double Rlocal[],Point beamstart,Point beamend, int membernumber,double angle, Point midpoint, double slope, double length){
		
		this.Rlocal=Rlocal;
		this.beamstart=beamstart;
		this.beamend=beamend;
		
		this.angle=angle;
		this.midpoint=midpoint;
	
		this.length=length;
		
	}
	
	public void drawShear(Graphics2D g2d) {

		AffineTransform old = g2d.getTransform();
		 
		
//		
		g2d.setColor( Color.BLACK);
		for(int i=0; i<Rlocal.length; i++) {
		
			Rlocal[i]=Math.round(Rlocal[i] * 100.0) / 100.0;
			
		System.out.println(Rlocal[i]);
			
		}
		System.out.println( );
		for(int i=0; i<Rlocal.length; i++) {
			
	if(Rlocal[1]<0) {
		
		
		g2d.rotate((angle),(midpoint.x),(midpoint.y));
		
		g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-17, (int) (midpoint.y-Rlocal[1]+15));
	 	
	 	Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]+15, midpoint.x+length*10, midpoint.y-Rlocal[1]+15);
	 	Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y-Rlocal[1]+15);
	 	
	 	if(Rlocal[4]>0) {
	 		
	 	Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
	 	g2d.draw(endline);
	 	}else {
	 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[4]-15);
	 		g2d.draw(endline);
	 	}
	 	
	 	g2d.draw(line);
	 	g2d.draw(startline);
	 	
	  	g2d.setTransform(old);
	 	
	}
	
if(Rlocal[1]>0) {
		
	g2d.rotate((angle),(midpoint.x),(midpoint.y));
 	Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]-15, midpoint.x+length*10, midpoint.y-Rlocal[1]-15);
 	Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y-Rlocal[1]-15);
 	g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-15, (int) (midpoint.y-Rlocal[1]-5));
 	//Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]-10);
 	if(Rlocal[4]>0) {
 		
	 	Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[4]-15);
	 g2d.draw(endline);
	 	}else {
	 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]-15);
	 		g2d.draw(endline);
	 	}
 	
 	g2d.draw(line);
 	g2d.draw(startline);
 	//g2d.draw(endline);
  	g2d.setTransform(old);
		
}
//	if(i==4) {
//	g2d.rotate((angle),(midpoint.x),(midpoint.y));
//	
//	//g2d.drawLine(midpoint.x-(int)length*10, midpoint.y-(int)Rlocal[1]*5, (int)length*10*2,  (int)Rlocal[1]*5);
// 	Rectangle2D rect  = new Rectangle2D.Double(midpoint.x-length*10, midpoint.y, length*10*2, Rlocal[i]-10);
// 	g2d.draw(rect);
// 	g2d.setTransform(old);	
		
			//if (i ==1) {
	}
		
		//create parallel line
//		g2d.rotate((angle),(midpoint.x),(midpoint.y));
//		
//		//g2d.drawLine(midpoint.x-(int)length*10, midpoint.y-(int)Rlocal[1]*5, (int)length*10*2,  (int)Rlocal[1]*5);
//	 	Rectangle2D rect  = new Rectangle2D.Double(midpoint.x-length*10, midpoint.y-Rlocal[1]*5, length*10*2, 1);
//	 	g2d.draw(rect);
//	 	g2d.setTransform(old);
//	 
//		
//		//Create arrow at endpoint1
//		g2d.rotate((angle),(beamend.x),(beamend.y));
//	    g2d.drawLine(beamend.x, beamend.y, beamend.x, beamend.y-(int) Rlocal[2]*5);
//		g2d.setTransform(old);
//		
//		//Create arrow at endpoint2
////		g2d.rotate((angle),(beamstart.x),(beamstart.y));
////		g2d.drawLine(beamstart.x,beamstart.y-10,beamstart.x, beamstart.y-(int) Rlocal[i]*5);
////		g2d.setTransform(old);
//			//}
//			
//			
//			
//				g2d.rotate((angle),(beamstart.x),(beamstart.y));
//				g2d.drawLine(beamstart.x,beamstart.y-10,beamstart.x, beamstart.y-(int) Rlocal[4]*5);
//				g2d.setTransform(old);
			
		}

}
//}
