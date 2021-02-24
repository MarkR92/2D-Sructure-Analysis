import java.awt.Point;
import java.util.ArrayList;

public class Reactions  {
	
	private int dof;							//Degree of freedom for the structure.
	private int reduceddof;						//Degree of freedom for the structure once boundary conditions are accounted for.
	private double reaction_a;					//Reaction force of the lowest number node of a member.
	private double reaction_b;					//Reaction force of the highest number node of a member.
	private double momentreaction_a;			//Moment reaction force of the lowest number node of a member.
	private double momentreaction_b;			//Moment reaction force of the highest number node of a member.
	private double[] localMemberForces;		    //Contains local member forces. This is derived from loading conditions.
	private double[] globalMemberForces;		//Contains global member forces. (global = local*beta matrix).
	private double[] globalF ;
	private double[] globalQ  ;
	private double[] R;
	private double[] Rlocal;
	private double[] localFV;
	private double[] reduced;
	public ArrayList<double[]> shearResults = new ArrayList<double[]>();
	public ArrayList<double[]> memberForces = new ArrayList<double[]>();
	
	public String forcetype;
	
	Reactions(int dof, int reduceddof) {
		
		   this.dof = dof;
		   this.reduceddof = reduceddof;
		    
		}
	
 public void calculateMemberReaction(double P, String forcetype, double L, Point forcelocation, Point memberstart, Point memberend) { //Calculate reactions due to applied force on members.
		
	    this.forcetype=forcetype;
	 
		double a = Math.abs(forcelocation.getX()-memberend.x)/10/2;
		double b = Math.abs(forcelocation.getX()-memberstart.x)/10/2;
		
		if(a+b == 0) {
			
			 a = Math.abs(forcelocation.getY()-memberend.y)/10/2;
			 b = Math.abs(forcelocation.getY()-memberstart.y)/10/2;
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
		System.out.println("here");
		
	}
 public void intialLocalForces() {
	 for(int i=0;i<6;i++) {
		 localMemberForces[i]=0;
	 }
	 memberForces.add(localMemberForces);
 }
 public void localMemberForceVector() { 
		
		localMemberForces = new double[6];
		
		 for(int i=0;i<6;i++) {
			 localMemberForces[i]=0;
		 }
		
		localMemberForces[1] = reaction_a;
		localMemberForces[2] = momentreaction_a;
		
		localMemberForces[4] = reaction_b;
		localMemberForces[5] = momentreaction_b;
		
		for(int i=0;i< localMemberForces.length;i++) {
			System.out.print(localMemberForces[i] + " q ");
		}
		System.out.println();
		
	 }
	 
 public void globalMemberForceVector(double[][] BetaInv, int number) {
	 localMemberForceVector();
			globalMemberForces = new double[6];

			for(int i=0;i<6;i++) {
				globalMemberForces[i]=0;
				for(int j=0;j<6;j++) {
				
					globalMemberForces[i]+=((BetaInv[i][j]*localMemberForces[j]));
						
		}
				
				
				

			}
			//System.out.println();	
			memberForces.add(number, globalMemberForces);
//			for(int i=0;i<6;i++) {
//			
//				for(int j=0;j<6;j++) {
//		
//			System.out.print(memberForces.get(i)[j] + " g ");
//			}
//			}
//				
//			System.out.println();
		}
	 
 public double[] getGlobalForce() {
		 
			return globalMemberForces;
			
		} 
	 
 public void nodeReactionVector( double P,int dof,int index,double[] globalQtemp, String forcetype,String direction) {
     
	 globalQ= new double[dof];
	 
	 globalQtemp = new double[dof];
	 
	 for(int i=0;i< dof;i++) {
		 globalQtemp[i]=0;
			
		}
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
			tempForceF[i]=0;
					}
	}else {
		for(int i=0;i<dof;i++) {
			
			
			tempForceF[i] += localFV[i];

		}
	}
	//}else {
	

	//}
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
		 
		 if (globalF == null) {
				for(int j=0;j<dof;j++) {
//					//System.out.print(q[i] + "  ");
					globalF[i]=0;
							
					
			}
		 }
		 if (globalQ == null) {
				for(int j=0;j<dof;j++) {
//					//System.out.print(q[i] + "  ");
					subQ[i] = -globalF[i];
							
					
			}
		 }else {
			 subQ[i] = globalQ[i]-globalF[i]; 
		 }
		 
			
		 
	
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

public void calculateLocalReactions(double[][] Klocal, double[] Ulocal, int number) {
	 Rlocal = new double[6];

	 //System.out.println(memberForces.get(1)[1]);
	
	for (int row =0; row<6;row++) {
		for(int col = 0; col<6; col++) {
	
		Rlocal[row] += (Klocal[row][col]*Ulocal[col])/1000/1000;
		//System.out.print(Rlocal[row] + "  ");
		}
		//System.out.println(" ");	
		
	}
	
	//System.out.println(memberForces.size());
	//for (int i =0; i<memberForces.size();i++) {
		
	for (int row =0; row<6;row++) {
		
		Rlocal[row]=Rlocal[row]+memberForces.get(number)[row];
		
		System.out.println(memberForces.get(number)[row]+"r");
	//}
	System.out.println( );
	
}
}
public double[] getLocalReactions() {
	
	return Rlocal;
}

public double[] calculateGlobalReaction(double[][] K, double [] U  ) {
	
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

public String getForceType() {
	return forcetype;

}

}
