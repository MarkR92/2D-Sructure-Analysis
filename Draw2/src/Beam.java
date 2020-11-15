import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;


public class Beam {
	
	int x1,x2,y1,y2;		//beam start and end node location
	
	int start,end;			//beam start and end node number
	
	int vs,hs,rs;			//vertical,horizontal and rotational start node index
	int ve,he,re;			//vertical,horizontal and rotational end node index
	//private int l;
	private int number;
	private int dof;
	
	//private int[][] nodelist;

	private Color color;
	
	private boolean selected;
	private boolean highlighted;
	
	private int nodenum;
	
	private double Ra;
	private double Rb;
	private double Ma;
	private double Mb;
	
	
	Beam(int x1, int y1, int x2, int y2, int number, int dof, int nodenum,int start,int end) {
	   this.x1 = x1;
	   this.y1 = y1;
	   this.x2 = x2;
	   this.y2 = y2;
	   this.number =number;
	   this.color = Color.BLUE;
	   this.nodenum=nodenum;
	   this.start=start;
	   this.end = end;
	   this.dof = dof;
	    
	}
	
	public int getNodeNumber() {
		return nodenum;
	}
	public int[] getnodesList(){
		

	int[]	nodelist = {start,end};

	//System.out.println(start +"," +end);
		//getnodeDOFList();
		return nodelist;
		
	}
	
	public int[] getnodeDOFList() {

			vs=start*3-2;
			hs=vs+1;
			rs=hs+1;
			

			ve=end*3-2;
			he=ve+1;
			re=he+1;
		
		
		int[]	dofnodelist = {vs,hs,rs,ve,he,re};
		
		
		for(int j = 0; j<dofnodelist.length; j++) {
			//System.out.print(dofnodelist[j]+ "," );
		//	System.out.println(dofnodelistend[j]);
			}
		//	System.out.println();
			
			
		return dofnodelist;
		
	}
	
//	public int[] getNodeDOFList() {
//
//		if(number==1) {
//			int nodedoflist[] = {number,number+1,number+2};	
//			
//			for(int j = 0; j<nodedoflist.length; j++) {
//			//	System.out.print(nodedoflist[j]+ " ");
//				}
//			//	System.out.println();
//				//i++;
//			return nodedoflist;
//			
//		}
//		
//		if(number==2) {
//		int nodedoflist[] = {number+2,number+1+2,number+2+2};
//		for(int j = 0; j<nodedoflist.length; j++) {
//			//System.out.print(nodedoflist[j]+ " ");
//			}
//			//System.out.println();
//			//i++;
//		return nodedoflist;
//		}else {
//			
//			int nodedoflist[] = {number+number+number-2,number+number+number-1,number+number+number};	
//			
//		
//			
//			//System.out.println(i);
//			for(int j = 0; j<nodedoflist.length; j++) {
//			System.out.print(nodedoflist[j]+ " ");
//			}
//			System.out.println();
//			//i++;
//			return nodedoflist;
//		
//		}
//		}
	
	
	public Point getBeamStart() {
		Point bstart = new Point(x1,y1);
		
		return bstart;
	}
	public Point getBeamEnd() {
		Point bend = new Point(x2,y2);
		
		return bend;
	}
	public int getDOF() {
		dof = dof *3;
		return dof;
		}
	
	public double getDeltaX() {
		double dx= (double)((-x2+x1)/10)/2;
		//System.out.println(dx +"dx");
		//System.out.println(x2);
		return dx;
	}
	public double getDeltaY() {
		double dy= (double)((y2-y1)/10)/2;
		//System.out.println(y2 + "," +y1);
		//System.out.println(dy+"dy");
		return dy;
	}
	public double  getLength() {
		
		double xpow2 = Math.pow(getDeltaX(), 2);
		double ypow2 = Math.pow(getDeltaY(), 2);
		//Math.round(R[i] *100.0 )/ 100.0;
		double l=	(Math.sqrt(xpow2+ypow2));
	//System.out.println(l);
		double lr= Math.round(l *100.0 )/ 100.0;
	return lr;
	
	}
	
	public double getCosTheta() {
		double costheta = getDeltaX()/getLength();
		//System.out.println(costheta + "cos");
		return costheta;
	}
	
	public double getSinTheta() {
		double sintheta = getDeltaY()/getLength();
		//System.out.println(sintheta + "sin");
		return sintheta;
	}
	
	public double getE() {
		double n =Math.pow(10, 9);
		double E = 200*n;
		
		return E;
	}
	public double getA() {
		double A = 0.0006;
		
		return A;
	}
	public double getI() {
		double n =Math.pow(10, -6);
		double I = 60*n;
		return I ;
	}
	
	public double[][] getLocalKPrime() {
		double E = getE();
		double A = getA();
		double L = getLength();
		double lpow3 = Math.pow(L, 3);
		double lpow2 = Math.pow(L, 2);
		
		double I = getI();
		
		
		double k[][] = {
				{ (A*E)/L,				 0,				0,	-(A*E)/L,				  0,				0},
				{       0,	(12*E*I)/lpow3,	(6*E*I)/lpow2,		   0,	-(12*E*I)/lpow3,	(6*E*I)/lpow2},
				{	    0,	 (6*E*I)/lpow2,	    (4*E*I)/L,         0, 	 -(6*E*I)/lpow2,	    (2*E*I)/L},
				{-(A*E)/L,	             0,				0,	 (A*E)/L,				  0,				0},
				{       0, -(12*E*I)/lpow3,-(6*E*I)/lpow2,		   0,	 (12*E*I)/lpow3,   -(6*E*I)/lpow2},
				{		0,	 (6*E*I)/lpow2,		(2*E*I)/L,		   0,    -(6*E*I)/lpow2,	    (4*E*I)/L}	
		};
		
		//System.out.println(k[0][0]);
		return k;
	}
	
	public double[][] getBeta(){
		double c = getCosTheta();
		double s = getSinTheta();
		//System.out.println(c);
		//System.out.println(s);
		double b[][] = {
				{ c,	s,	0,	0,	0,	0},
				{-s,	c,	0,  0,	0,	0},
				{ 0,	0,	1,  0, 	0,  0},
				{ 0,    0,	0,	c,  s,	0},
				{ 0, 	0,	0, -s,	c,  0},
				{ 0,	0,	0,	0,	0,	1}	
		};
		return b;
	}
	public double[][] getBetaT(){
		
		double[][] b = getBeta();
		
		double bt[][] = new double[6][6];
		
		for(int i=0;i<6;i++) {
			for(int j=0;j<6;j++) {
				
				bt[i][j]= b [j][i];
			}
		}
		
		return bt;
	}
	
	public double[][] getLocalK() {
	//	this.k =k;
		double[][] k = getLocalKPrime();
		double[][] b = getBeta();
		double[][] bt = getBetaT();
		
		double[][]Kt = new double[6][6];
		double[][]K = new double[6][6];
		
		for(int i=0;i<6;i++) {
			for(int j=0;j<6;j++) {
				
				Kt[i][j]=0;
				
				for(int z=0;z<6;z++) {
					
					Kt[i][j]+=((bt[i][z]*k[z][j]));
				}
				//System.out.print(Kt[i][j] +" ");
				
			}
			//System.out.println();
			
		}
		for(int i=0;i<6;i++) {
			for(int j=0;j<6;j++) {
				K[i][j]=0;
				for(int z=0;z<6;z++) {
					
					K[i][j]+=(Kt[i][z]*b[z][j]);
				}
				//System.out.print(K[i][j] +" ");
				
			}
			//System.out.println();
			
		}
		return K;
	}
	

public void calBeamReaction(double P, Point ab) {
	
	double L = getLength();
	double a = Math.abs(ab.getX()-x2);
	double b = Math.abs(ab.getX()-x1);
	
	double a2 = Math.pow(a, 2);
	double b2 = Math.pow(b, 2);
	
	double L2 = Math.pow(L, 2);
	double L3 = Math.pow(L, 3);
	

	
	 Ra = (P*b2*(3*a+b))/L3;
	 Rb = (P*a2*(a+b*3))/L3;
	
	 Ma = (P*b2*a)/L2;
	 Mb = (P*b*a2)/L2;
	//System.out.println(a +"," +b);
	
}
 public double getRa() {
	 
	 return Ra;
 }
 public double getRb() {
	 
	 return Rb;
 }
 public double getMa() {
	 
	 return Ma;
 }
 public double getMb() {
	 
	 return Mb;
 }


	
	public double getSlope() {
		
		double dx1 = x1;
		double dx2 = x2;
		double dy1 = y1;
		double dy2 = y2;
		
		if (dx2-dx1 == 0) {
			
			double s = 1;
			return s;
			
		}else {
		double s = (dy2-dy1)/(dx2-dx1);
		
		return s;
		
		}	
		
	}

	
public double getAngle() {
	double dy1 = y1;
	double dy2 = y2;
	double dx1 = x1;
	double dx2 = x2;
	
	double o = (dy1-dy2);
	double a = (dx1-dx2);
	

	if (a<0) {
		o = (dy2-dy1);
	}
	
	double h =Math.sqrt((Math.pow(o, 2))+(Math.pow(a, 2)));
	//System.out.println(h);
	double angle =Math.asin((o)/h);
	
	if (angle <0.00 ) {
		// angle =Math.asin((dy2-dy1)/h);
	}
	//angle = Math.abs(angle);
	//angle = Math.toDegrees(angle);
	return angle;
	
			}
public void lineBoundx(int xi) {
	//y = mx + c
	int xc =getPoint1().x;
	int xcc = getPoint1().x;
	
	if (xc<=xi && xi>=xcc) {
		//System.out.println(true);
	}
	
	
}

	public Point getMidPoint() {
		
		//System.out.println(x1-x2);
		int mx =(x1+x2)/2;
		int my = (y1+y2)/2;
		Point midpoint = new Point(mx,my);
		//midpoint =(int mx, int my);
		//System.out.println(l);
		return midpoint;
	}
	public Point getPoint1() {
		
		Point point = new Point(x1,y1);
		return point;
	}
	
	public Point getPoint2() {
		
		Point point = new Point(x2,y2);
		return point;
	}

	
	
	public Graphics2D drawBeam(Graphics2D g2d) {
		//int l = getLength();
		AffineTransform old = g2d.getTransform();
	    Color prevColor = g2d.getColor();
	   // g2d.drawString(number + "", x + radius, y + radius);//draw the number of node
	    g2d.setColor(color);
	    if (highlighted || selected) {
	    	color = Color.RED;
	    	//g2d.drawString(String.valueOf(getLength()), (int)getMidPoint().getX(),(int)getMidPoint().getY()-20);
	    	
	    	
	    }else {
	    	color= Color.BLUE;
	    }
	 
	    g2d.drawLine(x1,y1,x2,y2);
	    
	//	color= Color.DARK_GRAY;
	   
	 
	    g2d.rotate((getAngle()),getMidPoint().getX(),getMidPoint().getY());
	    
	    g2d.drawString(String.valueOf(getLength()), (int)getMidPoint().getX()-8,(int)getMidPoint().getY()-10);
	    
	 //   g2d.drawRect(x2, y2, (int) getLength()*2*10, 10);
	    
	    g2d.setTransform(old);
		
	    g2d.rotate((getAngle()),getMidPoint().getX(),getMidPoint().getY());
	    
	    g2d.drawRect((int)getMidPoint().getX()-5,(int)getMidPoint().getY()-5,10, 10);
	    
	    g2d.setTransform(old);
	    
	    g2d.setColor(prevColor);
	    
	   // g2d.drawRect(x2, y2, (int) getLength()*2*10, 10);
	    return g2d;
	}

	public void drawBeamLength(Graphics2D g) {
		//color = Color.DARK_GRAY;
		
		g.drawLine(x1+10, y1, x2+10, y2);
		
		
	}

public void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
   
    
    if (highlighted) {
        color = Color.RED;
        
    }else {
    	
    	//color = Color.BLUE;
    	
    
    }
  
    
}

public void setSelected(boolean selected) {
    this.selected = selected;
    if (selected) {
    
    	//System.out.println(number);
       //color = Color.RED;
    } else {
        //color = Color.RED;
    }
    
 
}


public Point createBounds() {
	int xm = (x1+x2)/2;
	int ym = (y1+y2)/2;
	
	Point p = new Point(xm,ym);
	return p;
	
}
public Shape getbounds() {
	
	//g2d.rotate((getAngle()),getMidPoint().getX(),getMidPoint().getY());
    
    //g2d.drawRect((int)getMidPoint().getX()-5,(int)getMidPoint().getY()-5,10, 10);
	Double rect = new Rectangle2D.Double(getMidPoint().getX()-5,getMidPoint().getY()-5,10,10);
	
	AffineTransform at = AffineTransform.getRotateInstance(getAngle(), getMidPoint().getX(), getMidPoint().getY());
	
	Shape rotatedRect = at.createTransformedShape(rect);

	return rotatedRect;
}
public Rectangle2D getBounds() {
	Point p = createBounds();
	
    return new Rectangle2D.Double(p.x-5, p.y-5, 10, 1);
}

//public Line2D getBounds() {
	
  //  return new Line2D.Double(x1,y1,x2,y2);
//}


boolean isHighlighted() {
    return highlighted;
}



boolean isSelected() {
    return selected;
}


int getNumber() {
    return number;
}
}
