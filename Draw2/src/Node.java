import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;




public class Node implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 6916609588821220303L;

int x,y ;
 
private int radius ;
private Color color;
private  int number;
private boolean selected;
private boolean highlighted;
private boolean toAdd;
private int dof;
private double angle;
private String fixture="Free";
//private double[] magnitude=ne;
private String forcedirection;
private String forcetype;




public Node(int x, int y, int radius, int counter, boolean toAdd) {
	
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.number = counter;
    selected = false;
    highlighted = false;
    this.color = Color.RED;//default color of unselected node
    this.toAdd= toAdd;
    angle=0;
   
  
}
public boolean toAdd() {
	return toAdd;
}
public String getFixture()
{
	return fixture;
	
}
public void changeFixture(String fixture)
{
	this.fixture=fixture;

}
public void addForce(double magnitude,String forcetype,String direction)
{
	//this.magnitude.add(magnitude);
	
}



public Point getCoord() {
	Point coord = new Point(x,y);
	
	return coord;
}
public Point getCoordPlusRadius() {
	Point coord = new Point(x+10,y+10);
	
	return coord;
}
public int getX() {
	return x;
}

public int setX(int x) {
	
	this.x = x;
	return x;
}
public double convertAngle() {
	return Math.toRadians(angle);
}
public double setAngle(double angle) {
	
	this.angle = angle ;
	return this.angle;
	
}
public int getY() {
	return y;
}
public int setY(int y) {
	this.x = y;
	return y;
}
public void drawNode(Graphics2D g2d) {

	 
    g2d.setColor(color);
   
    g2d.drawOval(x+5, y+5, radius, radius);
    
    if ((highlighted || selected)) {
    	g2d.fillOval(x+5, y+5, radius, radius);
    	g2d.drawString(number + "", x + radius+10, y + radius+10);//draw the number of node
    	
    }else  if ((!highlighted || !selected) ){
    	
    	g2d.drawOval(x+5, y+5, radius, radius);
    }
    
   
}
public void drawFixturePinned(Graphics2D g2df) {
	AffineTransform old = g2df.getTransform();
	//double rotation =-convertAngle();
		//Rotate graphic so it is perpendicular to beam
	g2df.rotate((angle),(x),(y));
	
	 
  
	g2df.fillPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
  
    if ((highlighted || selected)) {

    	g2df.fillPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    	
    	
    }else  if ((!highlighted || !selected) ){
    	
    	g2df.drawPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    }
    g2df.setTransform(old);
}

public void drawFixtureSliding(Graphics2D g) {
	AffineTransform old = g.getTransform();
	double rotation =convertAngle();
	//Rotate graphic so it is perpendicular to beam
	g.rotate((rotation),(x+10),(y+10));

	g.fillPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
	g.drawOval(x-1, y+20, 6, 6);
	g.drawOval(x+15, y+20, 6, 6);
	g.drawOval(x+7, y+20, 6, 6);
	//g.drawOval(x+5, y+20, 5, 5);
	
    if ((highlighted || selected)) {

    	g.fillPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    	
    	
    }else  if ((!highlighted || !selected) ){
    	
    	g.drawPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    }
    g.setTransform(old);
}

public void drawFixtureFixed(Graphics2D g2df) {
	AffineTransform old = g2df.getTransform();
	double rotation =convertAngle();
	//Rotate graphic so it is perpendicular to beam
	g2df.rotate((rotation),(x+10),(y+10));
    
	g2df.drawLine(x, y+15, x+20, y+15);
	g2df.drawLine(x, y+15, x+5, y+20);
	g2df.drawLine(x+5, y+15, x+10, y+20);
	g2df.drawLine(x+10, y+15, x+15, y+20);
	g2df.drawLine(x+15, y+15, x+20, y+20);
   
    if ((highlighted || selected)) {
//    
    	//g2df.fillPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    	//System.out.println(fixturetype);
    	
    }else  if ((!highlighted || !selected) ){
    
    	//g2df.drawPolygon(new int[] {x, x+10, x+20}, new int[] {y+20, y+10, y+20}, 3);
    }
    g2df.setTransform(old);
}

public Rectangle2D getBounds() {
    return new Rectangle2D.Double(x+5, y+5, radius, radius);
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

public void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
   
    
    if (highlighted) {
        color = Color.RED;
       // System.out.println(number);
        
    }
     
}

boolean isHighlighted() {
    return highlighted;
}

public int getNodeNumber() {

    return number;
}
public int setNodeNumber(int number) {
this.number = number;
    return number;
}
public Point getMidPoint() {
	
	//System.out.println(x1-x2);
	int mx =x+10;
	int my = y+10;
	//Point midpoint = new Point(x,y);
	Point midpoint = new Point( mx, my);
	//System.out.println(l);
	return midpoint;
}
public int getDOF() {
	 dof = (number)*3;
	System.out.println(dof);
	return dof;
}

public int setDOF(int dof) {

	return this.dof= dof;
}




}



