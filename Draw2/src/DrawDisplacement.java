import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;

public class DrawDisplacement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2413528826466860705L;
	private double U[];					//Displacement vector
	private Point beamstart;
	private Point beamend;
	private int[] nodelist;
	private int[] nodedof;
	
	
	private int countX =0;
	private int countY =0;
	
	DrawDisplacement(double U[],Point beamstart,Point beamend, int[] nodelist,int[] nodedof){
		this.U=U;
		this.beamstart=beamstart;
		this.beamend=beamend;
		this.nodelist=nodelist;
		this.nodedof=nodedof;
	}
	
	public void drawDisplacments(Graphics2D g2d) {
		
		for(int i=0; i<U.length; i++) {
			//U[i]=U[i]*1000;
			U[i]=Math.round(U[i] * 100.0) / 100.0;
			
			//System.out.println(U[i]+"U");
		
		}
		
		System.out.println();
		
		for (int i=0;i<nodedof.length;i++) {
			
			//System.out.println(nodedof[i]);
			
		}
		
//////////////////////////////////////////////////////Horizontal Deflection///////////////////////////////////////////////////////////
		
		if(countX == 0) {
		for (int i=0;i<3;i++) {
			
			if(i==0 && (U[(nodedof[i]-1)])!=0) {
				
			beamend.x +=(U[(nodedof[i]-1)])*2;
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
		
		
//////////////////////////////////////////////////////Vertical Deflection///////////////////////////////////////////////////////////
		
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		g2d.setColor( Color.BLACK);
		
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		
		g2d.drawLine(beamstart.x, beamstart.y, beamend.x, beamend.y);
	}
	
}
