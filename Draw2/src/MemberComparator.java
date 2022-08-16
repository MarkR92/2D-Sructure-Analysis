import java.util.Comparator;

public class MemberComparator implements Comparator<Member> {
	@Override
	public int compare(Member a, Member b) {
		if (a.getMidPoint().x<b.getMidPoint().x) {
			//System.out.println("x<x");
			return -1;
		}
		else if (a.getMidPoint().x>b.getMidPoint().x ) {
			//System.out.println("x>x");
			return  1;
		}
		else {
			//System.out.println("x=x");
			if (a.getMidPoint().y<b.getMidPoint().y) {
				//System.out.println("y<y");
				return -1;
			}
			else if (a.getMidPoint().y>b.getMidPoint().y) {
				//System.out.println("y>y");
				return 1;
			}else {
				return 0;
			}
			}
		
//		//return a.getCoord().x.compareTo(b.getCoord().x);
//		if ((a.getMidPoint().x<b.getMidPoint().x) || (a.getMidPoint().y<b.getMidPoint().y)) {
//			return -1;
//		}
//		else if (a.getMidPoint().x>b.getMidPoint().x || a.getMidPoint().y>b.getMidPoint().y) {
//			return 1;
//		}
//		else {
//		return 0;
//	}
	}
}
