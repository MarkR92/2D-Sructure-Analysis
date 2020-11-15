import java.awt.AWTException;
import java.awt.Robot;


public class SnaptoGrid{
	public boolean result;
	private int x;
	private int y;
	
	
	SnaptoGrid(int x, int y,Boolean result){
		this.x=x;
		this.y=y;
		this.result=result;
		
	}
	
				


void snap() {
	if (result = true) {
	
	
	try {
		Robot robot = new Robot();
		robot.mouseMove(x+6, y+88);
		
	} catch (AWTException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		
	}
  
	
	
}
}
}