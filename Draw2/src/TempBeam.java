
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class TempBeam {
	private Point beamstart;
	private Point beamend;
	
	
//public TempBeam(Point beamstart, Point beamend) {
//	this.beamstart=beamstart;
//	this.beamend=beamend;
//}

public Point setBeamStart(Point beamstart) {
	
	//System.out.println(beamstart);
	
	return this.beamstart=beamstart;
	
}
public Point setBeamEnd(Point beamend) {
	//System.out.println(beamend);

	return this.beamend=beamend;
}



public Graphics2D drawTempBeam(Graphics2D g2d) {
	//int l = getLength();

	g2d.setColor(Color.DARK_GRAY);
    	
    
 
    g2d.drawLine(beamstart.x+20,beamstart.y,beamend.x+20,beamend.y);
    
//	color= Color.DARK_GRAY;
    
    return g2d;
}
}
