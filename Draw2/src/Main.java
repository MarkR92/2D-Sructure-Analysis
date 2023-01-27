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
import java.util.ArrayList;

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
	public ArrayList<String> fixtures = new ArrayList<>();
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
	private boolean resetglobalstiffness=false;
	
	int	memberNumber = 0;
	int start2;
	int end2;
	private int startNode;
	private int endNode;
	
	boolean isforceselected=false;
	boolean isforcehighlighted=false;
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
						
					node.changeFixture("Fixed");
					
					}
					if (parseText.getFixtures()[node.getNodeNumber()-1].equals("P") ) {
						
						node.changeFixture("Pinned");
						
					}
					if (parseText.getFixtures()[node.getNodeNumber()-1].equals("S") ) {
						
						node.changeFixture("Sliding");
						
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

					drawPanel.deleteReactions();
					drawPanel.deleteDisplacements();

					int dof =drawPanel.getFilteredNodes().size()*3;
					
					
					CreateGlobalMatrix globalStiffness = new CreateGlobalMatrix(dof);//create dof by dof global matrix.
					
				//this just handles how the structure is drawn. by default the user draws from left to right but just incase they draw right to left!
					for (Member member : drawPanel.getMembers()) {		
						for (Node node : drawPanel.getFilteredNodes()) {
			
							if (member.getMemberStart().equals(node.getCoordPlusRadius())) {
						
								 end2 = node.getNodeNumber();
							}
							if (member.getMemberEnd().equals(node.getCoordPlusRadius())) {

								start2 = node.getNodeNumber();
							}
							if(drawPanel.isRefreshed()==false) {
								member.calculateNodeDOFList2(start2,end2);
								
							}
							
						}
						
						//blows up local stiffness matrices from 6 by 6 to dof by dof. 
						globalStiffness.blowupLocalStiffness(member.getLocalStiffness(),member.getNodeDOFList());											 //blowup localk(6 by 6) up into globalk(dof by dof)
						
					}
						fixtures.clear();
						for (Node node : drawPanel.getFilteredNodes()) {
						
							fixtures.add(node.getFixture());// gets all the fixture types from nodes.
						
						}
					
					
					globalStiffness.reduceglobalStiffness(fixtures);//reduce globalK depending on fixtures
					
					Reactions reactions = new Reactions(dof, globalStiffness.getReducedDOF());
		
						
////////////////////////////////////////////////////Member Forces////////////////////////////////////////////////////////
										
					for (Member member : drawPanel.getMembers())
					{
					 for (Forces force : drawPanel.getForces())
					  {

						if ( force.getType().matches("Point") && member.getbounds().contains(force.getLocation())) 
						{
							
							    //resolve the member force into node forces
							    reactions.calculateLocalMemberReaction(force.getMagnitude(),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd() );
							   
							    //add the resolved forces to the member
							    member.setMemberReactions(reactions.getLocalMemberForces());
							    
							    //using the LocalMemberForces calculate the global member forces
							    member.calculateGlobalMemberReactions().getArray();
							   
							    //covert 6 by 1 global forces to dof by 1
						    	reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions().getColumnPackedCopy(), member.getNodeDOFList());
						    	
						    	//add blownup reactions to the members
						    	member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());

						}	
					
						
						else if( force.getType().matches("UDL") && member.getbounds().contains(force.getLocation()))
						{
							
							
							//resolve the member force into node forces
						    reactions.calculateLocalMemberReaction(force.getMagnitude(),force.getType(), member.getLength(), force.getLocation(), member.getMemberStart(), member.getMemberEnd() );
						   
						    //add the resolved forces to the member
						    member.setMemberReactions(reactions.getLocalMemberForces());
						    
						    //using the LocalMemberForces calculate the global member forces
						    member.calculateGlobalMemberReactions();
						   
						    //covert 6 by 1 global foreces to dof by 1
					    	reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions().getColumnPackedCopy(), member.getNodeDOFList());
					    	
					    	//add blownup reactions to the members
					    	member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());

								
						}
						else 
						{
								
									// if there are no member forces we still need to create a dof by 1 matrix full of zeros
								    
								    //covert 6 by 1 global foreces to dof by 1
							    	reactions.blowupLocalMemberForceVector(member.getGlobalMemberReactions().getColumnPackedCopy(), member.getNodeDOFList());
							    	//add blownup reactions to the members
							    	member.setBlownuplMemberReactions(reactions.getBlownupLocalMemberForceVector());			
								
								
						}	
						
						
						
					}
					 
					 
					reactions.addLocalMemberForces(member.getBlownupGlobalMemberReactions());
						
					}				
			
////////////////////////////////////////////////////Node Forces////////////////////////////////////////////////////////	
					
					for (Node node : drawPanel.getFilteredNodes())
					{
						
						for( Forces2 force:node.getForce())
						{
							reactions.nodeReactionVector((force.getMagnitude()),force.getForceType(),force.getDirection(),node.getNodeNumber());
						
						}
				
					}
					
					reactions.subtractNodeForces();
					
					
			
					if(globalStiffness.getReducedDOF()!=0) {
						
					reactions.reduceForceVector(fixtures);
					
					    Displacements d = new Displacements( dof, globalStiffness.getReducedDOF());
					    
					    d.calculateDeflections(globalStiffness.getreducedGlobalStiffnessInverse().getArray(), reactions.getReducedForceVector());
					  
					     d.blowupDisplacementVector(fixtures);
					   
					     U = d.getDisplacmentVector();
					     
					     drawPanel.addDisplacementResults(d.getDisplacmentVector());
					    
					    R = reactions.calculateGlobalReaction(globalStiffness.getGlobalStiffness (), d.getDisplacmentVector());
					  
					
					    for (Member member : drawPanel.getMembers()) {
					    	member.setLocalDeflections(U, globalStiffness.getReducedDofIndex());
					    	reactions.calculateLocalReactions(member.getLocalKPrime().getArray(), member.getLocalDeflections().getColumnPackedCopy(),member.getNumber());
							   
					    	//reactions.calculateLocalReactions(member.getLocalKPrime().getArray(), d.localDeflections(member.getNumber(),member.getBeta().getArray()),member.getNumber());
					   
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
					for (Node node : drawPanel.getFilteredNodes())
					{
					 node.deleteForce();
					}
				drawPanel.deleteNode(); 		// Delete selected node
				
				drawPanel.deleteMember(); 		// Delete selected beam
				drawPanel.deleteForce(); 		// Delete selected point load
				drawPanel.deleteFilteredNode();
				resetglobalstiffness = true;
				drawPanel.repaint(); // Update graphics
				}
				
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				for (Node n : drawPanel.getFilteredNodes()) { //Iterate through each node
					
				if (result == "Fixture" && n.isSelected()) { // check if fixture is pressed and if a node was selected
					
					  fixturepane = new FixturePopupPanel(); 	//Instance of Popup Panel
					  fixturepane.createPopup();  				//Display fixture menu pane
					
								if (fixturepane.isFixture() == "Fixed")
								{
									n.setAngle(fixturepane.getAngle());
									n.changeFixture("Fixed");
									n.setSelected(false);
								}
								
								else if  (fixturepane.isFixture() == "Pinned") 
								{
									n.setAngle(fixturepane.getAngle());
									n.changeFixture("Pinned");
									n.setSelected(false);
								}
								
								else if (fixturepane.isFixture() == "Sliding")
								{
									n.setAngle(fixturepane.getAngle());
									n.changeFixture("Sliding");
									n.setSelected(false);
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
						  
						  if (forcepane.getForceType() == "Point") {		//Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForceType(), forcepane.getDirection(),forcepane.getDirection2(),n.getNodeNumber(),n.getMidPoint(), 0.0,forcepane.getAngle(), n.getCoord(),n.getCoord()));
								
								n.addForce(new Forces2(forcepane.getMagnitude(), forcepane.getAngle(),n.getMidPoint(),"Point"));
								
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						 
						  else if (forcepane.getForceType() == "Moment") { //Point Force Selected
								//add to Arraylist 
								drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForceType(), forcepane.getDirection(),"Perpendicular",n.getNodeNumber(),n.getMidPoint(), 0.0,forcepane.getAngle(),n.getCoord(),n.getCoord()));
							    n.addForce(new Forces2(forcepane.getMagnitude(), forcepane.getAngle(),n.getMidPoint(),"Moment"));
								n.setSelected(false);
							
								drawPanel.repaint();
								
							}
						  
					}
					
				}
				for (Member member : drawPanel.getMembers()) { //Iterate through each memeber
					
					if (result == "Force" && member.isSelected()) { // check if force is pressed and if a memeber was selected
						
						  forcepane = new ForcePopupPanel(); //Instance of Popup Panel
						  forcepane.createPopup();  //Force popup panel is displayed
					
						  
						  
									if (forcepane.getForceType() == "Point")
									{ //Point Force Selected
										//add to Arraylist 
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForceType(), forcepane.getDirection(),forcepane.getDirection2(),member.getNumber(),member.getMidPoint(), member.getAngle(),forcepane.getAngle(), member.getMemberStart(), member.getMemberEnd()));
										member.addForce(new Forces2(forcepane.getMagnitude(), forcepane.getAngle(),member.getMidPoint(),"Point"));
										
										member.setSelected(false);
									
										drawPanel.repaint();
										
									}
									if (forcepane.getForceType() == "UDL")
									{
										
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForceType(), forcepane.getDirection(),"Perpendicular",member.getNumber(), member.getMidPoint(), member.getAngle(),forcepane.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										member.addForce(new Forces2(forcepane.getMagnitude(), forcepane.getAngle(),member.getMidPoint(),member.getMemberStart(), member.getMemberEnd(),"UDL",member.getAngle(),member.getLength()));
										drawPanel.repaint();
									}
									if (forcepane.getForceType() == "Moment") 
									{ //Point Force Selected
										//add to Arraylist 
										drawPanel.addForces(new Forces(forcepane.getMagnitude(),forcepane.getForceType(), forcepane.getDirection(),"Perpendicular",member.getNumber(),member.getMidPoint(), member.getAngle(),forcepane.getAngle(),member.getMemberStart(), member.getMemberEnd()));
										member.addForce(new Forces2(forcepane.getMagnitude(), forcepane.getAngle(),member.getMidPoint(),"Moment"));
										member.setSelected(false);
									
										drawPanel.repaint();
										
									}
									
									member.setForceType(forcepane.getForceType());
									
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
				
				drawPanel.createSnapGrid(me.getPoint().x, me.getPoint().y);


				labelPanel.setCorordinateLabelText((double)me.getX(), (double)me.getY());
				  drawPanel.setTranslate(drawPanel.getSnapX(),drawPanel.getSnapY());

				for (Node n : drawPanel.getFilteredNodes()) {// iterate through each node});
					
					
					if (n.getBounds().contains(me.getPoint())) {// get the node bounds and check if mouse is within its
																// bounds
						if (!n.isHighlighted()) {// if not already highlighted

							n.setHighlighted(true);// highlight node

						}

					} else {

						n.setHighlighted(false); // un-highlight node

					}
					
					for(Forces2 force : n.getForce())
					{
						if (force.getPointBounds().contains(me.getPoint())) { // is mouse near beam
							if (!force.isHighlighted()) {
								force.setHighlighted(true);
								isforcehighlighted=true;
								// System.out.println(f.isHighlighted());
								
							}

						} else {

							force.setHighlighted(false);
							isforcehighlighted=false;
						}
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
					
					if (member.getbounds().contains(me.getPoint())& !isforcehighlighted) { // is mouse near beam
						if (!member.isHighlighted()) {
							member.setHighlighted(true);
						
						}

					} else {

						member.setHighlighted(false);
					}
					for(Forces2 force :member.getForce()) 
					{
						if (force.getPointBounds().contains(me.getPoint()))
		{ // is mouse near beam
							if (!force.isHighlighted()) {
								force.setHighlighted(true);
								
								// System.out.println(f.isHighlighted());
							}

						} else {

							force.setHighlighted(false);
						}
						if(force.isSelected()  ) {
							//force.setLocation(drawPanel.getSnapX(),drawPanel.getSnapY());
							//f.setLocation(me.getPoint().x,me.getPoint().y);
							
						}
						
					}
				}

				drawPanel.repaint();
			}
			
		});
		
		drawPanel.addMouseListener(new MouseAdapter() {
			
			int count = 0;
			public void mousePressed(MouseEvent e) {
			
				
				if(toolbar.isdrawing) {
					
				last = new Point(drawPanel.getSnapX(),drawPanel.getSnapY());
			
				
				}
				for (Node n : drawPanel.getFilteredNodes()) {// iterate through each node
					
					if(e.getClickCount()==2 && n.getBounds().contains(e.getPoint())&& !toolbar.isdrawing ) {
						
						editDisplacement = new NodeDisplacmentPopup();
						
						editDisplacement.setNodeDisplacmentPopupListener(new NodeDisplacmentPopupListener() {

							@Override
							public void stringEmitted(String editresult) {
								if(editresult=="OK") {
							
								}
								
							}
							
						});
						editDisplacement.createPopup();
						
					}
					if (n.getBounds().contains(e.getPoint()) && !toolbar.isdrawing) {// get the node bounds and check if
																					// mouse click was within its
																						// bounds
						if (!n.isSelected()) 
						{// check if node has been clicked on
							n.setSelected(true);
						}

						else 
						{
							n.setSelected(false);
						}

					}
					for(Forces2 force :n.getForce())
				    {
						if(e.getClickCount()==2 && force.getPointBounds().contains(e.getPoint())) 
						{
							editforcepane = new EditForcePopupPanel(); 				//Instance of Popup Panel
							editforcepane.createPopup(force.getMagnitude(),force.getDirection());  
							
							force.setMagnitude(editforcepane.getMagnitude());
							force.setDirection(editforcepane.getDirection());
						}
						if (force.getPointBounds().contains(e.getPoint())&& !toolbar.isdrawing)
						{
							if (!force.isSelected())
							{
								force.setSelected(true);
								isforceselected=true;
						    }
							else 
						    {
							    force.setSelected(false);
							    isforceselected=false;
						    }
					   }
						if (force.isSelected())
						{
							
							drawPanel.repaint();
						
						}
				  }

				
				for (Member member : drawPanel.getMembers()) {
					//member.calculateYintercept();
					if(e.getClickCount()==2 && member.getbounds().contains(e.getPoint()) && !isforceselected) {
					
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
						if (!member.isSelected()&&!isforceselected ) {// check if beam has been clicked on
							member.setSelected(true);
							// System.out.println(b.isSelected());
							drawPanel.repaint();
						} else {
							member.setSelected(false);
						}
					}
					
				}
				//

				
					
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
							//System.out.println(currentnodenumber.getNodeNumber());
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
