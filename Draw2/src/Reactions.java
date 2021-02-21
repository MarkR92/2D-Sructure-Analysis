import java.awt.Point;
import java.util.ArrayList;

public class Reactions  {
	
	private int dof;							//Degree of freedom for the structure.
	private int reduceddof;						//Degree of freedom for the structure once boundary conditions are accounted for.
	private double reaction_a;					//Reaction force of the lowest number node of a member.
	private double reaction_b;					//Reaction force of the highest number node of a member.
	private double momentreaction_a;			//Moment reaction force of the lowest number node of a member.
	private double momentreaction_b;			//Moment reaction force of the highest number node of a member.
	private double[] globalForceVector;
	private double[] globalF ;
	private double[] globalQ  ;
	private double[] R;
	private double[] Rlocal;
	private double[] localFV;
	private double[] reduced;
	
	public ArrayList<double[]> shearResults = new ArrayList<double[]>();
	
	
	Reactions(int dof, int reduceddof) {
		
		   this.dof = dof;
		   this.reduceddof = reduceddof;
		    
		}
	
	public void calculateMemberReaction(double P, String forcetype, double L, Point forcelocation, int x1, int x2, int y1, int y2) {
		
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
		
		if(forcetype.matches("Point")) {
			
		reaction_a = (P*b2*(3*a+b))/L3;
		reaction_b = (P*a2*(a+b*3))/L3;
		
		momentreaction_a =  (P*b2*a)/L2;
		momentreaction_b = -(P*b*a2)/L2;
		
		}
		
		if(forcetype.matches("UDL")) {
			
			reaction_a = (P*L)/2;
			reaction_b = (P*L)/2;
			 
			momentreaction_a = (P*L2)/12;
			momentreaction_b = -(P*L2)/12;
			 
			}
		
		if(forcetype.matches("Moment")) {
			
			reaction_a = (6*P*a*b)/L3;
			reaction_b = (6*P*a*b)/L3;
			
			momentreaction_a = P*b*(2*a-b)/L2;
			momentreaction_b = P*a*(2*b-a)/L2;
			
		}
		
	}
	
	 public double[] memberReactionVector() { 
		 
		double[] localq= new double[6];
		for(int i=0;i< 6;i++) {
			//localq[i]=0;
		}
		 
		localq[1] = reaction_a;
		localq[2] = momentreaction_a;
		
		localq[4] = reaction_b;
		localq[5] = momentreaction_b;
		
		for(int i=0;i< localq.length;i++) {
			System.out.print(localq[i] + " q ");
		}
		System.out.println();
		
		return localq;

	 }
	 
 public void nodeReactionVector( double P,int dof,int index,double[] globalQtemp, String forcetype,String direction) {
     

//Check what the force type and direction is and assign to correct location in force vector.
	 
	 if(forcetype.matches("Moment")) {
		 
		globalQtemp[index*3-1]= P;
		 
	 }
	 
	 if(forcetype.matches("Point") && direction.matches("Perpendicular")) {
		
		globalQtemp[index*3-2]= P;
		
	 }
	
	 if(forcetype.matches("Point") && direction.matches("Parallel")) {
		
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
	double[] force =memberReactionVector();

	for(int i=0;i<6;i++) {
		globalForceVector[i]=0;
		for(int j=0;j<6;j++) {
			
			//globalForceVector[i]=0;
			
			//for(int z=0;z<6;z++) {
				
				globalForceVector[i]+=((BetaInv[i][j]*force[j]));
				
				//System.out.print(force[i] + " g ");
			//}
}
		System.out.print(globalForceVector[i] + " g ");
		
		//System.out.println();	
	}
	System.out.println();	
}

public double[] getGlobalForce() {
	return globalForceVector;
	
	
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private int index;
private ArrayList<double[]> localFlist = new ArrayList<double[]>() ;



public void blowupLocalMemberForceVector( double[] localFPrime, int[] beamNumber) {


 localFV = new double[dof]; //create zero matrix based on dof

 for ( int i=0; i< 6; i++) {	
	 index= beamNumber[i]-1 ;
	 
	 localFV[index]+= localFPrime[i];
 }





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
			System.out.print(globalF[i] + "  ");

			
	}
	System.out.println();
	
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
		
	//	System.out.print(globalF[i] +" s1  ");
		
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
System.out.println( );
	
	
	if(reduceddof!=0) {
	//reduceForceVector();
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////
public void reduceForceVector(ArrayList<String> fixtureList) {
	
	 reduced = new double[reduceddof];
	 
	 for(int z=0; z<fixtureList.size();z++) {
			
			if(fixtureList.get(z).matches("Fixed")) {
				
				for(int i=z*3;i<3*z+3;i++) {
						
						subQ[i]=Double.NaN;
					//	System.out.print(subQ[i] +"  s2 ");	
					}
				//System.out.println( );
				
			}
			
			if(fixtureList.get(z).matches("Pinned")) {
				
				for(int i=z*3;i<3*z+2;i++) {
							
					subQ[i]=Double.NaN;
					
				}
				
				//System.out.println( );
			}
			
			if(fixtureList.get(z).matches("Sliding")) {
				
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
	 Rlocal = new double[6];
	
	for (int row =0; row<6;row++) {
		for(int col = 0; col<6; col++) {
	
		Rlocal[row] += (Klocal[row][col]*Ulocal[col])/1000/1000;
		//System.out.print(Klocal[row][col] + "  ");
		}
		//System.out.println(" ");	
		
	}
	for (int row =0; row<6;row++) {
		//System.out.println(Rlocal[row]);
	}
	//return Rlocal;
	//shearResults.add(Rlocal);
}
public double[] getLocalReactions() {
	
	return Rlocal;
}

public double[] calculateGlobalReaction(double[][] K, double [] U) {
	
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
	 
	 return reaction_a;
}
public double getRb() {
	 
	 return reaction_b;
}
public double getMa() {
	 
	 return momentreaction_a;
}
public double getMb() {
	 
	 return momentreaction_b;
}



	 	

}
