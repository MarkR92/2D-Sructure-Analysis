import java.util.ArrayList;




public class CreateGlobalMatrix {
	
	private int dof;  					//degree of freedom determines global matrix size
	private int reduceddof;				//dof after boundary conditions are applied

	private int indexI;
	private int indexJ;
	private int iadjust;
	private int jadjust;
	private double[][] globalStiffness ; //global stiffness  matrix
	private double [][] localStiffness; //local stiffness matrix "blown up" with zeros
	private double [][]TempGlobalStiffness ;
	
	private ArrayList<double[][]> localKlist = new ArrayList<double[][]>() ;
	
	double[][] reducedGlobalStiffness ;
	
	public CreateGlobalMatrix(int dof) {
		
	    this.dof = dof;
	    TempGlobalStiffness=new double[dof][dof];
	   
	}

	
	//public void blowupLocalk( double[][] localkPrime, int[] nodeNumber) {

//		localK = new double[dof][dof]; 								//create zero matrix based on dof
//		
//		for ( int i=0; i< nodeNumber.length; i++) {	
//			
//			 index = nodeNumber[i];									//each node will have a row and col in the stiffness matrix 
//		 System.out.println(index +"ind");
//		}
//		
//		
//	//System.out.println(index +"ind");
//		 index=(index-2)*3;											//Index is used in three's
//		 System.out.println(index +"ind2");
//		for(int i=0+index;i<6+index;i++) {
//			  
//				for(int j=0+index;j<6+index;j++) {
//					
//					localK[i][j]+= localkPrime[i-index][j-index]; 	//add local matrix to zero matrix 
//					//System.out.print(localK[i][j]+" ");
//				}
//				//System.out.println( );
//				
//		  }
//		
//		
//		//localKlist.add(localK);	
		
	//}
	
	public void blowupLocalStiffness( double[][] localkPrime, int[] nodeNumber) {

		localStiffness= new double[dof][dof]; 								//create zero matrix based on dof
		//System.out.println("Local Prime");
		for(int i=0;i<6;i++) {
			
			 indexI = nodeNumber[i]-1;
			
			for(int j=0;j<6;j++) {
				
				indexJ = nodeNumber[j]-1;
				
				// System.out.println(indexI +"i");
				// System.out.println(indexI +"j");
				localStiffness[indexI][indexJ]+= localkPrime[i][j];
				//System.out.println(localkPrime[i][j]);
			}
			
		}
		 
		 for(int i=0;i<dof;i++) {
				
				for(int j=0;j<dof;j++) {
					
				//	System.out.print(localStiffness[i][j]+" ");
				}
				//System.out.println( );
		 }
		 addLocalStiffness();
	
	}
	
	

	
	public void addLocalStiffness() {
		
		
		
			 for(int i=0;i<dof;i++) {
					
					for(int j=0;j<dof;j++) {
						
						TempGlobalStiffness[i][j] += localStiffness[i][j];		
						
						}
			
			 }
		
			 globalStiffness=TempGlobalStiffness;
			 
			 System.out.println("Global Start");
			 for(int i=0;i<dof;i++) {
					
					for(int j=0;j<dof;j++) {

						System.out.print(globalStiffness[i][j]+" ");
						
						}
						System.out.println();
						
			 }
			 System.out.println("Global End");
			 
	
		
		
	}
	
	


	
	public void reduceglobalStiffness(ArrayList<String> fixtureList) {

		 double[][] K=new double[dof][dof];
		 for(int i=0;i<dof;i++) {
				
				for(int j=0;j<dof;j++) {
					
					K[i][j] += globalStiffness[i][j];		
					
					
					}
				
		 }
		
		
		reduceddof=dof;
		
		for(int z=0; z<fixtureList.size();z++) {
			
		if(fixtureList.get(z).matches("Fixed")) {
			
			
			reduceddof = reduceddof-3;
			
		for(int i=0;i<dof;i++) {
			
			for(int j=z*3;j<3*z+3;j++) {
				
				K[i][j]=Double.NaN;
				
			}
			
			}
		
		for(int i=z*3;i<3*z+3;i++) {
			
			for(int j=0;j<dof;j++) {
				
				K[i][j]=Double.NaN;
				System.out.print(K[i][j] + "  ");
			}
			System.out.println();
			}
		
		}
		
		
		if(fixtureList.get(z).matches("Pinned")) {	
			
			reduceddof = reduceddof-2;
			
		for(int i=0;i<dof;i++) {
			
			for(int j=z*3;j<3*z+2;j++) {
				
				K[i][j]=Double.NaN;
				
			}
			
			}
		
		for(int i=z*3;i<3*z+2;i++) {
			
			for(int j=0;j<dof;j++) {
				
				K[i][j]=Double.NaN;
				System.out.print(K[i][j] + "  ");
			}
			//
			
			System.out.println();
			}
		
		
		}	
		if(fixtureList.get(z).matches("Sliding") ) {
			reduceddof=reduceddof-1;
			//System.out.println("hh");
			
			for(int i=0;i<dof;i++) {
				
				for(int j=1+z*3;j<3*z+2;j++) {
					
					K[i][j]=Double.NaN;
					
				}
				
				}
			
			for(int i=1+z*3;i<3*z+2;i++) {
				
				for(int j=0;j<dof;j++) {
					
					K[i][j]=Double.NaN;
					System.out.print(K[i][j] + "  ");
				}
				System.out.println();
				}
		}
		
		}

		
		reducedGlobalStiffness = new double[reduceddof][reduceddof];
		//System.out.println(dof);
		//System.out.println(reduceddof);
		
		for(int i=0;i<dof;i++) {
			for(int j=0;j<dof;j++) {
				//System.out.println(countj);
					if (Double.isNaN(K[i][j])!=true) {
						//System.out.println(count);
						
						reducedGlobalStiffness[iadjust][jadjust] += K[i][j];
//					
					jadjust++;
					if(jadjust==reduceddof) {
						jadjust=0;
						iadjust++;	
					}
						}
					
					}
				
			}
		//counti=0;
		for(int i=0;i<reduceddof;i++) {
			for(int j=0;j<reduceddof;j++) {
				System.out.print(reducedGlobalStiffness[i][j] + "  ");
				
			}
		System.out.println();
		
			}
	
		//System.out.println(reduceddof + "new");
			}
	
	public double[][] getGlobalStiffness(){
		
		

		for(int i=0;i<dof;i++) {
			
			for(int j=0;j<dof;j++) {
								
				
				System.out.print(globalStiffness[i][j] + "g  ");
	}
				System.out.println();
		}
		
		return globalStiffness;
	}
	public double[][] getreducedGlobalStiffness(){
		
		return reducedGlobalStiffness;
	}
	
	public int getReducedDOF() {
		
		return reduceddof;
		
	}
public ArrayList<double[][]> getlocalKlist(){
		
		return localKlist;
		
	}
	
	}

