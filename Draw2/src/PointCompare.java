import java.awt.Point;
import java.util.Comparator;

public class PointCompare implements Comparator<Point> {

	@Override
	public int compare(Point a, Point b) {
		if (a.x<b.x) {
			System.out.println("x<x");
			return -1;
		}
		else if (a.x>b.x) {
			System.out.println("x>x");
			return 1;
		}
		
		else  {
			System.out.println("x=x");
			if (a.y<b.y) {
				System.out.println("y<y");
				return -1;
			}
			else if (a.y>b.y) {
				System.out.println("y>y");
				return 1;
			}else {
				return 0;
			}
	}
		//return 0;
	}
}
