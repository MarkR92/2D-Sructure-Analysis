
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.io.Serializable;
import java.util.ArrayList;

public class DrawDisplacement3 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2413528826466860705L;
	private double U[];					//Displacement vector
	private Point beamstart;
	private Point beamend;
	private int[] nodedof;
//	private double angle;
//	private Point midpoint;
//	private double length;
	private int x ;
	private int y ;
	private int count ;
	private double[] Temp;
	private double[] Temp2;
	private double[] TempY;
	private double[] TempX;
	private ArrayList<Point> node;
	private ArrayList<int[]> nodenumber;
	//private ArrayList<Point>[] nodeDisplacments;
	
	DrawDisplacement3(double U[],ArrayList<Point> node, ArrayList<int[]> nodenumber){
		this.U=U;

		Temp=new double[U.length];
		Temp2=new double[U.length];
		TempY=new double[U.length];
		TempX=new double[U.length];
		//nodeDisplacments=new ArrayList<Point>[U.length];
		//this.x=node.x;
		//this.y=node.y;
		this.node=node;
		this.nodenumber=nodenumber;
		this.count=count;
		roundDisplacements();
		
		
		
		
	}
	public void roundDisplacements() {
		
		for(int i=0; i<U.length; i++) {
			//U[i]=U[i]*1000;
			U[i]=Math.round(U[i] * 100.0) / 100.0;
			
			//System.out.println(U[i]+"U");
			//System.out.println(node.get(i));
			
		
		}	
		createNodeCordinateList();
		
	}
	public void createNodeCordinateList() {
		int j=0;
		for(int i=0; i<node.size(); i++) {
			//System.out.println(node.get(i)+" , " +nodenumber.get(i));
			System.out.println(node.size());
			Temp[j]=node.get(i).getX();
			Temp[j+1]=node.get(i).getY();
			Temp[j+2]=0;
			
			j=j+3;
			
			
		}
		addDisplacmentsToNodes();
	}
	public void addDisplacmentsToNodes() {
		int k=0;
		for(int i=0; i<node.size(); i++) {
			
			TempX[i]=Temp[k]+U[k]*2;
			TempY[i]=Temp[k+1]-U[k+1];
			
			k=k+3;
			//System.out.println(TempX[i]+"TempX" +" , "+TempY[i]+"TempY");
			//System.out.println(TempY[i]+"TempY");
		}
		createDisplacmentArray();
	}
	public void createDisplacmentArray() {
		int l=0;
		for(int i=0; i<node.size(); i++) {
			
			Temp2[l]=TempX[i];
			Temp2[l+1]=TempY[i];
			Temp2[l+2]=0;
			
			l=l+3;
			
			
		}
		//System.out.println();
		
		for (int i=0;i<Temp2.length;i++) {
			
			//System.out.println(Temp[i]);
			
		}
for (int i=0;i<nodenumber.size();i++) {
			
//			System.out.println("node "+nodenumber.get(i)[0]+","+nodenumber.get(i)[1]);
			
		}
	}
	
	


	public void drawDisplacments(Graphics2D g2d) {
		
		
		//AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		//g2d.rotate((angle));
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		
		int j= 0;
		int a,b;
	for(int i=0; i<nodenumber.size(); i++) {
		
		 a=nodenumber.get(i)[0]-1;
		 b =nodenumber.get(i)[1]-1;
		 //System.out.println("node "+a+","+b);
		g2d.drawLine((int) Temp2[a*3]+10, (int) Temp2[a*3+1]+10, (int) Temp2[b*3]+10, (int) Temp2[b*3+1]+10);
		//g2d.drawLine((int) Temp2[j]+10, (int) Temp2[j+1]+10, (int) Temp2[j+3]+10, (int) Temp2[j+4]+10);
		
		j=j+2;
		}
		//g2d.drawLine((int) Temp2[0], (int) Temp2[1]+10, (int) Temp2[3], (int) Temp2[4]+10);
		//g2d.setTransform(old);
	}
}
