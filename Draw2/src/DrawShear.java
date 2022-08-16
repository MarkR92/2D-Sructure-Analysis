import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;


public class DrawShear {
	
	private double Rlocal[];					//Force array
	private String forcetype;
	private Point midpoint;

	private double angle;

	private double length;
	private Point location;
	
	DrawShear(double Rlocal[],String forcetype,Point beamstart,Point beamend, int membernumber,double angle, Point midpoint, double slope, double length, Point location){
		
		this.Rlocal=Rlocal;
		this.angle=angle;
		this.midpoint=midpoint;
		this.length=length;
		this.forcetype=forcetype;
		this.location=location;
	}
	
	public void roundForceArray() {
		
		for(int i=0; i<Rlocal.length; i++) {
			
			Rlocal[i]=Math.round(Rlocal[i] * 100.0) / 100.0;
			
		System.out.println(Rlocal[i]);
			
		}
		System.out.println( );
		
	}
	public void drawShear(Graphics2D g2d) {

		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
		roundForceArray();
		
		
		
		
		for(int i=0; i<Rlocal.length; i++) {
			
			
			if(Rlocal[1]<0) {
			
			if(forcetype.matches("Point")) {
				
				System.out.println("Point"+",");
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D line = new Line2D.Double(location.x,  location.y, location.x,  location.y-10);
				g2d.draw(line);	
			}
			if(forcetype.matches("UDL")){
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]-15, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
				Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y-Rlocal[1]-15);
//			 	
				
				g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-50, (int) (midpoint.y-Rlocal[1]+100));
				g2d.drawString(String.valueOf(Rlocal[4])+"kN",midpoint.x+10, (int) (midpoint.y-Rlocal[4]+15));
				
				g2d.draw(line);	 
				g2d.draw(startline);
			  	g2d.setTransform(old);
			  	
			}else{
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]+15, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
				Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y+Rlocal[4]+15);
			 	
				if(Rlocal[4]>0) {
			 		
			 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
			 		g2d.draw(endline);
			 		
			 	}else {
			 		
			 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y-Rlocal[4]-15);
			 		g2d.draw(endline);
			 	}
				g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-17, (int) (midpoint.y-Rlocal[1]+15));
				
				g2d.draw(startline);
				g2d.draw(line);	 	
				g2d.setTransform(old);
			}
			
			
			
			}
	if(Rlocal[1]>0) {
		
		
		
		if(forcetype.matches("UDL")){
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]-15, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
			Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y-Rlocal[1]-15);
		 	Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
	 	
			g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-60, (int) (midpoint.y-Rlocal[1]-15));
			g2d.drawString(String.valueOf(Rlocal[4])+"kN",midpoint.x+60, (int) (midpoint.y+Rlocal[4]+15));
			g2d.draw(line);	 
			g2d.draw(startline);
			g2d.draw(endline);
		  	g2d.setTransform(old);
		  	
		}else{
			
			if(forcetype.matches("Point")) {
				
				System.out.println("Point"+",");
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				
				Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y+Rlocal[1]-15);
				Line2D line = new Line2D.Double(midpoint.x-length*10, midpoint.y+Rlocal[1]-15, location.x,  location.y+Rlocal[1]-15);
				
				Line2D midline = new Line2D.Double(location.x,  location.y+Rlocal[1]-15, location.x,  location.y+10);
				
				Line2D line2 = new Line2D.Double(location.x,  location.y+10, location.x+length*10,  location.y+10);
				
				
				Line2D endline = new Line2D.Double(location.x+length*10,  location.y+10, location.x+length*10, location.y);
			
				g2d.drawString(String.valueOf(Rlocal[1])+"kN",(int) (midpoint.x-length*10), (int) (midpoint.y-Rlocal[1]-15));
				g2d.drawString(String.valueOf(Rlocal[4])+"kN",(int) (midpoint.x+length*10), (int) (midpoint.y+Rlocal[4]+15));
				
				g2d.draw(line);	
				g2d.draw(midline);
				g2d.draw(line2);
				g2d.draw(endline);
				g2d.draw(startline);
				g2d.setTransform(old);
			}else {
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			
			Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-Rlocal[1]-15, midpoint.x+length*10, midpoint.y+Rlocal[4]-15);
			Line2D startline = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x-length*10, midpoint.y+Rlocal[4]-15);
			
			
			g2d.drawString(String.valueOf(Rlocal[1])+"kN",midpoint.x-17, (int) (midpoint.y-Rlocal[1]+15));
			
			
			if(Rlocal[4]>0) {
		 		
					Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]+15);
					g2d.draw(endline);
			 	}else {
			 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+Rlocal[4]-15);
			 		g2d.draw(endline);
			 	}
			g2d.draw(line);	 	
			g2d.draw(startline);
			g2d.setTransform(old);
		}
		
		
	}
	}


	}
		
		

		}

}
//}
