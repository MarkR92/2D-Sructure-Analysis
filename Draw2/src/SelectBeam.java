
public class SelectBeam {
	int xcurrent;
	int ycurrent;
	
	int x1;
	int y1;
	int x2;
	int y2;
	
	
	SelectBeam(int xcurrent, int ycurrent, int x1, int y1, int x2, int y2){
		this.xcurrent= xcurrent;
		this.ycurrent = ycurrent;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		
		
	}
	
	
	
	
	
	public boolean result;
	boolean isPointonLine() {
		int m = (y2-y1)/((x2-x1));
		int c = y1-m*x1;
		//System.out.println(m);
		
		//System.out.println(ycurrent-y1);
		//System.out.println(m*(xcurrent-x1)+c);
		
		if (ycurrent-y1 == m*(xcurrent-x1)+c) {
			
			 result = true;
			 //System.out.println(m);
		}
		
		return result;
		
		
	}
	

}
