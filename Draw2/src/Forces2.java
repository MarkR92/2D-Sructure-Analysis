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
	
	Forces2(double magnitude,double direction,Point location, String forcetype)
	{
		this.magnitude=magnitude;
		this.direction=direction;
		this.forcetype=forcetype;
		this.location=location;
		
		
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
//			gm.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
//			gm.drawLine(location.x, location.y+10, location.x, location.y+30);
			
//			if (highlighted||selected) {
//			//gm.drawRect(location.x-5, location.y, 10,30);
//			//gp.drawString(location.toString(), location.x-5, location.y-5);
//			//System.out.println(location.toString());
//			gm.setColor(Color.red);
//			
//			}

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
	 	
//	 	if (highlighted||selected) {
//	 	//gm.drawRect(location.x-5, location.y-30, 10,30);
//	 	//gp.drawString(location.toString(), location.x-30, location.y-30);
//		//System.out.println(location.toString().);
//	 		gm.setColor(Color.red);
//	 	}
	 	
	 	gm.setTransform(old);
	 	
	 	}
		
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
		
		
		//if (magnitude<=0) {
			//System.out.println(location.getX() +" , " +location.getY());
		double changesign = ( magnitude >= 0 ) ? Math.PI : 2*Math.PI;

		Double rect = new Rectangle2D.Double(location.getX()-5, location.getY()+10, 10,20);
		AffineTransform at = AffineTransform.getRotateInstance(-Math.toRadians(direction)+changesign, location.getX(), location.getY());
		Shape rotatedRect = at.createTransformedShape(rect);

		return rotatedRect;
		
		//}
//		else {
//			
//			Double rect = new Rectangle2D.Double(location.getX()-5, location.getY()+10, 10,20);
//			AffineTransform at = AffineTransform.getRotateInstance(direction, location.getX(), location.getY());
//			Shape rotatedRect = at.createTransformedShape(rect);
//			
//			return rotatedRect;
//				
//			}
		
	}

}
