import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Main_v1 extends JFrame{
	private Toolbar toolbar;
	private ContainerPanel containerPanel;
	private DrawPanel drawPanel;
	private FixturePopupPanel fixturepane;
	private ForcePopupPanel forcepane;
	private ResultPopupPanel resultpane;
	private FixtureType fixturetype;
	private JFileChooser fileChooser;
	
	private double R[];
	private double U[];
	private double[][] G;
	
	private double zoom = 1d;
	private int zoom2 = 1;
	private Point current ;
	private Point last;
	//Point p = new Point();
	private boolean toAdd = true;
	private int c;
	private int count3=0;
//	private int dof;
	
	public Main_v1() {

		toolbar = new Toolbar();
		containerPanel = new ContainerPanel();
		drawPanel = new DrawPanel();
		fixturetype = new FixtureType();
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new StructureFileFilter());
		fixturetype.Free(); //all nodes start free
		//fixture = "Free";
		
		JFrame frame = new JFrame(); // Instance of a JFrame
		JLabel label = new JLabel(" "); // Instance of a JLabel
		
		frame.setSize(800, 800);
		
	
		// containerPanel.add(drawPanel);
		
		frame.add(drawPanel);
	    
	
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(toolbar, BorderLayout.NORTH); // add tool-bar



		frame.setJMenuBar(createMenuBar()); // add menu-bar
	//	
		
		  drawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		   // frame.add(scroller);
		
		
		
		
	
		
		
	//	frame.setVisible(true);
		//frame.add(containerPanel);

		// Listen for a button on the toolbar to be pressed
		
		toolbar.setToolBarListener(new ToolBarListener() {
			@Override
			public void stringEmitted(String result) { //result from tool-bar button
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
				if (result == "Select") { 
					
					for (Node n : drawPanel.getNodes()) {
						n.setSelected(true);
					}
					for (Member member : drawPanel.getMembers()) {
						member.setSelected(true);
					}
					for (Forces f : drawPanel.getForces()) {
						f.setSelected(true);
					}
						
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (result == "Calculate") { 
					
					drawPanel.deleteReactions();
					drawPanel.deleteDisplacements();
					
					CreateGlobalMatrix g = new CreateGlobalMatrix(drawPanel.getBeamdof());									//create dof by dof global matrix (globalK).
					
					double [][]TempGlobalK = new double[drawPanel.getBeamdof()][drawPanel.getBeamdof()];
					double [][]TempGlobalK2 = new double[drawPanel.getBeamdof()][drawPanel.getBeamdof()];
					
					for (Member member : drawPanel.getMembers()) {																	//for all beam objects
				
				//	g.blowupLocalk(b.getLocalK(),b.getnodesList());															//blowup localk(6 by 6) up into globalk(dof by dof)
					
					g.blowupLocalk2(member.getLocalK(),member.getnodeDOFList());
					
					g.addLocalStiffness(TempGlobalK,TempGlobalK2);
					}
					
					//g.addLocalStiffness2();																					//add all globalk's together to create globalK
					
					
					g.reduceGlobalk(drawPanel.getFixtureType());															//reduce globalK depending on fixtures
					
					
					
					Reactions reactions = new Reactions(drawPanel.getBeamdof(), g.getreducedDOF());
					
					CalculateInvMatrix invmatrix = new CalculateInvMatrix();
					invmatrix.setN(6);
					
				//	GFG2 gfg2= new GFG2();
					double []TempForceF = new double[drawPanel.getBeamdof()];
					for (Member member : drawPanel.getMembers()) {
						
////////////////////////////////////////////////////Member Forces////////////////////////////////////////////////////////
					for (Forces f : drawPanel.getForces()) {
					//System.out.println(b.getBounds().getCenterX()+","+b.getBounds().getCenterY());
					//System.out.println(b.getBounds().getCenterY());
					//System.out.println(f.getLocation());
						
						if (member.getNumber() == f.getNumber() && f.getType()=="Point" && member.getbounds().contains(f.getLocation())) {
						System.out.println("true");
							reactions.calculateMemberReaction(f.getMagnitude(),f.getType(), member.getLength(), f.getLocation(), member.x1, member.x2,member.y1,member.y2 );
					
							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
							double [][]inv = new double[6][6]; // To store inverse of A[][] 
							
					    	invmatrix.adjoint(member.getBeta(), adj); 
						 
						    if (invmatrix.inverse(member.getBeta(), inv)) 
						   
						    reactions.globalForce( inv, reactions.memberReactionVector());
						    
						   // reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), b.getnodesList());
						    reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getnodeDOFList());
						    
						}else {
							reactions.calculateMemberReaction(0,f.getType(), member.getLength(), f.getLocation(), member.x1, member.x2,member.y1,member.y2 );
							
							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
							double [][]inv = new double[6][6]; // To store inverse of A[][] 
							
					    	invmatrix.adjoint(member.getBeta(), adj); 
						 
						    if (invmatrix.inverse(member.getBeta(), inv)) 
						   
						    reactions.globalForce( inv, reactions.memberReactionVector());
						    
						 //   reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), b.getnodesList());
						    reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getnodeDOFList());
							
							
							
						}
						
						if (member.getNumber()== f.getNumber() && f.getType()=="UDL") {
							
							reactions.calculateMemberReaction((f.getMagnitude()),f.getType(), member.getLength(), f.getLocation(), member.x1, member.x2,member.y1,member.y2);
						
							//reactions.memberReactionVector();
							
							double [][]adj = new double[6][6]; // To store adjoint of A[][]   
						    double [][]inv = new double[6][6]; // To store inverse of A[][] 
						    
						    invmatrix.adjoint(member.getBeta(), adj); 
							
							  if (invmatrix.inverse(member.getBeta(), inv)) 
							     
							    reactions.globalForce( inv, reactions.memberReactionVector());
							    
							   // reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), b.getnodesList());
							  reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getnodeDOFList());
							
							}
						
						
						//double []TempForceF = new double[drawPanel.getBeamdof()];
						//reactions.addLocalForces2();
						//System.out.println("here");
						//if(reactions.memberReactionVector()!=null){
							//System.out.println(reactions.memberReactionVector());
						reactions.addLocalMemberForces(TempForceF);
						//}
						}
					//reactions.addLocalForces(TempForceF);
					}
					
					double []TempForceQ = new double[drawPanel.getBeamdof()];
				
					
					for (Node n : drawPanel.getFilteredNodes()) {
						
////////////////////////////////////////////////////Node Forces////////////////////////////////////////////////////////						
						for (Forces f : drawPanel.getForces()) {
							
							if (n.getNumber() == f.getNumber() && f.getType()=="Moment") {

								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
							}else {
							//	reactions.nodeReactionVector((0),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
								
							}
							if (n.getNumber() == f.getNumber() && f.getType()=="Point" && f.getLocation().x == n.getMidPoint().x && f.getLocation().y == n.getMidPoint().y) {
							
								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
							}else {
								//reactions.nodeReactionVector((0),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
								
							}


							}
				
						}
					
					reactions.subtractNodeForces();
					
					//reactions.reduceForceVector(drawPanel.getFixtureType());
			
					if(g.getreducedDOF()!=0) {
						
					reactions.reduceForceVector(drawPanel.getFixtureType());
						
					double [][]adj = new double[g.getreducedDOF()][g.getreducedDOF()]; // To store adjoint of A[][] 
				    double [][]inv = new double[g.getreducedDOF()][g.getreducedDOF()]; // To store inverse of A[][] 
				    
				    invmatrix.setN( g.getreducedDOF());
				 
				    invmatrix.adjoint(g.getReducedK(), adj); 
					 
					    if (invmatrix.inverse(g.getReducedK(), inv)) {
					    	
					    }
					   // g.get
					    Displacements d = new Displacements( drawPanel.getBeamdof(), g.getreducedDOF());
					    
					  d.calculateDeflections(inv, reactions.getReducedForceVector());
					  
					     d.blowupDisplacementVector(drawPanel.getFixtureType());
					   
					     U = d.getDisplacmentVector();
					    //drawPanel.addReaction(R);
					    
					    R = reactions.calculateGlobalReaction(g.getGlobalK(), d.getDisplacmentVector());
					    
					    
					   // drawPanel.addReaction(new Reactions(drawPanel.getBeamdof(), g.getreducedDOF()));
					    
					
					    for (Member member : drawPanel.getMembers()) {
					    	
					    	d.localDeflections(member.getNumber());
					    	
					    	//r(b.getLocalKPrime(), d.getDisplacmentVector());
					    }
					  //  d.localDeflections(drawPanel.get);
					  // R= reactions.getReactions();
					  //  reactions.drawReaction(gp, b, angle);
				}
				}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(result == "Result") {
					
					resultpane = new ResultPopupPanel(); 	//Instance of Popup Panel
					resultpane.createPopup(); 
					
					
					if ( resultpane.getResultType() == "Reactions") {
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						
						int nodecount = 0;
						for(Node n:drawPanel.getFilteredNodes()) {
							drawPanel.addReaction( new DrawReactions(R,nodecount,n.getX(),n.getY(), drawPanel.getFilteredNodes().size()));
							nodecount++;
							
					}
						
					
					}
					if ( resultpane.getResultType() == "Displacements") {
						
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						
					
						
						for (Member member:drawPanel.getMembers()) {
							drawPanel.addDisplacement(new DrawDisplacement(U, member.getMemberStart(),member.getMemberEnd(),member.getnodesList(),member.getnodeDOFList()));
							
						}
					
					}
					
					if ( resultpane.getResultType() == "Shear Diagram") {
						
					}
					if ( resultpane.getResultType() == "Bending Diagram") {
						
					}
					
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (result == "Delete") { 
					
				drawPanel.deleteNode(); // Delete selected node
				drawPanel.deleteMember(); // Delete selected beam
				drawPanel.deleteForce(); // Delete selected point load
				drawPanel.deleteFilteredNode();
				
				drawPanel.repaint(); // Update graphics
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				for (Node n : drawPanel.getFilteredNodes()) { //Iterate through each node
					
				if (result == "Fixture" && n.isSelected()) { // check if fixture is pressed and if a node was selected
					
					
//					System.out.println(drawPanel.getNodes().get(n.getNumber()).x);
//					System.out.println(drawPanel.getNodes().get(n.getNumber()-1).x);
					
//					if (drawPanel.getNodes().get(n.getNumber()).x == drawPanel.getNodes().get(n.getNumber()-1).x) {
//						System.out.println("true");
//					}
					  fixturepane = new FixturePopupPanel(); 	//Instance of Popup Panel
					  fixturepane.createPopup();  				//Display fixture menu pane
					
								if (fixturepane.isFixture() == "Fixed") {
									
									drawPanel.changeFixture("Fixed", n);
							
								}
								
								if (fixturepane.isFixture() == "Pinned") {
									
									drawPanel.changeFixture("Pinned",n);
									
								}
								
								if (fixturepane.isFixture() == "Sliding") {
									
									drawPanel.changeFixture("Sliding",n);
									//System.out.println("h2");
								}
								
								drawPanel.repaint();
				}
				
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				for (Node n : drawPanel.getFilteredNodes()) {
					
					if (result == "Force" && n.isSelected()) {
						
						forcepane = new ForcePopupPanel(); 				//Instance of Popup Panel
						forcepane.createPopup();  						//Force popup panel is displayed
						  
						  if (forcepane.getForce() == "Point") {		//Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),n.getNumber(),n.getMidPoint(), 0.0, n.getCoord(),n.getCoord()));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  //System.out.println(n.getMidPoint() + "here");
						  if (forcepane.getForce() == "Moment") { //Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",n.getNumber(),n.getMidPoint(), 0.0,n.getCoord(),n.getCoord()));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  
					}
					
				}
				for (Member member : drawPanel.getMembers()) { //Iterate through each beam
					
					if (result == "Force" && member.isSelected()) { // check if force is pressed and if a beam was selected
						//System.out.println("Here");
						  forcepane = new ForcePopupPanel(); //Instance of Popup Panel
						  forcepane.createPopup();  //Force popup panel is displayed
						//b.getNumber();
						  
						  
									if (forcepane.getForce() == "Point") { //Point Force Selected
										//add to Arraylist 
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),member.getNumber(),member.getMidPoint(), member.getAngle(), member.getMemberStart(), member.getMemberEnd()));
										
										member.setSelected(false);
									
										drawPanel.repaint();
										
									}
									if (forcepane.getForce() == "UDL") {
										
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",member.getNumber(), member.getMidPoint(), member.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										drawPanel.repaint();
									}
									if (forcepane.getForce() == "Moment") { //Point Force Selected
										//add to Arraylist 
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",member.getNumber(),member.getMidPoint(), member.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										
										member.setSelected(false);
									
										drawPanel.repaint();
										
									}
									
									
									member.setSelected(false);
								
					}
					
				}
			}
		});
		
//////////////////////////////////////////////////////////////////////////////////////////////
		
		Action highlightAll =  new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		      
		    	//System.out.println("here");
		    	
		    	if(!toolbar.isdrawing) {
		    	for (Node n : drawPanel.getFilteredNodes()) {
					n.setSelected(true);
					}
					for (Member member : drawPanel.getMembers()) {
						member.setSelected(true);
					}
					for (Forces f : drawPanel.getForces()) {
						f.setSelected(true);
					}
				drawPanel.repaint();	
		    }
		    }
		};
	
		drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK),
                "highlightAll");
		
		drawPanel.getActionMap().put("highlightAll",
				highlightAll);
		
///////////////////////////////////////////////////////////////////////////////////////////////	
		
		Action deleteAll =  new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	drawPanel.deleteNode(); 		 // Delete selected node
		    	drawPanel.deleteFilteredNode();
				drawPanel.deleteMember(); 		 // Delete selected beam
				drawPanel.deleteForce(); 		 // Delete selected point load
				//drawPanel.deleteFixture();
				
				drawPanel.repaint(); 
		    	

		    
		    }
		};
		
		drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK),
                "deleteAll");
		
		drawPanel.getActionMap().put("deleteAll",
				deleteAll);
		
///////////////////////////////////////////////////////////////////////////////////////////////	
		
///////////////////////////////////////////////////////////////////////////////////////////////	
		
		Action rotateForce =  new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				for (Forces f : drawPanel.getForces()) {
					if(f.isSelected()) {
						
						f.setAngle();
						
					}
				}
					drawPanel.repaint();
				//System.out.println("Left");

			}
		};

		drawPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK),
				"rotateForce");

		drawPanel.getActionMap().put("rotateForce",
		rotateForce);
		
///////////////////////////////////////////////////////////////////////////////////////////////	
		
		
		
		drawPanel.addMouseWheelListener(new MouseAdapter() { 			//add wheel listener to drawPanel
			 @Override
             public void mouseWheelMoved(MouseWheelEvent e) {			//when wheel is moved
				
				if (e.getPreciseWheelRotation() < 0) {
					
                    zoom -= 0.1;
                } else {
                    zoom += 0.1;
                }

                if (zoom < 0.01) {
                    zoom = 0.01;
                }
            
          drawPanel.setZoom(zoom);
             zoom2 = e.getWheelRotation();   
        drawPanel.updatePreferredSize(e.getWheelRotation(), e.getPoint());
                //grid.setZoom(zoom);
                drawPanel.repaint();
                
			 }
			  
		});
		
		// Listen for mouse movement on JPanel
		drawPanel.addMouseMotionListener(new MouseAdapter() {
			//When mouse is dragged
			public void mouseDragged(MouseEvent de) {
				//System.out.println("here2");
				 
//				int dx = de.getX() - last.x;
//				 
//				  int dy = de.getY() - last.y;
				  
				//  drawPanel.setTranslate(dx,dy);
				  
				 
				  drawPanel.repaint();
			}
			
			public void mouseMoved(MouseEvent me) {
				super.mouseMoved(me);
				
				
				 //drawPanel.updatePreferredSize(zoom2, me.getPoint());
//                drawPanel.repaint();
				//tp.setBeamEnd(me.getPoint());
				if(toolbar.isdrawing) {
					current = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
				}
				//current = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
				
				if(toolbar.isdrawing && last!=null) {
					drawPanel.addTempMember(new TempMember(current,last));
				}
				//current = me.getPoint();
				//drawPanel.addTempMember(new TempMember());
				drawPanel.createGrid(me.getPoint().x, me.getPoint().y);
				
				//temp.getCurrent();
				//temp.drawTemp(null);
				//temp.setCurrent(me.getPoint());
				//temp.getCurrent();
				
				label.setText("[" + ((double)me.getX()/10)/2 + " , " + ((double)me.getY()/10)/2 + "]");



				for (Node n : drawPanel.getNodes()) {// iterate through each node});
					
					//for( int i = 0; i<1; i++) {
					//drawPanel.addTempMember(new TempMember(me.getPoint(),n.getCoord()));
					//}
					//TempMember temp = new TempMember(me.getPoint(), n.getCoord());
					if (n.getBounds().contains(me.getPoint())) {// get the node bounds and check if mouse is within its
																// bounds
						if (!n.isHighlighted()) {// if not already highlighted

							n.setHighlighted(true);// highlight node

						}

					} else {

						n.setHighlighted(false); // un-highlight node

					}

				}
				//drawPanel.repaint();

				for (Member b : drawPanel.getMembers()) {// iterate through each beam
					if (b.getbounds().contains(me.getPoint())) { // is mouse near beam
						if (!b.isHighlighted()) {
							b.setHighlighted(true);
							//System.out.println(b.getLength2());
							// System.out.println(b.isHighlighted(););
						}

					} else {

						b.setHighlighted(false);
					}
					for (Forces f : drawPanel.getForces()) {// iterate through each beam
						if (f.getPointBounds().contains(me.getPoint())) { // is mouse near beam
							if (!f.isHighlighted()) {
								f.setHighlighted(true);
								// System.out.println(f.isHighlighted());
							}

						} else {

							f.setHighlighted(false);
						}
						if(f.isSelected() && b.getbounds().contains(me.getPoint()) && b.getNumber() == f.getNumber() ) {
							f.setLocation(me.getPoint().x,me.getPoint().y);
							
						}
						
					}
				}

				drawPanel.repaint();
			}
			//drawPanel.repaint();
		});

		drawPanel.addMouseListener(new MouseAdapter() {

			private int counter = 1;
			private int counterold = 1;
			int count = 0;
			int	count2 = 0;
			
			int start;
			int end;
			
			public void mousePressed(MouseEvent e) {
			
				if(toolbar.isdrawing) {
				last = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
				}
				
				if(toolbar.isdrawing) {
				drawPanel.addTempMember(new TempMember(current,last));
				}
				
				for (Member member : drawPanel.getMembers()) {
					
					if (member.getbounds().contains(e.getPoint()) && !toolbar.isdrawing) {
						if (!member.isSelected()) {// check if beam has been clicked on
							member.setSelected(true);
							// System.out.println(b.isSelected());
							drawPanel.repaint();
						} else {
							member.setSelected(false);
						}
					}
					
				}
				//

				for (Node n : drawPanel.getNodes()) {// iterate through each node
					//n.createFixtureList();
					if (n.getBounds().contains(e.getPoint()) && !toolbar.isdrawing) {// get the node bounds and check if
																					// mouse click was within its
																						// bounds
						if (!n.isSelected()) {// check if node has been clicked on
							n.setSelected(true);

						}

						else {
							n.setSelected(false);
						}

					}

				}
				for (Forces f : drawPanel.getForces()) {// iterate through each beam
					if (f.getPointBounds().contains(e.getPoint())&& !toolbar.isdrawing) { // is mouse near beam
						if (!f.isSelected()) {
							f.setSelected(true);
							// System.out.println(f.getLocation());
						

					} else {

						f.setSelected(false);
					}
				}
					if (f.isSelected()) {
						//System.out.println(e.getPoint());
						//f.setLocation(e.getPoint());
						drawPanel.repaint();
					
					}
					//f.setSelected(false);
				}
				int ballRadius = 10;

//				int x = e.getX();
//				int y = e.getY();
				int x = drawPanel.getSnapX();
				int y = drawPanel.getSnapY();

				// check that we dont go offscreen by subtarcting its radius unless its x and y
				// are not bigger than radius
				if (y > ballRadius) {
					y -= ballRadius;
				}
				if (x > ballRadius) {
					x -= ballRadius;
				}

				if (toolbar.isdrawing == true) { // check if we are drawing

					for (Node n : drawPanel.getNodes()) { // check all nodes

						// if node is highlighted actual mouse coordinates becomes node coordinates
						if (n.isHighlighted()) {
							x = n.getX();
							y = n.getY();
						
							counter = n.getNumber(); 
							
							
								toAdd = false;
					
						}
						
						
					//	counter =drawPanel.getNodes().size();
					//	n.createFixtureList();
						
						
					}
					
					drawPanel.addNode(new Node(x, y, ballRadius, counter,toAdd));// add node to panel to be drawn
					
					toAdd = true;
					
					drawPanel.setNodeNum(counter);
				
				//System.out.println(drawPanel.getNodeNum());
				
					if (counterold>counter) {
						counterold--;
					}
					counter =counterold;
					
					count++;
					
				//	drawPanel.getNodes().size();
					//System.out.println(drawPanel.getNodeNum());
					
					//int[] beamends;
					//for (Node node:drawPanel.getNodes()) {
						//System.out.println(drawPanel.getNodeNum());
						
						if( count3 == 0) {
							start=drawPanel.getNodeNum();
						}
						
						if( count3 == 1) {
							end =drawPanel.getNodeNum();
						}
						count3++;
					
					if (drawPanel.getNodes().size() > 1) {
					
						
						
						int size = drawPanel.getNodes().size();

						
						int x1 = drawPanel.getNodes().get(size - 1).getX() + 10;
						int y1 = drawPanel.getNodes().get(size - 1).getY() + 10;
						int x2 = drawPanel.getNodes().get(size - 2).getX() + 10;
						int y2 = drawPanel.getNodes().get(size - 2).getY() + 10;
						
						
						if (count == 2) {
							//count2++;
							drawPanel.addMember(new Member(x1, y1, x2, y2,count2, counter, drawPanel.getNodeNum(),start,end));
							//System.out.println(drawPanel.getNodeNum());
							drawPanel.setBeamdof(counter);
							
							count = 0;
							count3 = 0;
							count2++;
							
							
						}

					}

					counter++;// increase the numberNode
					counterold++;

				}
				
			}
			
			
		});

		//frame.add(drawPanel);

		label.setLocation(100, 100);
		label.setHorizontalAlignment(JLabel.RIGHT);

		frame.add(label, BorderLayout.SOUTH);
		// frame.add(draw);
		//frame.pack();

		frame.setVisible(true);

	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void saveToFile (File file) throws IOException{
		DrawP dp = new DrawP();
		dp.saveToFile(file);
	}
	public void loadFromFile (File file) throws IOException{
		DrawP dp = new DrawP();

		dp.loadFromFile(file);
		dp.repaint();
	}
	public void refresh() {
		
	}
	

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem newItem = new JMenuItem("New");
		JMenuItem openItem = new JMenuItem("Open File...");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem saveasItem = new JMenuItem("Save As...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		// ex

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(Main_v1.this) == JFileChooser.APPROVE_OPTION) {
					//System.out.println(fileChooser.getSelectedFile());
					try {
						loadFromFile(fileChooser.getSelectedFile());
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main_v1.this, "Could not Load", "Error", JOptionPane.ERROR_MESSAGE);;
					}
					
				}
				
				
			}
			
		});
		
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(Main_v1.this)== JFileChooser.APPROVE_OPTION) {
					try {
						saveToFile(fileChooser.getSelectedFile());
						//System.out.println(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main_v1.this, "Could not Save", "Error", JOptionPane.ERROR_MESSAGE);;
					}
				}
				
				
			}
			
		});

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(Main_v1.this, 
						"Do you really want to close the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				
				if(action == JOptionPane.OK_OPTION) {
					System.exit(0);	
				}
				

			}

		});
		// fileMenu.addSeparator();

		JMenu editMenu = new JMenu("Edit");
		JMenu windowMenu = new JMenu("Window");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(windowMenu);

		return menuBar;
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main_v1();
				
			}
		});
	}
}
