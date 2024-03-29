import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Jama.Matrix;


public class Member implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063226332124518632L;

	int x1,x2,y1,y2;		//Member start and end node location
	
	int startNode,endNode;			//Member start and end node number
	int startNode2,endNode2;			//Member start and end node number
	
	
	int vs,hs,rs;			//vertical,horizontal and rotational start node index
	int ve,he,re;			//vertical,horizontal and rotational end node index
	
	private int memberNumber;		//Member number
	private int[] doflist;
	private Point memberStart;
	private Point memberEnd;
	private String forcetype="None";
	
	private Matrix reactions= new Matrix(new double[6][1]);
	private Matrix globalreactions= new Matrix(new double[6][1]);
	private Matrix localdeflections= new Matrix(new double[6][1]);
	private double[] blownupglobalreactions;
	private Color color;
	private Point forcelocation;
	private boolean selected;
	private boolean highlighted;
	
	//private int nodenum;
	
	private double Ra;
	private double Rb;
	private double Ma;
	private double Mb;
	
	private double E=200*Math.pow(10, 9);
	private double A = 0.0006;
	public double I = 60*Math.pow(10, -6);
	private String materialname="Default";
	private ArrayList<Forces2> forces = new ArrayList<>();

	
	Member(int x1, int y1, int x2, int y2, int memberNumber,int startNode,int endNode) {
		
	   this.x1 = x1;
	   this.y1 = y1;
	   this.x2 = x2;
	   this.y2 = y2;
	   this.memberNumber =memberNumber;
	   this.color = Color.BLUE;
	 //  this.nodenum=nodenum;
	   this.startNode=startNode;
	   this.endNode = endNode;
	   
	   calculateStartEnd();
	}
	
	public void addForce(Forces2 forces2)
	{
		this.forces.add(forces2);
		
	}
	public ArrayList<Forces2> getForce()
	{
		return forces;
		
	}
	public void deleteForce() {
		
	 	 for (Iterator<Forces2> forceIterator = forces.iterator(); forceIterator.hasNext();) {
	 		 
	 		Forces2 forces = forceIterator.next();
	 		
	 		if (forces.isSelected()) {
	 			
	 		 forceIterator.remove();
	 		 
	 		}

	      }
	 	
	 }
	public void setLocalDeflections(double[] U, int[] index)
	{
		 //U=new double[6];
		
		for( int i=0;i<doflist.length;i++)
		{
			for(int j=0;j<index.length;j++)
			{
				if(doflist[i]==index[j])
				{
					localdeflections.getArray()[i][0]=U[index[j]-1];
				}
				
			}
		}
		System.out.print("**********Local Deflections Member: "+memberNumber+" ************");
		localdeflections.print(1, 2);
		System.out.println("********************************");
		
	}
	public Matrix getLocalDeflections()
	{
		return getBeta().times(localdeflections);
	}
	
	
	
	
	
	
	
	
	
	 public void setStart(int start) {
		 this.startNode=start;
		 
	 }
	 public void setEnd(int end) {
		 this.endNode=end;
		 
	 }

	  public int setMemberNum(int membernum) {
	    	
	    	this.memberNumber=membernum;
	    	
	    	return memberNumber;
	    
	    }
	public int[] getNodesList(){
		

	int[]	nodelist = {startNode,endNode};
//System.out.println(startNode +","+endNode);
		return nodelist;
		
	}
	public int[] getNodesList2(){
		

		int[]	nodelist2 = {startNode2,endNode2};
	//System.out.println(startNode2 +","+endNode2);
			return nodelist2;
			
		}
	

	
	public int[] calculateNodeDOFList2(int start,int end) {
		//System.out.println("here");
		startNode2=start;
		endNode2=end;
	//	System.out.println(memberNumber+","+start+","+end);
		vs=start*3-2;
		hs=vs+1;
		rs=hs+1;
		

		ve=end*3-2;
		he=ve+1;
		re=he+1;
	
	
	int[]	dofnodelist = {vs,hs,rs,ve,he,re};

		
		doflist=dofnodelist;
	return dofnodelist;
	
}
	
	
	public int[] getNodeDOFList(){
		System.out.println("Node dof List");
		for(int j = 0; j<doflist.length; j++) {
			System.out.print(doflist[j]+ "," );
			
			}
			System.out.println();
		
		return doflist;
		
	}
	
	public void calculateStartEnd() {
		if (x1>x2) {
			//System.out.println("x<x");
			 memberStart = new Point(x1,y1);
			 memberEnd = new Point(x2,y2);
		}
		else if (x1<x2) {
			//System.out.println("x>x");
			memberStart = new Point(x2,y2);
			memberEnd = new Point(x1,y1);
		}
		else {
			//System.out.println("x=x");
			if (y1>y2) {
				memberStart= new Point(x1,y1);
				memberEnd = new Point(x2,y2);
				//System.out.println("y<y");
				
			}
			else if (y1<y2) {
				memberStart = new Point(x2,y2);
				memberEnd = new Point(x1,y1);
				//System.out.println("y>y");
				
			}
			}
	

	}
	public Point getMemberStart() {
		
		
		return memberStart;
	}
	public Point getMemberEnd() {
		
		
		return memberEnd;
	}


	
	public double getDeltaX() {
		//double dx= (double)((-x2+x1)/10)/2;
		double dx= (double)((-memberEnd.x+memberStart.x)/10)/2;
		
		return dx;
	}
	public double getDeltaY() {
		//double dy= (double)((y2-y1)/10)/2;
		double dy= (double)((memberEnd.y-memberStart.y)/10)/2;
		
		return dy;
	}
	public double  getLength() {
		
		double xpow2 = getDeltaX()*getDeltaX();
		double ypow2 = getDeltaY()*getDeltaY();
		//Math.round(R[i] *100.0 )/ 100.0;
		double l=	(Math.sqrt(xpow2+ypow2));
	
		double lr= Math.round(l *100.0 )/ 100.0;
		//System.out.println(lr+"l");
	return lr;
	
	}
	
	public double getCosTheta() {
		double costheta = getDeltaX()/getLength();
	//	System.out.println(costheta + "cos");
	//	System.out.println(getDeltaX()+"dX");
		return costheta;
	}
	
	public double getSinTheta() {
		double sintheta = getDeltaY()/getLength();
	//System.out.println(sintheta + "sin");
	//	System.out.println(getDeltaY()+"dy");
		return sintheta;
	}
	
	public double getE() {

		return E;
	}
	public void setE(double E) {
		this.E=E;
	}
	public double getA() {
		
		return A;
	}
	public void setA(double A) {
		this.A=A;
	}
	public double getI() {

		return I ;
	}
	public void setI(double I) {
		this.I=I;
	}
	public String getMaterialName() {

		return materialname ;
	}
	public void setMaterialName(String materialname) {
		
		this.materialname=materialname;
	}
	
	public Matrix getLocalKPrime() {
		
		double L = getLength();
		double lpow3 = L*L*L;
		double lpow2 =L*L;
		
		double k[][] = {
				{ (A*E)/L,				 0,				0,	-(A*E)/L,				  0,				0},
				{       0,	(12*E*I)/lpow3,	(6*E*I)/lpow2,		   0,	-(12*E*I)/lpow3,	(6*E*I)/lpow2},
				{	    0,	 (6*E*I)/lpow2,	    (4*E*I)/L,         0, 	 -(6*E*I)/lpow2,	    (2*E*I)/L},
				{-(A*E)/L,	             0,				0,	 (A*E)/L,				  0,				0},
				{       0, -(12*E*I)/lpow3,-(6*E*I)/lpow2,		   0,	 (12*E*I)/lpow3,   -(6*E*I)/lpow2},
				{		0,	 (6*E*I)/lpow2,		(2*E*I)/L,		   0,    -(6*E*I)/lpow2,	    (4*E*I)/L}	
		};
		
		Matrix K=new Matrix(k);
		
		return K;
	}
	
	public Matrix getBeta(){
		double c = getCosTheta();
		double s = getSinTheta();
		
		double b[][] = {
				{ c,	s,	0,	0,	0,	0},
				{-s,	c,	0,  0,	0,	0},
				{ 0,	0,	1,  0, 	0,  0},
				{ 0,    0,	0,	c,  s,	0},
				{ 0, 	0,	0, -s,	c,  0},
				{ 0,	0,	0,	0,	0,	1}	
		};
		
		Matrix B = new Matrix(b); 
		return B;
	}
	public double[][] setBeta(double angle){
		double c = getCosTheta();
		double s = getSinTheta();
		
		double c1 = calculateDeltaAngleCos(angle,getCosTheta());
		double s1 = calculateDeltaAngleSin(angle,getSinTheta());
		
		
		double b[][] = {
				{ c,	s,	0,	0,	0,	0},
				{-s,	c,	0,  0,	0,	0},
				{ 0,	0,	1,  0, 	0,  0},
				{ 0,    0,	0,	c1, s1, 0},
				{ 0, 	0,	0, -s1,	c1, 0},
				{ 0,	0,	0,	0,	0,	1}	
		};
		return b;
	}
	
	public Matrix getBetaT(){
			
			double bt[][] = new double[6][6];
			Matrix betaT = new Matrix(bt);
			

			betaT=getBeta().transpose();
			
			return betaT;
		}
		
		public Matrix getLocalStiffness() {

			Matrix k = getLocalKPrime();
			Matrix b = getBeta();
			Matrix bt = getBetaT();
			
			double[][]kt = new double[6][6];
			Matrix KT = new Matrix(kt);
			double[][]ks = new double[6][6];
			Matrix KS = new Matrix(ks);
			
			KT=bt.times(k);
			KS=KT.times(b);
			
			return KS;
		}
	public double calculateDeltaAngleCos(double angle,double oldangle) {
		
		angle=Math.toRadians(Math.abs(angle));
		oldangle=Math.acos(oldangle);
		double delta_angle=angle+oldangle;
		delta_angle=Math.cos(delta_angle);

		return delta_angle;
	}
	public double calculateDeltaAngleSin(double angle,double oldangle) {
		
		angle=Math.toRadians(angle);
		oldangle=Math.asin(oldangle);
		double delta_angle=angle+oldangle;
		delta_angle=Math.sin(delta_angle);
		//System.out.println(delta_angle);
		
		
		return delta_angle;
	}
	
	
public Matrix getInitialMemberReactions() {

	return reactions;
	
}
public void setMemberReactions(double[] reactions) {
	this.reactions=new Matrix (reactions,1);
	
}
public Matrix calculateGlobalMemberReactions()
{
	globalreactions= new Matrix(new double[6][1]);
	
	globalreactions=getBeta().inverse().times(getInitialMemberReactions().transpose());
	return globalreactions;
	
}

//public void setGlobalMemberReactions(double[] reactions) {
//	this.globalreactions=reactions;
//	
//}

public Matrix getGlobalMemberReactions() {
	//calculateGlobalMemberReactions();
	return globalreactions;
	
}
public void setBlownuplMemberReactions(double[] reactions) {
	this.blownupglobalreactions=reactions;
	
}
public double[] getBlownupGlobalMemberReactions() {
	 for(int i=0;i<blownupglobalreactions.length;i++) {
	//	 System.out.print(blownupglobalreactions[i]+" mb ");
	 }
	// System.out.println();
	return blownupglobalreactions;
	
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
		
		
		double dx1 = memberStart.x;
		double dx2 = memberEnd.x;
		double dy1 = memberStart.y;
		double dy2 = memberEnd.y;
		if (dx2-dx1 != 0) {
			return ((double)(dy2-dy1)/(double)(dx2-dx1));
				
	}
		return Integer.MAX_VALUE;
		}
//		if (dx2-dx1 == 0) {
//			
//			double s = 1;
//			return s;
//			
//		}else {
//		double s = (dy2-dy1)/(dx2-dx1);
//		//calculateYintercept();
//		return s;
//		
//		}	
		
	
	public void calculateYintercept() {
		//y = mx +c
		//c=y-mx
		//double c = (y2-y1)-(x2-x1)*getSlope();
		//System.out.println(getSlope());
		//System.out.println(c/10/2+ "intercept");
	}
	
public double getAngle() {
	double dy1 = memberStart.y;
	double dy2 = memberEnd.y;
	double dx1 = memberStart.x;
	double dx2 = memberEnd.x;
	
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
public double[] intialLocalForces() {
	return null;
}
	
	
	public Graphics2D drawBeam(Graphics2D g2d) {
		
		AffineTransform old = g2d.getTransform();
	    Color prevColor = g2d.getColor();
	  
	    g2d.setColor(color);
	    if (highlighted || selected) {
	    	color = Color.RED;
	    	
	    	g2d.rotate((getAngle()),getMidPoint().getX(),getMidPoint().getY());
	    	//g2d.drawString(String.valueOf(getLength()), (int)getMidPoint().getX()-8,(int)getMidPoint().getY()-10);
	    	g2d.drawString(memberNumber + "",   (int)getMidPoint().getX()-8,  + (int)getMidPoint().getY()-10);//draw the number of node
	    	
	    	g2d.setTransform(old);
	    	
	    }else {
	    	color= Color.BLUE;
	    }
	 
	    g2d.drawLine(x1,y1,x2,y2);
	    
	  
//	    Double rect = new Rectangle2D.Double(getMidPoint().getX()-getLength()*10+5,getMidPoint().getY()-5,getLength()*10*2-10,10);
		
//		AffineTransform at = AffineTransform.getRotateInstance(getAngle(), getMidPoint().getX(), getMidPoint().getY());
//		
//		Shape rotatedRect = at.createTransformedShape(rect);
		
		//g2d.draw(rotatedRect);
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
	//Double rect = new Rectangle2D.Double(getMidPoint().getX()-5,getMidPoint().getY()-5,10,10);
	Double rect = new Rectangle2D.Double(getMidPoint().getX()-getLength()*10+5,getMidPoint().getY()-5,getLength()*10*2-10,10);
	
	AffineTransform at = AffineTransform.getRotateInstance(getAngle(), getMidPoint().getX(), getMidPoint().getY());
	
	Shape rotatedRect = at.createTransformedShape(rect);

	return rotatedRect;
}
public Rectangle2D getBounds() {
	Point p = createBounds();
	
    return new Rectangle2D.Double(p.x-5, p.y-5, 10, 5);
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


public int getNumber() {
    return memberNumber;
}

public void setForceType(String forcetype) { 
	this.forcetype = forcetype;
	
}
public String getForceType() { 
	System.out.println(forcetype);
	return this.forcetype;
	
}
public void setReactions(double[] reactions) {
	this.reactions= new Matrix(reactions,1);
	
	for(int i=0; i<6; i++) {
		//System.out.println(reactions[i]+"reactions");
	}
	//System.out.println();
}
public Matrix getReactions() {
	return reactions;
}
public void setForceLocation(Point location) {
	this.forcelocation=location;
	
}
public Point getForceLocation() {
	return forcelocation;
	
}
}
