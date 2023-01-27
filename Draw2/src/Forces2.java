import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Forces2 {
	
	private double magnitude;
	private String forcetype;
	private double direction;
	private Point location;
	private boolean selected =false;
	private boolean highlighted =false;
	
	private double memberangle;
	private Point start;
	private Point end;
	private double length;
	
	Forces2(double magnitude,double direction,Point location, String forcetype)
	{
		this.magnitude=magnitude;
		this.direction=direction;
		this.forcetype=forcetype;
		this.location=location;
		
		
	}
	Forces2(double magnitude,double direction, Point location,Point start,Point end, String forcetype,double memberangle,double length)
	{
		this.magnitude=magnitude;
		this.direction=direction;
		this.forcetype=forcetype;
		this.location=location;
		this.start=start;
		this.end=end;
		this.memberangle=memberangle;
		this.length=length;
		
		
	}
	
	
	
	public void drawForce(Graphics2D g) {
		
		
		if(forcetype.equals("Point"))
		{
			drawPointLoad(g);
		 	
		}
		else if(forcetype.equals("Moment"))
		{
			drawMoment(g);
		}
		else if(forcetype.equals("UDL"))
		{
			drawUDL(g);
		}
	}
	public void drawPointLoad(Graphics2D g) {
		//change sign is used to rotate positive/negative point load 180 degree
		//1 points the graphic up and -1 points the graphic down
		
		double changesign = ( magnitude >= 0 ) ? Math.PI : 2*Math.PI;

	    AffineTransform old = g.getTransform();
		
		g.rotate(-Math.toRadians(direction)+(changesign),(location.x),(location.y));

		g.setColor( Color.GREEN);
		g.fillPolygon(new int[] {(location.x), (location.x)+5, (location.x)-5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
		g.drawLine(location.x, location.y+10, location.x,location.y+30);
		
		g.setColor( Color.BLACK);
		g.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
		g.drawLine(location.x, location.y+10, location.x, location.y+30);
		
		
		
	 	if (highlighted||selected) {
	 		
	 		    g.drawRect((location.x-5), (location.y+10), 10,20);

	 		  
			 	String mag=String.valueOf(magnitude); 
				g.setColor( Color.black);
			 	g.drawString(mag +"kN",location.x+10,location.y-20);

		
			 	}
	 	g.setTransform(old);
		
	}
	public void drawMoment(Graphics2D gm) {
		
		
		if (magnitude<=0) {
 			
 			AffineTransform old = gm.getTransform();
			
 			//Rotate graphic so it is perpendicular to beam
			gm.rotate(-Math.toRadians(direction),(location.x),(location.y));
			
			gm.setColor( Color.GREEN);
			gm.fillPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y+10), (location.y)+15, (location.y)+20}, 3);
			gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
			//gm.drawLine(location.x, location.y+10, location.x, location.y+30);
			
			gm.setColor( Color.BLACK);
			gm.drawPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y+10), (location.y)+15, (location.y)+20}, 3);
			gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
//			
			
			if (highlighted||selected)
			{
				
				gm.drawRect((location.x), (location.y+10), 10,10);
				gm.setColor( Color.black);
				gm.drawString(String.valueOf(magnitude) +"kNm",location.x+15,location.y+20);
			
			}

			gm.setTransform(old);
 	
 	}
 	
		else {
		
		AffineTransform old = gm.getTransform();
		
		
		gm.setColor( Color.GREEN);
		gm.fillPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y-20), (location.y)-15, (location.y)-10}, 3);
		gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
		//gm.drawLine(location.x, location.y+10, location.x, location.y+30);
		
		gm.setColor( Color.BLACK);
		gm.drawPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y-20), (location.y)-15, (location.y)-10}, 3);
		gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
	 	
	 if (highlighted||selected) {
		    gm.drawRect((location.x), (location.y-20), 10,10);
			gm.setColor( Color.black);
			gm.drawString(String.valueOf(magnitude) +"kNm",location.x+15,location.y-10);
		
		}
	 	
	 	gm.setTransform(old);
	 	
	 	}
		
	}
	public void drawUDL(Graphics2D g) {
		
			//if (magnitude>=0) {
		double rotation = ( magnitude >= 0 ) ? 2*Math.PI : Math.PI;
		double changesign = ( magnitude >= 0 ) ? 1 : -1;
			AffineTransform old = g.getTransform();
		
		 	g.setColor( Color.GREEN);
			
		 	//Create arrow at midpoint
		 	g.rotate((memberangle+rotation),(location.x),(location.y));
			g.drawLine(location.x, location.y-10, location.x, location.y-30);
			g.fillPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)-10, (location.y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((memberangle+rotation),(start.x),(start.y));
			g.fillPolygon(new int[] {(start.x),   (start.x)-5,   (start.x)+5}, new int[] {(start.y), (start.y)-10, (start.y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((memberangle+rotation),(end.x),(end.y));
			g.fillPolygon(new int[] {(end.x),   (end.x)-5,   (end.x)+5}, new int[] {(end.y), (end.y)-10, (end.y)-10}, 3);
			g.setTransform(old);
			
			g.setColor( Color.BLACK);
			
		 	
			//Create arrow at midpoint
		 	g.rotate((memberangle+rotation),(location.x),(location.y));
		 	g.drawLine(location.x, location.y-10, location.x, location.y-30);
			g.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)-10, (location.y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((memberangle+rotation),(start.x),(start.y));
		    g.drawLine(start.x, start.y-10, start.x, start.y-30);
			g.drawPolygon(new int[] {(start.x), (start.x)-5, (start.x)+5}, new int[] {(start.y), (start.y)-10, (start.y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((memberangle+rotation),(end.x),(end.y));
			g.drawLine(end.x, end.y-10, end.x, end.y-30);
			g.drawPolygon(new int[] {(end.x), (end.x)-5, (end.x)+5}, new int[] {(end.y), (end.y)-10, (end.y)-10}, 3);		
			g.setTransform(old);
			

		 	
			g.rotate((memberangle),(end.x),(end.y));
		 	Rectangle2D rect  = new Rectangle2D.Double(end.x, end.y-30*changesign, length*10*2, 1);
		 	g.draw(rect);

			 if (highlighted||selected) {
				 
				 
				  g.drawRect((location.x-5), (location.y+10), 10,20);

		 		  
				 	String mag=String.valueOf(magnitude); 
					g.setColor( Color.black);
				 	g.drawString(mag +"kN",location.x+10,location.y-20);
				 	
		}
		 	g.setTransform(old);
		 	
		 
			
		//}
			
//			else {
//			
//			AffineTransform old = g.getTransform();
//
//		 	g.setColor( Color.GREEN);
//		 
//		 	//Create arrow at midpoint
//		 	g.rotate((memberangle),(location.x),(location.y));
//			g.drawLine(location.x, location.y+10, location.x, location.y+30);
//			g.fillPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
//			g.setTransform(old);
//			
//			//Create arrow at endpoint1
//			g.rotate((memberangle),(start.x),(start.y));
//			g.fillPolygon(new int[] {(start.x),   (start.x)-5,   (start.x)+5}, new int[] {(start.y), (start.y)+10, (start.y)+10}, 3);
//			g.setTransform(old);
//			
//			//Create arrow at endpoint2
//			g.rotate((memberangle),(end.x),(end.y));
//			g.fillPolygon(new int[] {(end.x),   (end.x)-5,   (end.x)+5}, new int[] {(end.y), (end.y)+10, (end.y)+10}, 3);
//			g.setTransform(old);
//			
//			g.setColor( Color.BLACK);
//			
//		 	
//		 	//Create arrow at midpoint
//			g.rotate((memberangle),(location.x),(location.y));
//		 	g.drawLine(location.x, location.y+10, location.x, location.y+30);
//			g.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
//			g.setTransform(old);
//			
//			//Create arrow at endpoint1
//			g.rotate((memberangle),(start.x),(start.y));
//		    g.drawLine(start.x, start.y+10, start.x, start.y+30);
//			g.drawPolygon(new int[] {(start.x), (start.x)-5, (start.x)+5}, new int[] {(start.y), (start.y)+10, (start.y)+10}, 3);
//			g.setTransform(old);
//			
//			//Create arrow at endpoint2
//			g.rotate((memberangle),(end.x),(end.y));
//			g.drawLine(end.x, end.y+10, end.x, end.y+30);
//			g.drawPolygon(new int[] {(end.x), (end.x)-5, (end.x)+5}, new int[] {(end.y), (end.y)+10, (end.y)+10}, 3);		
//			g.setTransform(old);
//			
//			g.rotate((memberangle),(end.x),(end.y));
//		 	Rectangle2D rect  = new Rectangle2D.Double(end.x, end.y+30, length*10*2, 1);
//		 	g.draw(rect);
//		 	g.setTransform(old);
//			}
		
		}
	public double getMagnitude()
	{
		return magnitude;
	}
	public void setMagnitude(double magnitude)
	{
		this.magnitude=magnitude;
		}
	public double getDirection()
	{
		return direction;
	}
	public void setDirection(double direction)
	{
		this.direction=direction;
	}
	public String getForceType()
	{
		return forcetype;
	}
	public void setHighlighted(boolean highlighted) {
	    this.highlighted = highlighted;
	}
	boolean isHighlighted() {
	    return highlighted;
	}
	public void setSelected(boolean selected) {
	    this.selected = selected;
	}
	boolean isSelected() {
	    return selected;
	}
	
	
	
	public Shape getPointBounds() {
		double changesign = ( magnitude >= 0 ) ? Math.PI : 2*Math.PI;

		Double rect = new Rectangle2D.Double(location.getX()-5, location.getY()+10, 10,20);
		AffineTransform at = AffineTransform.getRotateInstance(-Math.toRadians(direction)+changesign, location.getX(), location.getY());
		Shape rotatedRect = at.createTransformedShape(rect);

		return rotatedRect;
	
	}
public Shape getUDLBounds() {
		

		double changesign = ( magnitude >= 0 ) ? Math.PI : 2*Math.PI;

		Double rect = new Rectangle2D.Double(end.x, end.y-30*changesign, length*10*2, 1);
		AffineTransform at = AffineTransform.getRotateInstance(-Math.toRadians(direction)+changesign, location.getX(), location.getY());
		Shape rotatedRect = at.createTransformedShape(rect);

		return rotatedRect;

		
	}

}
