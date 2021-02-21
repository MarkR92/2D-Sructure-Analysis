import java.util.ArrayList;




public class CreateGlobalMatrix {
	
	private int dof;  					//degree of freedom determines global matrix size
	private int reduceddof;				//dof after boundary conditions are applied
	private int index;
	private int indexI;
	private int indexJ;
	private int iadjust;
	private int jadjust;
	
	private double[][] globalK ; //global stiffness  matrix
	//private double[][] K;
	private double[][] globalK2;
	private double [][] localK; //local stiffness matrix "blown up" with zeros
	
	private ArrayList<double[][]> localKlist = new ArrayList<double[][]>() ;
	
	double[][] reducedK ;
	
	public CreateGlobalMatrix(int dof) {
		
	    this.dof = dof;
	    
	   
	}
	
	public void blowupLocalk( double[][] localkPrime, int[] nodeNumber) {

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
		
	}
	
	public void blowupLocalk2( double[][] localkPrime, int[] nodeNumber) {

		localK = new double[dof][dof]; 								//create zero matrix based on dof
		//System.out.println(dof);
		for(int i=0;i<6;i++) {
			//System.out.println(nodeNumber[i]);
			 indexI = nodeNumber[i]-1;
			
			for(int j=0;j<6;j++) {
				
				indexJ = nodeNumber[j]-1;
				
				// System.out.println(indexI +"i");
				// System.out.println(indexI +"j");
				localK[indexI][indexJ]+= localkPrime[i][j];
				
			}
			
		}
		 
		 for(int i=0;i<dof;i++) {
				
				for(int j=0;j<dof;j++) {
					
					//System.out.print(localK[i][j]+" ");
				}
				//System.out.println( );
		 }
	
	}
	public ArrayList<double[][]> getlocalKlist(){
		
		return localKlist;
		
	}
	
	
	
	public void addLocalStiffness(double[][] tempGlobalK, double[][] tempGlobalK2) {
		
			 for(int i=0;i<dof;i++) {
					
					for(int j=0;j<dof;j++) {
						
						tempGlobalK[i][j] += localK[i][j];		
						tempGlobalK2[i][j] += localK[i][j];
					//	System.out.print(tempGlobalK[i][j]+" ");
						
						}
					//	System.out.println();
			 }
			 
			 globalK=tempGlobalK;
			 
			 for(int i=0;i<dof;i++) {
					
					for(int j=0;j<dof;j++) {
						
							
						
						//System.out.print(globalK[i][j]+" ");
						
						}
						//System.out.println();
			 }
			 
			 
			 globalK2=tempGlobalK2;
		// return tempGlobalK;
		
		
		
	}
	
	
	
	public void addLocalStiffness2() {
		
//		int size = getlocalKlist().size();
//		
//		//System.out.println(size);
//		
//		globalK = new double[dof][dof];
//		globalK2 = new double[dof][dof];
//		
//		double[][] localk1=new double [dof][dof];
//		
//		for (int ii=0; ii<=size-1; ii++) {
//			
//			 localk1 =localKlist.get(ii);
//			 
//			 for(int i=0;i<dof;i++) {
//					
//					for(int j=0;j<dof;j++) {
//										
//						globalK[i][j] += localk1[i][j];
//						globalK2[i][j] += localk1[i][j];
//						
//						}
//			 }
//			
//		}
//			
//		for(int i=0;i<dof;i++) {
//			
//			for(int j=0;j<dof;j++) {
//								
//				//globalK[i][j] += localk1[i][j];
//				
//				
//			//	globalK2[i][j] = localk1[i][j]+localk2[i][j];
//				
//				//System.out.print(globalK[i][j] + "  ");
//	}
//				//System.out.println();
//		}

	}
	
	
	
	public void reduceGlobalk(ArrayList<String> fixtureList) {
	
	
		double[][] K=globalK2;
		
		reduceddof=dof;
		
		for(int z=0; z<fixtureList.size();z++) {
			
		if(fixtureList.get(z).matches("Fixed")) {
			
			//System.out.println(z +"here");
			reduceddof = reduceddof-3;
			//System.out.println(dof +"dof");
		for(int i=0;i<dof;i++) {
			
			for(int j=z*3;j<3*z+3;j++) {
				
				K[i][j]=Double.NaN;
				
			}
			
			}
		
		for(int i=z*3;i<3*z+3;i++) {
			
			for(int j=0;j<dof;j++) {
				
				K[i][j]=Double.NaN;
				//System.out.print(K[i][j] + "  ");
			}
			//System.out.println();
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
				//System.out.print(K[i][j] + "  ");
			}
			//System.out.println();
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
					//System.out.print(K[i][j] + "  ");
				}
				//System.out.println();
				}
		}
		
		}
		for(int i=0; i<dof;i++) {
			for(int j=0; j<dof;j++) {
				
				//System.out.print(K[i][j] + "  ");
				
			}
			//System.out.println();
		}
		//System.out.println(reduceddof + "new");
		
		reducedK = new double[reduceddof][reduceddof];
		//System.out.println(dof);
		//System.out.println(reduceddof);
		for(int i=0;i<dof;i++) {
			
			for(int j=0;j<dof;j++) {
				//System.out.println(countj);
					if (Double.isNaN(K[i][j])!=true) {
						//System.out.println(count);
						
						reducedK[iadjust][jadjust] += K[i][j];
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
				//System.out.print(reducedK[i][j] + "  ");
				
			}
			//System.out.println();
		
			}
	
		//System.out.println(reduceddof + "new");
			}
	public double[][] getGlobalK(){

//		for(int i=0;i<dof;i++) {
//			
//			for(int j=0;j<dof;j++) {
//								
//				
//				//System.out.print(globalK[i][j] + "  ");
//	}
//				//System.out.println();
//		}
		
		return globalK;
	}
	public double[][] getReducedK(){
		
		return reducedK;
	}
	
	public int getReducedDOF() {
		
		return reduceddof;
		
	}
	
	}

