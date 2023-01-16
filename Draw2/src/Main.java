import java.awt.BorderLayout;

import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Main extends JFrame implements ComponentListener{
	
	private Toolbar toolbar;
	private DrawPanel drawPanel;
	private LabelPanel labelPanel;
	private ParseTextEntry parseText;
	private FixturePopupPanel fixturepane;
	private ForcePopupPanel forcepane;
	private EditForcePopupPanel editforcepane;
	private EditMaterialPopup editmaterial;
	private NodeDisplacmentPopup editDisplacement;
	private MaterialDialog materialCollectionDialog;
	private ResultPopupPanel resultpane;
	private FixtureType fixturetype;
	private JFileChooser fileChooser;
	private CalculateNodeNumber currentnodenumber;
	//private Refresh refresh;
	private File currentfile;
	private MenuBar menubar;
	//private ScrollableGridDisplay grid;
	private MaterialCollection materialCollection;
	
	private String E= "200000000000";
	private String A="0.0006";
	private String I="0.00006";
	private String Name="Default";
	
	private double R[];
	private double U[];
	
	
	
	private double zoom = 1d;
	private Point current ;
	private Point last;
	
	private boolean toAdd = true;
	private int count3=0;
	
	int	memberNumber = 0;
	int start2;
	int end2;
	private int startNode;
	private int endNode;
	
	public Main() {

		toolbar = new Toolbar();
		drawPanel = new DrawPanel();
		menubar = new MenuBar();
		labelPanel = new LabelPanel();
		fixturetype = new FixtureType();
		fileChooser = new JFileChooser();
		materialCollectionDialog= new MaterialDialog(this);
		materialCollection = new MaterialCollection();
		materialCollection.addDefaultMaterial(new Material(E,A,I,Name));
		currentnodenumber = new CalculateNodeNumber(); //Keeps track of current nodenumber and how/if it will be modified.
		//ScrollableGridDisplay grid = new ScrollableGridDisplay();
		fileChooser.addChoosableFileFilter(new StructureFileFilter());
		fixturetype.Free(); //all nodes start free
		
		
		JFrame frame = new JFrame(); // Instance of a JFrame
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setSize(800, 800);
		//drawPanel.setSize(frame.getSize());
		//System.out.println(frame.getSize());
		JPanel containerPanel = new JPanel();     // extra JPanel 
		containerPanel.setLayout(new GridBagLayout());
		containerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 0, 1)));

		containerPanel.add(drawPanel);
		frame.add(new JScrollPane(containerPanel));
		 
		frame.add(toolbar, BorderLayout.NORTH); // add tool-bar
		frame.add(labelPanel, BorderLayout.SOUTH);
		
		frame.setJMenuBar(menubar); // add menu-bar
		frame.addComponentListener(this);
	
//		if(drawPanel.isRefreshed()==true) {
//		  refresh = new Refresh();
//		
//		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	menubar.setMenuBarListener(new MenuBarListener() {
				
		@Override
		public void stringEmitted(String result) {
			
			//System.out.println(result);
			if (result == "New") { 
		

				
			}
			if (result == "Open") { 
				
				if (fileChooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
					
					try {
						loadFromFile(fileChooser.getSelectedFile());
						labelPanel.setLoadedLabelText(fileChooser.getSelectedFile().getName());
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main.this, "Could not Load", "Error", JOptionPane.ERROR_MESSAGE);;
					}
					
				}
				drawPanel.repaint();
				
			}
			if (result == "Save") { 
				
				if (fileChooser.showSaveDialog(Main.this)== JFileChooser.APPROVE_OPTION) {
				try {
					saveToFile(getLoadedFile());
					//System.out.println(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Main.this, "Could not Save", "Error", JOptionPane.ERROR_MESSAGE);;
				}
			}
			
			
		
				
			}
			if (result == "SaveAs") { 
				if (fileChooser.showSaveDialog(Main.this)== JFileChooser.APPROVE_OPTION) {
					try {
						saveToFile(fileChooser.getSelectedFile());
						//System.out.println(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Main.this, "Could not Save", "Error", JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
			if (result == "Exit") { 
				int action = JOptionPane.showConfirmDialog(Main.this, 
						"Do you really want to close the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				
				if(action == JOptionPane.OK_OPTION) {
					System.exit(0);	
				}
			}
			if (result == "Material") { 
				
				materialCollectionDialog.setMaterialListener(new MaterialDialogListener() {
					
					public void stringEmitted(String resultMaterial) {
						
						if(resultMaterial == "Add") {
							
							materialCollection.addMaterial(new Material(materialCollectionDialog.getE(),materialCollectionDialog.getA(),materialCollectionDialog.getI(),materialCollectionDialog.getName()));
						
						}
						
					}
					
				});
				
				materialCollectionDialog.setVisible(true);
			
			}
		}
		
	});
	labelPanel.setTextEntryListener(new TextEntryListener() {

		@Override
		public void stringEmitted(String result) {
			parseText= new ParseTextEntry(result);
			
			
			for(int i=0;i<parseText.getNodeCordinatesX().length;i++) {
			
				
				drawPanel.addNode(new Node(parseText.getNodeCordinatesX()[i], parseText.getNodeCordinatesY()[i], 10, i+1,toAdd));
				toAdd = true;
				
				drawPanel.setNodeNum(parseText.getNodeCordinatesX().length);
			
				
					
				if (drawPanel.getNodes().size() > 1) {
					
					
					
					int size = drawPanel.getNodes().size();

					
					int x1 = drawPanel.getNodes().get(size - 1).getX() + 10;
					int y1 = drawPanel.getNodes().get(size - 1).getY() + 10;
					int x2 = drawPanel.getNodes().get(size - 2).getX() + 10;
					int y2 = drawPanel.getNodes().get(size - 2).getY() + 10;
			
						drawPanel.addMember(new Member(x1, y1, x2, y2,memberNumber,startNode,endNode));
						
						drawPanel.setBeamdof(parseText.getNodeCordinatesX().length);
						memberNumber++;


				}
			}
			for (Node node : drawPanel.getFilteredNodes()) {
				
					if (parseText.getFixtures()[node.getNodeNumber()-1].equals("X") ){
						
						drawPanel.changeFixture2("Fixed",node);
						
					}
					if (parseText.getFixtures()[node.getNodeNumber()-1].equals("P") ) {
						
						drawPanel.changeFixture2("Pinned",node);
						
					}
					if (parseText.getFixtures()[node.getNodeNumber()-1].equals("S") ) {
						
						drawPanel.changeFixture2("Sliding",node);
						
					}
//					  if (forcepane.getForce() == "Point") {		//Point Force Selected
//							//add to Arraylist 
//							drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),n.getNodeNumber(),n.getMidPoint(), 0.0,forcepane.getAngle(), n.getCoord(),n.getCoord()));
//							
//							n.setSelected(false);
//						
//							drawPanel.repaint();
//							
//						}
				
				
			}
			

			
		}
		
	});
		toolbar.setToolBarListener(new ToolBarListener() {	//Listen for a button on the toolbar to be pressed
			@Override
			
			public void stringEmitted(String result) { 				//result from tool-bar button as string
			//System.out.println(result);
				if (result == "Select") { 
					selectAll();
//					for (Node node : drawPanel.getNodes()) {
//						node.setSelected(true);
//					}
//					
//					for (Member member : drawPanel.getMembers()) {
//						member.setSelected(true);
//					}
//					
//					for (Forces force : drawPanel.getForces()) {
//						force.setSelected(true);
//					}
						
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (result == "Calculate") { 
//					for( Node node:drawPanel.getFilteredNodes()) {
//						node.getCoord();
//						System.out.println(node.getCoord()+ "Node");
//					}
//					drawPanel.
					//ArrayList<Point> n = drawPanel.sortNodeCoordinate();
					drawPanel.deleteReactions();
					drawPanel.deleteDisplacements();
					
					
					
					int dof =drawPanel.getMemberDOF();
					
					CreateGlobalMatrix globalStiffness = new CreateGlobalMatrix(dof);//create dof by dof global matrix.
					
				
					for (Member member : drawPanel.getMembers()) {		
						//member.setStart(n.get(index))n.
						for (Node node : drawPanel.getFilteredNodes()) {
//				
							if (member.getMemberStart().equals(node.getCoordPlusRadius())) {
								
//								System.out.println(node.getNodeNumber() +"," +"EndNode");
//								System.out.println(member.getNumber());
//								System.out.println("True1");
//								System.out.println(-member.x2+","+member.x1);
//								
								//member.setStart(node.getNodeNumber());
								 end2 = node.getNodeNumber();
							}
							if (member.getMemberEnd().equals(node.getCoordPlusRadius())) {
//							System.out.println(node.getNodeNumber() +"," +"StartNode");
//							System.out.println(member.getNumber());
//								System.out.println(member.y2+","+member.y1);
//								//member.setEnd(node.getNodeNumber());
								start2 = node.getNodeNumber();
							}
							if(drawPanel.isRefreshed()==false) {
								member.calculateNodeDOFList2(start2,end2);
								//System.out.println(member.getNumber()+","+start2+" , "+end2);
							}
						}
					//	System.out.println(member.getNodesList2());
//						System.out.println("here");
//						System.out.println(member.getNumber()+","+start2+" , "+end2);
						globalStiffness.blowupLocalStiffness(member.getLocalStiffness(),member.getNodeDOFList());											 //blowup localk(6 by 6) up into globalk(dof by dof)
					
						//globalStiffness.addLocalStiffness();														 //add all globalk's together to create globalK
					
					}

					globalStiffness.reduceglobalStiffness(drawPanel.getFixtureType());															//reduce globalK depending on fixtures
					
					Reactions reactions = new Reactions(drawPanel.getMemberDOF(), globalStiffness.getReducedDOF());
					
					CalculateInvMatrix invmatrix = new CalculateInvMatrix();
		
						
////////////////////////////////////////////////////Member Forces////////////////////////////////////////////////////////
					double []TempForceF = new double[drawPanel.getMemberDOF()];
					
				
						
						
					//for (Forces force : drawPanel.getForces()) {
					for (Member member : drawPanel.getMembers()) {
					for (Forces force : drawPanel.getForces()) {
						
							
				
						if ( force.getType().matches("Point") && member.getbounds().contains(force.getLocation())) {
							
							System.out.println("pointhere");
							
							
							reactions.calculateMemberReaction(force.getMagnitude(),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd() );
							
							member.setMemberReactions(reactions.getLocalMemberForces());
							
							double [][]adj = new double[6][6]; // To store adjoint of A[][] 
							double [][]inv = new double[6][6]; // To store inverse of A[][] 
							
					    	invmatrix.adjoint(member.getBeta(), adj); 
						 
						    if (invmatrix.inverse(member.getBeta(), inv)) {// check for singularity
						    	
						    	System.out.println(member.getNumber());
						    	reactions.globalMemberForceVector( inv,member.getInitialMemberReactions(),member.getNumber());
						    	member.setGlobalMemberReactions(reactions.getGlobalMemberForces());
						    	
						    	reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions(), member.getNodeDOFList());
						    	member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());
						    }
						    
						    
						}	
					
						
						else
							if( force.getType().matches("UDL") && member.getbounds().contains(force.getLocation())) {
							System.out.println("udlhere");
							reactions.calculateMemberReaction((force.getMagnitude()),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd());
							member.setMemberReactions(reactions.getLocalMemberForces());
							double [][]adj = new double[6][6]; // To store adjoint of A[][]   
						    double [][]inv = new double[6][6]; // To store inverse of A[][] 
						    
						    invmatrix.adjoint(member.getBeta(), adj); 
							
							  if (invmatrix.inverse(member.getBeta(), inv)) 
								  
								  System.out.println(member.getNumber());
								  
							      reactions.globalMemberForceVector(inv,member.getInitialMemberReactions(),member.getNumber());
							    
								member.setGlobalMemberReactions(reactions.getGlobalMemberForces());
							  
								reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions(), member.getNodeDOFList());
								member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());
								
							}else {
								
								//reactions.setLocalMemberForces(reactions);
						///	System.out.println("here force");
								double [][]adj = new double[6][6]; // To store adjoint of A[][] 
								double [][]inv = new double[6][6]; // To store inverse of A[][] 
								
								//reactions.localMemberForceVector();
								//reactions.setLocalMemberForces(member.getInitialMemberReactions());
								
								invmatrix.adjoint(member.getBeta(), adj); 
								if (invmatrix.inverse(member.getBeta(), inv)) {// check for singularity
							    	
									//System.out.println(member.getNumber());
							    	reactions.globalMemberForceVector( inv,member.getInitialMemberReactions(),member.getNumber());
							    	reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions(), member.getNodeDOFList());
							    	member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());
							    	
							    }					
								
								
							}	
						
						
						
						}
					reactions.addLocalMemberForces(TempForceF,member.getBlownupGlobalMemberReactions());
						//reactions.addLocalMemberForces(TempForceF);
					}
//					for (Member member : drawPanel.getMembers()) {
//						//reactions.addLocalMemberForces(TempForceF,member.getBlownupGlobalMemberReactions());
//					}
					
					double []TempForceQ = new double[drawPanel.getMemberDOF()];
				
					
					for (Node node : drawPanel.getFilteredNodes()) {
						
////////////////////////////////////////////////////Node Forces////////////////////////////////////////////////////////						
						for (Forces f : drawPanel.getForces()) {
							
							if (node.getNodeNumber() == f.getNumber() && f.getType().matches("Moment")) {
								
								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getMemberDOF(),node.getNodeNumber(),TempForceQ,f.getType(),f.getAngle2());
							}
							if (node.getNodeNumber() == f.getNumber() && f.getType().matches("Point") && f.getLocation().x == node.getMidPoint().x && f.getLocation().y == node.getMidPoint().y) {
							
								reactions.nodeReactionVector((f.getMagnitude()),drawPanel.getMemberDOF(),node.getNodeNumber(),TempForceQ,f.getType(),f.getAngle2());
							}


							}
				
						}
					
					reactions.subtractNodeForces();
					
					
			
					if(globalStiffness.getReducedDOF()!=0) {
						
					reactions.reduceForceVector(drawPanel.getFixtureType());
						
					double [][]adj = new double[globalStiffness.getReducedDOF()][globalStiffness.getReducedDOF()]; // To store adjoint of A[][] 
				    double [][]inv = new double[globalStiffness.getReducedDOF()][globalStiffness.getReducedDOF()]; // To store inverse of A[][] 
				    
				    invmatrix.setN( globalStiffness.getReducedDOF());
				 
				    invmatrix.adjoint(globalStiffness.getreducedGlobalStiffness(), adj); 
					 
					    if (invmatrix.inverse(globalStiffness.getreducedGlobalStiffness(), inv)) {
					    	
					    }
					   // g.get
					    Displacements d = new Displacements( drawPanel.getMemberDOF(), globalStiffness.getReducedDOF());
					    
					    d.calculateDeflections(inv, reactions.getReducedForceVector());
					  
					     d.blowupDisplacementVector(drawPanel.getFixtureType());
					   
					     U = d.getDisplacmentVector();
					     drawPanel.addDisplacementResults(d.getDisplacmentVector());
					    
					    R = reactions.calculateGlobalReaction(globalStiffness.getGlobalStiffness (), d.getDisplacmentVector());
					    
					    
					   // drawPanel.addReaction(new Reactions(drawPanel.getBeamdof(), g.getreducedDOF()));
					    
					
					    for (Member member : drawPanel.getMembers()) {
					    	//System.out.println(member.getNumber());
					    	reactions.calculateLocalReactions(member.getLocalKPrime(), d.localDeflections(member.getNumber(),member.getBeta()),member.getNumber());
					   
					    	drawPanel.addShearResults(reactions.getLocalReactions(),member.getForceType());
					    }
				
				}
				}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(result == "Result") {
					
					resultpane = new ResultPopupPanel(); 	//Instance of Popup Panel
					resultpane.createPopup(); 
					drawPanel.setHideForces(false);
					
					if ( resultpane.getResultType() == "Reactions") {

						drawPanel.deleteResultGraphics();
						
						//int nodecount = 0;
						for(Node n:drawPanel.getFilteredNodes()) {
							drawPanel.addReaction( new DrawReactions(R,n.getNodeNumber()-1,n.getX(),n.getY()));
							//nodecount++;
							
					}
						
					
					}
					if ( resultpane.getResultType() == "Displacements") {
						
						drawPanel.deleteResultGraphics();
						drawPanel.setHideForces(true);
						
						if (!drawPanel.getNodesSortedResult()) {
							drawPanel.sortNodeCoordinates();
						}
							
							drawPanel.addDisplacement( new DrawDisplacement3(U,drawPanel.getSortedNodeCoordinates(),drawPanel.getSortedMemberNodes()));
				
					}
					
					if ( resultpane.getResultType() == "Shear Diagram") {
						
						drawPanel.setHideForces(true);
						drawPanel.deleteResultGraphics();
						
						
						for (Member member:drawPanel.getMembers()) {
						
							drawPanel.addShear(new DrawShear(drawPanel.getShearResults().get(member.getNumber()),drawPanel.getForceType().get(member.getNumber()),member.getAngle(),member.getMidPoint(),member.getLength(),member.getForceLocation(),member.getSlope()));
							
						}
						
						
					}
					if ( resultpane.getResultType() == "Bending Diagram") {
						drawPanel.deleteResultGraphics();
						drawPanel.setHideForces(true);
						for (Member member:drawPanel.getMembers()) {
							//System.out.println(drawPanel.getForceType().get(member.getNumber()));
							drawPanel.addBending(new DrawBending(drawPanel.getShearResults().get(member.getNumber()),member.getMidPoint(),member.getAngle(),member.getLength(),drawPanel.getForceType().get(member.getNumber())));
							
						}
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
					
					  fixturepane = new FixturePopupPanel(); 	//Instance of Popup Panel
					  fixturepane.createPopup();  				//Display fixture menu pane
					
								if (fixturepane.isFixture() == "Fixed") {
									n.setAngle(fixturepane.getAngle());
									drawPanel.changeFixture("Fixed", n);
							
								}
								
								if (fixturepane.isFixture() == "Pinned") {
									n.setAngle(fixturepane.getAngle());
									drawPanel.changeFixture("Pinned",n);
									
								}
								
								if (fixturepane.isFixture() == "Sliding") {
									n.setAngle(fixturepane.getAngle());
									drawPanel.changeFixture("Sliding",n);
									//System.out.println("h2");
								}
								if (fixturepane.getAngle()!=0) {
									
									for (Member m:drawPanel.getMembers()) {
										
										//System.out.println(n.getNodeNumber());
										for(int i=0; i<2;i++) {
											if(m.getNodesList()[i]==n.getNodeNumber()) {
												System.out.println(fixturepane.getAngle());
												m.setBeta(fixturepane.getAngle());
											}
										}
										
									}
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
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),n.getNodeNumber(),n.getMidPoint(), 0.0,forcepane.getAngle(), n.getCoord(),n.getCoord()));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  //System.out.println(n.getMidPoint() + "here");
						  if (forcepane.getForce() == "Moment") { //Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",n.getNodeNumber(),n.getMidPoint(), 0.0,forcepane.getAngle(),n.getCoord(),n.getCoord()));
								
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
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),forcepane.getDirection2(),member.getNumber(),member.getMidPoint(), member.getAngle(),forcepane.getAngle(), member.getMemberStart(), member.getMemberEnd()));
										
										member.setSelected(false);
									
										drawPanel.repaint();
										
									}
									if (forcepane.getForce() == "UDL") {
										
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",member.getNumber(), member.getMidPoint(), member.getAngle(),forcepane.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										drawPanel.repaint();
									}
									if (forcepane.getForce() == "Moment") { //Point Force Selected
										//add to Arraylist 
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForce(), forcepane.getDirection(),"Perpendicular",member.getNumber(),member.getMidPoint(), member.getAngle(),forcepane.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										
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
				currentnodenumber.setNodeNumber(1);
				currentnodenumber.setOldNodeNumber(1);
				memberNumber=0;
				
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
				 drawPanel.setTranslate(e.getPoint().x,e.getPoint().y);
					
				if (e.getPreciseWheelRotation() < 0) {
					
                    zoom += 0.1;
                } else {
                    zoom -= 0.1;
                }

                if (zoom < 0.01) {
                    zoom = 0.01;
              }
          //  zoom*= Math.pow(1.2, e.getWheelRotation());
        //  drawPanel.updatePreferredSize(e.getPreciseWheelRotation(), e.getPoint());
         drawPanel.setZoom(zoom);
          

			
			
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
				 // drawPanel.setTranslate(drawPanel.getSnapX(),drawPanel.getSnapY());
					
				 
				  drawPanel.repaint();
			}
			
			public void mouseMoved(MouseEvent me) {
				super.mouseMoved(me);
			
				if(toolbar.isdrawing) {
					current = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
				}
			
				if(toolbar.isdrawing && last!=null ) {
					drawPanel.addTempMember(new TempMember(current,last));
				}
				//drawPanel.drawCurrentCoordinates(current.x, current.y);
				drawPanel.createSnapGrid(me.getPoint().x, me.getPoint().y);
			//drawPanel.drawCurrentCoordinates(me.getPoint().x, me.getPoint().y);
				labelPanel.setCorordinateLabelText((double)me.getX(), (double)me.getY());
				  drawPanel.setTranslate(drawPanel.getSnapX(),drawPanel.getSnapY());
					//System.out.println(drawPanel.getSnapX()+","+drawPanel.getSnapY());
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
						//Something to do when loading not used for now
//						member.setStart(3);
//						member.setEnd(4);
//						
//						drawPanel.setBeamdof(3);
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
							f.setLocation(drawPanel.getSnapX(),drawPanel.getSnapY());
							//f.setLocation(me.getPoint().x,me.getPoint().y);
							
						}
						
					}
				}

				drawPanel.repaint();
			}
			
		});

		drawPanel.addMouseListener(new MouseAdapter() {
			
			int count = 0;
			
			
			
			
			//CalculateNodeNumber currentnodenumber = new CalculateNodeNumber(); //Keeps track of current nodenumber and how/if it will be modified.
			
			public void mousePressed(MouseEvent e) {
			
				
				if(toolbar.isdrawing) {
					
				last = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
			
				
				}

				
				for (Member member : drawPanel.getMembers()) {
					//member.calculateYintercept();
					if(e.getClickCount()==2 && member.getbounds().contains(e.getPoint()) ) {
					
						editmaterial = new EditMaterialPopup();
						editmaterial.setEditMaterialPopupListener(new EditMaterialPopupListener() {

							@Override
							public void stringEmitted(String editresult) {
								if(editresult=="OK") {
									member.setMaterialName(editmaterial.getSelectedName());
									member.setE(Double.parseDouble(editmaterial.getSelectedE()));
									member.setA(Double.parseDouble(editmaterial.getSelectedA()));
									member.setI(Double.parseDouble(editmaterial.getSelectedI()));
								//	System.out.println(editmaterial.getSelectedName());
									//member.setE(Double.parseDouble(editmaterial.getSelectedE()));
								}
								
							}
							
						});
						
						for(Material material:materialCollection.getMaterialCollection()) {
							//Shows all material currently in the material libary.
							editmaterial.createNameList(material.getName());
							//System.out.println(material.getE());
							//editmaterial.setSelectedE(material.getE());
						}
					
						editmaterial.createPopup(member.getE(),member.getA(),member.getI());

					}
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
					if(e.getClickCount()==2 && n.getBounds().contains(e.getPoint())&& !toolbar.isdrawing ) {
						
						editDisplacement = new NodeDisplacmentPopup();
						
						editDisplacement.setNodeDisplacmentPopupListener(new NodeDisplacmentPopupListener() {

							@Override
							public void stringEmitted(String editresult) {
								if(editresult=="OK") {
//									member.setMaterialName(editmaterial.getSelectedName());
//									member.setE(Double.parseDouble(editmaterial.getSelectedE()));
//									member.setA(Double.parseDouble(editmaterial.getSelectedA()));
//									member.setI(Double.parseDouble(editmaterial.getSelectedI()));
								//	System.out.println(editmaterial.getSelectedName());
									//member.setE(Double.parseDouble(editmaterial.getSelectedE()));
								}
								
							}
							
						});
						editDisplacement.createPopup();
						
					}
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
					
					
					if(e.getClickCount()==2 && f.getPointBounds().contains(e.getPoint()) ) {
						editforcepane = new EditForcePopupPanel(); 				//Instance of Popup Panel
						editforcepane.createPopup(f.getMagnitude());  
						
						f.setMagnitude(editforcepane.getMagnitude());
					}
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
							
							
							currentnodenumber.setNodeNumber(drawPanel.getFilteredNodes().size()+1);
							currentnodenumber.setOldNodeNumber(drawPanel.getFilteredNodes().size()+1);
							System.out.println(drawPanel.getFilteredNodes().size()+1);
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
							startNode=drawPanel.getNodeNum();
						}
						
						if( count3 == 1) {
							endNode =drawPanel.getNodeNum();
						}
						count3++;
					
					if (drawPanel.getNodes().size() > 1) {
					
						
						
						int size = drawPanel.getNodes().size();

						
						int x1 = drawPanel.getNodes().get(size - 1).getX() + 10;
						int y1 = drawPanel.getNodes().get(size - 1).getY() + 10;
						int x2 = drawPanel.getNodes().get(size - 2).getX() + 10;
						int y2 = drawPanel.getNodes().get(size - 2).getY() + 10;
						
						
						if (count == 2) {
						
							drawPanel.addMember(new Member(x1, y1, x2, y2,memberNumber,startNode,endNode));
					//		System.out.println(currentnodenumber.getNodeNumber());
							drawPanel.setBeamdof(currentnodenumber.getNodeNumber());
							drawPanel.sortMemberMidPointCoordinate();
							drawPanel.sortNodeCoordinate();
							//drawPanel.sortMemberNodeCoordinate();
							
							
							
							//materialCollection.addDefaultMaterial(new Material(E,A,I,Name));
							count = 0;
							count3 = 0;
							memberNumber++;
							last=null;
							
						}

					}

						currentnodenumber.incrementNodeNumber();
				}
				
			}
			
			
		});

	

		frame.setVisible(true);

	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void selectAll() {
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
				new Main();
				
			}
		});
	}

	@Override
	public void componentResized(ComponentEvent e) {
	//System.out.println(e.getComponent().getSize());
	drawPanel.setPreferredSize(e.getComponent().getSize());
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
