import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class DrawShear {
	
	private double Rlocal[];					//Displacement vector
	private Point beamstart;
	private Point beamend;
	private Point midpoint;
	private int[] nodelist;
	private int[] nodedof;
	private int number;
	private double angle;
	private int x1,x2,y1,y2;
	private double slope;
	
	DrawShear(double Rlocal[],Point beamstart,Point beamend, int membernumber,double angle, Point midpoint, double slope){
		
		this.Rlocal=Rlocal;
		this.beamstart=beamstart;
		this.beamend=beamend;
		this.number=membernumber;
		this.angle=angle;
		this.midpoint=midpoint;
		this.slope=slope;
		
		//calculateYintercept();
	}
	
	public void drawShear(Graphics2D g2d) {

		AffineTransform old = g2d.getTransform();
		 
		
		
		g2d.setColor( Color.BLACK);
		for(int i=0; i<Rlocal.length; i++) {
			//U[i]=U[i]*1000;
			Rlocal[i]=Math.round(Rlocal[i] * 100.0) / 100.0;
			
			//System.out.println(Rlocal[i]+"Rlocal");
			
			
		}
		
		//System.out.println(angle);
		
		//for(int i=0; i<2; i++) {
			
			for(int j=0; j<Rlocal.length; j++) {
				
		if(j==1 ) {
			
			System.out.println(Rlocal[j]);
	
			//g2d.translate(0, -(int)Rlocal[j]*5);
			if(angle==0) {
				//g2d.translate(0, -(int)Rlocal[j]*5);
			
				//g2d.drawLine(beamend.x, beamend.y, beamstart.x, beamstart.y);
				g2d.setTransform(old);
			}else {
				
				g2d.translate(0, -(int)Rlocal[j]*5);
				g2d.rotate((-angle),(beamend.x),(beamend.y));
				
				//g2d.drawLine(beamend.x, beamend.y, beamstart.x, beamstart.y);
				g2d.rotate((angle),(beamend.x),(beamend.y-(int)Rlocal[j]*5));
				//g2d.translate( (int)Rlocal[j]*5*2,0);
				//g2d.translate( (int)Rlocal[j]*5,0);
				
				
			}
			
//			g2d.rotate((-angle),(beamend.x),(beamend.y));
//			g2d.rotate((angle),(beamend.x-(int)Rlocal[j]*5),(beamend.y));
//			g2d.drawLine(beamend.x, beamend.y, beamstart.x, beamstart.y);
			
			g2d.drawString(String.valueOf(Rlocal[j])+"kN",midpoint.x, midpoint.y-20);
			
			//g2d.getTransform();
			g2d.setTransform(old);
//			x1= beamend.x;
			//y1=beamend.y-(int)Rlocal[j]*5;
			g2d.rotate((angle),(beamend.x),(beamend.y));
			
			g2d.drawLine(beamend.x, beamend.y, beamend.x, beamend.y-(int)Rlocal[j]*5);
			g2d.drawLine(beamend.x, beamend.y-(int)Rlocal[j]*5, beamstart.x, beamend.y-(int)Rlocal[j]*5);
			
			
			g2d.setTransform(old);
	
			
		}
		
		if(j==4 ) {
			System.out.println(Rlocal[j]);

			
			//x2= beamstart.x;
		//	y2=beamstart.y+(int)Rlocal[j]*5;
			g2d.rotate((angle),(beamstart.x),(beamstart.y));
			
		
	
			g2d.drawLine(beamstart.x, beamstart.y, beamstart.x, beamstart.y+(int)Rlocal[j]*5);
			g2d.drawLine(beamstart.x, beamstart.y+(int)Rlocal[j]*5, beamend.x, beamstart.y+(int)Rlocal[j]*5);

			
			
			g2d.setTransform(old);
		
			//g2d.drawLine(x1, y1, x2, y2);
		}
		
		
		//g2d.drawLine(x1, y1, x2, y2);
		}
		//	g2d.drawLine(x1, y1, x2, y2);
			
	}
}
