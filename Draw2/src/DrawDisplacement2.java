import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawDisplacement2 {
	
	private double U[];					//Displacement vector
	
	private int[] Nodenum;				//node number based on iteration 
	private int  x1,y1;					//Node location (x,y)
	private int startnode, endnode;
	private int startx,starty,endx,endy;
	private int numberofmembers;
	private int size;
	private int[] X;					//Vector of node location in x (horizontal)
	private int[] Y;					//Vector of node location in y (vertical)
	
	private int countX =0;
	private int countY =0;
	
	
	DrawDisplacement2(double U[], int[] Nodenum, int x1, int y1,int size,  int[] X, int[]Y){
	//DrawDisplacement(double U[], int startnode, int endnode, int startx, int starty, int endx, int endy,int numberofmembers,  int[] X, int[]Y){
		this.U=U;  	
		this.startnode=startnode; 
		this.endnode=endnode;
		this.startx=startx;
		this.starty=starty;
		this.endx=endx;
		this.endy=endy;
		this.numberofmembers=numberofmembers;
		this.Nodenum=Nodenum;			
		//this.x1=x1+10;					
		//this.y1=y1+10;					
		this.size=size;
		
		this.X=X;
		this.Y=Y;
		
	}
	
	
	public void drawDisplacments(Graphics2D g2d) {

		for(int i=0; i<X.length; i++) {
		//System.out.println(len*10*2);
			
			//System.out.println(X1[i]);
		}
		
		for(int i=0; i<U.length; i++) {
			//U[i]=U[i]*1000;
			U[i]=Math.round(U[i] * 100.0) / 100.0;
			
			System.out.println(U[i]+"U");
		
		}
		
		System.out.println();

	
		//System.out.println(U[Nodenum*3]);
		
///////////////////////////////////Horizontal Displacement//////////////////////////////////////////////////////////
		for (int i = 0; i<Nodenum.length;i++,i++,i++) {
			
			
	if(U[Nodenum[i]-1]!=0) {
		
		AffineTransform old = g2d.getTransform();
		
		if(countX == 0) {
	
				X[Nodenum[i]-1]= X[Nodenum[i]-1]+((int)U[Nodenum[i]-1])+5; //node x location + displacement+5(scaler)
			
		countX++;
		
		}	 
		
			g2d.rotate((Math.PI),(x1),(y1));
			g2d.setColor( Color.BLACK);
		 	
			
			if(U[Nodenum[i]-1]<0) {
				 
				g2d.rotate((Math.PI),(x1),(y1));
				g2d.drawString(String.valueOf((double)U[Nodenum[i]-1]+"mm"), x1-40, y1-15);
				
				for(int j=0; j<X.length;j++) {
					
					if(X[j]!=0) {
						
							Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
							g2d.setStroke(dashed);
					       // g2d.drawPolyline(X1,Y1, size);      
					       // g2d.dispose();
				}
			
				}
			}
			
			if(U[Nodenum[i]-1]>0) {
				 
				g2d.rotate((Math.PI),(x1),(y1));
				g2d.drawString(String.valueOf((double)U[Nodenum[i]-1]+"mm"), x1-40, y1-15);
				
				for(int j=0; j<X.length;j++) {
					
					if(X[j]!=0) {
						
							Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
					        g2d.setStroke(dashed);
					        g2d.drawPolyline(X,Y, size);
					       // g2d.dispose();
				}
			}
			}
			//g2d.dispose();
			g2d.setTransform(old);
			
			}
///////////////////////////////////Vertical Forces//////////////////////////////////////////////////////////
			
			if(U[Nodenum[i]-1]+1!=0) {
		
		AffineTransform old = g2d.getTransform();
		
		if(countY == 0) {
			
		
				Y[Nodenum[i]-1]= Y[Nodenum[i]-1]-((int)U[Nodenum[i]-1]+1)+5;
				
		countY++;
		
		}	 
		g2d.rotate((Math.PI),(x1),(y1));

			g2d.setColor( Color.BLACK);
		 	
			
			if(U[Nodenum[i]-1]+1<0) {
				 
				g2d.rotate((Math.PI),(x1),(y1));
				g2d.drawString(String.valueOf((double)U[Nodenum[i]-1]+1+"mm"), x1-40, y1+30);
				
				for(int j=0; j<Y.length;i++) {
					
					if(Y[j]!=0) {
						
							Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
					        g2d.setStroke(dashed);
					      //  g2d.drawPolyline(X1,Y1, size);
					       // g2d.dispose();
				}
			
				}
			}
			
			if(U[Nodenum[i]-1]+1>0) {
				 
				g2d.rotate((Math.PI),(x1),(y1));
				g2d.drawString(String.valueOf((double)U[Nodenum[i]-1]+1+"mm"), x1-40, y1+30);
				
				for(int j=0; j<Y.length;j++) {
					if(Y[j]!=0) {
						
						 	Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
					        g2d.setStroke(dashed);
					        g2d.drawPolyline(X,Y, size);
					     //   g2d.dispose();
				}
			}
			}
			g2d.dispose();
			g2d.setTransform(old);
			
			}
	}
}
}
