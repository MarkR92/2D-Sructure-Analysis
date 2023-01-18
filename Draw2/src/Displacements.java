import java.util.ArrayList;

public class Displacements {
	
	public double [] U;							//Displacement Vector
	private double[] Uglobal;
	private int reduceddof;
	private int dof;
	
	public Displacements(int dof,int reduceddof) {
		this.dof=dof;
		this.reduceddof=reduceddof;
	}
	
	public double[] calculateDeflections(double[][] K, double[] F) {
	
		U= new double [reduceddof];
	
		for (int row =0; row<reduceddof;row++) {
			
			for(int col = 0; col<reduceddof; col++) {
				
			U[row] += (K[row][col]*F[col])*1000*1000;
	
			}
			System.out.print(U[row] + " U  ");
			
		}
		System.out.println();
		return U;
	}
	
	public void blowupDisplacementVector(ArrayList<String> fixtureList) {
		
		 Uglobal = new double[dof];
		 
		 for(int z=0; z<fixtureList.size();z++) {
				
				if(fixtureList.get(z).matches("Fixed")) {
			
					for(int i=z*3;i<3*z+3;i++) {
					
							Uglobal[i]=Double.NaN;
	
					}
		
				}
				
		 if(fixtureList.get(z).matches("Pinned")) {
				
				for(int i=z*3;i<3*z+2;i++) {
				
					Uglobal[i]=Double.NaN;
	
					
				}
		
			}
			
			if(fixtureList.get(z).matches("Sliding")) {
				
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
		
	}
	public double[] getDisplacmentVector() {
		return Uglobal;
	}
//	
}
