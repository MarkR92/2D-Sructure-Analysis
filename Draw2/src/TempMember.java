import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class TempMember   {
	
 Point current ;
 //Point[] currentList;
 Point last;
//Point[] lastList;

public TempMember(Point current, Point last) {
	this.current=current;
	this.last=last;
}


public void setCurrent(Point current) {
	this.current= current;
	//  currentList[0]=current;
	
}

public void setLast(Point last) {
	
	this.last = last;
	//lastList[0]=last;
	
}

public Point getCurrent() {
	//currentList[0]=current;
	System.out.println(current +"current");
	return this.current;
	
}
public Point getLast() {
	System.out.println(last +"last");
	//lastList[0] = last;
	return this.last;
}

public double getDeltaX() {
	double dx= (double)((-last.x+current.x)/10)/2;
	//System.out.println(dx +"dx");
	//System.out.println(x2);
	return dx;
}
public double getDeltaY() {
	double dy= (double)((last.y-current.y)/10)/2;
	//System.out.println(y2 + "," +y1);
	//System.out.println(dy+"dy");
	return dy;
}
public double  getLength() {
	double xpow2 = Math.pow(getDeltaX(), 2);
	double ypow2 = Math.pow(getDeltaY(), 2);
	
double l=	(Math.sqrt(xpow2+ypow2));
//System.out.println(l);
double lr =Math.round(l *100.0 )/ 100.0;
return lr;
}
public Point getMidPoint() {
	
	//System.out.println(x1-x2);
	int mx =(current.x+last.x)/2;
	int my = (current.y+last.y)/2;
	Point midpoint = new Point(mx,my);
	//midpoint =(int mx, int my);
	//System.out.println(l);
	return midpoint;
}

public void drawTemp(Graphics2D g2d) {
	
		
	g2d.setColor( Color.GRAY);
	

	
	//System.out.println(getCurrent()+"cc");
	//System.out.println(getLast()+"ll");
	g2d.drawString(String.valueOf(getLength()), (int)getMidPoint().getX()+20,(int)getMidPoint().getY()-20);
	g2d.drawLine(current.x,current.y, last.x, last.y);
	//g2d.drawOval(current.x, current.y, 10, 10);

}






}
