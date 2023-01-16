import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class DrawDisplacement implements Serializable{

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
	private int countX =0;
	private int countY =0;
	private int count ;
	private double[] Temp;
	
	DrawDisplacement(double U[],Point midpoint,double length,Point beamstart,Point beamend,int[] nodedof, int count,double[] Temp){
		this.U=U;
//		this.midpoint=midpoint;
//		this.length=length;
		this.beamstart=beamstart;
		this.beamend=beamend;
		this.nodedof=nodedof;
		this.count = count;
		Temp=new double[U.length];
		
		
		roundDisplacements();
		calculateHorizontalDisplacment();
		calculateVerticalDisplacment();
		
	}
	public void beamEndList() {
		double[] b = new double[U.length];
		
		for(int i=0;i<b.length;i++) {
			
		}
		
	}
	public void roundDisplacements() {
		if(count ==0) {
		for(int i=0; i<U.length; i++) {
			//U[i]=U[i]*1000;
			U[i]=Math.round(U[i] * 100.0) / 100.0;
			
			System.out.println(U[i]+"U");
		
		}
		
		System.out.println();
		
		for (int i=0;i<nodedof.length;i++) {
			
			//System.out.println(nodedof[i]);
			
		}
		}
	}
	public void calculateHorizontalDisplacment() {
		if(countX == 0) {
			for (int i=0;i<3;i++) {
				
				if(i==0 && (U[(nodedof[i]-1)])!=0) {
					//midpoint.x-length*10
				beamend.x +=(U[(nodedof[i]-1)])*2;
				System.out.println(beamend.x +"beamend");
				//g2d.drawString(String.valueOf((double)U[nodedof[i]-1]+"mm"), beamend.x-40, beamend.y-15);
				}
			}
			for (int i=3;i<6;i++) {
				if(i==3) {
			beamstart.x +=(U[(nodedof[i]-1)])*2;	
				}
			}
			countX++;
			}
			
	}
	public void calculateVerticalDisplacment() {
		if(countY == 0) {
			for (int i=0;i<3;i++) {
				if(i==0) {
				beamend.y -=(U[(nodedof[i])])*1.5;
				//g2d.drawString(String.valueOf((double)U[nodedof[i]]+"mm"), beamend.x-80, beamend.y-15);
				}
			}
			for (int i=3;i<6;i++) {
				if(i==3) {
			beamstart.y -=(U[(nodedof[i])])*1.5;	
				}
			}
			countY++;
			}
	}
	public void drawDisplacments(Graphics2D g2d) {

		
		AffineTransform old = g2d.getTransform();
		g2d.setColor( Color.BLACK);
		//g2d.rotate((angle));
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		
		g2d.drawLine(beamstart.x, beamstart.y, beamend.x, beamend.y);
		g2d.setTransform(old);
	}
}
