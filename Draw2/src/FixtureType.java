
import java.util.ArrayList;

public class FixtureType {
	private int x;
	private int y;
	private int z;
	private int rx;
	private int ry;
	private int rz;
	
	public ArrayList<Integer> FixtureList = new ArrayList<>(); 

	

public void Fixed() {
	
	x=0;
	y=0;
	z=0;
	rx=0;
	ry=0;
	rz=0;
	
	FixtureList.add(x);
	FixtureList.add(y);
	FixtureList.add(z);
	FixtureList.add(rx);
	FixtureList.add(ry);
	FixtureList.add(rz);
	//return FixtureList;
}
public void Free() {
	
	x=1;
	y=1;
	z=1;
	rx=1;
	ry=1;
	rz=1;
	
	FixtureList.add(x);
	FixtureList.add(y);
	FixtureList.add(z);
	FixtureList.add(rx);
	FixtureList.add(ry);
	FixtureList.add(rz);
	//return FixtureList;
}
public ArrayList<Integer> FixList(){
	return FixtureList;
}




public ArrayList<Integer>  Pinned() {
	x=0;
	y=0;
	z=0;
	rx=1;
	ry=1;
	rz=1;
	
	FixtureList.add(x);
	FixtureList.add(y);
	FixtureList.add(z);
	FixtureList.add(rx);
	FixtureList.add(ry);
	FixtureList.add(rz);
	return FixtureList;
}

}
