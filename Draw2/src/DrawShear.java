import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;


public class DrawShear {
	
	private double localReactions[];					//Force array
	private String forcetype;
	private Point midpoint;

	private double angle;

	private double length;
	private Point location;
	private double xStart;
	private double xEnd;
	private double memberSlope;
	private double shearSlope;
	private double x1Shear;
	private double y1Shear;
	private double x2Shear;
	private double y2Shear;
	
	DrawShear(double localReactions[],String forcetype,double angle, Point midpoint, double length, Point location, double memberSlope){
		
		this.localReactions=localReactions;
		this.angle=angle;
		this.midpoint=midpoint;
		this.length=length;
		this.forcetype=forcetype;
		this.location=location;
		xStart=midpoint.x-length*10;
		xEnd=midpoint.x+length*10;
		this.memberSlope=memberSlope;
	}
	
	public void roundLocalReactionsArray() {
		
		for(int i=0; i<localReactions.length; i++) {
			
			localReactions[i]=Math.round(localReactions[i] * 100.0) / 100.0;
			
		//System.out.println(localReactions[i]+"localReactions");
			
		}
		System.out.println( );
		
	}

	public void startLine(Graphics2D g2d) {
		
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
			if(localReactions[1]<0) {
				
				if(forcetype.matches("Point")){
					
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					
					Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y+localReactions[4]+15);

					g2d.draw(startline);	
					g2d.setTransform(old);
				}
		
			if(forcetype.matches("UDL")){
				
				Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y-localReactions[1]-15);
				//y1Shear=midpoint.y;
				g2d.draw(startline);
			  	g2d.setTransform(old);
			  	
			}
			if(forcetype.matches("None")){
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y+localReactions[4]+15);
				//y1Shear=midpoint.y;
				g2d.draw(startline);
				g2d.setTransform(old);
			}
			
			
			}
	if(localReactions[1]>0) {
		
		if(forcetype.matches("Point")) {
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			
			Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y+localReactions[1]-15);
		
			//y1Shear=midpoint.y;
			g2d.draw(startline);
			g2d.setTransform(old);
		}
	
		if(forcetype.matches("UDL")){
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y-localReactions[1]-15);
		 	//y1Shear=midpoint.y;
			g2d.draw(startline);
		  	g2d.setTransform(old);
		  	
		}			
		
		if(forcetype.matches("None")){
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			Line2D startline = new Line2D.Double(xStart,  midpoint.y, xStart, midpoint.y+localReactions[4]-15);
			
			//y1Shear=midpoint.y;
			g2d.draw(startline);
			g2d.setTransform(old);
		}
		
		
	
	}
	


	
	}
	public void line(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
			if(localReactions[1]<0) {
				
				if(forcetype.matches("Point")){
					
					g2d.rotate((angle),(midpoint.x),(midpoint.y));
					Line2D line = new Line2D.Double(location.x,  location.y, location.x,  location.y-10);
					g2d.draw(line);	
					g2d.setTransform(old);
				}
		
			if(forcetype.matches("UDL")){
				
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-localReactions[1]-15, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
				
				
				g2d.drawString(String.valueOf(localReactions[1])+"kN",midpoint.x-50, (int) (midpoint.y-localReactions[1]+100));
				g2d.drawString(String.valueOf(localReactions[4])+"kN",midpoint.x+10, (int) (midpoint.y-localReactions[4]+15));
				y1Shear=midpoint.y-localReactions[1]-15;
				y2Shear=midpoint.y+localReactions[4]+15;
				g2d.draw(line);	 
			  	g2d.setTransform(old);
			  	
			}
			if(forcetype.matches("None")){
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-localReactions[1]+15, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
				
				g2d.drawString(String.valueOf(localReactions[1])+"kN",midpoint.x-17, (int) (midpoint.y-localReactions[1]+15));
				y1Shear=midpoint.y-localReactions[1]+15;
				y2Shear=midpoint.y+localReactions[4]+15;
				g2d.draw(line);	 	
				g2d.setTransform(old);
				
			}
			
			
			}
	if(localReactions[1]>0) {
		
		if(forcetype.matches("Point")) {
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			
			Line2D line = new Line2D.Double(midpoint.x-length*10, midpoint.y+localReactions[1]-15, location.x,  location.y+localReactions[1]-15);
			
			Line2D midline = new Line2D.Double(location.x,  location.y+localReactions[1]-15, location.x,  location.y+10);
			
			Line2D line2 = new Line2D.Double(location.x,  location.y+10, location.x+length*10,  location.y+10);
			
			
			g2d.drawString(String.valueOf(localReactions[1])+"kN",(int) (midpoint.x-length*10), (int) (midpoint.y-localReactions[1]-15));
			g2d.drawString(String.valueOf(localReactions[4])+"kN",(int) (midpoint.x+length*10), (int) (midpoint.y+localReactions[4]+15));
			
			g2d.draw(line);	
			g2d.draw(midline);
			g2d.draw(line2);
			
			
			
			g2d.setTransform(old);
		}
	
		if(forcetype.matches("UDL")){
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-localReactions[1]-15, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
			
			g2d.drawString(String.valueOf(localReactions[1])+"kN",midpoint.x-60, (int) (midpoint.y-localReactions[1]-15));
			g2d.drawString(String.valueOf(localReactions[4])+"kN",midpoint.x+60, (int) (midpoint.y+localReactions[4]+15));
			y1Shear=midpoint.y-localReactions[1]-15;
			y2Shear=midpoint.y+localReactions[4]+15;
			g2d.draw(line);	 

		  	g2d.setTransform(old);
		  	
		}			
		
		if(forcetype.matches("None")){
			
			g2d.rotate((angle),(midpoint.x),(midpoint.y));
			Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y-localReactions[1]-15, midpoint.x+length*10, midpoint.y+localReactions[4]-15);
			
			
			g2d.drawString(String.valueOf(localReactions[1])+"kN",midpoint.x-17, (int) (midpoint.y-localReactions[1]+15));
			y1Shear=midpoint.y-localReactions[1]-15;
			y2Shear=midpoint.y+localReactions[4]-15;
		
			g2d.draw(line);
			g2d.setTransform(old);
		}
		
		
	
	}
		
	}
	public void endLine(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		
		if(localReactions[4]>0) {
			
			if(forcetype.matches("Point")) {
				
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				
				
				Line2D endline = new Line2D.Double(location.x+length*10,  location.y+10, location.x+length*10, location.y);
			
			
				g2d.draw(endline);
				
				g2d.setTransform(old);
			}
	
			if(forcetype.matches("UDL")){
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
		 	
			
				g2d.draw(endline);
			  	g2d.setTransform(old);
			  	
			}
				
			if(forcetype.matches("None")) {
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));

				
				//(localReactions[4]>0) {
			 		
						Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
						g2d.draw(endline);
//				 	}else {
//				 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]-15);
//				 		g2d.draw(endline);
//				 	}
						
						
				 	
				
				g2d.setTransform(old);
			}
			
			
		}

		if(localReactions[4]<0) {
			
			if(forcetype.matches("Point")) {
				
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				
				
				Line2D endline = new Line2D.Double(location.x+length*10,  location.y+10, location.x+length*10, location.y);
			
			
				g2d.draw(endline);
				
				g2d.setTransform(old);
			}
	
			if(forcetype.matches("UDL")){
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));
				Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
		 	
				
				g2d.draw(endline);
			  	g2d.setTransform(old);
			  	
			}
				
			if(forcetype.matches("None")) {
				
				g2d.rotate((angle),(midpoint.x),(midpoint.y));

				
				//(localReactions[4]>0) {
			 		
					//	Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
						//g2d.draw(endline);
//				 	}else {
				 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]-15);
				 		
				 		g2d.draw(endline);
//				 	}
				 	
				
				g2d.setTransform(old);
			}
			
			
		}
		


		
		
	}
	public void drawShear(Graphics2D g2d) {

		
		roundLocalReactionsArray();
		startLine(g2d);
		line(g2d);
		endLine(g2d);
		shearInflextion();
//		g2d.rotate((angle),(midpoint.x),(midpoint.y));
//		Line2D line = new Line2D.Double(midpoint.x-length*10,  midpoint.y, midpoint.x+length*10, midpoint.y);
//		
//		
//		g2d.drawString(String.valueOf(localReactions[1])+"kN",midpoint.x-17, (int) (midpoint.y-localReactions[1]+15));
//		
//	
//		g2d.draw(line);
//		
//		for(int i=0; i<localReactions.length; i++) {
//			
//			
//			
//			
//			
//			
//			
//	if(localReactions[1]>0) {
//		
//		
//		
//		if(forcetype.matches("UDL")){
//			
//			g2d.rotate((angle),(midpoint.x),(midpoint.y));
//			Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
//	 	
//			
//			g2d.draw(endline);
//		  	g2d.setTransform(old);
//		  	
//		}
//			
//			if(forcetype.matches("Point")) {
//				
//				
//				g2d.rotate((angle),(midpoint.x),(midpoint.y));
//				
//				
//				Line2D endline = new Line2D.Double(location.x+length*10,  location.y+10, location.x+length*10, location.y);
//			
//			
//				g2d.draw(endline);
//				
//				g2d.setTransform(old);
//			}else {
//			
//			g2d.rotate((angle),(midpoint.x),(midpoint.y));
//			
//			
//			
//			if(localReactions[4]>0) {
//		 		
//					Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]+15);
//					g2d.draw(endline);
//			 	}else {
//			 		Line2D endline = new Line2D.Double(midpoint.x+length*10,  midpoint.y, midpoint.x+length*10, midpoint.y+localReactions[4]-15);
//			 		g2d.draw(endline);
//			 	}
//			 	
//			
//			g2d.setTransform(old);
//		}
//		
//		
//	}
	


	
		
		

		}
public void shearInflextion() {
	System.out.println("Slope"+","+memberSlope);
	System.out.println(shearSlope);
	System.out.println(calculateYinterceptMember()/10/2);
	System.out.println(calculateYinterceptShear()/10/2);
	
}

public double calculateYinterceptMember() {
	if( memberSlope==Integer.MAX_VALUE) {
		return 0;
	}else {
	return (double)(midpoint.y)-(double)(midpoint.x)*memberSlope;
	}
}
public double calculateYinterceptShear() {
	if( shearSlope==Integer.MAX_VALUE) {
		return 0;
	}else {
	return (double)(y1Shear)-(double)(xStart)*calculateShearSlope();
	}
}
public double calculateShearSlope() {
	System.out.println(xEnd-xStart);
	if (xEnd-xStart != 0) {
		return (double)(y2Shear-y1Shear)/(double)(xEnd-xStart);
			
}
	return Integer.MAX_VALUE;
	
}
}
