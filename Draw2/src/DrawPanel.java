import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.Iterator;

import javax.swing.JPanel;



public class DrawPanel extends JPanel {

   
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5724101582125350331L;
	
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Node> nodesfilterd = new ArrayList<>();
	public ArrayList<Point> nodeCoordinates = new ArrayList<>();
	public ArrayList<Point> sortedNodeCoordinates = new ArrayList<>();
	private boolean nodesSorted=false;
	public ArrayList<Integer> sortedNodeNumber = new ArrayList<>();
	public ArrayList<int[]> sortedMemberNodes = new ArrayList<>();
	
	public ArrayList<Point> memberMidPointCoordinates = new ArrayList<>();
	public ArrayList<Point> memberNodeCoordinates = new ArrayList<>();
	public ArrayList<Member> members = new ArrayList<>();
	public ArrayList<TempMember> tempmember = new ArrayList<>();
	
	public ArrayList<Integer> beamends = new ArrayList<>();
	
	
//	public ArrayList<String> fixtures = new ArrayList<>();
	public ArrayList<Forces> forces = new ArrayList<>();
	
	public ArrayList<DrawReactions> drawreactions = new ArrayList<>();
	public ArrayList<DrawDisplacement3> drawdisplacement = new ArrayList<>();
	public ArrayList<DrawShear> drawshear = new ArrayList<>();
	public ArrayList<double[]> shearresults = new ArrayList<double[]>();
	public ArrayList<double[]> displacementresults = new ArrayList<double[]>();
	public ArrayList<String> forcetype = new ArrayList<String>();
	public ArrayList<DrawBending> drawbending = new ArrayList<>();
	
	
	private boolean hideForces=false;
	//public ArrayList<Element> element = new ArrayList<>();
	
	private Dimension preferredSize=(new Dimension(800,800)) ;   
	
	private static double prevN = 0;
	private int dof;
	private int nodenum;
	
	private double scale=1;
	
	private int tranx;
	private int trany;
	
	private boolean refresh;
	
	int w, h;
	
	    int vpw;
	    int vph;
	    int gridwidth = (int) (10);//grid width
	    int gridheight = (int) (10);//grid height
		
	    int snapmousepositionx, snapmousepositiony;//current snap coords
	    int currentmousepositionx,currentmousepositiony;
	   
    public void  addNode(Node n) {

    	nodes.add(n);
    	n.setHighlighted(true);
    	
    	filterNodes(n);
    	
        repaint();
    	
     
    }
    
    public void filterNodes(Node n) {
    	
    	if( n.toAdd()==true) {
    		nodesfilterd.add(n);
    		addNodeCoordinate(n);
    		
    	}
    	
    }
 
    public void  addNodeCoordinate(Node n) {

    	nodeCoordinates.add(n.getCoord());
     
    }
    
    public ArrayList<Point>  sortNodeCoordinate() {

    	
    	Collections.sort(nodesfilterd, new NodeComparator());
    	
    	for(int i=0;i<nodesfilterd.size();i++) {
    		//System.out.println(nodesfilterd.size());
    		nodesfilterd.get(i).setNodeNumber(i+1);
    		//sortedNodeCoordinates.add(nodesfilterd.get(i).getCoord());
    		//System.out.println(nodesfilterd.get(i).getCoord()+"Point"+ nodesfilterd.get(i).getNodeNumber());
    		
    	}
 
    return nodeCoordinates;
     
    }
    public void sortNodeCoordinates(){
    	for(int i=0;i<nodesfilterd.size();i++) {
    	
    	sortedNodeCoordinates.add(nodesfilterd.get(i).getCoord());
    	
    	}
    	nodesSorted=true;
    
    }
    public boolean getNodesSortedResult() {
    	return nodesSorted;
    }
    
    public ArrayList<Point> getSortedNodeCoordinates(){
    	return sortedNodeCoordinates;
    	
    }
    public ArrayList<Integer> getSortedNodeNumber(){
    	for(int i=0;i<nodesfilterd.size();i++) {
    	
    	sortedNodeNumber.add(nodesfilterd.get(i).getNodeNumber());
    	
    	}
    	return sortedNodeNumber;
    }

    public void createSnapGrid(int x, int y) {
    	
    	 currentmousepositionx = x;
    	 currentmousepositiony = y;
    	 
         int remainderx = currentmousepositionx % getGridWidth(), remaindery = currentmousepositiony % getGridHeight();
         
         if (remainderx<getGridWidth()/2) setSnapX(currentmousepositionx - remainderx) ;
         else setSnapX(currentmousepositionx + (getGridWidth()-remainderx));
         
         if (remaindery<getGridHeight()/2) setSnapY(currentmousepositiony - remaindery);
         else setSnapY(currentmousepositiony + (getGridHeight()-remaindery));
        
    	
    }
    public int getSnapX(){
    	
    	return (this.snapmousepositionx);
    }
    public int getSnapY(){
    	
    	return  (this.snapmousepositiony);
    }
    
    
    public void setSnapX(int snap){
    	this.snapmousepositionx=(int) (snap);
    	
    	
    }
    public void setSnapY(int snap){
    	this.snapmousepositiony=(int) (snap);
    	
    	
    }
    public int getGridWidth(){
    	
    	return (int) (this.gridwidth);
    }
    public int getGridHeight(){
    	
    	return (int) (this.gridheight);
    }
    
    public void drawGrid(Graphics2D g) {
    	g.setColor(Color.lightGray);
    	g.clearRect(0, 0, getWidth(), getHeight());
    	
    	// System.out.println(getParent().getParent().getWidth());
        //grid vertical lines
        for (int i= (gridwidth);i<getWidth();i+=gridwidth) {
             g.drawLine(i, 0, i, getHeight());
             
        }
       
        //grid horizontal lines
        for (int j= (gridheight);j<getHeight();j+=gridheight) {
             g.drawLine(0, j, getWidth(), j);
        }
    // System.out.println(getWidth()+","+getHeight());
        //show the snapped point
        g.setColor(Color.BLACK);
        if ( getSnapX()>=0 &&  getSnapY()>=0 &&  getSnapX()<=getWidth() && getSnapY()<=getHeight()) {
        	// result =true;
             g.drawOval((int) ( getSnapX())-4, (int) (getSnapY()-4), 8, 8);
             
             
        }

   }

    
    
    public void updatePreferredSize(double n, Point p) {

        if(n == 0)              // ideally getWheelRotation() should never return 0. 
            n = -1 * prevN;     // but sometimes it returns 0 during changing of zoom 
                                // direction. so if we get 0 just reverse the direction.

        double d = (double) n * 1.08;
        d = (n > 0) ? 1 / d : -d;

        int w = (int) (getWidth() * d);
        int h = (int) (getHeight() * d);
        
        preferredSize.setSize(w, h);
        
        int offX = (int)(p.x * d) - p.x;
        int offY = (int)(p.y * d) - p.y;
        
        System.out.println(offX+","+offY);
        getParent().setLocation(getParent().getLocation().x-offX,getParent().getLocation().y-offY); 
        //in the original code, zoomPanel is being shifted. here we are shifting containerPanel
       
        getParent().doLayout();             // do the layout for containerPanel
        getParent().getParent().doLayout(); // do the layout for jf (JFrame)
       
        prevN = n;
        
        
    }
 

    @Override
    public Dimension getPreferredSize() {

    
        return preferredSize;
        
    }

 
    
    public void setPreferredSize(Dimension d) {
    	preferredSize=d;
        
    }

   
    
 public void addMember(Member member) {
    	
        members.add(member);
        
      // member.getnodesList();
       addMemberMidPointCoordinate(member);
       addMemberNodeCoordinate(member);
    
    }
 
 public void addTempMember(TempMember tm) {
 	
    tempmember.add( tm);

 
 }
 public void  addMemberMidPointCoordinate(Member m) {

 	memberMidPointCoordinates.add(m.getMidPoint());
  
 }
 public void  addMemberNodeCoordinate(Member m) {

	 	memberNodeCoordinates.add(m.getMemberStart());
	 	memberNodeCoordinates.add(m.getMemberEnd());
	 	System.out.println(m.getMemberEnd() +","+"Start");
	 	System.out.println(m.getMemberStart() +","+"End");
	
	 }
 

public ArrayList<Point>  sortMemberMidPointCoordinate() {

 	
 	Collections.sort(members , new MemberComparator());
	
 	for(int i=0;i< members.size();i++) {
 	
 		members.get(i).setMemberNum(i);
 	
 		//System.out.println( members.get(i).getMidPoint()+"MidPoint"+members.get(i).getNumber()+" , "+members.get(i).getNodesList()[0]+","+members.get(i).getNodesList()[1]);
 		//System.out.println(members.get(i).getNodesList()[0]+","+members.get(i).getNodesList()[1]);
 	}
 	System.out.println();

 return  memberMidPointCoordinates ;
  
 }
public ArrayList<Point>  sortMemberNodeCoordinate() {

 	
 	Collections.sort(members , new MemberNodeComparator());
	
 	for(int i=0;i< members.size();i++) {
 		System.out.println(members.get(i).getNumber()+" , "+members.get(i).getNodesList()[0]+", "+members.get(i).getNodesList()[1]);

 	}
 	System.out.println();

 return  memberNodeCoordinates ;
  
 }
public ArrayList<int[]> getSortedMemberNodes(){
	for(int i=0;i<members.size();i++) {
		sortedMemberNodes.add(members.get(i).getNodesList2());
	
	
	}
	return sortedMemberNodes;
}


    public void addForces(Forces f) {
    	
    	forces.add(f);
    	
    }
    
    public void addReaction(DrawReactions drawReactions) {
    	
    	drawreactions.add(drawReactions);
    	
    }
    
    public void addDisplacement(DrawDisplacement3 drawDisplacement) {
    	
    	drawdisplacement.add(drawDisplacement);
    	
    }
    
    public void addShear(DrawShear drawShear) {
    	
    	drawshear.add(drawShear);
    	
    }
    
  public void addBending(DrawBending drawBending) {
    	
    	drawbending.add(drawBending);
    	
    }
    
    public void addShearResults(double[] shearresults,String forcetype) {
    	
    	this.shearresults.add(shearresults);
    	this.forcetype.add(forcetype);
    }
    
    public ArrayList<double[]> getShearResults() {
    	
    	return shearresults;
    	
    }
  public void addDisplacementResults(double[] displacemnetresults) {
    	
    	this.displacementresults.add(displacemnetresults);
    	
    }
    
    public ArrayList<double[]> getDisplacementResults() {
    	
    	return displacementresults;
    	
    }
    public ArrayList<String> getForceType() {
    	
    	return forcetype;
    	
    }
    
   
    
    public void deleteNode() {
    	 for (Iterator<Node> nodeIterator = nodes.iterator(); nodeIterator.hasNext();) {
    		Node node = nodeIterator.next();
    		
    		if (node.isSelected()) {
    		 nodeIterator.remove();
    		//fixtures.remove(node.getNumber()-1);
    		}

         }
    	
    }
    public void deleteFilteredNode() {
   	 for (Iterator<Node> nodeIterator = nodesfilterd.iterator(); nodeIterator.hasNext();) {
   		Node node = nodeIterator.next();
   		
   		if (node.isSelected()) {
   		 nodeIterator.remove();
   		//fixtures.remove(node.getNumber()-1);
   		}

        }
   	
   }
  
    public void deleteMember() {
    	
   	 for (Iterator<Member> memberIterator = members.iterator(); memberIterator.hasNext();) {
   		 
   		Member member = memberIterator.next();
   		
   		if (member.isSelected()) {
   			
   		 memberIterator.remove();
   		
   		}

        }
   	
   }
    public void deleteForce() {
    	
      	 for (Iterator<Forces> forceIterator = forces.iterator(); forceIterator.hasNext();) {
      		 
      		Forces forces = forceIterator.next();
      		
      		if (forces.isSelected()) {
      			
      		 forceIterator.remove();
      		 
      		}

           }
      	
      }
    public void deleteReactions() {
    	
    	
    	drawreactions.clear();

     	
     }
  public void deleteDisplacements() {
    	
    	
	  drawdisplacement.clear();

     	
     }
  
  public void deleteShearDiagram() {
	  drawshear.clear();
  }
  public void deleteBendingDiagram() {
	  drawbending.clear();
  }
  public void deleteResultGraphics() {
	    deleteReactions();
		deleteDisplacements();
		deleteShearDiagram();
		deleteBendingDiagram();
  }
    public boolean containsNode(Node n) {
    	
    	boolean result = false ;
    	
    
		return result;
    	
    }

    public ArrayList<Node> getNodes() {
    	
        return nodes;
        
    }
  public ArrayList<Node> getFilteredNodes() {
    	
        return nodesfilterd;
        
    }
    
    public ArrayList<Member> getMembers() {
    	
        return members;
        
    }
    
    public int getMemberDOF() {
    	
    	return dof;
    
    }
    public int setBeamdof(int dof) {
    	//beams.size();
    	this.dof=dof*3;
    	
    	return dof;
    
    }
    public int getNodeNum() {
    	//beams.size();
    	//int dof = beams.size()*3;
    	
    	return nodenum;
    
    }
    public int setNodeNum(int nodenum) {
    	//beams.size();
    	this.nodenum=nodenum;
    	//int dof = beams.size()*3;
    //System.out.println(nodenum);
    	return nodenum;
    
    }
  
    
    int[] beamset;
    
    public void beamEndList() {
    	
    	beamends.add(nodenum);
		//int[] nodelistt =node;
    	for (int i=0;i<beamends.size();i++) {
    		beamset[i]=beamends.get(i);
    	}
		
    	
    }
    
    public int[] getBeamEndList() {
    	return beamset;
    }
    

    public void setZoom(double zoom) {
    	//System.out.println(zoom + "zoom");
    	zoom=Math.round(zoom*10.0)/10.0;
    	//System.out.println(zoom + "zoom2");
    	this.scale=zoom;
    	
    	//System.out.println(scale);
    }

    
    public void setTranslate(int dx, int dy) {
    	this.tranx=dx;
    	this.trany=dy;
    }
    
 
    
    public ArrayList<Forces> getForces(){
    	
		return forces;
    	
    }
    
	public boolean setRefresh(boolean refreshed) {
		return this.refresh=refreshed;
	}
	public boolean isRefreshed() {
		
		return refresh;
		
	}
	
    int count4 = 0;
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2d = (Graphics2D) grphcs;
        

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
      
       AffineTransform at = g2d.getTransform();

      
        at.translate(tranx, trany);
        at.scale(scale, scale);
        at.translate(-tranx, -trany);
        g2d.setTransform(at);
     
		g2d.setColor(Color.lightGray);
		drawGrid(g2d);
		

				
				g2d.setColor(Color.lightGray);
		
				
				
	
        for (Node node : nodesfilterd)
        {
        	node.drawNode(g2d);
        			
        	if(hideForces ==false)
        	{
        				
        	
        		for (Forces2 force:node.getForce())
        		{
        					 
        			force.drawForce(g2d);
        	        
        	    }
        		 
        		 
//        	 for (Forces force:forces) {
//        		 
//        		 if (node.getNodeNumber() == force.getNumber() && force.getType().matches("Point")) {
//        			 //System.out.println("hereforce2");
//     				//force.drawPointLoad(g2d);
//				 }
//        		 if (node.getNodeNumber() == force.getNumber() && force.getType().matches("Moment")) {
//        			//	force.drawMoment(g2d);
//  				 }
//        		 
//        	 }
        	
        		

        			}
        }
            

            	for (Member member :members) {
            		
            	member.drawBeam(g2d);
           
       			 count4++;
       			 if(hideForces ==false) {
       			 for (Forces force:forces) {
       				 
       				
       					 
       				 if (member.getNumber() == force.getNumber() && force.getType().matches("Point")) {
       					 force.drawPointLoad(g2d);
       				 }
       				 if (member.getNumber() == force.getNumber() && force.getType().matches("UDL")) {
       					 force.drawUDL(g2d, member);
     				 }
       				 if (member.getNumber() == force.getNumber() && force.getType().matches("Moment")) {
       					 force.drawMoment(g2d);
     				 }
        		
       			 }
       			 
            	}
            	}
            	for (DrawReactions drawreaction:drawreactions) {
            		drawreaction.drawReactions(g2d);
            	}
            	for (DrawDisplacement3 drawdisplacement:drawdisplacement) {
            		drawdisplacement.drawDisplacments(g2d);
            	}
            	for (DrawShear drawshear:drawshear) {
            		drawshear.drawShear(g2d);
            	}
            	for (DrawBending drawbending:drawbending) {
            		drawbending.drawBending(g2d);
            	}
            	
            	//TempMember tempmember = new TempMember();
            	//
            	for (TempMember tempmember:tempmember) {
            		
            		tempmember.drawTemp(g2d);
            		
            		}
            	
            	tempmember.clear();
            	
            	}
    
   
	public void clearDrawPanel() {
    	
    	nodes.clear();
		nodesfilterd.clear();
		
		//fixtures.clear();
		members.clear();
		forces.clear();
		
		setRefresh(true);
		repaint();
    }
    
    public void setHideForces(boolean hideforce) {
    	hideForces = hideforce;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public void saveToFile(File file ) throws IOException {
    	 
    	 FileOutputStream  fos = new FileOutputStream(file) ;
    	 ObjectOutputStream  oos = new ObjectOutputStream(fos) ;
    	
    	

    	 Node[] node = nodesfilterd.toArray(new Node[nodesfilterd.size()]);
    	// String[] fixture = fixtures.toArray(new String[fixtures.size()]);
    	 Member[] member = members.toArray(new Member[members.size()]);
    	 Forces[] force = forces.toArray(new Forces[forces.size()]);
    	// DrawReactions[] drawreaction = drawreactions.toArray(new DrawReactions[drawreactions.size()]);
    	// DrawDisplacement[] drawdisplacements = drawdisplacement.toArray(new DrawDisplacement[drawdisplacement.size()]);
    	 
    	 
    	 oos.writeObject(node);
    	// oos.writeObject(fixture);
    	 oos.writeObject(member);
    	 oos.writeObject(force);
        // oos.writeObject(drawreaction);
         //oos.writeObject(drawdisplacements);
    	
    	 
    	 oos.close();
    	 fos.close();
     }
         
     public void loadFromFile(File file ) throws IOException {
    	 
    	 FileInputStream  fis = new FileInputStream(file) ;
    	 ObjectInputStream  ois = new ObjectInputStream(fis) ;
    	
    	 
    	
    	 try {
			Node[] node= (Node[]) ois.readObject();
			String[] fixture= (String[]) ois.readObject();
			Member[] member= (Member[]) ois.readObject();
			Forces[] force = (Forces[]) ois.readObject();
			//DrawReactions[] drawreaction = (DrawReactions[]) ois.readObject();
			//DrawDisplacement[] drawdisplacements=(DrawDisplacement[]) ois.readObject();
			
			clearDrawPanel();
			//drawreactions.clear();
			//drawdisplacement.clear();
			
			nodesfilterd.addAll(Arrays.asList(node));
			//fixtures.addAll(Arrays.asList(fixture));
			members.addAll(Arrays.asList(member));
			forces.addAll(Arrays.asList(force));
			//drawreactions.addAll(Arrays.asList(drawreaction));
			
			setRefresh(true);
			
			
			//members.get(0).getnodesList();
			//System.out.println(members.size());
			
			 
			 repaint();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	 ois.close();
    	 fis.close();
    	
     }
           public int loadDOF() {
        	  int loaddof= nodes.get(3).getDOF();
        	   
        	   return loaddof;
           }
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      	
           // }

		public void drawCurrentCoordinates(int x, int y) {
			// TODO Auto-generated method stub
			
		}
       

 
}