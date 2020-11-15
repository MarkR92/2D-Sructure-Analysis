
public class SelectGraphic {
	int xcurrent;
	int ycurrent;
	int xpreviousminus;
	int ypreviousminus;
	int xpreviousplus;
	int ypreviousplus;
	
	
	SelectGraphic(int xc, int yc, int xpm, int ypm,int xpp, int ypp){
		this.xcurrent=xc;
		this.ycurrent=yc;
		this.xpreviousminus=xpm;
		this.ypreviousminus=ypm;
		this.xpreviousplus=xpp;
		this.ypreviousplus=ypp;
		
	}
		
	
	public boolean result ;
	boolean isSelected() {
		if (xcurrent>= xpreviousminus && xcurrent <= xpreviousplus && ycurrent >= ypreviousminus && ycurrent <= ypreviousplus)  {
			result = true;
			//System.out.println(result);
			
	}
		
	return result;	
	

}
	
	
}

