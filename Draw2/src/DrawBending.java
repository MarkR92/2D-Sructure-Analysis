import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class DrawBending {
	
	private double Rlocal[];
	private Point midpoint;
	private double angle;
	private String forcetype;
	private double scale=2;
	private double x1;
	private double x2;
	
	DrawBending(double Rlocal[],Point midpoint,double angle,double length,String forcetype){
		this.Rlocal=Rlocal;
		this.midpoint=midpoint;
		this.angle=angle;
		this.forcetype=forcetype;
		x1= midpoint.x-length*10;
		x2= midpoint.x+length*10;
	}
	public void roundBending() {
		
		for(int i=0; i<Rlocal.length; i++) {
			
			Rlocal[i]=Math.round(Rlocal[i] * 100.0) / 100.0;
			
		System.out.println(Rlocal[i]+"bending");
			
		}
		System.out.println( );
	}
	public void startLine(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
				if(Rlocal[2]==0) {
			
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
				//	Line2D line = new Line2D.Double(x1,  midpoint.y, x2, midpoint.y-Rlocal[5]*scale);
			
				//	g2d.drawString(String.valueOf(Rlocal[5])+"kNm",(int) (x2+20), (int) (midpoint.y-Rlocal[5]*scale));
				//	g2d.draw(line);
					g2d.setTransform(old);
				}
				
				if(Rlocal[2]<0) {
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					//Line2D line = new Line2D.Double(x1,  midpoint.y+Rlocal[2]*scale, x2, midpoint.y-Rlocal[5]*scale);
					//g2d.draw(line);
				//	g2d.draw(line);
					g2d.setTransform(old);
						}
				if(Rlocal[2]>0) {
					
					g2d.drawString(String.valueOf(Rlocal[2])+"kNm",(int) (x2-80), (int) (midpoint.y-Rlocal[2]-60));
					g2d.drawString(String.valueOf(Rlocal[5])+"kNm",(int) (x2-20), (int) (midpoint.y-Rlocal[5]+80));
					
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					Line2D startline = new Line2D.Double(x1,  midpoint.y, x1, midpoint.y+Rlocal[2]*scale);
				
					g2d.draw(startline);
					g2d.setTransform(old);
				}
		
	}
	public void line(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
				if(Rlocal[2]==0) {
			
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					Line2D line = new Line2D.Double(x1,  midpoint.y, x2, midpoint.y-Rlocal[5]*scale);
			
					g2d.drawString(String.valueOf(Rlocal[5])+"kNm",(int) (x2+20), (int) (midpoint.y-Rlocal[5]*scale));
					g2d.draw(line);
					g2d.setTransform(old);
				}
				
				if(Rlocal[2]<0) {
					
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					Line2D line = new Line2D.Double(x1,  midpoint.y+Rlocal[2]*scale, x2, midpoint.y-Rlocal[5]*scale);
					g2d.draw(line);
					g2d.setTransform(old);
						}
				if(Rlocal[2]>0) {
		
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					Line2D line = new Line2D.Double(x1,  midpoint.y+Rlocal[2]*scale, x2, midpoint.y-Rlocal[5]*scale);
					
					
				
					
				
					g2d.draw(line);
					g2d.setTransform(old);
				}
	}
	public void drawBending(Graphics2D g2d) {
		
		
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
		roundBending();
		startLine(g2d);
		line(g2d);
		for(int i=0; i<Rlocal.length; i++) {
			
			if (forcetype.matches("UDL")) {
				
			}
			
			
			if(Rlocal[5]<0) {
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
		 		Line2D endline = new Line2D.Double(x2, midpoint.y, x2, midpoint.y-Rlocal[5]*scale);
		 		g2d.draw(endline);
		 		g2d.setTransform(old);
			}
			
	if(Rlocal[5]>0) {
		g2d.rotate((angle),(midpoint.x),(midpoint.y));
		 		Line2D endline = new Line2D.Double(x2, midpoint.y, x2, midpoint.y-Rlocal[5]*scale);
		 		g2d.draw(endline);
		 		g2d.setTransform(old);
			}
		
		}
	
		
	}
}


