
public class ParseTextEntry {
	String text;
	int[] nodeCoordinates;
	int[] nodeCoordinatesX;
	int[] nodeCoordinatesY;
	String[] fixtures;
	String[] forces;
	
	public ParseTextEntry(String text) {
		this.text=text;
		
		parseNodeCoordinates();
		parseFixtures();
	}
	public void parseNodeCoordinates() {
		
		String str = text;
        String[] arrOfStr = str.split("[N: FXPS;,]+");
        nodeCoordinatesX=new int[(arrOfStr.length-1)/2];
        nodeCoordinatesY=new int[(arrOfStr.length-1)/2];
		int j=0;
		        for ( int i=1;i<arrOfStr.length;i++) {
		        	
			         nodeCoordinatesX[j]=Integer.parseInt(arrOfStr[i]);
			         nodeCoordinatesY[j]=Integer.parseInt(arrOfStr[i+1]);
			        // System.out.println(number);
			       i++;  
			       j++;
		        }
		        
		           
		         
		    }
	public void parseFixtures() {
		String str = text;
        String[] arrOfStr = str.split("[N: 0123456789;,]+");
        fixtures=new String[(arrOfStr.length-1)];
        for ( int i=1;i<arrOfStr.length;i++) {
        	
	         fixtures[i-1]=(arrOfStr[i]);
	        
       }

        
	}
	public void parseForces() {
		String str = text;
        String[] arrOfStr = str.split("[N: 0123456789;,]+");
        forces=new String[(arrOfStr.length-1)];
	}
	
	public String[] getFixtures() {
		return fixtures;
		
	}
	
	public int[] getNodeCordinates() {
//		for(int i=0;i<nodeCoordinates.length;i++) {
//			//System.out.println(nodeCoordinates[i]);
//		}
		return nodeCoordinates;
		
	}
	public int[] getNodeCordinatesX() {
		for(int i=0;i<nodeCoordinatesX.length;i++) {
		//System.out.println(nodeCoordinatesX[i]+","+nodeCoordinatesY[i]);
		}
		return nodeCoordinatesX;
		
	}
	public int[] getNodeCordinatesY() {
	
		return nodeCoordinatesY;
		
	}
		//}
	}


