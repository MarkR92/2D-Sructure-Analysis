import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

// TempMember is used to display temporary graphics to help size members
// Inputs are from first mouse click(last) and then current mouse position(current)

public class TempMember   {
	
 Point current ;
 Point last;
 
public TempMember(Point current, Point last) {
	this.current=current;
	this.last=last;
}


public double getDeltaX() {
	
	return (double)((-last.x+current.x)/10)/2; // dividing by 10 then 2 converts from pixels to meters
}

public double getDeltaY() {
	
	return (double)((last.y-current.y)/10)/2; // dividing by 10 then 2 converts from pixels to meters
}

public double getLength() {
	
	double x = getDeltaX()*getDeltaX();
	double y = getDeltaY()*getDeltaY();
	double l = (Math.sqrt(x+y));

	return Math.round(l *100.0 )/ 100.0;

}

public Point getMidPoint() {
	
	int mx =(current.x+last.x)/2;
	int my = (current.y+last.y)/2;
	
	return new Point(mx,my);
}

public double calculateSlope() {
	
	if (current.x-last.x == 0) {

		return 1;
	}else {
	
	
	return ((double)(current.y-last.y)/(double)(current.x-last.x));

}
	}
public double calculateYintercept() {
	
	return (double)(current.y)-(double)(current.x)*calculateSlope();
	
}

public double[] circleLineIntersection() {
	

    double radius=(Math.abs(last.x-current.x));

	double slope = this.calculateSlope();
	double yInt = this.calculateYintercept();

	double circlex = last.getX(); //circle origin in x position
	double circley =last.getY();  //circle origin in y position
	
	double a = 1+slope*slope;	  // a, b and c are all derived from equation of circle (https://youtu.be/RZnoSenQjDY)
	double b = 2*(slope*(yInt-circley)-circlex);
	double c = circlex*circlex+(yInt-circley)*(yInt-circley)-radius*radius;
	
	double[] roots = new double[2];
	
	double d = b*b-4*a*c; //discriminant
	

	
	
	if( d ==0) {
		
		roots[0]=((-b+Math.sqrt(d))/(2*a));
		roots[1]=((0));
		
	}else if(d>0){
		
		roots[0]=((-b+Math.sqrt(d))/(2*a));
		roots[1]=((-b-Math.sqrt(d))/(2*a));
	}

	return roots;
	
	
}

public void drawTemp(Graphics2D g2d) {
	
	drawTempCurrent(g2d);
	drawTempX(g2d);
	drawTempY(g2d);

}

public void drawTempCurrent(Graphics2D g2d) {
	
	g2d.setColor( Color.GRAY);
	
	g2d.drawString(String.valueOf(getLength()), current.x+20,current.y-20);
	g2d.drawLine(current.x,current.y, last.x, last.y);
	

	double angle=Math.toDegrees((Math.asin((getDeltaY())/getLength())));
	angle=Math.round(angle*10.0)/10.0;

	drawAngle(g2d,(int)getMidPoint().getX(),last.y,current.x,current.y);
	
	g2d.drawString(String.valueOf(angle),last.x+5,last.y);
	
}
public void drawTempX(Graphics2D g2d) {
	
	g2d.drawString(String.valueOf(getDeltaX()), current.x, last.y);
	Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    g2d.setStroke(dashed);
	g2d.drawLine(last.x,last.y, current.x, last.y);
	
}
public void drawTempY(Graphics2D g2d) {
	
	g2d.drawString(String.valueOf(getDeltaY()), last.x, current.y);
	g2d.drawLine(last.x,last.y, last.x, current.y);
}


public void drawAngle(Graphics2D g, int x, int y, int x2, int y2) {

    int pw=Math.abs(x-x2);
    double size= (2+pw/2);
    double radius=size;
    double[] roots=circleLineIntersection();
   
    double x0 = last.x;
    double y0 = last.y;
    double p1 = roots[0];
    double p2 = (roots[0]*calculateSlope())+calculateYintercept();
    double q1 = last.getX()+size;
    double q2 = last.getY();
    
    if (Math.min(x,x2)==x2) {
    	  q1 = last.getX()-size;
    }
   
   
    double xa = x0-radius;
    double ya = y0-radius;
    double width = 2*radius;
    double height = 2*radius;
    double startAngle =  (180/Math.PI*Math.atan2(p2-y0, p1-x0));
    double endAngle =  (180/Math.PI*Math.atan2(q2-y0, q1-x0));
    
    
    g.drawArc((int)xa, (int)ya, (int)width, (int)height, (int)endAngle, -(int)startAngle);
 
}

}
