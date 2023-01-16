import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class DrawReactions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6129481665898917914L;
	
	private double Reactions[];
	
	private int Nodenum;
	private int x1,y1;
	
	DrawReactions(double R[], int Nodenum, int x1,int y1){
		this.Reactions=R;
		this.Nodenum=Nodenum;
		this.x1=x1+10;
		this.y1=y1+10;
	
		
	}
	
	public void drawReactions(Graphics2D g2d) {
		
		
		for(int i=0; i<Reactions.length; i++) {
			
			Reactions[i]=Math.round(Reactions[i] *100.0 )/ 100.0;
			
		//	System.out.println(R[i]+ "Reactions");
		
		}
		
		//System.out.println();

///////////////////////////////////Horizontal Forces//////////////////////////////////////////////////////////
				if(Reactions[Nodenum*3]!=0) {
					
					AffineTransform old = g2d.getTransform();
					 
					g2d.rotate((Math.PI/2),(x1),(y1));
				
				if(Reactions[Nodenum*3]<0) {
				 
					g2d.rotate((-Math.PI),(x1),(y1));
				 
				}
			 
			 	g2d.setColor( Color.BLUE);
				g2d.fillPolygon(new int[] {(x1), (x1)+5, (x1)-5}, new int[] {(y1), (y1)+10, (y1)+10}, 3);
				g2d.drawLine(x1, y1+10, x1, y1+30);
				
				g2d.setColor( Color.BLACK);
				g2d.drawPolygon(new int[] {(x1), (x1)-5, (x1)+5}, new int[] {(y1), (y1)+10, (y1)+10}, 3);
				g2d.drawLine(x1, y1+10, x1, y1+30);
				
				if(Reactions[Nodenum*3]<0) {
					 
					g2d.rotate((Math.PI/2),(x1),(y1));
					g2d.drawString(String.valueOf((double)Reactions[Nodenum*3]+"kN"), x1+30, y1-5);
					
					}
				
				if(Reactions[Nodenum*3]>0) {
					 
					g2d.rotate((-Math.PI/2),(x1),(y1));
					g2d.drawString(String.valueOf((double)Reactions[Nodenum*3]+"kN"), x1-60, y1-5);
					
					}
				
				g2d.setTransform(old);
				
				}
					
/////////////////////Vertical Reactions//////////////////////////////////////
				
				if(Reactions[Nodenum*3+1]!=0) { 
	
			 AffineTransform old = g2d.getTransform();
			 
			 if(Reactions[Nodenum*3+1]<0) {
				 
				 g2d.rotate((Math.PI),(x1),(y1));
				 
			 }
			 
			 	g2d.setColor( Color.BLUE);
				g2d.fillPolygon(new int[] {(x1), (x1)+5, (x1)-5}, new int[] {(y1), (y1)+10, (y1)+10}, 3);
				g2d.drawLine(x1, y1+10, x1, y1+30);
				
				g2d.setColor( Color.BLACK);
				g2d.drawPolygon(new int[] {(x1), (x1)-5, (x1)+5}, new int[] {(y1), (y1)+10, (y1)+10}, 3);
				g2d.drawLine(x1, y1+10, x1, y1+30);
				
				g2d.rotate((Math.PI),(x1),(y1));
				
				 if(Reactions[Nodenum*3+1]<0) {
					 
					 g2d.drawString(String.valueOf((double)Reactions[Nodenum*3+1]+ "kN"), x1-15, y1-35);
					 
				 }
				 
				if(Reactions[Nodenum*3+1]>0) {
					 
					 g2d.rotate((Math.PI),(x1),(y1));
					 g2d.drawString(String.valueOf((double)Reactions[Nodenum*3+1]+ "kN"), x1-15, y1+45);
				}
				 
				g2d.setTransform(old);
				
				}
//////////////////////////////////Moments////////////////////////////////////////
				if(Reactions[Nodenum*3+2]!=0) {
					
					
			// g2d.drawString(String.valueOf((double)R[Nodenum*3+2]+"kNm"), x1-70, y1-20);
			 
			 
			 
			 
			 
			 AffineTransform old = g2d.getTransform();
				
				g2d.rotate((Math.PI),(x1),(y1));
				
				 if(Reactions[Nodenum*3+2]<0) {
					 
					 g2d.rotate((-Math.PI),(x1),(y1));
					 
				 }
				
				g2d.setColor( Color.BLUE);
				g2d.fillPolygon(new int[] {(x1), (x1)+10, (x1)}, new int[] {(y1+10), (y1)+15, (y1)+20}, 3);
				g2d.drawArc(x1-15, y1-15,30, 30, 90, 180);
				
				g2d.setColor( Color.BLACK);
				g2d.drawPolygon(new int[] {(x1), (x1)+10, (x1)}, new int[] {(y1+10), (y1)+15, (y1)+20}, 3);
				g2d.drawArc(x1-15, y1-15,30, 30, 90, 180);
			
				if(Reactions[Nodenum*3+2]<0) {
					 
					// g2d.rotate((Math.PI),(x1),(y1));
					 g2d.drawString(String.valueOf((double)Reactions[Nodenum*3+2]+"kNm"), x1+20, y1+20);
					 
				 }
				
				if(Reactions[Nodenum*3+2]>0) {
					 
					 g2d.rotate((Math.PI),(x1),(y1));
					 g2d.drawString(String.valueOf((double)Reactions[Nodenum*3+2]+"kNm"), x1-70, y1-20);
					 
				 }
				
				g2d.setTransform(old);
			 
			
				}
	
		
		
			
		
		
	}

	

}
