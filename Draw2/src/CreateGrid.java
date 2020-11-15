import java.util.ArrayList;

public class CreateGrid {
	int x;
	int xintersect;	//x coordinate where x and y intersect on the grid.
	int y;
	int yintersect;	//y coordinate where x and y intersect on the grid.
	int sizex = 0;
	int sizey = 0;
	int scl=10;	 	//Scale factor in (x,y). Each grid box is scl pixels high/wide. ?
	int w=600;	 	//Width of JFrame.
	int h=600; 		//Height of JFrame.
	
	double zoom=1;
	
	int col=w/scl;	//Number of boxes in column.
	int rows=h/scl;	//Number of boxes in row.
	//*(int)zoom+10
	//double zoom=1;
	
	public static ArrayList<Integer> XGridpoints;
	public static ArrayList<Integer> YGridpoints;
	
	public static ArrayList<Integer> XInter;
	public static ArrayList<Integer> YInter;
	
	
	
	 //Create a grid.
	public void createGridList(){
		 
		XGridpoints = new ArrayList<Integer>();
		YGridpoints = new ArrayList<Integer>();
		
	for (int x = 0; x<col; x++) {
		
		XGridpoints.add(x*scl);
		
		for (int y = 0; y<rows; y++) {
			
			YGridpoints.add(y*scl);			
			
}
		}
	
	sizex = XGridpoints.size();
	sizey = YGridpoints.size();
	
	}
	 //Find intersection on grid and create an Array list for x and y.
	public void gridIntersections() {
		 XInter = new ArrayList<Integer>();
		 YInter = new ArrayList<Integer>();
		 
		 for (int i = 0; i<sizex; i++) {
			
			 xintersect =XGridpoints.get(i);
			
			
			 for (int j = 0; j<sizey; j++) {
			 yintersect =YGridpoints.get(j);
			 
			 XInter.add(xintersect);
			 YInter.add(yintersect);
			 
			 
			 }
			
		 }
		 
		
		
	 }
	public void setZoom(double zoom) {
		this.zoom=zoom;
	}
	
}

