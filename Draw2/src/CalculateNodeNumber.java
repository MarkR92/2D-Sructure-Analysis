
public class CalculateNodeNumber {
	
	private int current=1;
	private int old=1;
	
public int getNodeNumber() {
	
	if (old>current) {
		old--;
	}
	current =old;
	
	return current;
	
}

public int setNodeNumber(int nodenumber) {
	
	return this.current=nodenumber;
	
}
public int setOldNodeNumber(int oldnodenumber) {
	
	return this.old=oldnodenumber;
}

public void incrementNodeNumber() {
	current++;
	old++;
}

}
