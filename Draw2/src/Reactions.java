import java.awt.Point;
import java.util.ArrayList;

public class Reactions  {
	
	private int dof;								//Degree of freedom for the structure.
	private int reduceddof;							//Degree of freedom for the structure once boundary conditions are accounted for.
	private double pointReactionA;					//Reaction force of the lowest number node of a member.
	private double pointReactionB;					//Reaction force of the highest number node of a member.
	private double momentReactionA;					//Moment reaction force of the lowest number node of a member.
	private double momentReactionB;					//Moment reaction force of the highest number node of a member.
	private double[] localMemberForces;		   		//Contains local member forces reactions. This is derived from loading conditions.
	private double[][] localPrimeMemberForces;		//Contains localPrime member forces reactions. (localPrime = local*beta matrix).
	private double[] blownupLocalMemberForces;		//Contains local member forces reactions blown up to dof.
	private double[] globalNodalForces;				//Contains global nodal forces.
	private double[] globalMemberForces;			//Contains all member forces for a given structure under certain loading conditions.
	private double[] Rlocal;
	private double[] reduced;
	private double[] subQ ;
	public ArrayList<double[]> memberForces = new ArrayList<double[]>();
	
	public String forcetype;
	public Point forcelocation;
	
	Reactions(int dof, int reduceddof) {
		
		   this.dof = dof;
		   this.reduceddof = reduceddof;
		   this.globalMemberForces=new double[dof];
		    
		}
	
 public void calculateLocalMemberReaction(double P, String forcetype, double L, Point forcelocation, Point memberstart, Point memberend) { //Calculate reactions due to applied force on members.
	
	    this.forcetype=forcetype;
	    this.forcelocation=forcelocation;
	    
		double a = Math.abs(forcelocation.getX()-memberend.x)/10/2;
		double b = Math.abs(forcelocation.getX()-memberstart.x)/10/2;
		
		if(a+b == 0) {
			
			 a = Math.abs(forcelocation.getY()-memberend.y)/10/2;
			 b = Math.abs(forcelocation.getY()-memberstart.y)/10/2;
		}

		double a2 = a*a;
		double b2 = b*b;
		
		double L2 = L*L;
		double L3 = L*L*L;
		
		if(forcetype.matches("Point")) {
			
			pointReactionA = (P*b2*(3*a+b))/L3;
			pointReactionB = (P*a2*(a+b*3))/L3;
		
			momentReactionA =  (P*b2*a)/L2;
			momentReactionB = -(P*b*a2)/L2;
		
			localMemberForceVector();
		}
		
		if(forcetype.matches("UDL")) {
			
			pointReactionA = (P*L)/2;
			pointReactionB = (P*L)/2;
			 
			momentReactionA = (P*L2)/12;
			momentReactionB = -(P*L2)/12;
			
			localMemberForceVector();
			}
		
		if(forcetype.matches("Moment")) {
			
			pointReactionA = (6*P*a*b)/L3;
			pointReactionB = (6*P*a*b)/L3;
			
			momentReactionA = P*b*(2*a-b)/L2;
			momentReactionB = P*a*(2*b-a)/L2;
			
			localMemberForceVector();
		}
		
		
	}
 
 public void localMemberForceVector() { 
	// System.out.println("herelocal");
		localMemberForces = new double[6];
		
		 for(int i=0;i<6;i++) {
			 localMemberForces[i]=0;
		 }
		
		localMemberForces[1] = pointReactionA;
		localMemberForces[2] = momentReactionA;
		
		localMemberForces[4] = pointReactionB;
		localMemberForces[5] = momentReactionB;
		
	 }

 public void globalMemberForceVector( double[][] localMemberForces,int number) {
	 
	 
	 localPrimeMemberForces = localMemberForces;
	 if(localPrimeMemberForces==null)
	 {
		 for(int i=0;i<6;i++) {
			 localPrimeMemberForces[i][0]=0;

				System.out.println( localPrimeMemberForces[i]+"global");

			}
	
			
			//memberForces.add(number, localPrimeMemberForces.);		
		}
 }
	 
 public double[][] getGlobalMemberForces() {
		 
		return localPrimeMemberForces;
			
	} 
 
 public double[] getLocalMemberForces() {
	 
		return localMemberForces;
		
	}
 public void setLocalMemberForces(double[] reactions) {
	 
		 localMemberForces=reactions;
		
	}
	 
 public void nodeReactionVector( double P,int dof,int index,double[] globalQtemp, String forcetype,double direction) {
	 
     //Search's the structure and finds all the forces applied to nodes and places them into a list.
	 
	 globalNodalForces= new double[dof];
	 
	
	 
	
//Check what the force type and direction is and assign to correct location in force vector.
	 
	 if(forcetype.matches("Moment")) {
		 
		globalQtemp[index*3-1]= P;
		 
	 }
	 
	 if(forcetype.matches("Point") && direction==0) {
		
		globalQtemp[index*3-2]= P;
		
	 }
	
	 if(forcetype.matches("Point") && direction==90) {
		
			globalQtemp[index*3-3]= P;
			
	 }
	 
	 globalNodalForces=globalQtemp;

	for(int i=0;i< dof;i++) {
		
		//System.out.print(globalNodalForces[i] + "n ");
	}
	System.out.println();	
//	
	 }
 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private int index;




public void blowupLocalMemberForceVector( double[] localPrimeMemberForces, int[] beamNumber) {


	blownupLocalMemberForces = new double[dof]; //create zero matrix based on dof

 for ( int i=0; i< 6; i++) {	
	 index= beamNumber[i]-1 ;
	 
	 blownupLocalMemberForces[index]+= localPrimeMemberForces[i];
	 
	 
 }

 for(int j=0; j<dof; j++) {
	// System.out.println(blownupLocalMemberForces[j]+"FV");
 }
//System.out.println();



}
public double[] getBlownupLocalMemberForceVector() {
	return blownupLocalMemberForces;
	
}

public void addLocalMemberForces( double[] blownupLocalMemberForces) {
	

	//System.out.println(localFV);
	if(blownupLocalMemberForces ==null) {
		for(int i=0;i<dof;i++) {
			globalMemberForces[i]=0;
					}
	}else {
		for(int i=0;i<dof;i++) {
			
			
			globalMemberForces[i] += blownupLocalMemberForces[i];

		}
	}
	
	//globalMemberForces=tempForceF;

	for(int i=0;i<dof;i++) {
		
		
	System.out.print(globalMemberForces[i] + " f ");

			
	}
	System.out.println();
	
	
}




public void subtractNodeForces() {
	
	subQ = new double[dof];
	
	for(int i=0;i<dof;i++) {
		subQ[i]=0;
	}
	for (int i=0;i<dof;i++) {
		
		//System.out.print(globalF[i] +" s1  ");
		//System.out.print(globalQ[i] +" s2  ");
		//subQ[i] = globalQ[i]-globalF[i]; 
		 
		 if (globalMemberForces == null) {
				for(int j=0;j<dof;j++) {
//					//System.out.print(q[i] + "  ");
					globalMemberForces[i]=0;
							
					
			}
		 }
		 if (globalNodalForces == null) {
				for(int j=0;j<dof;j++) {
//					//System.out.print(q[i] + "  ");
					subQ[i] = -globalMemberForces[i];
							
					
			}
		 }else {
			 subQ[i] = globalNodalForces[i]-globalMemberForces[i]; 
		 }
		 
			
		 
	
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
				
			System.out.println(reduced[j] +"reduced");
			j++;
			
		}
		//reduced[i-reduceddof]= subQ[i];
	

	//System.out.print(reduced[i] +"  r ");	
		//System.out.print(subQ[i] +"  s2 ");	
	}
	System.out.println( );
	
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
		
		}
		//System.out.println(Ulocal[row] + " lo ");

		
		
	}
	
		
	for (int row =0; row<6;row++) {
//		Rlocal[row]+=memberForces.get(number)[row];
//		System.out.println(Rlocal[row] + " lo ");
		//System.out.println(memberForces.get(number)[row]+"r");
		//System.out.println(Rlocal[row]+" Rlocal");
	//}
	//System.out.println( );
	

	}
	//System.out.println( );
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
	//	System.out.println(U[col]);
		
		}
		
		//System.out.println();
		//System.out.print(R[row] + "  ");
}
	//System.out.println("Force Vector");
	for (int row =0; row<dof;row++) {
		
		R[row] += globalMemberForces[row];
		//System.out.println(String.format("%.2f",R[row]) + " ");		
		
		}
	//System.out.println();
	return R;
}



public double getRa() {
	 
	 return pointReactionA;
}
public double getRb() {
	 
	 return pointReactionB;
}
public double getMa() {
	 
	 return momentReactionA;
}
public double getMb() {
	 
	 return momentReactionB;
}

public String getForceType() {
	return forcetype;

}
public Point getLocation() {
	return forcelocation;
}

}
