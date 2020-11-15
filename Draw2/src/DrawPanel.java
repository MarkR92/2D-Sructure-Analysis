import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class DrawPanel extends JPanel {

   
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5724101582125350331L;
	
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Node> nodesfilterd = new ArrayList<>();
	public ArrayList<Member> members = new ArrayList<>();
	public ArrayList<TempMember> tempmember = new ArrayList<>();
	
	public ArrayList<Integer> beamends = new ArrayList<>();
	
	
	
	public ArrayList<String> fixtures = new ArrayList<>();
	public ArrayList<Forces> forces = new ArrayList<>();
	
	public ArrayList<DrawReactions> drawreactions = new ArrayList<>();
	public ArrayList<DrawDisplacement> drawdisplacement = new ArrayList<>();
	
	private double[] R;
	
	//public ArrayList<Element> element = new ArrayList<>();
	
	private Dimension preferredSize=(new Dimension(600,600)) ;   
	
	private static int prevN = 0;
	private int dof;
	private int nodenum;
	
	private double scale=1;
	private int tranx=0;
	private int trany=0;
	private boolean result;
	
	
	    int vpw =(int) (600*scale);//viewport width
	    int vph =(int) (600*scale);//viewport height
	    int gw = 10;//grid width
	    int gh = 10;//grid height
		
	    int npx, npy;//current snap coords
	    
	   
    public void  addNode(Node n) {

    	nodes.add(n);
    	n.setHighlighted(true);
    	
    	addFixture();
    	filterNodes(n);
    	
        repaint();
    	
     
    }
    
    public void filterNodes(Node n) {
    	
    	if( n.toAdd()==true) {
    		nodesfilterd.add(n);
    		
    	}
    
    		
    
    	
    }
 
   
    
    
    public void createGrid(int x, int y) {
    	
    	int xn = x, yn = y;
         int mx = xn % gw, my = yn % gh;
         if (mx<gw/2) npx = xn - mx;
         else npx = xn + (gw-mx);
         if (my<gh/2) npy = yn - my;
         else npy = yn + (gh-my);
        
    	
    }
    public void updatePreferredSize(int n, Point p) {
//   

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
    	    getParent().setLocation(getParent().getLocation().x-offX,getParent().getLocation().y-offY); 
    	    //in the original code, zoomPanel is being shifted. here we are shifting containerPanel

    	    getParent().doLayout();             // do the layout for containerPanel
    	    getParent().getParent().doLayout(); // do the layout for jf (JFrame)

    	    prevN = n;
    }
    @Override
    public Dimension getPreferredSize() {
    	//System.out.println(preferredSize);
        return preferredSize;
        
    }
    
    public void setPreferredSize(Dimension d) {
    	//preferredSize=d;
        
    }

    public void drawGrid(Graphics2D g) {
    	g.setColor(Color.lightGray);
    	g.clearRect(0, 0, vpw, vph);
    	
      //  g.setColor(Color.DARK_GRAY);
        
        //grid vertical lines
        for (int i=gw;i<vpw;i+=gw) {
             g.drawLine(i, 0, i, vph);
        }
        //grid horizontal lines
        for (int j=gh;j<vph;j+=gh) {
             g.drawLine(0, j, vpw, j);
        }
      //  g.setColor(Color.DARK_GRAY);
        //show the snapped point
        g.setColor(Color.BLACK);
        if (npx>=0 && npy>=0 && npx<=vpw && npy<=vph) {
        	 result =true;
             g.drawOval(npx-4, npy-4, 8, 8);
             
             
        }
        
        

   }
    public int getSnapX(){
    	
    	return this.npx;
    }
    public int getSnapY(){
    	
    	return this.npy;
    }
    
    
 public void addMember(Member member) {
    	
        members.add(member);
        
       member.getnodesList();
    
    }
 
 public void addTempMember(TempMember tm) {
 	
    tempmember.add( tm);

 
 }
 
    
    public void  addFixture() {
    
    	fixtures.add("Free");

    }

    public void addForces(Forces f) {
    	
    	forces.add(f);
    	
    }
    
    public void addReaction(DrawReactions drawReactions) {
    	
    	drawreactions.add(drawReactions);
    	
    }
    
    public void addDisplacement(DrawDisplacement drawDisplacement) {
    	
    	drawdisplacement.add(drawDisplacement);
    	
    }
    
    public void changeFixture(String fixture , Node n) {
    	
    	if( n.isSelected() ) {
    	fixtures.set(n.getNumber()-1, fixture);
    	n.setSelected(false);
    	
    	}
    	
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
    public int getNodeNum() {
    	//beams.size();
    	//int dof = beams.size()*3;
    	
    	return nodenum;
    
    }
    public int setNodeNum(int nodenum) {
    	//beams.size();
    	this.nodenum=nodenum;
    	//int dof = beams.size()*3;
    //	beamendList();
    	return nodenum;
    
    }
    
    int[] beamset;
    
    public void beamendList() {
    	
    	beamends.add(nodenum);
		//int[] nodelistt =node;
    	for (int i=0;i<beamends.size();i++) {
    		beamset[i]=beamends.get(i);
    	}
		
    	
    }
    
    public int[] getbeamendList() {
    	return beamset;
    }
    
    public int setBeamdof(int dof) {
    	//beams.size();
    	this.dof=dof*3;
    	
    	return dof;
    
    }
    public void setZoom(double zoom) {
    	//System.out.println(zoom);
    	this.scale=zoom;
    	
    	//System.out.println(scale);
    }
    
    public void setTranslate(int dx, int dy) {
    	this.tranx=dx;
    	this.tranx=dy;
    }
    
    public ArrayList<String> getFixtureType(){
    	
 		return fixtures;
     	
     }
    
    public ArrayList<Forces> getForces(){
    	
		return forces;
    	
    }
    
	
    int count4 = 0;
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2d = (Graphics2D) grphcs;
       // g2d.setColor(Color.DARK_GRAY);
 
        
        
        double width = getWidth();
        double height = getHeight();

        double zoomWidth = width * scale;
        double zoomHeight = height * scale;

        double anchorx = (width - zoomWidth) / 2;
        double anchory = (height - zoomHeight) / 2;
    
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
       // ArrayList<Integer> x=CreateGrid.XGridpoints;
		//ArrayList<Integer> y=CreateGrid.YGridpoints;
		
		//int s= x.size();
	//g2d.translate(anchorx, anchory);
	//	g2d.translate(tranx, trany);
	//g2d.scale(scale, scale);
		
		g2d.setColor(Color.lightGray);
		drawGrid(g2d);
		 
		
		//g2d.translate(anchorx, anchory);
		//	g2d.translate(tranx, trany);
		//g2d.scale(scale, scale);
		//for (int i= 0; i<s; i++) {
		//	for (int ii = 0; ii<s; ii++) {
				
				
				g2d.setColor(Color.lightGray);
			//	g2d.drawRect(x.get(i), y.get(ii),10, 10);
				//System.out.println(x.get(i));
				
				
		//}
	//}
		//g2d.scale(scale, scale);
		//repaint();
		//TempMember temp = new TempMember();
        for (Node node : nodesfilterd) {
        	
        	//TempMember temp = new TempMember(node.getCoord());
        	for (int i = 0; i<= fixtures.size()-1; i++) {
        	
        			node.drawNode(g2d);
        		//	temp.drawTemp(g2d);
        		 if(fixtures.get(node.getNumber()-1)=="Pinned" ){
        			 
        			node.drawFixturePinned(g2d);
        			
        		}
        		 if(fixtures.get(node.getNumber()-1)=="Fixed" ){
        			 
         			node.drawFixtureFixed(g2d);
         		}
        		 if(fixtures.get(node.getNumber()-1)=="Sliding" ){
        			 
          			node.drawFixtureSliding(g2d);
          		}
        	}
        	 for (Forces force:forces) {
        		 if (node.getNumber() == force.getNumber() && force.getType()=="Point") {
     				force.drawPointLoad(g2d);
				 }
        		 if (node.getNumber() == force.getNumber() && force.getType()=="Moment") {
        				force.drawMoment(g2d);
  				 }
        		 
        	 }
        	
        		

        }
            

            	for (Member member :members) {
            		
            	member.drawBeam(g2d);
            	//beam.drawBeamLength(g2d);
            	//temp.drawTemp(g2d);
       			 count4++;
       			 for (Forces force:forces) {
       		
       				 if (member.getNumber() == force.getNumber() && force.getType()=="Point") {
      				force.drawPointLoad(g2d);
				 }
       				 if (member.getNumber() == force.getNumber() && force.getType()=="UDL") {
           				force.drawUDL(g2d, member);
     				 }
       				if (member.getNumber() == force.getNumber() && force.getType()=="Moment") {
           				force.drawMoment(g2d);
     				 }
       				
       				
        		}
       			 
            	}
            	
            	for (DrawReactions drawreaction:drawreactions) {
            		drawreaction.drawReactions(g2d);
            	}
            	for (DrawDisplacement drawdisplacement:drawdisplacement) {
            		drawdisplacement.drawDisplacments(g2d);
            	}
            	
            	//TempMember tempmember = new TempMember();
            	//
            	for (TempMember tempmember:tempmember) {
            		//drawdisplacement.drawDisplacments(g2d);
            		tempmember.drawTemp(g2d);
            		//tempmember.clear();
            	//tempmember.clear();
            		}
            	tempmember.clear();
            	
            	}
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public void saveToFile(File file ) throws IOException {
    	 FileOutputStream  fos = new FileOutputStream(file) ;
    	 ObjectOutputStream  oos = new ObjectOutputStream(fos) ;
    	 
    	// Node[] nodea = nodes.toArray(new node(nodes.size()));
    	 Node[] n = nodes.toArray(new Node[nodes.size()]);
    	 
    	 System.out.println(getNodes().size() );
    	 oos.writeObject(n);
    	 
    	 oos.close();
    	 fos.close();
     }
         
     public void loadFromFile(File file ) throws IOException {
    	 FileInputStream  fis = new FileInputStream(file) ;
    	 ObjectInputStream  ois = new ObjectInputStream(fis) ;
    	 
    	// Node[] nodea = nodes.toArray(new node(nodes.size()));
    	 //Node [] n = nodes.toArray(new Node[nodes.size()]);
    	 
    	
    	 try {
			Node[] n= (Node[]) ois.readObject();
			
			nodes.clear();
			nodes.addAll(Arrays.asList(n));
			
			//System.out.println(n);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 repaint();
    	 System.out.println(nodes);
    	 ois.close();
    	 repaint();
     }
            	
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      	
           // }
       

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(600, 600);
//    }
}