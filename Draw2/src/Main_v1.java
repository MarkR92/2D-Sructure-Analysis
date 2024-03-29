import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Main_v1 extends JFrame{
	
	private Toolbar toolbar;
	private DrawPanel drawPanel;
	private LabelPanel labelPanel;
	private FixturePopupPanel fixturepane;
	private ForcePopupPanel forcepane;
	private ResultPopupPanel resultpane;
	private FixtureType fixturetype;
	private JFileChooser fileChooser;
	private Refresh refresh;
	private File currentfile;
	private MenuBar menubar;
	
	private double R[];
	private double U[];
	private double Rlocal[];
	
	
	private double zoom = 1d;
	private Point current ;
	private Point last;
	
	private boolean toAdd = true;
	private int count3=0;

	
	public Main_v1() {

		toolbar = new Toolbar();
		drawPanel = new DrawPanel();
		menubar = new MenuBar();
		labelPanel = new LabelPanel();
		fixturetype = new FixtureType();
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new StructureFileFilter());
		fixturetype.Free(); //all nodes start free
		
		JFrame frame = new JFrame(); // Instance of a JFrame
		
		//JLabel currentfile = new JLabel("Loaded: ");
		frame.setSize(800, 800);
		
		frame.add(drawPanel);
	    
	
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		frame.add(toolbar, BorderLayout.NORTH); // add tool-bar
		frame.add(labelPanel, BorderLayout.SOUTH);
		//frame.add(menubar,BorderLayout.NORTH);
		frame.setJMenuBar(menubar); // add menu-bar
		
		drawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		if(drawPanel.isRefreshed()==true) {
		  refresh = new Refresh();
		
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	menubar.setMenuBarListener(new MenuBarListener() {
				
		@Override
		public void stringEmitted(String result) {
			
			System.out.println(result);
			if (result == "New") { 
		

				
			}
			if (result == "Open") { 
				
				if (fileChooser.showOpenDialog(Main_v1.this) == JFileChooser.APPROVE_OPTION) {
					
					try {
						loadFromFile(fileChooser.getSelectedFile());
						labelPanel.setLoadedLabelText(fileChooser.getSelectedFile().getName());
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main_v1.this, "Could not Load", "Error", JOptionPane.ERROR_MESSAGE);;
					}
					
				}
				drawPanel.repaint();
				
			}
			if (result == "Save") { 
				
				if (fileChooser.showSaveDialog(Main_v1.this)== JFileChooser.APPROVE_OPTION) {
				try {
					saveToFile(getLoadedFile());
					//System.out.println(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Main_v1.this, "Could not Save", "Error", JOptionPane.ERROR_MESSAGE);;
				}
			}
			
			
		
				
			}
			if (result == "SaveAs") { 
				if (fileChooser.showSaveDialog(Main_v1.this)== JFileChooser.APPROVE_OPTION) {
					try {
						saveToFile(fileChooser.getSelectedFile());
						//System.out.println(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main_v1.this, "Could not Save", "Error", JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
			if (result == "Exit") { 
				int action = JOptionPane.showConfirmDialog(Main_v1.this, 
						"Do you really want to close the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				
				if(action == JOptionPane.OK_OPTION) {
					System.exit(0);	
				}
			}
			
		}
		
	});

		toolbar.setToolBarListener(new ToolBarListener() {	//Listen for a button on the toolbar to be pressed
			@Override
			
			public void stringEmitted(String result) { 				//result from tool-bar button
			//System.out.println(result);
				if (result == "Select") { 
					
					for (Node node : drawPanel.getNodes()) {
						node.setSelected(true);
					}
					
					for (Member member : drawPanel.getMembers()) {
						member.setSelected(true);
					}
					
					for (Forces force : drawPanel.getForces()) {
						force.setSelected(true);
					}
						
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (result == "Calculate") { 
					
					drawPanel.deleteReactions();
					drawPanel.deleteDisplacements();
					
					
					CreateGlobalMatrix globalK = new CreateGlobalMatrix(drawPanel.getMemberDOF());									//create dof by dof global matrix (globalK).
					
					double [][]TempGlobalK = new double[drawPanel.getMemberDOF()][drawPanel.getMemberDOF()];
					double [][]TempGlobalK2 = new double[drawPanel.getMemberDOF()][drawPanel.getMemberDOF()];
					
					for (Member member : drawPanel.getMembers()) {		
						
						if(drawPanel.isRefreshed()==false) {
							member.calculateNodeDOFList();
						}
					globalK.blowupLocalk2(member.getLocalK(),member.getNodeDOFList());											 //blowup localk(6 by 6) up into globalk(dof by dof)
					
					globalK.addLocalStiffness(TempGlobalK,TempGlobalK2);														 //add all globalk's together to create globalK
					
					}

					globalK.reduceGlobalk(drawPanel.getFixtureType());															//reduce globalK depending on fixtures
					
					Reactions reactions = new Reactions(drawPanel.getMemberDOF(), globalK.getReducedDOF());
					
					CalculateInvMatrix invmatrix = new CalculateInvMatrix();
					//invmatrix.setN(6);
					
					
					
				
					
						
////////////////////////////////////////////////////Member Forces////////////////////////////////////////////////////////
					double []TempForceF = new double[drawPanel.getMemberDOF()];
					for (Forces force : drawPanel.getForces()) {
					for (Member member : drawPanel.getMembers()) {
						
					
						
					//System.out.println(drawPanel.getForces().size());
						if (force.getType().matches("Point") && member.getbounds().contains(force.getLocation())) {
							
							reactions.calculateMemberReaction(force.getMagnitude(),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd() );
					
							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
							double [][]inv = new double[6][6]; // To store inverse of A[][] 
							
					    	invmatrix.adjoint(member.getBeta(), adj); 
						 
						    if (invmatrix.inverse(member.getBeta(), inv)) {// check for singularity
						    	
						    	
						    	reactions.globalMemberForceVector( inv,member.getNumber());
						    	reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getNodeDOFList());
						    
						    }
						    
						}
						else {
							
							
							//System.out.println("here force");
							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
							double [][]inv = new double[6][6]; // To store inverse of A[][] 
							reactions.localMemberForceVector();
							
							invmatrix.adjoint(member.getBeta(), adj); 
							if (invmatrix.inverse(member.getBeta(), inv)) {// check for singularity
						    	
						    	
						    	reactions.globalMemberForceVector( inv,member.getNumber());
						    	reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getNodeDOFList());
						    
						    }
//							reactions.calculateMemberReaction(0,force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd());
//							
//							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
//							double [][]inv = new double[6][6]; // To store inverse of A[][] 
//							
//					    	invmatrix.adjoint(member.getBeta(), adj); 
//						 
//						    if (invmatrix.inverse(member.getBeta(), inv)) { //check for singularity
//						   
//						    
//						    
//						    reactions.globalMemberForceVector( inv);
//						    
//
//						    reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getNodeDOFList());
					//}
							
							
						}
						
						if (member.getNumber()== force.getNumber() && force.getType().matches("UDL")) {
							
							reactions.calculateMemberReaction((force.getMagnitude()),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd());
						//System.out.println("here UDL");
							//reactions.memberReactionVector();
							
							double [][]adj = new double[6][6]; // To store adjoint of A[][]   
						    double [][]inv = new double[6][6]; // To store inverse of A[][] 
						    
						    invmatrix.adjoint(member.getBeta(), adj); 
							
							  if (invmatrix.inverse(member.getBeta(), inv)) 
								  
								  
								  
							      reactions.globalMemberForceVector(inv,member.getNumber());
							    
							  
							  reactions.blowupLocalMemberForceVector(reactions.getGlobalForce(), member.getNodeDOFList());
							
							}
						
				
						reactions.addLocalMemberForces(TempForceF);
						
						}
				
					}
					
					double []TempForceQ = new double[drawPanel.getMemberDOF()];
				
					
					for (Node n : drawPanel.getFilteredNodes()) {
						
////////////////////////////////////////////////////Node Forces////////////////////////////////////////////////////////						
						for (Forces f : drawPanel.getForces()) {
							
							if (n.getNodeNumber() == f.getNumber() && f.getType().matches("Moment")) {

								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getMemberDOF(),n.getNodeNumber(),TempForceQ,f.getType(),f.getDirection2());
							}else {
							//	reactions.nodeReactionVector((0),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
								
							}
							if (n.getNodeNumber() == f.getNumber() && f.getType().matches("Point") && f.getLocation().x == n.getMidPoint().x && f.getLocation().y == n.getMidPoint().y) {
							
								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getMemberDOF(),n.getNodeNumber(),TempForceQ,f.getType(),f.getDirection2());
							}else {
								//reactions.nodeReactionVector((0),drawPanel.getBeamdof(),n.getNumber(),TempForceQ,f.getType(),f.getDirection2());
								
							}


							}
				
						}
					
					reactions.subtractNodeForces();
					
					//reactions.reduceForceVector(drawPanel.getFixtureType());
			
					if(globalK.getReducedDOF()!=0) {
						
					reactions.reduceForceVector(drawPanel.getFixtureType());
						
					double [][]adj = new double[globalK.getReducedDOF()][globalK.getReducedDOF()]; // To store adjoint of A[][] 
				    double [][]inv = new double[globalK.getReducedDOF()][globalK.getReducedDOF()]; // To store inverse of A[][] 
				    
				    invmatrix.setN( globalK.getReducedDOF());
				 
				    invmatrix.adjoint(globalK.getReducedK(), adj); 
					 
					    if (invmatrix.inverse(globalK.getReducedK(), inv)) {
					    	
					    }
					   // g.get
					    Displacements d = new Displacements( drawPanel.getMemberDOF(), globalK.getReducedDOF());
					    
					    d.calculateDeflections(inv, reactions.getReducedForceVector());
					  
					     d.blowupDisplacementVector(drawPanel.getFixtureType());
					   
					     U = d.getDisplacmentVector();
				
					    
					    R = reactions.calculateGlobalReaction(globalK.getGlobalK(), d.getDisplacmentVector());
					    
					    
					   // drawPanel.addReaction(new Reactions(drawPanel.getBeamdof(), g.getreducedDOF()));
					    
					
					    for (Member member : drawPanel.getMembers()) {
					    	
					    	reactions.calculateLocalReactions(member.getLocalKPrime(), d.localDeflections(member.getNumber(),member.getBeta()),member.getNumber());
					    	//r(b.getLocalKPrime(), d.getDisplacmentVector());
					    	
					    	//drawPanel.addShear(new DrawShear(reactions.getLocalReactions(), member.getMemberStart(),member.getMemberEnd(),member.getnodesList(),member.getnodeDOFList()));
					//	System.out.println(reactions.getForceType());
					    	drawPanel.addShearResults(reactions.getLocalReactions(),member.getForceType());
					    }
				
				}
				}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(result == "Result") {
					
					resultpane = new ResultPopupPanel(); 	//Instance of Popup Panel
					resultpane.createPopup(); 
					
					
					if ( resultpane.getResultType() == "Reactions") {
						
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						drawPanel.deleteShearDiagram();
						
						int nodecount = 0;
						for(Node n:drawPanel.getFilteredNodes()) {
							drawPanel.addReaction( new DrawReactions(R,nodecount,n.getX(),n.getY(), drawPanel.getFilteredNodes().size()));
							nodecount++;
							
					}
						
					
					}
					if ( resultpane.getResultType() == "Displacements") {
						
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						drawPanel.deleteShearDiagram();
					
						
						for (Member member:drawPanel.getMembers()) {
							drawPanel.addDisplacement(new DrawDisplacement(U, member.getMemberStart(),member.getMemberEnd(),member.getnodesList(),member.getNodeDOFList()));
							
						}
					
					}
					
					if ( resultpane.getResultType() == "Shear Diagram") {
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						drawPanel.deleteShearDiagram();
						
						
						
						for (Member member:drawPanel.getMembers()) {
							//System.out.println(drawPanel.getForceType().get(member.getNumber()));
							drawPanel.addShear(new DrawShear(drawPanel.getShearResults().get(member.getNumber()),drawPanel.getForceType().get(member.getNumber()), member.getMemberStart(),member.getMemberEnd(),member.getNumber(),member.getAngle(),member.getMidPoint(), member.getSlope(),member.getLength()));
							
						}
						
						
					}
					if ( resultpane.getResultType() == "Bending Diagram") {
						drawPanel.deleteReactions();
						drawPanel.deleteDisplacements();
						drawPanel.deleteShearDiagram();
					}
					
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (result == "Delete") { 
					
				drawPanel.deleteNode(); 		// Delete selected node
				drawPanel.deleteMember(); 		// Delete selected beam
				drawPanel.deleteForce(); 		// Delete selected point load
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
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),n.getNodeNumber(),n.getMidPoint(), 0.0, n.getCoord(),n.getCoord()));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  //System.out.println(n.getMidPoint() + "here");
						  if (forcepane.getForce() == "Moment") { //Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",n.getNodeNumber(),n.getMidPoint(), 0.0,n.getCoord(),n.getCoord()));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  
					}
					
				}
				for (Member member : drawPanel.getMembers()) { //Iterate through each memeber
					
					if (result == "Force" && member.isSelected()) { // check if force is pressed and if a memeber was selected
						
						  forcepane = new ForcePopupPanel(); //Instance of Popup Panel
						  forcepane.createPopup();  //Force popup panel is displayed
					
						  
						  
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
									
									member.setForceType(forcepane.getForce());
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
				
				
				labelPanel.setCorordinateLabelText((double)me.getX(), (double)me.getY());


				for (Node n : drawPanel.getFilteredNodes()) {// iterate through each node});
					
					
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

				for (Member member : drawPanel.getMembers()) {// iterate through each beam
					
						if(drawPanel.isRefreshed()==true) {
						
						member.setStart(3);
						member.setEnd(4);
						
						drawPanel.setBeamdof(3);
						//drawPanel.refresh(false);
						//member.getnodeDOFList();
					};
					
					if (member.getbounds().contains(me.getPoint())) { // is mouse near beam
						if (!member.isHighlighted()) {
							member.setHighlighted(true);
							//System.out.println(b.getLength2());
							// System.out.println(b.isHighlighted(););
						}

					} else {

						member.setHighlighted(false);
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
						if(f.isSelected() && member.getbounds().contains(me.getPoint()) && member.getNumber() == f.getNumber() ) {
							f.setLocation(me.getPoint().x,me.getPoint().y);
							
						}
						
					}
				}

				drawPanel.repaint();
			}
			
		});

		drawPanel.addMouseListener(new MouseAdapter() {
			
			int count = 0;
			int	count2 = 0;
			
			private int start;
			private int end;
			
			CalculateNodeNumber currentnodenumber = new CalculateNodeNumber(); //Keeps track of current nodenumber and how/if it will be modified.
			
			public void mousePressed(MouseEvent e) {
			
				if(toolbar.isdrawing) {
				last = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
				}
				
				if(toolbar.isdrawing) {
				drawPanel.addTempMember(new TempMember(current,last));
				}
				
				for (Member member : drawPanel.getMembers()) {
					member.calculateYintercept();
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

				for (Node n : drawPanel.getFilteredNodes()) {// iterate through each node
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
				
				int nodeRadius = 10;

				int nodex = drawPanel.getSnapX();
				int nodey = drawPanel.getSnapY();

				// check that we dont go offscreen by subtarcting its radius unless its x and y
				// are not bigger than radius
				if (nodey > nodeRadius) {
					nodey -= nodeRadius;
				}
				if (nodex > nodeRadius) {
					nodex -= nodeRadius;
				}

				if (toolbar.isdrawing == true) { // check if we are drawing
					
					for (Node node : drawPanel.getFilteredNodes()) { // check all nodes
						
						if(drawPanel.isRefreshed()==true) {
							//refresh.refreshDOF(dof);
							//node.getDOF();
							
							currentnodenumber.setNodeNumber(drawPanel.getFilteredNodes().size()+1);
							currentnodenumber.setOldNodeNumber(drawPanel.getFilteredNodes().size()+1);
							
							drawPanel.setRefresh(false);
							
						}
						
						if (node.isHighlighted()) { 		// if node is highlighted and mouse is pressed mouse will snap to node.
							
							nodex = node.getX();
							nodey = node.getY();
												
							currentnodenumber.setNodeNumber(node.getNodeNumber());
							
								toAdd = false;
					
						}
						
					
						
					}
					
					drawPanel.addNode(new Node(nodex, nodey, nodeRadius, currentnodenumber.getNodeNumber(),toAdd));// add node to panel to be drawn
					
					toAdd = true;
					
					drawPanel.setNodeNum(currentnodenumber.getNodeNumber());
				
					
					count++;
				
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
							drawPanel.addMember(new Member(x1, y1, x2, y2,count2, currentnodenumber.getNodeNumber(), drawPanel.getNodeNum(),start,end));
							//System.out.println(drawPanel.getNodeNum());
							drawPanel.setBeamdof(currentnodenumber.getNodeNumber());
							
							count = 0;
							count3 = 0;
							count2++;
							
							
						}

					}

						currentnodenumber.incrementNodeNumber();
				}
				
			}
			
			
		});

	

		frame.setVisible(true);

	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void saveToFile (File file) throws IOException{
		drawPanel.saveToFile(file);
		
	}
	public void loadFromFile (File file) throws IOException{
		
		drawPanel.loadFromFile(file);
		setLoadedFile(file);
	
	}
	
	public void setLoadedFile(File file )throws IOException{
		this.currentfile=file; 
		
	}
	public File getLoadedFile()throws IOException{
		return currentfile; 
		
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
