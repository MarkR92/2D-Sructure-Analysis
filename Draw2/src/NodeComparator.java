
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

   
	@Override
	public int compare(Node a, Node b) {
		
		//return a.getCoord().x.compareTo(b.getCoord().x);
		if (a.x<b.x) {
			//System.out.println("x<x");
			return -1;
		}
		else if (a.x>b.x ) {
			//System.out.println("x>x");
			return  1;
		}
		else {
			//System.out.println("x=x");
			if (a.y<b.y) {
				//System.out.println("y<y");
				return -1;
			}
			else if (a.y>b.y) {
				//System.out.println("y>y");
				return 1;
			}else {
				return 0;
			}
			}
	}
}



 