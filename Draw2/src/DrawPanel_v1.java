
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel_v1 extends JPanel {

	static JLabel label;
	private static ArrayList<Point> points;
	//private static ArrayList<Point> pointsConfirm;
	private static ArrayList<Integer> Xpoints;
	private static ArrayList<Integer> Ypoints;
	private static ArrayList<Integer> XpointsConfirm;
	private static ArrayList<Integer> YpointsConfirm;
	private static ArrayList<Integer> Xnearpreviousminus;
	private static ArrayList<Integer> Xnearpreviousplus;
	private static ArrayList<Integer> Ynearpreviousminus;
	private static ArrayList<Integer> Ynearpreviousplus;
	int a= 0;
	int click = 0;
	int count = 0;
	int c =0;
	int c2=0;
	
	int x = 0;
	int y = 0;
	int size =0;
	int size2 =0;
	int i = 0;
	int ii=0;
	int iii=0;
	int newcount= 0;
	int nx = 0;
	int ny = 0;
	int n1 =0;
	int n2=0;
	boolean xt;
	boolean yt;
	boolean result = false;
	boolean result2 = false;
	
	int xnearpreviousminus=0;
	int xnearpreviousplus=0;
	int ynearpreviousminus=0;
	int ynearpreviousplus=0;
	
	int xprevious=0;
	int xcurrent=0;
	int yprevious=0;
	int ycurrent=0;
	
	int xc =0;
	int yc = 0;
		public DrawPanel_v1()   {
			points = new ArrayList<Point>();
			//pointsConfirm = new ArrayList<Point>();
			Xpoints = new ArrayList<Integer>();
			Ypoints = new ArrayList<Integer>();
			XpointsConfirm = new ArrayList<Integer>();
			YpointsConfirm = new ArrayList<Integer>();
			Xnearpreviousminus = new ArrayList<Integer>();
			Xnearpreviousplus = new ArrayList<Integer>();
			Ynearpreviousminus = new ArrayList<Integer>();
			Ynearpreviousplus = new ArrayList<Integer>();
			
			setBackground(Color.WHITE);
			
			addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e)
				{
					
					label.setText("["+ e.getX() + " , " +e.getY()+"]");
					xc =e.getX();
					yc =e.getY();
					
					for (iii=0;iii<=size2-1;iii++) {
						if (e.getX() > Xnearpreviousminus.get(iii) && e.getX() < Xnearpreviousplus.get(iii) && e.getY() > Ynearpreviousminus.get(iii) && e.getY() < Ynearpreviousplus.get(iii) ) {
							result = true;
							nx=Xnearpreviousminus.indexOf(Xnearpreviousminus.get(iii));
							ny=Ynearpreviousminus.indexOf(Ynearpreviousminus.get(iii));
							//System.out.println(Xnearpreviousminus.get(iii) );
							//System.out.println(Ynearpreviousminus.get(iii) );
							System.out.println( nx);
							//repaint();
							//System.out.println( e.getX());	
						}
						if ( e.getX() > xcurrent-5 && e.getX() < xcurrent+5 && e.getY() > ycurrent-5 && e.getY() < ycurrent+5) {
							//result = true;
							//n1=Xnearpreviousminus.indexOf(Xnearpreviousminus.get(iii));
							//System.out.println(size2);
							result2=true;
							//a=size2-n1;
									
							//System.out.println(n1);
			}
						
						repaint();
						a=0;
						
							}
					
			//	n2=0;
					
					
				}
				
				
				
			})
			;
			
			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					
					//points.add(new Point(e.getX(), e.getY()));
					
					
				
					Xpoints.add(e.getX());
					
					size= Xpoints.size();
					
					Ypoints.add(e.getY());
					//Xnearpreviousminus.add(0);
					//Xnearpreviousplus.add(0);
					
					
					//Ynearpreviousminus.add(0);
					//Ynearpreviousplus.add(0);//click++;
					//repaint();
					//Once the points are added to list we would like to check if the new point is within radius 5 of a previous point.
					if (click==1 || click ==2) {
						
					
						xprevious=Xpoints.get(click-1);
						yprevious=Ypoints.get(click-1);
						xnearpreviousminus=xprevious-5;
						//Xnearpreviousminus.add(xnearpreviousminus);
						
						xnearpreviousplus=xprevious+5;
						//Xnearpreviousplus.add(xnearpreviousplus);
						
						ynearpreviousminus=yprevious-5;
						//Ynearpreviousminus.add(ynearpreviousminus);
						
						ynearpreviousplus=yprevious+5;
						
						Xnearpreviousminus.add(xnearpreviousminus);
						Xnearpreviousplus.add(xnearpreviousplus);
						Ynearpreviousminus.add(ynearpreviousminus);
						Ynearpreviousplus.add(ynearpreviousplus);//click++;
						//count++;
					}
					click++;
					count++;
					//System.out.println(click);
					
					//System.out.println(xprevious);
					for ( int i=0;i<=size-1;i++) {
					
						
						//count++;
					x= Xpoints.get(i);
					y= Ypoints.get(i);
					
					
					xt=XpointsConfirm.contains(x);
					yt=YpointsConfirm.contains(y);	
						
					}
						
				
				//	if (xt  == false || yt == false ){
						
						XpointsConfirm.add(x);
						YpointsConfirm.add(y);
						
						if (c>1) {
						xprevious= XpointsConfirm.get(c2-1);
						yprevious= YpointsConfirm.get(c2-1);
						
						
						xnearpreviousminus=xprevious-5;
						Xnearpreviousminus.add(xnearpreviousminus);
						
						xnearpreviousplus=xprevious+5;
						Xnearpreviousplus.add(xnearpreviousplus);
						
						ynearpreviousminus=yprevious-5;
						Ynearpreviousminus.add(ynearpreviousminus);
						
						ynearpreviousplus=yprevious+5;
						Ynearpreviousplus.add(ynearpreviousplus);
						}
					//System.out.println( xt);
					//System.out.println( xprevious);
					//System.out.println( YpointsConfirm);
						c2++;
						
					//}
				
						
					
						
							
					size =XpointsConfirm.size();
					//System.out.println( size2);
					
					
					//n1=XpointsConfirm.indexOf(e.getX());
					
					
					
					for (ii=0;ii<=size-2;ii++) {
						
						
						
							
						xcurrent= e.getX();
						ycurrent= e.getY();
						
						
						//System.out.println( xcurrent);
						//System.out.println( Xnearpreviousplus);
						size2 = Xnearpreviousminus.size();
						//System.out.println(xcurrent);
					
						c++;
						
						
						
						
						
						for (iii=0;iii<=size2-1;iii++) {
					if (xcurrent > Xnearpreviousminus.get(iii) && xcurrent < Xnearpreviousplus.get(iii) && ycurrent > Ynearpreviousminus.get(iii) && ycurrent < Ynearpreviousplus.get(iii)) {
						//result = true;
						//System.out.println( result);
						
						
							//XpointsConfirm.add(n1, xprevious);
						
							//System.out.println(Xnearpreviousminus);
							//System.out.println(XpointsConfirm);
							//YpointsConfirm.remove(YpointsConfirm.indexOf(ycurrent));
							//YpointsConfirm.add(c2,yprevious);
							//size =XpointsConfirm.size();
							n1=Xnearpreviousminus.indexOf(Xnearpreviousminus.get(iii));
							n2=Ynearpreviousminus.indexOf(Ynearpreviousminus.get(iii));
							
							//System.out.println( n1);
							
							XpointsConfirm.set(click-1, XpointsConfirm.get(n1));
							YpointsConfirm.set(click-1, YpointsConfirm.get(n2));
							
					}
						}	
							
						
						
					//size =XpointsConfirm.size();
					}
					size =XpointsConfirm.size();
			//	}
					
					
					repaint();
					
				
				}
			
			});
			
	
		
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//System.out.println("i am in paint component");
			
			for ( int i=0;i<=size-1;i++) {
				
			g2.setColor(Color.red);
			g2.drawOval(XpointsConfirm.get(i)-5,YpointsConfirm.get(i)-5, 10, 10);	
			
	
			
			
			}
			if (result==true) {
				
				//System.out.println(n);
				g2.fillOval(XpointsConfirm.get(nx)-5,YpointsConfirm.get(ny)-5, 10, 10);
				//result=false;
				//repaint();
				//System.out.println( result);
				result=false;
				//a=0;
				//repaint();
			}
			if (result2==true) {
				
				//System.out.println("true detected");
				g2.fillOval(XpointsConfirm.get(size2)-5,YpointsConfirm.get(size2)-5, 10, 10);
				//result=false;
				//repaint();
				//System.out.println( result);
				result2=false;
				//a=0;
				//repaint();
			}
			
			
			if (size >1) {
				//repaint();
				
				for ( int iii=0;iii<=size-2;iii++) {
					g2.setColor(Color.blue);
					g2.drawLine(XpointsConfirm.get(iii),YpointsConfirm.get(iii),XpointsConfirm.get(iii+1),YpointsConfirm.get(iii+1));
					
					iii++;
					
				
			}
			//repaint();	
			}
			//repaint();	
			//count++;	
			//result = false;
		}
			
		
	public static void find_point() {
	System.out.println("i mam in create point method");
	}
	
	
		
	
	public static void main(String[] args) {
		
		    JFrame jFrame = new JFrame();
	        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        jFrame.setSize(600, 600);
	        jFrame.add(new DrawPa());
	        jFrame.setVisible(true);
			label = new JLabel(" ");
			label.setLocation(100, 100);
			label.setHorizontalAlignment(JLabel.RIGHT);
			jFrame.add(label, BorderLayout.SOUTH);
			
			
				
			
		
		//EventQueue.invokeLater(new Runnable() {
			
			//@Override
			//public void run() {
				
				
				
			}
	
		//});
	}

		
	//}
	
	
	




