import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Reactions {
	
	private int dof;
	private int reduceddof;
	
	Reactions(int dof, int reduceddof) {
		   this.dof = dof;
		   this.reduceddof = reduceddof;
		    
		}
	
	double Ra;
	double Rb;
	double Ma;
	double Mb;
	
	double[] globalForceVector;
	private double[] globalF ;
	private double[]  globalQ  ;
	private double[] R;
	private double[] localFV;
	
	private double[] reduced;
	
	private String direction;
	
	
	public void calculateMemberReaction(double P, String Type, double L, Point forcelocation, int x1, int x2, int y1, int y2) {
		
		double a = Math.abs(forcelocation.getX()-x2)/10/2;
		double b = Math.abs(forcelocation.getX()-x1)/10/2;
		
		
		if(a+b == 0) {
			
			 a = Math.abs(forcelocation.getY()-y2)/10/2;
			 b = Math.abs(forcelocation.getY()-y1)/10/2;
		}
		
		
		double a2 = Math.pow(a, 2);
		double b2 = Math.pow(b, 2);
		
		double L2 = Math.pow(L, 2);
		double L3 = Math.pow(L, 3);
		

		if(Type == "Point") {
			
		 Ra = (P*b2*(3*a+b))/L3;
		 Rb = (P*a2*(a+b*3))/L3;
		
		 Ma =  (P*b2*a)/L2;
		 Mb = -(P*b*a2)/L2;
		
		}
		
		if(Type == "UDL") {
			
			 Ra = (P*L)/2;
			 Rb = (P*L)/2;
			 
			 Ma = (P*L2)/12;
			 Mb = -(P*L2)/12;
			 
			}
		
		if(Type == "Moment") {
			
			Ra = (6*P*a*b)/L3;
			Rb = (6*P*a*b)/L3;
			
			Ma=P*b*(2*a-b)/L2;
			Mb=P*a*(2*b-a)/L2;
			
		}
		//memberReactionVector();
		
	}
	
	

	 public double[] memberReactionVector() { 
		 
		double[] localq= new double[6];
		for(int i=0;i< 6;i++) {
			localq[i]=0;
		}
		 
		localq[1] = Ra;
		localq[2] = Ma;
		
		localq[4] = Rb;
		localq[5] = Mb;
		
		for(int i=0;i< localq.length;i++) {
			//System.out.print(localq[i] + " q ");
		}
		//System.out.println();
		
		return localq;

	 }
	 
 public void nodeReactionVector( double P,int dof,int index,double[] globalQtemp, String forcetype,String direction) {
     

//Check what the force type and direction is and assign to correct location in force vector.
	 
	 if(forcetype =="Moment") {
		 
		globalQtemp[index*3-1]= P;
		 
	 }
	 
	 if(forcetype =="Point" && direction == "Perpendicular") {
		
		globalQtemp[index*3-2]= P;
		
	 }
	
	 if(forcetype =="Point" && direction == "Parallel") {
		
			globalQtemp[index*3-3]= P;
			
	 }
	 
	 globalQ=globalQtemp;

	for(int i=0;i< dof;i++) {
		
		//System.out.print(globalQ[i] + " ");
	}
	//System.out.println();	
	
	 }
 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 

public void globalForce(double[][] BetaInv, double[]Force) {
	
	globalForceVector = new double[6];
	

	for(int i=0;i<6;i++) {
		globalForceVector[i]=0;
		for(int j=0;j<6;j++) {
			
			//globalForceVector[i]=0;
			
			//for(int z=0;z<6;z++) {
				
				globalForceVector[i]+=((BetaInv[i][j]*Force[j]));
				
			//}
}
		//System.out.print(globalForceVector[i] + " g ");
		
		//System.out.println();	
	}
	//System.out.println();	
}

public double[] getGlobalForce() {
	return globalForceVector;
	
	
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private int index;
private ArrayList<double[]> localFlist = new ArrayList<double[]>() ;



public void blowupLocalMemberForceVector( double[] localFPrime, int[] beamNumber) {


//	localK = new double[dof][dof]; 								//create zero matrix based on dof
//	
//	for(int i=0;i<6;i++) {
//		
//		 indexI = nodeNumber[i]-1;
//		 
//		for(int j=0;j<6;j++) {
//			
//			indexJ = nodeNumber[j]-1;
//			
//			localK[indexI][indexJ]+= localkPrime[i][j];
//			
//		}
//		
//	}
	//System.out.println(beamNumber);
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 localFV = new double[dof]; //create zero matrix based on dof
//	
//	for ( int i=0; i< beamNumber.length; i++) {	
 for ( int i=0; i< 6; i++) {	
	 index= beamNumber[i]-1 ;
	 
	 localFV[index]+= localFPrime[i];
 }
//		
//		// index = nodeNumber[i];	//each node will have a row and col in the stiffness matrix 
//		index= beamNumber[i] ;
//		
//	System.out.println(index+"index");
//		
//		
////		 if (index==0) {  // intial localkPrime(nodes 1 and 2 added to positions 0 and 1)	
////				
////			 index=0;											
////		 }	
////		 if (index==1) {  // intial localkPrime(nodes 1 and 2 added to positions 0 and 1)	
////											
////			 index=3;											
////		 }													
//	}
//	 index=(index-2)*3;
//
//	for(int i=0+index;i<6+index;i++) {
//		  
//		
//				
//				localFV[i]+= localFPrime[i-index]; // add local matrix to zero matrix 
//		
//			
//	System.out.print(localFV[i] +"   ");	
//	  }
//	System.out.println( );
//	
//	localFlist.add(localFV);	
//	
//	for(int i=0;i<dof;i++) {
//
////System.out.print(localFV[i] +" FV  ");	
//}
////System.out.println( );
//




}

public ArrayList<double[]> getlocalFlist(){
	
	return localFlist;
	
}




public void addLocalMemberForces(double[] tempForceF) {
	
	
	//System.out.println(localFV);
	if(localFV ==null) {
		for(int i=0;i<dof;i++) {
		//	localFV[i]+=0;
					}
	}
	
	
	for(int i=0;i<dof;i++) {
		
		
		tempForceF[i] += localFV[i];

	}
	
	globalF=tempForceF;

	for(int i=0;i<dof;i++) {
		
		
		//System.out.print(globalF[i] + " f ");

			
	}
//	System.out.println();
	
	if (globalF == null) {
		for(int i=0;i<dof;i++) {
//			//System.out.print(q[i] + "  ");
			//globalF[i]= 0;
//		}
			
			
	}
		
	}
	
}
public void addLocalForces2() {
	
	
	int size = getlocalFlist().size();
	
	globalF = new double[dof];
	
	for(int i=0;i<dof;i++) {
//		//System.out.print(q[i] + "  ");
		globalF[i]= 0;
	}
	double[] localk1=new double [dof];
	double[] localk2=new double [dof];
	
	for (int ii=0; ii<=size-1; ii++) {
		
		 localk1=localFlist.get(ii);
		
		 for (int iii=0; iii<=size-2; iii++) {
				
				if(size>1) {
					
					 localk2=localFlist.get(iii);
			}
	}
		
	}
		
	for(int i=0;i<dof;i++) {
		
		//for(int j=0;j<9;j++) {
							
			globalF[i] = localk1[i]+localk2[i];
		//	System.out.print(globalF[i] + "  ");

			
	}
	//System.out.println();
	
	if (globalF == null) {
		for(int i=0;i<dof;i++) {
//			//System.out.print(q[i] + "  ");
			globalF[i]= 0;
//		}
	}
	}
}

private double[] subQ ;

public void subtractNodeForces() {
	
	subQ = new double[dof];
	
	for(int i=0;i<dof;i++) {
		subQ[i]=0;
	}
	for (int i=0;i<dof;i++) {
		
		
		
		//subQ[i] = globalQ[i]-globalF[i]; 
		 
		 if (subQ == null) {
				for(int j=0;j<dof;j++) {
//					//System.out.print(q[i] + "  ");
					//globalQ[i]=0;
			}
		 }
		 
			 subQ[i] = globalQ[i]-globalF[i]; 
		 
	
	//System.out.print(subQ[i] +" s1  ");	
	}
//System.out.println( );
	
	
	if(reduceddof!=0) {
	//reduceForceVector();
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////
public void reduceForceVector(ArrayList<String> fixtureList) {
	
	 reduced = new double[reduceddof];
	 
	 for(int z=0; z<fixtureList.size();z++) {
			
			if(fixtureList.get(z)=="Fixed") {
				
				for(int i=z*3;i<3*z+3;i++) {
						
						subQ[i]=Double.NaN;
					//	System.out.print(subQ[i] +"  s2 ");	
					}
				//System.out.println( );
				
			}
			
			if(fixtureList.get(z)=="Pinned") {
				
				for(int i=z*3;i<3*z+2;i++) {
							
					subQ[i]=Double.NaN;
					
				}
				
				//System.out.println( );
			}
			
			if(fixtureList.get(z)=="Sliding") {
				
				for(int i=1+z*3;i<3*z+2;i++) {
					
					
					
					subQ[i]=Double.NaN;
					
				
					
				}
				
				//System.out.println( );
			}
			for(int i=0; i<dof;i++) {
			//System.out.print(subQ[i] +"  s2 ");	
			}
		//System.out.println();
			
	 }
	int j=0;
	for (int i =0;i<dof;i++) {
		
		//System.out.println(subQ[i] +"n");
		
		if (Double.isNaN(subQ[i])!=true) {
		//	System.out.println(subQ[i] +"r");
			
			//for (int j =0;j<=reduceddof;j++) {
				reduced[j] = subQ[i];
			j++;
			//}
		}
		//reduced[i-reduceddof]= subQ[i];
	

	//System.out.print(reduced[i-reduceddof] +"  r ");	
		//System.out.print(subQ[i] +"  s2 ");	
	}
	//System.out.println( );
	
}
/////////////////////////////////////////////////////////////////////////////////////////////////
public double[] getReducedForceVector() {
	
	return reduced;
}
public void calculateLocalReactions(double[][] Klocal, double[] Ulocal) {
	double[] Rlocal = new double[6];
	
	for (int row =0; row<6;row++) {
		for(int col = 0; col<6; col++) {
	
		Rlocal[row] += Klocal[row][col]*Ulocal[col];
		}
	}
}
public  double[] calculateGlobalReaction(double[][] K, double [] U) {
	
	double[] R = new double[dof];
	
	for (int row =0; row<dof;row++) {
		for(int col = 0; col<dof; col++) {
	
		R[row] += K[row][col]*U[col]/1000/1000;
		
		//System.out.print(K[row][col] + "  ");	
		//System.out.println(U[col]);
		
		}
		
		//System.out.println();
		//System.out.print(R[row] + "  ");
}
	//System.out.println("Force Vector");
	for (int row =0; row<dof;row++) {
		
		R[row] += globalF[row];
		//System.out.println(String.format("%.2f",R[row]) + " ");		
		
		}
	//System.out.println();
	return R;
}

public double[] getReactions() {
	return this.R;
	
}
public double[] setReactions(double R[]) {
	return this.R=R;
	
}

public double getRa() {
	 
	 return Ra;
}
public double getRb() {
	 
	 return Rb;
}
public double getMa() {
	 
	 return Ma;
}
public double getMb() {
	 
	 return Mb;
}



	 	

}
