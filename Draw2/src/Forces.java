import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;



public class Forces implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1870926992857609535L;

	private double magnitude;
	
	private String type;
	private String direction;
	private String direction2;
	
	private int beamnumber;
	
	private boolean highlighted;
	private boolean selected;
	
	private Point firstlocation;
	private Point location;
	
	private double rotation;
	
	private double angle;
	private Point start;
	private Point end;
	
	public Forces(double magnitude, String type, String direction, String direction2, int beamnumber, Point firstlocation, double angle, Point start, Point end) {
		
		this.magnitude=magnitude;
		this.type = type;
		this.direction= direction;
		this.direction2= direction2;
		this.beamnumber= beamnumber;
		this.firstlocation= firstlocation;
		this.angle = angle;
		highlighted = false;
		selected = false;
		
		this.start=start;
		this.end=end;
	}
	
	public double getAngle() {
		return angle;
		
	}
	public double setAngle() {
		
		angle = angle - (Math.PI)/2;
		return angle;
		
	}
	public double getMagnitude() {
		 
		return magnitude;
		}
	public String getDirection2() {
		 
		return direction2;
		}
	
	public double setMagnitude() {
		return magnitude;
		}
	
	public String getType() {
		return type;
		}
	
	public Point getLocation() {

		if (location == null) {
			 this.location=firstlocation;	
		}
	
			return this.location;
	}
	
	public Point setLocation(int xx, int yy) {
		
		
		Point location = new Point(xx,yy);
		this.location=location;
		return location;

			
		}
	


	public int getNumber() {
		return beamnumber;
		}
	
	public void drawPointLoad(Graphics2D gp) {
	getLocation();
	//JLabel label = new JLabel(" ");
	if(direction2.matches("Parallel")) {
			rotation= -Math.PI/2;
		}
	 		if (direction == "Up") {
	 			
	 			AffineTransform old = gp.getTransform();
				
	 			//Rotate graphic so it is perpendicular to beam
				gp.rotate((angle+rotation),(location.x),(location.y));
				
				gp.setColor( Color.GREEN);
				gp.fillPolygon(new int[] {(location.x), (location.x)+5, (location.x)-5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
				gp.drawLine(location.x, location.y+10, location.x, location.y+30);
				
				gp.setColor( Color.BLACK);
				gp.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
				gp.drawLine(location.x, location.y+10, location.x, location.y+30);
				
//				gp.setColor( Color.RED);
//				gp.drawLine(location.x-50,location.y+30,location.x+50,location.y+30);
				
				if (highlighted||selected) {
				gp.drawRect(location.x-5, location.y, 10,30);
				//gp.drawString(location.toString(), location.x-5, location.y-5);
				//System.out.println(location.toString());
				
				
				}
				
				gp.setTransform(old);
	 	
	 	}
	 	
		if (direction.matches("Down")) {
			
			AffineTransform old = gp.getTransform();
			
			gp.rotate((angle+rotation),(location.x),(location.y));
			
			gp.setColor( Color.GREEN);
		 	gp.fillPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)-10, (location.y)-10}, 3);
		 	gp.drawLine(location.x, location.y-10, location.x, location.y-30);
		 	
		 	gp.setColor( Color.BLACK);
		 	gp.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)-10, (location.y)-10}, 3);
		 	gp.drawLine(location.x, location.y-10, location.x, location.y-30);

		 	if (highlighted||selected) {
		 	gp.drawRect(location.x-5, location.y-30, 10,30);
		 	
		 	String mag=String.valueOf(magnitude); 
		 	
		 	gp.drawString(mag +"kN",location.x+10,location.y-20);
		 	
			//gp.drawLine(location.x,location.y-30,b.x2,location.y-30);
		 	
			if(selected) {
				
			String dx =Integer.toString(Math.abs(location.x-end.x)); 
			String dy =Integer.toString(Math.abs(location.x-start.x));
			
			if(start.x- end.x ==0) {
				
				 dx =Integer.toString(Math.abs(location.y-end.y)); 
				 dy =Integer.toString(Math.abs(location.y-start.y));
			}
		 	gp.drawString(dx + ","+ dy,location.x-20,location.y-35);
		 	
			}
		 	}
		 	

		 	
		 	gp.setTransform(old);
		 	
		 	}
	
	}
	
	public void drawUDL(Graphics2D g,Member b) {
		getLocation();
			if (direction.matches("Down")) {
			AffineTransform old = g.getTransform();
		
		 	g.setColor( Color.GREEN);
			
		 	//Create arrow at midpoint
		 	g.rotate((b.getAngle()),(b.getMidPoint().x),(b.getMidPoint().y));
			g.drawLine(b.getMidPoint().x, b.getMidPoint().y-10, b.getMidPoint().x, b.getMidPoint().y-30);
			g.fillPolygon(new int[] {(b.getMidPoint().x), (b.getMidPoint().x)-5, (b.getMidPoint().x)+5}, new int[] {(b.getMidPoint().y), (b.getMidPoint().y)-10, (b.getMidPoint().y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((b.getAngle()),(b.getPoint1().x),(b.getPoint1().y));
			g.fillPolygon(new int[] {(b.getPoint1().x),   (b.getPoint1().x)-5,   (b.getPoint1().x)+5}, new int[] {(b.getPoint1().y), (b.getPoint1().y)-10, (b.getPoint1().y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((b.getAngle()),(b.getPoint2().x),(b.getPoint2().y));
			g.fillPolygon(new int[] {(b.getPoint2().x),   (b.getPoint2().x)-5,   (b.getPoint2().x)+5}, new int[] {(b.getPoint2().y), (b.getPoint2().y)-10, (b.getPoint2().y)-10}, 3);
			g.setTransform(old);
			
			g.setColor( Color.BLACK);
			
			
			//Create line based at midpoint
		 	g.rotate((b.getAngle()),(b.getMidPoint().x),(b.getMidPoint().y));
		 	
		 	Rectangle2D rect  = new Rectangle2D.Double(b.getMidPoint().x-b.getLength()/2, b.getMidPoint().y-30, b.getLength(), 1);
		 	g.draw(rect);
		 	
			//Create arrow at midpoint
		 	g.drawLine(b.getMidPoint().x, b.getMidPoint().y-10, b.getMidPoint().x, b.getMidPoint().y-30);
			g.drawPolygon(new int[] {(b.getMidPoint().x), (b.getMidPoint().x)-5, (b.getMidPoint().x)+5}, new int[] {(b.getMidPoint().y), (b.getMidPoint().y)-10, (b.getMidPoint().y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((b.getAngle()),(b.getPoint1().x),(b.getPoint1().y));
		    g.drawLine(b.getPoint1().x, b.getPoint1().y-10, b.getPoint1().x, b.getPoint1().y-30);
			g.drawPolygon(new int[] {(b.getPoint1().x), (b.getPoint1().x)-5, (b.getPoint1().x)+5}, new int[] {(b.getPoint1().y), (b.getPoint1().y)-10, (b.getPoint1().y)-10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((b.getAngle()),(b.getPoint2().x),(b.getPoint2().y));
			g.drawLine(b.getPoint2().x, b.getPoint2().y-10, b.getPoint2().x, b.getPoint2().y-30);
			g.drawPolygon(new int[] {(b.getPoint2().x), (b.getPoint2().x)-5, (b.getPoint2().x)+5}, new int[] {(b.getPoint2().y), (b.getPoint2().y)-10, (b.getPoint2().y)-10}, 3);		
			g.setTransform(old);
			
		}
			
		if (direction.matches("Up") ) {
			
			AffineTransform old = g.getTransform();

		 	g.setColor( Color.GREEN);
		 
		 	//Create arrow at midpoint
		 	g.rotate((b.getAngle()),(b.getMidPoint().x),(b.getMidPoint().y));
			g.drawLine(b.getMidPoint().x, b.getMidPoint().y+10, b.getMidPoint().x, b.getMidPoint().y+30);
			g.fillPolygon(new int[] {(b.getMidPoint().x), (b.getMidPoint().x)-5, (b.getMidPoint().x)+5}, new int[] {(b.getMidPoint().y), (b.getMidPoint().y)+10, (b.getMidPoint().y)+10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((b.getAngle()),(b.getPoint1().x),(b.getPoint1().y));
			g.fillPolygon(new int[] {(b.getPoint1().x),   (b.getPoint1().x)-5,   (b.getPoint1().x)+5}, new int[] {(b.getPoint1().y), (b.getPoint1().y)+10, (b.getPoint1().y)+10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((b.getAngle()),(b.getPoint2().x),(b.getPoint2().y));
			g.fillPolygon(new int[] {(b.getPoint2().x),   (b.getPoint2().x)-5,   (b.getPoint2().x)+5}, new int[] {(b.getPoint2().y), (b.getPoint2().y)+10, (b.getPoint2().y)+10}, 3);
			g.setTransform(old);
			
			g.setColor( Color.BLACK);
			
			
			//Create line based at midpoint
		 	g.rotate((b.getAngle()),(b.getMidPoint().x),(b.getMidPoint().y));
		 	Rectangle2D rect  = new Rectangle2D.Double(b.getMidPoint().x-b.getLength()/2, b.getMidPoint().y+30, b.getLength(), 1);
		 	g.draw(rect);
		 	
		 	//Create arrow at midpoint
		 	g.drawLine(b.getMidPoint().x, b.getMidPoint().y+10, b.getMidPoint().x, b.getMidPoint().y+30);
			g.drawPolygon(new int[] {(b.getMidPoint().x), (b.getMidPoint().x)-5, (b.getMidPoint().x)+5}, new int[] {(b.getMidPoint().y), (b.getMidPoint().y)+10, (b.getMidPoint().y)+10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint1
			g.rotate((b.getAngle()),(b.getPoint1().x),(b.getPoint1().y));
		    g.drawLine(b.getPoint1().x, b.getPoint1().y+10, b.getPoint1().x, b.getPoint1().y+30);
			g.drawPolygon(new int[] {(b.getPoint1().x), (b.getPoint1().x)-5, (b.getPoint1().x)+5}, new int[] {(b.getPoint1().y), (b.getPoint1().y)+10, (b.getPoint1().y)+10}, 3);
			g.setTransform(old);
			
			//Create arrow at endpoint2
			g.rotate((b.getAngle()),(b.getPoint2().x),(b.getPoint2().y));
			g.drawLine(b.getPoint2().x, b.getPoint2().y+10, b.getPoint2().x, b.getPoint2().y+30);
			g.drawPolygon(new int[] {(b.getPoint2().x), (b.getPoint2().x)-5, (b.getPoint2().x)+5}, new int[] {(b.getPoint2().y), (b.getPoint2().y)+10, (b.getPoint2().y)+10}, 3);		
			g.setTransform(old);
			
			}
		
		}
	
	public void drawMoment(Graphics2D gm) {
		getLocation();
		//System.out.println("here");
		
		if (direction.matches("Up") ) {
 			
 			AffineTransform old = gm.getTransform();
			
 			//Rotate graphic so it is perpendicular to beam
			gm.rotate((angle),(location.x),(location.y));
			
			gm.setColor( Color.GREEN);
			gm.fillPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y+10), (location.y)+15, (location.y)+20}, 3);
			gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
			//gm.drawLine(location.x, location.y+10, location.x, location.y+30);
			
			gm.setColor( Color.BLACK);
			gm.drawPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y+10), (location.y)+15, (location.y)+20}, 3);
			gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
//			gm.drawPolygon(new int[] {(location.x), (location.x)-5, (location.x)+5}, new int[] {(location.y), (location.y)+10, (location.y)+10}, 3);
//			gm.drawLine(location.x, location.y+10, location.x, location.y+30);
			
			if (highlighted||selected) {
			gm.drawRect(location.x-5, location.y, 10,30);
			//gp.drawString(location.toString(), location.x-5, location.y-5);
			//System.out.println(location.toString());
			
			
			}
			
			gm.setTransform(old);
 	
 	}
 	
	if (direction.matches("Down")) {
		
		AffineTransform old = gm.getTransform();
		
		
		gm.setColor( Color.GREEN);
		gm.fillPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y-20), (location.y)-15, (location.y)-10}, 3);
		gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
		//gm.drawLine(location.x, location.y+10, location.x, location.y+30);
		
		gm.setColor( Color.BLACK);
		gm.drawPolygon(new int[] {(location.x), (location.x)+10, (location.x)}, new int[] {(location.y-20), (location.y)-15, (location.y)-10}, 3);
		gm.drawArc(location.x-15, location.y-15,30, 30, 90, 180);
	 	
	 	if (highlighted||selected) {
	 	gm.drawRect(location.x-5, location.y-30, 10,30);
	 	//gp.drawString(location.toString(), location.x-30, location.y-30);
		//System.out.println(location.toString().);
	 	}
	 	
	 	gm.setTransform(old);
	 	
	 	}
		
	}
	
	public Shape getPointBounds() {
		
		getLocation();
		if (direction.matches("Up")) {
			//System.out.println(location.getX() +" , " +location.getY());
		Double rect = new Rectangle2D.Double(location.getX()-5, location.getY(), 10,30);
		AffineTransform at = AffineTransform.getRotateInstance(angle+rotation, location.getX(), location.getY());
		Shape rotatedRect = at.createTransformedShape(rect);

		return rotatedRect;
		
		}
		else {
			
			Double rect = new Rectangle2D.Double(location.getX()-5, location.getY()-30, 10,30);
			AffineTransform at = AffineTransform.getRotateInstance(angle+rotation, location.getX(), location.getY());
			Shape rotatedRect = at.createTransformedShape(rect);
			
			return rotatedRect;
				
			}
		
	}
	public void setHighlighted(boolean highlighted) {
	    this.highlighted = highlighted;
	    
	    if (highlighted) {
	        //Color color = Color.RED;
	        
	    }
	   
	    
	  
	}

	boolean isHighlighted() {
	    return highlighted;
	}
	


	public void setSelected(boolean selected) {
	    this.selected = selected;
	    if (selected) {
	    	
	    	//System.out.println(fixturetype);
	       // color = Color.BLACK;
	    } else {
	        //color = Color.RED;
	    }  
	 
	}
	boolean isSelected() {
	    return selected;
	}

	
		
	   //return this.polygon().getBounds;
	//}
	
}
