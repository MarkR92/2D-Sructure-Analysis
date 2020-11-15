import java.util.ArrayList;

public class Displacements {
	
	public double[][] K = new double [3][3]; // Stiffness Matrix
	public double[] F = new double [3];	//Force Vector
	public double [] U;	//Displacement Vector
	private double[] Uglobal;
	private double[] Ulocal;
	
	private int reduceddof;
	private int dof;
	
	public Displacements(int dof,int reduceddof) {
		this.dof=dof;
		this.reduceddof=reduceddof;
		
		//this.F=F;
	}
	
	public double[] calculateDeflections(double[][] K, double[] F) {
		
		//System.out.println(reduceddof);
		U= new double [reduceddof];
		//K = new double [3][3];
		//F = new double [3];
		
		
		for (int row =0; row<reduceddof;row++) {
			//U[row]=0;
			for(int col = 0; col<reduceddof; col++) {
				//U[col]=0;
			U[row] += (K[row][col]*F[col])*1000*1000;
			
			
			//System.out.print(F[col] + "   h");
			}
			System.out.print(U[row] + "   ");
			
		}
		System.out.println();
		return U;
	}
	
	public void blowupDisplacementVector(ArrayList<String> fixtureList) {
		
		 Uglobal = new double[dof];
		 
		 for(int z=0; z<fixtureList.size();z++) {
				
				if(fixtureList.get(z)=="Fixed") {
			
					for(int i=z*3;i<3*z+3;i++) {
					
							Uglobal[i]=Double.NaN;
	
					}
		
				}
				
		 if(fixtureList.get(z)=="Pinned") {
				
				for(int i=z*3;i<3*z+2;i++) {
				
					Uglobal[i]=Double.NaN;
	
					
				}
		
			}
			
			if(fixtureList.get(z)=="Sliding") {
				
				for(int i=1+z*3;i<3*z+2;i++) {
					
					
					
					Uglobal[i]=Double.NaN;
					
				
					
				}
				
			}
			//System.out.println( );
			}
		 for (int i =0; i<dof;i++) {
				//System.out.print(Uglobal[i] + "   Unan");
				}
		 
			
	 
		 int count = 0;
		for (int row =0; row<dof; row++) {
			//U[row]=0;
			//int count = 0;
			
			if (Uglobal[row]==0 ) {
				
				
					
				Uglobal[row] = U[count];
				count++;
				
				
			}
			
			
			else
			 {
				Uglobal[row]=0;
			}
		
			
		
			
	}
		for (int i =0; i<dof;i++) {
			//System.out.print(Uglobal[i] + "   U");
			}
			//System.out.println();
		//System.out.println();
	}
	public double[] getDisplacmentVector() {
		return Uglobal;
	}
	public void localDeflections(int beamnumber) {
		Ulocal = new double[6];
		int j = 0;
		//System.out.println(beamnumber);
		for (int i=beamnumber*3;i<6; i++) {
		Ulocal[j]=Uglobal[i]; 
		j++;
		}
		
		for (int i =0; i<6;i++) {
			//System.out.println(Ulocal[i] + "L ");
			}
		//System.out.println();
	}
	
}
