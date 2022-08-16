import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class DrawBending {
	
	private double Rlocal[];
	private Point midpoint;
	private double angle;
	private double length;
	private String forcetype;
	private double scale=15;
	
	DrawBending(double Rlocal[],Point midpoint,double angle,double length,String forcetype){
		this.Rlocal=Rlocal;
		this.midpoint=midpoint;
		this.angle=angle;
		this.length=length;
		this.forcetype=forcetype;
	}
	
	public void drawBending(Graphics2D g2d) {
		
		
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
		for(int i=0; i<Rlocal.length; i++) {
			
			Rlocal[i]=Math.round(Rlocal[i] * 100.0) / 100.0;
			
		System.out.println(Rlocal[i]+"bending");
			
		}
		System.out.println( );
		
		for(int i=0; i<Rlocal.length; i++) {
			
			if (forcetype.matches("UDL")) {
				
			}
			if(Rlocal[2]==0) {
				
		g2d.rotate((angle),(midpoint.x),(midpoint.y));
		Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[5]+scale);
		
		g2d.drawString(String.valueOf(Rlocal[5])+"kNm",(int) (midpoint.x+length*10+20), (int) (midpoint.y-Rlocal[5]+scale));
		g2d.draw(line);
		g2d.setTransform(old);
			}
			
			if(Rlocal[2]<0) {
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y+Rlocal[2]+scale, midpoint.x+length*10, midpoint.y-Rlocal[5]+scale);
				//g2d.draw(line);
				g2d.draw(line);
				g2d.setTransform(old);
					}
			
			if(Rlocal[5]<0) {
		 		
		 		Line2D endline = new Line2D.Double(midpoint.x+length*10, midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[5]+scale);
		 		g2d.draw(endline);
		 		g2d.setTransform(old);
			}
			if(Rlocal[2]>0) {
				
				g2d.drawString(String.valueOf(Rlocal[2])+"kNm",(int) (midpoint.x+length*10-80), (int) (midpoint.y-Rlocal[2]-60));
				g2d.drawString(String.valueOf(Rlocal[5])+"kNm",(int) (midpoint.x+length*10-20), (int) (midpoint.y-Rlocal[5]+80));
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y+Rlocal[2]+10, midpoint.x+length*10, midpoint.y-Rlocal[5]+scale);
				Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y+Rlocal[2]+scale);
				
			
				
				g2d.draw(startline);
				g2d.draw(line);
				//g2d.rotate((-angle),(midpoint.x),(midpoint.y));
				//g2d.drawString(String.valueOf(Rlocal[2])+"kNm",(int) (midpoint.x+length*10), (int) (midpoint.y-Rlocal[2]-50));
				g2d.setTransform(old);
			}
	if(Rlocal[5]>0) {
		g2d.rotate((angle),(midpoint.x),(midpoint.y));
		 		Line2D endline = new Line2D.Double(midpoint.x+length*10, midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[5]-scale);
		 		g2d.draw(endline);
		 		g2d.setTransform(old);
			}
		
		}
	
		
	}
}


