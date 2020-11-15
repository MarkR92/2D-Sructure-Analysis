
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel_v2 extends JPanel {

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
	int col,rows;
	int scl= 10;
	int w= 600;
	int h = 600 ;
	
	int r= 5;
	
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
	static boolean resultg;
	boolean resultg2 = false;
	public boolean result;
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
		public DrawPanel_v2()   {
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
			
			
			//Create a grid to be displayed JFrame
			CreateGrid grid = new CreateGrid();
			grid.createGridList();
			grid.gridIntersections();
			
			ArrayList<Integer> xx=CreateGrid.XInter;
			ArrayList<Integer> yy=CreateGrid.YInter;
			int ss=xx.size();
			//System.out.println(ss);
			setBackground(Color.WHITE);
			
			addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e)
				{
					
					label.setText("["+ e.getX() + " , " +e.getY()+"]");
					xc =e.getX();
					yc =e.getY();
					for (ii=0; ii<ss; ii++)	{
						
						
						//Check if mouse is near a grid intersect.
						SelectGraphic sg3 =
									
									new SelectGraphic(xc,yc, xx.get(ii)-1, yy.get(ii)-1, xx.get(ii)+1, yy.get(ii)+1);
						sg3.isSelected();
									 result=sg3.result;
								    
									//If mouse is near a grid intersect snap to intersect.
									if(sg3.result == true) {
										
										SnaptoGrid snap1 = new SnaptoGrid (xx.get(ii),yy.get(ii), sg3.result);
										
										snap1.snap();
										
									 }
									
								
								}
					for (iii=0;iii<=size2-1;iii++) {
						int xpm= Xnearpreviousminus.get(iii);
						int ypm= Ynearpreviousminus.get(iii);
						int xpp= Xnearpreviousplus.get(iii);
						int ypp= Ynearpreviousplus.get(iii);
													
						SelectGraphic sg1 =
									new SelectGraphic(e.getX(), e.getY(), xpm, ypm, xpp, ypp);
									sg1.isSelected();
									
									
									if (sg1.result == true) {
										resultg=true;
										nx=Xnearpreviousminus.indexOf(Xnearpreviousminus.get(iii));
										ny=Ynearpreviousminus.indexOf(Ynearpreviousminus.get(iii));
									}
							
							
									
						 SelectGraphic sg2 =
									new SelectGraphic(e.getX(), e.getY(), xcurrent-r,  ycurrent-r,  xcurrent+r, ycurrent+r);
									sg2.isSelected();
									
									boolean result2=sg2.result;
									
									
									if (result2 == true) {
										resultg2=true;
										
									}
								
									
									
						repaint();
						
							}
					
					
				}
				
				
				
			})
			;
			
				
				
			
			
			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					
					
					
					
				
					Xpoints.add(e.getX());
					
					size= Xpoints.size();
					
					Ypoints.add(e.getY());
				
					
					
					
					if (click==1 || click ==2) {
						
					
						xprevious=Xpoints.get(click-1);
						yprevious=Ypoints.get(click-1);
						xnearpreviousminus=xprevious-r;
					
						
						xnearpreviousplus=xprevious+r;
						
						
						ynearpreviousminus=yprevious-r;
						
						
						ynearpreviousplus=yprevious+r;
						
						Xnearpreviousminus.add(xnearpreviousminus);
						Xnearpreviousplus.add(xnearpreviousplus);
						Ynearpreviousminus.add(ynearpreviousminus);
						Ynearpreviousplus.add(ynearpreviousplus);//click++;
						
					}
					click++;
					count++;
					
					for ( int i=0;i<=size-1;i++) {
					
						
						
					x= Xpoints.get(i);
					y= Ypoints.get(i);
					
					
					xt=XpointsConfirm.contains(x);
					yt=YpointsConfirm.contains(y);	
						
					}
						
				
				
						
						XpointsConfirm.add(x);
						YpointsConfirm.add(y);
						
						if (c>1) {
						xprevious= XpointsConfirm.get(c2-1);
						yprevious= YpointsConfirm.get(c2-1);
						
						
						xnearpreviousminus=xprevious-r;
						Xnearpreviousminus.add(xnearpreviousminus);
						
						xnearpreviousplus=xprevious+r;
						Xnearpreviousplus.add(xnearpreviousplus);
						
						ynearpreviousminus=yprevious-r;
						Ynearpreviousminus.add(ynearpreviousminus);
						
						ynearpreviousplus=yprevious+r;
						Ynearpreviousplus.add(ynearpreviousplus);
						}
					
						c2++;
						
					
				
						
					
						
							
					size =XpointsConfirm.size();
					
					
					
					
					for (ii=0;ii<=size-2;ii++) {
						
						
						
							
						xcurrent= e.getX();
						ycurrent= e.getY();
						
						
						size2 = Xnearpreviousminus.size();
						
					
						c++;
						
						
						
						
						
						for (iii=0;iii<=size2-1;iii++) {
					if (xcurrent > Xnearpreviousminus.get(iii) && xcurrent < Xnearpreviousplus.get(iii) && ycurrent > Ynearpreviousminus.get(iii) && ycurrent < Ynearpreviousplus.get(iii)) {
						
							n1=Xnearpreviousminus.indexOf(Xnearpreviousminus.get(iii));
							n2=Ynearpreviousminus.indexOf(Ynearpreviousminus.get(iii));
							
							
							
							XpointsConfirm.set(click-1, XpointsConfirm.get(n1));
							YpointsConfirm.set(click-1, YpointsConfirm.get(n2));
							
					}
						}	
							
						
						
					
					}
					size =XpointsConfirm.size();
			
					
					
					repaint();
					
				
				}
			
			});
			
	
		
			
		}
		
		public void paintComponent(Graphics g)  {
			boolean r = DrawPanel_v2.resultg;
			super.paintComponent(g);
			
			//super.mouseMoved();
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//CreateGrid grid2 = new CreateGrid();
			
			ArrayList<Integer> x=CreateGrid.XGridpoints;
			ArrayList<Integer> y=CreateGrid.YGridpoints;
			
			int s= x.size();
			 
			for (int i= 0; i<s; i++) {
				for (int ii = 0; ii<s; ii++) {
					g2.setColor(Color.lightGray);
					g2.drawRect(x.get(i), y.get(ii),10, 10);
					if (click<1) {
					//System.out.println("HERE");
					}
					
			}
		}
			
			
					 
			
					 
			for ( int i=0;i<=size-1;i++) {
				
			g2.setColor(Color.red);
			g2.drawOval(XpointsConfirm.get(i)-5,YpointsConfirm.get(i)-5, 10, 10);	
			
	
			
			
			}
			 
			
			
			if (resultg==true) {
				
				
				g2.fillOval(XpointsConfirm.get(nx)-5,YpointsConfirm.get(ny)-5, 10, 10);
				
				resultg=false;
				
			}
			if (resultg2==true) {
				
				
				g2.fillOval(XpointsConfirm.get(size2)-5,YpointsConfirm.get(size2)-5, 10, 10);
				
				resultg2=false;
				
			}
			
			
			if (size >1) {
				
				
				for ( int iii=0;iii<=size-2;iii++) {
					g2.setColor(Color.blue);
					g2.drawLine(XpointsConfirm.get(iii),YpointsConfirm.get(iii),XpointsConfirm.get(iii+1),YpointsConfirm.get(iii+1));
					
					iii++;
					
				
			}
			
			}
			
		}
			
		
		

	
	public static void main(String[] args) {
		
		    JFrame jFrame = new JFrame();
	        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        jFrame.setSize(612, 652);
	        jFrame.add(new DrawPanel_v2());
	        jFrame.setVisible(true);
			label = new JLabel(" ");
			label.setLocation(100, 100);
			label.setHorizontalAlignment(JLabel.RIGHT);
			jFrame.add(label, BorderLayout.SOUTH);
			
			
			
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				
				
			}
	
		});

	}
}

		
	//}
	
	
	




