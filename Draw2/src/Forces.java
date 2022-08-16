import java.awt.AlphaComposite;
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
	
	private Point midpointlocation;
	private Point currentlocation;
	
	private double rotation;
	
	private double angle;
	private double angle2;
	private Point start;
	private Point end;
	
	public Forces(double magnitude, String type, String direction, String direction2, int beamnumber, Point midpointlocation, double angle,double angle2, Point start, Point end) {
		
		this.magnitude=magnitude;
		this.type = type;
		this.direction= direction;
		this.direction2= direction2;
		this.beamnumber= beamnumber;
		this.midpointlocation= midpointlocation;
		this.angle = angle;
		this.angle2=angle2;
		highlighted = false;
		selected = false;
		
		this.start=start;
		this.end=end;
	}
	
//	public double getAngle() {
//		return angle;
//		
//	}
	public double convertAngle() {
		return Math.toRadians(angle2);
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
	public double getAngle2() {
		return angle2;
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude=magnitude;
		}
	
	public String getType() {
		return type;
		}
	
	public Point getLocation() {

		if (currentlocation == null) {
			 this.currentlocation=midpointlocation;	
		}
	
			return this.currentlocation;
	}
	
	public Point setLocation(int xx, int yy) {
		
		
		Point currentlocation = new Point(xx,yy);
		this.currentlocation=currentlocation;
		return currentlocation;

			
		}
	


	public int getNumber() {
		return beamnumber;
		}
	
	public void drawPointLoad(Graphics2D gp) {
	getLocation();
	
			rotation= -convertAngle();
	System.out.println(angle+","+"force");
	 		if (direction == "Up") {
	 			
	 			AffineTransform old = gp.getTransform();
				
	 			//Rotate graphic so it is perpendicular to beam
	 			
				gp.rotate((angle+rotation),(currentlocation.x),(currentlocation.y));
				
				drawPointLoadGraphic(gp,1);

				if (highlighted||selected) {
				gp.drawRect(currentlocation.x-5, currentlocation.y, 10,30);
			
				
				
				}
				
				gp.setTransform(old);
	 	
	 	}
	 	
		if (direction.matches("Down")) {
			
			AffineTransform old = gp.getTransform();
			
			gp.rotate((angle+rotation),(currentlocation.x),(currentlocation.y));

		 	if (highlighted||selected) {
		 //	gp.drawRect(currentlocation.x-5, currentlocation.y-30, 10,30);
		 	
		 	String mag=String.valueOf(magnitude); 
			gp.setColor( Color.black);
		 	gp.drawString(mag +"kN",currentlocation.x+10,currentlocation.y-20);
		 	
			
			drawPositionGraphics(gp);
		 	drawMeasurementGraphics(gp);
		 	
			float opacity = 0.25f;
	 		gp.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			
		 	}
		 	
		 	drawPointLoadGraphic(gp,-1);

		 	
		 	gp.setTransform(old);
		 	
		 	}
	
	}
	public void drawPointLoadGraphic(Graphics2D g,int changesign) {
		//change sign is used to rotate positive/negative point load 180 degree
		//1 points the graphic up and -1 points the graphic down
		
		g.setColor( Color.GREEN);
		g.fillPolygon(new int[] {(currentlocation.x), (currentlocation.x)+5*changesign, (currentlocation.x)-5*changesign}, new int[] {(currentlocation.y), (currentlocation.y)+10*changesign, (currentlocation.y)+10*changesign}, 3);
		g.drawLine(currentlocation.x, currentlocation.y+10*changesign, currentlocation.x, currentlocation.y+30*changesign);
		
		g.setColor( Color.BLACK);
		g.drawPolygon(new int[] {(currentlocation.x), (currentlocation.x)-5, (currentlocation.x)+5}, new int[] {(currentlocation.y), (currentlocation.y)+10*changesign, (currentlocation.y)+10*changesign}, 3);
		g.drawLine(currentlocation.x, currentlocation.y+10*changesign, currentlocation.x, currentlocation.y+30*changesign);
		
		
	}
	public void drawMeasurementGraphics(Graphics2D g) {
		
//		AffineTransform old = g.getTransform();
//		
//		g.rotate((-angle*0.5),(end.x),(end.y));
		
		g.setColor( Color.GRAY);
		g.drawLine(midpointlocation.x,end.y-30, currentlocation.x, currentlocation.y-30);					//line
		g.drawLine(end.x, end.y-35, end.x, end.y-25);							//start point line
		g.drawLine(currentlocation.x, currentlocation.y-35, currentlocation.x, currentlocation.y-25);		//end point line
	//	g.setTransform(old);
		
		
	}
	public void drawPositionGraphics(Graphics2D g) {
		
		g.setColor( Color.GRAY);
		double dx =(Math.abs(currentlocation.getX()-end.getX())/10/2); 
		double dy =(Math.abs(currentlocation.getX()-start.getX())/10/2);
		
		if(start.x- end.x ==0) {
			

			 dx =(Math.abs(currentlocation.getY()-end.getY())/10/2); 
			 dy =(Math.abs(currentlocation.getY()-start.getY())/10/2);
		}
	 	  
	 	g.drawString(dx + ","+ dy,currentlocation.x-8,currentlocation.y-35);
	 	
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
		 	
		 	Rectangle2D rect  = new Rectangle2D.Double(b.getMidPoint().x-b.getLength()*10, b.getMidPoint().y-30, b.getLength()*10*2, 1);
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
			gm.rotate((angle),(currentlocation.x),(currentlocation.y));
			
			gm.setColor( Color.GREEN);
			gm.fillPolygon(new int[] {(currentlocation.x), (currentlocation.x)+10, (currentlocation.x)}, new int[] {(currentlocation.y+10), (currentlocation.y)+15, (currentlocation.y)+20}, 3);
			gm.drawArc(currentlocation.x-15, currentlocation.y-15,30, 30, 90, 180);
			//gm.drawLine(currentlocation.x, currentlocation.y+10, currentlocation.x, currentlocation.y+30);
			
			gm.setColor( Color.BLACK);
			gm.drawPolygon(new int[] {(currentlocation.x), (currentlocation.x)+10, (currentlocation.x)}, new int[] {(currentlocation.y+10), (currentlocation.y)+15, (currentlocation.y)+20}, 3);
			gm.drawArc(currentlocation.x-15, currentlocation.y-15,30, 30, 90, 180);
//			gm.drawPolygon(new int[] {(currentlocation.x), (currentlocation.x)-5, (currentlocation.x)+5}, new int[] {(currentlocation.y), (currentlocation.y)+10, (currentlocation.y)+10}, 3);
//			gm.drawLine(currentlocation.x, currentlocation.y+10, currentlocation.x, currentlocation.y+30);
			
			if (highlighted||selected) {
			//gm.drawRect(currentlocation.x-5, currentlocation.y, 10,30);
			//gp.drawString(currentlocation.toString(), currentlocation.x-5, currentlocation.y-5);
			//System.out.println(currentlocation.toString());
			gm.setColor(Color.red);
			
			}

			gm.setTransform(old);
 	
 	}
 	
	if (direction.matches("Down")) {
		
		AffineTransform old = gm.getTransform();
		
		
		gm.setColor( Color.GREEN);
		gm.fillPolygon(new int[] {(currentlocation.x), (currentlocation.x)+10, (currentlocation.x)}, new int[] {(currentlocation.y-20), (currentlocation.y)-15, (currentlocation.y)-10}, 3);
		gm.drawArc(currentlocation.x-15, currentlocation.y-15,30, 30, 90, 180);
		//gm.drawLine(currentlocation.x, currentlocation.y+10, currentlocation.x, currentlocation.y+30);
		
		gm.setColor( Color.BLACK);
		gm.drawPolygon(new int[] {(currentlocation.x), (currentlocation.x)+10, (currentlocation.x)}, new int[] {(currentlocation.y-20), (currentlocation.y)-15, (currentlocation.y)-10}, 3);
		gm.drawArc(currentlocation.x-15, currentlocation.y-15,30, 30, 90, 180);
	 	
	 	if (highlighted||selected) {
	 	//gm.drawRect(currentlocation.x-5, currentlocation.y-30, 10,30);
	 	//gp.drawString(currentlocation.toString(), currentlocation.x-30, currentlocation.y-30);
		//System.out.println(currentlocation.toString().);
	 		gm.setColor(Color.red);
	 	}
	 	
	 	gm.setTransform(old);
	 	
	 	}
		
	}
	
	public Shape getPointBounds() {
		
		getLocation();
		if (direction.matches("Up")) {
			//System.out.println(currentlocation.getX() +" , " +currentlocation.getY());
		Double rect = new Rectangle2D.Double(currentlocation.getX()-5, currentlocation.getY(), 10,30);
		AffineTransform at = AffineTransform.getRotateInstance(angle+rotation, currentlocation.getX(), currentlocation.getY());
		Shape rotatedRect = at.createTransformedShape(rect);

		return rotatedRect;
		
		}
		else {
			
			Double rect = new Rectangle2D.Double(currentlocation.getX()-5, currentlocation.getY()-30, 10,30);
			AffineTransform at = AffineTransform.getRotateInstance(angle+rotation, currentlocation.getX(), currentlocation.getY());
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
